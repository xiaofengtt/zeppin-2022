package cn.product.treasuremall.util.alipay;


import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.request.ZhimaCreditAntifraudVerifyRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.alipay.api.response.ZhimaCreditAntifraudVerifyResponse;

import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;



public class AliUtlity {

	public static Logger logger = LoggerFactory.getLogger(AliUtlity.class);
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
	public static final String ALI_PID = "2088021828014590";
	
	/**蚂蚁开放平台网关地址
	 * https://openapi.alipaydev.com/gateway.do
	 * https://openapi.alipay.com/gateway.do
	 * */
//	public static final String ALI_URL = "https://openapi.alipaydev.com/gateway.do";
	public static final String ALI_URL = "https://openapi.alipay.com/gateway.do";
//	
//	/**商户私钥*/
//	public static final String APP_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCj6KRLrowbcRAWew0Agn9pgY7nbernUIJVGC/RxIs1uo4LGJsC2p8cxpqGsopTurRvQszGKeQAd4s6nds2vPGO78AUjtxPSd+9cHiPf5No8xCCrBFivJhvIYDXeMYJ6/+KO6+9Y9CYsiNfam62GtqhEZSyTvgsaW22l8VQ5EZKKBxHVUS/j4OAKXDtDUjH5tyKNT71sjpVjgNdIRZxt0S+jQuFSS5lej+Oa2hcCJ1vnqZyvUOpss7n2cBi/j24r0BAgiPf5snY8ti7wpAc637QH6YrrHKAlS3pMn91qDVTPFSDYkRnkRB33GhRJic4v1U3AwDKNdXNHc3hXnSycIevAgMBAAECggEBAKKg1ErnL8qWftDfXIIx+Ls1OhXz4IuMPRSzP9cQ/NLde8wUqNDHG/IQOAgHo+n5qMdv7v97VucDtZf+Qh/ojoA07082g+8DrEQpEOXIPfl2md4dXc6qs1AoXM7t3QjBKLX+DJuMKs8miKRGVPzIXj5L1E6qveBK5vmxUqy1Iey2jW7AV0ANKbDXzi1myKvhtAxarR1Yc1mzhWw8rJtdPNOQGZnz12OUVMbJ2ZkDP9TaT8KPaXmaVOMjs0rurUWojYsxU/aL/4Upm6C+AkA32JQ5+ytkZsp+SnhJh79vZSxbBtUSFs5uVubLBEjR/FRU2gNPmUecIDrwS+eWvL7MSkkCgYEA8bPGllJDd+U+DjnQjUYLkSPNfZAbYd1IRqJI6W6n3pQmFKIKwNhGlqPFBE8h6ntWR9P3MJYh7QmYmPPyjN14soujsSHTlDDnI3jVDeE+lueZlK9nGXWTGVhs6bm7RaS3p+5hM3rbXNAEWMV9BlJqQZRBgV1gL1ELhjIBGy03M8MCgYEArZrMXIt110p9WAa5a1Ihp+sFv0te8jF3h//UyGUuaeIFj0to6QI2gT1Uzc8kk5+kRnWs3bkNZu5jxhsjO6TpVl+shOx95o39kkCya0qntb1PP6oTNMW2rhin0knVDWYV5EMMjfrPtDnZlYI6BvtHtZvoNx5LeNPfAoBh7VK3eaUCgYBLtTQPAdWASJ4XdqSMm9QjskM7gVgSX220MkEEXVTXsy/6ZodXwGbb6JBduSu2dsuf1BUpct1NkiPqRP9EgFq+El9DrITJdkfwJHkXz+X6/rBskkSJBPr+hWQYEcVHG0ErqM9pgKIVgFLcO3/d6xK9V+Ls0oK+T3R8pE0UZiVUYQKBgQCgNXri7NCTHesOkSYMJH9qtzlWj/fPCleE6lMznCx5ClyXIMBwR9qE6lSYmdDnayvu2intdBkqJFVvPRwGrumnDCPph1WoruCTV6FP4lVjIpE/73RJ/yvW/mnhZsF22/7X6Aht/kgvyjNCBiwGxV4n+vkR5KNBnkTvygqVOQCZAQKBgQDfn4HB8ur60y9lwXxcI6CRezV4b7c/8ULSfBAePulLHfYv4PZfEoFeVLYpA6sLgNks2nFOwAWLtTA8xf0EHC1VfJJm4t9IjWgdUICIjvwTQ0W1vSj713G09T5rpFV0Aj0n0Xreb7gqZeOGOkfYXzfkgt1YG09SwceQGcFzM5GF0w==";
//	
//	/**蚂蚁公钥
//	 * MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs6eAj+rXslrvOh68ntdtbOltViD9eDSG3ds/fE5rMeUIB+kpaKoN38k0DNIIwj29h+1bj4vY6tZipu1JBPz74fzJaYAnQ5QpjssR2/rV22PvL5XITj+vrX9nVPkVHOnKLvoTM/CDkd8C9/gtXIm3hwa0ZtO8y60GND6JZ429C91iuKyFASoC6L2ZlOw73+QzRGkcI+mJBHJhIwss/v2l9tBAFH7wHzAeaY4njTp+S7w2UMF2C1+jeEzA3dP5oCT8ReqiYCWLpV2lRO9Sz/VS3DpxJ5q6+RLmYssrQ1t3zUOnWNggT7HfCfDhMLxW56wO+OLW1/KGz4xWdJIttkLvvQIDAQAB
//	 * MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyOfTxCZDXUF3iIgvMWN0OGYmEcYbSBqnzzNp7zOQK+xqiiOBY/9YN9Q0hzNHhAo9g7vGIR72t27hG21tY2Fg7sEmVFnuP5m9n4UHOXZJeRT1Y1mwpZF0EP35xr0nHcDrKhKxzGseqt5ua2RSk7ncT1cR9LyPnDNooXE2rfFJuBj3JZc5IzaRnuHk4itZs9LriIl0m9xLSbMutNx+sirZYBEIThicc4We1bOeHXd5rmXVdyCMl2LC0BApQg4+ppWQUbJZpaS/4rXNDsSZDKo6Ta0yGLbmZ6rZk3UTCqYf6NEAa5TQjGyfXoukbGbitwbJSdWBwzA5Ct4D0hR/Bbz9EQIDAQAB
//	 * */
//	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs6eAj+rXslrvOh68ntdtbOltViD9eDSG3ds/fE5rMeUIB+kpaKoN38k0DNIIwj29h+1bj4vY6tZipu1JBPz74fzJaYAnQ5QpjssR2/rV22PvL5XITj+vrX9nVPkVHOnKLvoTM/CDkd8C9/gtXIm3hwa0ZtO8y60GND6JZ429C91iuKyFASoC6L2ZlOw73+QzRGkcI+mJBHJhIwss/v2l9tBAFH7wHzAeaY4njTp+S7w2UMF2C1+jeEzA3dP5oCT8ReqiYCWLpV2lRO9Sz/VS3DpxJ5q6+RLmYssrQ1t3zUOnWNggT7HfCfDhMLxW56wO+OLW1/KGz4xWdJIttkLvvQIDAQAB";
	
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
	public static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCHRYIuSsyjzT5UlUEXRw5D+Sy4COzg6ky73pUOtncFomfezB3uhXoE4R5ncYGmBElg2YgELb2zU8UA7jAC9q21vCsuAN7r9bRzBjBaZV7XtzPmoIrBMLQ61QIuFulA6XZxghCzx4y+8ycvoz5vHJRHB3c6LS8rLCH02qPEHdNrVElMKxRE1LlPHfYuGPQ3v9hxE19aLeSID35zj2GyAolmLSsTLXMMEPK3Z+byC8ZEYaY4jzpmrI0FwE/c2oECjJe5rb9R6WAtGUVtCHa8R32b4yx/GqrdhS8BlPBBGVEaGBvmVcb9bJKNW9XEdVx6rGTkxaGadzi8Rt678mQz1sG1AgMBAAECggEAVDJXjSjRp0MhANTF5w87OePmicZEatprWKR6cuXvv3oO3QZ/uu8pu8/DdO+ZmxQf+EBuAOvXn78MaD/Zcn7IMM4mskOofYY6Az5e6mUeW3aUv4A93XHLPLNI4BcyqEyohI5Ihx415MTEJ0qcmdxYDr3+4B8vtNeLazSLqo5GAg9JnExLOqO2j9ZHkGAyiKR1yOPjupz9GPGiIWfCZaL3OR4RFT1BJHU3t/F/xy7AXlx/l/ANXmLxZiizNOzj6pzR0XMssfJPKlNFJ0l4fr3OlqxNHIe7M0WINPwASxt8xQV0GDX/5LLbb01KiAFj/7iaw0vkEeJTSfQhWsqnrAr5AQKBgQD9ipNBpqkq5JHh56teqCy920zIrG78NEj61t1fFVhZTpBm3N+v3EX1r6mbUW+HtUTzRSy4wOSivv1sGZJ8MlWgT2jVBEoAlGKhZOgg08oG6WJOmnFRTXYIVw6svD0f0W2TEldKFJLDdy/xE+sHTWh1xoNuBJ8bVy9oPSIFOekUyQKBgQCIlVMYAlZstKvDTSg7SQqnbebXjdxHoY1pIm6cKHRjBo8ycY6jfvpbSNpP4/7zuFnP/fr/rJtiQvuQSQd/uHZy+5WB5JkXOIDsdnzQ9ighXop7iNrN69S9XlXuug3/YD+9TtW4ViHA0IgCeotcpoizWUJOTxBc+pvI8LS+BjZXjQKBgF4PH8w2QQIJo9kz8g5+w4J3rgeweMgVuZFdTujsaUdQbx/KBy4dwNKBpaFaV873v6mkWw/7d5as1iVZY0+x+LQtYY3NNor6gZCwd7FeANAJmw/gGfP4kbZMXcRVXTPFpSnvnvR7p2red6mvIv0liYBP8ghJrKEe+hZov4tCzaaJAoGAa8zf7p05tUbMIrNtQ42c2RUHE66l4+uAfzBr+Nh9NANjVj8Gg8ietRnFJTUgbyBb0qv1RYLoiR+xCep5/raK1qn3ELRqmEdCil/il9MRTXUe++3CNLEkEeq5DFjQ33UKGdJ1IK7qqRJtpvcts4zFbDjQ+pmwopIyDuDc5vZkQakCgYEAqN12rnYAQK9FCMgqIJXsvqcXBpZ5TqvVqnyEmdhCPWTqlK0fMWI2SSJAmEvhk32pT/1l5KsIVb6ny3mVSxc0XV8uUDEqvokQZU+3Elh50Iy0UjAMNip9lughVz2zpSicEsONPCc6X81ajqY7ahXeZcGwsGohR+go/48qIllEMsY=";

	/**蚂蚁公钥 */
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp9osO1VBmfCrg/ySLmASgPHIEx0RnYLVmNryCDeziNHyPXtmWPRMMHlVr5/F/chP+Er5GoXjyidjpGELcPSfmXqttdir7xiZcZ8re8WNdIO6loTT1IPlQ9OfS51CehzgeVH2BLpn1pF+ub9xhPwszmZrgb/rzrGkyaW8v2vFnLaMFTHdbnQ1PiZq+t7xUEYoSUwBKB0tgcfkDIeh2Ot9Bd8NBmpCTUIk5ZD2PyNkWNvLI9SZus7hYisKuiMasXO4vfymUqgJ9USBQAvBbr2Ce1gti4ip5EFnEoQU3daLHpXQjUCAAlLXj/Mgti20Jc3QwmyGVhOcVrbibzt1HOmGfwIDAQAB";

	public static final String ALI_APPID = "2c41c2ff-2c36-4f90-92e0-6c30618dd91d";
	
	public static final String API_URL = "http://192.168.1.101:8080";
	
	public static HashMap<String, Object> certification(String idcard, String username){
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "实名验证失败";
		AlipayClient alipayClient = new DefaultAlipayClient(ALI_URL, ALI_APPID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
		ZhimaCreditAntifraudVerifyRequest request = new ZhimaCreditAntifraudVerifyRequest();
		transAutoIncIdx = new AtomicLong(System.currentTimeMillis());
		request.setBizContent("{" +
		"    \"product_code\":\"w1010100000000002859\"," +
		"    \"transaction_id\":\""+transAutoIncIdx+"\"," +
		"    \"cert_no\":\""+idcard+"\"," +
		"    \"cert_type\":\"IDENTITY_CARD\"," +
		"    \"name\":\""+username+"\"" +
//		"    \"mobile\":\""+mobile+"\"" +
		"  }");
		try {
			ZhimaCreditAntifraudVerifyResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
			  System.out.println("调用成功");
	          System.out.println("欺诈信息验证验证码列表为=" + response.getVerifyCode() + ",bizNo=" + response.getBizNo());
	          boolean falg = true;
	          for(String verifyCode: response.getVerifyCode()){
	        	  if(verifyCode.indexOf("MA") == -1){
	        		  falg = false;
	        		  message = getResultMessage(verifyCode);
	        		  break;
	        	  }
	          }
	          result.put("request", true);
	          Map<String, Object> responseMap = new HashMap<String, Object>();
	          responseMap.put("bizNo", response.getBizNo());
	          responseMap.put("verifyCode", response.getVerifyCode());
	          responseMap.put("product_code", PRODUCT_CODE);
	          responseMap.put("transAutoIncIdx", transAutoIncIdx.toString());
	          responseMap.put("code", response.getCode());
	          responseMap.put("msg", response.getMsg());
	          responseMap.put("sub_code", response.getSubCode());
	          responseMap.put("sub_msg", response.getSubMsg());
	          result.put("response", responseMap);
	          
	          if(falg){
	        	  message = "实名认证成功";
		          result.put("result", true);
		          result.put("message", message);
	          }else{
		          result.put("result", false);
		          result.put("message", message);
	          }
	          return result;
			} else {
				message = message + "，"+response.getSubMsg();
				System.out.println(message+":"+response.getSubMsg());
				result.put("request", false);
				result.put("message", message);
				result.put("result", false);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("request", false);
			result.put("message", message);
			result.put("result", false);
			return result;
		}
		
	}
	
	public static HashMap<String, Object> certification(String idcard, String username,String phone,String bankcard){
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "验证失败";
		AlipayClient alipayClient = new DefaultAlipayClient(ALI_URL, ALI_APPID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
		ZhimaCreditAntifraudVerifyRequest request = new ZhimaCreditAntifraudVerifyRequest();
		transAutoIncIdx = new AtomicLong(System.currentTimeMillis());
		request.setBizContent("{" +
		"    \"product_code\":\"w1010100000000002859\"," +
		"    \"transaction_id\":\""+transAutoIncIdx+"\"," +
		"    \"cert_no\":\""+idcard+"\"," +
		"    \"cert_type\":\"IDENTITY_CARD\"," +
		"    \"name\":\""+username+"\"," +
		"    \"mobile\":\""+phone+"\"," +
		"    \"bank_card\":\""+bankcard+"\"" +
		"  }");
		try {
			ZhimaCreditAntifraudVerifyResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
			  System.out.println("调用成功");
	          System.out.println("欺诈信息验证验证码列表为=" + response.getVerifyCode() + ",bizNo=" + response.getBizNo());
	          boolean falg = true;
	          for(String verifyCode: response.getVerifyCode()){
	        	  if(verifyCode.indexOf("MA") == -1){
	        		  falg = false;
	        		  message = getResultMessage(verifyCode);
	        		  break;
	        	  }
	          }
	          result.put("request", true);
	          Map<String, Object> responseMap = new HashMap<String, Object>();
	          responseMap.put("bizNo", response.getBizNo());
	          responseMap.put("verifyCode", response.getVerifyCode());
	          responseMap.put("product_code", PRODUCT_CODE);
	          responseMap.put("transAutoIncIdx", transAutoIncIdx.toString());
	          responseMap.put("code", response.getCode());
	          responseMap.put("msg", response.getMsg());
	          responseMap.put("sub_code", response.getSubCode());
	          responseMap.put("sub_msg", response.getSubMsg());
	          result.put("response", responseMap);
	          
	          if(falg){
	        	  message = "验证成功";
		          result.put("result", true);
		          result.put("message", message);
	          }else{
		          result.put("result", false);
		          result.put("message", message);
	          }
	          return result;
			} else {
				message = message + "，"+response.getSubMsg();
				System.out.println(message+":"+response.getSubMsg());
				result.put("return", false);
				result.put("message", message);
				result.put("result", false);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("return", false);
			result.put("message", message);
			result.put("result", false);
			return result;
		}
		
	}
	
	public static String getResultMessage(String verifyCode){
		String message = "";
		switch (verifyCode) {
		case "V_PH_NA":
			message = "查询不到手机号信息";
			break;
		case "V_PH_CN_UM":
			message = "手机号与本人不匹配";
			break;
		case "V_PH_CN_MA_UL30D":
//			message = "手机号与本人匹配，30天内有使用";
			message = "认证成功";
			break;
		case "V_PH_CN_MA_UL90D":
//			message = "手机号与本人匹配，90天内有使用";
			message = "认证成功";
			break;
		case "V_PH_CN_MA_UL180D":
//			message = "手机号与本人匹配，180天内有使用";
			message = "认证成功";
			break;
		case "V_PH_CN_MA_UM180D":
//			message = "手机号与本人匹配，180天内没有使用";
			message = "认证成功";
			break;
		case "V_PH_NM_UM":
			message = "手机号与姓名不匹配";
			break;
		case "V_PH_NM_MA_UL30D":
//			message = "手机号与姓名匹配，30天内有使用";
			message = "认证成功";
			break;
		case "V_PH_NM_MA_UL90D":
//			message = "手机号与姓名匹配，90天内有使用";
			message = "认证成功";
			break;
		case "V_PH_NM_MA_UL180D":
//			message = "手机号与姓名匹配，180天内有使用";
			message = "认证成功";
			break;
		case "V_PH_NM_MA_UM180D":
//			message = "手机号与姓名匹配，180天内没有使用";
			message = "认证成功";
			break;
		case "V_CN_NA":
			message = "查询不到身份证信息";
			break;
		case "V_CN_NM_UM":
			message = "姓名与身份证号不匹配";
			break;
		case "V_CN_NM_MA":
			message = "姓名与身份证号匹配";
			break;
		case "V_BC_CN_UK":
//			message = "银行卡号与本人关系未知";
			message = "绑定失败，银行卡号与本人不匹配";
			break;
		case "V_BC_CN_UM":
//			message = "银行卡号与本人不匹配";
			message = "绑定失败，银行卡号与本人不匹配";
			break;
		case "V_BC_CN_MA_UL180D":
//			message = "银行卡号与本人匹配，180天内有使用";
			message = "验证成功";
			break;
		case "V_BC_CN_MA_UL360D":
//			message = "银行卡号与本人匹配，360天内有使用";
			message = "验证成功";
			break;
		case "V_BC_CN_MA_UM360D":
//			message = "银行卡号与本人匹配，360天内没有使用";
			message = "验证成功";
			break;
		case "V_BC_PH_UK":
//			message = "银行卡号与手机号码关系未知";
			message = "绑定失败，银行预留手机号错误";
			break;
		case "V_BC_PH_UM":
//			message = "银行卡号与手机号码不匹配";
			message = "绑定失败，银行预留手机号错误";
			break;
		case "V_BC_PH_MA_UL180D":
//			message = "银行卡号与手机号码匹配，180天内有使用";
			message = "验证成功";
			break;
		case "V_BC_PH_MA_UL360D":
//			message = "行卡号与手机号码匹配，360天内有使用";
			message = "验证成功";
			break;
		case "V_BC_PH_MA_UM360D":
//			message = "银行卡号与手机号码匹配，360天内没有使用";
			message = "验证成功";
			break;
		case "isv.grant-type-invalid":
			message = "参数不正确";
			break;
		case "isv.code-invalid":
			message = "授权码错误";
			break;
		case "isv.refresh-token-invalid":
			message = "刷新令牌错误";
			break;
		case "isv.refresh-token-time-out":
			message = "刷新令牌过期";
			break;
		case "isv.refreshed-token-invalid":
			message = "刷新令牌无效";
			break;
		case "isv.invalid-app-id":
			message = "接口调用失败，未授权";
			break;
		case "isp.unknow-error":
			message = "未知错误";
			break;
		case "REQUIRED_DATE":
			message = "起始和结束时间不能为空";
			break;
		case "ILLEGAL_DATE_FORMAT":
			message = "起始和结束时间格式不正确";
			break;
		case "ILLEGAL_DATE_TOO_LONG":
			message = "起始和结束时间间隔超过最大间隔";
			break;
		case "START_DATE_AFTER_NOW":
			message = "起始时间大于当前时间";
			break;
		case "START_DATE_AFTER_END_DATE":
			message = "起始时间大于结束时间";
			break;
		case "ILLEGAL_PAGE_NO":
			message = "当前页码必须为数据且必须大于0";
			break;
		case "START_DATE_OUT_OF_RANGE":
			message = "查询时间超出范围";
			break;
		case "ILLEGAL_PAGE_SIZE":
			message = "分页大小必须为数字且大于0";
			break;
		case "ILLEGAL_ACCOUNT_LOG_ID":
			message = "账务流水必须为数字且大于0";
			break;
		case "TOO_MANY_QUERY":
			message = "当前查询量太多";
			break;
		case "ACCOUNT_NOT_EXIST":
			message = "要查询的用户不存在";
			break;
		case "ACCESS_ACCOUNT_DENIED":
			message = "无权查询该账户的账务明细";
			break;
		case "SYSTEM_BUSY":
			message = "系统繁忙";
			break;
		case "ILLEGAL_SIGN":
			message = "签名不正确";
			break;
		case "ILLEGAL_ARGUMENT":
			message = "参数不正确";
			break;
		case "ILLEGAL_SERVICE":
			message = "非法服务名称";
			break;
		case "ILLEGAL_USER":
			message = "用户ID不正确";
			break;
		case "ILLEGAL_PARTNER":
			message = "合作伙伴信息不正确";
			break;
		case "ILLEGAL_EXTERFACE":
			message = "接口配置不正确";
			break;
		case "ILLEGAL_PARTNER_EXTERFACE":
			message = "合作伙伴接口信息不正确";
			break;
		case "ILLEGAL_SECURITY_PROFILE":
			message = "未找到匹配的密钥配置";
			break;
		case "ILLEGAL_SIGN_TYPE":
			message = "签名类型不正确";
			break;
		case "ILLEGAL_CHARSET":
			message = "字符集不合法";
			break;
		case "ILLEGAL_CLIENT_IP":
			message = "客户端IP地址无权访问服务";
			break;
		case "HAS_NO_PRIVILEGE":
			message = "未开通此接口权限";
			break;
		case "USER_DATA_MIGRATE_ERROR":
			message = "用户的数据未迁移或者迁移状态未完成";
			break;
		case "ILLEGAL_TRANS_CODE":
			message = "非法账务类别码";
			break;
		case "ACCOUNT_RELEASED":
			message = "账号ID非唯一，查询失败，请改用UID查询";
			break;

		default:
			break;
		}
		return message;
	}

	/**
	 * 获取授权登录签名验证信息
	 * @return
	 */
	public static String getAuthInfo(){
		String authInfo = "";
		transAutoIncIdx = new AtomicLong(System.currentTimeMillis());
		Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(ALI_PID, ALI_APPID, transAutoIncIdx.toString(), true);
		String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
		String sign = OrderInfoUtil2_0.getSign(authInfoMap, APP_PRIVATE_KEY, true);
		authInfo = info + "&" + sign;
		return authInfo;
	}
	
	/**
	 * 获取用户信息授权获取token令牌（换取授权访问令牌）
	 * @param code
	 * @param grantType
	 * @return
	 */
	public static HashMap<String, Object> getUserToken(String code, String grantType){
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "获取失败";
		
		AlipayClient alipayClient = new DefaultAlipayClient(ALI_URL,ALI_APPID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE);
		AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
		request.setGrantType(grantType);
		request.setCode(code);
//		request.setRefreshToken("201208134b203fe6c11548bcabd8da5bb087a83b");
		
		try {
			AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				System.out.println(response.toString());
			System.out.println("调用成功");
			} else {
			System.out.println("调用失败");
			}
//			ZhimaCreditAntifraudVerifyResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				System.out.println("调用成功"+response.getBody());
				result.put("request", true);
				Map<String, Object> responseMap = new HashMap<String, Object>();
		        responseMap.put("user_id", response.getUserId());
		        responseMap.put("access_token", response.getAccessToken());
		        responseMap.put("expires_in", response.getExpiresIn());
		        responseMap.put("target_id", transAutoIncIdx.toString());//业务流水号
		        responseMap.put("refresh_token", response.getRefreshToken());
		        responseMap.put("re_expires_in", response.getReExpiresIn());
		        
		        responseMap.put("code", response.getCode() == null ? "" : response.getCode());
		        responseMap.put("msg", response.getMsg() == null ? "" : response.getMsg());
		        responseMap.put("sub_code", response.getSubCode() == null ? "" : response.getSubCode());
		        responseMap.put("sub_msg", getResultMessage(response.getSubMsg() == null ? "" : response.getSubMsg()));
		          
		        result.put("response", responseMap);
		          
		        message = "获取成功";
		        result.put("result", true);
		        result.put("message", message);
		        return result;
			} else {
				message = message + "，"+getResultMessage(response.getSubMsg() == null ? "" : response.getSubMsg());
				System.out.println(message+":"+response.getSubMsg());
				result.put("request", false);
				result.put("message", message);
				result.put("result", false);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("request", false);
			result.put("message", message);
			result.put("result", false);
			return result;
		}
		
	}
	
	/**
	 * 获取用户信息（支付宝会员授权信息查询接口）
	 * @param accessToken
	 * @return
	 */
	public static HashMap<String, Object> getUserInfo(String accessToken){
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "获取失败";
		
		AlipayClient alipayClient = new DefaultAlipayClient(ALI_URL,ALI_APPID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE);
		AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
		
		
		try {
			AlipayUserInfoShareResponse response = alipayClient.execute(request,accessToken);
			if(response.isSuccess()){
				System.out.println(response.getBody());
			System.out.println("调用成功");
			} else {
			System.out.println("调用失败");
			}
//			ZhimaCreditAntifraudVerifyResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				System.out.println("调用成功"+response.getBody());
				result.put("request", true);
				Map<String, Object> responseMap = new HashMap<String, Object>();
		        responseMap.put("user_id", response.getUserId());
		        responseMap.put("avatar", response.getAvatar());
		        responseMap.put("province", response.getProvince());
		        responseMap.put("target_id", transAutoIncIdx.toString());//业务流水号
		        responseMap.put("city", response.getCity());
		        responseMap.put("nick_name", response.getNickName());
		        responseMap.put("is_student_certified", response.getIsStudentCertified());
		        responseMap.put("user_type", response.getUserType());
		        responseMap.put("user_status", response.getUserStatus());
		        responseMap.put("is_certified", response.getIsCertified());
		        responseMap.put("gender", response.getGender());
		        
		        responseMap.put("code", response.getCode() == null ? "" : response.getCode());
		        responseMap.put("msg", response.getMsg() == null ? "" : response.getMsg());
		        responseMap.put("sub_code", response.getSubCode() == null ? "" : response.getSubCode());
		        responseMap.put("sub_msg", getResultMessage(response.getSubMsg() == null ? "" : response.getSubMsg()));
		        
		        result.put("response", responseMap);
		          
		        message = "获取成功";
		        result.put("result", true);
		        result.put("message", message);
		        return result;
			} else {
				message = message + "，"+getResultMessage(response.getSubMsg() == null ? "" : response.getSubMsg());
				System.out.println(message+":"+response.getSubMsg());
				result.put("request", false);
				result.put("message", message);
				result.put("result", false);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("request", false);
			result.put("message", message);
			result.put("result", false);
			return result;
		}
		
	}
	
	/**
	 * 支付宝手机网站支付
	 * @param params
	 * @return
	 */
	public static HashMap<String, Object> createOrder4wap(Map<String, Object> params) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "订单创建失败";
		
		AlipayClient alipayClient = new DefaultAlipayClient(ALI_URL, ALI_APPID, APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE);
		AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
		try {
			request.setNotifyUrl(Utlity.NOTIFY_URL_ALI_WAP);
			request.setReturnUrl(Utlity.QUIT_URL_ALI_WAP_TEST);
			//准备参数
			String body = params.get("body") == null ? "账户充值" : params.get("body").toString();
			String subject = params.get("subject") == null ? "账户充值" : params.get("subject").toString();//必填--商品标题
			String out_trade_no = params.get("out_trade_no").toString();//必填--商户订单号
			String timeout_express = "5m";//订单超时时间
			Timestamp time = new Timestamp(System.currentTimeMillis());
			String time_now = Utlity.timeSpanToStringLess(new Timestamp(time.getTime()+5*60*1000));
			String time_expire = params.get("time_expire") == null ? time_now : params.get("time_expire").toString();;//绝对超时时间--格式：yyyy-MM-dd HH:mm
			BigDecimal total_amount = BigDecimal.valueOf(Double.valueOf(params.get("total_amount").toString())).setScale(2, BigDecimal.ROUND_HALF_UP);//金额 必填 精确到小数点后两位
			String passback_params = params.get("passback_params") == null ? "" : URLEncoder.encode(params.get("passback_params").toString(), "UTF-8");//公用回传参数 -
			String quit_url = Utlity.QUIT_URL_ALI_WAP_TEST; //必填--用户付款中途退出返回商户网站的地址
//			String notify_url = Utlity.NOTIFY_URL_ALI_WAP; //支付宝服务器主动通知商户服务器里指定的页面http/https路径
//			String business_params = params.get("business_params") == null ? "" : params.get("business_params").toString();//支付宝商户传入业务信息 JSON格式
			
			request.setBizContent("{" +
			"\"body\":\"" + body + "\"," +
			"\"subject\":\"" + subject + "\"," +
			"\"out_trade_no\":\"" + out_trade_no + "\"," +
			"\"timeout_express\":\"" + timeout_express + "\"," +
			"\"time_expire\":\"" + time_expire + "\"," +
			"\"total_amount\":" + total_amount + "," +
			"\"passback_params\":\"" + passback_params + "\"," +
//			"\"notify_url\":\"" + notify_url + "\"," +
			"\"quit_url\":\"" + quit_url + "\"," +
			"\"product_code\":\"" + PRODUCT_CODE_QUICK_WAP_WAY + "\"" +
//			"\"enable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
//			"\"disable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"" +
//			"\"business_params\":\"" + business_params + "\"" +
			"}");
			System.out.println(request.getBizContent());
			AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
			if (response.isSuccess()) {
				logger.info("调用成功！");
				logger.info(response.getBody());
				message = "订单创建成功！";
				result.put("request", true);
				result.put("result", true);
		        result.put("message", message);
		        result.put("response", response.getBody());
		        
			} else {
				logger.info("调用失败！");
				result.put("request", false);
				result.put("result", false);
		        result.put("message", message);
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("request", false);
			result.put("message", message);
			result.put("result", false);
			return result;
		}
	}
	
	/**
	 * 支付宝支付查询交易信息
	 * @param params
	 * @return
	 */
	public static HashMap<String, Object> getOrderInfo(Map<String, Object> params) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "交易不存在";
		AlipayClient alipayClient = new DefaultAlipayClient(ALI_URL, ALI_APPID, APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE);
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		try {
			String out_trade_no = params.get("orderNum").toString();
			request.setBizContent("{" +
					"\"out_trade_no\":\"" + out_trade_no + "\"" +
					"  }");
			AlipayTradeQueryResponse response = alipayClient.execute(request);
			Map<String, Object> responseMap = JSONUtils.json2map(response.getBody());
			if (response.isSuccess()) {
				logger.info("调用成功！");
				logger.info(response.getBody());
				message = "订单创建成功！";
				result.put("request", true);
				result.put("result", true);
		        result.put("message", message);
			} else {
				logger.info("调用失败！");
				result.put("request", false);
				result.put("result", false);
		        result.put("message", message);
			}
			result.put("response", responseMap.get("alipay_trade_query_response"));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("request", false);
			result.put("message", message);
			result.put("result", false);
			return result;
		}
	}


	public static Map<String, Object> createOrder4APIwap() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
//		PaymentClient client = new PaymentClient(API_URL, ALI_APPID, APP_PRIVATE_KEY, ALIPAY_PUBLIC_KEY);
//		result = client.createOrder("8c41c2ff-2c36-4f90-92e0-6c30618dd92d", "https://www.baidu.com", "用户充值", 1000, "138543541524254152", "{\"user\":\"dahaoren\",\"title\":\"is you\"}", "", null);
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, Object> result = createOrder4APIwap();
		
		for(String key: result.keySet()) {
			System.out.println("key:"+key);
			System.out.println("value:" + result.get(key));
			System.out.println("------------------------------------------------------------");
		}
	}
}
