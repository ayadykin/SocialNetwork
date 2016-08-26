$scope.getChatMessages = function(chatId) {

    $log.debug('getChatMessages chatId : ' + chatId);

    ChatRest.get({
	chatId : chatId
    }, function(messages) {
	$scope.messages = messages;
    });
};