package cn.zeppin.product.score.controller.notice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.internal.util.AlipaySignature;

import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.service.FrontUserHistoryCheckService;
import cn.zeppin.product.score.util.JSONUtils;
import cn.zeppin.product.score.util.alipay.AliUtlity;

/**
 * 	充值异步
 */

@Controller
@RequestMapping(value = "/notice/recharge")
public class NoticeRechargeController extends BaseController{
	
	@Autowired
    private FrontUserHistoryCheckService frontUserHistoryCheckService;
	
	/**
	 * 支付宝对公充值
	 * @return
	 */
	@RequestMapping(value = "/byAliwap", method = RequestMethod.POST)
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
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		System.out.println("参数列表："+JSONUtils.obj2json(params));
		try {
			boolean verify_result = AlipaySignature.rsaCheckV1(params, AliUtlity.ALIPAY_PUBLIC_KEY, AliUtlity.CHARSET, "RSA2");
			System.out.println("验签："+verify_result);
			if(verify_result) {
				//处理异步通知参数
				Map<String, Object> resultMap = this.frontUserHistoryCheckService.rechargeNoticeByAlipay(params);
				if((Boolean)resultMap.get("result")){
					response.getWriter().write("success");
				} else {
					response.getWriter().write("fail");
				}
				return;
			} else {
				response.getWriter().write("fail");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				response.getWriter().write("fail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
