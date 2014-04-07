package com.unifina.utils;

import grails.plugins.springsecurity.SpringSecurityService;

import java.lang.reflect.Constructor;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.unifina.domain.security.SecUser;

/**
 * This class provides methods to create the Globals instance that serves
 * as a holder/context for a certain SignalPath run. The returned object must
 * be explicitly destroyed when it is no longer needed. The default Globals objects
 * destroys itself on the DataSource stop event, but if you do not run the DataSource
 * (saving, loading etc.) you must destroy the Globals object explicitly.
 * 
 * @author Henri
 *
 */
public class GlobalsFactory {
	
	private static final Logger log = Logger.getLogger(GlobalsFactory.class);
	
	public static Globals createInstance(Map signalPathContext, GrailsApplication grailsApplication) {
		return new GlobalsFactory().create(signalPathContext, grailsApplication);
	}
	
	public Globals create(Map signalPathContext, GrailsApplication grailsApplication) {
		try {
			String className = MapTraversal.getString(grailsApplication.getConfig(), "unifina.globals.className");
			Class clazz = this.getClass().getClassLoader().loadClass(className!=null ? className : "com.unifina.utils.Globals");
			SecUser user = null;
			try {
				user = (SecUser) ((SpringSecurityService)grailsApplication.getMainContext().getBean("springSecurityService")).getCurrentUser();
			} catch (Exception e) {
				log.warn("create: springSecurityService is not defined, current user is null!");
				return null;
			}
			Constructor<Globals> c = clazz.getConstructor(Map.class, GrailsApplication.class, SecUser.class);
			return c.newInstance(signalPathContext,grailsApplication,user);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	} 
}