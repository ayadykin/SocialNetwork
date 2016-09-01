$scope.searchProfile = function() {
    ProfileRest.search($scope.profile, function(profiles) {
	$log.debug('search profile');
	$scope.profiles = profiles;
    });
};