package com.cmos.china.mobile.media.core.util;

import java.io.IOException;
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
	private final static String DES = "DES";  
    private final static String DES_KEY = "cmossomc";
	
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
     * Description 根据键值进行加密
     */  
    @SuppressWarnings("restriction")
	public static String getDes(String data){  
        byte[] bt = encrypt(data.getBytes(), DES_KEY.getBytes());  
        String strs = new BASE64Encoder().encode(bt);  
        return strs;  
    }  
   
    /** 
     * Description 根据键值进行解密
     */  
    @SuppressWarnings("restriction")
	public static String getFromDes(String data){  
        if (data == null)  
            return null;  
        BASE64Decoder decoder = new BASE64Decoder();  
        byte[] buf = null;
		try {
			buf = decoder.decodeBuffer(data);
		} catch (IOException e) {
			e.printStackTrace();
		}  
        byte[] bt = decrypt(buf,DES_KEY.getBytes());  
        return new String(bt);  
    }  
   
    /** 
     * Description 根据键值进行加密
     */  
    private static byte[] encrypt(byte[] data, byte[] key){
    	byte[]  result = null;
		try {
	        SecureRandom sr = new SecureRandom();
	        DESKeySpec dks = new DESKeySpec(key);
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);  
	        SecretKey securekey = keyFactory.generateSecret(dks);
	        Cipher cipher = Cipher.getInstance(DES);  
	        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr); 
	        result = cipher.doFinal(data);
		} catch (Exception e) {
			logger.error("getFromBase64Error", e); 
		} 
        return result;
    }  
       
       
    /** 
     * Description 根据键值进行解密
     */  
    private static byte[] decrypt(byte[] data, byte[] key){
    	byte[]  result = null;
		try {
			SecureRandom sr = new SecureRandom();
	        DESKeySpec dks;
			dks = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);  
	        SecretKey securekey = keyFactory.generateSecret(dks);
	        Cipher cipher = Cipher.getInstance(DES);
	        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
	        result = cipher.doFinal(data);
		} catch (Exception e) {
			logger.error("getFromBase64Error", e); 
		} 
        return result;
    }
    
    public static String getFromCipher(String cryptData) {
//    	System.out.println(getFromBase64(cryptData));
//    	System.out.println(getFromDes(getFromBase64(cryptData)));
//    	System.out.println(getFromBase64(getFromDes(getFromBase64(cryptData))));
//    	System.out.println(getFromDes(getFromBase64(getFromDes(getFromBase64(cryptData)))));
//    	
    	return getFromDes(getFromBase64(getFromDes(getFromBase64(cryptData))));
    }
    
    public static String getCipher(String data) {  
    
//    	
//    	System.out.println(getDes(data));
//    	System.out.println(getBase64(getDes(data)));
//    	System.out.println(getDes(getBase64(getDes(data))));
//    	System.out.println(getBase64(getDes(getBase64(getDes(data)))));
    	
    	return getBase64(getDes(getBase64(getDes(data))));
    }
    
    public static void main(String[] args) {
//     	String str = "1qaz!QAZ";
     	System.out.println(getFromCipher("UWZYMUZHbXZwZVpmVDdISzdWWmNYbjJnYUhlMVBZMGNYbHE5bXZzeW91anMxTTlINWNXSm9qMEJB\nRGZyQkk5b2FwRWkzdzdLS0c4dgpWdFE1QU0zTzRUR0NpaW4vcVd2TitTK1QxdVBFWHJ3UklzTHE2\naXJXdnluYUo3eVVaanBNY1p4cktRM1ZUTXVDVjVzM25OSkM3RW5VCkUzL0gyckE5MFpNaEswdGlR\ndnM9"));
//     	System.out.println(getFromCipher("ZU44c3M2OU1LS0VmcytVUjFtQ1NWUnlGb0ROVzBKNENENDZoRDFybUkvVGhadjZqWWlmUVV3PT0="));
//     	System.out.println(getFromBase64(getBase64(str)));
     }
}
