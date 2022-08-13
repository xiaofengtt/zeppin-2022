package cn.zeppin.authority;

import cn.zeppin.utility.Dictionary;

public class UserGroup
{
	public static final int[] ADMIN = { 
		Dictionary.USER_ROLE_ADMIN };
	
	public static final int[] EDITOR = { 
		Dictionary.USER_ROLE_ADMIN,
		Dictionary.USER_ROLE_SPECIALIST };
	
	public static final int[] ALL = { 
		Dictionary.USER_ROLE_ADMIN,
		Dictionary.USER_ROLE_SPECIALIST,
		Dictionary.USER_ROLE_USER};
}
