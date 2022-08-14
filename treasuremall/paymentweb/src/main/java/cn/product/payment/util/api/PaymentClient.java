package cn.product.payment.util.api;

import java.util.HashMap;
import java.util.Map;

public class PaymentClient {
	private String serverUrl;
	private String companyid;
	private String privateKey;
	private String publicKey;
	
	private String version = "3.5.2";
	private String charset = "UTF-8";
	
	private String proxyHost;
    private int    proxyPort;
    
	private int    connectTimeout = 3000;
    private int    readTimeout    = 15000;
    
    public class PaymentClientType {
    	public final static String RECHARGE_CREATE_ORDER = "/api/recharge/createOrder";
    	public final static String RECHARGE_QUERY_ORDER = "/api/recharge/queryOrder";
    	public final static String RECHARGE_CLOSE_ORDER = "/api/recharge/closeOrder";
    	
    	public final static String WITHDRAW_CREATE_ORDER = "/api/withdraw/createOrder";
    	public final static String WITHDRAW_QUERY_ORDER = "/api/withdraw/queryOrder";
    	public final static String WITHDRAW_CLOSE_ORDER = "/api/withdraw/closeOrder";
	}
    
    public PaymentClient(String serverUrl, String companyid, String privateKey, String publicKey) {
		this.serverUrl = serverUrl;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
		this.companyid = companyid;
		this.proxyHost = null;
		this.proxyPort = 0;
	}
    
    public Map<String, Object> createRechargeOrder(String channel, String notifyUrl, String title, Integer totalAmount,
    		String orderNum, String passbackParams, String infomation, Long timeout) throws PaymentException{
    	return createRechargeOrder(channel, notifyUrl, title, totalAmount, orderNum, passbackParams, infomation, null, timeout);
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> createRechargeOrder(String channel, String notifyUrl, String title, Integer totalAmount,
    		String orderNum, String passbackParams, String infomation, String returnUrl, Long timeout) throws PaymentException{
    	if(StringUtils.isEmpty(channel)){
    		throw new PaymentException("渠道不能为空！");
    	}
    	if(StringUtils.isEmpty(notifyUrl)){
    		throw new PaymentException("回调地址不能为空！");
    	}
    	if(StringUtils.isEmpty(title)){
    		throw new PaymentException("付款标题不能为空！");
    	}
    	if(totalAmount == null){
    		throw new PaymentException("付款金额不能为空！");
    	}
    	if(StringUtils.isEmpty(orderNum)){
    		throw new PaymentException("订单号不能为空！");
    	}
    	Map<String, String> params = new HashMap<>();
    	params.put("company", companyid);
    	params.put("version", version);
    	params.put("channel", channel);
    	params.put("notifyUrl", notifyUrl);
    	params.put("title", title);
    	params.put("amount", totalAmount.toString());
    	params.put("orderNum", orderNum);
    	params.put("timestamp", System.currentTimeMillis() + "");
    	if(!StringUtils.isEmpty(passbackParams)){
    		params.put("transData", passbackParams);
    	}
    	if(!StringUtils.isEmpty(infomation)){
    		params.put("infomation", infomation);
    	}
    	if(!StringUtils.isEmpty(returnUrl)){
    		params.put("returnUrl", returnUrl);
    	}
    	if(timeout != null){
    		params.put("timeout", timeout + "");
    	}
    	
		Map<String, String> signMap = new HashMap<String, String>(params);
		String sign = PaymentOrderUtil.getSign(signMap, privateKey);
		params.put("sign", sign);
		
		String url = serverUrl + PaymentClientType.RECHARGE_CREATE_ORDER;
		
		try {
			String result = WebUtil.post(url, params, connectTimeout, readTimeout);
			Map<String, Object> resultMap = JSONUtils.json2map(result);
			if("SUCCESS".equals(resultMap.get("status"))){
				Map<String, String> resultDataMap = (Map<String, String>)resultMap.get("data");
				if(PaymentOrderUtil.checkSign(resultDataMap, publicKey)){
					return resultMap;
				}else{
					 throw new PaymentException("验签失败！");
				}
			}else if("FAIL".equals(resultMap.get("status"))){
				return resultMap;
			}else{
				throw new PaymentException("参数有误！");
			}
		} catch (Exception e) {
	        throw new PaymentException("参数有误！");
		}
	}
    
    @SuppressWarnings("unchecked")
	public Map<String, Object> queryRechargeOrder(String orderNum, String paymentOrderNum) throws PaymentException{
    	if(StringUtils.isEmpty(orderNum) && StringUtils.isEmpty(paymentOrderNum)){
    		throw new PaymentException("订单号不能为空！");
    	}
    	
    	Map<String, String> params = new HashMap<>();
    	params.put("company", companyid);
    	params.put("version", version);
    	params.put("timestamp", System.currentTimeMillis() + "");
    	if(!StringUtils.isEmpty(orderNum)){
    		params.put("orderNum", orderNum);
    	}
    	if(!StringUtils.isEmpty(paymentOrderNum)){
    		params.put("paymentOrderNum", paymentOrderNum);
    	}
    	
    	Map<String, String> signMap = new HashMap<String, String>(params);
		String sign = PaymentOrderUtil.getSign(signMap, privateKey);
		params.put("sign", sign);
		
		String url = serverUrl + PaymentClientType.RECHARGE_QUERY_ORDER;
		
		try {
			String result = WebUtil.post(url, params, connectTimeout, readTimeout);
			Map<String, Object> resultMap = JSONUtils.json2map(result);
			if("SUCCESS".equals(resultMap.get("status"))){
				Map<String, String> dataMap = (Map<String, String>)resultMap.get("data");
				if(PaymentOrderUtil.checkSign(dataMap, publicKey)){
					return resultMap;
				}else{
					 throw new PaymentException("验签失败！");
				}
			}else if("FAIL".equals(resultMap.get("status"))){
				return resultMap;
			}else{
				throw new PaymentException("参数有误！");
			}
		} catch (Exception e) {
			e.printStackTrace();
	        throw new PaymentException("参数有误！");
		}
    }
    
    @SuppressWarnings("unchecked")
	public Map<String, Object> closeRechargeOrder(String orderNum, String paymentOrderNum) throws PaymentException{
    	if(StringUtils.isEmpty(orderNum) && StringUtils.isEmpty(paymentOrderNum)){
    		throw new PaymentException("订单号不能为空！");
    	}
    	
    	Map<String, String> params = new HashMap<>();
    	params.put("company", companyid);
    	params.put("version", version);
    	params.put("timestamp", System.currentTimeMillis() + "");
    	if(!StringUtils.isEmpty(orderNum)){
    		params.put("orderNum", orderNum);
    	}
    	if(!StringUtils.isEmpty(paymentOrderNum)){
    		params.put("paymentOrderNum", paymentOrderNum);
    	}
    	
    	Map<String, String> signMap = new HashMap<String, String>(params);
		String sign = PaymentOrderUtil.getSign(signMap, privateKey);
		params.put("sign", sign);
		
		String url = serverUrl + PaymentClientType.RECHARGE_CLOSE_ORDER;
		
		try {
			String result = WebUtil.post(url, params, connectTimeout, readTimeout);
			Map<String, Object> resultMap = JSONUtils.json2map(result);
			if("SUCCESS".equals(resultMap.get("status"))){
				Map<String, String> dataMap = (Map<String, String>)resultMap.get("data");
				if(PaymentOrderUtil.checkSign(dataMap, publicKey)){
					return resultMap;
				}else{
					throw new PaymentException("参数有误！");
				}
			}else if("FAIL".equals(resultMap.get("status"))){
				return resultMap;
			}else{
				 throw new PaymentException("参数有误！");
			}
		} catch (Exception e) {
	        throw new PaymentException("参数有误！");
		}
    }
    
    public Boolean checkOrder(Map<String, String> dataMap){
    	try {
	    	return PaymentOrderUtil.checkSign(dataMap, publicKey);
		} catch (Exception e) {
			return false;
		}
    	
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> createWithdrawOrder(String channel, String notifyUrl, String title, Integer totalAmount,
    		String orderNum, Map<String, String> dataMap, String passbackParams) throws PaymentException{
    	if(StringUtils.isEmpty(channel)){
    		throw new PaymentException("渠道不能为空！");
    	}
    	if(StringUtils.isEmpty(notifyUrl)){
    		throw new PaymentException("回调地址不能为空！");
    	}
    	if(StringUtils.isEmpty(title)){
    		throw new PaymentException("付款标题不能为空！");
    	}
    	if(totalAmount == null){
    		throw new PaymentException("付款金额不能为空！");
    	}
    	if(StringUtils.isEmpty(orderNum)){
    		throw new PaymentException("订单号不能为空！");
    	}
    	Map<String, String> params = new HashMap<>();
    	params.put("company", companyid);
    	params.put("version", version);
    	params.put("channel", channel);
    	params.put("notifyUrl", notifyUrl);
    	params.put("title", title);
    	params.put("amount", totalAmount.toString());
    	params.put("orderNum", orderNum);
    	params.put("timestamp", System.currentTimeMillis() + "");
    	params.put("data", JSONUtils.obj2json(dataMap));
    	if(!StringUtils.isEmpty(passbackParams)){
    		params.put("transData", passbackParams);
    	}
    	
		Map<String, String> signMap = new HashMap<String, String>(params);
		String sign = PaymentOrderUtil.getSign(signMap, privateKey);
		params.put("sign", sign);
		
		String url = serverUrl + PaymentClientType.WITHDRAW_CREATE_ORDER;
		
		try {
			String result = WebUtil.post(url, params, connectTimeout, readTimeout);
			Map<String, Object> resultMap = JSONUtils.json2map(result);
			if("SUCCESS".equals(resultMap.get("status"))){
				Map<String, String> resultDataMap = (Map<String, String>)resultMap.get("data");
				if(PaymentOrderUtil.checkSign(resultDataMap, publicKey)){
					return resultMap;
				}else{
					 throw new PaymentException("验签失败！");
				}
			}else if("FAIL".equals(resultMap.get("status"))){
				return resultMap;
			}else{
				throw new PaymentException("参数有误！");
			}
		} catch (Exception e) {
	        throw new PaymentException("参数有误！");
		}
	}
    
    @SuppressWarnings("unchecked")
	public Map<String, Object> queryWithdrawOrder(String orderNum, String paymentOrderNum) throws PaymentException{
    	if(StringUtils.isEmpty(orderNum) && StringUtils.isEmpty(paymentOrderNum)){
    		throw new PaymentException("订单号不能为空！");
    	}
    	
    	Map<String, String> params = new HashMap<>();
    	params.put("company", companyid);
    	params.put("version", version);
    	params.put("timestamp", System.currentTimeMillis() + "");
    	if(!StringUtils.isEmpty(orderNum)){
    		params.put("orderNum", orderNum);
    	}
    	if(!StringUtils.isEmpty(paymentOrderNum)){
    		params.put("paymentOrderNum", paymentOrderNum);
    	}
    	
    	Map<String, String> signMap = new HashMap<String, String>(params);
		String sign = PaymentOrderUtil.getSign(signMap, privateKey);
		params.put("sign", sign);
		
		String url = serverUrl + PaymentClientType.WITHDRAW_QUERY_ORDER;
		
		try {
			String result = WebUtil.post(url, params, connectTimeout, readTimeout);
			Map<String, Object> resultMap = JSONUtils.json2map(result);
			if("SUCCESS".equals(resultMap.get("status"))){
				Map<String, String> dataMap = (Map<String, String>)resultMap.get("data");
				if(PaymentOrderUtil.checkSign(dataMap, publicKey)){
					return resultMap;
				}else{
					 throw new PaymentException("验签失败！");
				}
			}else if("FAIL".equals(resultMap.get("status"))){
				return resultMap;
			}else{
				throw new PaymentException("参数有误！");
			}
		} catch (Exception e) {
			e.printStackTrace();
	        throw new PaymentException("参数有误！");
		}
    }
    
    @SuppressWarnings("unchecked")
	public Map<String, Object> closeWithdrawOrder(String orderNum, String paymentOrderNum) throws PaymentException{
    	if(StringUtils.isEmpty(orderNum) && StringUtils.isEmpty(paymentOrderNum)){
    		throw new PaymentException("订单号不能为空！");
    	}
    	
    	Map<String, String> params = new HashMap<>();
    	params.put("company", companyid);
    	params.put("version", version);
    	params.put("timestamp", System.currentTimeMillis() + "");
    	if(!StringUtils.isEmpty(orderNum)){
    		params.put("orderNum", orderNum);
    	}
    	if(!StringUtils.isEmpty(paymentOrderNum)){
    		params.put("paymentOrderNum", paymentOrderNum);
    	}
    	
    	Map<String, String> signMap = new HashMap<String, String>(params);
		String sign = PaymentOrderUtil.getSign(signMap, privateKey);
		params.put("sign", sign);
		
		String url = serverUrl + PaymentClientType.WITHDRAW_CLOSE_ORDER;
		
		try {
			String result = WebUtil.post(url, params, connectTimeout, readTimeout);
			Map<String, Object> resultMap = JSONUtils.json2map(result);
			if("SUCCESS".equals(resultMap.get("status"))){
				Map<String, String> dataMap = (Map<String, String>)resultMap.get("data");
				if(PaymentOrderUtil.checkSign(dataMap, publicKey)){
					return resultMap;
				}else{
					throw new PaymentException("参数有误！");
				}
			}else if("FAIL".equals(resultMap.get("status"))){
				return resultMap;
			}else{
				 throw new PaymentException("参数有误！");
			}
		} catch (Exception e) {
	        throw new PaymentException("参数有误！");
		}
    }
}
