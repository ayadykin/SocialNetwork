var getConfig = function (name) {
        return {
            cwd: 'js/' + name,
            src: ['templates/**/*.html'],
            dest: '../webapp/js/' + name + '/build/parts/templates.js',
            options: {
                bootstrap: function(module, script) {
                    return "angular.module('socialNetworkApp').run(function($templateCache) {\n" +
                        script + '\n});';
                },
                htmlmin: {
                    collapseWhitespace: true
                }
            }
        };
    };

module.exports = function () {
    return {
        mainViewer: getConfig('socialnetwork'),
        friendViewer: getConfig('friend'),
        groupViewer: getConfig('group')
    };
};