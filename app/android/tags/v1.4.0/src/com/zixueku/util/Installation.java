package com.zixueku.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Installation {
	public synchronized static String getIdentity(Context context) {
	    SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
	    String identity = preference.getString("identity", null);
	    if (identity == null) {
	        identity = java.util.UUID.randomUUID().toString();
	        preference.edit().putString("identity", identity).commit();
	    }
	    return identity;
	}
}
