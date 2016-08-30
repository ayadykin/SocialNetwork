angular.module('socialNetworkControllers').controller('FriendController',
	function($scope, $q, FriendRest, $log, $location, chatIdProperty, ErrorHandler) {

	    $scope.friends = [];

	    FriendRest.get(function(friends) {
		$log.debug('init friends list');
		$scope.friends = friends;
	    }, function(error) {
		sendError(error);
	    });

	    $scope.goToChat = function(chatId) {
		chatIdProperty.setChatId(chatId);
		$location.path('/chat');
	    };

	    $scope.deleteFriend = function(friendId) {
		$log.info('remove friendId : ' + friendId);
		FriendRest.remove({
		    friendId : friendId
		}).$promise.then(function(data) {
		    if (data.error) {
			alert(data.error);
		    }
		}, function(error) {
		    sendError(error);
		});
	    };
	    
	    function sendError(error) {
		ErrorHandler.notify($scope, error);
	    }
	});