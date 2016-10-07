function getModuleTree(name) {
    var prefix = 'js/' + name + '/';

    return [
	    prefix + 'init.js',
	    prefix + 'services/**/*.js',
	    prefix + 'controllers/**/*.js',
	    prefix + 'constants/**/*.js',
	    prefix + 'services/**/*.js',
	    prefix + 'helpers/**/*.js',
	    prefix + 'filters/**/*.js',
	    prefix + 'directives/**/*.js',
	    '../webapp/' + prefix + 'build/parts/*.js' ];
}

module.exports = function(grunt) {
    return {
	mainViewer : {
	    src : [
		    'bower_components/jquery/dist/jquery.js',
		    'bower_components/angular-bootstrap/ui-bootstrap.js',
		    'bower_components/angular-bootstrap/ui-bootstrap-tpls.js',

		    'bower_components/angular-translate/angular-translate.js',
		    'bower_components/angular-scroll-glue/src/scrollglue.js']
    		.concat(getModuleTree('socialnetwork')
		    .concat(
			    [
				    '../webapp/js/friend/build/app.js',
				    '../webapp/js/group/build/app.js',
				    '../webapp/js/chat/build/app.js',
				    '../webapp/js/profile/build/app.js' ])),
	    dest : '../webapp/js/socialnetwork/build/app.js'
	},
	snServer : {
	    src : [
		    '../webapp/js/socialnetwork/build/app.js',
		    'bower_components/angular-mocks/angular-mocks.js',
		    'js/mock.js' ],
	    dest : '../webapp/js/socialnetwork/build/app.js'
	},
	controllers : {
	    options : {
		splitter : '\n',
		footer : '\n});',
		process : function(src, path) {
		    if ((/init\.js/).test(path)) {
			src = src.replace(/\s*\}\s*\)\s*\;\s*$/g, '');
		    }

		    return src;
		}
	    },
	    files : {
		'../webapp/js/friend/controllers/build/ctrl.js' : [
			'js/friend/controllers/init.js',
			'js/friend/controllers/src/*.js' ],
		'../webapp/js/group/controllers/build/ctrl.js' : [
			'js/group/controllers/init.js',
			'js/group/controllers/src/*.js' ],
		'../webapp/js/chat/controllers/build/ctrl.js' : [
			'js/chat/controllers/init.js',
			'js/chat/controllers/src/*.js' ],
		'../webapp/js/profile/controllers/build/ctrl.js' : [
			'js/profile/controllers/init.js',
			'js/profile/controllers/src/*.js' ]
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
	},
	chatViewer : {
	    src : getModuleTree('chat').concat(
		    [ '!js/chat/controllers/**/*.js', '../webapp/js/chat/controllers/build/ctrl.js' ]),
	    dest : '../webapp/js/chat/build/app.js'
	},
	profileViewer : {
	    src : getModuleTree('profile').concat(
		    [ '!js/profile/controllers/**/*.js', '../webapp/js/profile/controllers/build/ctrl.js' ]),
	    dest : '../webapp/js/profile/build/app.js'
	}
    }
}