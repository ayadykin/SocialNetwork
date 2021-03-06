angular.module('socialNetworkServices').factory('GroupRest', function ($resource, config) {
        var url = config.groupPath;
		resource = $resource(url, null, {
            getGroupById: {
                method: 'GET',
                url: url + '/:groupId'
            },        
            getFriendsNotInGroup: {
                method: 'GET',
                isArray: true,
                url: config.friendsNotInGroup + '/:groupId',
            },
            addUser: {
                method: 'POST',
                url: config.addUserToGroupPath,
            },
            deleteUser: {
                method: 'PUT',
                url: config.deleteUserFromGroupPath,
            },
            leave: {
                method: 'DELETE',
                url: config.leaveGroupPath + '/:groupId'
            },
            remove: {
                method: 'DELETE',
                url: url + '/:groupId'
            }
        });

    return resource;
});