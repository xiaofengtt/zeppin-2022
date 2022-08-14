package cn.zeppin.product.utility;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import cn.zeppin.product.utility.ali.Alipay;
import cn.zeppin.product.utility.ali.AlipayConfig;
import cn.zeppin.product.utility.ali.AlipaySubmit;
import cn.zeppin.product.utility.ali.OrderInfoUtil2_0;
import cn.zeppin.product.utility.ali.XmlUtil;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.request.ZhimaCreditAntifraudVerifyRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.alipay.api.response.ZhimaCreditAntifraudVerifyResponse;



public class AliUtlity {

	public static Logger logger = LogManager.getLogger(AliUtlity.class);
	public static String JSON_DATA = "json";
	public static String XML_DATA = "xml";
	public static String TEXT_DATA = "text";
	
	/**商户 App ID
	 * 2016081900288878
	 * 2016071901635869
	 * */
//	public static final String ALI_APPID = "2016081900288878";
	public static final String ALI_APPID = "2016071901635869";
	
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
	
	/**商户私钥*/
	public static final String APP_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCj6KRLrowbcRAWew0Agn9pgY7nbernUIJVGC/RxIs1uo4LGJsC2p8cxpqGsopTurRvQszGKeQAd4s6nds2vPGO78AUjtxPSd+9cHiPf5No8xCCrBFivJhvIYDXeMYJ6/+KO6+9Y9CYsiNfam62GtqhEZSyTvgsaW22l8VQ5EZKKBxHVUS/j4OAKXDtDUjH5tyKNT71sjpVjgNdIRZxt0S+jQuFSS5lej+Oa2hcCJ1vnqZyvUOpss7n2cBi/j24r0BAgiPf5snY8ti7wpAc637QH6YrrHKAlS3pMn91qDVTPFSDYkRnkRB33GhRJic4v1U3AwDKNdXNHc3hXnSycIevAgMBAAECggEBAKKg1ErnL8qWftDfXIIx+Ls1OhXz4IuMPRSzP9cQ/NLde8wUqNDHG/IQOAgHo+n5qMdv7v97VucDtZf+Qh/ojoA07082g+8DrEQpEOXIPfl2md4dXc6qs1AoXM7t3QjBKLX+DJuMKs8miKRGVPzIXj5L1E6qveBK5vmxUqy1Iey2jW7AV0ANKbDXzi1myKvhtAxarR1Yc1mzhWw8rJtdPNOQGZnz12OUVMbJ2ZkDP9TaT8KPaXmaVOMjs0rurUWojYsxU/aL/4Upm6C+AkA32JQ5+ytkZsp+SnhJh79vZSxbBtUSFs5uVubLBEjR/FRU2gNPmUecIDrwS+eWvL7MSkkCgYEA8bPGllJDd+U+DjnQjUYLkSPNfZAbYd1IRqJI6W6n3pQmFKIKwNhGlqPFBE8h6ntWR9P3MJYh7QmYmPPyjN14soujsSHTlDDnI3jVDeE+lueZlK9nGXWTGVhs6bm7RaS3p+5hM3rbXNAEWMV9BlJqQZRBgV1gL1ELhjIBGy03M8MCgYEArZrMXIt110p9WAa5a1Ihp+sFv0te8jF3h//UyGUuaeIFj0to6QI2gT1Uzc8kk5+kRnWs3bkNZu5jxhsjO6TpVl+shOx95o39kkCya0qntb1PP6oTNMW2rhin0knVDWYV5EMMjfrPtDnZlYI6BvtHtZvoNx5LeNPfAoBh7VK3eaUCgYBLtTQPAdWASJ4XdqSMm9QjskM7gVgSX220MkEEXVTXsy/6ZodXwGbb6JBduSu2dsuf1BUpct1NkiPqRP9EgFq+El9DrITJdkfwJHkXz+X6/rBskkSJBPr+hWQYEcVHG0ErqM9pgKIVgFLcO3/d6xK9V+Ls0oK+T3R8pE0UZiVUYQKBgQCgNXri7NCTHesOkSYMJH9qtzlWj/fPCleE6lMznCx5ClyXIMBwR9qE6lSYmdDnayvu2intdBkqJFVvPRwGrumnDCPph1WoruCTV6FP4lVjIpE/73RJ/yvW/mnhZsF22/7X6Aht/kgvyjNCBiwGxV4n+vkR5KNBnkTvygqVOQCZAQKBgQDfn4HB8ur60y9lwXxcI6CRezV4b7c/8ULSfBAePulLHfYv4PZfEoFeVLYpA6sLgNks2nFOwAWLtTA8xf0EHC1VfJJm4t9IjWgdUICIjvwTQ0W1vSj713G09T5rpFV0Aj0n0Xreb7gqZeOGOkfYXzfkgt1YG09SwceQGcFzM5GF0w==";
	
	/**蚂蚁公钥
	 * MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs6eAj+rXslrvOh68ntdtbOltViD9eDSG3ds/fE5rMeUIB+kpaKoN38k0DNIIwj29h+1bj4vY6tZipu1JBPz74fzJaYAnQ5QpjssR2/rV22PvL5XITj+vrX9nVPkVHOnKLvoTM/CDkd8C9/gtXIm3hwa0ZtO8y60GND6JZ429C91iuKyFASoC6L2ZlOw73+QzRGkcI+mJBHJhIwss/v2l9tBAFH7wHzAeaY4njTp+S7w2UMF2C1+jeEzA3dP5oCT8ReqiYCWLpV2lRO9Sz/VS3DpxJ5q6+RLmYssrQ1t3zUOnWNggT7HfCfDhMLxW56wO+OLW1/KGz4xWdJIttkLvvQIDAQAB
	 * MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyOfTxCZDXUF3iIgvMWN0OGYmEcYbSBqnzzNp7zOQK+xqiiOBY/9YN9Q0hzNHhAo9g7vGIR72t27hG21tY2Fg7sEmVFnuP5m9n4UHOXZJeRT1Y1mwpZF0EP35xr0nHcDrKhKxzGseqt5ua2RSk7ncT1cR9LyPnDNooXE2rfFJuBj3JZc5IzaRnuHk4itZs9LriIl0m9xLSbMutNx+sirZYBEIThicc4We1bOeHXd5rmXVdyCMl2LC0BApQg4+ppWQUbJZpaS/4rXNDsSZDKo6Ta0yGLbmZ6rZk3UTCqYf6NEAa5TQjGyfXoukbGbitwbJSdWBwzA5Ct4D0hR/Bbz9EQIDAQAB
	 * */
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs6eAj+rXslrvOh68ntdtbOltViD9eDSG3ds/fE5rMeUIB+kpaKoN38k0DNIIwj29h+1bj4vY6tZipu1JBPz74fzJaYAnQ5QpjssR2/rV22PvL5XITj+vrX9nVPkVHOnKLvoTM/CDkd8C9/gtXIm3hwa0ZtO8y60GND6JZ429C91iuKyFASoC6L2ZlOw73+QzRGkcI+mJBHJhIwss/v2l9tBAFH7wHzAeaY4njTp+S7w2UMF2C1+jeEzA3dP5oCT8ReqiYCWLpV2lRO9Sz/VS3DpxJ5q6+RLmYssrQ1t3zUOnWNggT7HfCfDhMLxW56wO+OLW1/KGz4xWdJIttkLvvQIDAQAB";
	
	 /**编码格式*/
	public static final String FORMAT = "json";
	
	/**字符编码: GBK 或 UTF-8*/
	public static final String CHARSET = "UTF-8";
	
	/**加密方式:RSA或RSA2 */
	public static final String SIGN_TYPE = "RSA2";
	
	/**产品码,商户同蚂蚁签订合约时获取的产品码,欺诈信息验证产品请使用:w1010100000000002859*/
	public static final String PRODUCT_CODE = "w1010100000000002859";
	
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
			// TODO: handle exception
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
			// TODO: handle exception
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
		
		AlipayClient alipayClient = new DefaultAlipayClient(ALI_URL,ALI_APPID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,"RSA2");
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
			// TODO: handle exception
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
		
		AlipayClient alipayClient = new DefaultAlipayClient(ALI_URL,ALI_APPID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,"RSA2");
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
			// TODO: handle exception
			e.printStackTrace();
			result.put("request", false);
			result.put("message", message);
			result.put("result", false);
			return result;
		}
		
	}
	
	public static Alipay getBillList(String starttime, String endtime, String pageNum){
		String page_no = pageNum;
        // //////////////// 把请求参数打包成数组,发给支付宝
        Map<String, String> sParaTemp = new HashMap<String, String>();
        // 输入必须输参数
        sParaTemp.put("service", "account.page.query");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("page_no", page_no);
//        sParaTemp.put("logon_id", "18611920344");
        sParaTemp.put("gmt_start_time", starttime);
        sParaTemp.put("gmt_end_time", endtime);
//        sParaTemp.put("iw_account_log_id", "303187660684231");
        // 打包参数，用作验签的时候用
     
        URL url = null;
        URLConnection connec = null;
        BufferedReader reader = null;
        InputStream input = null;
        Alipay alipay = null;
        try {

            //***************拼接发送给支付宝的地址
            StringBuffer urlBuffer = new StringBuffer();
            urlBuffer.append("https://mapi.alipay.com/gateway.do");
            // _input_charset
            urlBuffer.append("?_input_charset=utf-8");
            // sign
            urlBuffer.append("&sign=").append(
                    AlipaySubmit.buildRequestMysign(sParaTemp));
            // _input_charset
            urlBuffer.append("&_input_charset=").append(
                    AlipayConfig.input_charset);
            // gmt_end_time
            urlBuffer.append("&gmt_end_time=").append(
                    URLEncoder.encode(endtime, "UTF-8"));
            // sign_type
            urlBuffer.append("&sign_type=").append(AlipayConfig.sign_type);
            // service
            urlBuffer.append("&service=").append("account.page.query");
            // partner
            urlBuffer.append("&partner=").append(AlipayConfig.partner);
            // page_no
            urlBuffer.append("&page_no=").append(page_no);
//            urlBuffer.append("&logon_id=").append("18611920344");
            // gmt_start_time
            urlBuffer.append("&gmt_start_time=").append(
                    URLEncoder.encode(starttime, "UTF-8"));
            // iw_account_log_id
//            urlBuffer.append("&iw_account_log_id=").append("303187660684231");
           
             //***********将拼接好的地址转换为一个URL,然后执行openConnection方法，打开发完支付宝
            url = new URL(urlBuffer.toString());
            System.out.println(urlBuffer.toString());
            connec = url.openConnection();
            //设置服务器超时时间
            connec.setConnectTimeout(TIME_OUT);
            connec.setReadTimeout(TIME_OUT);
            
           //***********将返回的URLConnection获取 getInputStream返回的字符流InputStream
            input = connec.getInputStream();

          //*********将返回的字符流转换为BufferedReader 
            reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String strTemp = null;
          //********  将BufferedReader  转化为StringBuffer
           while ((strTemp = reader.readLine()) != null) {
                sb.append(strTemp);
            }
            // StringBuffer转化为String
//            String res = sb.toString();
           //转化成一个xml格式的文档，碰到“><”就换行
//            String rexml = res.replace("><", ">\n<");
           //解析返回的xml，获取返回的支付宝帐务列表
//            alitemp = jiexiAlis(rexml);
            System.out.println(sb.toString());
//            result = XMLUtils.doXMLParse(sb.substring(0));
            alipay = XmlUtil.xmlToObject(sb.toString(), Alipay.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                	e.printStackTrace();
//                    logger.fatal(e.toString());
                }
            }
        }
        return alipay;
	}
	
	public static void main(String[] args) throws AlipayApiException {
//		 // "transaction_id" 是代表一笔请求的唯一标志，该标识作为对账的关键信息，
//        // 对于相同 transaction_id 的查询，芝麻在一天（86400秒）内返回首次查询数据，
//        // 超过有效期的查询即为无效并返回异常（错误码:TRANSACTION_ID_EXPIRED），
//        // 有效期内的重复查询不重新计费。
//        String transactionId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
//                               + transAutoIncIdx.getAndDecrement();
//
//        AlipayClient alipayClient = new DefaultAlipayClient(ALI_URL, ALI_APPID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
//
//        ZhimaCreditAntifraudVerifyRequest request = new ZhimaCreditAntifraudVerifyRequest();
//        request.setBizContent("{\"transaction_id\":\"" + transactionId + "\",\"product_code\":\""
//                              + PRODUCT_CODE + "\",\"cert_no\":\"640202199007164686\","
//                              + "\"cert_type\":\"100\"," + "    \"name\":\"牛德华\","
//                              + "\"mobile\":\"15843991158\","
//                              + "\"email\":\"jnlxhy@alitest.com\","
//                              + "\"bank_card\":\"20110602436748024138\","
//                              + "\"address\":\"北京 北京市 朝阳区 呼家楼街道北京市朝阳区东三环中路1号环球金融中心 东塔9层\","
//                              + "\"ip\":\"101.247.161.1\","
//                              + "\"mac\":\"AA-34-4D-59-61-28\","
//                              + "\"wifimac\":\"22-35-4A-5F-07-88\","
//                              + "\"imei\":\"868331011992179\"" + "}");
//        ZhimaCreditAntifraudVerifyResponse response;
//        try {
//            // 请求记录
//            System.out.println("send zhimaCreditAntifraudVerifyRequest request="
//                               + request.getBizContent());
//            response = alipayClient.execute(request);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            // 一般是网络异常 建议重试 入参(包括transaction_id)不变 不会重复计费
//            try {
//                // 纪录请求
//                System.out.println("send zhimaCreditAntifraudVerifyRequest retry request="
//                                   + request.getBizContent());
//                response = alipayClient.execute(request);
//            } catch (AlipayApiException e1) {
//                // 异常时请记录transactionId排查问题
//                System.err.println("send zhimaCreditAntifraudVerifyRequest error request="
//                                   + request.getBizContent());
//                e1.printStackTrace();
//                // 包装成业务异常抛出
//                throw new RuntimeException(e1);
//            }
//        }
//
//        if (response.isSuccess()) {
//            // 取得欺诈信息验证VerifyCode列表，各个VerifyCode具体含义参见芝麻信用提供的详细技术对接文档
//            // 建议记录bizNo用于核对
//            System.out.println("欺诈信息验证验证码列表为=" + response.getVerifyCode() + ",bizNo="
//                               + response.getBizNo());
//        } else {
//            // 处理各种异常 异常时请记录transactionId排查问题
//            System.out.println("查询芝麻信用-欺诈信息验证的错误 code=" + response.getCode() + ",msg="
//                               + response.getMsg() + ",transactionId=" + transactionId);
//        }
		transAutoIncIdx = new AtomicLong(System.currentTimeMillis());
		AlipayClient alipayClient = new DefaultAlipayClient(ALI_URL, ALI_APPID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
		ZhimaCreditAntifraudVerifyRequest request = new ZhimaCreditAntifraudVerifyRequest();
		request.setBizContent("{" +
		"    \"product_code\":\"w1010100000000002859\"," +
		"    \"transaction_id\":\""+transAutoIncIdx+"\"," +
		"    \"cert_no\":\"372330199210225111\"," +
		"    \"cert_type\":\"IDENTITY_CARD\"," +
		"    \"name\":\"刘运涛\"," +
		"    \"mobile\":\"18612033494\"," +
//		"    \"email\":\"jnlxhy@alitest.com\"," +
		"    \"bank_card\":\"6214830164014572\"" +
//		"    \"address\":\"北京 北京市 朝阳区 呼家楼街道北京市朝阳区东三环中路1号环球金融中心 东塔9层\"," +
//		"    \"ip\":\"101.247.161.1\"," +
//		"    \"mac\":\"AA-34-4D-59-61-28\"," +
//		"    \"wifimac\":\"22-35-4A-5F-07-88\"," +
//		"    \"imei\":\"868331011992179\"" +
		"  }");
		System.out.println(request);
		ZhimaCreditAntifraudVerifyResponse response = alipayClient.execute(request);
		if(response.isSuccess()){
		    System.out.println("调用成功");
		    System.out.println(response.getBody());
          System.out.println("欺诈信息验证验证码列表为=" + response.getVerifyCode() + ",bizNo="
          + response.getBizNo());
		} else {
		    System.out.println("调用失败");
		}
		
//		AlipayClient alipayClient = new DefaultAlipayClient(ALI_URL, ALI_APPID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
////		AlipayClient alipayClient = new DefaultAlipayClient(ALI_URL, ALI_APPID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
//		AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
//		request.setBizContent("{" +
//		"\"bill_type\":\"signcustomer\"," +
//		"\"bill_date\":\"2017-10-27\"" +
//		"}");
//		AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
//		if(response.isSuccess()){
//		System.out.println("调用成功");
//		} else {
//		System.out.println("调用失败");
//		}
//		System.out.println("V_BC_PH_UM".indexOf("MA"));
//		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",ALI_APPID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,"RSA2");
//		AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
//		request.setGrantType("authorization_code");
//		request.setCode("aa377979997b454a88ae8a369fe6OX48");
////		request.setRefreshToken("201208134b203fe6c11548bcabd8da5bb087a83b");
//		AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
//		if(response.isSuccess()){
//			System.out.println(response.toString());
//		System.out.println("调用成功");
//		} else {
//		System.out.println("调用失败");
//		}
		
//		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",ALI_APPID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,"RSA2");
//		AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
//		AlipayUserInfoShareResponse response = alipayClient.execute(request,"kuaijieBab6d32c5b0024e17a47c7f9bbc8b4X48");
//		if(response.isSuccess()){
//			System.out.println(response.getBody());
//		System.out.println("调用成功");
//		} else {
//		System.out.println("调用失败");
//		}
	}
    
}
