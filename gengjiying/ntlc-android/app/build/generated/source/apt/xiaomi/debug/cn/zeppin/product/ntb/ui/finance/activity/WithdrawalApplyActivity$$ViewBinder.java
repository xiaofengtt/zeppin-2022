// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.finance.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WithdrawalApplyActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.finance.activity.WithdrawalApplyActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131624266, "field 'tvDes'");
    target.tvDes = finder.castView(view, 2131624266, "field 'tvDes'");
    view = finder.findRequiredView(source, 2131624093, "field 'tvBankName'");
    target.tvBankName = finder.castView(view, 2131624093, "field 'tvBankName'");
    view = finder.findRequiredView(source, 2131624175, "field 'tvPrice'");
    target.tvPrice = finder.castView(view, 2131624175, "field 'tvPrice'");
    view = finder.findRequiredView(source, 2131624153, "field 'btnConfirm'");
    target.btnConfirm = finder.castView(view, 2131624153, "field 'btnConfirm'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvTitle = null;
    target.tvDes = null;
    target.tvBankName = null;
    target.tvPrice = null;
    target.btnConfirm = null;
  }
}
