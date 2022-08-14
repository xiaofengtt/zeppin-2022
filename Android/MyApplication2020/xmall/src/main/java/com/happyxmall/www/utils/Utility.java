package com.happyxmall.www.utils;

import java.util.Currency;
import java.util.Locale;

public class Utility {
    // 获取字体大小
     public static float adjustFontSize(int screenWidth, int screenHeight) {
//         screenWidth = screenWidth < screenHeight ? screenWidth : screenHeight;
         /**
           * 1. 在视图的 onsizechanged里获取视图宽度，一般情况下默认宽度是320，所以计算一个缩放比率 rate = (float)
           * w/320 w是实际宽度 2.然后在设置字体尺寸时 paint.setTextSize((int)(8*rate));
           * 8是在分辨率宽为320 下需要设置的字体大小 实际字体大小 = 默认字体大小 x rate
           */
         float rate = (5 * (float) screenWidth / 400); // 我自己测试这个倍数比较适合，当然你可以测试后再修改
         return rate < 15 ? 15 : rate; // 字体太小也不好看的
     }

    // 获取topbar高度
    public static int adjustTopbarSize(int screenWidth, int screenHeight) {
//         screenWidth = screenWidth < screenHeight ? screenWidth : screenHeight;
        /**
         * 1. 在视图的 onsizechanged里获取视图宽度，一般情况下默认宽度是320，所以计算一个缩放比率 rate = (float)
         * w/320 w是实际宽度 2.然后在设置字体尺寸时 paint.setTextSize((int)(8*rate));
         * 8是在分辨率宽为320 下需要设置的字体大小 实际字体大小 = 默认字体大小 x rate
         */
        int rate = (int)(5 * (float) screenWidth / 320); // 我自己测试这个倍数比较适合，当然你可以测试后再修改
        return rate < 15 ? 15 : rate; // 字体太小也不好看的
    }


    public static String getCurrencyInfo(){
        Locale locale = Locale.getDefault();
        System.out.println("Locale: " + locale.getDisplayName());
        Currency currency = Currency.getInstance(locale);
        String currencyCode = currency.getCurrencyCode();
        String symbol = currency.getSymbol();
        String country = locale.getCountry();
        if("CNY".equals(currencyCode)){
            currencyCode = "USD";
            symbol = "$";
            country = "US";
        }
//        System.out.println("Currency Code: " + currencyCode);
//        System.out.println("Symbol: " + symbol);
//        System.out.println("Default Fraction Digits: " + currency.getDefaultFractionDigits());
//        System.out.println(locale.getCountry());
        return currencyCode+"_"+symbol+"_"+country;
    }

    public static String getCountryCode(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        if("CN".equals(country)){
            country = "US";
        }
        return country;
    }

    /**
     *
     * @param value
     *            要检测的String值 是否为空
     * @return 空返回true
     */
    public static boolean checkStringNull(String value) {
        if (value == null || value.equals("")) {
            return true;
        }
        return false;
    }
}
