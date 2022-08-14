package cn.product.treasuremall.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketSendUtil {
	public static final Logger log= LoggerFactory.getLogger(SocketSendUtil.class);
	
//	private static final String link_url = "http://127.0.0.1/treasuremallweb/socket/websocket";
	private static final String link_url = Utlity.PATH_URL + "/socket/websocket";
	
	public static Map<String, Object> pushWin(Map<String, String> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean request = false;
		String message = "失败";
		String resultStr = "";
		try {
			resultStr = HttpClientUtil.post(link_url + "/pushWin", params);
        	log.info("-----------------发送成功--回收消息-------------------："+resultStr);
        	if("success".equals(resultStr)) {//成功
        		request = true;
        		message = "成功";
        	}
		} catch (Exception e) {
			e.printStackTrace();
			request = false;
    		message = "失败异常";
    		log.info("-----------------发送异常--回收消息-------------------："+resultStr);
		}
		
		//失败就重发
		result.put("request", request);
		result.put("message", message);
		return result;
	}
	
}
