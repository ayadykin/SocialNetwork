$scope.deleteMessage = function(messageId) {

    $log.debug('deleteMessage messageId :' + messageId);
    
    ChatRest.deleteMessage({
	messageId : messageId
    }, function(data) {
	if(data.response){
	    
	}	
    });
};