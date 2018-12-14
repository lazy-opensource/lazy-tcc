package com.lazy.tcc.core.cache;


import com.lazy.tcc.core.SpiConfiguration;

/**
 * <p>
 * CacheFactory Definition
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/12/14.
 */
public class CacheFactory {

    private static ICache cache;

    public static <K, V> ICache<K, V> newInstance() {
        if (cache == null) {
            synchronized (CacheFactory.class) {
                if (cache == null) {
                    try {
                        cache = SpiConfiguration.getInstance().getCacheClassImpl().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return cache;
    }

}
