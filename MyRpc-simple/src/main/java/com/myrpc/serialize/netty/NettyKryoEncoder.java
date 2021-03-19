package com.myrpc.serialize.netty;

import com.myrpc.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

/**
 * @description:
 * @author: Desmand
 * @time: 2021/3/19 2:44 下午
 */
@AllArgsConstructor
public class NettyKryoEncoder extends MessageToByteEncoder {
    private Serializer serializer;
    private Class<?> clazz;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        byte[] bytes = serializer.serialize(o);
        int dataLength = bytes.length;
        byteBuf.writeInt(dataLength);
        byteBuf.writeBytes(bytes);
    }
}
