var socialNetworkApp = angular.module('socialNetworkApp', [
	'pascalprecht.translate',	
	'ngLocale',
	'ngRoute',
	'ngCookies',
	'ui.bootstrap',
	'ngAnimate',
	//'socialNetworkAppM',
	'socialNetworkControllers',
	'socialNetworkServices',]);

angular.module('socialNetworkControllers', []);
angular.module('socialNetworkServices', [ 'ngResource' ]);

socialNetworkApp.run(function run($http, $cookies) {
    $http.defaults.headers.common['X-XSRF-TOKEN'] = $cookies.get('XSRF-TOKEN');
});

socialNetworkApp.config(function($translateProvider) {
    $translateProvider.useLoader('i18nLoader');
});