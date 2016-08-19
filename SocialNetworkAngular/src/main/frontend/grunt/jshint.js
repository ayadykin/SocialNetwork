module.exports = function () {
    var buildPaths = function (names) {
        var paths = [];

        names.forEach(function (n) {
            paths.push('js/' + n + '/**/*.js');
            paths.push('!js/' + n + '/build/**/*.js');
            paths.push('!js/' + n + '/templates/**/*.js');
        });

        return paths;
    };

    return {
        options: {
            reporter: require('jshint-stylish'),
            browser: true,
            loopfunc: true,
            eqnull: true,
            eqeqeq: false
        },
        src: buildPaths([
            'socialnetwork',
            'group',
            'friend'
        ])
    };
};