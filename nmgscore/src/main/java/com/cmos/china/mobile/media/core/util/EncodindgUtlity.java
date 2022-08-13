package com.cmos.china.mobile.media.core.util;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

import sun.misc.*;

public class EncodindgUtlity {
	
	private static Logger logger = LoggerFactory.getUtilLog(EncodindgUtlity.class);
	private static final byte[] DES_KEY = { 16, 5, -52, -82, 53, -75, 112, -45 };  
	
	/**
	 * base64加密
	 */
	@SuppressWarnings("restriction")
	public static String getBase64(String str) {  
		byte[] b = null;  
		String s = null;  
		try {  
		    b = str.getBytes("utf-8");  
	    } catch (UnsupportedEncodingException e) {  
	    	logger.error("getBase64Error", e);
	    }  
	    if (b != null) {  
	        s = new BASE64Encoder().encode(b);  
		}  
		return s;  
	}  
  
	/**
	 * base64解密
	 */  
    @SuppressWarnings("restriction")
    public static String getFromBase64(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
            	logger.error("getFromBase64Error", e); 
            }  
        }  
        return result;  
    }
    
    /**
	 * des加密
	 */ 
    @SuppressWarnings("restriction")
	public static String getDes(String data) {  
        String encryptedData = null;  
        try {  
            SecureRandom sr = new SecureRandom();  
            DESKeySpec deskey = new DESKeySpec(DES_KEY);  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey key = keyFactory.generateSecret(deskey);  
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(Cipher.ENCRYPT_MODE, key, sr);
            encryptedData = new sun.misc.BASE64Encoder().encode(cipher.doFinal(data.getBytes()));  
        } catch (Exception e) {
        	logger.error("getDesError", e); 
        }  
        return encryptedData;  
    } 
    
    /**
	 * des解密
	 */
    @SuppressWarnings("restriction")
	public static String getFromDes(String cryptData) {  
        String decryptedData = null;  
        try {
            SecureRandom sr = new SecureRandom();  
            DESKeySpec deskey = new DESKeySpec(DES_KEY);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey key = keyFactory.generateSecret(deskey);
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(Cipher.DECRYPT_MODE, key, sr);
            decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(cryptData)));  
        } catch (Exception e) {
        	logger.error("getFromDesError", e);
        }  
        return decryptedData;  
    } 
    
    public static String getFromCipher(String cryptData) {
    	return getFromDes(getFromBase64(getFromDes(getFromBase64(cryptData))));
    }
    
    public static String getCipher(String data) {  
    	return getBase64(getDes(getBase64(getDes(data))));
    }
}
