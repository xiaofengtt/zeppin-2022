package cn.product.worldmall.util.sms.yunpian;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SmsSendByYunpian {
	// 查账户信息的http地址
	private static String URI_GET_USER_INFO = "https://sms.yunpian.com/v2/user/get.json";

	// 智能匹配模板发送接口的http地址
	private static String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";

	// 模板发送接口的http地址
	private static String URI_TPL_SEND_SMS = "https://sms.yunpian.com/v2/sms/tpl_single_send.json";

	// 发送语音验证码接口的http地址
	private static String URI_SEND_VOICE = "https://voice.yunpian.com/v2/voice/send.json";

	// 绑定主叫、被叫关系的接口http地址
	private static String URI_SEND_BIND = "https://call.yunpian.com/v2/call/bind.json";

	// 解绑主叫、被叫关系的接口http地址
	private static String URI_SEND_UNBIND = "https://call.yunpian.com/v2/call/unbind.json";
	
	private final static String APIKEY = "dd87deea729dc2e6a1fa91fa3edd3879";

	// 编码格式。发送编码格式统一用UTF-8
	private static String ENCODING = "UTF-8";

	public static final String TREASUREMALL_SH_TEMP_GROUPWIN = "746930";//


	/**
	 * 取账户信息
	 *
	 * @return json格式字符串
	 * @throws java.io.IOException
	 */

	public static String getUserInfo(String apikey) throws IOException, URISyntaxException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		return post(URI_GET_USER_INFO, params);
	}

	/**
	 * 智能匹配模板接口发短信
	 *
	 * @param apikey apikey
	 * @param text   短信内容
	 * @param mobile 接受的手机号
	 * @return json格式字符串
	 * @throws IOException
	 */

	public static String sendSms(String apikey, String text, String mobile) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		params.put("text", text);
		params.put("mobile", mobile);
		return post(URI_SEND_SMS, params);
	}

	/**
	 * 通过模板发送短信(不推荐)
	 *
	 * @param apikey    apikey
	 * @param tpl_id    模板id
	 * @param tpl_value 模板变量值
	 * @param mobile    接受的手机号
	 * @return json格式字符串
	 * @throws IOException
	 */

	public static String tplSendSms(String apikey, long tpl_id, String tpl_value, String mobile) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		params.put("tpl_id", String.valueOf(tpl_id));
		params.put("tpl_value", tpl_value);
		params.put("mobile", mobile);
		return post(URI_TPL_SEND_SMS, params);
	}

	/**
	 * 通过接口发送语音验证码
	 * 
	 * @param apikey apikey
	 * @param mobile 接收的手机号
	 * @param code   验证码
	 * @return
	 */

	public static String sendVoice(String apikey, String mobile, String code) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		params.put("mobile", mobile);
		params.put("code", code);
		return post(URI_SEND_VOICE, params);
	}

	/**
	 * 通过接口绑定主被叫号码
	 * 
	 * @param apikey   apikey
	 * @param from     主叫
	 * @param to       被叫
	 * @param duration 有效时长，单位：秒
	 * @return
	 * 
	 * 
	 * 		public static String bindCall(String apikey, String from, String to,
	 *         Integer duration) { Map < String, String > params = new HashMap <
	 *         String, String > (); params.put("apikey", apikey); params.put("from",
	 *         from); params.put("to", to); params.put("duration",
	 *         String.valueOf(duration)); return post(URI_SEND_BIND, params); }
	 * 
	 * 
	 *         通过接口解绑绑定主被叫号码
	 * @param apikey apikey
	 * @param from   主叫
	 * @param to     被叫
	 * @return
	 * 
	 * 		public static String unbindCall(String apikey, String from, String
	 *         to) { Map < String, String > params = new HashMap < String, String >
	 *         (); params.put("apikey", apikey); params.put("from", from);
	 *         params.put("to", to); return post(URI_SEND_UNBIND, params); }
	 */

	/**
	 * 基于HttpClient 4.3的通用POST方法
	 *
	 * @param url       提交的URL
	 * @param paramsMap 提交<参数，值>Map
	 * @return 提交响应
	 */

	public static String post(String url, Map<String, String> paramsMap) {
		CloseableHttpClient client = HttpClients.createDefault();
		String responseText = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost method = new HttpPost(url);
			if (paramsMap != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> param : paramsMap.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
					paramList.add(pair);
				}
				method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
			}
			response = client.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity, ENCODING);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}
	
	public static void main(String[] args) throws IOException, URISyntaxException {

		// 修改为您的apikey.apikey可在官网（http://www.yunpian.com)登录后获取
//		String apikey = "";

		// 修改为您要发送的手机号
		String mobile = "+41766013251";//+9509262704776/+18595450393/+917064570832/+41766013251

		/**************** 查账户信息调用示例 *****************/
//            System.out.println(SmsSendByYunpian.getUserInfo(apikey));

		/**************** 使用智能匹配模板接口发短信(推荐) *****************/
		// 设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
		String text = "[LIUYUNTAO]45678 is your Verification Code. This code will expire in 5 minutes. Please do not disclose it for security purpose. ";
		// 发短信调用示例
//		System.out.println(SmsSendByYunpian.sendSms(APIKEY, text, mobile));

		/**************** 使用指定模板接口发短信(不推荐，建议使用智能匹配模板接口) ******/
		// 设置模板ID，如使用1号模板:【#company#】您的验证码是#code#
		long tpl_id = 3939038;
		// 设置对应的模板变量值

		String tpl_value = URLEncoder.encode("#code#", ENCODING) + "=" + URLEncoder.encode("666888", ENCODING);
		// 模板发送的调用示例
		System.out.println(tpl_value);
		System.out.println(SmsSendByYunpian.tplSendSms(APIKEY, tpl_id, tpl_value, mobile));

		/**************** 使用接口发语音验证码 *****************/
		String code = "1234";
		// System.out.println(SmsSendByYunpian.sendVoice(APIKEY, mobile ,code));

		/**************** 使用接口绑定主被叫号码 *****************/
		// String from = "+86130xxxxxxxx";
		// String to = "+86131xxxxxxxx";
		// Integer duration = 30 * 60; // 绑定30分钟
		// System.out.println(SmsSendByYunpian.bindCall(APIKEY, from ,to , duration));

		/**************** 使用接口解绑主被叫号码 *****************/
		// System.out.println(SmsSendByYunpian.unbindCall(APIKEY, from, to));
	}

}
