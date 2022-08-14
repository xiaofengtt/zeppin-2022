package cn.zeppin.product.utility.fuqianla;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.chanpay.ChanPayUtil;
import cn.zeppin.product.utility.fuqianla.data.SinglePaymentQueryRequest;
import cn.zeppin.product.utility.fuqianla.data.SinglePaymentRequest;
import cn.zeppin.product.utility.fuqianla.data.base.RequestData;
import cn.zeppin.product.utility.fuqianla.datastruct.IMsgList;
import cn.zeppin.product.utility.fuqianla.fillpara.FastMsgFill;
import cn.zeppin.product.utility.fuqianla.fillpara.SinglePaymentQueryMsgFill;
import cn.zeppin.product.utility.fuqianla.fillpara.SinglePaymentRequestMsgFill;
import cn.zeppin.product.utility.fuqianla.handler.IMsgListHandler;
import cn.zeppin.product.utility.fuqianla.handler.impl.IMsgListHandlerImpl;
import cn.zeppin.product.utility.fuqianla.json.SinglePaymentJson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FuqianlaUtlity {

	public static Logger logger = LogManager.getLogger(FuqianlaUtlity.class);
	public static String JSON_DATA = "json";
	public static String XML_DATA = "xml";
	public static String TEXT_DATA = "text";
	public final static String MD5_TYPE = "MD5";

	/**
	 * 商户号
	 */
	// TODO 商户号：0080340根据需要修改
	public static String MERCHANT_ID = "0080419";
	// String MERCHANT_ID = "C1800001846";

	// String MERCHANT_ID = "0000651";

	/**
	 * 渠道号
	 */
	// TODO 商户对应渠道号：根据需要修改00000000008
	public static String CHANNEL_ID = "00000000028";

	// String CHANNEL_ID = "00000000028";

	/**
	 * 商户seed
	 */
	// TODO
	// 商户号对应秘钥：根据需要修改zo3n0wl2m7tsedxkmv4wxo7cat7muz3pz1lo0td3rhl1fulfbnri2434hvqw7k93
	// String SEED =
	// "zo3n0wl2m7tsedxkmv4wxo7cat7muz3pz1lo0td3rhl1fulfbnri2434hvqw7k93";
	// fpyr4ic85i0ps66st8nvr9pe9ivjx5j35npr3of33rnk2di8w1ld4iyaitmir5hb
	public static String SEED = "789occc5yjrdbzkvs2c0iw4qrq10f79dxxukdtifg9ofzq9nxihqs0z8zv62hy6l";

	/**
	 * 交易及查询（单付、单扣、批付、批扣）(WebService)
	 * http://merchtest.fuqian.la:8092/ns-channel-
	 * adapter/services/CeFinalPaymentV1_06WS?wsdl
	 * http://113.57.130.107:8083/services/CeFinalPaymentV1_06WS?wsdl
	 * http://cwpay.fuqian.la/services/CeFinalPaymentV1_06WS?wsdl
	 * */
//	public static String WS_URL = "http://merchtest.fuqian.la:8092/ns-channel-adapter/services/CeFinalPaymentV1_06WS?wsdl";
	public static String WS_URL = "http://cwpay.fuqian.la/services/CeFinalPaymentV1_06WS?wsdl";

	/**
	 * 鉴权地址(WebService) http://merchtest.fuqian.la:8092/ns-channel-adapter
	 * http://upay.test.z-bank.com:8083/services/CeVerifyWS?wsdl
	 * */
	public static String CE_VERIFY_URL = "http://merchtest.fuqian.la:8092/ns-channel-adapter/services/CeVerifyWS?wsdl";

	/**
	 * 快捷短信地址(HTTPS)
	 * http://merchtest.fuqian.la:8092/fastpay-adapter/fastPayGetMsgV1_06.htm
	 * http://upay.test.z-bank.com:8083/fastPayGetMsgV1_06.htm
	 * http://cwpay.fuqian.la/fastPayGetMsgV1_06.htm
	 * http://merchtest.fuqian.la:8092/fastpay-adapter/fastPayGetMsgV1_06.htm
	 * */
//	public static String FAST_Msg_URL = "http://cwpay.fuqian.la/fastPayGetMsgV1_06.htm";
	public static String FAST_Msg_URL = "http://merchtest.fuqian.la:8092/fastpay-adapter/fastPayGetMsgV1_06.htm";

	/**
	 * 快捷交易测试地址(HTTPS)
	 * http://merchtest.fuqian.la:8092/fastpay-adapter/fastPayV1_06/fastPay.htm
	 * http://upay.test.z-bank.com:8083/fastPayV1_06/fastPay.htm
	 * http://cwpay.fuqian.la/fastPayNoAccountCard.htm
	 * http://merchtest.fuqian.la:8092/fastpay-adapter/fastPayV1_06/fastPay.htm
	 * */
	public static String FAST_TRADE_URL = "http://cwpay.fuqian.la/fastPayNoAccountCard.htm";

	/**
	 * 快捷交易查询地址(HTTPS)
	 * http://merchtest.fuqian.la:8092/fastpay-adapter/fastPayV1_06/query.htm
	 * http://upay.test.z-bank.com:8083/fastPayV1_06/query.htm
	 * */
	public static String FAST_TRADE_QUERY_URL = "http://merchtest.fuqian.la:8092/fastpay-adapter/fastPayV1_06/query.htm";

	/**
	 * 本地测试通知地址
	 */
	public static String noticeRechargeURL = Utlity.PATH_URL+"/web/notice/fuqianlaRechargeNotice";//支付通知接口地址（充值）
	public static String noticeWithdrawURL = Utlity.PATH_URL+"/web/notice/fuqianlaWithdrawNotice";//提现通知接口地址
	
	/**
	 * B2B、B2C请求交易(HTTPS)
	 * */
	// String E_BANK_URL =
	// "http://upay.test.z-bank.com:8083/gatewayV1_06/gwpayV1_06.htm";

	public static String E_BANK_URL = "http://113.57.130.107:8083/gatewayV1_06/gwpayV1_06.htm";

	/**
	 * B2B、B2C交易查询(HTTPS)
	 * */
	public static String E_BANK_QUERY_URL = "http://upay.test.z-bank.com:8083/gatewayV1_06/query.htm";

	
	/**
	 * 除去数组中的空值和签名参数
	 * 
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, Object> paraFilter(Map<String, Object> sArray) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (sArray == null || sArray.size() <= 0) {
			return result;
		}
		for (String key : sArray.keySet()) {
			String value = (String) sArray.get(key);
			if (value == null || value.equals("")
					|| key.equalsIgnoreCase("signInfo") || key.equalsIgnoreCase("sign_type")) {
				continue;
			}
			result.put(key, value);
		}
		return result;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, Object> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = (String) params.get(key);
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
			
		}
		return prestr;
	}
	
	/**
	 * 签名字符串
	 * 
	 * @param text需要签名的字符串
	 * @param sign
	 *            签名结果
	 * @param key密钥
	 * @param input_charset
	 *            编码格式
	 * @return 签名结果
	 */
	public static boolean verify(String text, String sign, String key,
			String input_charset) {
		text = text + "&" + key;
//		String mysign = DigestUtils
//				.md5Hex(getContentBytes(text, input_charset)).toUpperCase();
		String mysign = MD5.MD5Encode(text.toString(), input_charset);
		System.out.println("签名：" + mysign);
		if (mysign.equals(sign)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 组装请求参数
	 * 
	 * @param seed
	 * @param signInfo
	 * @param requestData
	 * @return
	 * @throws Throwable
	 */
	public static IMsgList<String, Object> before(String seed, Object signInfo,
			Object requestData) throws Exception, Throwable {
		List<RequestData> requestList = null;

		String[] signChoose;
		IMsgListHandler iMsgListHandler = null;

		if (requestData instanceof RequestData[]
				&& signInfo instanceof String[]) {
			int length = ((RequestData[]) requestData).length;
			signChoose = (String[]) signInfo;

			iMsgListHandler = new IMsgListHandlerImpl(signChoose[0],
					signChoose[1]);

			requestList = new ArrayList<RequestData>(length);

			for (RequestData element : (RequestData[]) requestData) {
				requestList.add(element);
			}

		} else {
			// logger.info("请求参数传递出错");
			throw new Exception("请求参数传递出错");
		}

		return iMsgListHandler.returnReqPara(requestList, seed);
	}

	/**
	 * 请求wsdl
	 * 
	 * @param seed
	 * @param url
	 * @param requestHeader
	 * @param signInfo
	 * @param requestData
	 * @return
	 * @throws Throwable
	 */
	public static String tradeForWS(String seed, String url,
			String[] requestHeader, String[] signInfo,
			RequestData... requestData) throws Exception, Throwable {
		HttpReqSend httpReqSend = new HttpReqSend(url);
		IMsgList<String, Object> iMsgList = before(seed, signInfo, requestData);
		return httpReqSend.httpReqSend(
				HttpReqPrepare.combineForXml(iMsgList, requestHeader),
				"application/soap+xml", "utf-8");
	}

	/**
	 * 
	 * @param seed
	 * @param url
	 * @param signInfo
	 * @param requestData
	 * @return
	 * @throws Throwable
	 */
	public static String tradeForPost(String seed, String url,
			String[] signInfo, RequestData... requestData) throws Exception,
			Throwable {
		HttpReqSend httpReqSend = new HttpReqSend(url);
		IMsgList<String, Object> iMsgList = before(seed, signInfo, requestData);
		return httpReqSend.httpReqSend(HttpReqPrepare.combineForPost(iMsgList),
				"application/x-www-form-urlencoded", "utf-8");
	}

	public static void singlePaymentTest() throws Exception, Throwable {
		// TradeMain tradeMain = (TradeMain) new
		// ClassPathXmlApplicationContext("/spring-config.xml").getBean("tradeMainImpl");

		String[] requestHeader = new String[2];
		requestHeader[0] = "<?xml version=\"1.0\"?>"
				+ "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<S:Body>"
				+ "<ns2:singlePayment xmlns:ns2='http://impl.channel.adapter.ns.creditease.com/'>"
				+ "<request>";

		requestHeader[1] = "</request>" + "</ns2:singlePayment>" + "</S:Body>"
				+ "</S:Envelope>";

		String xml = tradeForWS(SEED, WS_URL, requestHeader, new String[] {
				"signInfo", MD5_TYPE }, SinglePaymentRequestMsgFill.fillMsg());
		System.out.println("endxml:--" + xml);
		System.out.println("ednjson:" + XmlUtil.parseXML(xml));
		// 解析成JSON数据
		JSONArray json = XmlUtil.parseXML(xml);
		JSONObject jsObt = json.getJSONObject(0);
		// 转成相应的JSON对象
		SinglePaymentJson sps = (SinglePaymentJson) JSONObject.toJavaObject(
				jsObt, SinglePaymentJson.class);
		System.out.println(sps.getBizId());
		Map<String, Object> mapResult = JSONUtils.json2map(jsObt.toJSONString());
		String sign = sps.getSignInfo();
		mapResult = FuqianlaUtlity.paraFilter(mapResult);
        String linkString = FuqianlaUtlity.createLinkString(mapResult);
        System.out.println(linkString);
        //验签
		Boolean isTrue = FuqianlaUtlity.verify(linkString, sign, FuqianlaUtlity.SEED,ChanPayUtil.charset);
		System.out.println(isTrue);
	}

	public static void querySinglePaymentTest() throws Exception, Throwable {
		String[] requestHeader = new String[2];
		requestHeader[0] = "<?xml version=\"1.0\"?>"
				+ "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<S:Body>"
				+ "<ns2:singlePaymentResultQuery xmlns:ns2='http://impl.channel.adapter.ns.creditease.com/'>"
				+ "<request>";

		requestHeader[1] = "</request>" + "</ns2:singlePaymentResultQuery>"
				+ "</S:Body>" + "</S:Envelope>";

		String xml = tradeForWS(SEED, WS_URL, requestHeader, new String[] {
				"signInfo", MD5_TYPE }, SinglePaymentQueryMsgFill.fillMsg());
		System.out.println("endxml:--" + xml);
		System.out.println("ednjson:" + XmlUtil.parseXML(xml));
		// 解析成JSON数据
		JSONArray json = XmlUtil.parseXML(xml);
		JSONObject jsObt = json.getJSONObject(0);
		// 转成相应的JSON对象
		SinglePaymentJson sps = (SinglePaymentJson) JSONObject.toJavaObject(
				jsObt, SinglePaymentJson.class);
		System.out.println(sps.getBizId());
	}

	/**
	 * 单笔代付查询
	 * 
	 * @param singlePaymentMsg
	 * @throws Exception
	 * @throws Throwable
	 */
	public SinglePaymentJson querySinglePayment(
			SinglePaymentQueryRequest singlePaymentMsg) throws Exception,
			Throwable {
		String[] requestHeader = new String[2];
		requestHeader[0] = "<?xml version=\"1.0\"?>"
				+ "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<S:Body>"
				+ "<ns2:singlePaymentResultQuery xmlns:ns2='http://impl.channel.adapter.ns.creditease.com/'>"
				+ "<request>";

		requestHeader[1] = "</request>" + "</ns2:singlePaymentResultQuery>"
				+ "</S:Body>" + "</S:Envelope>";

		String xml = tradeForWS(SEED, WS_URL, requestHeader, new String[] {
				"signInfo", MD5_TYPE }, SinglePaymentQueryMsgFill.fillMsg());
		System.out.println("endxml:--" + xml);
		System.out.println("ednjson:" + XmlUtil.parseXML(xml));
		// 解析成JSON数据
		JSONArray json = XmlUtil.parseXML(xml);
		JSONObject jsObt = json.getJSONObject(0);
		// 转成相应的JSON对象
		SinglePaymentJson sps = (SinglePaymentJson) JSONObject.toJavaObject(
				jsObt, SinglePaymentJson.class);
		System.out.println(sps.getBizId());
		return sps;
	}

	/**
	 * 单笔代付接口
	 * 
	 * @param singlePayment
	 * @throws Exception
	 * @throws Throwable
	 */
	public SinglePaymentJson singlePayment(SinglePaymentRequest singlePayment)
			throws Exception, Throwable {
		// TradeMain tradeMain = (TradeMain) new
		// ClassPathXmlApplicationContext("/spring-config.xml").getBean("tradeMainImpl");

		String[] requestHeader = new String[2];
		requestHeader[0] = "<?xml version=\"1.0\"?>"
				+ "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<S:Body>"
				+ "<ns2:singlePayment xmlns:ns2='http://impl.channel.adapter.ns.creditease.com/'>"
				+ "<request>";

		requestHeader[1] = "</request>" + "</ns2:singlePayment>" + "</S:Body>"
				+ "</S:Envelope>";

		String xml = tradeForWS(SEED, WS_URL, requestHeader, new String[] {
				"signInfo", MD5_TYPE }, singlePayment);
		System.out.println("endxml:--" + xml);
		System.out.println("ednjson:" + XmlUtil.parseXML(xml));
		// 解析成JSON数据
		JSONArray json = XmlUtil.parseXML(xml);
		JSONObject jsObt = json.getJSONObject(0);
		// 转成相应的JSON对象
		SinglePaymentJson sps = (SinglePaymentJson) JSONObject.toJavaObject(
				jsObt, SinglePaymentJson.class);
		System.out.println(sps.getBizId());
		return sps;
	}
	
	
	public static void fastMsgTest() throws Throwable {
//        TradeMain tradeMain = (TradeMain) new ClassPathXmlApplicationContext("/spring-config.xml").getBean("tradeMainImpl");
//        String seed = MerchantConstant.SEED;
//        String url = UrlConstant.FAST_Msg_URL;
        String json = tradeForPost(SEED, FAST_Msg_URL, new String[]{"signInfo", MD5_TYPE}, FastMsgFill.fillMsg());
        System.out.println(json);
    }
	
	public static void main(String[] args) throws Throwable {
//		singlePaymentTest();
//		querySinglePaymentTest();
		fastMsgTest();
	}
}
