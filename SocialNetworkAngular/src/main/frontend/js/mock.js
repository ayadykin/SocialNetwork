angular.module('socialNetworkApp')
.config(function($provide) {
    $provide.decorator('$httpBackend', angular.mock.e2e.$httpBackendDecorator);

    var DELAY_MS = 2000;
    $provide.decorator('$httpBackend', function($delegate) {
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
    });
})
.run(function($httpBackend) {
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

    $httpBackend.whenPOST('/SocialNetworkApi/profile/search').respond([ {
	"userId" : 10,
	"firstName" : "Dima",
	"lastName" : "Sam",
	"street" : "",
	"city" : "NY",
	"country" : "USA",
	"friendStatus" : "ACCEPTED"
    }, {
	"userId" : 5,
	"firstName" : "Dima Loooong naaame",
	"lastName" : "Sam",
	"street" : "",
	"city" : "NY",
	"country" : "USA"
    } ]);

    /*
     * $httpBackend.whenPOST('/SocialNetworkApi/profile/search').respond(function(method,
     * url, obj) { return [ 401, { "chatId" : 3, } ] });
     */

});