$scope.editProfile = function() {
    $scope.editProfileView = true;
    $scope.changePasswordView = false;

    ProfileRest.get(function(profile) {
	$log.debug('ProfileController get profile');
	$scope.profile = profile;
	getLanguaches();

    });

    $scope.saveProfile = function() {
	$log.debug('ProfileController save profile');
	ProfileRest.save($scope.profile);
    };

    function getLanguaches() {
	$http.get('../i18n/i18n_config.properties').success(function(data) {
	    $log.debug(' profile getLanguaches ');
	    $scope.languaches = data;
	});
    }
};