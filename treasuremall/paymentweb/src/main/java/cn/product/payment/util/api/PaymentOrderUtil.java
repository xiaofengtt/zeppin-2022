package cn.product.payment.util.api;

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

@Component
public class PaymentOrderUtil {
	
	private static String opensslPath;
	@Value("${app.opensslPath}")  
	public void setOpensslPath(String opensslPath) {
		PaymentOrderUtil.opensslPath = opensslPath;
	}
	
	private static String keysPath;
	@Value("${app.keysPath}")  
	public void setKeysPath(String keysPath) {
		PaymentOrderUtil.keysPath = keysPath;
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
}
