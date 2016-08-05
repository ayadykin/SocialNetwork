<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Create Group</title>
</head>
<body>
	<%@ include file="../fragments/header.jsp"%>
	<div class="container">
		<div class="text-center">
			<form class="form-narrow form-horizontal" method="post"
				action="${pageContext.request.contextPath}/group/creategroup">
				<legend>Create Group</legend>
				<div class="form-group">
					<label class="col-lg-4 control-label">Group Name</label>
					<div class="col-lg-8">
						<input type="text" class="form-control" id="name" name="groupName"
							placeholder="Group Name" />
					</div>
				</div>
				<hlegend2>Friends list</legend>
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
		<%@ include file="../fragments/footer.jsp"%>
	</div>
</body>
</html>