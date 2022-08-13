// Generated code from Butter Knife. Do not modify!
package cn.zeppin.product.ntb.ui.main.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AdvertisementActivity$$ViewBinder<T extends cn.zeppin.product.ntb.ui.main.activity.AdvertisementActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624097, "field 'ivAdvertisement'");
    target.ivAdvertisement = finder.castView(view, 2131624097, "field 'ivAdvertisement'");
    view = finder.findRequiredView(source, 2131624098, "field 'tvTimer'");
    target.tvTimer = finder.castView(view, 2131624098, "field 'tvTimer'");
    view = finder.findRequiredView(source, 2131624096, "field 'flAdvertisement'");
    target.flAdvertisement = finder.castView(view, 2131624096, "field 'flAdvertisement'");
  }

  @Override public void unbind(T target) {
    target.ivAdvertisement = null;
    target.tvTimer = null;
    target.flAdvertisement = null;
  }
}
