angular.module('socialNetworkServices').factory('i18nLoader',
	function($q, $http, $translate, $cacheFactory, $log, config) {

	    var cacheName = config.i18nCacheKey, cache = $cacheFactory.get(cacheName);

	    if (!cache) {
		cache = $cacheFactory(cacheName);
	    }

	    return function(options) {
		options = options || {};

		$log.info("options : " + options.key);

		var deferred = $q.defer(), key = options.key, data = cache.get(key);

		if(!key){
		    key = 'en';  
		}
		
		if (data) {
		    complete(data, deferred, options);
		} else {
		    $http.get('../i18n/' + (key + '.properties' || '')).success(function(data) {
			complete(data, deferred, options);
		    }).error(function() {
			deferred.resolve();
		    });
		}

		return deferred.promise;
	    };

	    function complete(data, deferred, options) {
		var key = options.key;

		cache.put(key, data);
		$translate.use(key);

		deferred.resolve(data);
	    }
	});