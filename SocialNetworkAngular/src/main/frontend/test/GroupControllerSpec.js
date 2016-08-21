describe('GroupController', function() {
    var scope, controller, config, httpMock, location;

    beforeEach(module('socialNetworkApp', function($provide) {
	$provide.value('$log', console);
    }));

    beforeEach(inject(function($rootScope, $controller, _config_, $httpBackend, $location) {
	scope = $rootScope;
	controller = $controller;
	httpMock = $httpBackend;
	config = _config_;
	location = $location;
	scope.successDialog = function() {
	};
    }));

    it('get groups ', function() {

	controller('GroupController', {
	    '$scope' : scope
	});
	// Stub dialog

	httpMock.expectGET(config.groupPath).respond(
		'[{"name":"test","chatId":5,"groupId":1,"users":null,"groupAdmin":true,"hidden":false}]');

	httpMock.flush();
	scope.$apply();
	
	dump(scope.groups);

	expect(scope.groups[0].groupId).toEqual(1);
	expect(scope.groups[0].chatId).toEqual(5);
	expect(scope.groups.length).toEqual(1);

	// add group

	// delete group
	scope.deleteGroup(1);
	httpMock.expectDELETE(config.groupPath + '/1').respond(
		'{"name":"test","chatId":5,"groupId":1,"users":null,"groupAdmin":true,"hidden":false}');

	httpMock.flush();
	scope.$apply();

	expect(scope.groups.length).toEqual(0);

    });

    it('add group ', function() {

	controller('GroupController', {
	    '$scope' : scope
	});
	debugger;
	httpMock.expectGET(config.groupPath).respond(
		'[{"name":"test","chatId":5,"groupId":1,"users":null,"groupAdmin":true,"hidden":false}]');

	scope.initCreateGroup();
	httpMock.expectGET(config.friendPath).respond('[{"userId" : 3,"fullName" : "Dima D","groupAdmin" : false}]');

	httpMock.flush();
	scope.$apply();

	expect(scope.friends.length).toEqual(1);

	scope.groupName = 'test';
	scope.saveGroup();
	httpMock.expectPOST(config.groupPath).respond(
		'{"name":"test","chatId":5,"groupId":4,"users":null,"groupAdmin":true,"hidden":false}');

	httpMock.flush();
	scope.$apply();
	
	//dump(scope);
	
	expect(scope.groups.length).toEqual(2);
    });

});