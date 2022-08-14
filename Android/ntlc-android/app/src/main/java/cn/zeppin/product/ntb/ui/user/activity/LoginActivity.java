package cn.zeppin.product.ntb.ui.user.activity;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geng.library.baserx.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.ui.finance.adapter.TabPagerAdapter;
import cn.zeppin.product.ntb.ui.user.fragment.AccountFragment;
import cn.zeppin.product.ntb.ui.user.fragment.PhoneFragment;

import static cn.zeppin.product.ntb.R.id.tabLayout;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity {
    @Bind(tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.ll_loginView)
    LinearLayout llLoginView;
    @Bind(R.id.ivLog)
    ImageView ivLog;
    private String[] titles = {"手机快速登录", "账号密码登录"};
    private TabPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        AppApplication.getInstance().removeLocalInfo();
        //页面，数据源
        mFragments = new ArrayList<>();
        mFragments.add(new PhoneFragment());
        mFragments.add(new AccountFragment());
        //ViewPager的适配器
        mAdapter = new TabPagerAdapter(getSupportFragmentManager(), mFragments, titles);
        viewPager.setAdapter(mAdapter);
        //绑定
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.getTabAt(0).setCustomView(getTabView(0));
        mTabLayout.getTabAt(1).setCustomView(getTabView(1));
//        mTabLayout.addTab(mTabLayout.newTab().setCustomView(getTabView(1)));

        autoScrollView(llLoginView, viewPager);//弹出软键盘时滚动视图
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab_login, null);
        TextView txt_title = (TextView) view.findViewById(R.id.tv_tab_title);
        txt_title.setText(new SpannedString(setText(titles[position])));
        if (position == 0) {
            Drawable drawable = getResources().getDrawable(R.drawable.selector_login_phone);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            txt_title.setCompoundDrawables(drawable, null, null, null);
        }
        if (position == 1) {
            Drawable drawable = getResources().getDrawable(R.drawable.selector_login_account);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            txt_title.setCompoundDrawables(drawable, null, null, null);
        }
        return txt_title;
    }


    public void toRegisterActivity(View view) {
        startActivity(RegisterActivity.class);
        finish();
    }


    /**
     * @param root 最外层的View
     * @param scrollToView 不想被遮挡的View,会移动到这个Veiw的可见位置
     */
    private int scrollToPosition = 0;
    private long lastGoneTime = 0;

    private void autoScrollView(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Rect rect = new Rect();

                        //获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);

                        //获取root在窗体的不可视区域高度(被遮挡的高度)
                        int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;

                        //若不可视区域高度大于150，则键盘显示

                        if (rootInvisibleHeight > 150) {
                            ivLog.setVisibility(View.GONE);
                            lastGoneTime = System.currentTimeMillis();

//                            //获取scrollToView在窗体的坐标,location[0]为x坐标，location[1]为y坐标
//                            int[] location = new int[2];
//                            scrollToView.getLocationInWindow(location);
//
//                            //计算root滚动高度，使scrollToView在可见区域的底部
//                            int scrollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom - ContextUtils.dip2px(LoginActivity.this, 100);
//
//                            //注意，scrollHeight是一个相对移动距离，而scrollToPosition是一个绝对移动距离
//                            scrollToPosition += scrollHeight;

                        } else {
                            //键盘隐藏
//                            scrollToPosition = 0;
                            if (System.currentTimeMillis() - lastGoneTime > 500) {
                                ivLog.setVisibility(View.VISIBLE);
                            }
                        }
//                        root.scrollTo(0, scrollToPosition);

                    }
                });
    }

    private SpannableString setText(String content) {
        SpannableString ss = new SpannableString(content);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(16, true);//设置 字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().post(AppConstant.REFRESH_USER, true);//刷新用户信息
    }
}
