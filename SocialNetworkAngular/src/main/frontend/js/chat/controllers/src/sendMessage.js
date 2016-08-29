$scope.sendMessage = function() {

    $log.debug('sendMessage text : ' + $scope.messageText);

    ChatRest.sendMessage({
	chatId : $scope.chatId,
	message : $scope.messageText
    }, function(data) {
	$scope.messageText = '';

	if (data.error) {
	    sendError(data);
	}
    }, function(error) {
	sendError(error);
    });

};