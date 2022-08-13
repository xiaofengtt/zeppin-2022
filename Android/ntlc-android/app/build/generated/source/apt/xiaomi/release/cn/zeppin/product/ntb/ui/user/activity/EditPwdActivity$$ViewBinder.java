// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class EditPwdActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.activity.EditPwdActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131624169, "field 'cetConfirmPwd'");
    target.cetConfirmPwd = finder.castView(view, 2131624169, "field 'cetConfirmPwd'");
    view = finder.findRequiredView(source, 2131624170, "field 'btnSave'");
    target.btnSave = finder.castView(view, 2131624170, "field 'btnSave'");
    view = finder.findRequiredView(source, 2131624167, "field 'cetOldPwd'");
    target.cetOldPwd = finder.castView(view, 2131624167, "field 'cetOldPwd'");
    view = finder.findRequiredView(source, 2131624168, "field 'cetNewPwd'");
    target.cetNewPwd = finder.castView(view, 2131624168, "field 'cetNewPwd'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvTitle = null;
    target.cetConfirmPwd = null;
    target.btnSave = null;
    target.cetOldPwd = null;
    target.cetNewPwd = null;
  }
}
