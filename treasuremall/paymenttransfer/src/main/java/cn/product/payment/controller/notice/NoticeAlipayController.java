package cn.product.payment.controller.notice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.BaseController;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;

@Controller
@RequestMapping(value = "/notice/alipay")
public class NoticeAlipayController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5436076481089073094L;
	
	/**
	 * 支付宝对公充值
	 * @return
	 */
	@RequestMapping(value = "/companyNotice", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("unchecked")
	public void companyNotice(HttpServletRequest request,HttpServletResponse response) {
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
			InputParams iparams = new InputParams("noticeAlipayService", "companyNotice");
			iparams.addParams("params", null, params);
			DataResult<Object> result = (DataResult<Object>) this.execute(iparams);
			if(result.getData() != null) {
				Map<String, Object> resultMap = (Map<String, Object>) result.getData();
				if((Boolean)resultMap.get("result")){
					response.getWriter().write("success");
				}else{
					response.getWriter().write("fail");
				}
			}else{
				response.getWriter().write("fail");
			}
			return;
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
