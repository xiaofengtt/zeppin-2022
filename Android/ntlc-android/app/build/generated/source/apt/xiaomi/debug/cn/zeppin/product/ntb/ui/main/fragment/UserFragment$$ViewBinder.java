// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.main.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UserFragment$$ViewBinder<T extends cn.zeppin.product.ntb.ui.main.fragment.UserFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624187, "field 'ivHead'");
    target.ivHead = finder.castView(view, 2131624187, "field 'ivHead'");
    view = finder.findRequiredView(source, 2131624355, "field 'tvUserName'");
    target.tvUserName = finder.castView(view, 2131624355, "field 'tvUserName'");
    view = finder.findRequiredView(source, 2131624356, "field 'llUnLogin'");
    target.llUnLogin = finder.castView(view, 2131624356, "field 'llUnLogin'");
    view = finder.findRequiredView(source, 2131624358, "field 'llIdCard'");
    target.llIdCard = finder.castView(view, 2131624358, "field 'llIdCard'");
    view = finder.findRequiredView(source, 2131624362, "field 'llBankCard'");
    target.llBankCard = finder.castView(view, 2131624362, "field 'llBankCard'");
    view = finder.findRequiredView(source, 2131624365, "field 'rlTradingRecord'");
    target.rlTradingRecord = finder.castView(view, 2131624365, "field 'rlTradingRecord'");
    view = finder.findRequiredView(source, 2131624372, "field 'rlContactUs'");
    target.rlContactUs = finder.castView(view, 2131624372, "field 'rlContactUs'");
    view = finder.findRequiredView(source, 2131624373, "field 'btnLogout'");
    target.btnLogout = finder.castView(view, 2131624373, "field 'btnLogout'");
    view = finder.findRequiredView(source, 2131624361, "field 'tvAuthStatus'");
    target.tvAuthStatus = finder.castView(view, 2131624361, "field 'tvAuthStatus'");
    view = finder.findRequiredView(source, 2131624364, "field 'tvBindStatus'");
    target.tvBindStatus = finder.castView(view, 2131624364, "field 'tvBindStatus'");
    view = finder.findRequiredView(source, 2131624352, "field 'rlLogin'");
    target.rlLogin = finder.castView(view, 2131624352, "field 'rlLogin'");
    view = finder.findRequiredView(source, 2131624359, "field 'ivIdCard'");
    target.ivIdCard = finder.castView(view, 2131624359, "field 'ivIdCard'");
    view = finder.findRequiredView(source, 2131624363, "field 'ivBankCard'");
    target.ivBankCard = finder.castView(view, 2131624363, "field 'ivBankCard'");
    view = finder.findRequiredView(source, 2131624351, "field 'idToolbar'");
    target.idToolbar = view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131624235, "field 'tvAlipayIsBind'");
    target.tvAlipayIsBind = finder.castView(view, 2131624235, "field 'tvAlipayIsBind'");
    view = finder.findRequiredView(source, 2131624368, "field 'rlBindingAlipay'");
    target.rlBindingAlipay = finder.castView(view, 2131624368, "field 'rlBindingAlipay'");
    view = finder.findRequiredView(source, 2131624371, "field 'tvVersion'");
    target.tvVersion = finder.castView(view, 2131624371, "field 'tvVersion'");
    view = finder.findRequiredView(source, 2131624369, "field 'rlVersionUpdate'");
    target.rlVersionUpdate = finder.castView(view, 2131624369, "field 'rlVersionUpdate'");
    view = finder.findRequiredView(source, 2131624222, "field 'rlCoupon'");
    target.rlCoupon = finder.castView(view, 2131624222, "field 'rlCoupon'");
    view = finder.findRequiredView(source, 2131624370, "field 'ivNewVersion'");
    target.ivNewVersion = finder.castView(view, 2131624370, "field 'ivNewVersion'");
    view = finder.findRequiredView(source, 2131624354, "field 'ivNewNotice'");
    target.ivNewNotice = finder.castView(view, 2131624354, "field 'ivNewNotice'");
    view = finder.findRequiredView(source, 2131624353, "field 'flNotice'");
    target.flNotice = finder.castView(view, 2131624353, "field 'flNotice'");
    view = finder.findRequiredView(source, 2131624357, "field 'tvToLogin'");
    target.tvToLogin = finder.castView(view, 2131624357, "field 'tvToLogin'");
    view = finder.findRequiredView(source, 2131624360, "field 'textView3'");
    target.textView3 = finder.castView(view, 2131624360, "field 'textView3'");
    view = finder.findRequiredView(source, 2131624366, "field 'tvCouponCount'");
    target.tvCouponCount = finder.castView(view, 2131624366, "field 'tvCouponCount'");
    view = finder.findRequiredView(source, 2131624367, "field 'rlSafe' and method 'onClick'");
    target.rlSafe = finder.castView(view, 2131624367, "field 'rlSafe'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.ivHead = null;
    target.tvUserName = null;
    target.llUnLogin = null;
    target.llIdCard = null;
    target.llBankCard = null;
    target.rlTradingRecord = null;
    target.rlContactUs = null;
    target.btnLogout = null;
    target.tvAuthStatus = null;
    target.tvBindStatus = null;
    target.rlLogin = null;
    target.ivIdCard = null;
    target.ivBankCard = null;
    target.idToolbar = null;
    target.ivBack = null;
    target.tvTitle = null;
    target.tvAlipayIsBind = null;
    target.rlBindingAlipay = null;
    target.tvVersion = null;
    target.rlVersionUpdate = null;
    target.rlCoupon = null;
    target.ivNewVersion = null;
    target.ivNewNotice = null;
    target.flNotice = null;
    target.tvToLogin = null;
    target.textView3 = null;
    target.tvCouponCount = null;
    target.rlSafe = null;
  }
}
