package cn.zeppin.product.ntb.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminWechatAuthService;
import cn.zeppin.product.utility.weixin.CheckUtil;
import cn.zeppin.product.utility.weixin.ConfigUtil;
import cn.zeppin.product.utility.weixin.MessageUtil;

@Controller
@RequestMapping(value = "/web/wx")
public class WeixinController extends BaseController {
	
	@Autowired
	private IQcbAdminWechatAuthService qcbAdminWechatAuthService; 
	
	@RequestMapping(value = "/wxRespond")
	@ResponseBody
	public void wxRespond(HttpServletRequest request,HttpServletResponse response) throws Exception{
		wxVerify(request, response);
		HandleMessage(request, response);
	}
	
	/**
	 * 公众号校验
	 * 
	 * @throws IOException
	 */
	private void wxVerify(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		System.out.println(signature + "---" + timestamp + "---" + nonce + "----" + echostr);
		PrintWriter out = response.getWriter();
		if (CheckUtil.checkSignature(ConfigUtil.TOKEN,signature, timestamp, nonce)) {
			out.print(echostr);
		}
	}
	

	/**
	 * 消息处理
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	private void HandleMessage(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Map<String, String> map = MessageUtil.xmlToMap(request);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String message = null;
			if (MessageUtil.MESSAGE_EVNET.equals(msgType)) {// 事件
				String eventType = map.get("Event");
				if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {// 关注
					message = MessageUtil.initNewsMessage(toUserName,fromUserName);
				}
			} else {
				message = MessageUtil.initText(toUserName, fromUserName, "做安全靠谱的理财，我们是认真的~");
			}
			System.out.println(message);
			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {

			out.close();
		}
	}
}
