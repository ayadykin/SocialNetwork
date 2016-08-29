angular.module('socialNetworkServices').factory('ChatRest', function($resource, config) {
    var url = config.chatPath;
    resource = $resource(url, null, {
	getChat : {
	    method : 'GET',
	    url : url + '/:chatId'
	},
	getMessages : {
	    method : 'GET',
	    url : url + '/getMessages/:chatId',
	    isArray: true
	},
	getMessage : {
	    method : 'GET',
	    url : url + '/getMessage/:chatId'
	},
	sendMessage :{
	    method : 'POST',
	    url : url + '/sendMessage'
	},
	deleteMessage:{
	    method : 'DELETE',
	    url : url + '/deleteMessage/:messageId'
	},
	editMessage:{
	    method : 'POST',
	    url : url + '/editMessage'
	}
    });

    return resource;
});
