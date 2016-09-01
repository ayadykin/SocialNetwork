angular.module('socialNetworkServices').factory('restErrorInterceptor',
	function($translate, $log, $rootScope, $timeout) {
	    return {

		'response' : function(response) {
		    if (response.data.error) {
			$log.error("restErrorInterceptor : " + response.data.error);
			$rootScope.notification.message = response.data.error;
			$rootScope.notification.error = true;
			
			$timeout(function() {
				$rootScope.notification.message = '';
				$rootScope.notification.error = '';
			    }, 5000);
			return {};
		    }
		    return response;
		},
		'responseError' : function(error) {
		    $log.error("responseServerError  : " + error);		 
		    $rootScope.notification.message = "Server error";
		    $rootScope.notification.error = true;
		    return {};
		}
	    };
	});