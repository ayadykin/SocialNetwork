function initPagesPermissions($route) {
	var k, defaultRoutes = {
		'/friend' : {
			templateUrl : 'templates/pages/friend.html'
		},
		'/group' : {
			templateUrl : 'templates/pages/group.html'
		},
		'/createGroup' : {
			templateUrl : 'templates/pages/createGroup.html'
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
		$route.routes[k] = angular.extend(getRouteOptions({
			url : k
		}), defaultRoutes[k]);
	}

	$route.reload();
}
function getRouteOptions(params) {
	return angular.extend({
		reloadOnSearch : true
	}, pathRegExp(params.url, {}));
}

/**
 * @description From angular-route.js
 * @param path
 * @param opts
 * @returns {{originalPath: *, regexp: *}}
 */
function pathRegExp(path, opts) {
	var insensitive = opts.caseInsensitiveMatch, ret = {
		originalPath : path,
		regexp : path
	}, keys = ret.keys = [];

	path = path.replace(/([().])/g, '\\$1').replace(
			/(\/)?:(\w+)([\?\*])?/g,
			function(_, slash, key, option) {
				var optional = option === '?' ? option : null;
				var star = option === '*' ? option : null;
				keys.push({
					name : key,
					optional : !!optional
				});
				slash = slash || '';
				return '' + (optional ? '' : slash) + '(?:'
						+ (optional ? slash : '')
						+ (star && '(.+?)' || '([^/]+)') + (optional || '')
						+ ')' + (optional || '');
			}).replace(/([\/$\*])/g, '\\$1');

	ret.regexp = new RegExp('^' + path + '$', insensitive ? 'i' : '');
	return ret;
}
