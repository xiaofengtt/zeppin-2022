package cn.product.treasuremall.util.xingda;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.product.treasuremall.util.HttpClientUtil;
import cn.product.treasuremall.util.Utlity;

public class AcicpayUtlity {

	public static final Logger log= LoggerFactory.getLogger(AcicpayUtlity.class);
	
	public static final String APPID = "18024";//2c41c2ff-2c36-4f90-92e0-6c30618dd91d
	public static final String API_URL = "https://acicpay.cn/Pay_Index.html";//2c41c2ff-2c36-4f90-92e0-6c30618dd91d
	public static final String API_CHECK_URL = "https://acicpay.cn/Pay_Trade_query.html";
	protected static final String API_KEY = "oi80tcw5mvkicvugw6axsjj32hva6irc";//2c41c2ff-2c36-4f90-92e0-6c30618dd91d
	
	protected static String charset = "UTF-8";// 编码
	protected static String sign_type = "MD5";// 签名方式，暂时仅支持MD5
	
	/*
	 * 生产
	 */
	public static final String NOTICE_URL_RECHARGE = Utlity.PATH_URL + "/notice/recharge/byAcicpay";
	public static final String NOTICE_URL_WITHDRAW = Utlity.PATH_URL + "/notice/withdraw/byAcicpay";
	
	public static final String CHANNEL_BANKNO_ALIPAYCODE = "1002";//支付宝扫码
	public static final String CHANNEL_BANKNO_ALIPAYWEB = "1003";//支付宝h5
	public static final String CHANNEL_BANKNO_EXP = "1004";//银联快捷
	public static final String CHANNEL_BANKNO_EBANK = "1006";//网银
	
	
	public static void createorder() throws NoSuchAlgorithmException {
		
	    String merchantId = APPID;
	    String  Moneys    = "1";  //金额
        String    pay_memberid=merchantId;//商户id
        String    pay_orderid="20200509114000123456";//20位订单号 时间戳+6位随机字符串组成
        String    pay_applydate="2020-05-09 11:40:00";//yyyy-MM-dd HH:mm:ss
        String    pay_notifyurl=NOTICE_URL_RECHARGE;//通知地址
        String    pay_callbackurl=NOTICE_URL_WITHDRAW;//回调地址
        String    pay_amount=Moneys;
        String    pay_attach="";
        String    pay_productname="100元话费充值";
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("pay_memberid", pay_memberid);//商户ID
		map.put("pay_orderid", pay_orderid);//订单号
		map.put("pay_applydate", pay_applydate);//下单时间
		map.put("pay_bankcode", CHANNEL_BANKNO_ALIPAYCODE);//渠道ID
		map.put("pay_notifyurl", pay_notifyurl);//通知地址
		map.put("pay_callbackurl", pay_callbackurl);//回调地址
		map.put("pay_amount", pay_amount);//钱数
		map.put("pay_attach", pay_attach);//自定义参数
		map.put("pay_productname", pay_productname);//商品名称
		//以上是签名参数
		String sign = BuildMysign(map);
		System.out.println(sign);
		map.put("pay_md5sign", sign);//签名
		
		Map<String, Object> result = postSend(map,API_URL);
		System.out.println(result);
	}
	
	
	public static void checkorderInfo() throws NoSuchAlgorithmException {
		Map<String, String> map = new HashMap<String, String>();
//		map.put("pay_memberid", APPID);//商户ID
		map.put("pay_orderid", "20200509114000123456");//订单号
		String sign = BuildMysign(map);
		log.info("----------兴达支付签名------------："+sign);
		map.put("pay_md5sign", sign);//签名
		Map<String, Object> result = postSend(map,API_CHECK_URL);
		System.out.println(result);
	}
	
	/**
	 * 需要的参数：订单号/下单时间/回调地址/钱数/自定义参数/商品信息/渠道编码
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public static Map<String, String> createOrderInfo(Map<String, Object> params) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pay_memberid", APPID);//商户ID
		map.put("pay_orderid", String.valueOf(params.get("orderNum")));//订单号
		map.put("pay_applydate", String.valueOf(params.get("createtime")));//下单时间
		map.put("pay_bankcode", String.valueOf(params.get("codeNo")));//渠道ID
		map.put("pay_notifyurl", NOTICE_URL_RECHARGE);//通知地址
		map.put("pay_callbackurl", String.valueOf(params.get("returnUrl")));//回调地址
		map.put("pay_amount", String.valueOf(params.get("totalAmount")));//钱数
		map.put("pay_attach", String.valueOf(params.get("orderId")));//自定义参数
		map.put("pay_productname", String.valueOf(params.get("name")));//商品名称
		String sign = BuildMysign(map);
		log.info("----------兴达支付签名------------："+sign);
		map.put("pay_md5sign", sign);//签名
		return map;
		
	}
	
	public static Map<String, String> checkorderInfo(Map<String, Object> params) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pay_memberid", APPID);//商户ID
		map.put("pay_orderid", String.valueOf(params.get("orderNum")));//订单号
		String sign = BuildMysign(map);
		log.info("----------兴达支付签名------------："+sign);
		map.put("pay_md5sign", sign);//签名
		return map;
		
	}
	
	public static Map<String, Object> postSend(Map<String, String> params, String url) {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean request = false;
		String message = "失败";
		String resultStr = "";
		try {
			resultStr = HttpClientUtil.post(url, params);
        	log.info("-----------------发送成功--回收消息-------------------："+resultStr);
        	if("success".equals(resultStr)) {//成功
        		request = true;
        		message = "成功";
        	}
		} catch (Exception e) {
			e.printStackTrace();
			request = false;
    		message = "失败异常";
    		log.info("-----------------发送异常--回收消息-------------------："+resultStr);
		}
		
		//失败就重发
		result.put("request", request);
		result.put("message", message);
		return result;
	}
	
	public static Boolean verify(Map<String, String> params) throws NoSuchAlgorithmException {
		String sign = params.get("sign") == null ? "" : params.get("sign");
		String mySign = BuildMysign(params);
		
		return sign.equals(mySign);
	}
	
	/**
    *
    * @param sArray
    * @param key
    * @return
	 * @throws NoSuchAlgorithmException 
    */
   @SuppressWarnings("rawtypes")
	public static String BuildMysign(Map sArray) throws NoSuchAlgorithmException {
       if(sArray!=null && sArray.size()>0){
           StringBuilder prestr = CreateLinkString(sArray);
           System.out.println("prestr====>" + prestr);
           //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
           return md5(prestr.toString()+"&key="+API_KEY);
       }
       return null;
   }
	
   /**
    * 拼装参数
    * @param params
    * @return
    */
   @SuppressWarnings({ "rawtypes", "unchecked" })
	public static StringBuilder CreateLinkString(Map params){
       List keys = new ArrayList(params.keySet());
       Collections.sort(keys);
       StringBuilder prestr = new StringBuilder();
       String key="";
       String value="";
       for (int i = 0; i < keys.size(); i++) {
           key=(String) keys.get(i);
           value = (String) params.get(key);
           if("".equals(value) || value == null ||
                   key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type") || 
                   key.equalsIgnoreCase("attach") || key.equalsIgnoreCase("pay_attach") || key.equalsIgnoreCase("pay_productname")){
               continue;
           }
           prestr.append(key).append("=").append(value).append("&");
       }
       return prestr.deleteCharAt(prestr.length()-1);
   }
   
	
	/**
	 * 签名加密MD5,字符串大写处理
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String md5(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            //字符数组转换成字符串
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // 32位加密
            return buf.toString().toUpperCase();
            // 16位的加密
             //return buf.toString().substring(8, 24).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } 
//        catch (UnsupportedEncodingException e) {
//            throw new IllegalStateException(
//                    "System doesn't support your  EncodingException.");
//
//        }
    }
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("charset", ReapalUtil.getCharset());
//		map.put("trans_time",
//				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//		map.put("notify_url", ReapalUtil.notify_url_pay);
//		map.put("batch_no", new Timestamp(System.currentTimeMillis()).toString());
//		map.put("batch_count", "2");
//		map.put("batch_amount", "20");
//		map.put("pay_type", "1");
//		System.out.println(BuildMysign(map));
		createorder();
//		checkorderInfo();
	}
}
