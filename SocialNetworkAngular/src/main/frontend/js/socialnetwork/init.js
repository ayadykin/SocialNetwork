(function() {

    'use strict';
    
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
    angular.module('socialNetworkServices', [ 'ngResource' ]);
    angular.module('socialNetworkFilters', []);

    socialNetworkApp.config(function($translateProvider, $routeProvider, $httpProvider) {
	$translateProvider.useLoader('i18nLoader');

	$routeProvider.when('/profile/:profileId', {
	    templateUrl : 'pages/viewProfile.html'
	});

	$httpProvider.defaults.headers.common.Authorization = 'Bearer ' + window.sessionStorage.getItem('authData');
	$httpProvider.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
	$httpProvider.interceptors.push('restErrorInterceptor');
    });
}());