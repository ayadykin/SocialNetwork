angular.module('socialNetworkControllers').controller('AppController',
	function($q, $scope, $rootScope, $route, config, $log, $http, $locale, $translate, $timeout, $location) {

	    $rootScope.notification = {};
	    initRoutes($route);

	    $scope.changeLocale = function(key) {
		$q.when($translate.use(key)).then(function(data) {
		    $log.info("AppController load lang :" + data);
		}, function(error) {
		    $log.error("AppController load lang :" + data);
		    ServerErrorHandler(error);
		});
	    };

	    $scope.logout = function() {
		$log.info("AppController logout");

		$http.get(config.logoutPath, {}).success(function(data) {
		    $rootScope.authenticated = false;
		    $location.path("/signin");
		}).error(function(data) {
		    //$rootScope.authenticated = false;
		});
	    };

	    $scope.successMessageHandler = function(message) {
		$log.debug("AppController successMessageHandler message: " + message);
		if (!$rootScope.notification.error) {
		    $rootScope.notification.message = message;
		    $rootScope.notification.error = false;

		    $timeout(function() {
			$rootScope.notification.message = '';
			$rootScope.notification.error = '';
		    }, 5000);
		}
	    };

	    $scope.ServerErrorHandler = function(error) {
		$rootScope.notification.message = $translate.instant('service_Error') + error;
		$rootScope.notification.error = true;
		$log.error("ServerErrorHandler error :" + error);
	    };
	});