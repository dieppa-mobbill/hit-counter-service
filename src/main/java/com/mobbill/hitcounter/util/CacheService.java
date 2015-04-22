package com.mobbill.hitcounter.util;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by dieppa on 28/02/15.
 */
public class CacheService {

    private static Map<String, Object> cache = new WeakHashMap<>();

    public static void put(String key, Object object){
        synchronized (cache){
            cache.put(key, object);
        }
    }

    public static Object get(String key){
        return cache.get(key);
    }


    public static void delete(String key){
        synchronized (cache){
            cache.remove(key);
        }
    }


   public static void reset(){
    cache.clear();
   }


}
