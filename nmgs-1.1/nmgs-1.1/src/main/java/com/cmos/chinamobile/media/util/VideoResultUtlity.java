package com.cmos.chinamobile.media.util;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

public class VideoResultUtlity{
	private static final Logger logger = LoggerFactory.getActionLog(VideoResultUtlity.class);
	private static final byte[] key = { 23, 5, -51, -42, -13, -75, 102, 5 };
	
//    @SuppressWarnings("restriction")
//	public static String getDes(String data) {  
//        String encryptedData = null;  
//        try {  
//            SecureRandom sr = new SecureRandom();  
//            DESKeySpec deskey = new DESKeySpec(key);  
//            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
//            SecretKey key = keyFactory.generateSecret(deskey);  
//            Cipher cipher = Cipher.getInstance("DES");  
//            cipher.init(Cipher.ENCRYPT_MODE, key, sr);
//            encryptedData = new sun.misc.BASE64Encoder().encode(cipher.doFinal(data.getBytes()));  
//        } catch (Exception e) {
//        }  
//        return encryptedData;  
//    } 
    
    @SuppressWarnings("restriction")
	public static String getFromDes(String cryptData) {  
        String decryptedData = null;  
        try {
            SecureRandom sr = new SecureRandom();  
            DESKeySpec deskey = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey key = keyFactory.generateSecret(deskey);
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(Cipher.DECRYPT_MODE, key, sr);
            decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(cryptData)));  
        } catch (Exception e) {
        	logger.info("´íÎó", e);
        }  
        return decryptedData;  
    } 
	
	public static Boolean getResult(){
    	Timestamp info = getCurrentInfo(getFromDes("J6xLtFW8GE2lBCKjS2RJ0SFkYcKAjO1j"));
    	if(new Timestamp(new Date().getTime()).before(info)){
    		return true;
    	}else{
    		return false;
    	}
    }
	
	private static Timestamp getCurrentInfo(String format) {
		String defaultFormat = "yyyy-MM-dd HH:mm:ss";
		if (!"".equals(format)) {
			defaultFormat = format;
		}
		SimpleDateFormat df = new SimpleDateFormat(defaultFormat);
		String time = df.format(new Date());
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}
	
//	public static void main(String[] args) {
//    	String value = "2016-9-30 00:00:00";
//    	value = getDes(value);
//    	System.out.println(":" + value);
//    	value = getFromDes(value);
//        System.out.println(":" + value);
//    }
}
