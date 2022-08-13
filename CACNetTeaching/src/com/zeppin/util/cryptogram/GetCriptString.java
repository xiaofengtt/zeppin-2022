package com.zeppin.util.cryptogram;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author suijing ����ַ�ļ��ܽ���
 * 
 */
public class GetCriptString
{
    // ��������㷨����DES��DESede(��3DES)��Blowfish
    private static final String Algorithm = "DESede";
    private static final String PASSWORD_CRYPT_KEY = "20140205zeppinadfaljlkjlkdfadfs999fsfsfdsfs00099sfsdfds";

    /**
     * 
     * @param string
     *            ��������ַ�
     * @param ect
     *            ��������
     * @param ecm
     *            ���ܻ��ǽ���
     * @return ���ر�������ɵ��ַ�
     * 
     * @throws NoSuchAlgorithmException
     */
    public static String getString(String string, ECrypType ect, ECrypMethod ecm)
	    throws NoSuchAlgorithmException
    {
	NoSuchAlgorithmException exception = new NoSuchAlgorithmException();
	switch (ect)
	{
	case MD5:// ������,ֻ�м���
	    if (ecm == ECrypMethod.ENCODE)
	    {
		string = encryptMd5(string);
	    }
	    else
	    {

		throw exception;
	    }
	    break;
	case SHA:// �����棬ֻ�м���
	    if (ecm == ECrypMethod.ENCODE)
	    {
		string = encryptSha(string);
	    }
	    else
	    {
		throw exception;
	    }
	    break;
	case RSA:
	    break;
	case TRIDES:
	    if (ecm == ECrypMethod.ENCODE)
	    {
		string = encryptTriDes(string);

	    }
	    else
	    {
		string = decryptTriDes(string);
	    }
	    break;
	default:
	    break;
	}

	return string;

    }

    /**
     * 
     * @param info
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static String encryptMd5(String info)
	    throws NoSuchAlgorithmException
    {
	// ���MD5�㷨���MessageDigest����
	MessageDigest md5 = MessageDigest.getInstance("MD5");
	byte[] srcBytes = info.getBytes();
	// ʹ��srcBytes����ժҪ
	md5.update(srcBytes);
	// ��ɹ�ϣ���㣬�õ�result
	byte[] resultBytes = md5.digest();
	return parseByte2HexStr(resultBytes);
    }

    /**
     * @param info
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static String encryptSha(String info)
	    throws NoSuchAlgorithmException
    {
	MessageDigest sha = MessageDigest.getInstance("SHA");
	byte[] srcBytes = info.getBytes();
	// ʹ��srcBytes����ժҪ
	sha.update(srcBytes);
	// ��ɹ�ϣ���㣬�õ�result
	byte[] resultBytes = sha.digest();
	return parseByte2HexStr(resultBytes);
    }

    /**
     * ���ܷ���
     * 
     * @param src
     *            Դ��ݵ��ֽ�����
     * @return
     */
    private static String encryptTriDes(String info)
    {
	try
	{
	    SecretKey deskey = new SecretKeySpec(
		    build3DesKey(PASSWORD_CRYPT_KEY), Algorithm); // �����Կ
	    Cipher c1 = Cipher.getInstance(Algorithm); // ʵ�������/���ܵ�Cipher������
	    c1.init(Cipher.ENCRYPT_MODE, deskey); // ��ʼ��Ϊ����ģʽ
	    return parseByte2HexStr(c1.doFinal(info.getBytes()));
	}
	catch (java.security.NoSuchAlgorithmException e1)
	{
	    e1.printStackTrace();
	}
	catch (javax.crypto.NoSuchPaddingException e2)
	{
	    e2.printStackTrace();
	}
	catch (java.lang.Exception e3)
	{
	    e3.printStackTrace();
	}
	return null;
    }

    /**
     * ���ܺ���
     * 
     * @param src
     *            ���ĵ��ֽ�����
     * @return
     */
    private static String decryptTriDes(String info)
    {
	try
	{
	    SecretKey deskey = new SecretKeySpec(
		    build3DesKey(PASSWORD_CRYPT_KEY), Algorithm);
	    Cipher c1 = Cipher.getInstance(Algorithm);
	    c1.init(Cipher.DECRYPT_MODE, deskey); // ��ʼ��Ϊ����ģʽ
	    return new String(c1.doFinal(parseHexStr2Byte(info)));
	}
	catch (java.security.NoSuchAlgorithmException e1)
	{
	    e1.printStackTrace();
	}
	catch (javax.crypto.NoSuchPaddingException e2)
	{
	    e2.printStackTrace();
	}
	catch (java.lang.Exception e3)
	{
	    e3.printStackTrace();
	}
	return null;
    }

    /**
     * ����ַ������Կ�ֽ�����
     * 
     * @param keyStr
     *            ��Կ�ַ�
     * @return
     * @throws UnsupportedEncodingException
     */
    private static byte[] build3DesKey(String keyStr)
	    throws UnsupportedEncodingException
    {
	byte[] key = new byte[24]; // ����һ��24λ���ֽ����飬Ĭ�����涼��0
	byte[] temp = keyStr.getBytes("UTF-8"); // ���ַ�ת���ֽ�����

	/*
	 * ִ�����鿽�� System.arraycopy(Դ���飬��Դ�������￪ʼ������Ŀ�����飬��������λ)
	 */
	if (key.length > temp.length)
	{
	    // ���temp����24λ���򿽱�temp����������ȵ����ݵ�key������
	    System.arraycopy(temp, 0, key, 0, temp.length);
	}
	else
	{
	    // ���temp����24λ���򿽱�temp����24�����ȵ����ݵ�key������
	    System.arraycopy(temp, 0, key, 0, key.length);
	}
	return key;
    }

    /**
     * ��������ת����16����
     * 
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[])
    {
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < buf.length; i++)
	{
	    String hex = Integer.toHexString(buf[i] & 0xFF);
	    if (hex.length() == 1)
	    {
		hex = '0' + hex;
	    }
	    sb.append(hex.toUpperCase());
	}
	return sb.toString();
    }

    /**
     * ��16����ת��Ϊ������
     * 
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr)
    {
	if (hexStr.length() < 1)
	    return null;
	byte[] result = new byte[hexStr.length() / 2];
	for (int i = 0; i < hexStr.length() / 2; i++)
	{
	    int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
	    int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
		    16);
	    result[i] = (byte) (high * 16 + low);
	}
	return result;
    }

}
