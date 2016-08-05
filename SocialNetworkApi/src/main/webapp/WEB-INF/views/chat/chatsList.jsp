<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Chats</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/chat.js"></script>
</head>
<body>
	<%@ include file="../fragments/header.jsp"%>
	<script type="text/javascript">
            var loggedUserId = <%=request.getAttribute("loggedUserId")%>;
            var chatId = 0;
    </script>
	<div class="container">
		<div class="row">
			<div class="col-lg-4 pull-left">
				<div class="panel panel-default panel-height">
					<div class="panel-heading text-center">
						<h2>Chat list</h2>
					</div>
					<div class="panel-body">
						<ul class="list-group" id="chatList">
							<c:forEach items="${chats_list}" var="chat">
								<li class="list-group-item" id="chat_${chat.chatId}"
									onclick="getChatMessages(${chat.chatId})"><span
									class="label label-default label-pill pull-right"
									id="chat_count_${chat.chatId}"></span>
									<div id="chat_name_${chat.chatId}">${chat.name}</div></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-lg-8 pull-right">
				<div class="panel panel-default">
					<div class="panel-heading">
						Messages
						<div class="pull-right">
							<select id="filter" name="filter">
								<option value="ALL">All</option>
								<option value="DAY">Day</option>
								<option value="WEEK">Week</option>
								<option value="MONTH">Month</option>
							</select>
						</div>
					</div>
					<!-- Messages body container -->
					<div class="panel-body tab-pane" id="messagesBody"></div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading text-center">Comment</div>
					<div class="">
						<div class="form-group">
							<textarea class="form-control" rows="5" id="messageValue"
								name="text" required></textarea>
						</div>
						<div class="form-group">
							<div class="col-lg-offset-10">
								<a class="btn btn-info" onclick="sendMessage()">Send</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../fragments/footer.jsp"%>
	</div>

	<!-- Message template -->
	<div class="row" id="messageRow" style="display: none">
		<div class="panel panel-margin" id="message">
			<div class="panel-heading">
				<div class="name-margin" id="ownerName"></div>
				<div class="pull-right remove-margin" id="deleteMessage"
					style="display: none">
					<h6>remove</h6>
				</div>
				<div class=" pull-right edit-margin" id="editMessage"
					style="display: none">
					<h6>edit</h6>
				</div>
			</div>
			<div class="panel-body">
				<div id="messageText"></div>
				<div id="invitation_message" style="display: none">
					<a class="btn btn-info" id="acceptInvitation">Accept</a> <a
						class="btn btn-info" id="declineInvitation">Decline</a>
				</div>
			</div>
			<div class="panel-footer" id="messageDate"></div>
		</div>
	</div>
	<!-- Deleted message template -->
	<div class="row" id="deletedMessageRow" style="display: none">
		<div class="panel panel-margin" id="message">
			<div class="panel-heading">
				<div class="name-margin" id="ownerName"></div>
			</div>
			<div class="panel-body">
				<div id="messageText">This message has been removed. <span class="glyphicon glyphicon-trash"></span></div>
			</div>
			<div class="panel-footer" id="messageDate"></div>
		</div>
	</div>
	<!-- Chat template -->
	<li class="list-group-item" id="chatRow" style="display: none"><span
		class="label label-default label-pill pull-right" id="chat_count"></span>
		<div id="chat_name"></div></li>
</body>
</html>