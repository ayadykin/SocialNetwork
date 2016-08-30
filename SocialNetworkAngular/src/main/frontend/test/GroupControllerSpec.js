describe(
	'GroupController',
	function() {
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

		controller('GroupController', {
		    '$scope' : scope
		});

		httpMock
			.expectGET(config.groupPath)
			.respond(
				'[{"name":"test","chatId":5,"groupId":1,"users":[{"userId":1}, {"userId":2}],"groupAdmin":true,"hidden":false}]');

		httpMock.flush();
		// scope.$apply();
	    }));

	    afterEach(function() {
		httpMock.verifyNoOutstandingExpectation();
		httpMock.verifyNoOutstandingRequest();
	    });

	    it('delete group ', function() {

		expect(scope.groups[0].groupId).toEqual(1);
		expect(scope.groups[0].chatId).toEqual(5);
		expect(scope.groups.length).toEqual(1);

		// delete group
		scope.deleteGroup(1);
		httpMock.expectDELETE(config.groupPath + '/1').respond(
			'{"name":"test","chatId":5,"groupId":1,"users":null,"groupAdmin":true,"hidden":false}');

		httpMock.flush();
		// scope.$apply();

		expect(scope.groups.length).toEqual(0);

	    });

	    it('delete group error ', function() {

		dump(scope.notificationOptions);

		// delete group
		scope.deleteGroup(2);
		httpMock.expectDELETE(config.groupPath + '/2').respond('{"error":"Error delete group"}');

		httpMock.flush();
		scope.$apply();

		expect(scope.groups.length).toEqual(1);

	    });

	    it('delete group service error ', function() {

		dump(scope.notificationOptions);

		// delete group
		scope.deleteGroup(3);
		httpMock.expectDELETE(config.groupPath + '/3').respond(404, "HTTP ERROR");

		httpMock.flush();
		// scope.$apply();

		expect(scope.groups.length).toEqual(1);

	    });

	    it('add group ', function() {

		scope.initCreateGroup();
		httpMock.expectGET(config.friendPath).respond(
			'[{"userId" : 3,"fullName" : "Dima D","groupAdmin" : false}]');

		httpMock.flush();
		// scope.$apply();

		expect(scope.friends.length).toEqual(1);
		expect(scope.groups.length).toEqual(1);

		scope.group.name = 'test';
		scope.saveGroup();
		httpMock.expectPOST(config.groupPath).respond(
			'{"name":"test2","chatId":5,"groupId":4,"users":null,"groupAdmin":true,"hidden":false}');

		httpMock.flush();
		// scope.$apply();

		// dump(scope.groups);

		expect(scope.groups.length).toEqual(2);
	    });

	    it(
		    'add user to group ',
		    function() {

			scope.initEditGroup(1);

			httpMock.expectGET(config.friendPath).respond([ {
			    "userId" : 3,
			    "fullName" : "Dima D",
			    "groupAdmin" : false
			}, {
			    "userId" : 4
			}, {
			    "userId" : 2
			} ]);

			httpMock
				.expectGET(config.groupPath + '/1')
				.respond(
					'{"name":"test","chatId":5,"groupId":1,"users":[{"userId" : 1,"fullName" : "Andrei Y","groupAdmin" : true}, {"userId" :2 }],"groupAdmin":true,"hidden":false}');

			scope.addUserToGroup(1, 3);
			httpMock.expectPOST(config.addUserToGroupPath).respond(
				'{"userId" : 3, "fullName" : "Dima D","groupAdmin" : false}');

			httpMock.flush();
			// scope.$apply();

			expect(scope.group.users.length).toEqual(3);
		    });

	});