package com.test;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhCache2 {

	 public static void main(String[] args) {  
         //创建缓存管理器  
        final CacheManager cacheManager = new CacheManager();  
//  
//        // 创建一个缓存实例（在配置文件中获取一个缓存实例）  
//        final Cache cache = cacheManager.getCache("helloworld1");  
//  
//        final String key = "greeting";  
//        
//        //他建一个数据容器  
//        final Element putGreeting = new Element(key, "Hello, World!");  
//  
//        //将数据放入到缓存实例中  
//        for(int i=0;i<1000;i++){
//        	cache.put(new Element(key + i, "Hello, World!" + i)); 
//        }
////        
//        System.out.println(cache.getMemoryStoreSize());
        
  
        //取值  
        final Cache cache2 = cacheManager.getCache("helloworld1");  
        final Element getGreeting = cache2.get("greeting1");  
  
        // Print the value  
        System.out.println("value======//========"+getGreeting.getObjectValue());  
        System.out.println(System.getProperty("Java.io.tmpdir"));
	 }
}
