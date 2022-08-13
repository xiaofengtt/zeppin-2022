// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AddBankActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.activity.AddBankActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131624088, "field 'cetCardholder'");
    target.cetCardholder = finder.castView(view, 2131624088, "field 'cetCardholder'");
    view = finder.findRequiredView(source, 2131624090, "field 'cetBankcard'");
    target.cetBankcard = finder.castView(view, 2131624090, "field 'cetBankcard'");
    view = finder.findRequiredView(source, 2131624095, "field 'btnNext'");
    target.btnNext = finder.castView(view, 2131624095, "field 'btnNext'");
    view = finder.findRequiredView(source, 2131624092, "field 'ivBankIcon'");
    target.ivBankIcon = finder.castView(view, 2131624092, "field 'ivBankIcon'");
    view = finder.findRequiredView(source, 2131624093, "field 'tvBankName'");
    target.tvBankName = finder.castView(view, 2131624093, "field 'tvBankName'");
    view = finder.findRequiredView(source, 2131624094, "field 'cetPhone'");
    target.cetPhone = finder.castView(view, 2131624094, "field 'cetPhone'");
    view = finder.findRequiredView(source, 2131624091, "field 'llSelectBankCard'");
    target.llSelectBankCard = finder.castView(view, 2131624091, "field 'llSelectBankCard'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvTitle = null;
    target.cetCardholder = null;
    target.cetBankcard = null;
    target.btnNext = null;
    target.ivBankIcon = null;
    target.tvBankName = null;
    target.cetPhone = null;
    target.llSelectBankCard = null;
  }
}
