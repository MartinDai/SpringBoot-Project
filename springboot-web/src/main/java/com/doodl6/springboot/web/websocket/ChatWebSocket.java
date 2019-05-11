package com.doodl6.springboot.web.websocket;

import com.doodl6.springboot.web.service.mq.RocketMQService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;

/**
 * 聊天室webSocket
 * Created by daixiaoming on 2019/1/3.
 */
@ServerEndpoint(value = "/chatWebSocket/{userName}")
@Component
public class ChatWebSocket {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 用户会话Map,使用session作为key，是为了兼容用户重名问题，正常线上的话，可以使用userId这种唯一键作为key，value为session
     */
    private static Map<Session, String> userSessionMap = Maps.newHashMap();

    @Resource
    private RocketMQService rocketMQService;

    /**
     * 连接建立成功时
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName) {
        userSessionMap.put(session, userName);
        logger.info("有新用户加入！当前在线人数为{} ", userSessionMap.size());
    }

    /**
     * 收到客户端消息时
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        String userName = userSessionMap.get(session);

        logger.info("收到用户发送的消息 | {} | {} ", userName, message);

//        rocketMQService.sendNewChatRecord(userName, message, System.currentTimeMillis());

        //发送消息到所有客户端
        for (Map.Entry<Session, String> userSessionEntry : userSessionMap.entrySet()) {
            sendMessage(userSessionEntry.getKey(), userName + "：" + message);
        }
    }

    /**
     * 连接关闭时
     */
    @OnClose
    public void onClose(Session session) {
        userSessionMap.remove(session);
        logger.info("有用户离开！当前在线人数为 | {}", userSessionMap.size());
    }

    /**
     * 发生错误时
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error("socket异常", throwable);
    }

    /**
     * 发送消息给客户端
     */
    public void sendMessage(Session session, String message) {
        synchronized (session) {
            for (int i = 0; i < 3; i++) {
                try {
                    session.getBasicRemote().sendText(message);
                    break;
                } catch (IOException e) {
                    logger.error("发送消息异常", e);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }
    }
}
