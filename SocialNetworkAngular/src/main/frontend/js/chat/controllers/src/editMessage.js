$scope.editMessage = function(messageId) {

    $log.debug('editMessage messageId :' + messageId);
    
    ChatRest.editMessage({
	messageId : messageId,
	message : $scope.messageText
    }, function(data) {
	if(data.response){
	    
	}	
    });
    
};