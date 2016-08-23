angular.module('socialNetworkControllers').controller('GroupController',
	function($scope, $rootScope, $q, GroupRest, FriendRest, resultDialog, $log, $location, ErrorHandler) {

	    $scope.pageNo = 0;
	    $scope.pageSize = 10;
	    $scope.groups = [];

	    GroupRest.query(function(groups) {
		$log.info('init groups list');
		$scope.groups = groups;
		$scope.initGroupsList();
		$scope.isLoad = true;
	    });
	});