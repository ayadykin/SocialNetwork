<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/core.css" />" rel="stylesheet">
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript"
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/message.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/group.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/friend.js"></script>
<script>
	var contextPath = "${pageContext.request.contextPath}";
</script>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath}/">Social
					Network</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<sec:authorize access="isAuthenticated()">
						<script type="text/javascript">
							var token = '${_csrf.token}';
						</script>
						<li><a href="${pageContext.request.contextPath}/chat">Chats</a></li>
						<li><a href="${pageContext.request.contextPath}/friend">Friends</a></li>
						<li><a href="${pageContext.request.contextPath}/group">Groups</a></li>
						<li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
						<li><a
							href="${pageContext.request.contextPath}/profile/search">Search</a></li>
					</sec:authorize>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="locale?locale=en">English </a></li>
					<li><a href="locale?locale=ru">Russian </a></li>
					<sec:authorize access="!isAuthenticated()">
						<li><a href="signin">Sign in</a></li>
					</sec:authorize>
					<sec:authorize access="isAuthenticated()">
						<li><a href="${pageContext.request.contextPath}/#"
							onclick="document.getElementById('form').submit();">Logout</a>
							<form style="visibility: hidden" id="form" method="post"
								action="${pageContext.request.contextPath}/logout">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</form></li>
					</sec:authorize>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
</body>
</html>