package cn.product.payment.controller.notice;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.base.BaseController;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.NoticeInfo;
import cn.product.payment.entity.NoticeInfo.NoticeInfoStatus;
import cn.product.payment.entity.NoticeInfo.NoticeInfoType;
import cn.product.payment.entity.UserRecharge;
import cn.product.payment.service.ChannelAccountService;
import cn.product.payment.service.NoticeInfoService;
import cn.product.payment.service.UserRechargeService;
import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.Utlity;
import cn.product.payment.util.alipay.AliUtlity;

import com.alipay.api.internal.util.AlipaySignature;

@Controller
@RequestMapping(value = "/notice/alipay")
public class NoticeAlipay extends BaseController{
	
	@Autowired
	private UserRechargeService userRechargeService;
	
	@Autowired
	private ChannelAccountService channelAccountService;
	
	@Autowired
	private NoticeInfoService noticeInfoService;
	
	/**
	 * 支付宝对公充值
	 * @return
	 */
	@RequestMapping(value = "/companyNotice", method = RequestMethod.POST)
	@ResponseBody
	public void byAliwap(HttpServletRequest request,HttpServletResponse response) {
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		
		try {
			String orderNum = new String(params.get("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			String alipayOrderNum = new String(params.get("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			String status = new String(params.get("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			String passbackParams = new String(params.get("passback_params").getBytes("ISO-8859-1"),"UTF-8");
			
			if(Utlity.checkStringNull(passbackParams)){
				response.getWriter().write("fail");
				return;
			}
			
			UserRecharge ur = this.userRechargeService.get(passbackParams);
			if(ur == null){
				response.getWriter().write("fail");
				return;
			}
			
			ChannelAccount ca = this.channelAccountService.get(ur.getChannelAccount());
			Map<String, Object> caMap = JSONUtils.json2map(ca.getData());
			
			boolean verify_result = AlipaySignature.rsaCheckV1(params, caMap.get("publicKey").toString(), AliUtlity.CHARSET, AliUtlity.SIGN_TYPE);
			if(verify_result) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("orderNum", orderNum);
				dataMap.put("alipayOrderNum", alipayOrderNum);
				dataMap.put("userRecharge", passbackParams);
				dataMap.put("status", status);
				
				NoticeInfo notice = new NoticeInfo();
				notice.setUuid(UUID.randomUUID().toString());
				notice.setChannel(ur.getChannel());
				notice.setOrderNum(orderNum);
				notice.setData(JSONUtils.obj2json(dataMap));
				notice.setSource(JSONUtils.obj2json(params));
				notice.setType(NoticeInfoType.RECHARGE);
				notice.setStatus(NoticeInfoStatus.NORMAL);
				notice.setCreatetime(new Timestamp(System.currentTimeMillis()));
				
				this.noticeInfoService.insert(notice);
				
				response.getWriter().write("success");
				return;
			} else {
				response.getWriter().write("fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write("fail");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
