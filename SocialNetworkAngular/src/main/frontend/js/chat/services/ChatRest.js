angular.module('socialNetworkServices').factory('ChatRest', function($resource, config) {
    var url = config.chatPath;
    resource = $resource(url, null, {
	get : {
	    method : 'GET',
	    url : url + '/:chatId',
	    isArray: true
	},
    });

    return resource;
});
