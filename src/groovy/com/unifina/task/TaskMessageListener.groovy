package com.unifina.task

import grails.converters.JSON

import org.apache.log4j.Logger
import org.codehaus.groovy.grails.commons.GrailsApplication

import com.unifina.kafkaclient.UnifinaKafkaConsumer
import com.unifina.kafkaclient.UnifinaKafkaMessage
import com.unifina.kafkaclient.UnifinaKafkaMessageHandler
import com.unifina.utils.MapTraversal

class TaskMessageListener implements UnifinaKafkaMessageHandler {
	
	GrailsApplication grailsApplication
	UnifinaKafkaConsumer consumer
	List<TaskWorker> localTaskWorkers

	boolean quit = false
	
	public static final Logger log = Logger.getLogger(TaskMessageListener.class)
	
	public TaskMessageListener(GrailsApplication grailsApplication, List<TaskWorker> localTaskWorkers) {
		this.grailsApplication = grailsApplication
		this.localTaskWorkers = localTaskWorkers
		
		Map<String,Object> kafkaConfig = MapTraversal.flatten((Map) MapTraversal.getMap(grailsApplication.config, "unifina.kafka"));
		Properties properties = new Properties();
		for (String s : kafkaConfig.keySet())
			properties.setProperty(s, kafkaConfig.get(s).toString());
		
		consumer = new UnifinaKafkaConsumer(properties);
		consumer.subscribe(MapTraversal.getString(grailsApplication.config, "unifina.task.messageQueue"),this,false)
	}

	public void quit() {
		consumer.close()
	}

	@Override
	public void handleMessage(UnifinaKafkaMessage msg) {
		Map json = JSON.parse(msg.toString())
		if (json.type=="abort") {
			def id = json.id
			log.info("Abort message received for task $id")
			// Is any of the local TaskWorkers running a Task by this id?
			localTaskWorkers.each {TaskWorker tw->
				if (tw.currentTask?.id==id) {
					tw.abort()
				}
			}
		}
		else log.warn("Unknown type of task message received: "+msg)
	}

}