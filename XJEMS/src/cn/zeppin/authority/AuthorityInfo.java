/**
 * This class is used for ...
 * 
 * @author suijing
 */
package cn.zeppin.authority;

/**
 * @author sj
 * 
 */
public class AuthorityInfo {

	int[] user;
	int[] userGroup;
	int[] denyUser;
	String errMsg;

	/**
	 * 
	 * @param user
	 * @param userGroup
	 * @param denyUser
	 */
	public AuthorityInfo(int[] user, int[] userGroup, int[] denyUser, String errMsg) {

		this.user = user;
		this.userGroup = userGroup;
		this.denyUser = denyUser;
		this.errMsg = errMsg;

	}
}
