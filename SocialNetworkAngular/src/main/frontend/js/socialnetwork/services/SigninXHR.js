angular.module('socialNetworkServices').factory('Signin', function ($resource, config) {
    var url = config.signinPath,
        resource = $resource(url, null, {
            post: {
                method: 'POST',
                cache: true,
                params : {j_username: '@j_username', j_password : '@j_password'}
            },
            get:{
            	url: config.signinInit,
            	method: 'GET'
            }
        });

    return resource;
});