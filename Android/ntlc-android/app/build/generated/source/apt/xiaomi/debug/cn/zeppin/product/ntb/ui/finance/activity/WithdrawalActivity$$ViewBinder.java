// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.finance.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WithdrawalActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.finance.activity.WithdrawalActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624092, "field 'ivBankIcon'");
    target.ivBankIcon = finder.castView(view, 2131624092, "field 'ivBankIcon'");
    view = finder.findRequiredView(source, 2131624093, "field 'tvBankName'");
    target.tvBankName = finder.castView(view, 2131624093, "field 'tvBankName'");
    view = finder.findRequiredView(source, 2131624091, "field 'llSelectBankCard'");
    target.llSelectBankCard = finder.castView(view, 2131624091, "field 'llSelectBankCard'");
    view = finder.findRequiredView(source, 2131624232, "field 'etPrice'");
    target.etPrice = finder.castView(view, 2131624232, "field 'etPrice'");
    view = finder.findRequiredView(source, 2131624175, "field 'tvPrice'");
    target.tvPrice = finder.castView(view, 2131624175, "field 'tvPrice'");
    view = finder.findRequiredView(source, 2131624154, "field 'tvError'");
    target.tvError = finder.castView(view, 2131624154, "field 'tvError'");
    view = finder.findRequiredView(source, 2131624265, "field 'btnWithdrawal'");
    target.btnWithdrawal = finder.castView(view, 2131624265, "field 'btnWithdrawal'");
    view = finder.findRequiredView(source, 2131624229, "field 'llAddBank'");
    target.llAddBank = finder.castView(view, 2131624229, "field 'llAddBank'");
    view = finder.findRequiredView(source, 2131624155, "field 'llContent'");
    target.llContent = finder.castView(view, 2131624155, "field 'llContent'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.ivBankIcon = null;
    target.tvBankName = null;
    target.llSelectBankCard = null;
    target.etPrice = null;
    target.tvPrice = null;
    target.tvError = null;
    target.btnWithdrawal = null;
    target.llAddBank = null;
    target.llContent = null;
  }
}
