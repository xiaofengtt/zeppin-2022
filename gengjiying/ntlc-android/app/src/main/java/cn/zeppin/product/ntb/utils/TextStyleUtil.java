package cn.zeppin.product.ntb.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;

import cn.zeppin.product.ntb.R;


/**
 * 描述：
 * 开发人: geng
 * 创建时间: 17/12/7
 */

public class TextStyleUtil {

    /**
     * 设置字符串start到end之间字符的样式为14号字体大小
     *
     * @param content
     * @param start
     * @param end
     * @return
     */
    public static SpannableString setTxtLast14Style(Context context, String content, int start, int end) {
        SpannableString rate = new SpannableString(content);
        rate.setSpan(new TextAppearanceSpan(context, R.style.ProductRateTxtStyle), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return rate;
    }

    /**
     * 设置字符串start到end之间字符的样式为14号字体大小
     *
     * @param content
     * @param start
     * @param end
     * @return
     */
    public static SpannableString setTxtLast8Style(Context context, String content, int start, int end) {
        SpannableString rate = new SpannableString(content);
        rate.setSpan(new TextAppearanceSpan(context, R.style.GiftPriceTxtStyle), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return rate;
    }
}
