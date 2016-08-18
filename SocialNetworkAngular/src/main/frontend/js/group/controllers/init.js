angular.module('socialNetworkControllers').controller('GroupController',
	function($scope, $q, GroupRest, FriendRest, $log, $location) {

	    $q.all([ GroupRest.get().$promise ]).then(function(response) {
		$scope.groups = [];
		$scope.groups = response[0];
		$scope.initGroupsList();
	    });

	});