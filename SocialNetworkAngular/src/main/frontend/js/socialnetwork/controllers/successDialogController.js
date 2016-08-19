angular.module('socialNetworkControllers').controller('SuccessDialogController',
	function($uibModalInstance, $scope, message) {

	    $scope.message = message;
	    $scope.ok = function() {
		$uibModalInstance.close();
	    };
	});