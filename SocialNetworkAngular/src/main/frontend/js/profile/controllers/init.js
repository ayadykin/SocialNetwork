angular.module('socialNetworkControllers').controller('ProfileController',
	function($scope, $rootScope, $log, ProfileRest, $routeParams, $http, $location, FriendActions) {

	    $scope.profile = {};
	    $scope.readonly = false;

	    var profileId = $routeParams.profileId;

	    // View profile
	    if (profileId) {
		ProfileRest.get({
		    profileId : profileId
		}, function(profile) {
		    $log.debug('ProfileController init profile by profileId : ' + profileId);
		    $scope.profile = profile;
		    $scope.readonly = true;
		});
	    }

	});