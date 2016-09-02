angular.module('socialNetworkServices').factory('FriendActions', function($log, FriendRest) {

    return {
	inviteFriend : function(userId) {
	    $log.info('FriendActions inviteFriend');

	    var response = FriendRest.inviteFriend({
		userId : userId
	    }, function(data) {
		return data;
	    });

	    return response;
	},
	acceptInvitation : function(userId) {
	    $log.info('FriendActions acceptInvitation');

	    var response = FriendRest.acceptInvitation({
		userId : userId
	    }, function(data) {
		return data;
	    });

	    return response;
	},
	declineInvitation : function(userId) {
	    $log.info('FriendActions declineInvitation');

	    var response = FriendRest.declineInvitation({
		userId : userId
	    }, function(data) {
		return data;
	    });

	    return response;
	}
    };
});