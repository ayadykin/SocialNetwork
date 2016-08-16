 describe('GroupController', function() {
    var scope, controller, config, httpMock;

    beforeEach(module('socialNetworkApp'));   

    beforeEach(inject(function($rootScope, $controller, _config_, $httpBackend) {
    	scope = $rootScope;
        controller = $controller;
        httpMock = $httpBackend;
        config = _config_;

    }));

    it('get groups ', function() {

    	controller('GroupController', {'$scope': scope });
    	
    	httpMock.expectGET(config.groupPath).respond('[{"name":"test","chatId":5,"groupId":1,"users":null,"groupAdmin":true,"hidden":false}]');     

    	httpMock.flush();

        scope.$apply();

        expect(scope.groups[0].groupId).toEqual(1);
        expect(scope.groups[0].chatId).toEqual(5);
        
        scope.deleteGroup(5);
        
        httpMock.expectDELETE(config.groupPath).respond();
        
    });

});