function initRoutes($route) {
    var k, defaultRoutes = {
	'/friend' : {
	    templateUrl : 'pages/friend.html',
	},
	'/group' : {
	    templateUrl : 'pages/group.html'
	},
	'/chat' : {
	    templateUrl : 'pages/chat.html'
	},
	'/profile' : {
	    templateUrl : 'pages/editProfile.html'
	},
	'/search' : {
	    templateUrl : 'pages/searchProfile.html'
	},
	'/signin' : {
	    templateUrl : 'pages/signin.html'
	},
	'/signup' : {
	    templateUrl : 'pages/signup.html'
	},
	'' : {
	    templateUrl : 'pages/index.html'
	},
	'/home' : {
	    templateUrl : 'pages/homeSignedIn.html'
	}

    }, defaultUrl;

    for (k in defaultRoutes) {
	$route.routes[k] = angular.extend({
	    reloadOnSearch : true,
	    regexp : new RegExp('^' + k + '$')
	}, defaultRoutes[k]);

    }
}
