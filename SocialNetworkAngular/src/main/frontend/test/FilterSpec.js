describe('pagination filter', function() {
    var paginationFilter;
    
    beforeEach(module('socialNetworkFilters'));
    
    beforeEach(inject(function(_paginationFilter_) {
	paginationFilter = _paginationFilter_;
    }));
    
    it('should return a slice of the input array', function() {
	var input = [ 1, 2, 3, 4, 5, 6 ];
	expect(paginationFilter(input, 0, 2)).toEqual([ 1, 2 ]);
	expect(paginationFilter(input, 2, 2)).toEqual([ 5, 6 ]);
    });
    
    it('should return empty array for out-of bounds', function() {
	var input = [ 1, 2 ];
	expect(paginationFilter(input, 2, 2)).toEqual([]);
    });
});