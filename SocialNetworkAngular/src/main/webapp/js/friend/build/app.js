angular.module('socialNetworkServices').factory('FriendRest', function ($resource, config) {
    var url = config.friendPath,
        resource = $resource(url, null, {
            get: {
                method: 'GET',
                cache: true,
                isArray: true
            },
            remove: {
                method: 'DELETE',
                url: url + '/:friendId',
                params: {friendId: '@friendId'}
            },
        });

    return resource;
});
angular.module('socialNetworkApp').run(function($templateCache) {
  'use strict';

  $templateCache.put('templates/pages/friend.html',
    "<div ng-controller=\"FriendController\"><div class=\"panel panel-default\"><div class=\"panel-heading text-center\"><h2>Friends list</h2></div><div class=\"panel-body\"><table class=\"table\"><thead><tr><th>Friend name</th><th>Status</th><th></th><th></th></tr></thead><tr id=\"friend_\" data-ng-repeat=\"friend in friends\"><td>{{friend.name}}</td><td>{{friend.status}}</td><td><a class=\"btn btn-info\" data-ng-if=\"friend.status == 'ACCEPTED'\" ng-click=\"deleteFriend(friend.chatId)\">Delete</a></td><td></td></tr></table></div></div></div>"
  );

});
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