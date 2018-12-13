package com.lazy.tcc.common.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>
 * Serialization
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/12.
 */
public interface Serialization {

    /**
     * create serializer
     *
     * @param output
     * @return serializer
     * @throws IOException
     */
    ObjectOutput serialize(OutputStream output) throws IOException;

    /**
     * create deserializer
     *
     * @param input
     * @return deserializer
     * @throws IOException
     */
    ObjectInput deserialize(InputStream input) throws IOException;
}
