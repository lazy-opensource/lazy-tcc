package com.lazy.tcc.core.serializer;

import com.lazy.tcc.core.SpiConfiguration;

/**
 * <p>
 * SerializationFactory Definition
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/12/14.
 */
public class SerializationFactory {

    private static Serialization serialization;

    public static Serialization newInstance() {
        if (serialization == null) {
            synchronized (SerializationFactory.class) {
                if (serialization == null) {
                    try {
                        serialization = SpiConfiguration.getInstance().getSeriClassImpl().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return serialization;
    }
}