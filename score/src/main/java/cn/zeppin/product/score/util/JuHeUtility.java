package cn.zeppin.product.score.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class JuHeUtility {
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> certification(String idcard, String username){
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "实名验证失败";
		
		StringBuilder url = new StringBuilder();
		
		String key = "d7fc758d834cfad833ea3cccbc0c7623";
		
		url.append("http://op.juhe.cn/idcard/query?");
		try {
			url.append("key="+key);
			
			url.append("&idcard="+idcard);
			url.append("&realname="+username);
			
			// 创建url对象
			URL urls = new URL(url.toString());
			HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
			connection.setRequestMethod("POST");

			BufferedReader in = new BufferedReader(new InputStreamReader(urls.openStream(), "UTF-8"));
			String inputline = in.readLine();
			System.out.println(inputline);
			
			Map<String,Object> resultMap = JSONUtils.json2map(inputline);
			if("0".equals(resultMap.get("error_code").toString())){
				result.put("request", true);
				Map<String, Object> resultData = (Map<String, Object>)resultMap.get("result");
				if("1".equals(resultData.get("res").toString())){
					message = "验证成功";
					result.put("result", true);
					result.put("message", message);
				}else{
					message = "姓名与身份证号不符，认证失败";
					result.put("result", false);
					result.put("message", message);
				}
			}else{
				result.put("result", false);
				result.put("message", resultMap.get("reason").toString());
			}
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("request", false);
			result.put("message", message);
			result.put("result", false);
			System.out.println(result);
			return result;
		}
	}
}