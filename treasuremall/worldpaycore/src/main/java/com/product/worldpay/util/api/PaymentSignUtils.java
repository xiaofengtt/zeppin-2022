package com.product.worldpay.util.api;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class PaymentSignUtils {

	private static final String SIGN_TYPE_RSA = "RSA";
	private static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	public static String sign(String content, String privateKey) {
		try {
			PrivateKey priKey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA, new ByteArrayInputStream(privateKey.getBytes()));
			
			Signature signature = java.security.Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);

			signature.initSign(priKey);
			signature.update(content.getBytes(DEFAULT_CHARSET));

			byte[] signed = signature.sign();

			return new String(Base64.encodeBase64(signed));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static boolean check(String content, String sign, String publicKey){
		try {
			PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
			
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
			
			signature.initVerify(pubKey);
			signature.update(content.getBytes(DEFAULT_CHARSET));
			
			return signature.verify(Base64.decodeBase64(sign.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	 public static PrivateKey getPrivateKeyFromPKCS8(String algorithm,  InputStream ins) throws Exception {
		if (ins == null || StringUtils.isEmpty(algorithm)) {
			return null;
		}
		
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		
		byte[] encodedKey = StreamUtil.readText(ins).getBytes();
		
		encodedKey = Base64.decodeBase64(encodedKey);
		
		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
	}
	 
	public static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

		StringWriter writer = new StringWriter();
		StreamUtil.io(new InputStreamReader(ins), writer);
		
		byte[] encodedKey = writer.toString().getBytes();
		
		encodedKey = Base64.decodeBase64(encodedKey);
		
		return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
	}
}
