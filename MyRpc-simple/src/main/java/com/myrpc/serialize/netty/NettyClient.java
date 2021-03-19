package com.myrpc.serialize.netty;

import com.myrpc.serialize.entity.RpcRequest;
import com.myrpc.serialize.entity.RpcResponse;
import com.myrpc.serialize.kryo.KryoSerializer;
import com.myrpc.transport.RpcClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: Desmand
 * @time: 2021/3/19 2:21 下午
 */

public class NettyClient implements RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private String host;
    private int port;
    private static final Bootstrap b;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        b = new Bootstrap();
        KryoSerializer kryoSerializer = new KryoSerializer();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                //连接超时时间
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //自定义序列化编解码器
                        //对于客户端而言，先解码再编码
                        ch.pipeline().addLast(new NettyKryoDecoder(kryoSerializer, RpcResponse.class));
                        ch.pipeline().addLast(new NettyKryoEncoder(kryoSerializer, RpcRequest.class));
                        ch.pipeline().addLast(new NettyClientHandler());
                    }
                });
    }

    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        try {
            ChannelFuture f = b.connect(host, port).sync();
            logger.info("client connnect {}", host + ":" + port);
            Channel futureChannel = f.channel();
            logger.info("send message");
            if (futureChannel != null) {
                futureChannel.writeAndFlush(rpcRequest).addListener(future -> {
                    if (future.isSuccess()) {
                        logger.info("client send message:{}", rpcRequest.toString());
                    } else {
                        logger.error("error:" + future.cause());
                    }
                });
            }
            //阻塞等待，知道channel关闭
            futureChannel.closeFuture().sync();
            AttributeKey<RpcResponse> attributeKey = AttributeKey.valueOf("rpcResponse");
            return futureChannel.attr(attributeKey).get();
        } catch (InterruptedException e) {
            logger.error("error:", e);
        }
        return null;
    }
}
