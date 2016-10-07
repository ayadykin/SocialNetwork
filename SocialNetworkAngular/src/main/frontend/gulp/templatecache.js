var getConfig = function(gulp, gutil, name) {
    gutil.log('templatecache : ' + name);
    var templateCache = require('gulp-angular-templatecache');
    return gulp.src('js/' + name + '/templates/**/*.html').pipe(templateCache('templates.js', {
	module : 'socialNetworkApp'
    })).pipe(gulp.dest('../webapp/js/' + name + '/build/parts'));
};

module.exports = function(gulp, gutil) {

    return {
	chatViewer : function() {
	    getConfig(gulp, gutil, 'chat');
	},
	friendViewer : function() {
	    getConfig(gulp, gutil, 'friend');
	},
	groupViewer : function() {
	    getConfig(gulp, gutil, 'group');
	},
	profileViewer : function() {
	    getConfig(gulp, gutil, 'profile');
	},
	mainViewer : function() {
	    getConfig(gulp, gutil, 'socialnetwork');
	}
    };
};