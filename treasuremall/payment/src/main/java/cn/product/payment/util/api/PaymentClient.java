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
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> createRechargeOrder(String channel, String notifyUrl, String title, Integer totalAmount,
    		String orderNum, String passbackParams, String infomation, Long timeout) throws PaymentException{
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
	public static void main(String[] args) {
		String baseUrl = "http://localhost:18080";
//		String baseUrl = "https://backadmin.niutoulicai.com/payment";
		String company = "202003260000";
		String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCHRYIuSsyjzT5UlUEXRw5D+Sy4COzg6ky73pUOtncFomfezB3uhXoE4R5ncYGmBElg2YgELb2zU8UA7jAC9q21vCsuAN7r9bRzBjBaZV7XtzPmoIrBMLQ61QIuFulA6XZxghCzx4y+8ycvoz5vHJRHB3c6LS8rLCH02qPEHdNrVElMKxRE1LlPHfYuGPQ3v9hxE19aLeSID35zj2GyAolmLSsTLXMMEPK3Z+byC8ZEYaY4jzpmrI0FwE/c2oECjJe5rb9R6WAtGUVtCHa8R32b4yx/GqrdhS8BlPBBGVEaGBvmVcb9bJKNW9XEdVx6rGTkxaGadzi8Rt678mQz1sG1AgMBAAECggEAVDJXjSjRp0MhANTF5w87OePmicZEatprWKR6cuXvv3oO3QZ/uu8pu8/DdO+ZmxQf+EBuAOvXn78MaD/Zcn7IMM4mskOofYY6Az5e6mUeW3aUv4A93XHLPLNI4BcyqEyohI5Ihx415MTEJ0qcmdxYDr3+4B8vtNeLazSLqo5GAg9JnExLOqO2j9ZHkGAyiKR1yOPjupz9GPGiIWfCZaL3OR4RFT1BJHU3t/F/xy7AXlx/l/ANXmLxZiizNOzj6pzR0XMssfJPKlNFJ0l4fr3OlqxNHIe7M0WINPwASxt8xQV0GDX/5LLbb01KiAFj/7iaw0vkEeJTSfQhWsqnrAr5AQKBgQD9ipNBpqkq5JHh56teqCy920zIrG78NEj61t1fFVhZTpBm3N+v3EX1r6mbUW+HtUTzRSy4wOSivv1sGZJ8MlWgT2jVBEoAlGKhZOgg08oG6WJOmnFRTXYIVw6svD0f0W2TEldKFJLDdy/xE+sHTWh1xoNuBJ8bVy9oPSIFOekUyQKBgQCIlVMYAlZstKvDTSg7SQqnbebXjdxHoY1pIm6cKHRjBo8ycY6jfvpbSNpP4/7zuFnP/fr/rJtiQvuQSQd/uHZy+5WB5JkXOIDsdnzQ9ighXop7iNrN69S9XlXuug3/YD+9TtW4ViHA0IgCeotcpoizWUJOTxBc+pvI8LS+BjZXjQKBgF4PH8w2QQIJo9kz8g5+w4J3rgeweMgVuZFdTujsaUdQbx/KBy4dwNKBpaFaV873v6mkWw/7d5as1iVZY0+x+LQtYY3NNor6gZCwd7FeANAJmw/gGfP4kbZMXcRVXTPFpSnvnvR7p2red6mvIv0liYBP8ghJrKEe+hZov4tCzaaJAoGAa8zf7p05tUbMIrNtQ42c2RUHE66l4+uAfzBr+Nh9NANjVj8Gg8ietRnFJTUgbyBb0qv1RYLoiR+xCep5/raK1qn3ELRqmEdCil/il9MRTXUe++3CNLEkEeq5DFjQ33UKGdJ1IK7qqRJtpvcts4zFbDjQ+pmwopIyDuDc5vZkQakCgYEAqN12rnYAQK9FCMgqIJXsvqcXBpZ5TqvVqnyEmdhCPWTqlK0fMWI2SSJAmEvhk32pT/1l5KsIVb6ny3mVSxc0XV8uUDEqvokQZU+3Elh50Iy0UjAMNip9lughVz2zpSicEsONPCc6X81ajqY7ahXeZcGwsGohR+go/48qIllEMsY=";
		String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp9osO1VBmfCrg/ySLmASgPHIEx0RnYLVmNryCDeziNHyPXtmWPRMMHlVr5/F/chP+Er5GoXjyidjpGELcPSfmXqttdir7xiZcZ8re8WNdIO6loTT1IPlQ9OfS51CehzgeVH2BLpn1pF+ub9xhPwszmZrgb/rzrGkyaW8v2vFnLaMFTHdbnQ1PiZq+t7xUEYoSUwBKB0tgcfkDIeh2Ot9Bd8NBmpCTUIk5ZD2PyNkWNvLI9SZus7hYisKuiMasXO4vfymUqgJ9USBQAvBbr2Ce1gti4ip5EFnEoQU3daLHpXQjUCAAlLXj/Mgti20Jc3QwmyGVhOcVrbibzt1HOmGfwIDAQAB";
		
		PaymentClient pc = new PaymentClient(baseUrl, company, privateKey, publicKey);
		try {
//			Map<String, Object> aa = pc.createRechargeOrder("8c41c2ff-2c36-4f90-92e0-6c30618dd92d", "https://www.baidu.com", "商品", 2, "177A3213", "ASD", "一件", null);
//			Map<String, Object> aa = pc.queryRechargeOrder("", "10121348452093952");
			
			
			
//			Map<String, String> data = new HashMap<String, String>();
//			data.put("bank", "大唐很行");
//			data.put("bankcard", "62220231242151");
//			data.put("holder", "猪悟能");
//			Map<String, Object> aa = pc.createWithdrawOrder("d141c2ff-2c36-4f90-92e0-6c30618dd93a", "https://www.baidu.com", "提现", 2000, "1412421521521", data, "hehe");
			Map<String, Object> aa = pc.queryWithdrawOrder("", "11016650239011615019008");
			for(String key : aa.keySet()){
				System.out.println(key+":"+aa.get(key));
			}
		} catch (PaymentException e) {
			e.printStackTrace();
		}
	}
}
