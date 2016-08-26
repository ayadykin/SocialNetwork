angular.module('socialNetworkControllers').controller('FriendController', function($scope, $q, FriendRest, $log) {

    $scope.friends = [];

    FriendRest.get(function(friends) {
	$log.debug('init friends list');
	$scope.friends = friends;
    });

    $scope.deleteFriend = function(friendId) {
	$log.info('remove friendId : ' + friendId);
	FriendRest.remove({
	    friendId : friendId
	}).$promise.then(function(data) {
	    if (data.error) {
		alert(data.error);
	    }
	});
    };
});