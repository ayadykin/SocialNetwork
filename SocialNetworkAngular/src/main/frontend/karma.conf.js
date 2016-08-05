module.exports = function(config) {
  config.set({

    basePath: '',

    frameworks: ['jasmine'],

    files: [
      'bower_components/jquery/dist/jquery.js',
      'bower_components/angular/angular.js',
      'bower_components/angular-route/angular-route.js',
      'bower_components/angular-resource/angular-resource.js',
      'bower_components/angular-cookies/angular-cookies.js',
      'bower_components/angular-mocks/angular-mocks.js',
      'bower_components/restangular/dist/restangular.js',
      'js/socialnetwork/init.js',
      'js/socialnetwork/**/*.js',
      'js/friend/**/*.js',
      'test/**/*Spec.js'
    ],
    
    reporters: ['progress']
  });
};