angular.module('socialNetworkControllers').controller('ChatController',
	function($scope, $rootScope, $q, ChatRest, $log, ErrorHandler, $timeout, $templateCache, $compile) {

	    $scope.chats = [];
	    $scope.chatId = 0;
	    $scope.messages = [];
	    var reload = true;

	    ChatRest.query(function(chats) {
		$log.info('init chat list : ' + chats);
		$scope.chats = chats;
		getRedisChatMessages();
	    }, function(error) {
		sendError(error);
	    });

	    function getRedisChatMessages() {
		$log.info('getRedisChatMessages : ' + $scope.chatId);
		ChatRest.getMessage({
		    chatId : $scope.chatId
		}, function(message) {
		    $log.info('getMessage : ');

		    if (message.chatId == $scope.chatId) {
			var currentMessage = findMessageById($scope.messages, message.messageId);
			if (message.hidden) {
			    currentMessage.hidden = true;
			    currentMessage.text = message.text;
			} else if (currentMessage === 0) {
			    $scope.messages.push(message);
			} else {
			    currentMessage.text = message.text;
			}

		    } else if (message.chatId) {
			$log.debug('new message without chat');
			var newChat = true;
			angular.forEach($scope.chats, function(value, key) {
			    if (value.chatId == message.chatId) {
				var count = parseInt($scope.chats[key].newMessages);
				$scope.chats[key].newMessages = isNaN(count) ? 1 : count++;
				newChat = false;
			    }
			});

			// New chat
			if (newChat) {
			    ChatRest.getChat({
				chatId : message.chatId
			    }, function(chat) {
				$scope.chats.push(chat);
			    }, function(error) {
				$log.error('newChat error : ' + error);
				sendError(error);
			    });
			}
		    }

		    // $timeout(function() {
		    if (reload) {
			getRedisChatMessages();
		    }
		    // }, 5000);

		}, function(error) {
		    $log.error('getMessage error : ' + error);
		    reload = false;
		    sendError(error);
		});
	    }

	    function sendError(error) {
		ErrorHandler.notify($scope, error);
	    }

	    $scope.$on("$destroy", function() {
		$log.debug('ChatController destroy');
		reload = false;
	    });
	});