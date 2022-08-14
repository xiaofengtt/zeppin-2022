package com.happyxmall.www.utils;

import android.annotation.SuppressLint;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Base64;

public class DESUtil {

	/**
	 * 偏移变量，固定占8位字节
	 */
	//private final static String IV_PARAMETER = "12345678";

	private final static byte[] IV_DEFAULT = {1, 1, 1, 1, 1, 1, 1, 1};
	/**
	 * 密钥算法
	 */
	private static final String ALGORITHM = "DES";
	/**
	 * 加密/解密算法-工作模式-填充模式
	 */
	private static final String CIPHER_ALGORITHM = "DES";
	/**
	 * 默认编码
	 */
	private static final String CHARSET = "utf-8";
	private final static String DES_KEY = "treasuremallsys_2020";

	/**
	 * 生成key
	 *
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static Key generateKey(String password) throws Exception {
		DESKeySpec dks = new DESKeySpec(password.getBytes(CHARSET));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		return keyFactory.generateSecret(dks);
	}

	/**
	 * DES加密字符串
	 *
	 * @param password 加密密码，长度不能够小于8位
	 * @param data     待加密字符串
	 * @return 加密后内容
	 */
	@SuppressLint("NewApi")
	public static String encrypt(String password, String data) {
		if(Utility.checkStringNull(password)){
			password = DES_KEY;
		}
		if (data == null)
			return null;
		try {
			Key secretKey = generateKey(password);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
//			IvParameterSpec iv = new IvParameterSpec(IV_DEFAULT);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] bytes = cipher.doFinal(data.getBytes(CHARSET));
			//JDK1.8及以上可直接使用Base64，JDK1.7及以下可以使用BASE64Encoder,或者使用Apache的common
			//Android平台可以使用android.util.Base64
//			return new String(Base64.getEncoder().encode(bytes));
			return byteArr2HexStr(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}
	}

	/**
	 * DES解密字符串
	 * @param password 解密密码，长度不能够小于8位
	 * @param data     待解密字符串
	 * @return 解密后内容
	 */
	@SuppressLint("NewApi")
	public static String decrypt(String password, String data) {
		if (password == null || password.length() < 8) {
			throw new RuntimeException("加密失败，password不能小于8位");
		}
		if (data == null) {
			return "";
		}
		try {
			//先Base64解码
//			byte[] text = Base64.getDecoder().decode(data.getBytes(CHARSET));
			byte[] text = hexStr2ByteArr(data);
			Key secretKey = generateKey(password);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
//			IvParameterSpec iv = new IvParameterSpec(IV_DEFAULT);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] original = cipher.doFinal(text);
			return new String(original,CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * DES加密文件
	 *
	 * @param srcFile  待加密的文件
	 * @param destFile 加密后存放的文件路径
	 * @return 加密后的文件路径
	 */
	@SuppressLint("NewApi")
	public static String encryptFile(String password, String srcFile, String destFile) {
		try {
			byte[] bytes = Files.readAllBytes(Paths.get(srcFile));
			Key secretKey = generateKey(password);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(IV_DEFAULT);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			//byte[] cip = cipher.doFinal(bytes);
			//base64加密
			byte[] encodes = Base64.getEncoder().encode(cipher.doFinal(bytes));
			//写文件
			Files.write(Paths.get(destFile),encodes);
			System.out.println("文件加密完成.目标路径->"+destFile);
			return destFile;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * DES解密文件
	 * @param srcFile  已加密的文件
	 * @param destFile 解密后存放的文件路径
	 * @return 解密后的文件路径
	 */
	@SuppressLint("NewApi")
	public static String decryptFile2(String password, String srcFile, String destFile) {
		if (password == null || password.length() < 8) {
			throw new RuntimeException("加密失败，password不能小于8位");
		}

		try {
			//先base64解密,这里的如果是27M文件,大概需要2分钟左右,很慢,不推荐使用这种方式
			InputStream inWrap = Base64.getDecoder().wrap(new FileInputStream(srcFile));
			File file = new File(destFile);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			IvParameterSpec iv = new IvParameterSpec(IV_DEFAULT);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, generateKey(password), iv);
			//InputStream is = new FileInputStream(srcFile);
			OutputStream out = new FileOutputStream(destFile);
			CipherOutputStream cos = new CipherOutputStream(out, cipher);
			byte[] buffer = new byte[1024 * 2];
			int r;
			while ((r = inWrap.read(buffer)) >= 0) {
				cos.write(buffer, 0, r);
			}
			cos.close();
			inWrap.close();
			out.close();
			System.out.println("解密完成,目标路径->"+destFile);
			return destFile;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}


	/**
	 * DES解密文件
	 * 效率很高，27M文件1.149s，推荐
	 * @param srcFile  已加密的文件
	 * @param destFile 解密后存放的文件路径
	 * @return 解密后的文件路径
	 */
	@SuppressLint("NewApi")
	public static String decryptFile(String password, String srcFile, String destFile) {
		if (password == null || password.length() < 8) {
			throw new RuntimeException("加密失败，password不能小于8位");
		}
		try {
         /*   File file = new File(destFile);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }*/
			//先Base64解码
			byte[] src = Files.readAllBytes(Paths.get(srcFile));
			byte[] text = Base64.getDecoder().decode(src);
			Key secretKey = generateKey(password);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(IV_DEFAULT);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			//byte[] original = cipher.doFinal(text);
			//写文件
			Files.write(Paths.get(destFile),cipher.doFinal(text));
			System.out.println("解密完成,目标路径->"+destFile);
			return destFile;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 *
	 * @param arrB 需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception 本方法不处理任何异常，所有异常全部抛出
	 */
	private static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 *
	 * @param strIn 需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception 本方法不处理任何异常，所有异常全部抛出
	 * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
	 */
	private static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes(CHARSET);
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}
}
