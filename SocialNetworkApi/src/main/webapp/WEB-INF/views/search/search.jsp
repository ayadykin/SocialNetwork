<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Search</title>
</head>
<body>
	<%@ include file="../fragments/header.jsp"%>
	<div class="container">
		<form class="form-narrow form-horizontal" action="search"
			method="post">
			<fieldset>
				<legend>Search user</legend>
				<div class="form-group">
					<label class="col-lg-2 control-label">First name</label>
					<div class="col-lg-10">
						<input type="text" class="form-control" id="firstName"
							name="firstName" value="${profileDto.firstName}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label">Last name</label>
					<div class="col-lg-10">
						<input type="text" class="form-control" id="lastName"
							name="lastName" value="${profileDto.lastName}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label">City</label>
					<div class="col-lg-10">
						<input type="text" class="form-control" id="city" name="city"
							value="${profileDto.city}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label">Country</label>
					<div class="col-lg-10">
						<input type="text" class="form-control" id="country"
							name="country" value="${profileDto.country}" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-lg-offset-2 col-lg-10">
						<button type="submit" class="btn btn-default">Search</button>
					</div>
				</div>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</fieldset>
		</form>
		<div class="text-center">
			<h2>User list</h2>
			<table class="table">
				<thead>
					<tr>
						<th>User id</th>
						<th>First name</th>
						<th>Last name</th>
						<th>City</th>
						<th>Country</th>
					</tr>
				</thead>
				<c:forEach items="${profile_list}" var="profile">
					<tr>
						<td><c:out value="${profile.userId}" /></td>
						<td><c:out value="${profile.firstName}" /></td>
						<td><c:out value="${profile.lastName}" /></td>
						<td><c:out value="${profile.city}" /></td>
						<td><c:out value="${profile.country}" /></td>
						<c:choose>
							<c:when test="${profile.yourProfile}">
								<td>Your profile</td>
							</c:when>
							<c:when test="${profile.friendStatus != null}">
								<td>${profile.friendStatus}</td>
							</c:when>
							<c:otherwise>
								<td id="friendStatus_${profile.userId}">
									<button type="submit" class="btn btn-default"
										onclick="inviteFriend(${profile.userId})">Invite
										${profile.firstName}</button>
								</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</table>
		</div>
		<%@ include file="../fragments/footer.jsp"%>
	</div>
</body>
</html>