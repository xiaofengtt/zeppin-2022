// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MyBankDetailActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.activity.MyBankDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131624092, "field 'ivBankIcon'");
    target.ivBankIcon = finder.castView(view, 2131624092, "field 'ivBankIcon'");
    view = finder.findRequiredView(source, 2131624093, "field 'tvBankName'");
    target.tvBankName = finder.castView(view, 2131624093, "field 'tvBankName'");
    view = finder.findRequiredView(source, 2131624409, "field 'ivBankIconBig'");
    target.ivBankIconBig = finder.castView(view, 2131624409, "field 'ivBankIconBig'");
    view = finder.findRequiredView(source, 2131624089, "field 'tvBankcard'");
    target.tvBankcard = finder.castView(view, 2131624089, "field 'tvBankcard'");
    view = finder.findRequiredView(source, 2131624210, "field 'tvSingleLimit'");
    target.tvSingleLimit = finder.castView(view, 2131624210, "field 'tvSingleLimit'");
    view = finder.findRequiredView(source, 2131624211, "field 'tvDailyLimit'");
    target.tvDailyLimit = finder.castView(view, 2131624211, "field 'tvDailyLimit'");
    view = finder.findRequiredView(source, 2131624212, "field 'btnUnbind'");
    target.btnUnbind = finder.castView(view, 2131624212, "field 'btnUnbind'");
    view = finder.findRequiredView(source, 2131624346, "field 'cvBank'");
    target.cvBank = finder.castView(view, 2131624346, "field 'cvBank'");
    view = finder.findRequiredView(source, 2131624155, "field 'llContent'");
    target.llContent = finder.castView(view, 2131624155, "field 'llContent'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvTitle = null;
    target.ivBankIcon = null;
    target.tvBankName = null;
    target.ivBankIconBig = null;
    target.tvBankcard = null;
    target.tvSingleLimit = null;
    target.tvDailyLimit = null;
    target.btnUnbind = null;
    target.cvBank = null;
    target.llContent = null;
  }
}
