module.exports = function (grunt) {
    var htmlPath = '../webapp/html/',
        jadePath = 'jade/',
        files = {},
        debugFiles = {},
        appVersion = grunt.config.get('appVersion'),
        versionSuffix = appVersion ? ('.' + appVersion) : '';

    files[htmlPath + "socialnetwork.html"] = [jadePath + "socialnetwork.jade"];
    debugFiles[htmlPath + "socialnetwork-debug.html"] = [jadePath + "socialnetwork.jade"];

    return {
        files: {
            options: {
                data: {
                    versionSuffix: versionSuffix,
                    cssSuffix: versionSuffix,
                    jsSuffix: versionSuffix  + '.min'
                }
            },
            files: files
        },
        debug_files: {
            options: {
                pretty: true,
                data: {
                    versionSuffix: versionSuffix,
                    cssSuffix: versionSuffix,
                    jsSuffix: versionSuffix
                }
            },
            files: debugFiles
        }
    };
};