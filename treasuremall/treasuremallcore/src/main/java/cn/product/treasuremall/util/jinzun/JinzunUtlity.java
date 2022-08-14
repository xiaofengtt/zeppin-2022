package cn.product.treasuremall.util.jinzun;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import cn.product.payment.util.api.JSONUtils;
import cn.product.treasuremall.util.HttpClientUtil;
import cn.product.treasuremall.util.Utlity;

public class JinzunUtlity {

	public static final Logger logger= LoggerFactory.getLogger(JinzunUtlity.class);
	
	public static final String APPID = "10270";//xy112233
	public static final String API_CREATE_URL = "http://api.jdzf.net/pay/createOrder";
	public static final String API_QUERY_URL = "http://api.jdzf.net/pay/queryOrder";
	protected static final String API_KEY = "acb94cbf0b5445f49f346340ab922d03";
	
	/*
	 * 生产
	 */
	public static final String NOTICE_URL_RECHARGE = Utlity.PATH_URL + "/notice/recharge/byJinzun";
	
	public static void createorder() throws NoSuchAlgorithmException {
		
	    String merchantId = APPID;
	    String  Moneys    = "401";  //金额
        String    businessId=merchantId;//商户id
        String    outTradeNo="509403547953100951";//20位订单号 时间戳+6位随机字符串组成
        String    random=System.currentTimeMillis() + "";//yyyy-MM-dd HH:mm:ss
        String    notifyUrl=NOTICE_URL_RECHARGE;//通知地址
        String    returnUrl=NOTICE_URL_RECHARGE;//回调地址
        String    amount=Moneys;
        String    remark="0716074a-a1f4-4122-9701-85b6224d6200";
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("businessId", businessId);//商户ID
		map.put("outTradeNo", outTradeNo);//订单号
		map.put("random", random);//下单时间
		map.put("payMethodId", "4");//渠道ID
		map.put("notifyUrl", notifyUrl);//通知地址
		map.put("returnUrl", returnUrl);//回调地址
		map.put("amount", amount);//钱数
		map.put("remark", remark);//自定义参数
		//以上是签名参数
		String sign = getSign(map);
		System.out.println(sign);
		map.put("sign", sign);//签名
		
		Map<String, Object> result = postCreate(map,API_CREATE_URL);
		
		System.out.println(result);
	}
	
	/**
	 * 需要的参数：订单号/下单时间/回调地址/钱数/自定义参数/商品信息/渠道编码
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public static Map<String, Object> createOrder(Map<String, Object> params) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("businessId", APPID);//商户ID
		map.put("outTradeNo", String.valueOf(params.get("orderNum")));//订单号
		map.put("random", String.valueOf(params.get("createtime")));//下单时间
		map.put("payMethodId", String.valueOf(params.get("codeNo")));//渠道ID
		map.put("notifyUrl", NOTICE_URL_RECHARGE);//通知地址
		map.put("returnUrl", String.valueOf(params.get("returnUrl")));//回调地址
		map.put("amount", String.valueOf(params.get("totalAmount")));//钱数
		map.put("remark", String.valueOf(params.get("orderId")));//自定义参数
		String sign = getSign(map);
		logger.info("----------金樽支付签名------------："+sign);
		map.put("sign", sign);//签名
		
		Map<String, Object> result = postCreate(map,API_CREATE_URL);
		return result;
	}
	
	/**
	 * 查询提现订单信息
	 * @return
	 */
	public static Map<String, Object> queryOrder(Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			Map<String, String> map = new HashMap<>();
			map.put("businessId", APPID);
			map.put("outTradeNo", String.valueOf(params.get("orderNum")));//订单号
			map.put("random", String.valueOf(params.get("createtime")));//下单时间
			
			//以上是签名参数
			String sign = getSign(map);
			map.put("sign", sign);//签名
			Map<String, Object> returnMap = postQuery(map,API_QUERY_URL);
			logger.info("-------接口调用成功---------message:"+returnMap.get("message").toString());
			
			if(!(Boolean)returnMap.get("request")) {
				result.put("message", "接口调用失败");
				result.put("result", false);
				return result;
			}
			
			result.put("message", "查询成功");
			result.put("result", true);
			result.put("response", returnMap.get("message"));
		}catch (Exception e) {
			logger.error("-------接口调用异常---------message:接口调用失败exception:"+e.getMessage());
			e.printStackTrace();
			result.put("message", "网络繁忙，请稍后重试！");
			result.put("result", false);
			return result;
		}
		return result;
	}
	
	public static Map<String, Object> postQuery(Map<String, String> params, String url) {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean request = false;
		String message = "失败";
		String resultStr = "";
		try {
			resultStr = HttpClientUtil.post(url, params);
			logger.info("-----------------发送成功--回收消息-------------------："+resultStr);
    		request = true;
    		message = resultStr;
		} catch (Exception e) {
			e.printStackTrace();
			request = false;
    		message = "失败异常";
    		logger.info("-----------------发送异常--回收消息-------------------："+resultStr);
		}
		
		result.put("request", request);
		result.put("message", message);
		return result;
	}
	
	public static Map<String, Object> postCreate(Map<String, String> params, String url) {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean request = false;
		String message = "失败";
		String resultStr = "";
		try {
			resultStr = HttpClientUtil.post(url, params);
			logger.info("-----------------发送成功--回收消息-------------------："+resultStr);
        	if(resultStr.indexOf("<script>") > -1) {//成功
        		request = true;
        		message = resultStr;
        	}else{
        		request = false;
        		Map<String, Object> resultMap = JSONUtils.json2map(resultStr);
        		message = resultMap.get("errorDesc").toString();
        	}
		} catch (Exception e) {
			e.printStackTrace();
			request = false;
    		message = "失败异常";
    		logger.info("-----------------发送异常--回收消息-------------------："+resultStr);
		}
		
		result.put("request", request);
		result.put("message", message);
		return result;
	}
	
	public static Boolean verify(Map<String, String> params) {
		String sign = params.get("sign") == null ? "" : params.get("sign").toString();
		String mySign = getSign(params);
		return sign.equals(mySign);
	}
	
//	/**
//    *
//    * @param sArray
//    * @param key
//    * @return
//	 * @throws NoSuchAlgorithmException 
//    */
//   @SuppressWarnings("rawtypes")
//	public static String getSign(Map sArray) throws NoSuchAlgorithmException {
//       if(sArray!=null && sArray.size()>0){
//           StringBuilder prestr = getSignString(sArray);
//           System.out.println("prestr====>" + prestr);
//           //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
//           return MD5.getMD5(prestr.toString()+"&secret="+API_KEY);
//       }
//       return null;
//   }

   public static String getSign(Map<String, String> params) {
	   params.remove("sign");
       params.put("secret", API_KEY);
       List<String> keys = new ArrayList<String>(params.keySet());
       Collections.sort(keys);
       String prestr = "";
       for (int i = 0; i < keys.size(); i++) {
           String key = keys.get(i);
           String value = params.get(key) + "";
           if (StringUtils.isNotBlank(value)) {
               prestr = prestr + key + "=" + value + "&";
               // System.out.println("排序字段：" + key + "=" + value);
           }
       }
       params.remove("secret");
       // 拼接时，不包括最后一个&字符
       prestr = prestr.substring(0, prestr.length() - 1);
        System.out.println("待签名字段：" + prestr);
       return md5(prestr);
   }
   
	public static String md5(String inStr) {
		try {
			return DigestUtils.md5DigestAsHex(inStr.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return inStr;
   }
   
//	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
//		String params = "{\"random\":1590126592413,\"amount\":5000.00,\"tradeNo\":\"JZ1027020200522014813559763512\",\"outTradeNo\":\"5901264901096452472\",\"businessId\":10270,\"sign\":\"a45c892f54db67c60c75ea9e2bea7a4c\",\"orderState\":2}";
//		try {
//			String aa = HttpClientUtil.post("http://localhost:28080/notice/recharge/byJinzun", json2strmap(params));
//			System.out.print(aa);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		createorder();
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("orderNum", "20200509114000123156");
//		map.put("createtime", System.currentTimeMillis() + "");
//		
//		Map<String, Object> aa = queryOrder(map);
//		logger.info(aa.toString());
//	}
//	
//	public static <T> Map<String,String> json2strmap(String jsonStr){  
//    	Map<String,T> map = JSONUtils.json2obj(jsonStr, Map.class); 
//        Map<String, String> strMap = new HashMap<String, String>();
//        for(String key : map.keySet()){
//        	strMap.put(key, map.get(key) == null ? null : map.get(key).toString());
//        }
//        return strMap;  
//    } 
}
