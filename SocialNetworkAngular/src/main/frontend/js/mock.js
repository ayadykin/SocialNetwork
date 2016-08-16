angular.module('socialNetworkApp', ['ngMockE2E']).run(function($httpBackend) {

    $httpBackend.whenGET('../i18n/en-us.properties').respond({

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
	"groupId" : 1,
	"users" : null,
	"groupAdmin" : true,
	"hidden" : true
    } ]);

    $httpBackend.whenPOST('/SocialNetworkApi/group').respond(function(method, url, obj) {
	console.log(obj);

	return {
	    "response" : true
	};
    });

    $httpBackend.whenDELETE('/SocialNetworkApi/group/1').respond({
	"response" : true
    });

    $httpBackend.whenGET('/SocialNetworkApi/group/friends_not_in_group/1').respond({
	"response" : true
    });

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

    /*
     * $httpBackend.whenPOST(/\/merchant-manager\/general\/merchant\/document\/incorporationCertificate\?/).respond(
     * function(method, url, obj) { console.log(arguments);
     * 
     * return [ 200, { "returnCode" : "SUCCESS", "internalStatusCode" : 0,
     * "data" : {} } ]; });
     * 
     * $httpBackend.whenPOST(/\/merchant-manager\/general\/merchant\/document\/directorsPassportsAndIds\?/).respond(
     * function(method, url, obj) { console.log(arguments);
     * 
     * return [ 200, { "returnCode" : "SUCCESS", "internalStatusCode" : 0,
     * "data" : {} } ]; });
     */

});