
package com.lazy.tcc.common.serializer.fst;


import com.lazy.tcc.common.serializer.ObjectInput;
import com.lazy.tcc.common.serializer.ObjectOutput;
import com.lazy.tcc.common.serializer.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FstSerialization implements Serialization {


    @Override
    public ObjectOutput serialize(OutputStream out) throws IOException {
        return new FstObjectOutput(out);
    }

    @Override
    public ObjectInput deserialize(InputStream is) throws IOException {
        return new FstObjectInput(is);
    }
}
