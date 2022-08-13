package cn.zeppin.authority;

import java.util.HashMap;

public class AccessList
{
	public static final HashMap<String, AuthorityInfo> ACCESS_INFOS = new HashMap<String, AuthorityInfo>()
	{
		private static final long serialVersionUID = -202330548341045530L;

		{
			put("main_specialistAction_add", new AuthorityInfo(null, UserGroup.ADMIN, null, "无权添加专家！"));
			put("main_specialistAction_edit", new AuthorityInfo(null, UserGroup.ADMIN, null, "无权编辑专家！"));
			put("main_specialistAction_delete", new AuthorityInfo(null, UserGroup.ADMIN, null, "无权删除专家！"));
		}
		
		{
			put("main_userManageAction_add", new AuthorityInfo(null, UserGroup.ADMIN, null, "无权添加用户！"));
			put("main_userManageAction_edit", new AuthorityInfo(null, UserGroup.ADMIN, null, "无权编辑用户！"));
			put("main_userManageAction_delete", new AuthorityInfo(null, UserGroup.ADMIN, null, "无权删除用户！"));
		}
		
		{
			put("main_categoryAction_add", new AuthorityInfo(null, UserGroup.ADMIN, null, "无权添加分类！"));
			put("main_categoryAction_edit", new AuthorityInfo(null, UserGroup.ADMIN, null, "无权编辑分类！"));
			put("main_categoryAction_delete", new AuthorityInfo(null, UserGroup.ADMIN, null, "无权删除分类！"));
		}
	};

}
