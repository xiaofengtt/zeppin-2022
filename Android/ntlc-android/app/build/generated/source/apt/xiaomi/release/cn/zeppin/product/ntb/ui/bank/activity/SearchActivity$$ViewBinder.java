// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.bank.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SearchActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.bank.activity.SearchActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131624247, "field 'cEtSearch'");
    target.cEtSearch = finder.castView(view, 2131624247, "field 'cEtSearch'");
    view = finder.findRequiredView(source, 2131624248, "field 'mTabLayout'");
    target.mTabLayout = finder.castView(view, 2131624248, "field 'mTabLayout'");
    view = finder.findRequiredView(source, 2131624250, "field 'mRecyclerView'");
    target.mRecyclerView = finder.castView(view, 2131624250, "field 'mRecyclerView'");
    view = finder.findRequiredView(source, 2131624249, "field 'mRefreshLayout'");
    target.mRefreshLayout = finder.castView(view, 2131624249, "field 'mRefreshLayout'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvTitle = null;
    target.cEtSearch = null;
    target.mTabLayout = null;
    target.mRecyclerView = null;
    target.mRefreshLayout = null;
  }
}
