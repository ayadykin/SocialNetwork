function getModuleTree(name) {
    var prefix = 'js/' + name + '/';

    return [
	    prefix + 'init.js',
	    prefix + 'services/**/*.js',
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
	    src : [
		    'bower_components/jquery/dist/jquery.js',
		    'bower_components/angular/angular.js',
		    'bower_components/angular-bootstrap/ui-bootstrap.js',
		    'bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
		    'bower_components/angular-animate/angular-animate.js',
		    'bower_components/angular-touch/angular-touch.js',
		    'bower_components/angular-route/angular-route.js',
		    'bower_components/angular-resource/angular-resource.js',
		    'bower_components/angular-cookies/angular-cookies.js' ].concat(getModuleTree('socialnetwork')
		    .concat([ '../webapp/js/friend/build/app.js', '../webapp/js/group/build/app.js' ])),
	    dest : '../webapp/js/socialnetwork/build/app.js'
	},
	snServer : {
	    src : [ '../webapp/js/socialnetwork/build/app.js',
	    'bower_components/angular-mocks/angular-mocks.js',
	    'bower_components/jquery/dist/jquery.js',
	    'js/mock.js' ],
	    dest : '../webapp/js/socialnetwork/build/app.js'
	},
	controllers : {
	    files : {
		'../webapp/js/friend/controllers/build/ctrl.js' : [ 'js/friend/controllers/**/*.js' ],
		'../webapp/js/group/controllers/build/ctrl.js' : [ 'js/group/controllers/**/*.js' ]
	    }
	},
	friendViewer : {
	    src : getModuleTree('friend').concat(
		    [ '!js/friend/controllers/**/*.js', '../webapp/js/friend/controllers/build/ctrl.js' ]),
	    dest : '../webapp/js/friend/build/app.js'
	},
	groupViewer : {
	    src : getModuleTree('group').concat(
		    [ '!js/group/controllers/**/*.js', '../webapp/js/group/controllers/build/ctrl.js' ]),
	    dest : '../webapp/js/group/build/app.js'
	}
    }
}