angular.module('socialNetworkAppM', [ 'ngMockE2E' ]).run(function($httpBackend) {
    // 'ngMockE2E'
    $httpBackend.whenGET('../i18n/en.properties').respond({
	"code" : "en",
	"error_delete_group" : "Error delete group ",
	"success_delete_group" : "Success delete group ",
	"ADD_USER" : "Add user",
	"FRIENDS" : "Friends"
    });

    $httpBackend.whenGET('../i18n/no.properties').respond(function(method, url, obj) {
	return [ 400, {
	    "error" : 'error load file '
	} ]
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

    $httpBackend.whenDELETE('/SocialNetworkApi/group/1').respond({
	"response" : true
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

});