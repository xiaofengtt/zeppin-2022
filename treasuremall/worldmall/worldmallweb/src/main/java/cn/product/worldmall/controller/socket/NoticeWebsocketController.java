package cn.product.worldmall.controller.socket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.controller.socket.io.SocketIOService;
import cn.product.worldmall.controller.socket.server.WebSocketServer;
import cn.product.worldmall.util.JSONUtils;

/**
 * 	长连接push数据
 */

@Controller
@RequestMapping(value = "/socket/websocket")
public class NoticeWebsocketController extends BaseController{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4804795696895389295L;
	private final static Logger log = LoggerFactory.getLogger(NoticeWebsocketController.class);

	@Autowired
	private WebSocketServer webSocketServer;

	@Autowired
	private SocketIOService socketIOService;

//	@Autowired
//	private SocketIOService socketIOService;
	
	/**
	 * 长连接push群发数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/pushWin", method = RequestMethod.POST)
	@ResponseBody
	public void pushWin(HttpServletRequest request,HttpServletResponse response) {
		try {
			Map<String,Object> paramsls = new HashMap<String,Object>();
			Map<String,String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化 
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				paramsls.put(name, valueStr);
			}
			log.info("参数列表："+JSONUtils.obj2json(paramsls));
			webSocketServer.sendAllAsync(paramsls);
			socketIOService.pushMessageAll(paramsls);
			response.getWriter().write("success");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * demo-fail
	 * @return
	 */
	@RequestMapping(value = "/demoerr", method = RequestMethod.POST)
	@ResponseBody
	public void demoerr(HttpServletRequest request,HttpServletResponse response) {
		try {
			Map<String,String> paramsls = new HashMap<String,String>();
			Map<String,String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				paramsls.put(name, valueStr);
			}
			log.debug("参数列表："+JSONUtils.obj2json(paramsls));
			response.getWriter().write("fail");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
//	/**
//	 * demo-socket.io
//	 * @return
//	 */
//	@RequestMapping(value = "/socketio", method = RequestMethod.GET)
//	@ResponseBody
//	public void socketio(HttpServletRequest request,HttpServletResponse response) {
//		try {
//			Map<String,String> paramsls = new HashMap<String,String>();
//			Map<String,String[]> requestParams = request.getParameterMap();
//			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
//				String name = (String) iter.next();
//				String[] values = (String[]) requestParams.get(name);
//				String valueStr = "";
//				for (int i = 0; i < values.length; i++) {
//					valueStr = (i == values.length - 1) ? valueStr + values[i]
//							: valueStr + values[i] + ",";
//				}
//				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
//				paramsls.put(name, valueStr);
//			}
//			log.debug("参数列表："+JSONUtils.obj2json(paramsls));
//			socketIOService.pushMessageAll();
//			response.getWriter().write("fail");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
