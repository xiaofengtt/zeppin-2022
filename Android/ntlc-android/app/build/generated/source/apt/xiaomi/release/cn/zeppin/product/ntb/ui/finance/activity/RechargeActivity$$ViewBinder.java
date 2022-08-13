// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.finance.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RechargeActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.finance.activity.RechargeActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624226, "field 'ivPayBankIcon'");
    target.ivPayBankIcon = finder.castView(view, 2131624226, "field 'ivPayBankIcon'");
    view = finder.findRequiredView(source, 2131624227, "field 'tvPayBankName'");
    target.tvPayBankName = finder.castView(view, 2131624227, "field 'tvPayBankName'");
    view = finder.findRequiredView(source, 2131624091, "field 'llSelectBankCard'");
    target.llSelectBankCard = finder.castView(view, 2131624091, "field 'llSelectBankCard'");
    view = finder.findRequiredView(source, 2131624232, "field 'etPrice'");
    target.etPrice = finder.castView(view, 2131624232, "field 'etPrice'");
    view = finder.findRequiredView(source, 2131624233, "field 'btnPay'");
    target.btnPay = finder.castView(view, 2131624233, "field 'btnPay'");
    view = finder.findRequiredView(source, 2131624154, "field 'tvError'");
    target.tvError = finder.castView(view, 2131624154, "field 'tvError'");
    view = finder.findRequiredView(source, 2131624229, "field 'llAddBank'");
    target.llAddBank = finder.castView(view, 2131624229, "field 'llAddBank'");
    view = finder.findRequiredView(source, 2131624210, "field 'tvSingleLimit'");
    target.tvSingleLimit = finder.castView(view, 2131624210, "field 'tvSingleLimit'");
    view = finder.findRequiredView(source, 2131624211, "field 'tvDailyLimit'");
    target.tvDailyLimit = finder.castView(view, 2131624211, "field 'tvDailyLimit'");
    view = finder.findRequiredView(source, 2131624228, "field 'llBankLimit'");
    target.llBankLimit = finder.castView(view, 2131624228, "field 'llBankLimit'");
    view = finder.findRequiredView(source, 2131624155, "field 'llContent'");
    target.llContent = finder.castView(view, 2131624155, "field 'llContent'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.ivPayBankIcon = null;
    target.tvPayBankName = null;
    target.llSelectBankCard = null;
    target.etPrice = null;
    target.btnPay = null;
    target.tvError = null;
    target.llAddBank = null;
    target.tvSingleLimit = null;
    target.tvDailyLimit = null;
    target.llBankLimit = null;
    target.llContent = null;
  }
}
