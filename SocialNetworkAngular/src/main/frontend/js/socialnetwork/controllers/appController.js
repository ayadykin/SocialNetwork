angular.module('socialNetworkControllers').controller('AppController',
	function($scope, $rootScope, $route, config, $uibModal, $log, $http, $locale) {
	    initPagesPermissions($route);

	    $log.info("locale : " + $locale.id);

	    $scope.successDialog = function(result, message) {
		var templateUrl;
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
	    }

	    // Load messages
	    $http.get('../i18n/' + $locale.id + '.properties').then(function(res) {
		$scope.messages = res.data;
		$log.info($scope.messages);
	    });
	});