package com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.cache.CacheInspectionContent;

@RestController
@RequestMapping("/api")
public class CacheController {
	
	@Autowired
	private CacheInspectionContent cacheInspectionContent;
	
	@GetMapping("/cache")
	public void getCacheData(@RequestParam(value = "cacheName") String cacheName) {
		cacheInspectionContent.printCacheContent(cacheName);
	}
	
}
