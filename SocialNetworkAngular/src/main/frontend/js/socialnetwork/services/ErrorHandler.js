angular.module('socialNetworkServices').factory('ErrorHandler', function($translate, $log, $timeout, config) {
    return {
	result : function(result, type) {
	    var message;

	    if (result.error) {
		message = result.error;
	    } else {
		message = $translate.instant(service_Error);

	    }

	    $timeout(function() {
	    }, 3000);
	    return {
		type : type,
		message : message,
		show : true
	    };
	}
    };
});