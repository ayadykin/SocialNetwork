angular.module('socialNetworkControllers').controller('FriendController', function($scope, $q, FriendRest, $log) {

    $q.all([ FriendRest.get().$promise ]).then(function(response) {
	$scope.friends = [];
	$scope.friends = response[0];
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