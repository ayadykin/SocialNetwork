$scope.getFilteredMessages = function() {

    $log.debug('getFilteredMessages dateFilter : ' + JSON.stringify($scope.dateFilter).substr(1,10));
    
    $scope.messages = [];
    ChatRest.getFilteredMessages({
	chatId : $scope.chatId,
	dateFilter : JSON.stringify($scope.dateFilter).substr(1,10)
    }, function(messages) {
	 $scope.messages = messages;
    });

};