package com.geng.library.commonutils;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 集合操作工具类
 */
public class CollectionUtils {

    /**
     * 判断集合是否为null或者0个元素
     *
     * @param c
     * @return
     */
    public static boolean isNullOrEmpty(Collection c) {
        if (null == c || c.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {

        Pattern pattern = Pattern.compile("[0-9]*");

        return pattern.matcher(str).matches();

    }

    /**
     * 验证是否是手机号
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        Pattern p = Pattern
                .compile("(^1[3456789]\\d{9}$)");
        Matcher m = p.matcher(phone);
        return m.matches();
    }


    /**
     * 请输入8-20位字母数字组合密码
     *
     * @param pwd
     * @return
     */
    public static boolean checkPwd(String pwd) {
        Pattern p = Pattern
                .compile("(^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$)");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    /**
     * 验证日期字符串是否是YYYY-MM-DD格式
     *
     * @param str
     * @return
     */
    public static boolean isDataFormat(String str) {
        boolean flag = false;
        String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1 = Pattern.compile(regxStr);
        Matcher isNo = pattern1.matcher(str);
        if (isNo.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 验证付款是否有效
     *
     * @return
     */
    public static boolean isValidMoney(String money) {
        Pattern p = Pattern
                .compile("(/^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$/)");
        Matcher m = p.matcher(money);
        return m.matches();
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeyboard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // 判定是否需要隐藏
    public static boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    /**
     * 手机号码格式化
     *
     * @param phone
     * @return
     */
    public static String formatPhone(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }


    /**
     * 保留double类型小数后两位，不四舍五入，直接取小数后两位 比如：10.1269 返回：10.12
     *
     * @param doubleValue
     * @return
     */
    public static String calculateProfit(double doubleValue) {
        // 保留4位小数
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.0000");
        String result = df.format(doubleValue);

        // 截取第一位
        String index = result.substring(0, 1);

        if (".".equals(index)) {
            result = "0" + result;
        }

        // 获取小数 . 号第一次出现的位置
        int inde = firstIndexOf(result, ".");

        // 字符串截断
        return result.substring(0, inde + 3);
    }

    /**
     * 查找字符串pattern在str中第一次出现的位置
     *
     * @param str
     * @param pattern
     * @return
     */
    public static int firstIndexOf(String str, String pattern) {
        for (int i = 0; i < (str.length() - pattern.length()); i++) {
            int j = 0;
            while (j < pattern.length()) {
                if (str.charAt(i + j) != pattern.charAt(j))
                    break;
                j++;
            }
            if (j == pattern.length())
                return i;
        }
        return -1;
    }

    public static String numFormat4UnitString(Double dnumber) {
        BigDecimal number = new BigDecimal(dnumber);
//        number.setScale(2, BigDecimal.ROUND_DOWN);
//        Map<String, String> formatMap = new HashMap<String, String>();
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);//去掉逗号
        String numStr = "0";
        String unitStr = "";
        //格式化过程
        if (number != null) {
            if (number.compareTo(BigDecimal.valueOf(100000000)) == 1 || number.compareTo(BigDecimal.valueOf(100000000)) == 0) {
                BigDecimal num = number.divide(BigDecimal.valueOf(100000000));
                Integer scaleNum = num.scale();//获取小数位数
                nf.setMaximumFractionDigits(scaleNum);//设置格式化精度
                numStr = nf.format(num);
                unitStr = "亿";
            } else if (number.compareTo(BigDecimal.valueOf(10000)) == 1 || number.compareTo(BigDecimal.valueOf(10000)) == 0) {
                BigDecimal num = number.divide(BigDecimal.valueOf(10000));
                Integer scaleNum = num.scale();//获取小数位数
                nf.setMaximumFractionDigits(scaleNum);//设置格式化精度
                numStr = nf.format(num);
                unitStr = "万";
            } else {
//                Integer scaleNum = number.scale();//获取小数位数
                nf.setMaximumFractionDigits(2);//设置格式化精度2位
                numStr = nf.format(number);
                unitStr = "";
            }
        }
//        formatMap.put("numStr", numStr);
//        formatMap.put("unitStr", unitStr);
        return numStr + unitStr;
    }

    public static String numFormat4UnitString(BigDecimal number) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);//去掉逗号
        String numStr = "0";
        String unitStr = "";
        //格式化过程
        if (number != null) {
            if (number.compareTo(BigDecimal.valueOf(100000000)) == 1 || number.compareTo(BigDecimal.valueOf(100000000)) == 0) {
                BigDecimal num = number.divide(BigDecimal.valueOf(100000000));
                Integer scaleNum = num.scale();//获取小数位数
                nf.setMaximumFractionDigits(scaleNum);//设置格式化精度
                numStr = nf.format(num);
                unitStr = "亿";
            } else if (number.compareTo(BigDecimal.valueOf(10000)) == 1 || number.compareTo(BigDecimal.valueOf(10000)) == 0) {
                BigDecimal num = number.divide(BigDecimal.valueOf(10000));
                Integer scaleNum = num.scale();//获取小数位数
                nf.setMaximumFractionDigits(scaleNum);//设置格式化精度
                numStr = nf.format(num);
                unitStr = "万";
            } else {
//                Integer scaleNum = number.scale();//获取小数位数
                nf.setMaximumFractionDigits(2);//设置格式化精度2位
                numStr = nf.format(number);
                unitStr = "";
            }
        }
//        formatMap.put("numStr", numStr);
//        formatMap.put("unitStr", unitStr);
        return numStr + unitStr;
    }

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


    /**
     * 货币格式化（逗号）保留两位小数
     *
     * @param price
     * @return
     */
    public static String numFormat4UnitDetail(Double price) {
        BigDecimal number = new BigDecimal(price);
        String formatStr = "0.00";
        NumberFormat nf = NumberFormat.getInstance();

        //格式化过程
        if (number != null) {
            Integer scaleNum = 2;//获取小数位数
            nf.setMaximumFractionDigits(scaleNum);//设置格式化精度
            formatStr = nf.format(number);
        }

        if (formatStr.indexOf(".") > -1) {
            if (formatStr.length() - formatStr.indexOf(".") == 2) {
                formatStr += "0";
            }
        } else {
            formatStr += ".00";
        }
        return formatStr;
    }

    /**
     * 货币格式化（逗号）
     *
     * @param price
     * @return
     */
    public static String numFormat4UnitDetailLess(Double price) {
        BigDecimal number = new BigDecimal(price);
        String formatStr = "0.00";
        NumberFormat nf = NumberFormat.getInstance();

        //格式化过程
        if (number != null) {
            Integer scaleNum = 2;//获取小数位数
            nf.setMaximumFractionDigits(scaleNum);//设置格式化精度
            formatStr = nf.format(number);
        }
        return formatStr;
    }

    /**
     * double转String,保留小数点后两位
     * @param num
     * @return
     */
    public static String doubleToString(double num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }
}
