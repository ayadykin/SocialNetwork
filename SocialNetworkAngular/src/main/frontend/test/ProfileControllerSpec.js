describe('ProfileController', function() {
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

    it('get  ', function() {

    });

});