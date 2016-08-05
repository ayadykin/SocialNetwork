angular.module('socialNetworkServices').factory('FriendRest', function ($resource, config) {
    var url = config.friendPath,
        resource = $resource(url, null, {
            get: {
                method: 'GET',
                cache: true,
                isArray: true
            },
            remove: {
                method: 'DELETE',
                url: url + '/:friendId',
                params: {friendId: '@friendId'}
            },
        });

    return resource;
});