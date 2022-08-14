package cn.product.worldmall.util;

import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {
	/**
	 * base64加密
	 */
	public static String getBase64(String str) {  
		byte[] b = null;  
		String s = null;  
		try {  
		    b = str.getBytes("utf-8");  
	    } catch (UnsupportedEncodingException e) {  
	    	e.printStackTrace();
	    }  
	    if (b != null) {  
	        s = new BASE64Encoder().encode(b);  
		}  
		return s;  
	}  
  
	/**
	 * base64解密
	 */
	public static String getFromBase64(String s) {  
        byte[] b;  
        String result = null;  
        if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
            	e.printStackTrace();
            }  
        }  
        return result;  
    }
    
    public static void main(String[] args) throws Exception {
		System.out.println(getFromBase64("MDExNTg4MjMyMjE4NTM1MmFhNGUzNzRiYjBiOTg0MzJiODUwZTZiNmNjZmZlNmI4ZDA5NzA3NjBlYTQ3OTMyNGM5YTNmMWRmMmQyNjU0ZTk5ZTM3NjE1NGY0MDkxMGUxMGZjZGZmM2FlY2IxMGY3"));
//		
//		String token = "MDExNTg1MzY3MjMxNDkzMTM3MTc1ODIxNzEyYWE0ZTM3NGJiMGI5ODQzMmI4NTBlNmI2Y2NmZmU2YjhkMDk3MDc2MGVhNDc5MzI0YzlhM2YxZGYyZDI2NTRlMWU4ZGU5N2YyNTUwZjlhMmM5MDQxN2NlNDZlOTA3NDYxNjM2ZDU5ZjA5OThhMjA4ODdhZDliNWIxYTE3ZGJiOQ==";
//		if(token != null && !"".equals(token)){
//			token = Base64Util.getFromBase64(token);
//			String deviceNumber = token.substring(0,2);
//			if(deviceNumber == null || "".equals(deviceNumber)){
////				return false;
//				System.out.println(1);
//			}
//			if(Utlity.DEVICE_APP.equals(deviceNumber)){//app
//				String timestamp = token.substring(2,15);
//				if(timestamp == null || "".equals(timestamp)){
////					return false;
//					System.out.println(2);
//				}
//				long time = Long.parseLong(timestamp);
//				long nowTime = System.currentTimeMillis();
//				if(time <= nowTime){
//					if(Utlity.checkLessTime(time, nowTime)){
////						return false;
//						System.out.println(3);
//					}
//				} else {
////					return false;
//					System.out.println(4);
//				}
//				String mobile = token.substring(15, 26);
//				if(mobile == null || "".equals(mobile)){
////					return false;
//					System.out.println(5);
//				}
//				FrontUser frontUser = null;
//				//this.frontUserService.getByMobile(mobile);
//				InputParams params = new InputParams("frontUserService", "getByMobile");
//				params.addParams("mobile", null, mobile);
////				DataResult<Object> result = (DataResult<Object>) this.transferService.execute(params);
////				if(result.getData() != null) {
////					frontUser = (FrontUser) result.getData();
////				}
//				if(frontUser == null){
////					return false;
//					System.out.println(6);
//				}
//				String md5Str = token.substring(26);
//				if(md5Str == null || "".equals(md5Str)){
////					return false;
//				}
//				
//				String realMD5Str = DESUtil.encryptStr(Utlity.KEY_FRONT_MD5+timestamp+mobile);
//				if(md5Str.equals(realMD5Str)){
////					request.setAttribute("frontUser", frontUser);
////					return true;
//					System.out.println(md5Str);
//					System.out.println(realMD5Str);
//					System.out.println(7);
//				}
//				
//			} else {
////				return false;
//				System.out.println(8);
//			}
//		}
		
	}
}
