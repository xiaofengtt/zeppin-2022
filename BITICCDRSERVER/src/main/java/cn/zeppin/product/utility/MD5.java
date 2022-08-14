package cn.zeppin.product.utility;

import java.security.MessageDigest;
import java.sql.Timestamp;

public class MD5 {
	
	public final static String getMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		String token = "c848dab5ab80e1a1b5fe88eb6cf00bb6";
//		token = "C848DAB5AB80E1A1B5FE88EB6CF00BB6";
		String time = Utlity.timeSpanToDateSecondString(new Timestamp(System.currentTimeMillis()));
		System.out.println(time);
		token += time;
		System.out.println(token);
		System.out.println(getMD5(token));
	}
}

