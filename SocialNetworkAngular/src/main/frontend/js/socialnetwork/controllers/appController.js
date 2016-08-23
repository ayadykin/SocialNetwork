angular.module('socialNetworkControllers').controller('AppController',
	function($q, $scope, $rootScope, $route, config, $log, $http, $locale, $translate) {

	    $scope.notificationOptions = {};
	    $scope.notificationOptions.show = true;

	    initRoutes($route);

	    loadFile('en');

	    $scope.changeLocale = function(locale) {
		loadFile(locale);
	    };

	    function loadFile(key) {
		$q.when($translate.use(key)).then(function(data) {
		    $log.info("AppController load lang :" + data);
		}, function(data) {
		    loadFile("en");
		    $log.error("AppController load lang :" + data);
		});
	    }
	});