package com.makati.common.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    private static final String PHONE_REGEX = "^1\\d{10}$";

    /**
     * 是否是手机号
     * @param str
     * @return
     */
    public static boolean isPhone(String str){
        if(str == null ){
            return false;
        }
        Matcher matcher = Pattern.compile(PHONE_REGEX).matcher(str);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isQQOrWX(String qqorwx) {

        //QQ号最短5位，微信号最短是6位最长20位
        Pattern p = Pattern.compile("^[a-zA-Z0-9_-]{5,19}$");
        Matcher m = p.matcher(qqorwx);
        return m.matches();
    }
//    public static void main(String[] args) {
//        boolean phone = isPhone("28320809352");
//        phone = isPhone(null);
//        System.out.println(phone);
//    }
}
