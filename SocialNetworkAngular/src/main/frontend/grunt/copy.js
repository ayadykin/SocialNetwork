module.exports = function(grunt) {

	grunt.loadNpmTasks('grunt-contrib-copy');

	return {
		main : {
			files : [

			{
				expand : true,
				src : [ 'css/*.css' ],
				dest : '../webapp/'
			}, {
				expand : true,
				src : [ 'WEB-INF/**' ],
				dest : '../webapp/'
			}, {
				expand : true,
				src : [ 'html/*.html' ],
				dest : '../webapp/'
			}, {
				expand : true,
				src : [ 'i18n/*.properties' ],
				dest : '../webapp/'
			} ]
		}
	};

};