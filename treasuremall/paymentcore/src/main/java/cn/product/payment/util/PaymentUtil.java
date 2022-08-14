package cn.product.payment.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.product.payment.util.api.PaymentSignUtils;

@Component
public class PaymentUtil {
	
	private static String opensslPath;
	@Value("${app.opensslPath}")  
	public void setOpensslPath(String opensslPath) {
		PaymentUtil.opensslPath = opensslPath;
	}
	
	private static String keysPath;
	@Value("${app.keysPath}")  
	public void setKeysPath(String keysPath) {
		PaymentUtil.keysPath = keysPath;
	}
	
	/**
	 * 拼接键值对
	 */
	private static String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		} else {
			sb.append(value);
		}
		return sb.toString();
	}
	
	/**
	 * 签名
	 * @return
	 */
	public static String getSign(Map<String, String> map, String privateKey){
		List<String> keys = new ArrayList<String>(map.keySet());
		// key排序
		Collections.sort(keys);

		StringBuilder authInfo = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			authInfo.append(buildKeyValue(key, value, false));
			authInfo.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		authInfo.append(buildKeyValue(tailKey, tailValue, false));
		String oriSign = PaymentSignUtils.sign(authInfo.toString(), privateKey);
		String encodedSign = "";

		try {
			encodedSign = URLEncoder.encode(oriSign, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encodedSign;
	}
	
	/**
	 * 签名
	 * @return
	 */
	public static String getApisSign(Map<String, String> map, String publicKey){
		List<String> keys = new ArrayList<String>(map.keySet());
		// key排序
		Collections.sort(keys);

		StringBuilder authInfo = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			authInfo.append(buildKeyValue(key, value, false));
			authInfo.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		authInfo.append(buildKeyValue(tailKey, tailValue, false));
		authInfo.append(publicKey);
		try {
			String authStr = URLEncoder.encode(authInfo.toString(), "UTF-8");
			return MD5.getMD5(authStr).toUpperCase();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 验签
	 * @return
	 */
	public static Boolean checkSign(Map<String, String> map, String publicKey){
		try{
			String sign = URLDecoder.decode(map.get("sign"),"UTF-8");
			map.remove("sign");
			
			List<String> keys = new ArrayList<String>(map.keySet());
			// key排序
			Collections.sort(keys);
	
			StringBuilder authInfo = new StringBuilder();
			for (int i = 0; i < keys.size() - 1; i++) {
				String key = keys.get(i);
				String value = map.get(key);
				authInfo.append(buildKeyValue(key, value, false));
				authInfo.append("&");
			}
	
			String tailKey = keys.get(keys.size() - 1);
			String tailValue = map.get(tailKey);
			authInfo.append(buildKeyValue(tailKey, tailValue, false));
			return PaymentSignUtils.check(authInfo.toString(), sign, publicKey);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 验签
	 * @return
	 */
	public static Boolean checkApisSign(Map<String, String> map, String publicKey){
		try{
			String sign = URLDecoder.decode(map.get("sign"),"UTF-8");
			map.remove("sign");
			
			List<String> keys = new ArrayList<String>(map.keySet());
			// key排序
			Collections.sort(keys);
	
			StringBuilder authInfo = new StringBuilder();
			for (int i = 0; i < keys.size() - 1; i++) {
				String key = keys.get(i);
				String value = map.get(key);
				authInfo.append(buildKeyValue(key, value, false));
				authInfo.append("&");
			}
	
			String tailKey = keys.get(keys.size() - 1);
			String tailValue = map.get(tailKey);
			authInfo.append(buildKeyValue(tailKey, tailValue, false));
			authInfo.append(publicKey);
			
			String authStr = URLEncoder.encode(authInfo.toString(), "UTF-8");
			String checkStr = MD5.getMD5(authStr).toUpperCase();
			if(sign.equals(checkStr)){
				return true;
			}
			
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 验证秘钥是否匹配
	 * @return
	 */
	public static Boolean checkKeys(String privateKey, String publicKey){
		Map<String, String> map = new HashMap<String, String>();
		map.put("timestamp", System.currentTimeMillis() + "");
		
		String sign = getSign(map, privateKey);
		map.put("sign", sign);
		
		return checkSign(map, publicKey);
	}
	
	/**
	 * 创建秘钥
	 */
	public static Map<String, String> createKeys(String company){
		Map<String, String> resultMap = new HashMap<String, String>();
		BufferedReader privateReader = null;
		BufferedReader publicReader = null;
		
		try {
			String cmd1 = opensslPath + " genrsa -out " + keysPath + company + "_private_key.pem";
			String cmd2 = opensslPath + " rsa -in " + keysPath + company + "_private_key.pem -pubout -out " + keysPath + company + "_public_key.pem";
			String cmd3 = opensslPath + " pkcs8 -topk8 -inform PEM -in " + keysPath + company + "_private_key.pem -out " + keysPath + company + "_private_key_pkcs8.pem -outform PEM -nocrypt";
			
			Process p1 = Runtime.getRuntime().exec(cmd1);
			p1.waitFor();
			p1.destroy();
			Process p2 = Runtime.getRuntime().exec(cmd2);
			p2.waitFor();
			p2.destroy();
			Process p3 = Runtime.getRuntime().exec(cmd3);
			p3.waitFor();
			p3.destroy();
			
			String tempString = "";
			
			privateReader = new BufferedReader(new FileReader(keysPath + company + "_private_key_pkcs8.pem"));
			String privateKey = "";
			while((tempString = privateReader.readLine()) != null){
				if(tempString.indexOf("PRIVATE KEY") < 0){
					privateKey += tempString;
				}
			}
			resultMap.put("privateKey", privateKey);
			
			publicReader = new BufferedReader(new FileReader(keysPath + company + "_public_key.pem"));
			String publicKey = "";
			while((tempString = publicReader.readLine()) != null){
				if(tempString.indexOf("PUBLIC KEY") < 0){
					publicKey += tempString;
				}
			}
			resultMap.put("publicKey", publicKey);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				privateReader.close();
				publicReader.close();
			} catch (IOException e) {
				e.printStackTrace();
				return resultMap;
			}
		}
		return resultMap;
	}
}
