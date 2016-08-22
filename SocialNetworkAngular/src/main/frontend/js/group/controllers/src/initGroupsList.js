$scope.initGroupsList = function() {
    $scope.groupListView = true;
    $scope.createGroupView = false;
    $scope.editGroupView = false;

    $scope.deleteGroup = function(groupId) {
	$log.info('remove groupId : ' + groupId);
	GroupRest.remove({
	    groupId : groupId
	}, function(data) {
	    if (data.groupId) {
		removeGroupById($scope.groups, groupId);
		$rootScope.successDialog(data.response, 'success_delete_group');
	    } else if (data.error) {
		$log.error('error remove group : ' + data.error);
		$rootScope.successDialog(data.response, data.error);
	    } else {
		$log.error('error remove groupId : ' + groupId);
		$rootScope.successDialog(data.response, 'error_delete_group');
	    }
	});
    };
};