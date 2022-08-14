package cn.product.worldmall.controller.notice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.util.JSONUtils;

/**
 * 	提现异步
 */

@Controller
@RequestMapping(value = "/notice/withdraw")
public class NoticeWithdrawController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1806497273571327329L;

	/**
	 * 融宝提现异步
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/byReapal", method = RequestMethod.POST)
	@ResponseBody
	public void byReapal(HttpServletRequest request,String encryptkey, String data,HttpServletResponse response) throws Exception {
		LoggerFactory.getLogger(getClass()).info("推送回调接口encryptkey==========>" + encryptkey);
        LoggerFactory.getLogger(getClass()).info("推送回调接口data==========>" + data);
        try {
        	InputParams params = new InputParams("noticeWithdrawService", "byReapal");
			params.addParams("encryptkey", null, encryptkey);
			params.addParams("data", null, data);
			DataResult<Object> result = (DataResult<Object>) this.execute(params);
        	//处理异步通知参数
			if(result.getData() != null) {
				HashMap<String, Object> resultMap = (HashMap<String, Object>) result.getData();
				if((Boolean)resultMap.get("result")){
					response.getWriter().write("success");
					
				} else {
					response.getWriter().write("fail");
				}
				return;
			} else {
				response.getWriter().write("fail");
				return;
			}
        	
        } catch (NumberFormatException e) {
			e.printStackTrace();
			response.getWriter().write("fail");
		} catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("fail");
        }
	}
	
	/**
	 * 聚合支付回调
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/byUnion", method = RequestMethod.POST)
	@ResponseBody
	public void byUnion(HttpServletRequest request,HttpServletResponse response) {
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
			paramsls.put(name, valueStr);
		}
		System.out.println("参数列表："+JSONUtils.obj2json(paramsls));
		try {
				
			//处理异步通知参数
			InputParams params = new InputParams("noticeWithdrawService", "byUnion");
			params.addParams("paramsls", null, paramsls);
			DataResult<Object> result = (DataResult<Object>) this.execute(params);
			if(result.getData() != null) {
				Map<String, Object> resultMap = (Map<String, Object>) result.getData();
				if((Boolean)resultMap.get("result")){
					response.getWriter().write("success");
				} else {
					response.getWriter().write("fail");
				}
				return;
            } else {
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
