package com.makati.common.util.lottery;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * Created by json on 2018/1/11.
 */
@Slf4j
public class DoubleUtil {

    /**
     * 截取两位小数
     * @param value:double值
     * @return
     */
    public static Double transforDouble(double value){
        return transforDouble(value,2);
    }

    /**
     *
     * @param value
     * @param newScale:截取几位小数
     * @return
     */
    public static Double transforDouble(double value,int newScale){
        BigDecimal bd=new BigDecimal(value);
        return bd.setScale(newScale, BigDecimal.ROUND_DOWN).doubleValue();
    }

    public static int getInt(Object o){
       if(o == null) {
           return 0;
       }
       try {
          return Integer.valueOf(o.toString());
       }catch (Exception e){
           log.error(" transfor int fail ,o ={}",o);
           return 0;
       }
    }
}
