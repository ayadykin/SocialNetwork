module.exports = function(grunt) {
	var options = {
		// for Angular.js
		mangle : false
	}, deployDir = '../webapp/';

	return {
		sn : {
			options : options,
			'src' : [ deployDir + 'js/socialnetwork/build/app.js' ],
			'dest' : deployDir + 'js/socialnetwork/build/app.min.js'
		},
		friendViewer : {
			options : options,
			'src' : [ deployDir + 'js/friend/build/app.js' ],
			'dest' : deployDir + 'js/friend/build/app.min.js'
		},
		groupViewer : {
			options : options,
			'src' : [ deployDir + 'js/group/build/app.js' ],
			'dest' : deployDir + 'js/group/build/app.min.js'
		},
		profileViewer : {
			options : options,
			'src' : [ deployDir + 'js/profile/build/app.js' ],
			'dest' : deployDir + 'js/profile/build/app.min.js'
		}
	};
};
