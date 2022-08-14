package cn.zeppin.product.utility;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;

import cn.zeppin.product.itic.backadmin.security.SecurityRealm;

public class RealmUtility {

	/**
	 * 清空权限缓存
	 */
	public static void clearAuth() {
		RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		SecurityRealm realm = (SecurityRealm) rsm.getRealms().iterator().next();
		realm.clearAuthz();
	}
}
