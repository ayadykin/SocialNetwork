function initDatePicker($scope) {
    // Date options
    $scope.dateFilter = new Date();
    $scope.popup = {
	opened : false
    };
    $scope.dateOptions = {
	maxDate : new Date()
    };
    $scope.openDatePicker = function() {
	$scope.popup.opened = true;
    };
}
