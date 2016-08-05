window.onload = function() {
	pollForMessages();
	reload = true;
	window.onbeforeunload = function() {
		reload = false;
		xhr.abort();
	};
};

var xhr;
var reload;
function pollForMessages() {

	xhr = $.ajax({
		url : "/SocialNetworkApi/chat/getMessage/" + chatId,
		type : "GET",
		data : {},
		cache : false,
		success : function(message) {
			// Add new chat
			if ($("#chat_" + message.chatId).length === 0) {
				addNewChat(message.chatId, message.ownerName);
			}

			if (chatId === message.chatId) {
				if ($("#messageRow_" + message.messageId).html() !== undefined) {
					
					if(message.text.length === 0){
						//Delete message
						$("#message_" + message.messageId + " #messageText_" + message.messageId).html($("#deletedMessageRow #messageText").html());
					}else{
						// Update message
						$("#message_" + message.messageId + " #messageText_" + message.messageId).text(
							message.text);
					}
				} else {
					// Create message
					createMessage(message);
				}
			} else {
				// Set counter
				var current = $("#chat_count_" + message.chatId).text();
				if (isNaN(parseInt(current))) {
					current = 1;
				} else {
					current = parseInt(current) + 1;
				}
			}
			$("#chat_count_" + message.chatId).text(current);
		},
		error : function(messages) {
			
		},
		complete : function() {
			if (reload == true) {
				pollForMessages();
			}
			;
		}
	});
}

function addNewChat(chat, ownerName) {

	var $newChat = $("#chatRow").clone();
	$newChat.attr('id', 'chat_' + chat);
	$newChat.find("#chat_name").attr('id', 'chat_name_' + chat);
	$newChat.find("#chat_name_" + chat).text(ownerName);
	$newChat.find("#chat_count").attr('id', 'chat_count_' + chat);
	$newChat.appendTo("#chatList").show();

	registerChat(chat);
}