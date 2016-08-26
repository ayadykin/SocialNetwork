angular.module('socialNetworkServices').factory('ErrorHandler', function($translate, $timeout, $log, config) {
    return {
	notify : function($scope, result) {
	    var message, resp;

	    if (result.error) {
		message = result.error;
		withTimeout();
	    } else {
		message = $translate.instant('service_Error');
	    }
	    $log.error('error: ' + message);

	    $scope.$emit('ErrorHandler', {
		message : message,
		show : true
	    });

	    function withTimeout() {
		$timeout(function() {
		    $scope.$emit('ErrorHandler', null);
		}, 3000);
	    }
	}
    };
});