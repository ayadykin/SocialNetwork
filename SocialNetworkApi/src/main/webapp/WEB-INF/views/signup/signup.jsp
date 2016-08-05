<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Signup</title>
</head>
<body>
	<%@ include file="../fragments/header.jsp"%>
	<div class="container">
		<form:form class="form-narrow form-horizontal" action="signup"
			method="post" commandName="signupFormDto">

			<fieldset>
				<legend>Please Sign Up</legend>
				<c:if test="${not empty exception}">
                    <div class="error">${exception}</div>
                </c:if>
				<div class="form-group">
					<label for="email" class="col-lg-2 control-label">Email</label>
					<div class="col-lg-10">
						<form:input path="email" type="text" class="form-control"
							id="email" name="email" placeholder="Email address" />
						<form:errors path="email" cssClass="alert alert-danger"
							element="div" />
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-lg-2 control-label">Password</label>
					<div class="col-lg-10">
						<form:input path="password" type="password" class="form-control"
							id="password" name="password" placeholder="Password" />
						<form:errors path="password" cssClass="alert alert-danger"
							element="div" />
					</div>
				</div>				
				<div class="form-group">
					<div class="col-lg-offset-2 col-lg-10">
						<button type="submit" class="btn btn-default">Sign up</button>
					</div>
				</div>
				<div class="form-group">
					<div class="col-lg-offset-2 col-lg-10">
						<p>
							Already have an account? <a href="signin">Sign In</a>
						</p>
					</div>
				</div>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</fieldset>
		</form:form>
	</div>
</body>
</html>