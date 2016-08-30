angular.module('socialNetworkServices').service('chatIdProperty', function() {
    var chatId = 0;

    return {
	getChatId : function() {
	    return chatId;
	},
	setChatId : function(value) {
	    chatId = value;
	}
    };
});