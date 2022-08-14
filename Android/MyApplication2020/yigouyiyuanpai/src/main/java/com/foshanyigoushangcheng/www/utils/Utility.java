package com.foshanyigoushangcheng.www.utils;

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
}
