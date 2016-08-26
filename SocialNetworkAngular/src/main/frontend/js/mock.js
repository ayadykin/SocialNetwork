angular.module('socialNetworMockServerkApp', [ 'ngMockE2E' ]).run(function($httpBackend) {
    // 'ngMockE2E'
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

    $httpBackend.whenGET('../i18n/i18n_config.properties').respond({
	"en" : {
	    "id" : "en",
	    "code" : "en",
	    "name" : "English"
	},
	"ru" : {
	    "id" : "ru",
	    "code" : "ru",
	    "name" : "Русский"
	}
    });

    $httpBackend.whenGET('../i18n/ru.properties').respond({
	"code" : "en",
	"error_delete_group" : "Error delete group ",
	"success_delete_group" : "Success delete group ",
	"ADD_USER" : "Добавить друга",
	"FRIENDS" : "Друзья"
    });

    $httpBackend.whenGET('/SocialNetworkApi/signin').respond({});
    $httpBackend.whenPOST('/SocialNetworkApi/j_spring_security_check?j_password=user1&j_username=user1').respond({
	"login" : 'SUCCESS'
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
	"friendId" : 4,
	"name" : "Dima D",
	"status" : "INVITED",
	"chatId" : 2
    }, {
	"friendId" : 5,
	"name" : "Dima R",
	"status" : "ACCEPTED",
	"chatId" : 2
    } ]);

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

    $httpBackend.whenGET('/SocialNetworkApi/chat/1').respond([ {
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
	"messageInviteStatus" : "SYSTEM"
    } ]);
    /**
     * Profile mock
     */

    $httpBackend.whenGET('/SocialNetworkApi/profile').respond({
	"firstName" : "Andrei Y"
    });

    $httpBackend.whenGET('/SocialNetworkApi/profile/2').respond({
	"firstName" : "Andrei",
	"lastName" : "D"
    });

    $httpBackend.whenPOST('/SocialNetworkApi/profile').respond({
	"firstName" : "Andrei Y"
    });

});