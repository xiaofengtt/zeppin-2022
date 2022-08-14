package cn.zeppin.product.utility;

import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {
	/**
	 * base64加密
	 */
	public static String getBase64(String str) {  
		byte[] b = null;  
		String s = null;  
		try {  
		    b = str.getBytes("utf-8");  
	    } catch (UnsupportedEncodingException e) {  
	    	e.printStackTrace();
	    }  
	    if (b != null) {  
	        s = new BASE64Encoder().encode(b);  
		}  
		return s;  
	}  
  
	/**
	 * base64解密
	 */
	public static String getFromBase64(String s) {  
        byte[] b;  
        String result = null;  
        if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
            	e.printStackTrace();
            }  
        }  
        return result;  
    }
    
    public static void main(String[] args) {
		System.out.println(getFromBase64("MTI0MjE0MjE0MjE0NDE="));
	}
}
