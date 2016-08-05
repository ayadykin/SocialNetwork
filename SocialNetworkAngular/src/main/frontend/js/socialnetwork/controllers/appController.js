angular.module('socialNetworkControllers').controller('AppController',
		function($scope, $rootScope, $route, config) {
			initPagesPermissions($route);
		});