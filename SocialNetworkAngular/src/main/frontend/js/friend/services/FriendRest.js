angular.module('socialNetworkServices').factory('FriendRest', function ($resource, config) {
    var url = config.friendPath,
        resource = $resource(url, null, {
            get: {
                method: 'GET',
                cache: true,
                isArray: true
            },
            inviteFriend :{
        	method: 'POST',
        	url: url + '/inviteFriend/:userId',
                params: {userId: '@userId'}
            },
            acceptInvitation :{
        	method: 'POST',
        	url: url + '/acceptInvitation/:userId',
                params: {userId: '@userId'}
            },
            declineInvitation :{
        	method: 'POST',
        	url: url + '/declineInvitation/:userId',
                params: {userId: '@userId'}
            },
            remove: {
                method: 'DELETE',
                url: url + '/:userId',
                params: {userId: '@userId'}
            }
        });

    return resource;
});