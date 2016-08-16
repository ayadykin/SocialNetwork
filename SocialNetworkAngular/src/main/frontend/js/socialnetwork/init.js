var models = {}, socialNetworkApp = angular.module('socialNetworkApp', [
	'socialNetworkControllers',
	'socialNetworkServices',
	'ngLocale',
	'ngRoute',
	'ngCookies',
	'ui.bootstrap']);

angular.module('socialNetworkControllers', []);
angular.module('socialNetworkServices', [ 'ngResource' ]);

socialNetworkApp.run(function run($http, $cookies) {
    $http.defaults.headers.common['X-XSRF-TOKEN'] = $cookies.get('XSRF-TOKEN');
});