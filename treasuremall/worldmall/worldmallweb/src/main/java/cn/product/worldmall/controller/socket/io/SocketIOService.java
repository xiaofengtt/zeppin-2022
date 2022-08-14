package cn.product.worldmall.controller.socket.io;

public interface SocketIOService {
	 //推送的事件
    public static final String PUSH_EVENT = "push_event";
    
	 //推送的事件
    public static final String PUT_EVENT = "put_event";

    // 启动服务
    void start() throws Exception;

    // 停止服务
    void stop();

    // 推送信息
    void pushMessageToUser(PushMessage pushMessage);

    // 推送信息
    void pushMessageAll(Object data);
}
