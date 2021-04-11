package com.doodl6.springboot.web.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;


public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) {
        //为channel注册handler，统一调用addLast方法，入站处理顺序为从上到下，出站则相反
        ChannelPipeline pipeline = ch.pipeline();
        //添加闲置处理,60秒没有数据传输，触发事件
        pipeline.addLast(new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS));
        //将字节解码为HttpMessage对象，并将HttpMessage对象编码为字节
        pipeline.addLast(new HttpServerCodec());
        //出站数据压缩
        pipeline.addLast(new HttpContentCompressor());
        //聚合多个HttpMessage为单个FullHttpRequest
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        //如果被请求的端点是/ws，则处理该升级握手
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        //聊天消息处理
        pipeline.addLast(new ChatServerHandler());
        //心跳处理
        pipeline.addLast(new HeartbeatHandler());
    }
}
