package cn.product.treasuremall.controller.socket.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.control.TransferService;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.util.socket.ServerEncoder;

@ServerEndpoint(value = "/link", encoders = { ServerEncoder.class })
@Component
public class WebSocketServer {
	private final static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
	 
    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
 
    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
 
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
 
    // 接收token
    private String token = "";
    
//    /*
//     * 提供一个spring context上下文(解决方案)
//     */
//    private static ApplicationContext applicationContext;
//    
//	public static void setApplicationContext(ApplicationContext applicationContext) {
//		WebSocketServer.applicationContext = applicationContext;
//	}

	private static TransferService transferService;
	
	@Autowired
	public void setTransferService(TransferService transferService) {
		WebSocketServer.transferService = transferService;
	}

	/**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     // 加入set中
        addOnlineCount();           // 在线数加1
        Map<String, List<String>> params = session.getRequestParameterMap();
        System.out.println("----------连接参数----------" + params);
        this.token = params.get("token").get(0);
        log.info("客户端: " + token + " 连接成功, 当前在线人数为：" + getOnlineCount());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("发送消息异常：", e);
        }
    }
 
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  // 从set中删除
        subOnlineCount();           // 在线数减1
        log.info("有一个连接关闭，当前在线人数为：" + getOnlineCount());
    }
 
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @SuppressWarnings("unchecked")
	@OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自客户端 " + token + " 的信息: " + message);
//        // 群发消息
//        for (WebSocketServer item : webSocketSet) {
//            try {
//                item.sendMessageAsync(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        //点对点发送
        //获取客户端消息JSON
        Map<String, Object> responseMap = new HashMap<String, Object>();
        Map<String, Object> params = JSONUtils.json2map(message);
        String dataType = params.get("dataType") == null ? "" : params.get("dataType").toString();
        if(Utlity.checkStringNull(dataType)) {//返回错误提示
        	responseMap.put("status", ResultStatusType.FAILED);
        	responseMap.put("message", "方法路径错误！");
        	this.session.getAsyncRemote().sendObject(responseMap);
        	return;
        }
        responseMap.put("dataType", dataType);
//        transferService = applicationContext.getBean(TransferService.class);
        if("lotteryList".equals(dataType)) {//获取即将开奖列表
        	Integer pageNum = Integer.valueOf(params.get("pageNum") == null ? "0" : params.get("pageNum").toString());
    		Integer pageSize = Integer.valueOf(params.get("pageSize") == null ? "0" : params.get("pageSize").toString());
        	InputParams input = new InputParams("frontGoodsIssueService", "list");
        	input.addParams("statuses", null, new String[] {"lottery","finished"});
        	input.addParams("pageNum", null, pageNum);
        	input.addParams("pageSize", null, pageSize);
        	input.addParams("sort", null, "status desc,lotterytime desc");
    		DataResult<Object> data =  (DataResult<Object>) WebSocketServer.transferService.execute(input);
    		responseMap.put("status", data.getStatus());
    		responseMap.put("message", data.getMessage());
    		responseMap.put("pageNum", data.getPageNum());
    		responseMap.put("pageSize", data.getPageSize());
    		responseMap.put("totalResultCount", data.getTotalResultCount());
    		responseMap.put("data", JSONUtils.obj2json(data.getData()));
    		this.session.getAsyncRemote().sendObject(responseMap);
        	return;
        } else if ("lotteryGet".equals(dataType)) {
        	String uuid = params.get("uuid") == null ? "" : params.get("uuid").toString();
        	InputParams input = new InputParams("frontGoodsIssueService", "get");
        	input.addParams("uuid", null, uuid);
    		DataResult<Object> data =  (DataResult<Object>) WebSocketServer.transferService.execute(input);
    		responseMap.put("status", data.getStatus());
    		responseMap.put("message", data.getMessage());
    		responseMap.put("data", JSONUtils.obj2json(data.getData()));
    		this.session.getAsyncRemote().sendObject(responseMap);
        	return;
        } else {
        	this.session.getAsyncRemote().sendObject(params);
        	return;
        }
    }
 
    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }
 
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
//        this.session.getBasicRemote().sendObject(data);
    }
    
    /**
     * 实现服务器主动推送-点对点-同步
     */
    public void sendMessage(Map<String, Object> data) throws IOException {
//        this.session.getBasicRemote().sendText(message);
        try {
			this.session.getBasicRemote().sendObject(data);
		} catch (EncodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 实现服务器主动推送-点对点-异步
     */
    public void sendMessageAsync(Map<String, Object> data) {
    	this.session.getAsyncRemote().sendObject(data);
    }
    
    /**
     * 群发自定义消息
     */
    public void sendInfo(String message) throws IOException {
        log.info("推送消息到客户端：" + token + "，内容: " + message);
        for (WebSocketServer item : webSocketSet) {
            try {
                // 这里可以设定只推送给这个token的，为null则全部推送
                if (token == null) {
                    item.sendMessage(message);
                } else if (item.token.equals(token)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }
    
    
    /**
     * 群发自定义消息--同步
     */
    public void sendAll(Map<String, Object> data) throws IOException {
        log.info("群发消息到客户端" + "，内容: " + data);
        for (WebSocketServer item : webSocketSet) {
            try {
                item.session.getBasicRemote().sendObject(data);//同步发送
            } catch (IOException e) {
            	e.printStackTrace();
                continue;
            } catch (EncodeException e) {
            	e.printStackTrace();
            	continue;
			}
        }
    }
    
    /**
     * 群发自定义消息--异步
     */
    public void sendAllAsync(Map<String, Object> data) {
        log.info("群发消息到客户端" + "，内容: " + data);
        for (WebSocketServer item : webSocketSet) {
            item.session.getAsyncRemote().sendObject(data);//异步发送
        }
    }
 
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
 
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }
 
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
