// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisterActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.activity.RegisterActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624183, "field 'etPhone'");
    target.etPhone = finder.castView(view, 2131624183, "field 'etPhone'");
    view = finder.findRequiredView(source, 2131624184, "field 'etCode'");
    target.etCode = finder.castView(view, 2131624184, "field 'etCode'");
    view = finder.findRequiredView(source, 2131624185, "field 'tvGetCode'");
    target.tvGetCode = finder.castView(view, 2131624185, "field 'tvGetCode'");
    view = finder.findRequiredView(source, 2131624239, "field 'etPassword'");
    target.etPassword = finder.castView(view, 2131624239, "field 'etPassword'");
    view = finder.findRequiredView(source, 2131624240, "field 'btnRegister'");
    target.btnRegister = finder.castView(view, 2131624240, "field 'btnRegister'");
    view = finder.findRequiredView(source, 2131624186, "field 'tvWarn'");
    target.tvWarn = finder.castView(view, 2131624186, "field 'tvWarn'");
    view = finder.findRequiredView(source, 2131624241, "field 'cbPurAgreement'");
    target.cbPurAgreement = finder.castView(view, 2131624241, "field 'cbPurAgreement'");
    view = finder.findRequiredView(source, 2131624238, "field 'llRegisterView'");
    target.llRegisterView = finder.castView(view, 2131624238, "field 'llRegisterView'");
    view = finder.findRequiredView(source, 2131624224, "field 'tvAgreement'");
    target.tvAgreement = finder.castView(view, 2131624224, "field 'tvAgreement'");
  }

  @Override public void unbind(T target) {
    target.etPhone = null;
    target.etCode = null;
    target.tvGetCode = null;
    target.etPassword = null;
    target.btnRegister = null;
    target.tvWarn = null;
    target.cbPurAgreement = null;
    target.llRegisterView = null;
    target.tvAgreement = null;
  }
}
