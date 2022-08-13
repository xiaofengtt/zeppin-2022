// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MessageActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.activity.MessageActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131624406, "field 'tvRight'");
    target.tvRight = finder.castView(view, 2131624406, "field 'tvRight'");
    view = finder.findRequiredView(source, 2131624206, "field 'messageRecyclerView'");
    target.messageRecyclerView = finder.castView(view, 2131624206, "field 'messageRecyclerView'");
    view = finder.findRequiredView(source, 2131624205, "field 'mRefreshLayout'");
    target.mRefreshLayout = finder.castView(view, 2131624205, "field 'mRefreshLayout'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvTitle = null;
    target.tvRight = null;
    target.messageRecyclerView = null;
    target.mRefreshLayout = null;
  }
}
