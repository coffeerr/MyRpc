package com.myrpc.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.myrpc.common.exception.SerializeException;
import com.myrpc.serialize.Serializer;
import com.myrpc.serialize.entity.RpcRequest;
import com.myrpc.serialize.entity.RpcResponse;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.rowset.serial.SerialException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * @description:
 * @author: Desmand
 * @time: 2021/3/18 3:04 下午
 */

public class KryoSerializer implements Serializer {
    private final static Logger logger = LoggerFactory.getLogger(KryoSerializer.class);
    private static final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RpcRequest.class);
        kryo.register(RpcResponse.class);
        kryo.setReferences(true);
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    @Override
    public byte[] serialize(Object object) {
        try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteOutputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            kryo.writeObject(output, object);
            kryoThreadLocal.remove();
            return output.toBytes();
        } catch (Exception e) {
            logger.error("occur exception when serialize:", e);
            throw new SerializeException("序列化失败");
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream);
        ) {
            Kryo kryo = kryoThreadLocal.get();
            Object o = kryo.readObject(input, clazz);
            kryoThreadLocal.remove();
            return clazz.cast(o);

        } catch (Exception e) {
            logger.error("occur exception when deserialize:", e);
            throw new SerializeException("反序列失败");
        }
    }
}
