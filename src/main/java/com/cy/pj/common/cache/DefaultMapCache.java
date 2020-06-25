package com.cy.pj.common.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class DefaultMapCache {
	//使用map对象作为存储数据的容器
	private Map<Object,Object> cache=new ConcurrentHashMap<>();
	public void putObject(Object key,Object value) {
		cache.put(key, value);
	}
	public Object getObject(Object key) {
		return cache.get(key);
	}
	public void clear() {
		cache.clear();
	}
}
