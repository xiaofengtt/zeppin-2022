package cn.zeppin.product.ntb.utils;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class EncryptUtil {
    private EncryptUtil() {
        throw new IllegalAccessError("Utility class");
    }

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
            Log.w("TAG", e);
        }
        if (b != null) {
            s = Base64.encodeToString(b, Base64.NO_WRAP);
        }
        return s;
    }

    /**
     * base64解密
     */
    @SuppressWarnings("restriction")
    public static String getFromBase64(String s) {
        String result = null;
        if (s != null) {
            try {
                byte[] b = Base64.decode(s, Base64.DEFAULT);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                Log.w("TAG", e);
            }
        }
        return result;
    }

    /**
     * Description 根据键值进行加密
     */
    @SuppressWarnings("restriction")
    public static String getDes(String data) {
        byte[] bt = encrypt(data.getBytes(), DES_KEY.getBytes());
        String str = Base64.encodeToString(bt, Base64.DEFAULT);
        return str;
    }

    /**
     * Description 根据键值进行解密
     */
    @SuppressWarnings("restriction")
    public static String getFromDes(String data) {
        String result = null;
        if (data == null)
            return null;
        byte[] b = null;
        try {
            b = Base64.decode(data, Base64.DEFAULT);
            result = new String(decrypt(b, DES_KEY.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Description 根据键值进行加密
     */
    private static byte[] encrypt(byte[] data, byte[] key) {
        byte[] result = null;
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Description 根据键值进行解密
     */
    private static byte[] decrypt(byte[] data, byte[] key) {
        byte[] result = null;
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
            e.printStackTrace();
        }
        return result;
    }

    public static String getFromCipher(String cryptData) {
        return getFromDes(getFromBase64(getFromDes(getFromBase64(cryptData))));
    }

    public static String getCipher(String data) {
        return getBase64(getDes(getBase64(getDes(data))));
    }
}
