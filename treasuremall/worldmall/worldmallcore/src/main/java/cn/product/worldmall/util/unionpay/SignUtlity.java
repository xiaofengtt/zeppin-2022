package cn.product.worldmall.util.unionpay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SignUtlity {
	/**
    *
    * @param sArray
    * @param key
    * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
    */
   @SuppressWarnings("rawtypes")
	public static String BuildMysign(Map sArray, String key, String charset, boolean isEcode) throws NoSuchAlgorithmException, UnsupportedEncodingException {
       if(sArray!=null && sArray.size()>0){
           StringBuilder prestr = CreateLinkString(sArray);
           prestr.append(key);
           System.out.println("prestr====>" + prestr);
           //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
           if(isEcode) {
        	   System.out.println(URLEncoder.encode(prestr.toString(), charset));
        	   return md5(URLEncoder.encode(prestr.toString(), charset),charset);
           }
           return md5(prestr.toString(), charset);
       }
       return null;
   }

   /**
    * 拼装参数
    * @param params
    * @return
    */
   @SuppressWarnings({ "rawtypes", "unchecked" })
	public static StringBuilder CreateLinkString(Map params){
       List keys = new ArrayList(params.keySet());
       Collections.sort(keys);
       StringBuilder prestr = new StringBuilder();
       String key="";
       String value="";
       for (int i = 0; i < keys.size(); i++) {
           key=(String) keys.get(i);
           value = (String) params.get(key);
           if(value == null ||
                   key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type") || 
                   key.equalsIgnoreCase("pay_attach") || key.equalsIgnoreCase("pay_productname")){
               continue;
           }
           prestr.append(key).append("=").append(value).append("&");
       }
       return prestr.deleteCharAt(prestr.length()-1);
   }
	
	
	
	/**
	 * 签名加密MD5,字符串大写处理
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String md5(String str, String charset) throws NoSuchAlgorithmException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes(charset));
            byte[] byteDigest = md.digest();
            int i;
            //字符数组转换成字符串
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // 32位加密
            return buf.toString().toUpperCase();
            // 16位的加密
             //return buf.toString().substring(8, 24).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(
                    "System doesn't support your  EncodingException.");

        }
    }
}
