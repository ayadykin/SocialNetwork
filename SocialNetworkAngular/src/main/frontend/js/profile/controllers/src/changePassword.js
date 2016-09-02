$scope.changePassword = function() {
    $scope.changePasswordView = true;
    $scope.editProfileView = false;

    $scope.savePassword = function() {
	ProfileRest.changepassword({
	    profileId : profileId
	}, function(profile) {

	});
    };
};