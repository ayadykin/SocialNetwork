$scope.initEditGroup = function(groupId) {
    $scope.editGroupView = true;
    $scope.createGroupView = false;
    $scope.groupListView = false;

    $scope.group = [];
    $scope.group.users = [];

    $q.all([ FriendRest.get().$promise, GroupRest.getGroupById({
	groupId : groupId
    }).$promise ]).then(function(response) {
	$log.info('initEditGroup');
	$scope.friends = [];
	$scope.friends = response[0];
	$scope.group = response[1];

	$scope.friendsNotInGroup = [];

	createUserFromFriend($scope.friends, $scope.group.users);
    });

    function createUserFromFriend(friends, groupUsers) {
	angular.forEach(friends, function(value1, key1) {
	    $log.debug(' createUserFromFriend id ' + value1.userId + '-' + value1.status);
	    angular.forEach(groupUsers, function(value2, key2) {
		if (value1.userId === value2.userId || value1.status !== 'ACCEPTED') {
		    friends.splice(key1, 1);
		}
	    });
	});
	$scope.friendsNotInGroup = friends;
    }

    $scope.addUserToGroup = function(groupId, userId) {
	$log.info('add user to groupId : ' + groupId);
	GroupRest.addUser({
	    groupId : groupId,
	    userId : userId
	}, function(data) {
	    if (data.userId) {
		removeUserById($scope.friendsNotInGroup, userId);
		$scope.group.users.push(data);
		resultDialog.dialog(true, "Success add user with userId : " + userId);
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