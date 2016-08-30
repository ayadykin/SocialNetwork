describe('myDir directive', function() {
    var element, scope;

    beforeEach(module('socialNetworkApp'));

    beforeEach(inject(function($compile, $rootScope) {
	var linkingFn = $compile('<my-dir></my-dir>');
	scope = $rootScope;
	element = linkingFn(scope);
    }));

    it('has some properties', function() {
	//expect(element.someMethod()).toBe(XXX);
    });

    it('does something to the scope', function() {
	//expect(scope.someField).toBe(XXX);
    });

});
