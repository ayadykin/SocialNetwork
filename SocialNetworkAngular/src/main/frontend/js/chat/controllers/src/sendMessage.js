$scope.sendMessage = function() {

    $log.debug('sendMessage text : ' + $scope.messageText);

    ChatRest.sendMessage({
	chatId : $scope.chatId,
	message : $scope.messageText,
	publicMessage : $scope.publicMessage
    }, function(data) {
	$scope.messageText = '';

	if (data.error) {
	    sendError(data);
	}
    }, function(error) {
	sendError(error);
    });

};