package cn.zeppin.product.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import cn.zeppin.product.itic.core.entity.TnumberRelation;

import com.alibaba.fastjson.JSONObject;

public class CdrUtlity {

	private static String vccid = SystemConfig.getProperty("com.itic.cdr.vccid");
	private static String key = SystemConfig.getProperty("com.itic.cdr.key");
	private static String key_low = SystemConfig.getProperty("com.itic.cdr.key.low");

	public static String iplist = SystemConfig.getProperty("com.itic.ip.whitelist");
	public static String ipenable = SystemConfig.getProperty("com.itic.cdr.ipenable");
	public static String ResolutionPushpath = SystemConfig.getProperty("com.itic.cdr.ResolutionPushpath");
	public static String Srfmsgidpath = SystemConfig.getProperty("com.itic.cdr.Srfmsgidpath");
	public static String vediosavepath = SystemConfig.getProperty("com.itic.cdr.vediosavepath");

	public static String srfmsgidUrlA = SystemConfig.getProperty("com.itic.cdr.srfmsgidUrlA");
	public static String srfmsgidUrlB = SystemConfig.getProperty("com.itic.cdr.srfmsgidUrlB");
	public static String dayvediosavepath = SystemConfig.getProperty("com.itic.cdr.dayvediosavepath");
	public static String dayUrlA = SystemConfig.getProperty("com.itic.cdr.dayUrlA");
	public static String dayUrlB = SystemConfig.getProperty("com.itic.cdr.dayUrlB");
	public static String setBindNum = SystemConfig.getProperty("com.itic.cdr.service.setBindNum");
	public static String apiUrl = SystemConfig.getProperty("com.itic.cdr.service.api");

	public final static Integer process_operate_create = 0;// ??????
	public final static Integer process_operate_edit = 1;// ??????
	public final static Integer process_operate_delete = 2;// ??????

	public final static Integer process_type_create = 0;// ????????????
	public final static Integer process_type_edit = 1;// ????????????
	public final static Integer process_type_delete = 2;// ????????????

	public final static String request_status_0000 = "0000";
	public final static String request_status_0001 = "0001";
	public final static String request_status_0002 = "0002";
	public final static String request_status_0003 = "0003";
	public final static String request_status_0004 = "0004";
	public final static String request_status_9999 = "9999";

	public static String getToken() {
		String token = "";
		String time = Utlity.timeSpanToDateSecondString(new Timestamp(System.currentTimeMillis()));
		token += (key + time);
		return MD5.getMD5(token);
	}
	
	public static String getTokenByLowKey() {
		String token = "";
		String time = Utlity.timeSpanToDateSecondString(new Timestamp(System.currentTimeMillis()));
		token += (key_low + time);
		return MD5.getMD5(token);
	}

	public static String getStreamnumber() {
		String token = "itic";
		String time = Utlity.timeSpanToDateSecondString(new Timestamp(System.currentTimeMillis()));
		token += time;
		return token;
	}
	
	public static String getReason(String cause) {
		String reason = "";
		switch (cause) {
		case "1":
			reason = "????????????";
			break;
		case "2":
			reason = "????????????";
			break;
		case "3":
			reason = "?????????????????????";
			break;
		case "4":
			reason = "???????????????";
			break;
		case "5":
			reason = "????????????";
			break;
		case "6":
			reason = "??????";
			break;
		case "7":
			reason = "??????";
			break;
		case "8":
			reason = "????????????";
			break;
		case "9":
			reason = "????????????????????????????????????";
			break;
		case "99":
			reason = "??????";
			break;
		case "20":
			reason = "??????????????????";
			break;

		default:
			break;
		}
		
		return reason;
	}

	/**
	 * ???????????????????????? { "header":{ "SERVICENAME":"setBindNum", "OPERATE":1,
	 * "TOKEN":"b5e8df8022d2e14be9cc3d87971fc243", "VCCID":3268 }, "body":{
	 * "TYPE":1, "STREAMNUMBER":"itic20180115112707", "CALLERNUM":"18500139801",
	 * "WAYBILLNUM":"", "MIDDLEINNUM":"01086480195",
	 * "MESSAGEID":"itic20180115112707", "MIDDLEOUTNUM":"01089689597",
	 * "CALLEDNUM":"01059680692", "MAXDURATION":3600, "ISRECORD":1, "STATE":0,
	 * "VALIDTIME":"" } }
	 * 
	 * @param inputParams
	 * @return
	 */
	public static Map<String, Object> setBindNum(Integer type, TnumberRelation tr) {
		StringBuffer sb = new StringBuffer();
		// ??????header
		Integer operate = type;
		sb.append("{");
		sb.append("\"header\":{");
		sb.append("\"SERVICENAME\":\"" + setBindNum + "\"");
		sb.append(",");
		sb.append("\"OPERATE\":" + operate);
		sb.append(",");
		sb.append("\"TOKEN\":\"" + getTokenByLowKey() + "\"");
		sb.append(",");
		sb.append("\"VCCID\":" + vccid);
		sb.append("},");

		// ??????body
		String callernum = tr.getToMobile();
		String waybillnum = "";
		String middleinnum = tr.getToTel();
		String middleoutnum = tr.getTcTel();
		String callednum = tr.getTcPhone();
		Integer maxduration = tr.getMaxduration();
		Integer isrecord = tr.getIsrecord();
		Integer state = 0;
		String validtime = tr.getExpiryDate() == null ? "" : tr.getExpiryDate().toString();
		sb.append("\"body\":{");
		sb.append("\"TYPE\":1");
		sb.append(",");
		sb.append("\"STREAMNUMBER\":\"" + getStreamnumber() + "\"");
		sb.append(",");
		sb.append("\"CALLERNUM\":\"" + callernum + "\"");
		sb.append(",");
		sb.append("\"WAYBILLNUM\":\"" + waybillnum + "\"");
		sb.append(",");
		sb.append("\"MIDDLEINNUM\":\"" + middleoutnum + "\"");
		sb.append(",");
		sb.append("\"MESSAGEID\":\"" + getStreamnumber() + "\"");
		sb.append(",");
		sb.append("\"MIDDLEOUTNUM\":\"" + middleinnum + "\"");
		sb.append(",");
		sb.append("\"CALLEDNUM\":\"" + callednum + "\"");
		sb.append(",");
		sb.append("\"MAXDURATION\":" + maxduration);
		sb.append(",");
		sb.append("\"ISRECORD\":" + isrecord);
		sb.append(",");
		sb.append("\"STATE\":" + state);
		sb.append(",");
		sb.append("\"VALIDTIME\":\"" + validtime + "\"");
		sb.append("}");
		sb.append("}");
		System.out.println(sb.toString());
		// ??????json??????
		JSONObject json = JSONObject.parseObject(sb.toString());

		Map<String, Object> result = post(json);
		return result;
	}

	@SuppressWarnings("finally")
	public static Map<String, Object> post(JSONObject json) {

		Map<String, Object> map = new HashMap<>();
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(apiUrl);

		post.addHeader(HTTP.CONTENT_TYPE, "application/json");
		String result = "";

		try {

			StringEntity s = new StringEntity(json.toJSONString(), "utf-8");
			s.setContentType("text/json");
			s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(s);

			// ????????????
			HttpResponse httpResponse = client.execute(post);

			// ?????????????????????
			InputStream inStream = httpResponse.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
			StringBuilder strber = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
				strber.append(line + "\n");
			inStream.close();

			result = strber.toString();
			System.out.println(result);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				System.out.println("???????????????????????????????????????");
				map = JSONUtils.json2map(result);

			} else {
				map.put("header", "\"STATE_CODE\":\"9999\",\"STATE_NAME\":\"????????????????????????\"");
				System.out.println("?????????????????????");

			}

		} catch (Exception e) {
			System.out.println("????????????");
			map.put("header", "\"STATE_CODE\":\"9999\",\"STATE_NAME\":\"???????????????\"");
//			throw new RuntimeException(e);
		} finally {
			return map;
		}
	}

	public static Map<String, Object> post1(JSONObject json) {
		Map<String, Object> map = new HashMap<>();
		OutputStreamWriter out = null;
		BufferedReader reader = null;
		String response = "";
		try {
			URL httpUrl = null; // HTTP URL??? ???????????????????????????
			// ??????URL
			httpUrl = new URL(apiUrl);
			// ????????????
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("connection", "keep-alive");
			conn.setUseCaches(false);// ??????????????????
			conn.setInstanceFollowRedirects(true);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			// POST??????
			out = new OutputStreamWriter(conn.getOutputStream());
			out.write(json.toJSONString());
			out.flush();
			// ????????????
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String lines;
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				response += lines;
			}
			reader.close();
			// ????????????
			conn.disconnect();
			System.out.println(response);
			map = JSONUtils.json2map(response);
			// log.info(response.toString());
		} catch (Exception e) {
			System.out.println("?????? POST ?????????????????????" + e);
			e.printStackTrace();
		}
		// ??????finally?????????????????????????????????
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return map;
	}

	/**
	 * ?????????????????????ajax??????
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAjax(ServletRequest request) {
		if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
			return true;
		}
		return false;
	}

	/**
	 * ??????JSON????????????
	 * 
	 * @param httpResponse
	 * @param jsonString
	 * @throws IOException
	 */
	public static void sendJson(HttpServletResponse httpResponse, String jsonString) throws IOException {
		httpResponse.setContentType("application/json");
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = httpResponse.getWriter();
		writer.write(jsonString);
		writer.flush();
		writer.close();
	}

}
