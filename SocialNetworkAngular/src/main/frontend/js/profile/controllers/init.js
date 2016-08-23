angular.module('socialNetworkControllers').controller('ProfileController', function($scope, $log, ProfileRest) {

    $scope.profile = {};

    ProfileRest.get(function(profile) {
	$log.debug('init profile');
	$scope.profile = profile;
    });

    $scope.saveProfile = function() {
	$log.debug('save profile');
	$scope.profile.locales = [];
	ProfileRest.save($scope.profile);
    }
    
    function getLanguaches() {
	$http.get('../i18n/i18n_config.properties').success(function(data) {
	    $scope.languaches = data;
	}).error(function(data) {

	});
    }

});