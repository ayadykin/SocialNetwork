$scope.initEditGroup = function(groupId) {
    $scope.editGroupView = true;
    $scope.createGroupView = false;
    $scope.groupListView = false;

    $scope.group = [];
    $scope.group.users = [];
    
    $q.all([ GroupRest.getFriendsNotInGroup({
	groupId : groupId
    }).$promise, GroupRest.getGroupById({
	groupId : groupId
    }).$promise ]).then(function(response) {
	$log.info('initEditGroup');
	$scope.friendsNotInGroup = [];	
	$scope.friendsNotInGroup = response[0];
	$scope.group = response[1];
    });

    $scope.addUserToGroup = function(groupId, userId) {
	$log.info('add user to groupId : ' + groupId);
	GroupRest.addUser({
	    groupId : groupId,
	    userId : userId
	}, function(data) {
	    if (data.userId) {
		removeUserById($scope.friendsNotInGroup, userId);
		$scope.group.users.push(data);
		resultDialog.dialog(true);
	    }
	});
    };

    $scope.deleteUserFromGroup = function(groupId, userId) {
	$log.info('delete user from groupId : ' + groupId);
	GroupRest.deleteUser({
	    groupId : groupId,
	    userId : userId
	}, function(data) {
	    if (data.userId) {
		removeUserById($scope.group.users, userId);
		$scope.friendsNotInGroup.push(data);
		resultDialog.dialog(true);
	    }
	});
    };

    $scope.leaveGroup = function(groupId) {
	$log.info('leave groupId : ' + groupId);
	GroupRest.leave({
	    groupId : groupId
	}, function(data) {
	    if (data.response) {
		resultDialog.dialog(data.response);
	    }
	});
    };

    $scope.viewProfile = function(userId) {
	$scope.profileId = userId;
	$location.path("/profile");
    };
};