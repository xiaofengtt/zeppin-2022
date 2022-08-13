// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ResetPwdActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.activity.ResetPwdActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131624242, "field 'etNewPwd'");
    target.etNewPwd = finder.castView(view, 2131624242, "field 'etNewPwd'");
    view = finder.findRequiredView(source, 2131624243, "field 'etConfirmPwd'");
    target.etConfirmPwd = finder.castView(view, 2131624243, "field 'etConfirmPwd'");
    view = finder.findRequiredView(source, 2131624186, "field 'tvWarn'");
    target.tvWarn = finder.castView(view, 2131624186, "field 'tvWarn'");
    view = finder.findRequiredView(source, 2131624153, "field 'btnConfirm'");
    target.btnConfirm = finder.castView(view, 2131624153, "field 'btnConfirm'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvTitle = null;
    target.etNewPwd = null;
    target.etConfirmPwd = null;
    target.tvWarn = null;
    target.btnConfirm = null;
  }
}
