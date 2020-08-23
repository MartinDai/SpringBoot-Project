package com.doodl6.springboot.web.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Map;

public class ChatServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Map<ChannelHandlerContext, String> userChannelMap = Maps.newHashMap();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        JSONObject messageJSON = JSON.parseObject(msg.text());
        String userName = messageJSON.getString("userName");
        String content = messageJSON.getString("content");
        ChannelHandlerContext context;
        for (Map.Entry<ChannelHandlerContext, String> entry : userChannelMap.entrySet()) {
            context = entry.getKey();
            if (context == ctx) {
                context.writeAndFlush(new TextWebSocketFrame("当前用户：" + content));
            } else {
                context.writeAndFlush(new TextWebSocketFrame("用户[" + userName + "]：" + content));
            }
        }

        userChannelMap.put(ctx, userName);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        userChannelMap.put(ctx, "用户[" + ctx.channel().remoteAddress() + "]");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        String userName = null;
        for (Map.Entry<ChannelHandlerContext, String> entry : userChannelMap.entrySet()) {
            if (entry.getKey() == ctx) {
                userName = entry.getValue();
                userChannelMap.remove(ctx);
                break;
            }
        }

        if (userName != null) {
            for (ChannelHandlerContext context : userChannelMap.keySet()) {
                context.writeAndFlush(new TextWebSocketFrame("用户[" + userName + "] 离开聊天室"));
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
