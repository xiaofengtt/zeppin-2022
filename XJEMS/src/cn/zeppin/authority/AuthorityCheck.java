/**
 * This class is used for 判断用户是否可以使用方法
 * 
 * @author suijing
 */
package cn.zeppin.authority;

import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;

/**
 * @author sj
 * 
 */
public class AuthorityCheck {
	
	public static boolean CheckUser(AuthorityInfo authInfo, int userId, ActionResult actionResult) {
		boolean isAccess = false;
		actionResult.init(BaseAction.FAIL_STATUS, authInfo.errMsg, null);

		if (authInfo.denyUser.length > 0) {
			for (int i : authInfo.denyUser) {
				if (userId == i) {
					return false;
				}
			}
		}

		if (authInfo.userGroup.length > 0) {
			for (int i : authInfo.userGroup) {
				if (i == userId) {
					return true;
				}
			}
		}

		if (authInfo.user.length > 0) {
			for (int i : authInfo.user) {
				if (i == userId) {
					return true;
				}
			}
		}

		return isAccess;
	}

	/*
	 * public static boolean checkIsExist(String mString) { boolean isAccess =
	 * false; if (AccessList.ACCESS_INFOS.containsKey(mString)) { return true; }
	 * return isAccess; }
	 */
}
