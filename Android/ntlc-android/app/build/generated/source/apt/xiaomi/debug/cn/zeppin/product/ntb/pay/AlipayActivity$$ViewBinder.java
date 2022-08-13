// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.pay;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AlipayActivity$$ViewBinder<T extends cn.zeppin.product.ntb.pay.AlipayActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624099, "field 'button'");
    target.button = finder.castView(view, 2131624099, "field 'button'");
  }

  @Override public void unbind(T target) {
    target.button = null;
  }
}
