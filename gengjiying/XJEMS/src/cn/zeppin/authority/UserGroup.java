/**
 * This class is used for ...
 * 
 * @author suijing
 */
package cn.zeppin.authority;

import cn.zeppin.utility.Dictionary;

/**
 * @author sj
 * 
 */
public class UserGroup
{
	/**
	 * 只有超级管理员才能访问
	 */
	public static final int[] SUPERADMIN_ONLY = { 
		Dictionary.USER_ROLE_SUPERADMIN };
	
	/**
	 * 能访问的角色
	 */
	public static final int[] EDITOR_ADD_EDIT = { 
		Dictionary.USER_ROLE_SUPERADMIN, 
		Dictionary.USER_ROLE_MANAGER};
	
}
