<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Profile</title>
</head>
<body>
	<%@ include file="../fragments/header.jsp"%>
	<div class="container">
		<form:form class="form-narrow form-horizontal" action="profile"
			method="post" commandName="profileDto">
			<fieldset>
				<legend>User profile</legend>
				<c:if test="${not empty msg}">
					<div class="msg">${msg}</div>
				</c:if>
				<div class="form-group">
					<label class="col-lg-3 control-label">First name</label>
					<div class="col-lg-9">
						<input type="text" class="form-control" id="firstName"
							name="firstName" value="${profileDto.firstName}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-3 control-label">Last name</label>
					<div class="col-lg-9">
						<input type="text" class="form-control" id="lastName"
							name="lastName" value="${profileDto.lastName}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-3 control-label">City</label>
					<div class="col-lg-9">
						<input type="text" class="form-control" id="city" name="city"
							value="${profileDto.city}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-3 control-label">Country</label>
					<div class="col-lg-9">
						<input type="text" class="form-control" id="country"
							name="country" value="${profileDto.country}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-3 control-label">Locale</label>
					<div class="col-lg-9">
						<select class="form-control" id="locale" name="locale">
							<option value="${profileDto.locale}" selected="selected">${profileDto.locale.getDisplayCountry()}</option>
							<c:forEach items="${profileDto.locales}" var="locale">
								<c:if test="${not empty locale.getDisplayCountry()}">
									<option value="${locale}">${locale.getDisplayCountry()}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-3 control-label">Translate</label>
					<div class="col-lg-1">
						<input type="checkbox" class="form-control" id="translate"
							name="translate" value="true"
							<% if(true) {out.print("checked=\"checked\"");}%> /> 
					</div>
				</div>
				<div class="panel-heading text-center">
					<div class="col-lg-offset-2 col-lg-10">
						<button type="submit" class="btn btn-default">Save</button>
					</div>
				</div>
				<a href="${pageContext.request.contextPath}/profile/changepassword"
					class="btn btn-default"> Change Password </a> <input type="hidden"
					name="${_csrf.parameterName}" value="${_csrf.token}" />
			</fieldset>
		</form:form>
		<%@ include file="../fragments/footer.jsp"%>
	</div>
</body>
</html>