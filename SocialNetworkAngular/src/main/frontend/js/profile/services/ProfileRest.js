angular.module('socialNetworkServices').factory('ProfileRest', function($resource, config) {
    var url = config.profilePath;
    resource = $resource(url, null, {

    });

    return resource;
});
