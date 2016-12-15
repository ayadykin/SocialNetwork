angular.module('socialNetworkServices').factory('restErrorInterceptor',
	function($translate, $log, $rootScope, $timeout, $location) {
	    return {

		'response' : function(response) {
		    if (response.data.error) {
			$log.error("restErrorInterceptor : " + response.data.error);
			$rootScope.notification.message = response.data.error;
			$rootScope.notification.error = true;

			$timeout(function() {
			    $rootScope.notification.message = '';
			    $rootScope.notification.error = '';
			    if (response.data.status === 401) {
				$log.error("restErrorInterceptor response status : " + response.data.status);
				$location.path("/signin");
			    }
			}, 5000);
			return {};
		    }
		    return response;
		},
		'responseError' : function(error) {
		    if (error.status === 401) {
			$log.error("restErrorInterceptor response status : " + response.status);
			$location.path("/signin");
		    }else{		    
        		    $log.error("responseServerError  : " + angular.toJson(error));
        
        		    $rootScope.notification.message = "Server error";
        		    $rootScope.notification.error = true;
		    }
		    return {};

		}
	    };
	});