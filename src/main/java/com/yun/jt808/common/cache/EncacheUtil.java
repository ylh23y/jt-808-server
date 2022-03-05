package com.yun.jt808.common.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
/**
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午12:31:49
 */
public class EncacheUtil {
	
	private static CacheManager cacheManager = null;
	
	public static final String cacheName1 = "SERVER_CACHE_1";
	
	public static void init() {
		cacheManager = new CacheManager();
	}
	
	public static void put(String key, Object value){
		Cache cache = EncacheUtil.getCacheManager().getCache(cacheName1);
		Element e = new Element(key, value);
		cache.put(e);
	}
	
	public static Object get(String key){
		Cache cache = EncacheUtil.getCacheManager().getCache(cacheName1);
		return cache.get(key);
	}
	
	public static CacheManager getCacheManager() {
		return cacheManager;
	}

	public static void setCacheManager(CacheManager cacheManager) {
		EncacheUtil.cacheManager = cacheManager;
	}

	/**
	 * 从缓存中删除对象
	 * @param key
	 * @return
	 */
	public static Object remove(String key) {
		Cache cache = EncacheUtil.getCacheManager().getCache(cacheName1);
		Element element = cache.get(key);
		if(element == null){
			return null;
		}
		if(cache.removeElement(element)){
			return element;
		}
		return null;
	}
	
}
