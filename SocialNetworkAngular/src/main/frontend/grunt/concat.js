function getModuleTree(name) {
	var prefix = 'js/' + name + '/';

	return [ prefix + 'init.js', prefix + 'services/**/*.js',
			prefix + 'controllers/**/*.js',
			prefix + 'constants/**/*.js',
			prefix + 'services/**/*.js',
			prefix + 'helpers/**/*.js',
			prefix + 'directives/**/*.js',
			'../webapp/' + prefix + 'build/parts/*.js' ];
}

module.exports = function(grunt) {
	return {
		mainViewer : {
			src : [ 'bower_components/jquery/dist/jquery.js',
			        'bower_components/angular/angular.js',
					'bower_components/angular-route/angular-route.js',
					'bower_components/angular-resource/angular-resource.js',
					'bower_components/angular-cookies/angular-cookies.js']
					.concat(getModuleTree('socialnetwork').concat(
							[ '../webapp/js/friend/build/app.js' ])),
			dest : '../webapp/js/socialnetwork/build/app.js'
		},
		controllers: {
            files: {
                '../webapp/js/friend/controllers/build/ctrl.js': [
                    'js/friend/controllers/init.js',
                    'js/friend/controllers/src/**/*.js'
                ]
            }
		},
		friendViewer : {
			src : getModuleTree('friend').concat(
					[ '!js/friend/controllers/**/*.js',
					  '../webapp/js/friend/controllers/build/ctrl.js' ]),
			dest : '../webapp/js/friend/build/app.js'
		}
	}
}