package com.lazy.tcc.common.serializer.kryo;

import com.lazy.tcc.common.serializer.ObjectInput;
import com.lazy.tcc.common.serializer.ObjectOutput;
import com.lazy.tcc.common.serializer.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>
 * KryoSerialization
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/12.
 */
public class KryoSerialization implements Serialization {


    @Override
    public ObjectOutput serialize(OutputStream output) throws IOException {
        return new KryoObjectOutput(output);
    }

    @Override
    public ObjectInput deserialize(InputStream input) throws IOException {
        return new KryoObjectInput(input);
    }
}
