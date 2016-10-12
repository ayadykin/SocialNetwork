var gulp = require('gulp');
var gutil = require('gulp-util');
var concat = require('gulp-concat-util');

module.exports = function() {

    return {
	controllers : function() {
	    concatController('chat');
	    concatController('friend');
	    concatController('group');
	    concatController('profile');
	},
	concatModule : function(name) {
	    var path = getModuleTree(name);
	    path.push('../webapp/js/' + name + '/controllers/build/ctrl.js');
	    gutil.log(name +' concatModule: ' + path);
	    gulp.src(path).pipe(concat('app.js')).pipe(gulp.dest('../webapp/js/' + name + '/controllers/build/'));
	},

	concatMainModule : function() {
	    var path = getModuleTree('socialnetwork');
	    path.push('../webapp/js/friend/controllers/build/app.js', '../webapp/js/group/controllers/build/app.js',
		    '../webapp/js/chat/controllers/build/app.js', '../webapp/js/profile/controllers/build/app.js');
	    path.push('js/mock.js');

	    return gulp.src(path).pipe(concat('app.js')).pipe(gulp.dest('../webapp/js/socialnetwork/build/'));

	},
	serverMock : function() {
	    gulp.src('js/mock.js').pipe(concat('app.js')).pipe(gulp.dest('../webapp/js/socialnetwork/build/'));
	}
    };

    function getModuleTree(name) {
	var prefix = 'js/' + name + '/';

	return [
		prefix + 'init.js',
		prefix + 'services/**/*.js',
		prefix + 'constants/**/*.js',
		prefix + 'services/**/*.js',
		prefix + 'helpers/**/*.js',
		prefix + 'filters/**/*.js',
		prefix + 'directives/**/*.js',
		'../webapp/' + prefix + 'build/parts/templates.js' ];
    }

    function concatController(name) {
	gutil.log('controllers concat : ' + name);

	gulp.src('js/' + name + '/controllers/**/*.js').pipe(concat('ctrl.js', {
	    process : function(src, path) {
		if ((/init\.js/).test(path)) {
		    src = src.replace(/\s*\}\s*\)\s*\;\s*$/g, '');
		}
		return src;
	    }
	})).pipe(concat.footer('\n});')).pipe(gulp.dest('../webapp/js/' + name + '/controllers/build/'));
    }
    ;

};