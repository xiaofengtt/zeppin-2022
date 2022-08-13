// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.finance.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SendCodeDialogActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.finance.activity.SendCodeDialogActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131624184, "field 'etCode'");
    target.etCode = finder.castView(view, 2131624184, "field 'etCode'");
    view = finder.findRequiredView(source, 2131624185, "field 'tvGetCode'");
    target.tvGetCode = finder.castView(view, 2131624185, "field 'tvGetCode'");
    view = finder.findRequiredView(source, 2131624278, "field 'btnCancel'");
    target.btnCancel = finder.castView(view, 2131624278, "field 'btnCancel'");
    view = finder.findRequiredView(source, 2131624153, "field 'btnConfirm'");
    target.btnConfirm = finder.castView(view, 2131624153, "field 'btnConfirm'");
  }

  @Override public void unbind(T target) {
    target.tvTitle = null;
    target.etCode = null;
    target.tvGetCode = null;
    target.btnCancel = null;
    target.btnConfirm = null;
  }
}
