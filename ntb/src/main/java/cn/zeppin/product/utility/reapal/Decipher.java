package cn.zeppin.product.utility.reapal;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class Decipher {

	/**
	 * 解密1
	 * 
	 * @param merchant_id
	 * @param data
	 * @param encryptkey
	 * @return
	 * @throws com.reapal.common.exception.ServiceException
	 */
	public static String decryptData(String post) throws Exception {

		// 将返回的json串转换为map

		TreeMap<String, String> map = JSON.parseObject(post,
				new TypeReference<TreeMap<String, String>>() {
				});
		String encryptkey = map.get("encryptkey");
		String data = map.get("data");

		// 获取自己私钥解密
		PrivateKey pvkformPfx = RSA.getPvkformPfx(ReapalConfig.privateKey,
				ReapalConfig.password);
		String decryptData = RSA.decrypt(encryptkey, pvkformPfx);

		post = AES.decryptFromBase64(data, decryptData);

		return post;
	}

	/**
	 * 解密2
	 * 
	 * @param merchant_id
	 * @param data
	 * @param encryptkey
	 * @return
	 * @throws com.reapal.common.exception.ServiceException
	 */
	public static String decryptData(String encryptkey, String data)
			throws Exception {

		// 获取自己私钥解密
		PrivateKey pvkformPfx = RSA.getPvkformPfx(ReapalConfig.privateKey, ReapalConfig.password);
				
		String decryptKey = RSA.decrypt(encryptkey, pvkformPfx);

		return AES.decryptFromBase64(data, decryptKey);

	}
	

	/**
	 * 加密
	 * 
	 * @param merchant_id
	 * @param data
	 * @param encryptkey
	 * @return
	 * @throws com.reapal.common.exception.ServiceException
	 */
	public static Map<String, String> encryptData(String json) throws Exception {
		System.out.println("json数据=============>" + json);

		// 商户获取融宝公钥
		PublicKey pubKeyFromCrt = RSA.getPubKeyFromCRT(ReapalConfig.pubKeyUrl);
		// 随机生成16数字
		String key = getRandom(16);

		// 使用RSA算法将商户自己随机生成的AESkey加密
		String encryptKey = RSA.encrypt(key, pubKeyFromCrt);
		// 使用AES算法用随机生成的AESkey，对json串进行加密
		String encryData = AES.encryptToBase64(json, key);

		System.out.println("密文key============>" + encryptKey);
		System.out.println("密文数据===========>" + encryData);

		Map<String, String> map = new HashMap<String, String>();
		map.put("data", encryData);
		map.put("encryptkey", encryptKey);

		return map;
	}

	public static Random random = new Random();

	public static String getRandom(int length) {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < length; i++) {
			boolean isChar = (random.nextInt(2) % 2 == 0);// 输出字母还是数字
			if (isChar) { // 字符串
				int choice = (random.nextInt(2) % 2 == 0) ? 65 : 97; // 取得大写字母还是小写字母
				ret.append((char) (choice + random.nextInt(26)));
			} else { // 数字
				ret.append(Integer.toString(random.nextInt(10)));
			}
		}
		return ret.toString();
	}

	public static void main(String[] args) {

		String data = "IaCNI1qIP6BKiITCOGghopk3hXJnpqQYNdxCHU/K+MxIhNkcha2+vu1OHMmBNhctU4hvQBMNWCF+Pq4Hk0ZjfI70qAofmvohO+N05Fl+ym7gkEJp57TqTawq7XQjN4TXaoqHfjVoZkpt7ITXjAgjx0dP6mVdCEh3yyr3Qc1Dy8LedvoEKG7uTE3Xi6DjmJN7rGR31RGwpJZ6iocaJRv+3TihcuKLYmWKDW+G1BU48fKYRZL4W5a/S1e3BJb0v6iOzUslPuUd6fShxUZADX5SVSBtfyHaOHOwzdRgbLshWDI=";
		String encryptkey = "YBZIFt09u/Y8gGMqAo43XdrNm763BIxwmgHAKg1asJ0BDrRvjfUpLrKd6Uh1dH38kE+NKV72Tr2mS3uvsgEPgbQmcVhbSWITQSecHNxSdRTyvj5Nj4fe67TD74GI2MTN0Fq2y0Sr5J9RI0fsoAzBRZZg5b3z/WJVjaq0+gS//YM500DJioa3iGjUDqgdUUwhh8cz3b5jwiFv0LvHeuUW1Qr9iSE4UW1RBRoCKFWNDNsxVoU8kZgEmSk2t439SMjY27Ixx81cxIW9q9m+MbCZwh1FnyohpREz0vTI3qfo76lsk9/2NJMHbJfF5B9skXjDBqIx3jK71auQpBhZD94CgA==";
		
		try {
			System.out.println("=========" + decryptData(encryptkey, data));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
