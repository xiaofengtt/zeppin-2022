package com.zixueku.widget;

import android.support.v4.app.Fragment;

/**
 * 类说明
 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/1021/1813.html
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月7日 上午10:07:21
 */
public abstract class LazyFragment extends Fragment {
	protected boolean isVisible;

	/**
	 * 在这里实现Fragment数据的缓加载.
	 * 
	 * @param isVisibleToUser
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}
	}

	protected void onVisible() {
		lazyLoad();
	}

	protected abstract void lazyLoad();

	protected void onInvisible() {
	}
}