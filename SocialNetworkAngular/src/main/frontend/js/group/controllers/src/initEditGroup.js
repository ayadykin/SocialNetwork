$scope.initEditGroup = function(groupId) {
    $scope.editGroupView = true;
    $scope.createGroupView = false;
    $scope.groupListView = false;

    $q.all([ GroupRest.getFriendsNotInGroup({
	groupId : groupId
    }).$promise, GroupRest.getGroupById({
	groupId : groupId
    }).$promise ]).then(function(response) {
	$scope.friendsNotInGroup = [];
	$scope.group = [];
	$scope.friendsNotInGroup = response[0];
	$scope.group = response[1];
    });

    $scope.addUserToGroup = function(groupId, userId) {
	$log.info('add user to groupId : ' + groupId);
	GroupRest.addUser({
	    groupId : groupId,
	    userId : userId
	}).$promise.then(function(data) {
	    if (data.userId) {		
		removeUserById($scope.friendsNotInGroup, userId);
		$scope.group.users.push(data);
		$scope.successDialog(true);
	    }
	});
    };

    $scope.deleteUserFromGroup = function(groupId, userId) {
	$log.info('delete user from groupId : ' + groupId);
	GroupRest.deleteUser({
	    groupId : groupId,
	    userId : userId
	}).$promise.then(function(data) {
	    if (data.userId) {
		removeUserById($scope.group.users, userId);
		$scope.friendsNotInGroup.push(data);
		$scope.successDialog(true);
	    }
	});
    };

    $scope.leaveGroup = function(groupId) {
	$log.info('leave groupId : ' + groupId);
	GroupRest.leave({
	    groupId : groupId
	}).$promise.then(function(data) {
	    if (data.response) {
		$scope.successDialog(data.response);
	    }
	});
    };
};