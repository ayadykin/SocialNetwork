$scope.getChatMessages = function(chatId) {

    $log.debug('getChatMessages chatId : ' + chatId);

    $scope.chatId = chatId;

    ChatRest.getMessages({
	chatId : chatId
    }, function(messages) {
	$scope.messages = messages;
	findChatById($scope.chats, chatId).newMessages = '';
    }, function(error) {
	ServerErrorHandler(error);
    });

};