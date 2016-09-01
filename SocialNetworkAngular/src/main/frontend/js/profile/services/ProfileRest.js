angular.module('socialNetworkServices').factory('ProfileRest', function($resource, config) {
    var url = config.profilePath;
    resource = $resource(url, null, {
	get : {
	    method : 'GET',
	    url : url + '/:profileId'
	},
	search : {
	    method : 'POST',
	    url : url + '/search',
	    isArray : true
	}
    });

    return resource;
});
