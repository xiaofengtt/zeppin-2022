package cn.zeppin.product.utility.itic;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Utils {

	private static Logger logger = LoggerFactory.getLogger(MD5Utils.class);
	
	private static MessageDigest md5 = null;
	
	static {
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
	
	/**
	 * 用于获取String类型的md5值
	 * @param string
	 * @return
	 */
	public static String getMD5(String str) {
		byte [] bs = md5.digest(str.getBytes());
		StringBuffer sb = new StringBuffer(40);
		for (byte x : bs) {
			if ((x & 0xff)>>4 == 0) {
				sb.append("0").append(Integer.toHexString(x & 0xff));
			}else {
				sb.append(Integer.toHexString(x & 0xff));
			}
		}
		return sb.toString();
	}
	
	/**
	 * 加密解密算法 执行一次加密，两次解密(加密可能造成乱码)
	 * @param Str
	 * @return
	 */
	public static String MD5encrypt(String Str){
        char[] a = Str.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
	}

    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
