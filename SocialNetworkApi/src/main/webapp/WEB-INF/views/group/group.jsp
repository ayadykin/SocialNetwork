<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>View Group</title>
</head>
<body>
	<%@ include file="../fragments/header.jsp"%>
	<div class="container">
		<div class="text-center">
			<h2>
				<c:out value="Group ${group.name} (id:${group.groupId})" />
			</h2>
			<table class="table">
				<thead>
					<tr>
						<th>User id</th>
						<th>User name</th>
						<th>Profile</th>
					</tr>
				</thead>
				<tr>
					<td>${group.admin.userId}(admin)</td>
					<td>${group.admin.firstName}${group.admin.lastName}</td>
					<td>
						<!--a href="${pageContext.request.contextPath}/profile/view/${group.admin.userId}"
                                class="btn btn-info">Profile
                            </a-->
					</td>
				</tr>
				<c:forEach items="${group.users}" var="user">
					<tr>
						<td><c:out value="${user.userId}" /></td>
						<td><c:out value="${user.firstName} ${user.lastName} " /></td>
						<td><a
							href="${pageContext.request.contextPath}/profile/view/${user.userId}"
							class="btn btn-info">Profile </a></td>
					<tr>
				</c:forEach>
			</table>
			<div class="container">
				<div class="text-center">
					<a href="${pageContext.request.contextPath}/chat/${group.chatId}"
						class="btn btn-default">Enter group chat</a>
					<button class="btn btn-default" onclick="history.back()">Back</button>
					<form action="leavegroup" method="post" />
					<input type="hidden" name="groupId" value="${group.groupId}" /> <input
						type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<button type="submit" class="btn btn-default">Leave Group</button>
					</form>
				</div>
			</div>
		</div>
		<%@ include file="../fragments/footer.jsp"%>
	</div>
</body>
</html>