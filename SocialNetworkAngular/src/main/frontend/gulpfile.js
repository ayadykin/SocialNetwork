var gulp = require('gulp');
var gutil = require('gulp-util');
var debug = require('gulp-debug');
var concat = require('./gulp/concat');
var clean = require('./gulp/clean');
var rename = require("gulp-rename");

gulp.task('default', ['jade', 'browserifyModues' ]);
gulp.task('production', ['jade', 'browserifyModues' ]);

// Clean
gulp.task('clean', function() {
    var clean = require('gulp-clean');
    gulp.src([ '../webapp/js/socialnetwork/build/*.js' ]).pipe(clean({
	force : true
    }));
});

// Concat controllers
gulp.task('controllersConcat', [ 'clean' ], function() {
    concat(gulp, gutil).controllers();
});

// Template Cache
gulp.task('templatecache', function() {
    var templatecache = require('./gulp/templatecache');

    templatecache(gulp, gutil).chatViewer();
    templatecache(gulp, gutil).friendViewer();
    templatecache(gulp, gutil).groupViewer();
    templatecache(gulp, gutil).profileViewer();
    templatecache(gulp, gutil).mainViewer();
});

// Concat Modules
gulp.task('concat', [ 'controllersConcat', 'templatecache' ], function() {
    concat().concatModule('chat');
    concat().concatModule('friend');
    concat().concatModule('group');
    concat().concatModule('profile');
});

// Main Module
gulp.task('mainViewer', [ 'concat' ], function() {
    concat().concatMainModule();

});

gulp.task('ngAnnotate', [ 'mainViewer' ], function() {
    var ngAnnotate = require('gulp-ng-annotate');

    gulp.src('../webapp/js/socialnetwork/build/app.js').pipe(debug({
	title : 'ngAnnotate:'
    })).pipe(rename('app.annotate.js')).pipe(ngAnnotate()).pipe(gulp.dest('../webapp/js/socialnetwork/build/'));
});

gulp.task('uglify', [ 'ngAnnotate' ], function() {
    var uglify = require('gulp-uglify');
    gulp.src('../webapp/js/socialnetwork/build/app.annotate.js').pipe(debug({
	title : 'uglify:'
    })).pipe(rename('app.min.js')).pipe(uglify()).pipe(gulp.dest('../webapp/js/socialnetwork/build/'));
});

// Jade
gulp.task('jade', [ 'uglify' ], function() {
    var jade = require('gulp-jade');

    gulp.src('jade/*.jade').pipe(jade({
	pretty : true,
	locals : {
	    jsSuffix : '.min'
	}
    })).pipe(gulp.dest('../webapp'))

    gulp.src('jade/*.jade').pipe(jade({
	pretty : true,
	locals : {
	    jsSuffix : ''
	},
    })).pipe(rename("socialnetwork-debug.html")).pipe(gulp.dest('../webapp'))
});

// Browserify Modules
gulp.task('browserifyModues', [], function() {
    var concat = require('gulp-concat-util');
    var browserify = require('gulp-browserify');
    gulp.src('js/modules.js').pipe(browserify()).pipe(concat('node_modules.js')).pipe(gulp.dest('../webapp/js'))
});