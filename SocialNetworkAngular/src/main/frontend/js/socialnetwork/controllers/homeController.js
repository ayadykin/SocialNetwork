angular.module('socialNetworkControllers').controller('HomeController',
	function($rootScope, $scope, $location, $log, $http, config) {
    
    $http.get(config.tokenPath, {
	    headers : headers,
	    data: {
		scope: 'server',
		grant_type: 'client_credentials'
	    }
	}).success(function(data) {
	    if (data.access_token) {
		$window.sessionStorage.setItem('authData', data.access_token);
		$rootScope.authenticated = true;
		$scope.name = data.name;
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
}