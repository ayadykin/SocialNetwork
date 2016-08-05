module.exports = function(grunt) {
	
	grunt.loadNpmTasks('grunt-karma');
	return {
		unit : {
			configFile : 'karma.conf.js',
			port : 9999,
			singleRun : false,
			browsers : [ 'Firefox' ],
			logLevel : 'DEBUG',
			colors: true
		}
	}
}