angular.module('socialNetworkControllers').controller('FriendController',
		function($scope, $q, FriendRest) {

			$q.all([ FriendRest.get().$promise ]).then(function(response) {
				$scope.friends = [];
				$scope.friends = response[0];
			});

			$scope.deleteFriend = function(friendId) {
				console.log('remove friendId : '+ friendId);
				FriendRest.remove({friendId: friendId}).$promise.then(function(data) {
					if(data.error){
						alert(data.error);
					}
				});
				
			}

		});