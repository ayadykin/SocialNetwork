function findMessageById(messages, messageId) {
    var result = $.grep(messages, function(e) {
	return e.messageId == messageId;
    });
    if (result.length === 0) {
	return 0;
    } else if (result.length == 1) {
	return result[0];
    } else {
	alert("multiple items found");
    }
    
}