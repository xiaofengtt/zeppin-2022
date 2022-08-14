package cn.product.treasuremall.util.alipay.check;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import cn.product.treasuremall.util.JSONUtils;

/**
 * 身份证实名校验
 * 手机号实名校验
 * 银行卡实名校验
 * @author Administrator
 *
 */
public class RealmeCheckUtil {

	private static final String AppCode = "aa12335f7c7f4c79b1de378baf488b88";
	
	private static final String AppKey = "203728518";
	
	private static final String AppSecret = "3u8jccavkj4ejr1rax5yvaxw4debigy7";
	
	private static final String LinkUrl = "https://yhkyz.market.alicloudapi.com";
	
	private static final String LinkPath = "/integrated_bankcard/check";
	
	
	@SuppressWarnings({ "unchecked" })
	public static Map<String, Object> certification(Map<String, String> params){
		
		Map<String, Object> result = new HashMap<String, Object>();
	    Map<String, String> headers = new HashMap<String, String>();
	    headers.put("Authorization", "APPCODE " + AppCode);

	    try {
	    	HttpResponse response = HttpUtils.doGet(LinkUrl, LinkPath, headers, params);
	    	System.out.println(response.toString());
	    	int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {// 出现链接异常，抛出
				throw new Exception("HttpClient,error status code :"
						+ statusCode);
			}
			String httpEntityContent = getHttpEntityContent(response);
	    	
//	    	//获取response的body
//	    	System.out.println(EntityUtils.toString(response.getEntity()));
			Map<String, Object> entity = JSONUtils.json2map(httpEntityContent);
			String message = "";
			String code = entity.get("code") == null ? "-1" : entity.get("code").toString();
			message = getMessage(code);
			boolean flag = false;
			if("0".equals(code)) {//请求成功
//				result.put("result", true);
				Map<String, String> dataMap = entity.get("data") == null ? null : (Map<String, String>)entity.get("data");
				String resultStr = dataMap.get("result");
				message = getResultMessage(resultStr);
				if("1".equals(resultStr)) {//校验成功
					flag = true;
				}
			}
			result.put("result", flag);
			result.put("message", message);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return null;
	    }
	    return result;
	}
	
	public static String getMessage(String code) {
		String message = "";
		try {

			switch (code) {
			case "0":
				message = "成功";
				break;
			case "10016":
				message = "姓名为空或者包含特殊字符";
				break;
			case "20502":
				message = "银行卡号不标准：银行卡为空或者卡号错误	";
				break;
			default:
				message = "成功";
				break;
			}
			return message;
		} catch (Exception e) {
			return message;
		}
	}
	
	
	public static String getResultMessage(String result) {
		String message = "";
		try {

			switch (result) {
			case "1":
				message = "认证信息匹配";
				break;
			case "2":
				message = "认证信息不匹配";
				break;
			case "3":
				message = "无法验证";
				break;
			case "-1":
				message = "异常情况";
				break;
			default:
				message = "异常情况";
				break;
			}
			return message;
		} catch (Exception e) {
			return message;
		}
	}
	
	private static String getHttpEntityContent(HttpResponse response)
			throws IOException, UnsupportedEncodingException {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream is = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			String line = br.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line + "\n");
				line = br.readLine();
			}
			return sb.toString();
		}
		return "";
	}
	public static void main(String[] args) {
		Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + AppCode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("bankcard", "6214830164014598");
	    querys.put("idcard", "130929199212018467");
	    querys.put("mobile", "13717582171");
	    querys.put("name", "刘海迪");


	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doGet(LinkUrl, LinkPath, headers, querys);
	    	System.out.println(response.toString());
	    	//获取response的body
	    	System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
}
