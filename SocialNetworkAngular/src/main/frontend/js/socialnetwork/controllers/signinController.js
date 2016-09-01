angular.module('socialNetworkControllers').controller('SigninController',
	function($rootScope, $scope, $location, Signin, $log) {

	    Signin.get().$promise.then(function(data) {
		if (data.response) {
		    $scope.token = true;
		} else {
		    $scope.token = false;
		}
	    });

	    $scope.login = function() {
		$log.info("login ");
		Signin.post({
		    j_username : $scope.username,
		    j_password : $scope.password
		}).$promise.then(function(data) {

		    if (data.userId) {
			$rootScope.authenticated = true;
			$rootScope.userId = data.userId;
			$rootScope.userLocale = data.userLocale;
			$scope.changeLocale(data.userLocale);
			$location.path("/home");
			$scope.error = false;
		    } else {
			$rootScope.authenticated = false;
			$location.path("/signin");
			$scope.error = true;
		    }
		    $log.info("login userId : " + data.userId);
		});
	    };
	});