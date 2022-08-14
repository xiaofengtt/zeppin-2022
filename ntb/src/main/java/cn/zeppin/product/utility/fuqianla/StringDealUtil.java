package cn.zeppin.product.utility.fuqianla;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Sherlock Blaze
 * @date 03/11/2017-5:28 PM
 * @description
 */
public class StringDealUtil {
    /**
     * @author Sherlock Blaze
     * @date 05/11/2017-4:30 AM
     * @description 获取MD5加密串
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static StringBuffer getSignStrForMD5(Map paraMap) {
        Set<Map.Entry<String, Object>> set = paraMap.entrySet();
        Iterator<Map.Entry<String, Object>> it = set.iterator();
        Map.Entry<String, Object> entry = null;
        StringBuffer sb = new StringBuffer();
        String value = "";

        while (it.hasNext()) {
            entry = it.next();

            value = entry.getValue().toString();

            sb.append(entry.getKey() + "=" + value + "&");
        }

        return sb;
    }

    /**
     * @author Sherlock Blaze
     * @date 05/11/2017-4:30 AM
     * @description 进行MD5加密
     */
    public static String md5Sign(String signStr) {
        byte[] signInfo = null;
        try {
            signInfo = MessageDigest.getInstance("MD5").digest(signStr.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return byteArrayToHexString(signInfo);
    }


    private static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(byteToHexString(b[i]));
        }

        return sb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }

        return hexDigits[n / 16] + hexDigits[n % 16];
    }

    private static final String[] hexDigits = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"
    };

}
