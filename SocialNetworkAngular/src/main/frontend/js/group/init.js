angular.module('socialNetworkControllers').controller('GroupController',
	function($scope, $rootScope, $q, GroupRest, $log) {

	    $q.all([ GroupRest.get().$promise ]).then(function(response) {
		$scope.groups = [];
		$scope.groups = response[0];
	    });

	    $scope.deleteUserFromGroup = function(groupId, userId) {
		$log.info('delete user from groupId : ' + groupId);
		GroupRest.deleteUser({
		    groupId : groupId,
		    userId : userId
		}).$promise.then(function(data) {
		    if (data.response) {
			$scope.successDialog(data.response);
		    }
		});
	    }

	    $scope.leaveGroup = function(groupId) {
		$log.info('leave groupId : ' + groupId);
		GroupRest.leave({
		    groupId : groupId
		}).$promise.then(function(data) {
		    if (data.response) {
			$scope.successDialog(data.response);
		    }
		});
	    }

	    $scope.deleteGroup = function(groupId) {
		$log.info('remove groupId : ' + groupId);
		GroupRest.remove({
		    groupId : groupId
		}).$promise.then(function(data) {
		    if (data.response) {
			$scope.successDialog(data.response, $scope.messages.success_delete_group);
		    } else if (data.error != '') {
			$scope.successDialog(data.response, data.error);
		    } else {
			$scope.successDialog(data.response, $scope.messages.error_delete_group);
		    }
		});
	    }

	});