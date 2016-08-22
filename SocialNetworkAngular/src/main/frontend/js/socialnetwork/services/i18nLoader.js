angular.module('socialNetworkServices').factory('i18nLoader',
	function($q, $http, $translate, $cacheFactory, $log, config) {

	    var cacheName = config.i18nCacheKey, cache = $cacheFactory.get(cacheName);
	    
	    $log.debug("i18nLoader start");
	    
	    if (!cache) {
		cache = $cacheFactory(cacheName);
	    }

	    return function(options) {
		options = options || {};

		$log.debug("i18nLoader options key : " + options.key + ", cacheName : " + cacheName);

		var deferred = $q.defer(), key = options.key, data = cache.get(key);

		$log.debug("i18nLoader data : " + data);

		if (!key) {
		    key = 'en';
		}

		if (data) {
		    $log.debug("i18nLoader load from cache");
		    complete(data, deferred, key);
		} else {
		    $http.get('../i18n/' + (key + '.properties' || '')).success(function(data) {
			complete(data, deferred, key);
		    }).error(function(data) {
			deferred.reject(data.error);
		    });
		}

		return deferred.promise;
	    };

	    function complete(data, deferred, key) {
		cache.put(key, data);
		deferred.resolve(data);
	    }
	});