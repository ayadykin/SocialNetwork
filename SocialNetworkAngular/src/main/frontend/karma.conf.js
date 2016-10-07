module.exports = function(config) {
    config.set({

	basePath : '',

	frameworks : [ 'jasmine' ],

	files : [
		'bower_components/jquery/dist/jquery.js',

		'bower_components/angular-bootstrap/ui-bootstrap.js',
		'bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
		'bower_components/angular-animate/angular-animate.js',
		'bower_components/angular-route/angular-route.js',
		'bower_components/angular-resource/angular-resource.js',
		'bower_components/angular-translate/angular-translate.js',
		'bower_components/angular-cookies/angular-cookies.js',
		'bower_components/angular-mocks/angular-mocks.js',
		'bower_components/restangular/dist/restangular.js',
		'../webapp/js/socialnetwork/**/app.js',
		'test/**/*Spec.js' ],

	reporters : [ 'progress' ]
    });
};