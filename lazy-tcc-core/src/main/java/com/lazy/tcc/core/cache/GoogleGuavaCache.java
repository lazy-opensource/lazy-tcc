package com.lazy.tcc.core.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * GoogleGuavaCache Definition
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/12/14.
 */
public class GoogleGuavaCache<K, V> implements ICache<K, V> {

    private final Cache<K, V> cache;

    public GoogleGuavaCache() {
        //120 sec
        int defaultExpire = 120;
        this.cache = CacheBuilder.newBuilder().expireAfterAccess(defaultExpire, TimeUnit.SECONDS).maximumSize(1000).build();
    }

    /**
     * put
     *
     * @param k key
     * @param v val
     */
    @Override
    public void put(K k, V v) {
        cache.put(k, v);
    }

    /**
     * get
     *
     * @param k key
     * @return val
     */
    @Override
    public V get(K k) {
        return cache.getIfPresent(k);
    }

    /**
     * remove
     *
     * @param k key
     */
    @Override
    public void remove(K k) {
        cache.invalidate(k);
    }

    /**
     * clean all
     */
    @Override
    public void cleanAll() {
        cache.cleanUp();
    }
}
