<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Friends</title>
</head>
<body>
	<%@ include file="../fragments/header.jsp"%>
	<div class="container">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading text-center">
					<h2>Friends list</h2>
				</div>
				<div class="panel-body">
					<table class="table">
						<thead>
							<tr>
								<th>Friend name</th>
								<th>Status</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<c:forEach items="${friends_list}" var="friend">
							<tr id="friend_${friend.friendId}">
								<td><c:out value="${friend.name}" /></td>
								<td><c:out value="${friend.status}" /></td>
								<td>
										<a class="btn btn-info"
											href="${pageContext.request.contextPath}/chat"
											role="button">Go to chat</a>
									</td>
								<td><c:if test="${friend.status == 'ACCEPTED'}">
										<a class="btn btn-info"
											onclick="deleteFriend('${_csrf.token}', ${friend.friendId})">Delete</a>
									</c:if></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
		<%@ include file="../fragments/footer.jsp"%>
	</div>
</body>
</html>