$scope.initCreateGroup = function() {
    $scope.createGroupView = true;
    $scope.groupListView = false;
    $scope.editGroupView = false;

    $q.all([ FriendRest.get().$promise ]).then(function(response) {
	$scope.friends = [];
	$scope.friends = response[0];
	$scope.friendsId = [];
    });

    $scope.saveGroup = function() {
	$log.info('Create group name : ' + $scope.groupName + ', friendId : ' + $scope.friendsId);

	GroupRest.save({
	    groupName : $scope.groupName,
	    friendId : $scope.friendsId
	}).$promise.then(function(data) {
	    if (data.groupId) {
		$scope.successDialog(true);
		$location.path("/group");
	    } else if (data.error != '') {
		$scope.successDialog(data.response, data.error);
	    } else {
		$scope.successDialog(data.response, '');
	    }
	});
    }
};
