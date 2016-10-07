angular.module('socialNetworkServices').factory('resultDialog', function($translate, $uibModal, $log, config) {

    return {
	dialog : function(result, key) {
	    var templateUrl, message = $translate.instant(key);
	    if (result) {
		templateUrl = 'parts/success_dialog.html';
	    } else {
		templateUrl = 'parts/error_dialog.html';
	    }

	    var uibModalInstance = $uibModal.open({
		animation : true,
		templateUrl : templateUrl,
		size : 'sm',
		controller : 'SuccessDialogController',
		resolve : {
		    message : function() {
			return message;
		    }
		}
	    });
	    $log.info('Modal open,  message: ' + message);
	}
    };
});