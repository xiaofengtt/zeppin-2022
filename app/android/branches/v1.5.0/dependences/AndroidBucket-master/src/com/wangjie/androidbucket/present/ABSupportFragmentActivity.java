package com.wangjie.androidbucket.present;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import com.wangjie.androidbucket.manager.BaseActivityManager;
import com.wangjie.androidbucket.mvp.ABActivityViewer;
import com.wangjie.androidbucket.mvp.ABBasePresenter;

/**
 * Created by wangjie on 6/15/14.
 */
/**
 * @deprecated Use {@link com.wangjie.androidbucket.present.ABAppCompatActivity} instead.
 */
@Deprecated
public class ABSupportFragmentActivity extends FragmentActivity implements ABActivityViewer, ABActivityCommon {
    private BaseActivityManager baseActivityManager;

    private boolean isActivityLifeCycleAutoCallBack = true;
    private ABBasePresenter presenter;

    public boolean isActivityLifeCycleAutoCallBack() {
        return isActivityLifeCycleAutoCallBack;
    }

    public void setActivityLifeCycleAutoCallBack(boolean isActivityLifeCycleAutoCallBack) {
        this.isActivityLifeCycleAutoCallBack = isActivityLifeCycleAutoCallBack;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (shouldCallBack() && !isFinishing()) {
            baseActivityManager.dispatchActivityCreate(savedInstanceState);
        }

    }

    private boolean isFirstFocused = true;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
            if (isFirstFocused && hasFocus) {
                isFirstFocused = false;
                onWindowInitialized();
            }
    }

    /**
     * 界面渲染完毕，可在这里进行初始化工作，建议在这里启动线程进行初始化工作
     */
    public void onWindowInitialized() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (shouldCallBack()) {
            baseActivityManager.dispatchActivityDestory();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (shouldCallBack() && !isFinishing()) {
            baseActivityManager.dispatchActivityResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (shouldCallBack() && !isFinishing()) {
            baseActivityManager.dispatchActivityPause();
        }
    }

    @Override
    public BaseActivityManager getBaseActivityManager() {
        ensureBaseActivityMananger();
        return baseActivityManager;
    }

    @Override
    public void setBaseActivityManager(BaseActivityManager baseActivityManager) {
        this.baseActivityManager = baseActivityManager;
    }

    private void ensureBaseActivityMananger() {
        if (null == baseActivityManager) {
            this.baseActivityManager = new BaseActivityManager(this);
        }
    }

    private boolean shouldCallBack() {
        return null != baseActivityManager && isActivityLifeCycleAutoCallBack;
    }


    /**
     * ******************* ABActivityViewer impl ********************
     */

    @Override
    public void showToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInfoDialog(String message) {
        showInfoDialog(null, message);
    }

    @Override
    public void showInfoDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    public void showLoadingDialog(String message) {

    }

    @Override
    public void cancelLoadingDialog() {

    }

    @Override
    public void showInfoDialog(String title, String message, String okButtonText) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(okButtonText, null)
                .show();
    }

    @Override
    public void registerPresenter(ABBasePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void closeAllTask() {
        if (null != presenter) {
            presenter.closeAllTask();
        }
    }
}
