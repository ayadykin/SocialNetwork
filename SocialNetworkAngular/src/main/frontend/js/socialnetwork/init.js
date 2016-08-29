var socialNetworkApp = angular.module('socialNetworkApp', [
	'pascalprecht.translate',
	'ngLocale',
	'ngRoute',
	'ngCookies',
	'ui.bootstrap',
	'ngAnimate',
	//'socialNetworMockServerkApp',
	'socialNetworkControllers',
	'socialNetworkServices',
	'socialNetworkFilters',
	'templates']);

angular.module('socialNetworkControllers', ['luegg.directives']);
angular.module('socialNetworkServices', [ 'ngResource' ]);
angular.module('socialNetworkFilters', []);

socialNetworkApp.run(function run($http, $cookies) {
    $http.defaults.headers.common['X-XSRF-TOKEN'] = $cookies.get('XSRF-TOKEN');
});

socialNetworkApp.config(function($translateProvider, $routeProvider) {
    $translateProvider.useLoader('i18nLoader');
    
    $routeProvider.when('/profile/:profileId', {
	templateUrl : 'templates/pages/profile.html'
    });
});