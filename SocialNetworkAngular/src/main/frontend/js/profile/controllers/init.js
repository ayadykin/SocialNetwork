angular.module('socialNetworkControllers').controller('ProfileController',
	function($scope, $rootScope, $log, ProfileRest, $routeParams, $http, $location, FriendRest) {

	    $scope.profile = {};
	    $scope.readonly = false;

	    var profileId = $routeParams.profileId;
	    $scope.routeParams = $routeParams;
	    
	    if (profileId) {
		ProfileRest.get({
		    profileId : profileId
		}, function(profile) {
		    $log.debug('init profile by profileId : ' + profileId);
		    $scope.profile = profile;
		    $scope.readonly = true;
		});
	    } else if($location.path() === '/profile'){
		getLanguaches();
		ProfileRest.get(function(profile) {
		    $log.debug('init profile');
		    $scope.profile = profile;

		});
	    }

	    $scope.saveProfile = function() {
		$log.debug('save profile');
		ProfileRest.save($scope.profile);
	    };	  

	    function getLanguaches() {
		$http.get('../i18n/i18n_config.properties').success(function(data) {
		    $log.debug(' profile getLanguaches ');
		    $scope.languaches = data;
		});
	    }

	});