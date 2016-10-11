angular.module('socialNetworkControllers').controller('SigninController',
	function($rootScope, $scope, $location, $log, $http, config) {

	    var authenticate = function(credentials) {

		var headers = credentials ? {
		    authorization : "Basic " + btoa(credentials.username + ":" + credentials.password)
		} : {};

		$http.get(config.signinPath, {
		    headers : headers
		}).success(function(data) {
		    if (data.email) {
			$rootScope.authenticated = true;
		    } else if (data.error) {
			$scope.error = true;
		    } else {
			$rootScope.authenticated = false;
		    }
		    callback();
		}).error(function() {
		    $rootScope.authenticated = false;
		    callback();
		});

	    };

	    var callback = function() {
		if ($rootScope.authenticated) {
		    $log.debug("callback  " + $rootScope.authenticated);
		    $location.path("/home");
		    $scope.error = false;
		}
	    };

	    if (!$rootScope.authenticated) {
		authenticate();
	    }
	    $scope.credentials = {};
	    $scope.login = function() {
		authenticate($scope.credentials);
	    };
	    /*
	     * 
	     * $scope.login = function() { $log.info("login ");
	     * Signin.get("/signin", {headers :
	     * headers}).$promise.then(function(data) {
	     * 
	     * if (data.userId) { $rootScope.authenticated = true;
	     * $rootScope.userId = data.userId; $rootScope.userLocale =
	     * data.userLocale; $scope.changeLocale(data.userLocale); }
	     * $log.info("login userId : " + data.userId); }); };
	     */
	});