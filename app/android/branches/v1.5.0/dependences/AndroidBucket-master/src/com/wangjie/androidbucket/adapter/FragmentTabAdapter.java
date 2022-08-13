package com.wangjie.androidbucket.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wangjie.androidbucket.R;
import com.wangjie.androidbucket.application.ABApplication;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-10-10
 * Time: 上午9:25
 */
public class FragmentTabAdapter<T extends Fragment> implements RadioGroup.OnCheckedChangeListener {
    private List<T> fragments; // 一个tab页面对应一个Fragment
    private RadioGroup rgs; // 用于切换tab
    private FragmentManager fragmentActivity; // Fragment所属的Activity
    private int fragmentContentId; // Activity中所要被替换的区域的id

    private int currentTab; // 当前Tab页面索引

    private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener; // 用于让调用者在切换tab时候增加新的功能
    private OnTabClickedListener onTabClickedListener; // 点击tab时回调
    private OnTabDoubleClickListener onTabDoubleClickListener;

    public FragmentTabAdapter(FragmentManager fragmentManager, List<T> fragments, int fragmentContentId, RadioGroup rgs, OnTabDoubleClickListener onTabDoubleClickListener) {
        this.fragments = fragments;
        this.rgs = rgs;
        this.fragmentActivity = fragmentManager;
        this.fragmentContentId = fragmentContentId;

        // 默认显示第一页
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(fragmentContentId, fragments.get(0));
        ft.commit();

        rgs.setOnCheckedChangeListener(this);
        //双击tab
        if (onTabDoubleClickListener != null) {
            this.onTabDoubleClickListener = onTabDoubleClickListener;
            this.onTabDoubleClickListener.setOnTabDoubliClickedListener(rgs, 0, currentTab);
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        for (int i = 0; i < rgs.getChildCount(); i++) {
            if (rgs.getChildAt(i).getId() == checkedId) {
                if (ABApplication.getInstance().getString(R.string.tag_fragment_tab_click_only).equals(rgs.getChildAt(i).getTag())) {
                    ((RadioButton) rgs.getChildAt(currentTab)).setChecked(true);
                    if (null != onTabClickedListener) {
                        onTabClickedListener.onTabClickedListener();
                    }
                    return;
                }
                switchFragment(i);
                // 如果设置了切换tab额外功能功能接口
                if (null != onRgsExtraCheckedChangedListener) {
                    onRgsExtraCheckedChangedListener.OnRgsExtraCheckedChanged(radioGroup, checkedId, i);
                }

                if (null != onTabDoubleClickListener) {
                    this.onTabDoubleClickListener.setOnTabDoubliClickedListener(radioGroup, checkedId, i);
                }
                break;
            }
        }

    }

    private void switchFragment(int index) {
        Fragment to = fragments.get(index);
        Fragment current = getCurrentFragment();
        if (current != to) {
            FragmentTransaction transaction = obtainFragmentTransaction(index);
            if (to.isAdded()) {
                transaction.hide(current).show(to).commit();
            } else {
                transaction.hide(current).add(fragmentContentId, to).show(to).commit();
            }
            currentTab = index;
        }
    }

    /**
     * 获取一个带动画的FragmentTransaction
     *
     * @param index
     * @return
     */
    private FragmentTransaction obtainFragmentTransaction(int index) {
        FragmentTransaction ft = fragmentActivity.beginTransaction();
        // 设置切换动画
        if (index > currentTab) {
            ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        } else {
            ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
        }
        return ft;
    }

    public int getCurrentTab() {
        return currentTab;
    }

    public Fragment getCurrentFragment() {
        return fragments.get(currentTab);
    }

    public void setOnRgsExtraCheckedChangedListener(OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
        this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
    }

    /**
     * 切换tab额外功能功能接口
     */
    public static class OnRgsExtraCheckedChangedListener {
        public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
        }
    }

    /**
     * tab点击回调（只有加了TAG[R.string.tag_fragment_tab_click_only]的RadioButton才会回调）
     */
    public static class OnTabClickedListener {
        public void onTabClickedListener() {
        }
    }

    public static interface OnTabDoubleClickListener {
        void setOnTabDoubliClickedListener(RadioGroup radioGroup, int radiobuttonId, int index);
    }
}
