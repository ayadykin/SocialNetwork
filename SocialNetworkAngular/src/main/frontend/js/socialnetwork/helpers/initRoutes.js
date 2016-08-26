function initRoutes($route) {
    var k, defaultRoutes = {
	'/friend' : {
	    templateUrl : 'templates/pages/friend.html',
	},
	'/group' : {
	    templateUrl : 'templates/pages/group.html'
	},
	'/chat' : {
	    templateUrl : 'templates/pages/chat.html'
	},
	'/profile' : {
	    templateUrl : 'templates/pages/profile.html'
	},
	'/signin' : {
	    templateUrl : 'templates/pages/signin.html'
	},
	'/signup' : {
	    templateUrl : 'templates/pages/signup.html'
	},
	'' : {
	    templateUrl : 'templates/pages/home.html'
	},
	'/home' : {
	    templateUrl : 'templates/pages/homeSignedIn.html'
	}

    }, defaultUrl;

    for (k in defaultRoutes) {
	$route.routes[k] = angular.extend({
	    reloadOnSearch : true,
	    regexp : new RegExp('^' + k + '$')
	}, defaultRoutes[k]);

    }
}
