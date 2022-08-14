package cn.product.payment.util.wechat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.MD5;


public class WechatUtlity {
	
	// 微信支付统一接口(POST)
	public final static String CREATE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 订单查询接口(POST)
	public final static String QUERY_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
		
	/**
	 * 手机网站支付
	 * @param params
	 * @return
	 */
	public static HashMap<String, Object> createOrder(Map<String, Object> acParams, Map<String, String> params) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "订单创建失败";
		
		String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
		try{
			StringBuffer signString = new StringBuffer();
			signString.append("appid=").append(acParams.get("appid"));
			signString.append("&attach=").append(params.get("attach"));
			signString.append("&body=").append(params.get("body"));
			signString.append("&limit_pay=").append("no_credit");
			signString.append("&mch_id=").append(acParams.get("mchid"));
			signString.append("&nonce_str=").append(nonce_str);
			signString.append("&notify_url=").append(params.get("notify_url"));
			signString.append("&out_trade_no=").append(params.get("out_trade_no"));
			signString.append("&sign_type=").append("MD5");
			signString.append("&spbill_create_ip=").append(params.get("spbill_create_ip"));
			signString.append("&time_expire=").append(params.get("time_expire"));
			signString.append("&total_fee=").append(params.get("total_fee"));
			signString.append("&trade_type=").append("MWEB");
			signString.append("&key=").append(acParams.get("key"));
			
			Map<String,Object> wechatParams = new HashMap<String,Object>();
			wechatParams.put("appid", acParams.get("appid"));
			wechatParams.put("attach", params.get("attach"));
			wechatParams.put("body", params.get("body"));
			wechatParams.put("limit_pay", "no_credit");
			wechatParams.put("mch_id", acParams.get("mchid"));
			wechatParams.put("nonce_str", nonce_str);
			wechatParams.put("sign", MD5.MD5Encode(signString.toString(), "utf-8").toUpperCase());
			wechatParams.put("out_trade_no", params.get("out_trade_no"));
			wechatParams.put("time_expire", params.get("time_expire"));
			wechatParams.put("total_fee", params.get("total_fee"));
			wechatParams.put("spbill_create_ip", params.get("spbill_create_ip"));
			wechatParams.put("notify_url", params.get("notify_url"));
			wechatParams.put("trade_type", "MWEB");
			wechatParams.put("sign_type", "MD5");
			String xmls = XMLUtils.getRequestXml(wechatParams);
			String xml = HttpUtility.postXml(CREATE_ORDER_URL, xmls);
			Map<String, Object> dataMap = XMLUtils.doXMLParse(xml);
			System.out.println(dataMap);
			
			if("SUCCESS".equals(dataMap.get("return_code").toString())){
				String url = dataMap.get("mweb_url").toString();
				String referer = acParams.get("referer").toString();
				
				Map<String, String> resultDataMap = new HashMap<String, String>();
				resultDataMap.put("referer", referer);
				resultDataMap.put("url", url);
				
				message = "订单创建成功！";
				result.put("request", true);
				result.put("result", true);
		        result.put("message", message);
		        result.put("response", JSONUtils.obj2json(resultDataMap));
		        return result;
			}else{
				message = dataMap.get("return_msg").toString();
				result.put("request", false);
				result.put("message", message);
				result.put("result", false);
				return result;
			}
		} catch (Exception e){
			e.printStackTrace();
			result.put("request", false);
			result.put("message", message);
			result.put("result", false);
			return result;
		}
	}
	
	/**
	 * 查询交易信息
	 * @param params
	 * @return
	 */
	public static HashMap<String, Object> queryOrder(Map<String, Object> acParams, String orderNum) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "交易不存在";
		
		String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
		try{
			StringBuffer signString = new StringBuffer();
			signString.append("appid=").append(acParams.get("appid"));
			signString.append("&mch_id=").append(acParams.get("mchid"));
			signString.append("&nonce_str=").append(nonce_str);
			signString.append("&out_trade_no=").append(orderNum);
			signString.append("&sign_type=").append("MD5");
			signString.append("&key=").append(acParams.get("key"));
			
			Map<String,Object> wechatParams = new HashMap<String,Object>();
			wechatParams.put("appid", acParams.get("appid"));
			wechatParams.put("mch_id", acParams.get("mchid"));
			wechatParams.put("nonce_str", nonce_str);
			wechatParams.put("sign", MD5.MD5Encode(signString.toString(), "utf-8").toUpperCase());
			wechatParams.put("out_trade_no", orderNum);
			wechatParams.put("sign_type", "MD5");
			String xmls = XMLUtils.getRequestXml(wechatParams);
			String xml = HttpUtility.postXml(QUERY_ORDER_URL, xmls);
			Map<String, Object> dataMap = XMLUtils.doXMLParse(xml);
			System.out.println(dataMap);
			
			if("SUCCESS".equals(dataMap.get("return_code").toString())){
				message = "订单查询成功！";
				result.put("request", true);
				result.put("result", true);
		        result.put("message", message);
		        result.put("response", JSONUtils.obj2json(dataMap));
		        return result;
			}else{
				message = dataMap.get("return_msg").toString();
				result.put("request", false);
				result.put("message", message);
				result.put("result", false);
				return result;
			}
		} catch (Exception e){
			e.printStackTrace();
			result.put("request", false);
			result.put("message", message);
			result.put("result", false);
			return result;
		}
	}
//	public static void main(String[] args) {
//		Map<String, Object> acParams = new HashMap<String, Object>();
//		acParams.put("appid", "wx845ccda6572bb8da");
//		acParams.put("mchid", "1573077731");
//		acParams.put("key", "9134a47382ac1553b9efc3ecc10c6e6c");
//		acParams.put("referer", "https://www.baidu.com");
//		
//		String orderNum = UUID.randomUUID().toString().replaceAll("-", "");
//		Map<String, String> wechatParams = new HashMap<String, String>();
//		wechatParams.put("body", "商品");
//		wechatParams.put("attach", orderNum);
//		wechatParams.put("nonce_str", orderNum);
//		wechatParams.put("out_trade_no", orderNum);
//		wechatParams.put("total_fee", "100");
//		wechatParams.put("notify_url", "https://www.baidu.com");
//		wechatParams.put("time_expire", Utlity.timestampToWechatString(new Timestamp(System.currentTimeMillis() + 36000)));
//		wechatParams.put("spbill_create_ip", "192.168.1.1");
//		
////		Map<String, Object> resultMap = createOrder(acParams, wechatParams);
//		Map<String, Object> resultMap = queryOrder(acParams, orderNum);
//		for(String key : resultMap.keySet()){
//			System.out.println("==========================================");
//			System.out.println(key + ":" + resultMap.get(key).toString());
//		}
//	}
}
