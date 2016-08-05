angular.module('socialNetworkControllers').controller('FriendController',
		function($scope, $rootScope, $q, FriendRest) {

			$q.all([ FriendRest.get().$promise ]).then(function(response) {
				$scope.friends = [];
				$scope.friends = response[0];
			});

			$scope.deleteFriend = function(chatId) {
				console.log('remove friendId : '+ chatId);
				FriendRest.remove({friendId: chatId}).$promise.then(function(data) {
					if(data.error){
						alert(data.error);
					}
				});
				
			}

		});