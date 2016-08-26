angular.module('socialNetworkControllers').controller('ChatController',
	function($scope, $rootScope, $q, ChatRest, $log, ErrorHandler) {

	    $scope.chats = [];
	    ChatRest.query(function(chats) {
		$log.info('init chat list : '+ $rootScope.userId);
		$scope.chats = chats;
	    }, function(data) {
		sendError(data);
	    });

	    function sendError(data) {
		ErrorHandler.notify($scope, data);
	    }
	});