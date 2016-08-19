angular.module('socialNetworkControllers').controller('GroupController',
	function($scope, $rootScope, $q, GroupRest, FriendRest, $log, $location) {

	    $q.all([ GroupRest.get().$promise ]).then(function(response) {
		$log.info('init groups list');
		$scope.groups = [];
		$scope.groups = response[0];
		$scope.initGroupsList();
	    });

	});