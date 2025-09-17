package com.api.cache;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheInspectionContent {

	private CacheManager cacheManager;
	
	public CacheInspectionContent(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public void printCacheContent(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache != null) {
			System.out.println("Cache Contents:");
			System.out.println(Objects.requireNonNull(cache.getNativeCache().toString()));
		} else {
			System.out.println("No Such Cache Found: " + cacheName);
		}
	}
}
