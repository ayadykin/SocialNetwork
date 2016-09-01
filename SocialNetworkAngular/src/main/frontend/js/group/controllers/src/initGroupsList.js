$scope.initGroupsList = function() {
    $scope.groupListView = true;
    $scope.createGroupView = false;
    $scope.editGroupView = false;

    // Sort group
    $scope.sortField = undefined;
    $scope.reverse = false;

    $scope.sort = function(fieldName) {
	if ($scope.sortField === fieldName) {
	    $scope.reverse = !$scope.reverse;
	} else {
	    $scope.sortField = fieldName;
	    $scope.reverse = false;
	}
    };
    $scope.isSortUp = function(fieldName) {
	return $scope.sortField === fieldName && !$scope.reverse;
    };
    $scope.isSortDown = function(fieldName) {
	return $scope.sortField === fieldName && $scope.reverse;
    };

    // Delete group
    $scope.deleteGroup = function(groupId) {
	$log.debug('remove groupId : ' + groupId);
	GroupRest.remove({
	    groupId : groupId
	}, function(data) {
	    if (data.groupId) {
		removeGroupById($scope.groups, groupId);
		resultDialog.dialog(true, 'success_delete_group');
	    }
	});
    };
    
    $scope.goToChat = function(chatId) {
	chatIdProperty.setChatId(chatId);
	$location.path('/chat');
    };

    $scope.nextPage = function() {
	$scope.pageNo++;
    };
    $scope.prevPage = function() {
	if ($scope.pageNo > 0) {
	    $scope.pageNo--;
	}
    };

};