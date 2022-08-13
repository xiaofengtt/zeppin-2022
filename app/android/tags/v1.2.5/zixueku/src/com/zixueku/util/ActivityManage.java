package com.zixueku.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-9 下午1:52:46
 */
public class ActivityManage {

	private List<Activity> list = new ArrayList<Activity>();

	public void addActivity(Activity act) {
		list.add(act);
	}

	public void finishAll() {
		for (Activity act : list) {
			if (!act.isFinishing()) {
				act.finish();
			}
		}
		list = null;
	}
}
