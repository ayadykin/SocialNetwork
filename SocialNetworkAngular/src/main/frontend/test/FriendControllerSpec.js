describe('FriendController', function() {
    var scope, controller, config, httpMock;

    beforeEach(module('socialNetworkApp', function($provide) {
	$provide.value('$log', console);
    }));

    beforeEach(inject(function($rootScope, $controller, _config_, $httpBackend) {
	scope = $rootScope;
	controller = $controller;
	httpMock = $httpBackend;
	config = _config_;
    }));

    it('get friends ', function() {

	controller('FriendController', {
	    '$scope' : scope
	});

	httpMock.expectGET(config.friendPath).respond(
		'[{"friendId":4,"userId":3,"name":"Dima D","status":"INVITED","chatId":2}]');

	httpMock.flush();

	scope.$apply();

	expect(scope.friends[0].friendId).toEqual(4);
	expect(scope.friends[0].userId).toEqual(3);

	scope.deleteFriend(3);

	httpMock.expectDELETE(config.friendPath).respond();

    });

});