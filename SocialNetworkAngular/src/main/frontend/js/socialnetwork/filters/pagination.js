angular.module('socialNetworkFilters', []).filter('pagination',function($log) {
	    return function(inputArray, selectedPage, pageSize) {
		$log.debug('socialNetworkFilters pagination : selectedPage ' + selectedPage + ', pageSize ' + pageSize);
		var start = selectedPage * pageSize;
		return inputArray.slice(start, start + pageSize);
	    };
	});