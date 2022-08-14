package com.foshanyigoushangcheng.www.utils;

import android.content.res.Resources;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.foshanyigoushangcheng.www.app.AppApplication;


public class ToastUtil {


    private static Toast toast;

    /**
     * 初始化Toast(消息，时间)
     */
    private static Toast initToast(CharSequence message, int duration) {
        if (toast == null) {
            toast = Toast.makeText(AppApplication.getAppContext(), message, duration);
        } else {
            //设置文字
            toast.setText(message);
            //设置存续期间
            toast.setDuration(duration);
        }
        return toast;
    }

    /**
     * 短时间显示Toast(消息 String等)
     */
    public static void showShort(CharSequence message) {
        initToast(message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 短时间显示Toast（资源id)
     */
    public static void showShort(int strResId) {
        initToast(AppApplication.getAppContext().getResources().getText(strResId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast(消息 String等)
     */
    public static void showLong(CharSequence message) {
        initToast(message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast（资源id)
     */
    public static void showLong(int strResId) {
        initToast(AppApplication.getAppContext().getResources().getText(strResId), Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间(消息 String等，时间)
     */
    public static void show(CharSequence message, int duration) {
        initToast(message, duration).show();
    }

    /**
     * 自定义显示Toast时间(消息 资源id，时间)
     */
    public static void show(int strResId, int duration) {
        initToast(AppApplication.getAppContext().getResources().getText(strResId), duration).show();
    }

    public static void showToastCenter( String toastStr) {
        Toast toast = Toast.makeText(AppApplication.getAppContext().getApplicationContext(), toastStr, Toast.LENGTH_SHORT);
        int tvToastId = Resources.getSystem().getIdentifier("message", "id", "android");
        TextView tvToast = ((TextView) toast.getView().findViewById(tvToastId));
        if(tvToast != null){
            tvToast.setGravity(Gravity.CENTER);
        }
        toast.show();
    }

}