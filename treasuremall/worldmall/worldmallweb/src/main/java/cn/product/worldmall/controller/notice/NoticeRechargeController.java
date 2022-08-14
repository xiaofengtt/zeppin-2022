package cn.product.worldmall.controller.notice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.internal.util.AlipaySignature;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.alipay.AliUtlity;

/**
 * 	充值异步
 */

@Controller
@RequestMapping(value = "/notice/recharge")
public class NoticeRechargeController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1601636116341171269L;
	
	private final static Logger log = LoggerFactory.getLogger(NoticeRechargeController.class);

	/**
	 * 支付宝对公充值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/byAliwap", method = RequestMethod.POST)
	@ResponseBody
	public void byAliwap(HttpServletRequest request,HttpServletResponse response) {
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
		System.out.println("参数列表："+JSONUtils.obj2json(paramsls));
		try {
			boolean verify_result = AlipaySignature.rsaCheckV1(paramsls, AliUtlity.ALIPAY_PUBLIC_KEY, AliUtlity.CHARSET, "RSA2");
			System.out.println("验签："+verify_result);
			if(verify_result) {
				
				//处理异步通知参数
				InputParams params = new InputParams("noticeRechargeService", "byAliwap");
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
	
	/**
	 * 聚合支付充值回调
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
			InputParams params = new InputParams("noticeRechargeService", "byUnion");
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
	
	/**
	 * 兴达支付充值回调
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/byAcicpay", method = RequestMethod.POST)
	@ResponseBody
	public void byAcicpay(HttpServletRequest request,HttpServletResponse response) {
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
			InputParams params = new InputParams("noticeRechargeService", "byAcicpay");
			params.addParams("paramsls", null, paramsls);
			DataResult<Object> result = (DataResult<Object>) this.execute(params);
			if(result.getData() != null) {
				Map<String, Object> resultMap = (Map<String, Object>) result.getData();
				if((Boolean)resultMap.get("result")){
					response.getWriter().write("OK");
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
	
	/**
	 * 金樽支付充值回调
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/byJinzun", method = RequestMethod.POST)
	@ResponseBody
	public void byJinzun(HttpServletRequest request,HttpServletResponse response) {
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
			InputParams params = new InputParams("noticeRechargeService", "byJinzun");
			params.addParams("paramsls", null, paramsls);
			DataResult<Object> result = (DataResult<Object>) this.execute(params);
			if(result.getData() != null) {
				Map<String, Object> resultMap = (Map<String, Object>) result.getData();
				if((Boolean)resultMap.get("result")){
					response.getWriter().write("SUCCESS");
				} else {
					response.getWriter().write("FAIL");
				}
				return;
            } else {
            	response.getWriter().write("FAIL");
            }
			return;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write("FAIL");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	
	/**
	 * 聚合支付充值回调
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/byStripe", method = RequestMethod.POST)
	@ResponseBody
	public void byStripe(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			
			String signHeader = request.getHeader("Stripe-Signature");//header签名
			String uuid = request.getParameter("uuid");//本地账户ID
			
			InputStream inputStream = request.getInputStream();
	        ByteArrayOutputStream output = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024*4];
	        int n = 0;
	        while (-1 != (n = inputStream.read(buffer))) {
	            output.write(buffer, 0, n);
	        }
	        byte[] bytes = output.toByteArray();
	        String payload = new String(bytes, "UTF-8");//验签参数
	        
			//处理异步通知参数
			InputParams params = new InputParams("noticeRechargeService", "byStripe");
			params.addParams("signHeader", null, signHeader);
			params.addParams("uuid", null, uuid);
			params.addParams("payload", null, payload);
			DataResult<Object> result = (DataResult<Object>) this.execute(params);
			if(result.getData() != null) {
				Map<String, Object> resultMap = (Map<String, Object>) result.getData();
				if((Boolean)resultMap.get("result")){
					response.setStatus(200);
					response.getWriter().write("success");
				} else {
					response.setStatus(400);
					response.getWriter().write("fail");
				}
				return;
            } else {
            	response.setStatus(400);
            	response.getWriter().write("fail");
            }
			return;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.setStatus(400);
				response.getWriter().write("fail");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
