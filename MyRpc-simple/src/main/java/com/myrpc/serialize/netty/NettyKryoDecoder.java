package com.myrpc.serialize.netty;

import com.myrpc.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Desmand
 * @time: 2021/3/19 2:44 下午
 */
@AllArgsConstructor
public class NettyKryoDecoder extends ByteToMessageDecoder {
    private Serializer serializer;
    private Class<?> clazz;
    private static final int BODY_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() >= BODY_LENGTH) {
            byteBuf.markReaderIndex();
            int dataLength = byteBuf.readInt();
            if (dataLength < 0 || byteBuf.readableBytes() < 0) {

                return;
            }
            if (byteBuf.readableBytes() < dataLength) {
                byteBuf.resetReaderIndex();
                return;
            }
            //序列化
            byte[] body = new byte[dataLength];
            byteBuf.readBytes(body);
            Object obj = serializer.deserialize(body, clazz);
            list.add(obj);
        }
    }
}
