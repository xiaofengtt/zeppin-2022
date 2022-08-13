// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.main.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.main.activity.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624402, "field 'mRadioGroup'");
    target.mRadioGroup = finder.castView(view, 2131624402, "field 'mRadioGroup'");
    view = finder.findRequiredView(source, 2131624403, "field 'rbBank'");
    target.rbBank = finder.castView(view, 2131624403, "field 'rbBank'");
    view = finder.findRequiredView(source, 2131624404, "field 'rbFinance'");
    target.rbFinance = finder.castView(view, 2131624404, "field 'rbFinance'");
    view = finder.findRequiredView(source, 2131624405, "field 'rbUser'");
    target.rbUser = finder.castView(view, 2131624405, "field 'rbUser'");
    view = finder.findRequiredView(source, 2131624400, "field 'mainTab'");
    target.mainTab = finder.castView(view, 2131624400, "field 'mainTab'");
  }

  @Override public void unbind(T target) {
    target.mRadioGroup = null;
    target.rbBank = null;
    target.rbFinance = null;
    target.rbUser = null;
    target.mainTab = null;
  }
}
