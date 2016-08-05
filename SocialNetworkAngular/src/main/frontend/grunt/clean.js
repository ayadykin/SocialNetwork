module.exports = function(grunt) {
	var getConfig = function(name) {
		return {
			options : {
				force : true
			},
			src : [ '../webapp/js/' + name + '/build/**/*',
					'../webapp/js/' + name + '/controllers',
					'!../webapp/js/' + name + '/build/app.*' ]
		};
	};

	grunt.loadNpmTasks('grunt-contrib-clean');

	return {
		start : {
			options : {
				force : true
			},
			src : [ 'manifest.*', '../webapp/**/*', 'css/main.css' ]
		},
		mainViewer : getConfig('socialnetwork'),
		friendViewer : getConfig('friend')
	};
};