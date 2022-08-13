// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TradingRecordDetailActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.activity.TradingRecordDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131624256, "field 'tvTradingType'");
    target.tvTradingType = finder.castView(view, 2131624256, "field 'tvTradingType'");
    view = finder.findRequiredView(source, 2131624257, "field 'tvTypeCN'");
    target.tvTypeCN = finder.castView(view, 2131624257, "field 'tvTypeCN'");
    view = finder.findRequiredView(source, 2131624175, "field 'tvPrice'");
    target.tvPrice = finder.castView(view, 2131624175, "field 'tvPrice'");
    view = finder.findRequiredView(source, 2131624258, "field 'tvOrderType'");
    target.tvOrderType = finder.castView(view, 2131624258, "field 'tvOrderType'");
    view = finder.findRequiredView(source, 2131624259, "field 'tvStatus'");
    target.tvStatus = finder.castView(view, 2131624259, "field 'tvStatus'");
    view = finder.findRequiredView(source, 2131624236, "field 'tvAccountBalance'");
    target.tvAccountBalance = finder.castView(view, 2131624236, "field 'tvAccountBalance'");
    view = finder.findRequiredView(source, 2131624117, "field 'tvType'");
    target.tvType = finder.castView(view, 2131624117, "field 'tvType'");
    view = finder.findRequiredView(source, 2131624260, "field 'tvOrderNum'");
    target.tvOrderNum = finder.castView(view, 2131624260, "field 'tvOrderNum'");
    view = finder.findRequiredView(source, 2131624261, "field 'tvCreateTime'");
    target.tvCreateTime = finder.castView(view, 2131624261, "field 'tvCreateTime'");
    view = finder.findRequiredView(source, 2131624142, "field 'tvRemark'");
    target.tvRemark = finder.castView(view, 2131624142, "field 'tvRemark'");
    view = finder.findRequiredView(source, 2131624155, "field 'llContent'");
    target.llContent = finder.castView(view, 2131624155, "field 'llContent'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvTitle = null;
    target.tvTradingType = null;
    target.tvTypeCN = null;
    target.tvPrice = null;
    target.tvOrderType = null;
    target.tvStatus = null;
    target.tvAccountBalance = null;
    target.tvType = null;
    target.tvOrderNum = null;
    target.tvCreateTime = null;
    target.tvRemark = null;
    target.llContent = null;
  }
}
