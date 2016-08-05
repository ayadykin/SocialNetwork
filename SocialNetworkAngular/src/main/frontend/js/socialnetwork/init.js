var models = {},
    backOfficeApp = angular.module('socialNetworkApp', ['socialNetworkControllers', 'socialNetworkServices','ngRoute', 'ngCookies']);


angular.module('socialNetworkControllers', []);
angular.module('socialNetworkServices', ['ngResource']);

backOfficeApp.run(function run( $http, $cookies ){
	  $http.defaults.headers.common['X-XSRF-TOKEN'] = $cookies.get('XSRF-TOKEN');
	});

backOfficeApp.config(function ($httpProvider) {

});