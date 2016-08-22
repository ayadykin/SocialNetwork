angular.module('socialNetworkControllers').controller('AppController',
	function($q, $scope, $rootScope, $route, config, $uibModal, $log, $http, $locale, $translate) {
	    initPagesPermissions($route);

	    loadFile('en');

	    $scope.changeLocale = function(locale) {
		loadFile(locale);
	    };

	    $rootScope.successDialog = function(result, key) {
		var templateUrl, message = $translate.instant(key);
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

	    function loadFile(key) {
		$q.when($translate.use(key)).then(function(data) {
		    $log.info("AppController load lang :" + data);
		}, function(data) {
		    loadFile("en");
		    $log.error("AppController load lang :" + data);
		});
	    }
	});