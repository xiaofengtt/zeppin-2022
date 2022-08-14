package cn.zeppin.product.ntb.qcb.controller.wechat;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyAccountHistoryVO;
import cn.zeppin.product.ntb.qcb.vo.QcbEmployeeHistoryVO;
import cn.zeppin.product.utility.weixin.CheckUtil;
import cn.zeppin.product.utility.weixin.ConfigUtil;
import cn.zeppin.product.utility.weixin.MessageUtil;
/**
 * <p>Title:WeixinController</p>
 * <p>Description:企财宝公众号（只有公众号处于开发者模式才有用）</p>
 * @author geng
 * @date 2018年1月31日 上午9:32:32
 */
@Controller
@RequestMapping(value = "/qcbWechat/wx")
public class WechatController extends BaseController {
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	@Autowired
	private IQcbEmployeeHistoryService qcbEmployeeHistoryService;
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbCompanyAccountHistoryService qcbCompanyAccountHistoryService;
	
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
		if (CheckUtil.checkSignature(ConfigUtil.QCB_TOKEN,signature, timestamp, nonce)) {
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
					message = MessageUtil.initText(toUserName, fromUserName, 
							"你好，欢迎来到QCB服务号[玫瑰]。您可以点击“员工服务”菜单先进行身份确认[拥抱][拥抱]，然后获取您的相关薪资信息[红包][红包][红包]，并且享受我们为您提供的便利的服务。[爱心][爱心][爱心][爱心]");
				}
			} 
			System.out.println(message);
			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {

			out.close();
		}
	}

	/**
	 * 获取服务器当前时间
	 * @return
	 */
	@RequestMapping(value = "/getTime", method = RequestMethod.GET)
	@ResponseBody
	public Result getTime(){
		return ResultManager.createDataResult(System.currentTimeMillis());
	}
	
	/**
	 * 获取订单信息
	 * @param uuid
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/getBillInfo", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "订单编号", required = true)
	@ActionParam(key = "type", type = DataType.STRING, message = "订单类型", required = true)
	@ResponseBody
	protected Result getBillInfo(String uuid, String type,ServletRequest request){
		if("qcbEmployee".equals(type)){
			QcbEmployeeHistory qeh = this.qcbEmployeeHistoryService.get(uuid);
			if(qeh != null){
				QcbEmployeeHistoryVO qehvo = new QcbEmployeeHistoryVO(qeh);
				QcbEmployee qe = this.qcbEmployeeService.get(qeh.getQcbEmployee());
				if(qe != null){
					qehvo.setQcbEmployeeName(qe.getRealname());
				}
				return ResultManager.createDataResult(qehvo);
			}else{
				return ResultManager.createFailResult("订单不存在！");
			}
		}else if("qcbCompany".equals(type)){
			QcbCompanyAccountHistory qcah = this.qcbCompanyAccountHistoryService.get(uuid);
			if(qcah != null){
				QcbCompanyAccountHistoryVO qcahvo = new QcbCompanyAccountHistoryVO(qcah);
				QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcah.getQcbCompany());
				if(qca != null){
					qcahvo.setQcbCompanyAccountName(qca.getName());
				}
				return ResultManager.createDataResult(qcahvo);
			}else{
				return ResultManager.createFailResult("订单不存在！");
			}
		}else{
			return ResultManager.createFailResult("订单不存在！");
		}
	}
}
