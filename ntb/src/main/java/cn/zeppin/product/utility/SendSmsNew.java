package cn.zeppin.product.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Map;

import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

/**
 * 发送短信 说明:
 * http://api.duanxin.cm/?ac=send&username=用户账号&password=MD5位32密码&phone=
 * 号码&content=内容 状态: 100 发送成功 101 验证失败 102 短信不足 103 操作失败 104 非法字符 105 内容过多 106
 * 号码过多 107 频率过快 108 号码内容空 109 账号冻结 110 禁止频繁单条发送 111 系统暂定发送 112 号码不正确 120 系统升级
 * 
 */
public class SendSmsNew {
	
	private static final String SIGN_SHBX = "【社保熊】";

	public static String sendSms(String content, String mobile) {
		String result = "";

		String url = "http://api.qirui.com:7891/mt";

		// apiKey和APISecret
		String apiKey = "8013510010";// 8013510010//8013510011
		String apiSecret = "dcf6afddfbaf416b72c8";// dcf6afddfbaf416b72c8//e4416c90c6b96f473290

		// 接受手机号
		// String mobile = "18612033494";// 13717582171
		// String mobile = "13717582171";
		// 短信内容(【签名】+短信内容)，系统提供的测试签名和内容，如需要发送自己的短信内容请在启瑞云平台申请签名和模板
		// String message = "您的验证码是:1234【企财宝】";
		try {
			StringBuilder sb = new StringBuilder(2000);
			sb.append(url);
			sb.append("?dc=15");
			sb.append("&sm=").append(URLEncoder.encode(content, "utf8"));
			sb.append("&da=").append(mobile);
			sb.append("&un=").append(apiKey);
			sb.append("&pw=").append(apiSecret);
			sb.append("&tf=3&rd=0&rf=2"); // 短信内容编码为 urlencode+utf8

			String request = sb.toString();
			// 创建url对象
			URL urls = new URL(request.toString());

			// 打开url连接
			HttpURLConnection connection = (HttpURLConnection) urls
					.openConnection();

			// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("GET");

			// 发送
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urls.openStream(), "utf-8"));

			// 返回发送结果
			result = in.readLine();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getRsult(result);
	}

	public static String sendSms4Qcb(String content, String mobile) {
		StringBuilder url = new StringBuilder();
		// 牛投理财
		String uid = "70212974";
		String pwd = "acd50ce3f96114043e138b04074eeb40";
		// 自学酷
		// String uid = "70208580";
		// String pwd = "b3aae0eeeb290755569d22c167c77f63";
		// 新疆师范大学
		// String uid = "70211188";
		// String pwd = "04ad2d52a0176ab18ed14e468bf70661";
		url.append("http://api.duanxin.cm/?");
		try {
			content = URLEncoder.encode(content, "utf-8");
			// 向StringBuffer追加用户名
			url.append("action=send&username=" + uid);

			// 向StringBuffer追加密码（密码采用MD5 32位 小写）
			url.append("&password=" + pwd);

			// 向StringBuffer追加手机号码
			url.append("&phone=" + mobile);

			// 向StringBuffer追加消息内容转URL标准码
			url.append("&content=" + content);

			// 向StringBuffer追加字符集编码
			url.append("&encode=utf8");
			// 创建url对象
			URL urls = new URL(url.toString());

			// 打开url连接
			HttpURLConnection connection = (HttpURLConnection) urls
					.openConnection();

			// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("POST");

			// 发送
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urls.openStream()));

			// 返回发送结果
			String inputline = in.readLine();
			return getRsult(inputline);

		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
	}

	public static String sendSms4Shbx(String content, String mobile) {
		String result = "";

		String url = "http://api.qirui.com:7891/mt";
		content = SIGN_SHBX + content;
		// apiKey和APISecret
		String apiKey = "8013510012";// 8013510010//8013510011//8013510012
		String apiSecret = "c664ec498df247adcbf0";// dcf6afddfbaf416b72c8//e4416c90c6b96f473290//c664ec498df247adcbf0

		// 接受手机号
		// String mobile = "18612033494";// 13717582171
		// String mobile = "13717582171";
		// 短信内容(【签名】+短信内容)，系统提供的测试签名和内容，如需要发送自己的短信内容请在启瑞云平台申请签名和模板
		// String message = "您的验证码是:1234【企财宝】";
		try {
//			// 签名方式发送短信
//			// 组装签名token
//			String times = DataTimeConvert.timespanToString(new Timestamp(
//					System.currentTimeMillis()), "yyyyMMddHHmmss");
//			byte[] users = apiKey.getBytes("utf-8");
//			byte[] pwds = apiSecret.getBytes("utf-8");
//			byte[] time = times.getBytes("utf-8");
//			byte[] message = content.getBytes("utf-8");
//
//			byte[] all = addBytes(addBytes(users, pwds),
//					addBytes(time, message));
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			md.digest(all);
//			Encoder e = Base64.getEncoder();
//			byte[] res = e.encode(md.digest(all));
//			String token = new String(res);

			StringBuilder sb = new StringBuilder();
			sb.append(url);
			sb.append("?dc=15");
			sb.append("&sm=").append(URLEncoder.encode(content, "utf8"));
			sb.append("&da=").append(mobile);
			sb.append("&un=").append(apiKey);
			 sb.append("&pw=").append(apiSecret);
//			sb.append("&pw=").append(token);
//			sb.append("&ts=").append(times);
			sb.append("&tf=3&rd=0&rf=2"); // 短信内容编码为 urlencode+utf8

			String request = sb.toString();
			// 创建url对象
			URL urls = new URL(request.toString());

			// 打开url连接
			HttpURLConnection connection = (HttpURLConnection) urls
					.openConnection();

			// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("GET");

			// 发送
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urls.openStream(), "utf-8"));

			// 返回发送结果
			result = in.readLine();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getRsultNew(result);
	}

	/**
	 * 
	 * @param result
	 * @return
	 */
	public static String getRsult(String result) {
		String status = "";
		try {

			switch (result) {
			case "9002":
				status = result + "_" + "未知命令";
				break;
			case "101":
				status = result + "_" + "验证失败";
				break;
			case "102":
				status = result + "_" + "短信不足";
				break;
			case "103":
				status = result + "_" + "操作失败";
				break;
			case "104":
				status = result + "_" + "非法字符";
				break;
			case "105":
				status = result + "_" + "内容过多";
				break;
			case "106":
				status = result + "_" + "号码过多";
				break;
			case "107":
				status = result + "_" + "频率过快";
				break;
			case "108":
				status = result + "_" + "号码内容空";
				break;
			case "109":
				status = result + "_" + "账号冻结";
				break;
			case "110":
				status = result + "_" + "禁止频繁单条发送";
				break;
			case "111":
				status = result + "_" + "系统暂定发送";
				break;
			case "112":
				status = result + "_" + "号码不正确";
				break;
			case "114":
				status = result + "_" + "账号被锁定，10分钟后解锁";
				break;
			case "120":
				status = result + "_" + "系统升级";
				break;
			default:
				status = 0 + "_" + 0;
				break;
			}
			return status;
		} catch (Exception e) {
			return status;
		}
	}

	/**
	 * {"success": false, "r":"9101"} {"success": true, "id":
	 * "157864905049727130"}
	 * 
	 * @param result
	 * @return
	 */
	public static String getRsultNew(String result) {
		String status = "";
		Map<String, Object> resultMap = JSONUtils.json2map(result);
		result = resultMap.get("r") == null ? "success" : resultMap.get("r")
				.toString();
		Boolean flag = resultMap.get("success") == null ? false : Boolean
				.parseBoolean(resultMap.get("success").toString());
		try {
			if (flag) {
				status = 0 + "_" + 0;
				return status;
			}
			switch (result) {
			case "9002":
				status = result + "_" + "未知命令";
				break;
			case "9012":
				status = result + "_" + "短信消息内容错误";
				break;
			case "9013":
				status = result + "_" + "目标地址错误";
				break;
			case "9014":
				status = result + "_" + "短信内容太长";
				break;
			case "9015":
				status = result + "_" + "路由错误";
				break;
			case "9016":
				status = result + "_" + "没有下发网关";
				break;
			case "9017":
				status = result + "_" + "定时时间错误";
				break;
			case "9018":
				status = result + "_" + "有效时间错误";
				break;
			case "9019":
				status = result + "_" + "无法拆分或者拆分错误";
				break;
			case "9020":
				status = result + "_" + "号码段错误";
				break;
			case "9021":
				status = result + "_" + "消息编号错误，这个和 PacketIndex 参数有关";
				break;
			case "9022":
				status = result + "_" + "用户不能发长短信(EsmClass 错误)";
				break;
			case "9023":
				status = result + "_" + "ProtocolID 错误";
				break;
			case "9024":
				status = result + "_" + "结构错误，一般是指长短信";
				break;
			case "9025":
				status = result + "_" + "短信编码错误";
				break;
			case "9026":
				status = result + "_" + "内容不是长短信";
				break;
			case "9027":
				status = result + "_" + "签名不对";
				break;
			case "9028":
				status = result + "_" + "目标网关不支持长短信";
				break;
			case "9029":
				status = result + "_" + "路由拦截";
				break;
			case "9030":
				status = result + "_" + "目标地址(手机号)太多";
				break;
			case "9031":
				status = result + "_" + "目标地址(手机号)太少";
				break;
			case "9032":
				status = result + "_" + "发送速度太快";
				break;
			case "9101":
				status = result + "_" + "验证失败，一般和用户名/密码/IP 地址相关";
				break;
			case "9102":
				status = result + "_" + "没有填写用户名";
				break;
			case "9103":
				status = result + "_" + "名字没找到";
				break;
			case "9104":
				status = result + "_" + "IP 地址不对";
				break;
			case "9105":
				status = result + "_" + "超过最大连接数，就是 tcp 连接数，http 也是一样的";
				break;
			case "9106":
				status = result + "_" + "协议版本错误";
				break;
			case "9107":
				status = result + "_" + "帐号无效，比如过期/禁用";
				break;
			case "9401":
				status = result + "_" + "计费错误";
				break;
			case "9402":
				status = result + "_" + "非法内容";
				break;
			case "9403":
				status = result + "_" + "黑名单";
				break;
			case "9404":
				status = result + "_" + "丢弃";
				break;
			case "9405":
				status = result + "_" + "Api 帐号丢失";
				break;
			case "9406":
				status = result + "_" + "配置拒绝，就是帐号设置了拒绝标记";
				break;
			case "9407":
				status = result + "_" + "帐号没有生成时间,这个属于非法帐号";
				break;
			case "9408":
				status = result + "_" + "消息超时，超过短信或帐号或系统设置的生存时间";
				break;
			case "9409":
				status = result + "_" + "由约束规则拒绝";
				break;
			case "9410":
				status = result + "_" + "状态报告超时";
				break;
			case "9411":
				status = result + "_" + "状态报告";
				break;
			case "9412":
				status = result + "_" + "帐号无效";
				break;
			case "9413":
				status = result + "_" + "重发拦截";
				break;
			case "9414":
				status = result + "_" + "转发时丢弃，比如该通道已经废弃";
				break;
			case "9415":
				status = result + "_" + "人工审核失败";
				break;
			case "9416":
				status = result + "_" + "可能是诈骗信息";
				break;
			case "9417":
				status = result + "_" + "不匹配模板";
				break;
			case "9418":
				status = result + "_" + "拒绝审核（审核功能可能关闭）";
				break;
			case "9419":
				status = result + "_" + "超过该手机号码的日发送次数限制";
				break;
			case "9501":
				status = result + "_" + "非法目标地址，即手机号";
				break;
			case "9502":
				status = result + "_" + "消息无法投入队列";
				break;
			case "9601":
				status = result + "_" + "上行路由失败";
				break;
			case "9602":
				status = result + "_" + "超过最大重试";
				break;
			case "9701":
				status = result + "_" + "通知失败";
				break;
			case "9702":
				status = result + "_" + "处理配置错误";
				break;
			case "9801":
				status = result + "_" + "投递地址错";
				break;
			case "9802":
				status = result + "_" + "无法连接到服务器";
				break;
			case "9803":
				status = result + "_" + "投递发送数据失败";
				break;
			case "9804":
				status = result + "_" + "投递接收结果失败";
				break;
			case "9902":
				status = result + "_" + "网关无此能力";
				break;
			case "9903":
				status = result + "_" + "二进制数据太长了；如网关没有特别说明，一般不能超过 140，";
				break;
			case "9904":
				status = result + "_" + "网关不支持 EsmClass 字段，或等同字段";
				break;
			case "9905":
				status = result + "_" + "网关不支持 ProtocolID 字段，或等同字段";
				break;
			case "9906":
				status = result + "_" + "网关不支持 UDHI 字段，或等同字段";
				break;
			case "9907":
				status = result + "_" + "网关支持 Letter 字段发送，但短信记录没有 letter";
				break;
			case "9908":
				status = result + "_" + "网关不存在";
				break;
			case "9909":
				status = result + "_" + "网关没有应答";
				break;
			case "9910":
				status = result + "_" + "网关不支持该短信编码";
				break;
			case "9911":
				status = result + "_" + "区域错误";
				break;
			default:
				status = 0 + "_" + 0;
				break;
			}
			System.out.println(Utlity.timeSpanToChinaDateString(new Timestamp(System.currentTimeMillis()))+"短信发送状态："+status);
			return status;
		} catch (Exception e) {
			return status;
		}
	}

	public static String getXmlStr() {

		StringBuilder xmlStr = new StringBuilder(
				"<?xml version='1.0' encoding='GB2312' ?>");// 重组xml信息

		xmlStr.append("<request>");
		xmlStr.append("<spnumber>09914336811</spnumber>");
		xmlStr.append("<uid>09914336811</uid>");
		xmlStr.append("<pwd>d66e2afd3a7ac1077379822d09ec31af</pwd>");
		xmlStr.append("<msgid>46978785</msgid>");
		xmlStr.append("</request>");

		return xmlStr.toString();
	}

	public static String getStatus() {

		try {

			String xmlStr = getXmlStr();

			String url = "http://222.83.7.147:8080/ecs/service/APUserSMS.jws?wsdl";

			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTimeout(new Integer(5000));
			call.setTargetEndpointAddress(url);
			call.setOperationName("sendStatQuery");
			call.addParameter("xmlStr", XMLType.XSD_STRING, ParameterMode.IN);

			call.setReturnType(XMLType.XSD_STRING);
			String result = (String) call.invoke(new Object[] { xmlStr });
			return result;
		} catch (Exception e) {
		}
		return "";
	}

	public static String SendSmsNewTest() {
		String result = "";

		String url = "http://api.qirui.com:7891/mt";

		// apiKey和APISecret
		String apiKey = "8013510012";// 8013510010//8013510011//8013510012
		String apiSecret = "c664ec498df247adcbf0";// dcf6afddfbaf416b72c8//e4416c90c6b96f473290//c664ec498df247adcbf0

		// 接受手机号
		String mobile = "18612033494";// 13717582171
		// String mobile = "13717582171";
		// 短信内容(【签名】+短信内容)，系统提供的测试签名和内容，如需要发送自己的短信内容请在启瑞云平台申请签名和模板
		String message = "您的验证码是:1234【企财宝】";
		String captcha = Utlity.getCaptcha();
		message = "【社保熊】验证码："+captcha+"，工作人员不会向您索取，千万不要告诉其他人，验证码将在5分钟后失效。";
//		message = "您本次操作的验证码为：697203，请及时使用，且勿告知他人，验证码将在5分钟后失效！【社保熊】";
//		message = "验证码：697204，用于密码修改，工作人员不会向您索取，千万不要告诉其他人，验证码将在5分钟后失效。【社保熊】";
//		message = "验证码：697205，为了您的账户安全，千万不要告诉其他人，验证码将在5分钟后失效。（如非本人操作，请忽略本短信）【社保熊】";

		try {
//			// 签名方式发送短信
//			// 组装签名token
//			String times = DataTimeConvert.timespanToString(new Timestamp(
//					System.currentTimeMillis()), "yyyyMMddHHmmss");
//			byte[] users = apiKey.getBytes("utf-8");
//			byte[] pwds = apiSecret.getBytes("utf-8");
//			byte[] time = times.getBytes("utf-8");
//			byte[] content = message.getBytes("utf-8");
//
//			byte[] all = addBytes(addBytes(users, pwds),
//					addBytes(time, content));
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			md.digest(all);
//			Encoder e = Base64.getEncoder();
//			byte[] res = e.encode(md.digest(all));
//			String token = new String(res,"utf-8");
//			System.out.println(token);
			StringBuilder sb = new StringBuilder();
			sb.append(url);
			sb.append("?dc=15");
			sb.append("&sm=").append(URLEncoder.encode(message, "utf8"));
			sb.append("&da=").append(mobile);
			sb.append("&un=").append(apiKey);
			 sb.append("&pw=").append(apiSecret);
//			sb.append("&pw=").append(token);
//			sb.append("&ts=").append(times);
			sb.append("&tf=3&rd=0&rf=2"); // 短信内容编码为 urlencode+utf8
			System.out.println(sb.toString());
			String request = sb.toString();
			// 创建url对象
			URL urls = new URL(request.toString());

			// 打开url连接
			HttpURLConnection connection = (HttpURLConnection) urls
					.openConnection();

			// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("GET");

			// 发送
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urls.openStream(), "utf-8"));

			// 返回发送结果
			result = in.readLine();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param data1
	 * @param data2
	 * @return data1 与 data2拼接的结果
	 */
	public static byte[] addBytes(byte[] data1, byte[] data2) {
		byte[] data3 = new byte[data1.length + data2.length];
		System.arraycopy(data1, 0, data3, 0, data1.length);
		System.arraycopy(data2, 0, data3, data1.length, data2.length);
		return data3;

	}

	public static void main(String[] args) {
		// System.out.println(sendSms4Qcb("测试发送", "18612033494"));
		System.out.println(SendSmsNewTest());
	}
}