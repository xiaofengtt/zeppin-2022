package cn.product.payment.util.alipay;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.Utlity;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;



public class AliUtlity {

	public static Logger logger = LoggerFactory.getLogger(AliUtlity.class);
	public static String JSON_DATA = "json";
	public static String XML_DATA = "xml";
	public static String TEXT_DATA = "text";
	
	/**蚂蚁开放平台网关地址
	 * https://openapi.alipay.com/gateway.do
	 * */
	public static final String ALI_URL = "https://openapi.alipay.com/gateway.do";

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
	 * 支付宝手机网站支付
	 * @param params
	 * @return
	 */
	public static HashMap<String, Object> createOrder4wap(Map<String, Object> acParams, Map<String, String> params) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "订单创建失败";
		
		AlipayClient alipayClient = new DefaultAlipayClient(ALI_URL, acParams.get("appid").toString(), acParams.get("privateKey").toString(),FORMAT,CHARSET, acParams.get("publicKey").toString(),SIGN_TYPE);
		AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
		try {
			request.setNotifyUrl(params.get("notify_url").toString());
			request.setReturnUrl(Utlity.QUIT_URL_ALI_WAP);
			request.setBizContent("{" +
					"\"body\":\"" + params.get("body").toString() + "\"," +
					"\"subject\":\"" + params.get("subject").toString() + "\"," +
					"\"out_trade_no\":\"" + params.get("out_trade_no").toString() + "\"," +
					"\"time_expire\":\"" + params.get("time_expire").toString() + "\"," +
					"\"total_amount\":" + params.get("total_amount").toString() + "," +
					"\"passback_params\":\"" + params.get("passback_params").toString() + "\"," +
					"\"quit_url\":\"" + Utlity.QUIT_URL_ALI_WAP + "\"," +
					"\"product_code\":\"" + PRODUCT_CODE_QUICK_WAP_WAY + "\"" +
					"}");
			
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
	public static HashMap<String, Object> getOrderInfo(Map<String, Object> acParams, String orderNum) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "交易不存在";
		AlipayClient alipayClient = new DefaultAlipayClient(ALI_URL, acParams.get("appid").toString(), acParams.get("privateKey").toString(),FORMAT,CHARSET,acParams.get("publicKey").toString(),SIGN_TYPE);
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		try {
			request.setBizContent("{" +
					"\"out_trade_no\":\"" + orderNum + "\"" +
					"  }");
			AlipayTradeQueryResponse response = alipayClient.execute(request);
			Map<String, Object> responseMap = JSONUtils.json2map(response.getBody());
			if (response.isSuccess()) {
				logger.info("调用成功！");
				logger.info(response.getBody());
				message = "订单查询成功！";
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
	
//	public static void main(String[] args) {
//		String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCj6KRLrowbcRAWew0Agn9pgY7nbernUIJVGC/RxIs1uo4LGJsC2p8cxpqGsopTurRvQszGKeQAd4s6nds2vPGO78AUjtxPSd+9cHiPf5No8xCCrBFivJhvIYDXeMYJ6/+KO6+9Y9CYsiNfam62GtqhEZSyTvgsaW22l8VQ5EZKKBxHVUS/j4OAKXDtDUjH5tyKNT71sjpVjgNdIRZxt0S+jQuFSS5lej+Oa2hcCJ1vnqZyvUOpss7n2cBi/j24r0BAgiPf5snY8ti7wpAc637QH6YrrHKAlS3pMn91qDVTPFSDYkRnkRB33GhRJic4v1U3AwDKNdXNHc3hXnSycIevAgMBAAECggEBAKKg1ErnL8qWftDfXIIx+Ls1OhXz4IuMPRSzP9cQ/NLde8wUqNDHG/IQOAgHo+n5qMdv7v97VucDtZf+Qh/ojoA07082g+8DrEQpEOXIPfl2md4dXc6qs1AoXM7t3QjBKLX+DJuMKs8miKRGVPzIXj5L1E6qveBK5vmxUqy1Iey2jW7AV0ANKbDXzi1myKvhtAxarR1Yc1mzhWw8rJtdPNOQGZnz12OUVMbJ2ZkDP9TaT8KPaXmaVOMjs0rurUWojYsxU/aL/4Upm6C+AkA32JQ5+ytkZsp+SnhJh79vZSxbBtUSFs5uVubLBEjR/FRU2gNPmUecIDrwS+eWvL7MSkkCgYEA8bPGllJDd+U+DjnQjUYLkSPNfZAbYd1IRqJI6W6n3pQmFKIKwNhGlqPFBE8h6ntWR9P3MJYh7QmYmPPyjN14soujsSHTlDDnI3jVDeE+lueZlK9nGXWTGVhs6bm7RaS3p+5hM3rbXNAEWMV9BlJqQZRBgV1gL1ELhjIBGy03M8MCgYEArZrMXIt110p9WAa5a1Ihp+sFv0te8jF3h//UyGUuaeIFj0to6QI2gT1Uzc8kk5+kRnWs3bkNZu5jxhsjO6TpVl+shOx95o39kkCya0qntb1PP6oTNMW2rhin0knVDWYV5EMMjfrPtDnZlYI6BvtHtZvoNx5LeNPfAoBh7VK3eaUCgYBLtTQPAdWASJ4XdqSMm9QjskM7gVgSX220MkEEXVTXsy/6ZodXwGbb6JBduSu2dsuf1BUpct1NkiPqRP9EgFq+El9DrITJdkfwJHkXz+X6/rBskkSJBPr+hWQYEcVHG0ErqM9pgKIVgFLcO3/d6xK9V+Ls0oK+T3R8pE0UZiVUYQKBgQCgNXri7NCTHesOkSYMJH9qtzlWj/fPCleE6lMznCx5ClyXIMBwR9qE6lSYmdDnayvu2intdBkqJFVvPRwGrumnDCPph1WoruCTV6FP4lVjIpE/73RJ/yvW/mnhZsF22/7X6Aht/kgvyjNCBiwGxV4n+vkR5KNBnkTvygqVOQCZAQKBgQDfn4HB8ur60y9lwXxcI6CRezV4b7c/8ULSfBAePulLHfYv4PZfEoFeVLYpA6sLgNks2nFOwAWLtTA8xf0EHC1VfJJm4t9IjWgdUICIjvwTQ0W1vSj713G09T5rpFV0Aj0n0Xreb7gqZeOGOkfYXzfkgt1YG09SwceQGcFzM5GF0w==";
//		String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs6eAj+rXslrvOh68ntdtbOltViD9eDSG3ds/fE5rMeUIB+kpaKoN38k0DNIIwj29h+1bj4vY6tZipu1JBPz74fzJaYAnQ5QpjssR2/rV22PvL5XITj+vrX9nVPkVHOnKLvoTM/CDkd8C9/gtXIm3hwa0ZtO8y60GND6JZ429C91iuKyFASoC6L2ZlOw73+QzRGkcI+mJBHJhIwss/v2l9tBAFH7wHzAeaY4njTp+S7w2UMF2C1+jeEzA3dP5oCT8ReqiYCWLpV2lRO9Sz/VS3DpxJ5q6+RLmYssrQ1t3zUOnWNggT7HfCfDhMLxW56wO+OLW1/KGz4xWdJIttkLvvQIDAQAB";
//
//		Map<String, Object> acParams = new HashMap<String, Object>();
//		acParams.put("appid", "2016071901635869");
//		acParams.put("privateKey", privateKey);
//		acParams.put("publicKey", publicKey);
//		
//		Map<String, Object> aliResult = getOrderInfo(acParams, "132190627173412905");
//		Map<String, Object> aa = JSONUtils.json2map(aliResult.get("response").toString());
//		for(String key : aa.keySet()){
//			System.out.println(key+":"+aa.get(key));
//		}
//	}
}
