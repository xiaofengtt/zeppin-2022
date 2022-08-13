/**
 * This class is used for ...
 * 
 * @author suijing
 * @version 1.0, 2014年7月23日 下午3:12:46
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
	 * 运营管理者以上才能访问
	 */
	public static final int[] MANAGER_ADD_EDIT = { 
		Dictionary.USER_ROLE_SUPERADMIN, 
		Dictionary.USER_ROLE_MANAGER };
	
	/**
	 * 编辑以上才能访问
	 */
	public static final int[] EDITOR_ADD_EDIT = { 
		Dictionary.USER_ROLE_SUPERADMIN, 
		Dictionary.USER_ROLE_MANAGER, 
		Dictionary.USER_ROLE_EDITOR };
	
	/**
	 * 合作单位负责人以上才能访问
	 */
	public static final int[] EX_MANAGER_ADD_EDIT = { 
		Dictionary.USER_ROLE_SUPERADMIN, 
		Dictionary.USER_ROLE_MANAGER, 
		Dictionary.USER_ROLE_EDITOR, 
		Dictionary.USER_ROLE_EX_MANAGER};
	
	public static final int[] ALL = { 
		Dictionary.USER_ROLE_SUPERADMIN, 
		Dictionary.USER_ROLE_MANAGER, 
		Dictionary.USER_ROLE_EDITOR, 
		Dictionary.USER_ROLE_EX_MANAGER,
		Dictionary.USER_ROLE_EX_EDITOR};
}
