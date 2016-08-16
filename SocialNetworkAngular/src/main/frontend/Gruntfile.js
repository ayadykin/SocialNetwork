module.exports = function(grunt) {

	require('time-grunt')(grunt);

	grunt.log.warn('Version - ' + grunt.config.get('appVersion'));
	grunt.initConfig({});

	var socialNetworkTasks = [ 'clean:start', 'concat:controllers', 'groupViewer', 'friendViewer', 'mainViewer',
			'jade:files', 'jade:debug_files', 'copy' ];

	var taskList = {
		mainViewer : [ 'ngtemplates:mainViewer', 'concat:mainViewer',"clean:mainViewer" ],
		friendViewer : [ 'ngtemplates:friendViewer', 'concat:friendViewer','uglify:friendViewer',"clean:friendViewer" ],
		groupViewer : [ 'ngtemplates:groupViewer', 'concat:groupViewer','uglify:groupViewer',"clean:groupViewer" ],
		"sn-debug" : socialNetworkTasks,
		"sn-debug:server": ['sn-debug', 'concat:snServer'],
		sn: socialNetworkTasks.concat(['uglify:sn']),
		"karma" : ['karma:unit:run']
	};

	for (t in taskList) {
		grunt.registerTask(t, taskList[t]);
	}

	grunt.loadNpmTasks('grunt-angular-templates');
	grunt.loadNpmTasks('grunt-contrib-concat');
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-contrib-jade');

	require('grunt-config-dir')(grunt, {
		configDir : require('path').resolve(__dirname + '/grunt'),
		fileExtensions : [ 'js' ]
	}, function(err) {
		grunt.log.error('grunt-config-dir' + err);
	});


}