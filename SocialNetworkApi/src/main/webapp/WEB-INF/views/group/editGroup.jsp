<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Edit Group</title>
</head>
<body>
	<%@ include file="../fragments/header.jsp"%>
	<div class="container">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading text-center">
					<h2>
						<c:out value="Group ${group.name} (id:${group.groupId})" />
					</h2>
				</div>
				<div class="panel-body">
					<table class="table">
						<thead>
							<tr>
								<th>User id</th>
								<th>User name</th>
								<th>Profile</th>
								<th></th>
							</tr>
						</thead>
						<c:forEach items="${group.users}" var="user">
							<tr id="groupUser_${user.userId}">
								<td><c:out value="${user.userId}" /></td>
								<td><c:out value="${user.fullName}" /></td>
								<td><a
									href="${pageContext.request.contextPath}/profile/view/${user.userId}"
									class="btn btn-info">Profile </a></td>
								<td>
								<c:if test="${not user.groupAdmin}">
									<button type="button"
										onclick="deleteUserFromGroup(${group.groupId}, ${user.userId})"
										class="btn btn-info">Delete User</button>
										</c:if>
								</td>
							<tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<br />
			<div class="panel panel-default">
				<div class="panel-heading text-center">
					<h2>List of friends</h2>
				</div>
				<div class="panel-body">
					<table class="table">
						<thead>
							<tr>
								<th>Friend id</th>
								<th>Friend name</th>
								<th>Profile</th>
							</tr>
						</thead>
						<c:forEach items="${friends}" var="friend">
							<tr id="groupFriend_${friend.userId}">
								<td><c:out value="${friend.userId}" /></td>
								<td><c:out value="${friend.name}" /></td>
								<td><a
									href="${pageContext.request.contextPath}/profile/view/${friend.userId}"
									class="btn btn-info">Profile</a></td>
								<td>
									<button type="button" class="btn btn-info"
										onclick="addUserToGroup(${group.groupId}, ${friend.userId})">Add
										user</button>

								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>

		</div>
		<div class="col-lg-12">
			<div class="text-center">
				<a href="${pageContext.request.contextPath}/chat/${group.chatId}"
					class="btn btn-default">Enter group chat</a>
				<button class="btn btn-default" onclick="history.back()">Back</button>
			</div>
		</div>
		<%@ include file="../fragments/footer.jsp"%>
	</div>
</body>
</html>
