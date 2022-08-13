// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CertificationYesActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.activity.CertificationYesActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131624156, "field 'tvRealName'");
    target.tvRealName = finder.castView(view, 2131624156, "field 'tvRealName'");
    view = finder.findRequiredView(source, 2131624145, "field 'tvIdCard'");
    target.tvIdCard = finder.castView(view, 2131624145, "field 'tvIdCard'");
    view = finder.findRequiredView(source, 2131624158, "field 'tvCerStatus'");
    target.tvCerStatus = finder.castView(view, 2131624158, "field 'tvCerStatus'");
    view = finder.findRequiredView(source, 2131624157, "field 'llCer'");
    target.llCer = finder.castView(view, 2131624157, "field 'llCer'");
    view = finder.findRequiredView(source, 2131624155, "field 'llContent'");
    target.llContent = finder.castView(view, 2131624155, "field 'llContent'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvTitle = null;
    target.tvRealName = null;
    target.tvIdCard = null;
    target.tvCerStatus = null;
    target.llCer = null;
    target.llContent = null;
  }
}
