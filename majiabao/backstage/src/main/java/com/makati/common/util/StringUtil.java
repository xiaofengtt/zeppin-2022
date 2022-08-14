package com.makati.common.util;

import java.io.UnsupportedEncodingException;

public class StringUtil {
    private static final String CHARSET_NAME = "UTF-8";

    public static String object2String(Object obj){
        if(obj == null){
            return "";
        }
        return obj.toString();
    }

    /**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static byte[] getBytes(String str){
        if (str != null){
            try {
                return str.getBytes(CHARSET_NAME);
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }else{
            return null;
        }
    }


}
