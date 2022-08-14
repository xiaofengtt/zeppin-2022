package com.makati.common.util;

import java.util.*;

public class LotteryUtils {
    /**
     * 生成充值订单号
     * @return
     */
    public static String  getChargeOrderNum(){
        String order =DateUtils.getDate("yyMMddHHmmsss")+getStrFromList(7);
        return order;
    }

    /**
     * 从list里获取指定位数的字符串
     * @param num
     * @return
     */
    private static  String getStrFromList(int num){
        List<String>  strList=Arrays.asList(
                new String[]{"0","1","2","3","4","5","6","7","8","9",
                        "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"});
        Collections.shuffle(strList);
        String str="";
        for (int i = 0; i<num; i++) {
            str+=strList.get(i);
        }
        return str;
    }
}
