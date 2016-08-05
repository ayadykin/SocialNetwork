angular.module('socialNetworkControllers').controller('SigninController',
		function($rootScope, $scope, $location,  Signin ) {

	  Signin.get().$promise.then(function(data) {
		  if(data.response){
			  $rootScope.token = true;
		  }else{
			  $rootScope.token = false;
		  }
	  });
	  
	  $scope.login = function() {
		  console.log("login ");
		  Signin.post({j_username: $scope.username, j_password : $scope.password}).$promise.then(function(data) {
		      
			  if (data.login == 'SUCCESS') {
		        $rootScope.authenticated = true;
		        $location.path("/home");
		          $scope.error = false;
		      } else {
		        $rootScope.authenticated = false;
		        $location.path("/signin");
		          $scope.error = true;
		      }
			  console.log("login : " + data.login);
		    });
	  };
	});