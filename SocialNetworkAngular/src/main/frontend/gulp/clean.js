module.exports = function(gulp, name) {
var clean = require('gulp-clean');
    return 
	    gulp.src('../../webapp/js/socialnetwork/build/app.js').pipe(clean({
		force : true
	    }));

};

var getConfig = function(gulp, name) {
    
    
};