// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AlipayBindActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.user.activity.AlipayBindActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131624225, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131624207, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131624101, "field 'ivBindBg'");
    target.ivBindBg = finder.castView(view, 2131624101, "field 'ivBindBg'");
    view = finder.findRequiredView(source, 2131624102, "field 'tvBindDes'");
    target.tvBindDes = finder.castView(view, 2131624102, "field 'tvBindDes'");
    view = finder.findRequiredView(source, 2131624103, "field 'tvAlipayNickName'");
    target.tvAlipayNickName = finder.castView(view, 2131624103, "field 'tvAlipayNickName'");
    view = finder.findRequiredView(source, 2131624105, "field 'ivAlipayHead'");
    target.ivAlipayHead = finder.castView(view, 2131624105, "field 'ivAlipayHead'");
    view = finder.findRequiredView(source, 2131624104, "field 'flAlipayHeadBg'");
    target.flAlipayHeadBg = finder.castView(view, 2131624104, "field 'flAlipayHeadBg'");
    view = finder.findRequiredView(source, 2131624100, "field 'llTopBg'");
    target.llTopBg = finder.castView(view, 2131624100, "field 'llTopBg'");
    view = finder.findRequiredView(source, 2131624106, "field 'btnBind'");
    target.btnBind = finder.castView(view, 2131624106, "field 'btnBind'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvTitle = null;
    target.ivBindBg = null;
    target.tvBindDes = null;
    target.tvAlipayNickName = null;
    target.ivAlipayHead = null;
    target.flAlipayHeadBg = null;
    target.llTopBg = null;
    target.btnBind = null;
  }
}
