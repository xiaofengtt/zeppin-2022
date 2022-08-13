package cn.zeppin.utility;

import java.security.MessageDigest;

public class MD5Util {
	
	/**
	 * MD5编码
	 * @param str
	 * @return
	 */
	public static String string2MD5(String str){
		
//		MessageDigest md5 = null;
//		try {
//			md5 = MessageDigest.getInstance("MD5");
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println(e.toString());
//			e.printStackTrace();
//			return "";
//					
//		}
//		char[] charArray = str.toCharArray();
//		byte[] byteArray = new byte[charArray.length];
//		
//		byte[] md5Bytes = md5.digest(byteArray);  
//        StringBuffer hexValue = new StringBuffer();  
//        for (int i = 0; i < md5Bytes.length; i++){  
//            int val = ((int) md5Bytes[i]) & 0xff;  
//            if (val < 16)  
//                hexValue.append("0");  
//            hexValue.append(Integer.toHexString(val));  
//        }  
//        return hexValue.toString();  
		
		if (null != str && !"".equals(str)) {
	         String s = null;
	         char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
	               '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	         try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            md.update(str.getBytes());
	            byte tmp[] = md.digest();
	            char charArray[] = new char[16 * 2];
	            int k = 0;
	            for (int i = 0; i < 16; i++) {

	               byte byte0 = tmp[i];
	               charArray[k++] = hexDigits[byte0 >>> 4 & 0xf];

	               charArray[k++] = hexDigits[byte0 & 0xf];
	            }
	            s = new String(charArray);

	         } catch (Exception e) {
//	            log.error("md5", e);
	            e.printStackTrace();
	         }
	         return s;
	      } else {
	         return null;
	      }
				
	}
	
	/** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
    public static String convertMD5(String inStr){  
  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
  
    }  

}
