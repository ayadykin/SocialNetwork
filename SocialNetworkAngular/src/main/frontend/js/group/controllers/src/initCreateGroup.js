$scope.initCreateGroup = function() {
    $scope.createGroupView = true;
    $scope.groupListView = false;
    $scope.editGroupView = false;

    FriendRest.get(function(friends) {
	$log.info('initCreateGroup');
	$scope.friends = friends;
	$scope.friendsId = [];
	$scope.groupName = {};
    });

    $scope.saveGroup = function() {
	$log.info('Create group name : ' + $scope.groupName.text + ', friendId : ' + $scope.friendsId);

	if ($scope.groupName.text) {
	    GroupRest.save({
		groupName : $scope.groupName.text,
		friendId : $scope.friendsId
	    }, function(data) {
		if (data.groupId) {
		    $scope.groups.push(data);
		    resultDialog.dialog(true);
		    $location.path("/group");
		} else if (data.error) {
		    resultDialog.dialog(false, data.error);
		} else {
		    resultDialog.dialog(data.response, '');
		}
	    });
	}else{
	    resultDialog.dialog(false, 'no name'); 
	}
    };
};
