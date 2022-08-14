package cn.product.worldmall.controller.socket.io;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.control.TransferService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;

@Service(value = "socketIODemoService")
public class SocketIODemoServiceImpl implements SocketIOService {
	private final static Logger log = LoggerFactory.getLogger(SocketIODemoServiceImpl.class);
	
	 // 用来存已连接的客户端
    private static Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();
    
    @Autowired
    private SocketIOServer socketIOServerDemo;

	private static TransferService transferService;
	
	@Autowired
	public void setTransferService(TransferService transferService) {
		SocketIODemoServiceImpl.transferService = transferService;
	}
    /**
     * Spring IoC容器创建之后，在加载SocketIOServiceImpl Bean之后启动
     * @throws Exception
     */
    @PostConstruct
    private void autoStartup() throws Exception {
        start();
    }
    
    /**
     * Spring IoC容器在销毁SocketIOServiceImpl Bean之前关闭,避免重启项目服务端口占用问题
     * @throws Exception
     */
    @PreDestroy
    private void autoStop() throws Exception  {
        stop();
    }
    
	@SuppressWarnings("unchecked")
	@Override
	public void start() throws Exception {
		// 监听客户端连接
        socketIOServerDemo.addConnectListener(client -> {
            String loginUserNum = getParamsByClient(client);
            log.info("1长连接用户"+loginUserNum+"接入，"+"当前连接数："+clientMap.size()+"个");
            if (loginUserNum != null) {
                clientMap.put(loginUserNum, client);
            }
            log.info("2长连接用户"+loginUserNum+"接入，"+"当前连接数："+clientMap.size()+"个");
            client.sendEvent(PUT_EVENT, "shidake666");
        });

        // 监听客户端断开连接
        socketIOServerDemo.addDisconnectListener(client -> {
            String loginUserNum = getParamsByClient(client);
            if (loginUserNum != null) {
                clientMap.remove(loginUserNum);
                client.disconnect();
            }
            log.info("长连接用户"+loginUserNum+"断开链接，"+"当前连接数："+clientMap.size()+"个");
        });

        // 处理自定义的事件，与连接监听类似
        socketIOServerDemo.addEventListener(PUSH_EVENT, Object.class, (client, data, ackSender) -> {
        	
        	log.info("长连接用户发送数据"+data+"--PUSH_EVENT，");
        	client.getHandshakeData();
//            System.out.println( " 客户端：************ " + data);
        	//点对点发送
            //获取客户端消息JSON
            Map<String, Object> responseMap = new HashMap<String, Object>();
            Map<String, Object> params = JSONUtils.json2map(String.valueOf(data));
            String dataType = params.get("dataType") == null ? "" : params.get("dataType").toString();
            if(Utlity.checkStringNull(dataType)) {//返回错误提示
            	responseMap.put("status", ResultStatusType.FAILED);
            	responseMap.put("message", "方法路径错误！");
//            	this.session.getAsyncRemote().sendObject(responseMap);
            	client.sendEvent(PUT_EVENT, responseMap);
            	return;
            }
            responseMap.put("dataType", dataType);
//            transferService = applicationContext.getBean(TransferService.class);
            if("lotteryList".equals(dataType)) {//获取即将开奖列表
            	Integer pageNum = Integer.valueOf(params.get("pageNum") == null ? "0" : params.get("pageNum").toString());
        		Integer pageSize = Integer.valueOf(params.get("pageSize") == null ? "0" : params.get("pageSize").toString());
            	InputParams input = new InputParams("frontGoodsIssueService", "list");
            	input.addParams("statuses", null, new String[] {"lottery","finished"});
            	input.addParams("pageNum", null, pageNum);
            	input.addParams("pageSize", null, pageSize);
            	input.addParams("sort", null, "status desc,lotterytime desc");
            	input.addParams("demoFlag", null, "1");
        		DataResult<Object> dataReslut =  (DataResult<Object>) SocketIODemoServiceImpl.transferService.execute(input);
        		responseMap.put("status", dataReslut.getStatus());
        		responseMap.put("message", dataReslut.getMessage());
        		responseMap.put("pageNum", dataReslut.getPageNum());
        		responseMap.put("pageSize", dataReslut.getPageSize());
        		responseMap.put("totalResultCount", dataReslut.getTotalResultCount());
        		responseMap.put("data", JSONUtils.obj2json(dataReslut.getData()));
//        		this.session.getAsyncRemote().sendObject(responseMap);
//        		ackSender.sendAckData(responseMap);
            	client.sendEvent(PUT_EVENT, responseMap);
            	return;
            } else if ("lotteryGet".equals(dataType)) {
            	String uuid = params.get("uuid") == null ? "" : params.get("uuid").toString();
            	InputParams input = new InputParams("frontGoodsIssueService", "get");
            	input.addParams("uuid", null, uuid);
        		DataResult<Object> dataReslut =  (DataResult<Object>) SocketIODemoServiceImpl.transferService.execute(input);
        		responseMap.put("status", dataReslut.getStatus());
        		responseMap.put("message", dataReslut.getMessage());
        		responseMap.put("data", JSONUtils.obj2json(dataReslut.getData()));
//        		this.session.getAsyncRemote().sendObject(responseMap);
//        		ackSender.sendAckData(responseMap);
            	client.sendEvent(PUT_EVENT, responseMap);
            	return;
            } else {
//            	this.session.getAsyncRemote().sendObject(params);
//            	ackSender.sendAckData(params);
            	client.sendEvent(PUT_EVENT, responseMap);
            	return;
            }
//        	ackSender.sendAckData(data);
        });
        socketIOServerDemo.start();
	}

	@Override
	public void stop() {
		if (socketIOServerDemo != null) {
            socketIOServerDemo.stop();
            socketIOServerDemo = null;
        }
	}

	@Override
	public void pushMessageToUser(PushMessage pushMessage) {
		String loginUserNum = pushMessage.getLoginUserNum();
//        if (StringUtils.isNotBlank(loginUserNum)) {
		if(!Utlity.checkStringNull(loginUserNum)) {
            SocketIOClient client = clientMap.get(loginUserNum);
            if (client != null)
                client.sendEvent(PUSH_EVENT, pushMessage);
        }
	}

	/**
     * 此方法为获取client连接中的参数，可根据需求更改
     * @param client
     * @return
     */
    private String getParamsByClient(SocketIOClient client) {
        // 从请求的连接中拿出参数（这里的loginUserNum必须是唯一标识）
        Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
        List<String> list = params.get("token");
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

	public static Map<String, SocketIOClient> getClientMap() {
		return clientMap;
	}

	public static void setClientMap(Map<String, SocketIOClient> clientMap) {
		SocketIODemoServiceImpl.clientMap = clientMap;
	}

	@Override
	public void pushMessageAll(Object data) {
		log.info("长连接用户发送数据"+data+"--PUSH_EVENT，");
		PushMessage pushMessage = new PushMessage();
		pushMessage.setContent(data);
		for(Entry<String, SocketIOClient> entry : clientMap.entrySet()){
			SocketIOClient client = entry.getValue();
			if (client != null)
                client.sendEvent(PUT_EVENT, pushMessage.getContent());
		}
	}
    
    
}
