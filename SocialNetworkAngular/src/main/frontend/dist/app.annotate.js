(function() {

    'use strict';
    
    var socialNetworkApp = angular.module('socialNetworkApp', [
	    'pascalprecht.translate',
	    'ngLocale',
	    'ngRoute',
	    'ngCookies',
	    'ui.bootstrap',
	    'ngAnimate',
	    'socialNetworkControllers',
	    'socialNetworkServices',
	    'socialNetworkFilters' ]);

    angular.module('socialNetworkControllers', [ 'luegg.directives' ]);
    angular.module('socialNetworkServices', [ 'ngResource' ]);
    angular.module('socialNetworkFilters', []);

    socialNetworkApp.config(["$translateProvider", "$routeProvider", "$httpProvider", function($translateProvider, $routeProvider, $httpProvider) {
	$translateProvider.useLoader('i18nLoader');

	$routeProvider.when('/profile/:profileId', {
	    templateUrl : 'pages/viewProfile.html'
	});

	$httpProvider.interceptors.push('restErrorInterceptor');
    }]);
}());
angular.module('socialNetworkServices').service('chatIdProperty', function() {
    var chatId = 0;

    return {
	getChatId : function() {
	    return chatId;
	},
	setChatId : function(value) {
	    chatId = value;
	}
    };
});
angular.module('socialNetworkServices').factory('i18nLoader',
	["$q", "$http", "$translate", "$cacheFactory", "$log", "config", function($q, $http, $translate, $cacheFactory, $log, config) {

	    var cacheName = config.i18nCacheKey, cache = $cacheFactory.get(cacheName);
	    
	    $log.debug("i18nLoader start");
	    
	    if (!cache) {
		cache = $cacheFactory(cacheName);
	    }

	    return function(options) {
		options = options || {};

		$log.debug("i18nLoader options key : " + options.key + ", cacheName : " + cacheName);

		var deferred = $q.defer(), key = options.key, data = cache.get(key);

		$log.debug("i18nLoader data : " + data);

		if (!key) {
		    key = 'en';
		}

		if (data) {
		    $log.debug("i18nLoader load from cache");
		    complete(data, deferred, key);
		} else {
		    $http.get('../i18n/' + (key + '.properties' || '')).success(function(data) {
			complete(data, deferred, key);
		    }).error(function(data) {
			deferred.reject(data.error);
		    });
		}

		return deferred.promise;
	    };

	    function complete(data, deferred, key) {
		cache.put(key, data);
		deferred.resolve(data);
	    }
	}]);
angular.module('socialNetworkServices').factory('restErrorInterceptor',
	["$translate", "$log", "$rootScope", "$timeout", "$location", function($translate, $log, $rootScope, $timeout, $location) {
	    return {

		'response' : function(response) {
		    if (response.data.error) {
			$log.error("restErrorInterceptor : " + response.data.error);
			$rootScope.notification.message = response.data.error;
			$rootScope.notification.error = true;

			$timeout(function() {
			    $rootScope.notification.message = '';
			    $rootScope.notification.error = '';
			    if (response.data.status === 401) {
				$log.error("restErrorInterceptor response status : " + response.data.status);
				$location.path("/signin");
			    }
			}, 5000);
			return {};
		    }
		    return response;
		},
		'responseError' : function(error) {
		    $log.error("responseServerError  : " + angular.toJson(error));

		    $rootScope.notification.message = "Server error";
		    $rootScope.notification.error = true;
		    return {};

		}
	    };
	}]);
angular.module('socialNetworkServices').factory('resultDialog', ["$translate", "$uibModal", "$log", "config", function($translate, $uibModal, $log, config) {

    return {
	dialog : function(result, key) {
	    var templateUrl, message = $translate.instant(key);
	    if (result) {
		templateUrl = 'parts/success_dialog.html';
	    } else {
		templateUrl = 'parts/error_dialog.html';
	    }

	    var uibModalInstance = $uibModal.open({
		animation : true,
		templateUrl : templateUrl,
		size : 'sm',
		controller : 'SuccessDialogController',
		resolve : {
		    message : function() {
			return message;
		    }
		}
	    });
	    $log.info('Modal open,  message: ' + message);
	}
    };
}]);
angular.module('socialNetworkApp').constant('config', {
    basePath: "/SocialNetworkApi/",
    
    friendPath: "/SocialNetworkApi/friend",
    
    groupPath: "/SocialNetworkApi/group",
    deleteUserFromGroupPath: "/SocialNetworkApi/group/delete_user",
    addUserToGroupPath: "/SocialNetworkApi/group/add_user",
    leaveGroupPath: "/SocialNetworkApi/group/leave_group",
    
    chatPath: "/SocialNetworkApi/chat",
    
    signinPath: "/SocialNetworkApi/signin",
    logoutPath: "/SocialNetworkApi/logout",
    
    profilePath: "/SocialNetworkApi/profile",
    
    i18nCacheKey: "i18nCache"
});
function initRoutes($route) {
    var k, defaultRoutes = {
	'/friend' : {
	    templateUrl : 'pages/friend.html',
	},
	'/group' : {
	    templateUrl : 'pages/group.html'
	},
	'/chat' : {
	    templateUrl : 'pages/chat.html'
	},
	'/profile' : {
	    templateUrl : 'pages/editProfile.html'
	},
	'/search' : {
	    templateUrl : 'pages/searchProfile.html'
	},
	'/signin' : {
	    templateUrl : 'pages/signin.html'
	},
	'/signup' : {
	    templateUrl : 'pages/signup.html'
	},
	'' : {
	    templateUrl : 'pages/index.html'
	},
	'/home' : {
	    templateUrl : 'pages/homeSignedIn.html'
	}

    }, defaultUrl;

    for (k in defaultRoutes) {
	$route.routes[k] = angular.extend({
	    reloadOnSearch : true,
	    regexp : new RegExp('^' + k + '$')
	}, defaultRoutes[k]);

    }
}

function findUserById(users, userId) {
    var result = $.grep(users, function(e) {
	return e.userId == userId;
    });
    if (result.length === 0) {
	alert("User not found");
    } else if (result.length == 1) {
	return result[0];
    } else {
	alert("multiple items found");
    }
    return 0;
}

function removeUserById(users, userId) {
    for (var i = 0; i < users.length; i++)
	if (users[i].userId == userId) {
	    users.splice(i, 1);
	    break;
	}
}
angular.module('socialNetworkFilters', []).filter('pagination',["$log", function($log) {
	    return function(inputArray, selectedPage, pageSize) {
		$log.debug('socialNetworkFilters pagination : selectedPage ' + selectedPage + ', pageSize ' + pageSize);
		var start = selectedPage * pageSize;
		return inputArray.slice(start, start + pageSize);
	    };
	}]);
angular.module('socialNetworkControllers').directive('headerBlock', function () {
    return {
        templateUrl: "parts/header.html"
    };
});
angular.module('socialNetworkApp').run(['$templateCache', function($templateCache) {$templateCache.put('pages/homeSignedIn.html','<div class="container">\r\n\t<div class="text-center">\r\n\t\t<h2>Welcome to Social Network</h2>\r\n\t</div>\r\n</div>\r\n');
$templateCache.put('pages/index.html','<div class="container">\r\n\t<div class="text-center">\r\n\t\t<h1>Social Network</h1>\r\n\t\t<p class="lead">Welcome Get started quickly by signing up.</p>\r\n\t\t<p>\r\n\t\t\t<a href="#/signup" class="btn btn-success btn-lg">Sign up</a>\r\n\t\t</p>\r\n\t</div>\r\n\r\n</div>');
$templateCache.put('pages/signin.html','<div class="col-lg-6 col-lg-offset-3" ng-controller="SigninController">\r\n\t<div class="alert alert-danger" ng-show="error">There was a\r\n\t\tproblem logging in. Please try again.</div>\r\n\t<form role="form" ng-submit="login()">\r\n\t\t<div class="form-group">\r\n\t\t\t<label for="username">Username:</label> <input type="text"\r\n\t\t\t\tclass="form-control" data-ng-model="credentials.username" />\r\n\t\t</div>\r\n\t\t<div class="form-group">\r\n\t\t\t<label for="password">Password:</label> <input type="password"\r\n\t\t\t\tclass="form-control" data-ng-model="credentials.password" />\r\n\t\t</div>\r\n\t\t<button type="submit" class="btn btn-primary">Submit</button>\r\n\t</form>\r\n</div>');
$templateCache.put('parts/error_dialog.html','<!-- Success dialog -->\r\n\r\n<div>\r\n\t<div class="modal-header alert-danger">\r\n\t\t<h3 class="modal-title">Error !</h3>\r\n\t</div>\r\n\t<div class="modal-body">\r\n\t\t<b>{{message}}</b>\r\n\t</div>\r\n\t<div class="modal-footer">\r\n\t\t<button class="btn btn-primary" type="button" ng-click="ok()">OK</button>\r\n\t</div>\r\n</div>\r\n');
$templateCache.put('parts/header.html','\r\n<div class="container">\r\n\t<div class="navbar-header">\r\n\t\t<button type="button" class="navbar-toggle" data-toggle="collapse"\r\n\t\t\tdata-target=".nav-collapse">\r\n\t\t\t<span class="icon-bar"></span> <span class="icon-bar"></span> <span\r\n\t\t\t\tclass="icon-bar"></span>\r\n\t\t</button>\r\n\t\t<a class="navbar-brand" href="#">Social Network</a>\r\n\t</div>\r\n\t<div class="navbar-collapse collapse" data-ng-cloak>\r\n\t\t<ul class="nav navbar-nav" data-ng-show="authenticated">\r\n\t\t\t<li><a href="#/chat">Chats</a></li>\r\n\t\t\t<li><a href="#/friend">{{\'FRIENDS\' | translate}}</a></li>\r\n\t\t\t<li><a href="#/group">Groups</a></li>\r\n\t\t\t<li><a href="#/profile">Profile</a></li>\r\n\t\t\t<li><a href="#/search">Search</a></li>\r\n\t\t</ul>\r\n\t\t<ul class="nav navbar-nav navbar-right">\r\n\t\t\t<li><a href="#" data-ng-click="changeLocale(\'en\')">English </a></li>\r\n\t\t\t<li><a href="#" data-ng-click="changeLocale(\'ru\')">Russian </a></li>\r\n\r\n\t\t\t<li data-ng-show="!authenticated"><a href="#/signin">Sign in</a></li>\r\n\t\t\t<li data-ng-show="authenticated"><a href="#" data-ng-click="logout()">Logout</a></li>\r\n\r\n\t\t</ul>\r\n\t</div>\r\n\r\n\t<!-- Error message block -->\r\n\t<div class="alert-message-at-bottom error" data-ng-class="{true: \'error\', false: \'msg\'}[notification.error]" \r\n\t\tdata-ng-show="notification.message" >\r\n\t\t<span>{{notification.message}}</span>\r\n\t</div>\r\n</div>\r\n');
$templateCache.put('parts/success_dialog.html','<!-- Success dialog -->\r\n\r\n<div>\r\n\t<div class="modal-header alert-success">\r\n\t\t<h3 class="modal-title">Success !</h3>\r\n\t</div>\r\n\t<div class="modal-body">\r\n\t\t<b>{{message}}</b>\r\n\t</div>\r\n\t<div class="modal-footer">\r\n\t\t<button class="btn btn-primary" type="button" ng-click="ok()">OK</button>\r\n\t</div>\r\n</div>\r\n');}]);
angular.module('socialNetworkControllers').controller('AppController',
	["$q", "$scope", "$rootScope", "$route", "config", "$log", "$http", "$locale", "$translate", "$timeout", "$location", function($q, $scope, $rootScope, $route, config, $log, $http, $locale, $translate, $timeout, $location) {

	    $rootScope.notification = {};
	    initRoutes($route);

	    $scope.changeLocale = function(key) {
		$q.when($translate.use(key)).then(function(data) {
		    $log.info("AppController load lang :" + data);
		}, function(error) {
		    $log.error("AppController load lang :" + data);
		    ServerErrorHandler(error);
		});
	    };

	    $scope.logout = function() {
		$log.info("AppController logout");

		$http.get(config.logoutPath, {}).success(function(data) {
		    $rootScope.authenticated = false;
		    $location.path("/signin");
		}).error(function(data) {
		    //$rootScope.authenticated = false;
		});
	    };

	    $scope.successMessageHandler = function(message) {
		$log.debug("AppController successMessageHandler message: " + message);
		if (!$rootScope.notification.error) {
		    $rootScope.notification.message = message;
		    $rootScope.notification.error = false;

		    $timeout(function() {
			$rootScope.notification.message = '';
			$rootScope.notification.error = '';
		    }, 5000);
		}
	    };

	    $scope.ServerErrorHandler = function(error) {
		$rootScope.notification.message = $translate.instant('service_Error') + error;
		$rootScope.notification.error = true;
		$log.error("ServerErrorHandler error :" + error);
	    };
	}]);
angular.module('socialNetworkControllers').controller('SigninController',
	["$rootScope", "$scope", "$location", "$log", "$http", "config", function($rootScope, $scope, $location, $log, $http, config) {

	    var authenticate = function(credentials) {

		var headers = credentials ? {
		    authorization : "Basic " + btoa(credentials.username + ":" + credentials.password)
		} : {};

		$http.get(config.signinPath, {
		    headers : headers
		}).success(function(data) {
		    if (data.email) {
			$rootScope.authenticated = true;
		    } else if (data.error) {
			$scope.error = true;
		    } else {
			$rootScope.authenticated = true;
		    }
		    callback();
		}).error(function() {
		    $rootScope.authenticated = true;
		    callback();
		});

	    };

	    var callback = function() {
		if ($rootScope.authenticated) {
		    $log.debug("callback  " + $rootScope.authenticated);
		    $location.path("/home");
		    $scope.error = false;
		}
	    };

	    if (!$rootScope.authenticated) {
		authenticate();
	    }
	    $scope.credentials = {};
	    $scope.login = function() {
		authenticate($scope.credentials);
	    };
	    /*
	     * 
	     * $scope.login = function() { $log.info("login ");
	     * Signin.get("/signin", {headers :
	     * headers}).$promise.then(function(data) {
	     * 
	     * if (data.userId) { $rootScope.authenticated = true;
	     * $rootScope.userId = data.userId; $rootScope.userLocale =
	     * data.userLocale; $scope.changeLocale(data.userLocale); }
	     * $log.info("login userId : " + data.userId); }); };
	     */
	}]);

angular.module('socialNetworkControllers').controller('SuccessDialogController',
	["$uibModalInstance", "$scope", "message", function($uibModalInstance, $scope, message) {

	    $scope.message = message;
	    $scope.ok = function() {
		$uibModalInstance.close();
	    };
	}]);
angular.module('socialNetworkServices').factory('ChatRest', ["$resource", "config", function($resource, config) {
    var url = config.chatPath;
    resource = $resource(url, null, {
	getChat : {
	    method : 'GET',
	    url : url + '/:chatId'
	},
	getMessages : {
	    method : 'GET',
	    url : url + '/getMessages/:chatId',
	    isArray: true
	},
	getFilteredMessages:{
	    method : 'POST',
	    url : url + '/getMessages',
	    isArray: true
	},
	getMessage : {
	    method : 'GET',
	    url : url + '/getMessage'
	},
	sendMessage :{
	    method : 'POST',
	    url : url + '/sendMessage'
	},
	deleteMessage:{
	    method : 'DELETE',
	    url : url + '/deleteMessage/:messageId'
	},
	editMessage:{
	    method : 'POST',
	    url : url + '/editMessage'
	}
    });

    return resource;
}]);

function findChatById(chats, chatId) {
    var result = $.grep(chats, function(e) {
	return e.chatId == chatId;
    });
    if (result.length === 0) {
    } else if (result.length == 1) {
	return result[0];
    } else {
	alert("multiple items found");
    }
    return 0;
}
function initDatePicker($scope) {
    // Date options
    $scope.dateFilter = new Date();
    $scope.popup = {
	opened : false
    };
    $scope.dateOptions = {
	maxDate : new Date()
    };
    $scope.openDatePicker = function() {
	$scope.popup.opened = true;
    };
}

function findMessageById(messages, messageId) {
    var result = $.grep(messages, function(e) {
	return e.messageId == messageId;
    });
    if (result.length === 0) {
	return 0;
    } else if (result.length == 1) {
	return result[0];
    } else {
	alert("multiple items found");
    }
    
}
angular.module('socialNetworkApp').run(['$templateCache', function($templateCache) {$templateCache.put('pages/chat.html','\r\n<div ng-controller="ChatController">\r\n\t<div class="row">\r\n\t\t<div class="col-lg-4 pull-left">\r\n\t\t\t<div class="panel panel-default panel-height">\r\n\t\t\t\t<div class="panel-heading text-center">\r\n\t\t\t\t\t<span class="medium-font">Chat list</span>\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class="panel-body">\r\n\t\t\t\t\t<ul class="list-group" id="chatList">\r\n\t\t\t\t\t\t<li class="list-group-item" data-ng-repeat="chat in chats"\r\n\t\t\t\t\t\t\tdata-ng-click="getChatMessages(chat.chatId)"><span\r\n\t\t\t\t\t\t\tclass="label label-default label-pill pull-right">{{chat.newMessages}}</span>\r\n\t\t\t\t\t\t\t<div>{{chat.name}}</div></li>\r\n\t\t\t\t\t</ul>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<div class="col-lg-8 pull-right">\r\n\t\t\t<div class="panel panel-default">\r\n\t\t\t\t<div class="panel-heading">\r\n\t\t\t\t\t<span class="medium-font">Messages</span>\r\n\t\t\t\t\t<div class="input-group col-lg-4 pull-right">\r\n\t\t\t\t\t\t<input type="text" class="form-control"\r\n\t\t\t\t\t\t\tdata-ng-change="getFilteredMessages()"\r\n\t\t\t\t\t\t\tuib-datepicker-popup="dd.MM.yyyy" ng-model="dateFilter"\r\n\t\t\t\t\t\t\tis-open="popup.opened" datepicker-options="dateOptions"\r\n\t\t\t\t\t\t\tdata-close-text="Close" /> <span class="input-group-btn">\r\n\t\t\t\t\t\t\t<button type="button" class="btn btn-default"\r\n\t\t\t\t\t\t\t\tdata-ng-click="openDatePicker()">\r\n\t\t\t\t\t\t\t\t<i class="glyphicon glyphicon-calendar"></i>\r\n\t\t\t\t\t\t\t</button>\r\n\t\t\t\t\t\t</span>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\t\t\t\t<!-- Messages body container -->\r\n\t\t\t\t<div class="panel-body tab-pane" scroll-glue-bottom>\r\n\t\t\t\t\t<div class="row" data-ng-repeat="message in messages">\r\n\t\t\t\t\t\t<div class="panel"\r\n\t\t\t\t\t\t\tdata-ng-class="{false: \'panel-success pull-left\', true: \'panel-info pull-right\'}[message.ownerId == userId]">\r\n\t\t\t\t\t\t\t<div class="panel-heading">\r\n\t\t\t\t\t\t\t\t<div class="name-margin">{{message.ownerName}}</div>\r\n\t\t\t\t\t\t\t\t<div\r\n\t\t\t\t\t\t\t\t\tdata-ng-show="message.messageInviteStatus == null && message.ownerId == userId && !message.hidden">\r\n\t\t\t\t\t\t\t\t\t<div class="pull-right remove-margin"\r\n\t\t\t\t\t\t\t\t\t\tdata-ng-click="deleteMessage(message.messageId)">\r\n\t\t\t\t\t\t\t\t\t\t<h6>remove</h6>\r\n\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t\t<div class=" pull-right edit-margin"\r\n\t\t\t\t\t\t\t\t\t\tdata-ng-click="editMessage(message.messageId)">\r\n\t\t\t\t\t\t\t\t\t\t<h6>edit</h6>\r\n\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t<div class="panel-body">\r\n\t\t\t\t\t\t\t\t<div\r\n\t\t\t\t\t\t\t\t\tdata-ng-class="{true: \'glyphicon glyphicon-trash\'}[message.hidden]">{{message.text}}</div>\r\n\t\t\t\t\t\t\t\t<div id="invitation_message"\r\n\t\t\t\t\t\t\t\t\tdata-ng-show="message.messageInviteStatus == \'INVITE\' && message.ownerId != userId">\r\n\t\t\t\t\t\t\t\t\t<a class="btn btn-info" data-ng-click="acceptInvitation()">Accept</a>\r\n\t\t\t\t\t\t\t\t\t<a class="btn btn-info" data-ng-click="declineInvitation()">Decline</a>\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t<div class="panel-footer">{{message.date}}</div>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t\t<div class="panel panel-default" data-ng-show="chatId != 0">\r\n\t\t\t\t<div class="panel-heading text-center">Comment</div>\r\n\t\t\t\t<div>\r\n\t\t\t\t\t<textarea class="form-control" rows="5" data-ng-model="messageText"\r\n\t\t\t\t\t\tname="text" required></textarea>\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class="panel-footer">\r\n\t\t\t\t\t<a class="btn btn-info " data-ng-click="sendMessage()">Send</a>\r\n\t\t\t\t\t<div class="pull-right col-lg-2">\r\n\t\t\t\t\t\t<span>Public</span> \r\n\t\t\t\t\t\t<input\r\n\t\t\t\t\t\t\ttype="checkbox" data-ng-model="publicMessage"\r\n\t\t\t\t\t\t\tdata-ng-false-value="false" data-ng-true-value="true" />\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t</div>\r\n</div>\r\n\r\n<!-- Deleted message template -->\r\n<div class="row" id="deletedMessageRow" style="display: none">\r\n\t<div class="panel panel-margin" id="message">\r\n\t\t<div class="panel-heading">\r\n\t\t\t<div class="name-margin" id="ownerName"></div>\r\n\t\t</div>\r\n\t\t<div class="panel-body">\r\n\t\t\t<div id="messageText">\r\n\t\t\t\tThis message has been removed. <span\r\n\t\t\t\t\tclass="glyphicon glyphicon-trash"></span>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<div class="panel-footer" id="messageDate"></div>\r\n\t</div>\r\n</div>\r\n<!-- Chat template -->\r\n<li class="list-group-item" id="chatRow" style="display: none"><span\r\n\tclass="label label-default label-pill pull-right" id="chat_count"></span>\r\n\t<div id="chat_name"></div></li>');
$templateCache.put('parts/message.html','<div class="row" id="messageRow" >\r\n    <div class="panel panel-margin" id="message">\r\n        <div class="panel-heading">\r\n            <div class="name-margin" id="ownerName"></div>\r\n            <div class="pull-right remove-margin" id="deleteMessage"\r\n                style="display: none">\r\n                <h6>remove</h6>\r\n            </div>\r\n            <div class=" pull-right edit-margin" id="editMessage"\r\n                style="display: none">\r\n                <h6>edit</h6>\r\n            </div>\r\n        </div>\r\n        <div class="panel-body">\r\n            <div id="messageText"></div>\r\n            <div id="invitation_message" style="display: none">\r\n                <a class="btn btn-info" id="acceptInvitation">Accept</a> <a\r\n                    class="btn btn-info" id="declineInvitation">Decline</a>\r\n            </div>\r\n        </div>\r\n        <div class="panel-footer" id="messageDate"></div>\r\n    </div>\r\n</div>');}]);
angular.module('socialNetworkControllers').controller('ChatController',
	["$scope", "$rootScope", "$q", "ChatRest", "$log", "$templateCache", "$compile", "chatIdProperty", function($scope, $rootScope, $q, ChatRest, $log, $templateCache, $compile, chatIdProperty) {

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

	    // Load messages from redis
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

	    $scope.getChatMessages = function(chatId) {

		$log.debug('getChatMessages chatId : ' + chatId);

		$scope.chatId = chatId;

		ChatRest.getMessages({
		    chatId : chatId
		}, function(messages) {
		    $scope.messages = messages;
		    findChatById($scope.chats, chatId).newMessages = '';
		}, function(error) {
		    ServerErrorHandler(error);
		});

	    };
	    $scope.sendMessage = function() {

		$log.debug('sendMessage text : ' + $scope.messageText);

		ChatRest.sendMessage({
		    chatId : $scope.chatId,
		    message : $scope.messageText,
		    publicMessage : $scope.publicMessage
		}, function(data) {
		    $scope.messageText = '';
		});

	    };
$scope.deleteMessage = function(messageId) {

    $log.info('deleteMessage messageId :' + messageId);
    
    ChatRest.deleteMessage({
	messageId : messageId
    }, function(data) {
	if(data.response){
	    
	}	
    });
};
$scope.editMessage = function(messageId) {

    $log.debug('editMessage messageId :' + messageId);
    
    ChatRest.editMessage({
	messageId : messageId,
	message : $scope.messageText
    }, function(data) {
	if(data.response){
	    
	}	
    });
    
};
$scope.getFilteredMessages = function() {

    $log.debug('getFilteredMessages dateFilter : ' + JSON.stringify($scope.dateFilter).substr(1,10));
    
    $scope.messages = [];
    ChatRest.getFilteredMessages({
	chatId : $scope.chatId,
	dateFilter : JSON.stringify($scope.dateFilter).substr(1,10)
    }, function(messages) {
	 $scope.messages = messages;
    });

};
}]);
angular.module('socialNetworkServices').factory('FriendActions', ["$log", "FriendRest", function($log, FriendRest) {

    return {
	inviteFriend : function(userId) {
	    $log.info('FriendActions inviteFriend');

	    var response = FriendRest.inviteFriend({
		userId : userId
	    }, function(data) {
		return data;
	    });

	    return response;
	},
	acceptInvitation : function(userId) {
	    $log.info('FriendActions acceptInvitation');

	    var response = FriendRest.acceptInvitation({
		userId : userId
	    }, function(data) {
		return data;
	    });

	    return response;
	},
	declineInvitation : function(userId) {
	    $log.info('FriendActions declineInvitation');

	    var response = FriendRest.declineInvitation({
		userId : userId
	    }, function(data) {
		return data;
	    });

	    return response;
	}
    };
}]);
angular.module('socialNetworkServices').factory('FriendRest', ["$resource", "config", function ($resource, config) {
    var url = config.friendPath,
        resource = $resource(url, null, {
            get: {
                method: 'GET',
                cache: true,
                isArray: true
            },
            inviteFriend :{
        	method: 'POST',
        	url: url + '/inviteFriend/:userId',
                params: {userId: '@userId'}
            },
            acceptInvitation :{
        	method: 'POST',
        	url: url + '/acceptInvitation/:userId',
                params: {userId: '@userId'}
            },
            declineInvitation :{
        	method: 'POST',
        	url: url + '/declineInvitation/:userId',
                params: {userId: '@userId'}
            },
            remove: {
                method: 'DELETE',
                url: url + '/:userId',
                params: {userId: '@userId'}
            }
        });

    return resource;
}]);
angular.module('socialNetworkApp').run(['$templateCache', function($templateCache) {$templateCache.put('pages/friend.html','<div ng-controller="FriendController">\r\n\t<div class="panel panel-default">\r\n\t\t<div class="panel-heading text-center">\r\n\t\t\t<span class="medium-font">Friends list</span>\r\n\t\t</div>\r\n\t\t<div class="panel-body">\r\n\t\t\t<table class="table">\r\n\t\t\t\t<thead>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<th>Friend name</th>\r\n\t\t\t\t\t\t<th>Status</th>\r\n\t\t\t\t\t\t<th>Chat</th>\r\n\t\t\t\t\t\t<th>Delete</th>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t</thead>\r\n\t\t\t\t<tr id="friend_" data-ng-repeat="friend in friends">\r\n\t\t\t\t\t<td>{{friend.name}}</td>\r\n\t\t\t\t\t<td>{{friend.status}}</td>\r\n\t\t\t\t\t<td><a class="btn btn-info"\r\n\t\t\t\t\t\tdata-ng-show="friend.status == \'ACCEPTED\'"\r\n\t\t\t\t\t\tdata-ng-click=\'goToChat(friend.chatId)\'>Go to chat</a></td>\r\n\t\t\t\t\t<td><a class="btn btn-info"\r\n\t\t\t\t\t\tdata-ng-show="friend.status == \'ACCEPTED\'"\r\n\t\t\t\t\t\tdata-ng-click=\'deleteFriend(friend.friendId)\'>Delete</a></td>\r\n\t\t\t\t</tr>\r\n\t\t\t</table>\r\n\t\t</div>\r\n\t</div>\r\n</div>');}]);
angular.module('socialNetworkControllers').controller('FriendController',
	["$scope", "FriendRest", "$log", "$location", "chatIdProperty", function($scope, FriendRest, $log, $location, chatIdProperty) {

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
}]);
angular.module('socialNetworkServices').factory('GroupRest', ["$resource", "config", function ($resource, config) {
        var url = config.groupPath;
		resource = $resource(url, null, {
            getGroupById: {
                method: 'GET',
                url: url + '/:groupId'
            },        
            getFriendsNotInGroup: {
                method: 'GET',
                isArray: true,
                url: config.friendsNotInGroup + '/:groupId',
            },
            addUser: {
                method: 'POST',
                url: config.addUserToGroupPath,
            },
            deleteUser: {
                method: 'PUT',
                url: config.deleteUserFromGroupPath,
            },
            leave: {
                method: 'DELETE',
                url: config.leaveGroupPath + '/:groupId'
            },
            remove: {
                method: 'DELETE',
                url: url + '/:groupId'
            }
        });

    return resource;
}]);
function findGroupById(groups, groupId) {
    var result = $.grep(groups, function(e) {
	return e.groupId == groupId;
    });
    if (result.length === 0) {
	alert("group not found");
    } else if (result.length == 1) {
	return result[0];
    } else {
	alert("multiple items found");
    }
    return 0;
}

function removeGroupById(groups, groupId) {
    for (var i = 0; i < groups.length; i++)
	if (groups[i].groupId == groupId) {
	    groups.splice(i, 1);
	    break;
	}
}
angular.module('socialNetworkApp').run(['$templateCache', function($templateCache) {$templateCache.put('pages/group.html','<div ng-controller="GroupController">\r\n\t<div data-ng-show="groupListView"\r\n\t\tdata-ng-include="\'parts/groupList.html\'"></div>\r\n\t<div data-ng-show="createGroupView"\r\n\t\tdata-ng-include="\'parts/createGroup.html\'"></div>\r\n\t<div data-ng-show="editGroupView"\r\n\t\tdata-ng-include="\'parts/editGroup.html\'"></div>\r\n</div>');
$templateCache.put('parts/createGroup.html','<div class="panel panel-default">\r\n\t<div class="panel-heading text-center">\r\n\t\t<span class="medium-font">Create group</span>\r\n\t</div>\r\n\t<div class="panel-body">\r\n\t\t<form class="form-horizontal" role="form">\r\n\t\t\t<div class="form-group">\r\n\t\t\t\t<label class="col-sm-3 control-label">Group Name</label>\r\n\t\t\t\t<div class="col-sm-7">\r\n\t\t\t\t\t<input type="text" data-ng-model="group.name" class="form-control" />\r\n\t\t\t\t</div>\r\n\t\t\t\t<label class="col-sm-3 control-label">Public</label>\r\n\t\t\t\t<div class="col-sm-1">\r\n\t\t\t\t\t<input type="checkbox" data-ng-model="group.type"\r\n\t\t\t\t\t\tdata-ng-false-value="false" data-ng-true-value="true" />\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t\t<div class="form-group text-center">\r\n\t\t\t\t<span class="medium-font">Friends list</span>\r\n\t\t\t</div>\r\n\t\t\t<div class="form-group">\r\n\t\t\t\t<table class="table">\r\n\t\t\t\t\t<thead>\r\n\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t<th></th>\r\n\t\t\t\t\t\t\t<th>Friend id</th>\r\n\t\t\t\t\t\t\t<th>Friend name</th>\r\n\t\t\t\t\t\t\t<th>Friend status</th>\r\n\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t</thead>\r\n\t\t\t\t\t<tr class="blank_row text-center" data-ng-hide="friends">\r\n\t\t\t\t\t\t<td colspan="4">No friends</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t<tr id="friend_" data-ng-repeat="friend in friends">\r\n\t\t\t\t\t\t<td><input type="checkbox" data-ng-model="friendsId[$index]"\r\n\t\t\t\t\t\t\tdata-ng-false-value="undefined"\r\n\t\t\t\t\t\t\tdata-ng-true-value="{{friend.userId}}" /></td>\r\n\t\t\t\t\t\t<td>{{friend.friendId}}</td>\r\n\t\t\t\t\t\t<td>{{friend.name}}</td>\r\n\t\t\t\t\t\t<td>{{friend.status}}</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t</table>\r\n\t\t\t</div>\r\n\t\t</form>\r\n\t</div>\r\n\t<div class="panel-footer">\r\n\t\t<div class="col-lg-offset-5">\r\n\t\t\t<a class="btn btn-default" data-ng-click="initGroupsList()">Back</a>\r\n\t\t\t<a class="btn btn-default" data-ng-click="saveGroup()">Save</a>\r\n\t\t</div>\r\n\t</div>\r\n</div>\r\n');
$templateCache.put('parts/editGroup.html','<div class="panel panel-default">\r\n\t<div class="panel-heading text-center">\r\n\t\t<span class="medium-font"> Edit group {{group.name}}, group id: {{group.groupId}}</span>\r\n\t</div>\r\n\t<div class="panel-body">\r\n\t\t<table class="table">\r\n\t\t\t<thead>\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<th>User id</th>\r\n\t\t\t\t\t<th>User name</th>\r\n\t\t\t\t\t<th>Profile</th>\r\n\t\t\t\t\t<th>Delete</th>\r\n\t\t\t\t</tr>\r\n\t\t\t</thead>\r\n\t\t\t<tr id="groupUser_{{user.userId}}"\r\n\t\t\t\tdata-ng-repeat="user in group.users">\r\n\t\t\t\t<td>{{user.userId}}</td>\r\n\t\t\t\t<td>{{user.fullName}}</td>\r\n\t\t\t\t<td><a class="btn btn-info" href="#/profile/{{user.userId}}">Profile </a></td>\r\n\t\t\t\t<td><a class="btn btn-info" data-ng-show="!user.groupAdmin"\r\n\t\t\t\t\tng-click="deleteUserFromGroup(group.groupId, user.userId)">Delete\r\n\t\t\t\t\t\tuser</a></td>\r\n\t\t\t<tr>\r\n\t\t</table>\r\n\t</div>\r\n</div>\r\n<br />\r\n<div class="panel panel-default">\r\n\t<div class="panel-heading text-center">\r\n\t\t<span class="medium-font">List of friends</span>\r\n\t</div>\r\n\t<div class="panel-body">\r\n\t\t<table class="table">\r\n\t\t\t<thead>\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<th>User id</th>\r\n\t\t\t\t\t<th>User name</th>\r\n\t\t\t\t\t<th>Profile</th>\r\n\t\t\t\t</tr>\r\n\t\t\t</thead>\r\n\t\t\t<tr id="groupFriend_{{friend.userId}}"\r\n\t\t\t\tdata-ng-repeat="friend in friendsNotInGroup">\r\n\t\t\t\t<td>{{friend.userId}}</td>\r\n\t\t\t\t<td>{{friend.name}}</td>\r\n\t\t\t\t<td><a class="btn btn-info" data-ng-click="viewProfile(friend.userId)">Profile</a></td>\r\n\t\t\t\t<td><a class="btn btn-info"\r\n\t\t\t\t\tdata-ng-click="addUserToGroup(group.groupId, friend.userId)">{{\'ADD_USER\'\r\n\t\t\t\t\t\t| translate}}</a></td>\r\n\t\t\t</tr>\r\n\t\t</table>\r\n\t</div>\r\n\t<div class="panel-footer">\r\n\t\t<div class="col-lg-offset-5">\r\n\t\t\t<a class="btn btn-default" ng-click="initGroupsList()">Back</a>\r\n\t\t\t<a class="btn btn-default" ng-click="initCreateGroup()">CreateGroup</a>\r\n\t\t</div>\r\n\t</div>\r\n</div>\r\n\r\n');
$templateCache.put('parts/groupList.html','<div class="panel panel-default">\r\n\t<div class="panel-heading text-center">\r\n\t\t<span class="medium-font">Groups list</span>\r\n\t</div>\r\n\t<div class="panel-body">\r\n\t\t<table class="table">\r\n\t\t\t<thead>\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<th data-ng-click="sort(\'groupId\')">Group id <i\r\n\t\t\t\t\t\tdata-ng-class="{\'glyphicon-chevron-up\' : isSortUp(\'groupId\'),\'iglyphcon-chevron-down\':isSortDown(\'groupId\')}"></i>\r\n\t\t\t\t\t</th>\r\n\t\t\t\t\t<th data-ng-click="sort(\'name\')">Name</th>\r\n\t\t\t\t\t<th>Hidden</th>\r\n\t\t\t\t\t<th>Edit</th>\r\n\t\t\t\t\t<th>Delete</th>\r\n\t\t\t\t</tr>\r\n\t\t\t</thead>\r\n\t\t\t<tr>\r\n\t\t\t\t<td><input type="text" data-ng-model="criteriaId"></td>\r\n\t\t\t\t<td><input type="text" data-ng-model="criteriaName"></td>\r\n\t\t\t\t<td></td>\r\n\t\t\t\t<td></td>\r\n\t\t\t\t<td></td>\r\n\t\t\t</tr>\r\n\t\t\t<tr id="friend_" data-ng-show="isLoad"\r\n\t\t\t\tdata-ng-repeat="group in filteredGroups = (groups | filter:{groupId:criteriaId, name:criteriaName} \r\n\t\t\t\t| orderBy:sortField:reverse | pagination:pageNo:pageSize)">\r\n\t\t\t\t<td>{{group.groupId}}</td>\r\n\t\t\t\t<td>{{group.name}}</td>\r\n\t\t\t\t<td>{{group.hidden}}</td>\r\n\t\t\t\t<td><a class="btn btn-info" data-ng-show="group.groupAdmin"\r\n\t\t\t\t\tdata-ng-click="initEditGroup(group.groupId)">Edit group</a></td>\r\n\t\t\t\t<td><a class="btn btn-info"\r\n\t\t\t\t\tdata-ng-show="group.groupAdmin"\r\n\t\t\t\t\tdata-ng-click=\'deleteGroup(group.groupId)\'>Delete group</a> <a\r\n\t\t\t\t\tclass="btn btn-info" data-ng-show="!group.groupAdmin"\r\n\t\t\t\t\tdata-ng-click=\'leaveGroup(group.groupId)\'>Leave group</a></td>\r\n\t\t\t\t<td></td>\r\n\t\t\t</tr>\r\n\t\t</table>\r\n\t\tTotal groups: {{filteredGroups.length}}\r\n\t\t<div><a data-ng-click="prevPage()">prev</a> - <a data-ng-click="nextPage()">next</a></div>\t\t\r\n\t</div>\r\n\t<div class="panel-footer text-center">\r\n\t\t<a class="btn btn-default" ng-click="initCreateGroup()">CreateGroup</a>\r\n\t</div>\r\n</div>\r\n');}]);
angular.module('socialNetworkControllers').controller('GroupController',
	["$scope", "$rootScope", "$q", "GroupRest", "FriendRest", "resultDialog", "$log", "$location", function($scope, $rootScope, $q, GroupRest, FriendRest, resultDialog, $log, $location) {

	    $scope.pageNo = 0;
	    $scope.pageSize = 10;
	    $scope.groups = [];
	    
	    GroupRest.query(function(groups) {
		$log.info('init groups list');
		$scope.groups = groups;
		$scope.initGroupsList();
		$scope.isLoad = true;
	    });
$scope.initCreateGroup = function() {
    $scope.createGroupView = true;
    $scope.groupListView = false;
    $scope.editGroupView = false;
    
    $scope.group = {};
    $scope.group.type = false;
    
    FriendRest.get(function(friends) {
	$log.info('initCreateGroup');
	$scope.friends = friends;
	$scope.friendsId = [];	
    });

    $scope.saveGroup = function() {
	$log.info('Create group name : ' + $scope.group.text + ', friendId : ' + $scope.friendsId);

	if ($scope.group.name) {
	    GroupRest.save({
		groupName : $scope.group.name,
		friendsId : $scope.friendsId,
		publicGroup : $scope.group.type
	    }, function(data) {
		if (data.groupId) {
		    $scope.groups.push(data);
		    resultDialog.dialog(true, "Success create group " + data.name);
		    $scope.initGroupsList();
		} else if (data.error) {
		    resultDialog.dialog(false, data.error);
		} else {
		    resultDialog.dialog(data.response, '');
		}
	    });
	}else{
	    resultDialog.dialog(false, 'no name'); 
	}
    };
};

$scope.initEditGroup = function(groupId) {
    $scope.editGroupView = true;
    $scope.createGroupView = false;
    $scope.groupListView = false;

    $scope.group = [];
    $scope.group.users = [];

    $q.all([ FriendRest.get().$promise, GroupRest.getGroupById({
	groupId : groupId
    }).$promise ]).then(function(response) {
	$log.info('initEditGroup');
	$scope.friends = [];
	$scope.friends = response[0];
	$scope.group = response[1];

	$scope.friendsNotInGroup = [];

	createUserFromFriend($scope.friends, $scope.group.users);
    });

    function createUserFromFriend(friends, groupUsers) {
	angular.forEach(friends, function(value1, key1) {
	    $log.debug(' createUserFromFriend id ' + value1.userId + '-' + value1.status);
	    angular.forEach(groupUsers, function(value2, key2) {
		if (value1.userId === value2.userId || value1.status !== 'ACCEPTED') {
		    friends.splice(key1, 1);
		}
	    });
	});
	$scope.friendsNotInGroup = friends;
    }

    $scope.addUserToGroup = function(groupId, userId) {
	$log.info('add user to groupId : ' + groupId);
	GroupRest.addUser({
	    groupId : groupId,
	    userId : userId
	}, function(data) {
	    if (data.userId) {
		removeUserById($scope.friendsNotInGroup, userId);
		$scope.group.users.push(data);
		resultDialog.dialog(true, "Success add user with userId : " + userId);
	    }
	});
    };

    $scope.deleteUserFromGroup = function(groupId, userId) {
	$log.info('delete user from groupId : ' + groupId);
	GroupRest.deleteUser({
	    groupId : groupId,
	    userId : userId
	}, function(data) {
	    if (data.userId) {
		removeUserById($scope.group.users, userId);
		$scope.friendsNotInGroup.push(data);
		resultDialog.dialog(true);
	    }
	});
    };

    $scope.leaveGroup = function(groupId) {
	$log.info('leave groupId : ' + groupId);
	GroupRest.leave({
	    groupId : groupId
	}, function(data) {
	    if (data.response) {
		resultDialog.dialog(data.response);
	    }
	});
    };

    $scope.viewProfile = function(userId) {
	$scope.profileId = userId;
	$location.path("/profile");
    };
};
$scope.initGroupsList = function() {
    $scope.groupListView = true;
    $scope.createGroupView = false;
    $scope.editGroupView = false;

    // Sort group
    $scope.sortField = undefined;
    $scope.reverse = false;

    $scope.sort = function(fieldName) {
	if ($scope.sortField === fieldName) {
	    $scope.reverse = !$scope.reverse;
	} else {
	    $scope.sortField = fieldName;
	    $scope.reverse = false;
	}
    };
    $scope.isSortUp = function(fieldName) {
	return $scope.sortField === fieldName && !$scope.reverse;
    };
    $scope.isSortDown = function(fieldName) {
	return $scope.sortField === fieldName && $scope.reverse;
    };

    // Delete group
    $scope.deleteGroup = function(groupId) {
	$log.debug('remove groupId : ' + groupId);
	GroupRest.remove({
	    groupId : groupId
	}, function(data) {
	    if (data.groupId) {
		removeGroupById($scope.groups, groupId);
		resultDialog.dialog(true, 'success_delete_group');
	    }
	});
    };
    
    $scope.goToChat = function(chatId) {
	chatIdProperty.setChatId(chatId);
	$location.path('/chat');
    };

    $scope.nextPage = function() {
	$scope.pageNo++;
    };
    $scope.prevPage = function() {
	if ($scope.pageNo > 0) {
	    $scope.pageNo--;
	}
    };

};
}]);
angular.module('socialNetworkServices').factory('ProfileRest', ["$resource", "config", function($resource, config) {
    var url = config.profilePath;
    resource = $resource(url, null, {
	get : {
	    method : 'GET',
	    url : url + '/:profileId'
	},
	search : {
	    method : 'POST',
	    url : url + '/search',
	    isArray : true
	},
	changepassword : {
	    method : 'POST',
	    url : url + '/changepassword'
	}
    });

    return resource;
}]);

angular.module('socialNetworkApp').run(['$templateCache', function($templateCache) {$templateCache.put('pages/editProfile.html','<div class="col-lg-6 col-lg-offset-3" ng-controller="ProfileController" data-ng-init="editProfile()">\r\n\t<div data-ng-show="changePasswordView"\r\n                data-ng-include="\'parts/changePassword.html\'"></div>\r\n\t<div class="panel panel-default" data-ng-show="editProfileView">\r\n\t\t<div class="panel-heading text-center">\r\n\t\t\t<span class="medium-font">My profile</span>\r\n\t\t</div>\r\n\t\t<div class="panel-body">\r\n\t\t\t<form class="form-narrow form-horizontal">\r\n\r\n\t\t\t\t<div data-ng-include="\'parts/publicInfo.html\'"></div>\r\n\r\n\t\t\t\t<div class="form-group">\r\n\t\t\t\t\t<label class="col-lg-3 control-label">Gender</label>\r\n\t\t\t\t\t<div class="col-lg-7">\r\n\t\t\t\t\t\t<label class="col-lg-5 control-label"> <input type="radio"\r\n\t\t\t\t\t\t\tdata-ng-model="profile.sex" value="male"> Male\r\n\t\t\t\t\t\t</label> <label class="col-lg-5 control-label"> <input\r\n\t\t\t\t\t\t\ttype="radio" data-ng-model="profile.sex" value="female">\r\n\t\t\t\t\t\t\tFemale\r\n\t\t\t\t\t\t</label>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\r\n\t\t\t\t<label class="col-lg-3 control-label">Locale</label>\r\n\t\t\t\t<div class="col-lg-9">\r\n\t\t\t\t\t<select class="form-control" data-ng-model="profile.locale">\r\n\t\t\t\t\t\t<option data-ng-repeat="lang in languaches" value="{{lang.code}}">{{lang.name}}</option>\r\n\t\t\t\t\t</select>\r\n\t\t\t\t</div>\r\n\t\t\t\t\r\n\t\t\t\t<label class="col-lg-3 control-label">Translate</label>\r\n\t\t\t\t<div class="col-lg-1">\r\n\t\t\t\t\t<input type="checkbox" class="form-control"\r\n\t\t\t\t\t\tdata-ng-model="profile.translate" />\r\n\t\t\t\t</div>\r\n\t\t\t\t<pre data-ng-bind="profile|json"></pre>\r\n\t\t\t</form>\r\n\t\t</div>\r\n\r\n\t\t<div class="panel-footer">\r\n\t\t\t<div class="col-lg-offset-5">\r\n\t\t\t\t<a class="btn btn-default" data-ng-click="saveProfile()">Save</a> <a\r\n\t\t\t\t\tclass="btn btn-default" data-ng-click="changePassword()">Change\r\n\t\t\t\t\tPassword </a>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t</div>\r\n</div>');
$templateCache.put('pages/searchProfile.html','<div ng-controller="ProfileController">\r\n\t<div class="row">\r\n\t\t<div class="col-lg-4 pull-left">\r\n\t\t\t<div class="panel panel-default">\r\n\t\t\t\t<div class="panel-heading text-center">\r\n\t\t\t\t\t<span class="medium-font">Search user profile</span>\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class="panel-body">\r\n\t\t\t\t\t<form class="form-narrow form-horizontal">\r\n\t\t\t\t\t\t<div data-ng-include="\'parts/publicInfo.html\'"></div>\r\n\t\t\t\t\t</form>\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class="panel-footer">\r\n\t\t\t\t\t<div class="col-lg-offset-5">\r\n\t\t\t\t\t\t<a class="btn btn-default" data-ng-click="searchProfile()">Search</a>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<div class="col-lg-8 pull-right">\r\n\t\t\t<div class="panel panel-default">\r\n\t\t\t\t<div class="panel-heading">\r\n\t\t\t\t\t<span class="medium-font">User list </span>\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class="panel-body">\r\n\t\t\t\t\t<table class="table">\r\n\t\t\t\t\t\t<thead>\r\n\t\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t\t<th>User id</th>\r\n\t\t\t\t\t\t\t\t<th>First name</th>\r\n\t\t\t\t\t\t\t\t<th>Last name</th>\r\n\t\t\t\t\t\t\t\t<th>Street</th>\r\n\t\t\t\t\t\t\t\t<th>City</th>\r\n\t\t\t\t\t\t\t\t<th>Country</th>\r\n\t\t\t\t\t\t\t\t<th>Invite</th>\r\n\t\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t\t</thead>\r\n\t\t\t\t\t\t<tr data-ng-repeat="profile in profiles">\r\n\t\t\t\t\t\t\t<td>{{profile.userId}}</td>\r\n\t\t\t\t\t\t\t<td>{{profile.firstName}}</td>\r\n\t\t\t\t\t\t\t<td>{{profile.lastName}}</td>\r\n\t\t\t\t\t\t\t<td>{{profile.streey}}</td>\r\n\t\t\t\t\t\t\t<td>{{profile.city}}</td>\r\n\t\t\t\t\t\t\t<td>{{profile.country}}</td>\r\n\t\t\t\t\t\t\t<td data-ng-switch="profile.friendStatus"><span\r\n\t\t\t\t\t\t\t\tdata-ng-switch-when="YOU">Your profile</span> <a\r\n\t\t\t\t\t\t\t\tclass="btn btn-default" data-ng-switch-when="ACCEPTED"\r\n\t\t\t\t\t\t\t\tdata-ng-click="deleteFriend(profile.userId)">Delete\r\n\t\t\t\t\t\t\t\t\t{{profile.firstName}}</a> <span data-ng-switch-when="INVITEE">\r\n\t\t\t\t\t\t\t\t\tINVITEE </span>\r\n\t\t\t\t\t\t\t\t<div data-ng-switch-when="INVITER">\r\n\t\t\t\t\t\t\t\t\t<a class="btn btn-default"\r\n\t\t\t\t\t\t\t\t\t\tdata-ng-click="declineFriend(profile.userId)">Decline\r\n\t\t\t\t\t\t\t\t\t\t{{profile.firstName}}</a> <a class="btn btn-default"\r\n\t\t\t\t\t\t\t\t\t\tdata-ng-click="acceptFriend(profile.userId)">Accept\r\n\t\t\t\t\t\t\t\t\t\t{{profile.firstName}}</a>\r\n\t\t\t\t\t\t\t\t</div> <a class="btn btn-default" data-ng-switch-default\r\n\t\t\t\t\t\t\t\tdata-ng-click="inviteFriend(profile.userId)">Invite\r\n\t\t\t\t\t\t\t\t\t{{profile.firstName}}</a></td>\r\n\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t</table>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t</div>\r\n</div>');
$templateCache.put('pages/viewProfile.html','<div class="col-lg-8 col-lg-offset-2" ng-controller="ProfileController">\r\n\t<div class="panel panel-default">\r\n\t\t<div class="panel-heading text-center">\r\n\t\t\t<span class="medium-font">View {{profile.firstName}} profile</span>\r\n\t\t</div>\r\n\t\t<div class="panel-body">\r\n\t\t\t<form class="form-narrow form-horizontal">\r\n\r\n\t\t\t\t<div data-ng-include="\'parts/publicInfo.html\'"></div>\r\n\t\t\t\t\r\n\t\t\t\t<div class="form-group">\r\n\t\t\t\t\t<label class="col-lg-3 control-label">Status</label>\r\n\t\t\t\t\t<div class="col-lg-7 text-center">\r\n\t\t\t\t\t\t{{profile.friendStatus}}\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\t\t\t\t\r\n\t\t\t\t<pre data-ng-bind="profile|json"></pre>\r\n\t\t\t</form>\r\n\t\t</div>\r\n\t\t<div class="panel-footer" data-ng-show="!profile.friendStatus">\r\n            <div class="col-lg-offset-5">\r\n                <a class="btn btn-default" data-ng-click="inviteFriend()">Send Invitation</a>\r\n            </div>\r\n        </div>\r\n\t</div>\r\n</div>');
$templateCache.put('parts/changePassword.html','<div class="panel panel-default">\r\n\t<div class="panel-heading text-center">\r\n\t\t<span class="medium-font">Change Password</span>\r\n\t</div>\r\n\t<div class="panel-body">\r\n\t\t<form class="form-narrow form-horizontal">\r\n\r\n\t\t\t<div class="form-group">\r\n\t\t\t\t<input class="form-control" data-ng-model="profile.oldPassword"\r\n\t\t\t\t\ttype="password" placeholder="Old Password" />\r\n\t\t\t</div>\r\n\r\n\t\t\t<div class="form-group">\r\n\t\t\t\t<input class="form-control" data-ng-model="profile.newPassword"\r\n\t\t\t\t\ttype="password" placeholder="New Password" /> <input\r\n\t\t\t\t\tclass="form-control" data-ng-model="profile.confirmPassword"\r\n\t\t\t\t\ttype="password" placeholder="Confirm Password" />\r\n\t\t\t</div>\r\n\t\t</form>\r\n\t</div>\r\n\t<div class="panel-footer">\r\n\t\t<div class="col-lg-offset-5">\r\n\t\t\t<a class="btn btn-default" data-ng-click="editProfile()">Back</a>\r\n\t\t\t<a class="btn btn-default" data-ng-click="savePassword()">Save\r\n\t\t\t\tPassword </a>\r\n\t\t</div>\r\n\t</div>\r\n</div>\r\n');
$templateCache.put('parts/publicInfo.html','<div class="form-group">\r\n\t<label class="col-sm-5 control-label">First name</label>\r\n\t<div class="col-sm-7">\r\n\t\t<input type="text" class="form-control"\r\n\t\t\tdata-ng-readonly="readonly" data-ng-model="profile.firstName"\r\n\t\t\tdata-ng-minlength="3" data-ng-maxlength="10" data-ng-not-empty />\r\n\t</div>\r\n</div>\r\n\r\n<div class="form-group">\r\n\t<label class="col-sm-5 control-label">Last name</label>\r\n\t<div class="col-sm-7">\r\n\t\t<input type="text" class="form-control"\r\n\t\t\tdata-ng-readonly="readonly" data-ng-model="profile.lastName" />\r\n\t</div>\r\n</div>\r\n\r\n<div class="form-group">\r\n\t<label class="col-sm-5 control-label">Street</label>\r\n\t<div class="col-sm-7">\r\n\t\t<input type="text" class="form-control"\r\n\t\t\tdata-ng-readonly="readonly" data-ng-model="profile.street" />\r\n\t</div>\r\n</div>\r\n\r\n<div class="form-group">\r\n\t<label class="col-sm-5 control-label">City</label>\r\n\t<div class="col-sm-7">\r\n\t\t<input type="text" class="form-control"\r\n\t\t\tdata-ng-readonly="readonly" data-ng-model="profile.city" />\r\n\t</div>\r\n</div>\r\n\r\n<div class="form-group">\r\n\t<label class="col-sm-5 control-label">Country</label>\r\n\t<div class="col-sm-7">\r\n\t\t<input type="text" class="form-control"\r\n\t\t\tdata-ng-readonly="readonly" data-ng-model="profile.country" />\r\n\t</div>\r\n</div>');}]);
angular.module('socialNetworkControllers').controller('ProfileController',
	["$scope", "$rootScope", "$log", "ProfileRest", "$routeParams", "$http", "$location", "FriendActions", function($scope, $rootScope, $log, ProfileRest, $routeParams, $http, $location, FriendActions) {

	    $scope.profile = {};
	    $scope.readonly = false;

	    var profileId = $routeParams.profileId;

	    // View profile
	    if (profileId) {
		ProfileRest.get({
		    profileId : profileId
		}, function(profile) {
		    $log.debug('ProfileController init profile by profileId : ' + profileId);
		    $scope.profile = profile;
		    $scope.readonly = true;
		});
	    }
$scope.changePassword = function() {
    $scope.changePasswordView = true;
    $scope.editProfileView = false;

    $scope.savePassword = function() {
	ProfileRest.changepassword({
	    profileId : profileId
	}, function(profile) {

	});
    };
};
$scope.editProfile = function() {
    $scope.editProfileView = true;
    $scope.changePasswordView = false;

    ProfileRest.get(function(profile) {
	$log.debug('ProfileController get profile');
	$scope.profile = profile;
	getLanguaches();

    });

    $scope.saveProfile = function() {
	$log.debug('ProfileController save profile');
	ProfileRest.save($scope.profile);
    };

    function getLanguaches() {
	$http.get('../i18n/i18n_config.properties').success(function(data) {
	    $log.debug(' profile getLanguaches ');
	    $scope.languaches = data;
	});
    }
};
$scope.searchProfile = function() {

    ProfileRest.search($scope.profile, function(profiles) {
	$log.info('ProfileController search profile');
	$scope.profiles = profiles;
    });

    $scope.inviteFriend = function(userId) {
	var response = FriendActions.inviteFriend(userId);
	
	if (response.userId) {
	    $scope.successMessageHandler("Success invite user : " + response.userId);
	}
    };

    $scope.acceptInvitation = function(userId) {
	var response = FriendActions.acceptInvitation(userId);
	
	if (response.userId) {
	    $scope.successMessageHandler("Success accept invitation user : " + response.userId);
	}
    };

    $scope.declineInvitation = function(userId) {
	var response = FriendActions.declineInvitation(userId);
	
	if (response.userId) {
	    $scope.successMessageHandler("Success decline invitation user : " + response.userId);
	}
    };
};
}]);
var socialNetworMockServerkApp = angular.module('socialNetworkApp')
// 'ngMockE2E'

socialNetworMockServerkApp.config(["$provide", function($provide) {
    $provide.decorator('$httpBackend', angular.mock.e2e.$httpBackendDecorator);

    var DELAY_MS = 2000;
    $provide.decorator('$httpBackend', ["$delegate", function($delegate) {
	var proxy = function(method, url, data, callback, headers) {
	    var interceptor = function() {
		var _this = this, _arguments = arguments;
		setTimeout(function() {
		    // return result to the client AFTER delay
		    callback.apply(_this, _arguments);
		}, DELAY_MS);
	    };
	    return $delegate.call(this, method, url, data, interceptor, headers);
	};
	for ( var key in $delegate) {
	    proxy[key] = $delegate[key];
	}
	return proxy;
    }]);
}]);

socialNetworMockServerkApp.run(["$httpBackend", function($httpBackend) {
    /**
     * Lang
     */
    $httpBackend.whenGET('../i18n/en.properties').respond({
	"code" : "en",
	"error_delete_group" : "Error delete group ",
	"success_delete_group" : "Success delete group ",
	"ADD_USER" : "Add user",
	"FRIENDS" : "Friends",
	"service_Error" : "Service not available"
    });

    $httpBackend.whenGET('../i18n/no.properties').respond(function(method, url, obj) {
	return [ 400, {
	    "error" : 'error load file '
	} ]
    });

    $httpBackend.whenGET('../i18n/i18n_config.properties').respond([ {
	"id" : "en",
	"code" : "en",
	"name" : "English"
    }, {
	"id" : "ru",
	"code" : "ru",
	"name" : "Русский"
    } ]);

    $httpBackend.whenGET('../i18n/ru.properties').respond({
	"code" : "en",
	"error_delete_group" : "Error delete group ",
	"success_delete_group" : "Success delete group ",
	"ADD_USER" : "Добавить друга",
	"FRIENDS" : "Друзья"
    });

    /**
     * Signin
     */
    $httpBackend.whenGET('/SocialNetworkApi/signin').respond({});
    $httpBackend.whenPOST('/SocialNetworkApi/j_spring_security_check?j_password=user1&j_username=user1').respond({
	"userId" : 1,
	"userLocale" : "en",
	"userName" : "Andrey"
    });

    /**
     * Logout
     */

    $httpBackend.whenGET('/SocialNetworkApi/logout').respond({
	"response" : true
    });

    /**
     * Group mock
     */

    $httpBackend.whenGET('/SocialNetworkApi/group').respond([ {
	"name" : "Mock test",
	"chatId" : 5,
	"groupId" : 1,
	"users" : null,
	"groupAdmin" : true,
	"hidden" : false
    }, {
	"name" : "Mock test hidden",
	"chatId" : 5,
	"groupId" : 2,
	"users" : null,
	"groupAdmin" : true,
	"hidden" : true
    }, {
	"name" : "Mock test error delete",
	"chatId" : 5,
	"groupId" : 3,
	"users" : null,
	"groupAdmin" : true,
	"hidden" : false
    }, {
	"name" : "Mock test service error",
	"chatId" : 5,
	"groupId" : 4,
	"users" : null,
	"groupAdmin" : true,
	"hidden" : false
    } ]);

    $httpBackend.whenGET('/SocialNetworkApi/group/1').respond({
	"name" : "Mock test",
	"chatId" : 5,
	"groupId" : 1,
	"users" : [ {
	    "userId" : 1,
	    "fullName" : "Andrey Y",
	    "groupAdmin" : true
	}, {
	    "userId" : 2,
	    "fullName" : "Andrey P",
	    "groupAdmin" : false
	} ],
	"groupAdmin" : true
    });

    $httpBackend.whenPOST('/SocialNetworkApi/group').respond(function(method, url, obj) {
	console.log(obj);

	if (obj) {
	    return [ 200, {
		"groupId" : 10
	    } ]
	} else {
	    return [ 200, {
		"error" : 'no name'
	    } ]
	}

    });

    $httpBackend.whenPOST('/SocialNetworkApi/group/add_user').respond({
	"userId" : 3,
	"fullName" : "Dima D",
	"groupAdmin" : false
    });

    $httpBackend.whenPUT('/SocialNetworkApi/group/delete_user').respond({
	"userId" : 2,
	"fullName" : "Andrey P",
	"groupAdmin" : false
    });

    // Delete group
    $httpBackend.whenDELETE('/SocialNetworkApi/group/3').respond(function(method, url, obj) {

	return [ 200, {
	    "error" : 'Error delele grop'
	} ]
    });

    $httpBackend.whenDELETE('/SocialNetworkApi/group/4').respond(404, "HTTP ERROR");

    $httpBackend.whenDELETE('/SocialNetworkApi/group/1').respond({
	"groupId" : 1
    });

    $httpBackend.whenGET('/SocialNetworkApi/group/friends_not_in_group/1').respond([ {
	"userId" : 3,
	"fullName" : "Dima D",
	"groupAdmin" : false
    } ]);

    /**
     * Friends mock
     */

    $httpBackend.whenGET('/SocialNetworkApi/friend').respond([ {
	"userId" : 4,
	"name" : "Dima D",
	"status" : "INVITED",
	"chatId" : 2
    }, {
	"userId" : 5,
	"name" : "Dima R",
	"status" : "ACCEPTED",
	"chatId" : 1
    } ]);
    
    $httpBackend.whenPOST('/SocialNetworkApi/friend/inviteFriend/5').respond({
	"userId" : 5,
	"name" : "Dima D",
	"status" : "INVITED"
    });
    
    $httpBackend.whenPOST('/SocialNetworkApi/friend/acceptInvitation/5').respond();
    
    $httpBackend.whenPOST('/SocialNetworkApi/friend/declineInvitation/5').respond();
    
    $httpBackend.whenDELETE('/SocialNetworkApi/friend/5').respond();

    /**
     * Chat mock
     */
    $httpBackend.whenGET('/SocialNetworkApi/chat').respond([ {
	"name" : "Andrei P",
	"chatId" : 1
    }, {
	"name" : "Dima D",
	"chatId" : 2
    } ]);

    $httpBackend.whenGET('/SocialNetworkApi/chat/3').respond({
	"name" : "new Chat P",
	"chatId" : 3
    });

    $httpBackend.whenGET('/SocialNetworkApi/chat/getMessages/1').respond([ {
	"chatId" : 1,
	"messageId" : 1,
	"text" : "Andrey Y would like to add you on Social Network.",
	"date" : "2016-08-2518:53:25",
	"ownerId" : 1,
	"ownerName" : "Andrey Y",
	"hidden" : false,
	"messageInviteStatus" : "SYSTEM"
    }, {
	"chatId" : 1,
	"messageId" : 4,
	"text" : "Andrey P has shared contact details.",
	"date" : "2016-08-25 18:53:26",
	"ownerId" : 2,
	"ownerName" : "Andrey P",
	"hidden" : false,
	"messageInviteStatus" : "INVITE"
    }, {
	"chatId" : 1,
	"messageId" : 5,
	"text" : "",
	"date" : "2016-08-25 18:53:26",
	"ownerId" : 1,
	"ownerName" : "Andrey P",
	"hidden" : true,
	"messageInviteStatus" : null
    } ]);

    /*
     * $httpBackend.whenGET('/SocialNetworkApi/chat/getMessage').respond({
     * "chatId" : 3, "messageId" : 7, "text" : "new chat.", "date" : "2016-08-25
     * 18:53:26", "ownerId" : 1, "ownerName" : "Andrey P", "hidden" : false,
     * "messageInviteStatus" : null });
     */

    $httpBackend.whenGET('/SocialNetworkApi/chat/getMessage').respond({
	"chatId" : 1,
	"messageId" : 6,
	"text" : "Hi.",
	"date" : "2016-08-25 18:53:26",
	"ownerId" : 1,
	"ownerName" : "Andrey P",
	"hidden" : false,
	"messageInviteStatus" : null
    });

    $httpBackend.whenPOST('/SocialNetworkApi/chat/sendMessages').respond(function(method, url, obj) {
	console.log(obj);
	return [ 200, {
	    "chatId" : 3,
	    "messageId" : 7,
	    "text" : "new chat.",
	    "date" : "2016-08-25 18:53:26",
	    "ownerId" : 1,
	    "ownerName" : "Andrey P",
	    "hidden" : false,
	    "messageInviteStatus" : null
	} ]
    });

    /**
     * Profile mock
     */

    $httpBackend.whenGET('/SocialNetworkApi/profile').respond({
	"firstName" : "Andrei Y"
    });

    $httpBackend.whenGET('/SocialNetworkApi/profile/2').respond({
	"firstName" : "Andrei",
	"lastName" : "D",
	"friendStatus" : "ACCEPTED"
    });

    $httpBackend.whenGET('/SocialNetworkApi/profile/3').respond({
	"firstName" : "Dima",
	"lastName" : "S"
    });

    $httpBackend.whenGET('/SocialNetworkApi/profile/30').respond({
	"error" : "No row with the given identifier exists: [com.social.network.domain.model.User#20]"
    });

    $httpBackend.whenPOST('/SocialNetworkApi/profile').respond({
	"firstName" : "Andrei Y"
    });

    $httpBackend.whenPOST('/SocialNetworkApi/profile/search').respond([{
	"userId" : 10,
	"firstName" : "Dima",
	"lastName" : "Sam",
	"street" : "",
	"city" : "NY",
	"country" : "USA",
	"friendStatus" : "ACCEPTED"
    },{
	"userId" : 5,
	"firstName" : "Dima Loooong naaame",
	"lastName" : "Sam",
	"street" : "",
	"city" : "NY",
	"country" : "USA"	
    }]);
    
    /*$httpBackend.whenPOST('/SocialNetworkApi/profile/search').respond(function(method, url, obj) {
	return [ 401, {
	    "chatId" : 3,
	} ]
    });*/
    
}]);