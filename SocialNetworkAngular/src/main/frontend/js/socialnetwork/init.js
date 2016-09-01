var socialNetworkApp = angular.module('socialNetworkApp', [
	'pascalprecht.translate',
	'ngLocale',
	'ngRoute',
	'ngCookies',
	'ui.bootstrap',
	'ngAnimate',
	'socialNetworkControllers',
	'socialNetworkServices',
	'socialNetworkFilters' ]);

angular.module('socialNetworkControllers', [ 'luegg.directives' ]);
angular.module('socialNetworkServices', ['ngResource']);
angular.module('socialNetworkFilters', []);

socialNetworkApp.config(function($translateProvider, $routeProvider, $httpProvider) {
    $translateProvider.useLoader('i18nLoader');

    $routeProvider.when('/profile/:profileId', {
	templateUrl : 'templates/pages/viewProfile.html'
    });

    $httpProvider.interceptors.push('restErrorInterceptor');

});
