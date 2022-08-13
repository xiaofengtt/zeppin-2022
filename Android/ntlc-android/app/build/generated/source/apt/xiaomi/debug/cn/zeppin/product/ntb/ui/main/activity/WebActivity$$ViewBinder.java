// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.main.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WebActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.main.activity.WebActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131624263, "field 'webProgress'");
    target.webProgress = finder.castView(view, 2131624263, "field 'webProgress'");
    view = finder.findRequiredView(source, 2131624264, "field 'webView'");
    target.webView = finder.castView(view, 2131624264, "field 'webView'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvTitle = null;
    target.webProgress = null;
    target.webView = null;
  }
}
