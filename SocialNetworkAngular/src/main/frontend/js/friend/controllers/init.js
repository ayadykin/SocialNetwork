angular.module('socialNetworkControllers').controller('FriendController',
	function($scope, FriendRest, $log, $location, chatIdProperty) {

	    $scope.friends = [];

	    FriendRest.get(function(friends) {
		$log.info('init friends list');
		$scope.friends = friends;
	    });

	    $scope.goToChat = function(chatId) {
		chatIdProperty.setChatId(chatId);
		$location.path('/chat');
	    };

	    $scope.deleteFriend = function(userId) {
		$log.info('remove userId : ' + userId);
		FriendRest.remove({
		    userId : userId
		}, function(data) {
		    
		});
	    };
	    
	});