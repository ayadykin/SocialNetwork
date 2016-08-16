angular.module('socialNetworkControllers').controller('CreateGroupController',
	function($scope, $rootScope, $q, GroupRest, FriendRest, $log) {

	    $q.all([ FriendRest.get().$promise ]).then(function(response) {
		$scope.friends = [];
		$scope.friends = response[0];
		$scope.friendsId = [];
	    });

	    $scope.createGroup = function() {
		$log.info('Create group name : ' + $scope.groupName + ', friendId : ' + $scope.friendsId);
		GroupRest.save({
		    groupName : $scope.groupName,
		    friendId : $scope.friendsId
		}).$promise.then(function(data) {
		    if (data.response) {
			$scope.successDialog(data.response);
		    }else if (data.error != '') {
			$scope.successDialog(data.response, data.error);
		    } else {
			$scope.successDialog(data.response, '');
		    }
		});
	    }
	    /*
	     * $q.all([ GroupRest.getFriendsNotInGroup({groupId :
	     * groupId}).$promise ]).then(function(response) {
	     * $scope.friendsNotInGroup = []; $scope.friendsNotInGroup =
	     * response[0]; });
	     */
	});