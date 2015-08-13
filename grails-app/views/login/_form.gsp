<div class="page-signin-alt">

	<h1 class="form-header"><g:message code="springSecurity.login.header"/></h1>

	<!-- Form -->
	<form action='${postUrl}' method='POST' id='loginForm' autocomplete='off' class="panel">
	
		<p class='text-danger login-failed-message' ${flash.message ? "" : "style='display:none'"}>${flash.message}</p>
	
		<div class="form-group">
			<input type="text" name="j_username" id="username" class="form-control input-lg" placeholder="${message(code:"secuser.username.label", default:"Username")}">
		</div> <!-- / Username -->

		<div class="form-group signin-password">
			<input type="password" name="j_password" id="password" class="form-control input-lg" placeholder="Password">
			<g:link controller="register" action="forgotPassword" class="forgot">Forgot?</g:link>
		</div> <!-- / Password -->
		
		<g:if test="${ grailsApplication.config.grails.plugin.springsecurity.rememberMe.enabled == true }">
			<div class="checkbox remember">
				<label class="text-center">
					<input type="checkbox" class="px" name="_spring_security_remember_me">
					<span class="lbl">Remember me</span>
				</label>
			</div>
		</g:if>
		
		<div class="form-actions">
			<input id="loginButton" type="submit" value="${message(code:'springSecurity.login.button')}" class="btn btn-primary btn-block btn-lg">
		</div> <!-- / .form-actions -->
	</form>
	<!-- / Form -->

</div>