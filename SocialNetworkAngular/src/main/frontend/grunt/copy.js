module.exports = function(grunt) {

	grunt.loadNpmTasks('grunt-contrib-copy');

	return {
		main : {
			files : [

			{
				expand : true,
				src : [ 'css/login.css' ],
				dest : '../webapp/resources/css/'
			}, {
				expand : true,
				src : [ 'WEB-INF/**' ],
				dest : '../webapp/'
			}, {
				expand : true,
				src : [ 'html/*.html' ],
				dest : '../webapp/'
			} ]
		}
	};

};