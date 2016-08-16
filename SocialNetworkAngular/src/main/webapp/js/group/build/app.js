angular.module('socialNetworkControllers').controller('GroupController',
	function($scope, $rootScope, $q, GroupRest, $log) {

	    $q.all([ GroupRest.get().$promise ]).then(function(response) {
		$scope.groups = [];
		$scope.groups = response[0];
	    });

	    $scope.deleteUserFromGroup = function(groupId, userId) {
		$log.info('delete user from groupId : ' + groupId);
		GroupRest.deleteUser({
		    groupId : groupId,
		    userId : userId
		}).$promise.then(function(data) {
		    if (data.response) {
			$scope.successDialog(data.response);
		    }
		});
	    }

	    $scope.leaveGroup = function(groupId) {
		$log.info('leave groupId : ' + groupId);
		GroupRest.leave({
		    groupId : groupId
		}).$promise.then(function(data) {
		    if (data.response) {
			$scope.successDialog(data.response);
		    }
		});
	    }

	    $scope.deleteGroup = function(groupId) {
		$log.info('remove groupId : ' + groupId);
		GroupRest.remove({
		    groupId : groupId
		}).$promise.then(function(data) {
		    if (data.response) {
			$scope.successDialog(data.response, $scope.messages.success_delete_group);
		    } else if (data.error != '') {
			$scope.successDialog(data.response, data.error);
		    } else {
			$scope.successDialog(data.response, $scope.messages.error_delete_group);
		    }
		});
	    }

	});
angular.module('socialNetworkServices').factory('GroupRest', function ($resource, config) {
        var url = config.groupPath;
		resource = $resource(url, null, {
            get: {
                method: 'GET',
                isArray: true
            },
            save: {
                method: 'POST'
            },
            getFriendsNotInGroup: {
                method: 'GET',
                isArray: true,
                url: config.friendsNotInGroup,
                params: {groupId: '@groupId'}
            },
            deleteUser: {
                method: 'PUT',
                url: config.deleteUserFromGroupPath,
                data: {groupId: '@groupId', userId: '@userId'}
            },
            leave: {
                method: 'DELETE',
                url: config.leaveGroupPath + '/:groupId',
                params: {groupId: '@groupId'}
            },
            remove: {
                method: 'DELETE',
                url: url + '/:groupId',
                params: {groupId: '@groupId'}
            },
        });

    return resource;
});
angular.module('socialNetworkApp').run(function($templateCache) {
  'use strict';

  $templateCache.put('templates/pages/createGroup.html',
    "<div ng-controller=\"CreateGroupController\"><div class=\"panel panel-default\"><div class=\"panel-heading text-center\"><h2>Create group</h2></div><div class=\"panel-body\"><form class=\"form-horizontal\" role=\"form\"><div class=\"form-group\"><label class=\"col-sm-3 control-label\">Group Name</label><div class=\"col-sm-7\"><input type=\"text\" class=\"form-control\" ng-model=\"groupName\"></div></div><div class=\"form-group text-center\"><h2>Friends list</h2></div><div class=\"form-group\"><table class=\"table\"><thead><tr><th></th><th>Friend id</th><th>Friend name</th><th>Friend status</th></tr></thead><tr class=\"blank_row text-center\" ng-hide=\"friends\"><td colspan=\"3\">No friends</td></tr><tr id=\"friend_\" data-ng-repeat=\"friend in friends\"><td><input type=\"checkbox\" ng-model=\"friendsId[$index]\" ng-false-value=\"undefined\" ng-true-value=\"{{friend.friendId}}\"></td><td>{{friend.friendId}}</td><td>{{friend.name}}</td><td>{{friend.status}}</td></tr></table></div></form></div><div class=\"panel-footer\"><div class=\"col-lg-offset-5\"><a href=\"#/group\" class=\"btn btn-default\" role=\"button\">Back</a> <button type=\"button\" class=\"btn btn-default\" ng-click=\"createGroup()\">Save</button></div></div></div></div>"
  );


  $templateCache.put('templates/pages/group.html',
    "<div ng-controller=\"GroupController\"><div class=\"panel panel-default\"><div class=\"panel-heading text-center\"><h2>Groups list</h2></div><div class=\"panel-body\"><table class=\"table\"><thead><tr><th>Group id</th><th>Name</th><th>Hidden</th><th></th></tr></thead><tr id=\"friend_\" data-ng-repeat=\"group in groups\"><td>{{group.groupId}}</td><td>{{group.name}}</td><td>{{group.hidden}}</td><td ng-hide=\"group.hidden\"><a class=\"btn btn-info\" data-ng-if=\"group.groupAdmin\" ng-click=\"deleteGroup(group.groupId)\">Delete group</a> <a class=\"btn btn-info\" data-ng-if=\"!group.groupAdmin\" ng-click=\"leaveGroup(group.groupId)\">Leave group</a></td><td></td></tr></table></div><div class=\"panel-footer text-center\"><a class=\"btn btn-default\" href=\"#/createGroup\">CreateGroup</a></div></div></div>"
  );

});
angular.module('socialNetworkControllers').controller('CreateGroupController',
	function($scope, $rootScope, $q, GroupRest, FriendRest, $log) {

	    $q.all([ FriendRest.get().$promise ]).then(function(response) {
		$scope.friends = [];
		$scope.friends = response[0];
		$scope.friendsId = [];
	    });

	    $scope.createGroup = function() {
		$log.info('Create group name : ' + $scope.groupName + ', friendId : ' + $scope.friendsId);
		GroupRest.save({
		    groupName : $scope.groupName,
		    friendId : $scope.friendsId
		}).$promise.then(function(data) {
		    if (data.response) {
			$scope.successDialog(data.response);
		    }else if (data.error != '') {
			$scope.successDialog(data.response, data.error);
		    } else {
			$scope.successDialog(data.response, '');
		    }
		});
	    }
	    /*
	     * $q.all([ GroupRest.getFriendsNotInGroup({groupId :
	     * groupId}).$promise ]).then(function(response) {
	     * $scope.friendsNotInGroup = []; $scope.friendsNotInGroup =
	     * response[0]; });
	     */
	});