$scope.initCreateGroup = function() {
    $scope.createGroupView = true;
    $scope.groupListView = false;
    $scope.editGroupView = false;
    
    $scope.group = {};
    $scope.group.type = false;
    
    FriendRest.get(function(friends) {
	$log.info('initCreateGroup');
	$scope.friends = friends;
	$scope.friendsId = [];	
    });

    $scope.saveGroup = function() {
	$log.info('Create group name : ' + $scope.group.text + ', friendId : ' + $scope.friendsId);

	if ($scope.group.name) {
	    GroupRest.save({
		groupName : $scope.group.name,
		friendsId : $scope.friendsId,
		publicGroup : $scope.group.type
	    }, function(data) {
		if (data.groupId) {
		    $scope.groups.push(data);
		    resultDialog.dialog(true, "Success create group " + data.name);
		    $scope.initGroupsList();
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
