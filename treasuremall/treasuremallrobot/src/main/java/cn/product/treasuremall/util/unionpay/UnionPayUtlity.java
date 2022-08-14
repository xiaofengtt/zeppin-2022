package cn.product.treasuremall.util.unionpay;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.product.payment.util.api.ApiResultUtlity.ApiResultCode;
import cn.product.payment.util.api.PaymentClient;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;



public class UnionPayUtlity {

	public static Logger logger = LoggerFactory.getLogger(UnionPayUtlity.class);
	public static String JSON_DATA = "json";
	public static String XML_DATA = "xml";
	public static String TEXT_DATA = "text";
	
	/**商户 App ID
	 * 2016081900288878
	 * 2016071901635869
	 * */
//	public static final String ALI_APPID = "2016081900288878";2017083108482534
//	public static final String ALI_APPID = "2016071901635869";
	
	/**
	 * 商户PID
	 */
	public static final String ALI_PID = "202003260000";
	
	/**蚂蚁开放平台网关地址
	 * https://openapi.alipaydev.com/gateway.do
	 * https://openapi.alipay.com/gateway.do
	 * */
//	public static final String ALI_URL = "https://openapi.alipaydev.com/gateway.do";
	public static final String ALI_URL = "https://backadmin.niutoulicai.com/payment";
	
	 /**编码格式*/
	public static final String FORMAT = "json";
	
	/**字符编码: GBK 或 UTF-8*/
	public static final String CHARSET = "UTF-8";
	
	/**加密方式:RSA或RSA2 */
	public static final String SIGN_TYPE = "RSA2";
	
	/**产品码,商户同蚂蚁签订合约时获取的产品码,欺诈信息验证产品请使用:w1010100000000002859*/
	public static final String PRODUCT_CODE = "w1010100000000002859";
	
	public static final String PRODUCT_CODE_QUICK_WAP_WAY = "QUICK_WAP_WAY";
	
	public static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
	
	public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
	
	/**用于生成TransactionId的自增器*/
	public static AtomicLong transAutoIncIdx;
	
	public static String trans_code_msg_transfer = "转账";
	public static String trans_code_msg_charge = "收费";
	public static String trans_code_msg_recharge = "充值";
	public static String trans_code_msg_withdraw = "提现";
	public static String trans_code_msg_reticket = "退票";
	public static String trans_code_msg_olpayment = "在线支付";
	
	public static final Integer TIME_OUT = 8000;//设置服务器超时时间 单位：毫秒
	/**
	 *交易状态 
	 */
	public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY"; //交易创建，等待买家付款
	public static final String TRADE_CLOSED = "TRADE_CLOSED"; //未付款交易超时关闭，或支付完成后全额退款
	public static final String TRADE_SUCCESS = "TRADE_SUCCESS"; //交易支付成功
	public static final String TRADE_FINISHED = "TRADE_FINISHED"; //交易结束，不可退款
	
	/**
	 * 业务错误码
	 */
	public static final String CODE_10000 = "10000"; //
	public static final String CODE_20000 = "20000"; //
	public static final String CODE_20001 = "20001"; //
	public static final String CODE_40001 = "40001"; //
	public static final String CODE_40002 = "40002"; //
	public static final String CODE_40004 = "40004"; //
	public static final String CODE_40006 = "40006"; //
	public static final String SUB_CODE_SYSTEM_ERROR = "ACQ.SYSTEM_ERROR";
	public static final String SUB_CODE_INVALID_PARAMETER = "ACQ.INVALID_PARAMETER";
	public static final String SUB_CODE_TRADE_NOT_EXIST = "ACQ.TRADE_NOT_EXIST";
	
	
	/**商户私钥*/
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
	
//	public static final String API_URL = "http://alipay.yyxunyue.com";
	
	public static final String API_URL = "https://payment.tjiuzhoudy.com";
	
//	public static final String API_URL = "http://192.168.1.101:18080";
	
//	public static final String API_URL = "https://backadmin.niutoulicai.com/payment";

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
		
		String passbackParams = params.get("passbackParams").toString();
		String information = params.get("information").toString();
		
		try {
			PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
			Map<String, Object> returnMap = client.createRechargeOrder(channel, NOTICE_URL_RECHARGE, title, totalAmount.intValue(), orderNum, passbackParams, information, expireTimeOut);
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
		
		PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
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
		
		Map<String, Object> returnMap = client.createWithdrawOrder(channel, NOTICE_URL_WITHDRAW, title, totalAmount.intValue(), orderNum, dataMap, passbackParams);
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
//		Map<String, Object> data = (Map<String, Object>)returnMap.get("data");
		result.put("message", message);
		result.put("result", returnFlag);
//		result.put("response", data.get("data").toString());
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
			PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
			Map<String, Object> returnMap = client.queryRechargeOrder(orderNum, null);
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
			PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
			Map<String, Object> returnMap = client.queryWithdrawOrder(orderNum, null);
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
			PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
			Map<String, Object> returnMap = client.closeWithdrawOrder(orderNum, null);
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
		Map<String, Object> result = new HashMap<String, Object>();
		
		PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
		result = client.createRechargeOrder("1f1495de-a2b9-42ea-a031-6eef6ec1897d", "https://www.baidu.com", "用户充值", 1000, "138543541524254152", "{\"name\":\"value\"}", "充值", null);
		return result;
	}
	
	
	public static Map<String, Object> createOrder4APIwithdraw() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("bank", "中国银行");
		dataMap.put("bankcard", "165464343463486385");
		dataMap.put("holder", "朱某某");
		result = client.createWithdrawOrder("7e083559-9bd1-4344-87ad-339d88d073d5", "https://www.baidu.com", "用户提现", 1000, "138543541524254152", dataMap, "");
		return result;
	}
	
	/**
	 * 查询充值订单信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> createOrder4APIwapCheck() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
		result = client.queryRechargeOrder("38684265180958720", null);
		return result;
	}
	
	
	/**
	 * 查询提现订单信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> createOrder4APIwithdrawCheck() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
		result = client.queryWithdrawOrder("138543541524254152", null);
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
	 */
	public static Boolean verify(Map<String, String> paramsls) {
		System.out.println(paramsls);
		PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
		return client.checkOrder(paramsls);
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, Object> result = closeWithdrawOrder();
		Map<String, Object> data = JSONUtils.json2map(result.get("data").toString());
//		Map<String, Object> data = (Map<String, Object>) result.get("data");
		for(String key: result.keySet()) {
			System.out.println("key:"+key);
			System.out.println("value:" + result.get(key));
			System.out.println("------------------------------------------------------------");
		}
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
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
