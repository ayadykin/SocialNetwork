angular.module('socialNetworkServices').factory('GroupRest', function ($resource, config) {
        var url = config.groupPath;
		resource = $resource(url, null, {
            get: {
                method: 'GET',
                isArray: true
            },
            save: {
                method: 'POST'
            },
            getFriendsNotInGroup: {
                method: 'GET',
                isArray: true,
                url: config.friendsNotInGroup,
                params: {groupId: '@groupId'}
            },
            deleteUser: {
                method: 'PUT',
                url: config.deleteUserFromGroupPath,
                data: {groupId: '@groupId', userId: '@userId'}
            },
            leave: {
                method: 'DELETE',
                url: config.leaveGroupPath + '/:groupId',
                params: {groupId: '@groupId'}
            },
            remove: {
                method: 'DELETE',
                url: url + '/:groupId',
                params: {groupId: '@groupId'}
            },
        });

    return resource;
});