// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.bank.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PaySuccessActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.bank.activity.PaySuccessActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131624213, "field 'tvContent'");
    target.tvContent = finder.castView(view, 2131624213, "field 'tvContent'");
    view = finder.findRequiredView(source, 2131624208, "field 'tvTime'");
    target.tvTime = finder.castView(view, 2131624208, "field 'tvTime'");
    view = finder.findRequiredView(source, 2131624153, "field 'btnConfirm'");
    target.btnConfirm = finder.castView(view, 2131624153, "field 'btnConfirm'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvTitle = null;
    target.tvContent = null;
    target.tvTime = null;
    target.btnConfirm = null;
  }
}
