package com.myrpc.serialize.netty;

import com.myrpc.serialize.entity.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: Desmand
 * @time: 2021/3/18 3:45 下午
 */

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private final static Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            RpcResponse rpcResponse = (RpcResponse) msg;
            logger.info("clent receive message:" + msg.toString());
            //声明一个AttributeKey对象
            AttributeKey<Object> key = AttributeKey.valueOf("rpcResponse");
            //将服务端的数据保存到AttributeKeymap上，AttributeMap可以看作是Channel的共享源
            //AttributeMap的key是AttributeKey，value是Attribute
            ctx.channel().attr(key).set(rpcResponse);
            ctx.channel().close();
        } finally {
            ReferenceCountUtil.release(msg);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("error:" + cause);
        ctx.close();
    }
}
