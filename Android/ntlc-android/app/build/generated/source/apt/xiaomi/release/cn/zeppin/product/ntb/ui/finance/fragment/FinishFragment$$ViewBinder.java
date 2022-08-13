// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.finance.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FinishFragment$$ViewBinder<T extends cn.zeppin.product.ntb.ui.finance.fragment.FinishFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624298, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131624298, "field 'recyclerView'");
  }

  @Override public void unbind(T target) {
    target.recyclerView = null;
  }
}
