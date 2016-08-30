$scope.deleteMessage = function(messageId) {

    $log.info('deleteMessage messageId :' + messageId);
    
    ChatRest.deleteMessage({
	messageId : messageId
    }, function(data) {
	if(data.response){
	    
	}	
    });
};