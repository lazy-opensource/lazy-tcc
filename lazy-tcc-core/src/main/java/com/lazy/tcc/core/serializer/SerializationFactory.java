package com.lazy.tcc.core.serializer;

import com.lazy.tcc.core.spi.SpiConfiguration;

/**
 * <p>
 * SerializationFactory Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
public final class SerializationFactory {

    private SerializationFactory(){}

    private static volatile Serialization serialization;

    public static Serialization create() {
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
