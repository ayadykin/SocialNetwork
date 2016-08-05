<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Sign In</title>
</head>
<body>
	<%@ include file="../fragments/header.jsp"%>
	<div class="container">
		<form class="form-narrow form-horizontal"
			action="j_spring_security_check" method="post">

			<fieldset>
				<legend>Please Sign In</legend>
				<c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>
                <c:if test="${not empty msg}">
                    <div class="msg">${msg}</div>
                </c:if>
				<div class="form-group">
					<label for="inputEmail" class="col-lg-2 control-label">Email</label>
					<div class="col-lg-10">
						<input type="text" class="form-control" id="inputEmail"
							placeholder="Email" name="j_username" />
					</div>
				</div>
				<div class="form-group">
					<label for="inputPassword" class="col-lg-2 control-label">Password</label>
					<div class="col-lg-10">
						<input type="password" class="form-control" id="inputPassword"
							placeholder="Password" name="j_password" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-lg-offset-2 col-lg-10">
						<div class="checkbox">
							<label> <input type="checkbox"
								name="_spring_security_remember_me" /> Remember me
							</label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-lg-offset-2 col-lg-10">
						<button type="submit" class="btn btn-default">Sign in</button>
					</div>
				</div>
				<div class="form-group">
					<div class="col-lg-offset-2 col-lg-10">
						<p>
							New here? <a href="signup">Sign Up</a>
						</p>
					</div>
				</div>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</fieldset>
		</form>
	</div>
</body>
</html>