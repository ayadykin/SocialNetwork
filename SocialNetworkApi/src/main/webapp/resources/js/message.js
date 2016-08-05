function registerDeleteMessage(messageId, messageChatId) {
	$("#deleteMessage_" + messageId).click(function() {
		deleteMessage(messageId, messageChatId);
	});
}
function registerEditMessage(messageId) {
	$("#editMessage_" + messageId).click(function() {
		editMessage(messageId);
	});
}

function registerChat(chatId) {
	$("#chat_" + chatId).click(function() {
		getChatMessages(chatId);
	});
}

function setActiveChat() {
	$("#chatList").each(function() {
		$("li").removeClass("active");
	});

	$("#chat_" + chatId).addClass("active");
}

function getChatMessages(chatId) {

	this.chatId = chatId;
	$("#messagesBody").text("");
	setActiveChat();

	$.ajax({
		type : "POST",
		url : "chat",
		data : {
			_csrf : token,
			chatId : chatId,
			filter : $("#filter").val()
		},
		success : function(messages) {
			$('#chat_' + chatId + ' #chat_count_' + chatId).text('');
			$.each(messages, function(i, message) {
				if (message.hidden) {
					createHiddenMessage(message);
				} else {
					createMessage(message);
				}
			});
		}
	});
}

function sendMessage() {
	if (chatId === 0) {
		alert("Chat id: " + chatId);
		return;
	}

	$.ajax({
		type : "POST",
		url : contextPath + "/chat/sendMessage",
		data : {
			_csrf : token,
			message : $('#messageValue').val(),
			chatId : chatId
		},
		success : function(result) {

		}
	});

}
function deleteMessage(messageId, messageChatId) {
	$.ajax({
		type : "POST",
		url : contextPath + "/chat/deleteMessage",
		data : {
			_csrf : token,
			messageId : messageId,
			messageChatId : messageChatId
		},
		success : function(result) {
			$("#message_" + messageId + " #messageText_" + messageId).html($("#deletedMessageRow #messageText").html());
			$("#deleteMessage_" + messageId).hide();
			$("#editMessage_" + messageId).hide();
		}
	});
}

function editMessage(messageId) {
	$.ajax({
		type : "POST",
		url : contextPath + "/chat/editMessage",
		data : {
			_csrf : token,
			messageId : messageId,
			message : $("#messageValue").val()
		},
		success : function(result) {
			$("#message_" + messageId + " #messageText_" + messageId).text(
					$("#messageValue").val());
		}
	});
}

function createMessage(message) {
	var messageId = message.messageId;
	var messageChatId = message.chatId;

	// Clone
	var $newMessage = $("#messageRow").clone();

	$newMessage.attr('id', 'messageRow_' + messageId);
	$newMessage.children("#message").attr('id', 'message_' + messageId);
	$newMessage.find("#ownerName").attr('id', 'ownerName_' + messageId);
	$newMessage.find("#ownerName_" + messageId).text(message.ownerName);
	$newMessage.find("#messageText").attr('id', 'messageText_' + messageId);
	$newMessage.find("#messageText_" + messageId).text(message.text);
	$newMessage.find("#messageDate").attr('id', 'messageDate_' + messageId);
	$newMessage.find("#messageDate_" + messageId).text(message.date);

	$newMessage.appendTo("#messagesBody").show();

	// Align and delete/edit actions
	if (message.ownerId === loggedUserId) {
		alignRight(messageId);
		if (message.messageInviteStatus == null) {
			$newMessage.find("#deleteMessage").attr('id',
					'deleteMessage_' + messageId);
			$newMessage.find("#editMessage").attr('id',
					'editMessage_' + messageId);
			$("#deleteMessage_" + messageId).show();
			$("#editMessage_" + messageId).show();
			registerDeleteMessage(messageId, messageChatId);
			registerEditMessage(messageId);
		}
	} else {
		alignLeft(messageId);
	}

	// Invitation message
	if (message.messageInviteStatus === 'INVITE' && message.ownerId !== loggedUserId) {
		$newMessage.find("#invitation_message").attr('id',
				'invitation_message_' + message.ownerId);
		$newMessage.find("#invitation_message_" + message.ownerId).show();
		$newMessage.find("#acceptInvitation").attr('id',
				'acceptInvitation_' + message.ownerId);
		$newMessage.find("#declineInvitation").attr('id',
				'declineInvitation_' + message.ownerId);

		registerAcceptInvitation(message.ownerId);
		registerDeclineInvitation(message.ownerId);
	}

	// Scroll bottom
	$("#messagesBody").animate({
		scrollTop : $(document).height()
	}, "slow");
}

function createHiddenMessage(message) {
	var messageId = message.messageId;

	var $newMessage = $("#deletedMessageRow").clone();

	$newMessage.attr('id', 'messageRow_' + messageId);
	$newMessage.children("#message").attr('id', 'message_' + messageId);
	$newMessage.find("#ownerName").attr('id', 'ownerName_' + messageId);
	$newMessage.find("#ownerName_" + messageId).text(message.ownerName);
	$newMessage.find("#messageDate").attr('id', 'messageDate_' + messageId);
	$newMessage.find("#messageDate_" + messageId).text(message.date);

	$newMessage.appendTo("#messagesBody").show();
	
	// Align message
	if (message.ownerId === loggedUserId) {
		alignRight(messageId);
	} else {
		alignLeft(messageId);
	}
}

function alignLeft(messageId) {
	$("#message_" + messageId).addClass("pull-left");
	$("#message_" + messageId).addClass("panel-success");
}

function alignRight(messageId) {
	$("#message_" + messageId).addClass("pull-right");
	$("#message_" + messageId).addClass("panel-info");
}
