$scope.searchProfile = function() {

    ProfileRest.search($scope.profile, function(profiles) {
	$log.info('ProfileController search profile');
	$scope.profiles = profiles;
    });

    $scope.inviteFriend = function(userId) {
	var response = FriendActions.inviteFriend(userId);
	
	if (response.userId) {
	    $scope.successMessageHandler("Success invite user : " + response.userId);
	}
    };

    $scope.acceptInvitation = function(userId) {
	var response = FriendActions.acceptInvitation(userId);
	
	if (response.userId) {
	    $scope.successMessageHandler("Success accept invitation user : " + response.userId);
	}
    };

    $scope.declineInvitation = function(userId) {
	var response = FriendActions.declineInvitation(userId);
	
	if (response.userId) {
	    $scope.successMessageHandler("Success decline invitation user : " + response.userId);
	}
    };
};