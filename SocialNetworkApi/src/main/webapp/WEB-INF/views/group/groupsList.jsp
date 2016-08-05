<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Group list</title>
</head>
<body>
	<%@ include file="../fragments/header.jsp"%>
	<div class="container">
		<div class="col-lg-12">
			<div class="panel panel-default" id="groupList">
				<div class="panel-heading text-center">
					<h2>Groups list</h2>
				</div>
				<div class="panel-body">
					<table class="table">
						<thead>
							<tr>
								<th>Group id</th>
								<th>Name</th>
								<th>Hidden</th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<c:forEach items="${groups_list}" var="group">
							<tr id="group_${group.groupId}">
								<td><c:out value="${group.groupId}" /></td>
								<td><c:out value="${group.name}" /></td>
								<td id="group_hidden"><c:out value="${group.hidden}" /></td>
								<td><a href="chat" class="btn btn-info">Enter
										group chat</a></td>
								<c:choose>
									<c:when test="${group.groupAdmin}">
										<td><a href="group/edit/${group.groupId}"
											class="btn btn-info"> Edit group </a></td>
									</c:when>
									<c:otherwise>
										<td><a href="group/${group.groupId}" class="btn btn-info">
												View group </a></td>
									</c:otherwise>
								</c:choose>
								<td><c:choose>
										<c:when test="${group.groupAdmin}">
											<td>
												<button type="button"
													onclick="deleteGroup(${group.groupId})"
													class="btn btn-info">Delete Group</button>
											</td>
										</c:when>
										<c:otherwise>
											<td>
												<button type="button" onclick="leaveGroup(${group.groupId})"
													class="btn btn-info">Leave Group</button>
											</td>
										</c:otherwise>
									</c:choose>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="panel-heading text-center">
					<a href="${pageContext.request.contextPath}/group/creategroup"
						class="btn btn-default" onclick="showCreateGroup()">CreateGroup</a>
				</div>
			</div>
			<div class="panel panel-default text-center" id="createGroup"
				style="display: none">
				<form class="form-narrow form-horizontal" method="post"
					action="${pageContext.request.contextPath}/group/creategroup">
					<legend>Create Group</legend>
					<div class="form-group">
						<label class="col-lg-4 control-label">Group Name</label>
						<div class="col-lg-8">
							<input type="text" class="form-control" id="name"
								name="groupName" placeholder="Group Name" />
						</div>
					</div>
					<legend>Friends list</legend>
					<table class="table">
						<thead>
							<tr>
								<th></th>
								<th>Friend id</th>
								<th>Friend name</th>
							</tr>
						</thead>
						<c:forEach items="${friend_list}" var="friend">
							<tr>
								<c:if test="${friend.status eq 'ACCEPTED'}">
									<td><input type="checkbox" id="friend" name="friendsId"
										value="${friend.userId}" /></td>
									<td><c:out value="${friend.userId}" /></td>
									<td><c:out value="${friend.name}" /></td>
								</c:if>
							</tr>
						</c:forEach>
					</table>
					<div class="form-group">
						<div class="col-lg-offset-2 col-lg-10">
							<a href="${pageContext.request.contextPath}/group"
								class="btn btn-default" role="button">Back</a>
							<button type="submit" class="btn btn-default">Save</button>
						</div>
					</div>
			</div>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			</form>
		</div>
		<%@ include file="../fragments/footer.jsp"%>
	</div>
</body>
</html>