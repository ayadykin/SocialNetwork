function findChatById(chats, chatId) {
    var result = $.grep(chats, function(e) {
	return e.chatId == chatId;
    });
    if (result.length === 0) {
    } else if (result.length == 1) {
	return result[0];
    } else {
	alert("multiple items found");
    }
    return 0;
}