package org.uly.server.websocket;

import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocketServer
 * 在线测试 http://www.websocket-test.com/
 * ws://127.0.0.1:9377/desktop/wss/1
 *
 * @author C.H 2022/02/28 14:11
 */
@ServerEndpoint(value = "/desktop/wss/{userId}")
@Component
@EqualsAndHashCode
public class WebSocketServer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    /**
     * 用来存放每个客户端对应的MyWebSocket对象。
     */
    private static final Map<Long, WebSocketServer> WEB_SOCKET_SERVER_MAP = new ConcurrentHashMap<>();

    public Session getSession() {
        return session;
    }

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收参数中的用户ID
     */
    private Long userId;


    /**
     * 连接建立成功调用的方法
     * 接收url中的参数
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) {
        this.session = session;
        this.userId = userId;
        WEB_SOCKET_SERVER_MAP.put(userId, this);
        //在线数加1
        log.info("有新连接加入！当前在线人数为" + ONLINE_COUNT.incrementAndGet() + "  userId==== " + userId);
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        // 删除
        WEB_SOCKET_SERVER_MAP.remove(this.userId);
        // 在线数减1
        log.info("有一连接关闭！当前在线人数为" + ONLINE_COUNT.decrementAndGet());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
    }

    /**
     * @param session /
     * @param error   /
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误" + error);
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public void sendMessage(byte[] data) throws IOException {
        this.session.getBasicRemote().sendBinary(ByteBuffer.wrap(data));
    }


    /**
     * 私发
     *
     * @param message /
     * @throws IOException /
     */
    public void sendInfo(Long userId, String message) throws IOException {
        WebSocketServer webSocketServer = WEB_SOCKET_SERVER_MAP.get(userId);
        webSocketServer.sendMessage(message);
    }

    /**
     * 私发
     *
     * @param data /
     * @throws IOException /
     */
    public void sendInfo(Long userId, byte[] data) throws IOException {
        WebSocketServer webSocketServer = WEB_SOCKET_SERVER_MAP.get(userId);
        webSocketServer.sendMessage(data);
    }

    public WebSocketServer getWebSocketServer(Long userId){
        return WEB_SOCKET_SERVER_MAP.get(userId);
    }


    public int getONLINE_COUNT() {
        return ONLINE_COUNT.get();
    }
}
