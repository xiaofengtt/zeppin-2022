package org.linlinjava.litemall.wx.util.phoneyzm;

import java.security.SecureRandom;
import java.util.UUID;

public class IdGen {

    private static SecureRandom random = new SecureRandom();

    /**
     * 訂單號生成工具
     * @author Allan
     */
    public static String getOrderNum(String type){
        int hashCodeV = ((System.currentTimeMillis() + "").substring(1) +
                (System.nanoTime() + "").substring(7, 10).hashCode()).hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        String orderId=type + String.format("%013d", hashCodeV);
        // System.out.println(orderId);
        return orderId;
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String randomLong(int count) {
        String result=Math.abs(random.nextLong())+"";
        return result.substring(0,count);
    }

}
