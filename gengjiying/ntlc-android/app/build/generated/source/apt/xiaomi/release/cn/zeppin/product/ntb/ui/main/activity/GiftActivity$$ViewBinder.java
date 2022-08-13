// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.main.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class GiftActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.main.activity.GiftActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624193, "field 'giftRecyclerView'");
    target.giftRecyclerView = finder.castView(view, 2131624193, "field 'giftRecyclerView'");
    view = finder.findRequiredView(source, 2131624194, "field 'ivBgGift'");
    target.ivBgGift = finder.castView(view, 2131624194, "field 'ivBgGift'");
    view = finder.findRequiredView(source, 2131624195, "field 'tvReceive' and method 'onClick'");
    target.tvReceive = finder.castView(view, 2131624195, "field 'tvReceive'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624196, "field 'tvShare' and method 'onClick'");
    target.tvShare = finder.castView(view, 2131624196, "field 'tvShare'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624197, "field 'tvCancel' and method 'onClick'");
    target.tvCancel = finder.castView(view, 2131624197, "field 'tvCancel'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.giftRecyclerView = null;
    target.ivBgGift = null;
    target.tvReceive = null;
    target.tvShare = null;
    target.tvCancel = null;
  }
}
