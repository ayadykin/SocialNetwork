$scope.initGroupsList = function() {
    $scope.groupListView = true;
    $scope.createGroupView = false;
    $scope.editGroupView = false;

    $scope.deleteGroup = function(groupId) {
	$log.info('remove groupId : ' + groupId);
	GroupRest.remove({
	    groupId : groupId
	}).$promise.then(function(data) {
	    if (data.response) {
		findGroupById($scope.groups, groupId).hidden = true;

		$scope.successDialog(data.response, 'success_delete_group');
	    } else if (data.error != '') {
		$scope.successDialog(data.response, data.error);
	    } else {
		$scope.successDialog(data.response,'error_delete_group');
	    }
	});
    }
};