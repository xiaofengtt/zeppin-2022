package cn.zeppin.product.utility;

import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class WeixinDecrypt {
	public static byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) {
	    try {
	    	Security.addProvider(new BouncyCastleProvider());
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
	        Key sKeySpec = new SecretKeySpec(keyByte, "AES");

	        cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// 初始化 
	        byte[] result = cipher.doFinal(content);
	        return result;
	    } catch (Exception e) {
	        e.printStackTrace();  
	        return null;
	    }
	}
	
	public static AlgorithmParameters generateIV(byte[] iv) throws Exception{  
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");  
        params.init(new IvParameterSpec(iv));  
        return params;  
    }  
}
