angular.module('socialNetworkControllers').controller(
	'ChatController',
	function($scope, $rootScope, $q, ChatRest, $log, $templateCache, $compile,
		chatIdProperty) {

	    $scope.chats = [];
	    $scope.messages = [];
	    var reload = true;
	    $scope.chatId = chatIdProperty.getChatId();

	    // Init date picker
	    initDatePicker($scope);

	    // Load messages from DB and start loop
	    ChatRest.query(function(chats) {
		$log.debug('init chat list : ' + chats);
		$scope.chats = chats;
		if ($scope.chatId !== 0) {
		    getChatMessages($scope.chatId);
		}

		getRedisChatMessages();
	    });

	    function getRedisChatMessages() {
		$log.debug('getRedisChatMessages : ');
		ChatRest.getMessage(function(message) {
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
				$scope.chats[key].newMessages = isNaN(count) ? 1 : ++count;
				newChat = false;
			    }
			});

			// New chat
			if (newChat) {
			    ChatRest.getChat({
				chatId : message.chatId
			    }, function(chat) {
				$scope.chats.push(chat);
			    });
			}
		    }
		    if (reload) {
			getRedisChatMessages();
		    }

		}, function(error) {
		    $log.error('getMessage error : ' + error);
		    reload = false;
		});
	    }

	    $scope.$on("$destroy", function() {
		$log.debug('ChatController destroy');
		reload = false;
	    });

	});