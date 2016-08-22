angular.module('socialNetworkControllers').controller('GroupController',
	function($scope, $rootScope, $q, GroupRest, FriendRest, $log, $location) {

	    GroupRest.query(function(groups) {
		$log.info('init groups list');
		$scope.groups = groups;
		$scope.initGroupsList();
	    });
	});