package cn.product.treasuremall.util.unionpay;


import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.product.payment.util.api.ApiResultUtlity.ApiResultCode;
import cn.product.payment.util.api.PaymentClient;
import cn.product.treasuremall.util.HttpClientUtil;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;



public class UnionPayUtlity {

	public static Logger logger = LoggerFactory.getLogger(UnionPayUtlity.class);
	
	/**字符编码: GBK 或 UTF-8*/
	public static final String CHARSET = "UTF-8"; 
	
	
	
//	/**商户私钥*/
//	public static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCHRYIuSsyjzT5UlUEXRw5D+Sy4COzg6ky73pUOtncFomfezB3uhXoE4R5ncYGmBElg2YgELb2zU8UA7jAC9q21vCsuAN7r9bRzBjBaZV7XtzPmoIrBMLQ61QIuFulA6XZxghCzx4y+8ycvoz5vHJRHB3c6LS8rLCH02qPEHdNrVElMKxRE1LlPHfYuGPQ3v9hxE19aLeSID35zj2GyAolmLSsTLXMMEPK3Z+byC8ZEYaY4jzpmrI0FwE/c2oECjJe5rb9R6WAtGUVtCHa8R32b4yx/GqrdhS8BlPBBGVEaGBvmVcb9bJKNW9XEdVx6rGTkxaGadzi8Rt678mQz1sG1AgMBAAECggEAVDJXjSjRp0MhANTF5w87OePmicZEatprWKR6cuXvv3oO3QZ/uu8pu8/DdO+ZmxQf+EBuAOvXn78MaD/Zcn7IMM4mskOofYY6Az5e6mUeW3aUv4A93XHLPLNI4BcyqEyohI5Ihx415MTEJ0qcmdxYDr3+4B8vtNeLazSLqo5GAg9JnExLOqO2j9ZHkGAyiKR1yOPjupz9GPGiIWfCZaL3OR4RFT1BJHU3t/F/xy7AXlx/l/ANXmLxZiizNOzj6pzR0XMssfJPKlNFJ0l4fr3OlqxNHIe7M0WINPwASxt8xQV0GDX/5LLbb01KiAFj/7iaw0vkEeJTSfQhWsqnrAr5AQKBgQD9ipNBpqkq5JHh56teqCy920zIrG78NEj61t1fFVhZTpBm3N+v3EX1r6mbUW+HtUTzRSy4wOSivv1sGZJ8MlWgT2jVBEoAlGKhZOgg08oG6WJOmnFRTXYIVw6svD0f0W2TEldKFJLDdy/xE+sHTWh1xoNuBJ8bVy9oPSIFOekUyQKBgQCIlVMYAlZstKvDTSg7SQqnbebXjdxHoY1pIm6cKHRjBo8ycY6jfvpbSNpP4/7zuFnP/fr/rJtiQvuQSQd/uHZy+5WB5JkXOIDsdnzQ9ighXop7iNrN69S9XlXuug3/YD+9TtW4ViHA0IgCeotcpoizWUJOTxBc+pvI8LS+BjZXjQKBgF4PH8w2QQIJo9kz8g5+w4J3rgeweMgVuZFdTujsaUdQbx/KBy4dwNKBpaFaV873v6mkWw/7d5as1iVZY0+x+LQtYY3NNor6gZCwd7FeANAJmw/gGfP4kbZMXcRVXTPFpSnvnvR7p2red6mvIv0liYBP8ghJrKEe+hZov4tCzaaJAoGAa8zf7p05tUbMIrNtQ42c2RUHE66l4+uAfzBr+Nh9NANjVj8Gg8ietRnFJTUgbyBb0qv1RYLoiR+xCep5/raK1qn3ELRqmEdCil/il9MRTXUe++3CNLEkEeq5DFjQ33UKGdJ1IK7qqRJtpvcts4zFbDjQ+pmwopIyDuDc5vZkQakCgYEAqN12rnYAQK9FCMgqIJXsvqcXBpZ5TqvVqnyEmdhCPWTqlK0fMWI2SSJAmEvhk32pT/1l5KsIVb6ny3mVSxc0XV8uUDEqvokQZU+3Elh50Iy0UjAMNip9lughVz2zpSicEsONPCc6X81ajqY7ahXeZcGwsGohR+go/48qIllEMsY=";
//
//	/**聚合公钥 */
//	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp9osO1VBmfCrg/ySLmASgPHIEx0RnYLVmNryCDeziNHyPXtmWPRMMHlVr5/F/chP+Er5GoXjyidjpGELcPSfmXqttdir7xiZcZ8re8WNdIO6loTT1IPlQ9OfS51CehzgeVH2BLpn1pF+ub9xhPwszmZrgb/rzrGkyaW8v2vFnLaMFTHdbnQ1PiZq+t7xUEYoSUwBKB0tgcfkDIeh2Ot9Bd8NBmpCTUIk5ZD2PyNkWNvLI9SZus7hYisKuiMasXO4vfymUqgJ9USBQAvBbr2Ce1gti4ip5EFnEoQU3daLHpXQjUCAAlLXj/Mgti20Jc3QwmyGVhOcVrbibzt1HOmGfwIDAQAB";

	/**商户私钥*/
	public static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCHRYIuSsyjzT5UlUEXRw5D+Sy4COzg6ky73pUOtncFomfezB3uhXoE4R5ncYGmBElg2YgELb2zU8UA7jAC9q21vCsuAN7r9bRzBjBaZV7XtzPmoIrBMLQ61QIuFulA6XZxghCzx4y+8ycvoz5vHJRHB3c6LS8rLCH02qPEHdNrVElMKxRE1LlPHfYuGPQ3v9hxE19aLeSID35zj2GyAolmLSsTLXMMEPK3Z+byC8ZEYaY4jzpmrI0FwE/c2oECjJe5rb9R6WAtGUVtCHa8R32b4yx/GqrdhS8BlPBBGVEaGBvmVcb9bJKNW9XEdVx6rGTkxaGadzi8Rt678mQz1sG1AgMBAAECggEAVDJXjSjRp0MhANTF5w87OePmicZEatprWKR6cuXvv3oO3QZ/uu8pu8/DdO+ZmxQf+EBuAOvXn78MaD/Zcn7IMM4mskOofYY6Az5e6mUeW3aUv4A93XHLPLNI4BcyqEyohI5Ihx415MTEJ0qcmdxYDr3+4B8vtNeLazSLqo5GAg9JnExLOqO2j9ZHkGAyiKR1yOPjupz9GPGiIWfCZaL3OR4RFT1BJHU3t/F/xy7AXlx/l/ANXmLxZiizNOzj6pzR0XMssfJPKlNFJ0l4fr3OlqxNHIe7M0WINPwASxt8xQV0GDX/5LLbb01KiAFj/7iaw0vkEeJTSfQhWsqnrAr5AQKBgQD9ipNBpqkq5JHh56teqCy920zIrG78NEj61t1fFVhZTpBm3N+v3EX1r6mbUW+HtUTzRSy4wOSivv1sGZJ8MlWgT2jVBEoAlGKhZOgg08oG6WJOmnFRTXYIVw6svD0f0W2TEldKFJLDdy/xE+sHTWh1xoNuBJ8bVy9oPSIFOekUyQKBgQCIlVMYAlZstKvDTSg7SQqnbebXjdxHoY1pIm6cKHRjBo8ycY6jfvpbSNpP4/7zuFnP/fr/rJtiQvuQSQd/uHZy+5WB5JkXOIDsdnzQ9ighXop7iNrN69S9XlXuug3/YD+9TtW4ViHA0IgCeotcpoizWUJOTxBc+pvI8LS+BjZXjQKBgF4PH8w2QQIJo9kz8g5+w4J3rgeweMgVuZFdTujsaUdQbx/KBy4dwNKBpaFaV873v6mkWw/7d5as1iVZY0+x+LQtYY3NNor6gZCwd7FeANAJmw/gGfP4kbZMXcRVXTPFpSnvnvR7p2red6mvIv0liYBP8ghJrKEe+hZov4tCzaaJAoGAa8zf7p05tUbMIrNtQ42c2RUHE66l4+uAfzBr+Nh9NANjVj8Gg8ietRnFJTUgbyBb0qv1RYLoiR+xCep5/raK1qn3ELRqmEdCil/il9MRTXUe++3CNLEkEeq5DFjQ33UKGdJ1IK7qqRJtpvcts4zFbDjQ+pmwopIyDuDc5vZkQakCgYEAqN12rnYAQK9FCMgqIJXsvqcXBpZ5TqvVqnyEmdhCPWTqlK0fMWI2SSJAmEvhk32pT/1l5KsIVb6ny3mVSxc0XV8uUDEqvokQZU+3Elh50Iy0UjAMNip9lughVz2zpSicEsONPCc6X81ajqY7ahXeZcGwsGohR+go/48qIllEMsY=";

	/**聚合公钥 */
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAraN+rzeueaXiOSqPRNQExOW65Vcfz0kd8sP+XY+MZZbTL22gLffyPVPMutWaGpNnj9gROtSf1z+nu8D3rMWM1u0pAED/odgtHQmp3BEJGqkT0okrsB0Q5pg1v6xuh5t07LW4x8f7akgC9bnm4jtacg0axffQqxkRQxyvTMw/mwe8YiSRg1TJJeBa9Wx+kT8cFoGJ8cuJJurt7jL0Gn7Pbl3nhTy/43iuwjwn7M/MV0g657P3LFVBtVPNDEJh46ZixqdrqTpt59TfzIhlA5UI5K/gVRzy1j7HP/FvQduhHvgpDdnajzDNSMUjJi7LGFe57FLaAQmY+mv8ZapMpD4RRQIDAQAB";

	
//	public static final String ALI_APPID = "202003260000";//2c41c2ff-2c36-4f90-92e0-6c30618dd91d
	public static final String ALI_APPID = "202004100000";//2c41c2ff-2c36-4f90-92e0-6c30618dd91d
	
	public static final String API_URL = "https://payment.tjiuzhoudy.com";
	
//	public static final String API_URL = "http://192.168.1.113:18080";
	
	
	public static final String RETURN_URL = "http://xydb.jumeiygc.com";

	/*
	 * 生产
	 */
	public static final String NOTICE_URL_RECHARGE = Utlity.PATH_URL + "/notice/recharge/byUnion";
	public static final String NOTICE_URL_WITHDRAW = Utlity.PATH_URL + "/notice/withdraw/byUnion";
	
	/*
	 * 测试
	 */
//	public static final String NOTICE_URL_RECHARGE = "http://192.168.1.102:28080/notice/recharge/byUnion";
//	public static final String NOTICE_URL_WITHDRAW = "http://192.168.1.102:28080/notice/withdraw/byUnion";
	
	public static final Long expireTimeOut = 600000L;//十分钟超时
	
	public static final String CHANNEL_RECHARGE_ALI = "1f1495de-a2b9-42ea-a031-6eef6ec1897d";
	public static final String CHANNEL_WITHDRAW_ALI = "7e083559-9bd1-4344-87ad-339d88d073d5";
	
	/**
	 * 充值
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> createOrder4APIwap(Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		String message = "下单成功";
		Boolean returnFlag = true;
		String channel = params.get("channel").toString();
		String title = params.get("title").toString();
		String orderNum = params.get("orderNum").toString();
		BigDecimal totalAmount = BigDecimal.valueOf(Double.valueOf(params.get("totalAmount").toString()));
//		Integer totalAmount = Integer.valueOf(params.get("totalAmount").toString());
		String returnUrl = params.get("returnUrl") == null ? RETURN_URL : params.get("returnUrl").toString(); 
		String passbackParams = params.get("passbackParams").toString();
		String holder = params.get("holder") == null ? "" : params.get("holder").toString();
		
		//增加clientIp参数--微信支付专用20200619
		String clientIp = params.get("clientIp").toString();
		try {
			String url = API_URL + "/apis/recharge/createOrder";
			Map<String, String> paramsMap = new HashMap<>();
			paramsMap.put("company", ALI_APPID);
			paramsMap.put("channel", channel);
			paramsMap.put("notifyUrl", NOTICE_URL_RECHARGE);
			paramsMap.put("amount", totalAmount.intValue()+"");
			paramsMap.put("orderNum", orderNum);
			paramsMap.put("timestamp", System.currentTimeMillis() + "");
			//以上是签名参数
			String sign = SignUtlity.BuildMysign(paramsMap, ALIPAY_PUBLIC_KEY, CHARSET, true);
			logger.info("-----------充值订单签名------------:"+sign);
			paramsMap.put("sign", sign);//签名
			paramsMap.put("title", title);
			paramsMap.put("passbackParams", passbackParams);
			paramsMap.put("returnUrl", returnUrl);
			paramsMap.put("clientIp", clientIp);
			paramsMap.put("holder", holder);
//			paramsMap.put("timeout", String.valueOf(expireTimeOut));
			Map<String, Object> returnMap = postSend(paramsMap,url);
//			PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
//			Map<String, Object> returnMap = client.createRechargeOrder(channel, NOTICE_URL_RECHARGE, title, totalAmount.intValue(), orderNum, passbackParams, information, returnUrl, expireTimeOut);
			String code = returnMap.get("code").toString();
			logger.info("-------接口调用成功---------code:"+code);
			logger.info("-------接口调用成功---------message:"+returnMap.get("message").toString());
			
			String status = returnMap.get("status").toString().toLowerCase();
			if("fail".equals(status)) {
				message = "网络繁忙，请稍后重试！";
				returnFlag = false;
				result.put("message", message);
				result.put("result", returnFlag);
				return result;
			}
			Map<String, Object> data = (Map<String, Object>)returnMap.get("data");
			result.put("message", message);
			result.put("result", returnFlag);
			result.put("response", data.get("data").toString().split("<script>")[0]);
		} catch (Exception e) {
			logger.error("-------接口调用异常---------message:"+message+"exception:"+e.getMessage());
			e.printStackTrace();
			message = "网络繁忙，请稍后重试！";
			returnFlag = false;
			result.put("message", message);
			result.put("result", returnFlag);
			return result;
		}
		
		return result;
	}
	
	/**
	 * 提现
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> createOrder4APIwithdraw(Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String message = "提现成功";
		Boolean returnFlag = true;
		
		String bank = params.get("bank").toString();
		String bankcard = params.get("bankcard").toString();
		String holder = params.get("holder").toString();
		
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("bank", bank);
		dataMap.put("bankcard", bankcard);
		dataMap.put("holder", holder);
		
		String channel = params.get("channel").toString();
		String title = params.get("title").toString();
		String orderNum = params.get("orderNum").toString();
		BigDecimal totalAmount = BigDecimal.valueOf(Double.valueOf(params.get("totalAmount").toString()));
		String passbackParams = params.get("passbackParams").toString();
		try {
			
			String url = API_URL + "/apis/withdraw/createOrder";
			Map<String, String> paramsMap = new HashMap<>();
			paramsMap.put("company", ALI_APPID);
			paramsMap.put("channel", channel);
			paramsMap.put("notifyUrl", NOTICE_URL_WITHDRAW);
			paramsMap.put("amount", totalAmount.intValue() + "");
			paramsMap.put("orderNum", orderNum);
			paramsMap.put("timestamp", System.currentTimeMillis() + "");
			paramsMap.put("data", JSONUtils.obj2json(dataMap));
			//以上是签名参数
			String sign = SignUtlity.BuildMysign(paramsMap, ALIPAY_PUBLIC_KEY, CHARSET, true);
			logger.info("-----------提现订单签名------------:"+sign);
			paramsMap.put("sign", sign);//签名
			paramsMap.put("title", title);
			paramsMap.put("passbackParams", passbackParams);
			Map<String, Object> returnMap = postSend(paramsMap,url);
			
//			PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
//			Map<String, Object> returnMap = client.createWithdrawOrder(channel, NOTICE_URL_WITHDRAW, title, totalAmount.intValue(), orderNum, dataMap, passbackParams);
			String code = returnMap.get("code").toString();
			logger.info("-------接口调用成功---------code:"+code);
			logger.info("-------接口调用成功---------message:"+returnMap.get("message").toString());
			logger.info("-------接口调用成功---------message:"+JSONUtils.obj2json(returnMap));
			
			String status = returnMap.get("status").toString().toLowerCase();
			if("fail".equals(status)) {
				message = "网络繁忙，请稍后重试！";
				returnFlag = false;
				result.put("message", message);
				result.put("result", returnFlag);
				return result;
			}
			result.put("message", message);
			result.put("result", returnFlag);
		} catch (Exception e) {
			logger.error("-------接口调用异常---------message:"+message+"exception:"+e.getMessage());
			e.printStackTrace();
			message = "网络繁忙，请稍后重试！";
			returnFlag = false;
			result.put("message", message);
			result.put("result", returnFlag);
			return result;
		}

		return result;
	}	
	
	/**
	 * 查询充值订单信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> createOrder4APIwapCheck(Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String message = "查询成功";
		Boolean returnFlag = true;
		
		String orderNum = params.get("orderNum").toString();
		try {
			String url = API_URL + "/apis/recharge/queryOrder";
			Map<String, String> paramsMap = new HashMap<>();
			paramsMap.put("company", ALI_APPID);
			paramsMap.put("paymentOrderNum", "");
			paramsMap.put("orderNum", orderNum);
			paramsMap.put("timestamp", System.currentTimeMillis() + "");
			//以上是签名参数
			String sign = SignUtlity.BuildMysign(paramsMap, ALIPAY_PUBLIC_KEY, CHARSET, true);
			logger.info("-----------充值查单签名------------:"+sign);
			paramsMap.put("sign", sign);//签名
			Map<String, Object> returnMap = postSend(paramsMap,url);
			
//			PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
//			Map<String, Object> returnMap = client.queryRechargeOrder(orderNum, null);
			String code = returnMap.get("code").toString();
			logger.info("-------接口调用成功---------code:"+code);
			logger.info("-------接口调用成功---------message:"+returnMap.get("message").toString());
			
			String status = returnMap.get("status").toString().toLowerCase();
			if(!"success".equals(status)) {
				message = "网络繁忙，请稍后重试！";
				returnFlag = false;
				result.put("message", message);
				result.put("result", returnFlag);
				return result;
			}
			Map<String, Object> data = (Map<String, Object>)returnMap.get("data");
			result.put("message", message);
			result.put("result", returnFlag);
			result.put("response", data.toString());
		}catch (Exception e) {
			logger.error("-------接口调用异常---------message:"+message+"exception:"+e.getMessage());
			e.printStackTrace();
			message = "网络繁忙，请稍后重试！";
			returnFlag = false;
			result.put("message", message);
			result.put("result", returnFlag);
			return result;
		}
		return result;
	}
	
	
	/**
	 * 查询提现订单信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> createOrder4APIwithdrawCheck(Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String message = "查询成功";
		Boolean returnFlag = true;
		
		String orderNum = params.get("orderNum").toString();
		try {
			String url = API_URL + "/apis/withdraw/queryOrder";
			Map<String, String> paramsMap = new HashMap<>();
			paramsMap.put("company", ALI_APPID);
			paramsMap.put("paymentOrderNum", "");
			paramsMap.put("orderNum", orderNum);
			paramsMap.put("timestamp", System.currentTimeMillis() + "");
			//以上是签名参数
			String sign = SignUtlity.BuildMysign(paramsMap, ALIPAY_PUBLIC_KEY, CHARSET, true);
			logger.info("-----------提现查单签名------------:"+sign);
			paramsMap.put("sign", sign);//签名
			Map<String, Object> returnMap = postSend(paramsMap,url);
			
			String code = returnMap.get("code").toString();
			logger.info("-------接口调用成功---------code:"+code);
			logger.info("-------接口调用成功---------message:"+returnMap.get("message").toString());
			
			String status = returnMap.get("status").toString().toLowerCase();
			if(!"success".equals(status)) {
				message = "网络繁忙，请稍后重试！";
				returnFlag = false;
				result.put("message", message);
				result.put("result", returnFlag);
				return result;
			}
			Map<String, Object> data = (Map<String, Object>)returnMap.get("data");
			result.put("message", message);
			result.put("result", returnFlag);
			result.put("response", data.toString());
		}catch (Exception e) {
			logger.error("-------接口调用异常---------message:"+message+"exception:"+e.getMessage());
			e.printStackTrace();
			message = "网络繁忙，请稍后重试！";
			returnFlag = false;
			result.put("message", message);
			result.put("result", returnFlag);
			return result;
		}
		return result;
	}
	
	
	/**
	 * 查询提现订单信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> closeWithdrawOrder(Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String message = "关闭订单成功";
		Boolean returnFlag = true;
		
		String orderNum = params.get("orderNum").toString();
		try {
			String url = API_URL + "/apis/withdraw/closeOrder";
			Map<String, String> paramsMap = new HashMap<>();
			paramsMap.put("company", ALI_APPID);
			paramsMap.put("paymentOrderNum", "");
			paramsMap.put("orderNum", orderNum);
			paramsMap.put("timestamp", System.currentTimeMillis() + "");
			//以上是签名参数
			String sign = SignUtlity.BuildMysign(paramsMap, ALIPAY_PUBLIC_KEY, CHARSET, true);
			logger.info("-----------提现关单签名------------:"+sign);
			paramsMap.put("sign", sign);//签名
			Map<String, Object> returnMap = postSend(paramsMap,url);
			
//			PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
//			Map<String, Object> returnMap = client.closeWithdrawOrder(orderNum, null);
			String code = returnMap.get("code").toString();
			logger.info("-------接口调用成功---------code:"+code);
			logger.info("-------接口调用成功---------message:"+returnMap.get("message").toString());
			
			String status = returnMap.get("status").toString().toLowerCase();
			message = returnCode(code);
			result.put("code", code);
			result.put("message", message);
			if(!"success".equals(status)) {
				//判断code
				returnFlag = false;
				result.put("result", returnFlag);
				return result;
			}
			Map<String, Object> data = (Map<String, Object>)returnMap.get("data");
			result.put("message", message);
			result.put("result", returnFlag);
			result.put("response", data.toString());
		}catch (Exception e) {
			logger.error("-------接口调用异常---------message:"+message+"exception:"+e.getMessage());
			e.printStackTrace();
			message = "网络繁忙，请稍后重试！";
			returnFlag = false;
			result.put("message", message);
			result.put("result", returnFlag);
			return result;
		}
		return result;
	}
	
	/**
	 * code翻译
	 * @param code
	 * @return
	 */
	private static String returnCode(String code) {
		if(ApiResultCode.TRADE_CLOSE_COMPANY_ERROR.name().equals(code)) {
			return ApiResultCode.TRADE_CLOSE_COMPANY_ERROR.toString();
		} else if(ApiResultCode.TRADE_CLOSE_FAIL.name().equals(code)) {
			return ApiResultCode.TRADE_CLOSE_FAIL.toString();
		} else if(ApiResultCode.TRADE_CLOSE_ORDERNUM_EMPTY.name().equals(code)) {
			return ApiResultCode.TRADE_CLOSE_ORDERNUM_EMPTY.toString();
		} else if(ApiResultCode.TRADE_CLOSE_OREDER_ALREADY.name().equals(code)) {
			return ApiResultCode.TRADE_CLOSE_OREDER_ALREADY.toString();
		} else if(ApiResultCode.TRADE_CLOSE_OREDER_DISABLE.name().equals(code)) {
			return ApiResultCode.TRADE_CLOSE_OREDER_DISABLE.toString();
		} else if(ApiResultCode.TRADE_CLOSE_OREDER_NULL.name().equals(code)) {
			return ApiResultCode.TRADE_CLOSE_OREDER_NULL.toString();
		} else if(ApiResultCode.TRADE_CLOSE_SIGN_ERROR.name().equals(code)) {
			return ApiResultCode.TRADE_CLOSE_OREDER_NULL.toString();
		} else if(ApiResultCode.TRADE_CLOSE_TIMESTAMP_TIMEOUT.name().equals(code)) {
			return ApiResultCode.TRADE_CLOSE_OREDER_NULL.toString();
		} else if(ApiResultCode.TRADE_CLOSE_SUCCESS.name().equals(code)) {
			return ApiResultCode.TRADE_CLOSE_SUCCESS.toString();
		} else {
			return "网络繁忙，请稍后重试！";
		}
	}
	public static Map<String, Object> createOrder4APIwap() throws Exception {
//		Map<String, Object> result = new HashMap<String, Object>();
//		
//		PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
//		result = client.createRechargeOrder("1f1495de-a2b9-42ea-a031-6eef6ec1897d", "https://www.baidu.com", "用户充值", 1000, "138543541524254152", "{\"name\":\"value\"}", "充值",RETURN_URL, null);
//		return result;
		Map<String, Object> result = new HashMap<String, Object>();
		String baseUrl = "http://192.168.1.113:18080/";
		String url = baseUrl + "/apis/recharge/createOrder";
		Map<String, String> params = new HashMap<>();
		params.put("company", ALI_APPID);
		params.put("channel", "0746ae3b-49fa-413c-982a-d536d8f25f5d");
		params.put("notifyUrl", "http://192.168.1.116:28080/notice/recharge/byUnion");
		params.put("amount", "2000");
		params.put("orderNum", "177A112133213");
		params.put("timestamp", System.currentTimeMillis() + "");
		//以上是签名参数
		String sign = SignUtlity.BuildMysign(params, ALIPAY_PUBLIC_KEY, CHARSET, true);
		System.out.println(sign);
		params.put("sign", sign);//签名
		params.put("title", "UserRecharge");
		params.put("passbackParams", "uuid");
		params.put("clientIp", "42.200.228.8");
//		params.put("holder", "你大爷");
		params.put("returnUrl", "https://www.baidu.com");
		result = postSend(params,url);
		System.out.println(result);
		//title
		//passbackParams
		return result;
	}
	
	
	public static Map<String, Object> createOrder4APIwithdraw() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String baseUrl = "http://192.168.1.101:18080/";
		String url = baseUrl + "/apis/withdraw/createOrder";
//		PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("bank", "中国银行");
		dataMap.put("bankcard", "165464343463486385");
		dataMap.put("holder", "朱某某");
//		result = client.createWithdrawOrder("d141c2ff-2c36-4f90-92e0-6c30618dd93a", "https://www.baidu.com", "用户提现", 1000, "138543541524254152", dataMap, "");
		Map<String, String> params = new HashMap<>();
		params.put("company", ALI_APPID);
		params.put("channel", "632759dd-2294-4139-be6d-47925912ea92");
		params.put("notifyUrl", "https://www.baidu.com");
		params.put("amount", "20000");
		params.put("orderNum", "177A112133213");
		params.put("timestamp", System.currentTimeMillis() + "");
		params.put("data", JSONUtils.obj2json(dataMap));
		//以上是签名参数
		String sign = SignUtlity.BuildMysign(params, ALIPAY_PUBLIC_KEY, CHARSET, true);
		System.out.println(sign);
		params.put("sign", sign);//签名
		params.put("title", "用户提现");
		params.put("passbackParams", "uuid");
		result = postSend(params,url);
		System.out.println(result);
		//title
		//passbackParams
		return result;
	}
	
	/**
	 * 查询充值订单信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> createOrder4APIwapCheck() throws Exception {
//		Map<String, Object> result = new HashMap<String, Object>();
//		
//		PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
//		result = client.queryRechargeOrder("38684265180958720", null);
//		return result;
		Map<String, Object> result = new HashMap<String, Object>();
		String baseUrl = "http://192.168.1.101:18080/";
		String url = baseUrl + "/apis/recharge/queryOrder";
		Map<String, String> params = new HashMap<>();
		params.put("company", ALI_APPID);
		params.put("paymentOrderNum", "");
		params.put("orderNum", "177A112133213");
		params.put("timestamp", System.currentTimeMillis() + "");
		//以上是签名参数
		String sign = SignUtlity.BuildMysign(params, ALIPAY_PUBLIC_KEY, CHARSET, true);
		System.out.println(sign);
		params.put("sign", sign);//签名
		result = postSend(params,url);
		System.out.println(result);
		//title
		//passbackParams
		return result;
	}
	
	
	/**
	 * 查询提现订单信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> createOrder4APIwithdrawCheck() throws Exception {
//		Map<String, Object> result = new HashMap<String, Object>();
//		
//		PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
//		result = client.queryWithdrawOrder("138543541524254152", null);
//		return result;
		Map<String, Object> result = new HashMap<String, Object>();
//		String baseUrl = "http://192.168.1.101:18080/";
		String url = API_URL + "/apis/withdraw/queryOrder";
		Map<String, String> params = new HashMap<>();
		params.put("company", ALI_APPID);
		params.put("paymentOrderNum", "");
		params.put("orderNum", "48840183164047360");
		params.put("timestamp", System.currentTimeMillis() + "");
		//以上是签名参数
		String sign = SignUtlity.BuildMysign(params, ALIPAY_PUBLIC_KEY, CHARSET, true);
		System.out.println(sign);
		params.put("sign", sign);//签名
		result = postSend(params,url);
		System.out.println(result);
		//title
		//passbackParams
		return result;
	}
	
	
	/**
	 * 关闭充值订单信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> closeRechargeOrder() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
		result = client.closeRechargeOrder("138543541524254152", null);
		return result;
	}
	
	/**
	 * 关闭提现订单信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> closeWithdrawOrder() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
		result = client.closeWithdrawOrder("38946923629121536", null);
		return result;
	}
	
	/**
	 * 异步通知验签
	 * @param paramsls
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static Boolean verify(Map<String, String> paramsls) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(paramsls);
		String sign = paramsls.get("sign");
		String signCheck = SignUtlity.BuildMysign(paramsls, ALIPAY_PUBLIC_KEY, CHARSET, true);
		return sign.equals(signCheck);
	}
	
	public static Map<String, Object> postSend(Map<String, String> params, String url) {
		Map<String, Object> result = new HashMap<String, Object>();
		String resultStr = "";
		try {
			resultStr = HttpClientUtil.post(url, params);
			logger.info("-----------------发送成功--回收消息-------------------："+resultStr);
			result = JSONUtils.json2map(resultStr);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "FAIL");
			result.put("message", "请求异常！");
    		logger.info("-----------------发送异常--回收消息-------------------："+resultStr);
		}
		
		return result;
	}
	public static void main(String[] args) throws Exception {
		Map<String, Object> result = createOrder4APIwap();
		Map<String, Object> data = JSONUtils.json2map(result.get("data").toString());
//		Map<String, Object> data = (Map<String, Object>) result.get("data");
		for(String key: result.keySet()) {
			System.out.println("key:"+key);
			System.out.println("value:" + result.get(key));
			System.out.println("------------------------------------------------------------");
		}
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(data.size());
		String sign = data.get("sign").toString();
		String signCheck = SignUtlity.BuildMysign(data, ALIPAY_PUBLIC_KEY, CHARSET, true);
		System.out.println(sign);
		System.out.println(signCheck);
		for(String key: data.keySet()) {
			System.out.println("key:"+key);
			System.out.println("value:" + data.get(key));
			System.out.println("------------------------------------------------------------");
		}
//		String mapStr = "{\"amount\":\"10000\",\"channel\":\"8c41c2ff-2c36-4f90-92e0-6c30618dd92d\",\"company\":\"2c41c2ff-2c36-4f90-92e0-6c30618dd91d\",\"orderNum\":\"1231111213\",\"paymentOrderNum\":\"10126646600552186777600\",\"sign\":\"Talyz3ccLnh0s%2B2lMeqtT5o%2BK0nEOR0wR6m1pmGuoFiG29Jgg97W66XoPT4y%2BljNYkq8eKkfE00NsgNTMwaVf2A%2FkVHHmp1ZlpBfXWFgAiTionRses3ld%2Fhleha270P%2B4sotlyipNhTRCPsfnoysRJOtWtRU%2FCmSQuT7N5YdJu4WhIb9xO29r2YmUYoPCkkX5JqHdUG3JA5xN4ORnYp0KgpIJaGyoauobqBxTI%2F6DmqD4EI9nmIM5vrbgzfrf6ujvQ4pSMp%2B2%2B96hs3JHlKZ3ay0GhyUedbjCWreCAkoKpNnFBrSyAH8rRb59NAZx5lQ%2BNABzCuTevMWLt%2FO1ByZfw%3D%3D\",\"status\":\"close\",\"timestamp\":\"1585647523728\",\"transData\":\"111211345\"}";
//		Map<String, String> paramsls = new HashMap<String, String>();
//		System.out.println(paramsls);
//		System.out.println(verify(paramsls));
		//{"amount":"990","channel":"d141c2ff-2c36-4f90-92e0-6c30618dd93a","company":"202003260000","orderNum":"138543541524254152","paymentOrderNum":"11016651304103794315264","poundage":"10","timestamp":"1585794474017"}
	}
}
