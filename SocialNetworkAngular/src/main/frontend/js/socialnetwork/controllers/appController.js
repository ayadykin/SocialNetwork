angular.module('socialNetworkControllers').controller('AppController',
	function($q, $scope, $rootScope, $route, config, $uibModal, $log, $http, $locale, $translate) {
	    initPagesPermissions($route);

	    $q.all([ $translate.use("en") ]).then(function(data) {
	    });

	    $scope.changeLocale = function(locale) {
		$q.all([ $translate.use(locale) ]).then(function(data) {
		});
	    };

	    $rootScope.successDialog = function(result, key) {
		var templateUrl,
		message = $translate.instant(key);
		if (result) {
		    templateUrl = 'templates/parts/success_dialog.html';
		} else {
		    templateUrl = 'templates/parts/error_dialog.html';
		}

		var uibModalInstance = $uibModal.open({
		    animation : true,
		    templateUrl : templateUrl,
		    controller : 'SuccessDialogController',
		    size : 'sm',
		    resolve : {
			message : function() {
			    return message;
			}
		    }
		});
		$log.info('Modal open,  message: ' + message);
	    };
	});