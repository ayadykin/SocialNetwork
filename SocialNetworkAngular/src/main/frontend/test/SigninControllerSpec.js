 describe('SigninController', function() {
    var scope, controller, config, httpMock;

    beforeEach(module('socialNetworkApp'));   
    
    beforeEach(module('socialNetworkApp'));   

    beforeEach(inject(function($rootScope, $controller, _config_, $httpBackend) {
    	scope = $rootScope;
        controller = $controller;
        httpMock = $httpBackend;
        config = _config_;
    }));

    // Test sign in
    it('Test sign in', function() {
    	
    	controller('SigninController', {'$scope': scope });
    	
		httpMock.expectGET(config.signinInit).respond();
    
        scope.login();
        httpMock.expectPOST(config.signinPath).respond("{\"login\" : \"SUCCESS\"}");
        
        httpMock.flush();
        scope.$apply();
        
        expect(scope.authenticated).toBe(true);
        
        scope.login();
        httpMock.expectPOST(config.signinPath).respond("{\"login\" : \"FALSE\"}");
        
        httpMock.flush();
        scope.$apply();
        
        expect(scope.authenticated).toBe(false);


    });
    
    
});