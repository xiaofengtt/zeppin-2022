/**
 * This class is used for 访问许可列表
 * 
 * @author suijing
 * @version 1.0, 2014年7月23日 下午3:37:57
 */
package cn.zeppin.authority;

import java.util.HashMap;

/**
 * @author sj
 * 
 */
public class AccessList
{
	public static final HashMap<String, AuthorityInfo> ACCESS_INFOS = new HashMap<String, AuthorityInfo>()
	{
		/**
	 * 
	 */
		private static final long serialVersionUID = -202330548341045530L;

		{

			/**
			 * admin_gradeAction start
			 */
			put("main_gradeAction_add", new AuthorityInfo(null, UserGroup.MANAGER_ADD_EDIT, null, "无权添加学段！"));
			put("main_gradeAction_edit", new AuthorityInfo(null, UserGroup.MANAGER_ADD_EDIT, null, "无权编辑学段！"));
			put("main_gradeAction_delete", new AuthorityInfo(null, UserGroup.MANAGER_ADD_EDIT, null, "无权删除学段！"));

			/**
			 * 知识点接口操作权限
			 */
			put("main_knowledgeAction_Add", new AuthorityInfo(null, UserGroup.EDITOR_ADD_EDIT, null, "无权添加知识点！"));
			put("main_knowledgeAction_Update", new AuthorityInfo(null, UserGroup.EDITOR_ADD_EDIT, null, "无权修改知识点！"));
			put("main_knowledgeAction_Delete", new AuthorityInfo(null, UserGroup.EDITOR_ADD_EDIT, null, "无权删除知识点！"));
			put("main_knowledgeAction_List", new AuthorityInfo(null, UserGroup.EDITOR_ADD_EDIT, null, "无权管理知识点！"));
			
			/**
			 * 编辑用户接口操作权限
			 */
			put("main_editorAction_Add", new AuthorityInfo(null, UserGroup.MANAGER_ADD_EDIT, null, "无权添加编辑！"));
			put("main_editorAction_Update", new AuthorityInfo(null, UserGroup.MANAGER_ADD_EDIT, null, "无权修改编辑！"));
			put("main_editorAction_Delete", new AuthorityInfo(null, UserGroup.MANAGER_ADD_EDIT, null, "无权删除编辑！"));
			put("main_editorAction_List", new AuthorityInfo(null, UserGroup.MANAGER_ADD_EDIT, null, "无权管理编辑！"));
			put("main_editorAction_Load", new AuthorityInfo(null, UserGroup.SUPERADMIN_ONLY, null, "无权查看编辑！"));
			put("main_editorAction_Search", new AuthorityInfo(null, UserGroup.SUPERADMIN_ONLY, null, "无权搜索编辑！"));
			
			/**
			 * 运营管理者用户接口操作权限
			 */
			put("main_managerAction_Add", new AuthorityInfo(null, UserGroup.SUPERADMIN_ONLY, null, "无权添加运营管理者！"));
			put("main_managerAction_Update", new AuthorityInfo(null, UserGroup.SUPERADMIN_ONLY, null, "无权修改运营管理者！"));
			put("main_managerAction_Delete", new AuthorityInfo(null, UserGroup.SUPERADMIN_ONLY, null, "无权删除运营管理者！"));
			put("main_managerAction_List", new AuthorityInfo(null, UserGroup.SUPERADMIN_ONLY, null, "无权管理运营管理者！"));
			put("main_managerAction_Load", new AuthorityInfo(null, UserGroup.SUPERADMIN_ONLY, null, "无权查看运营管理者！"));
			put("main_managerAction_Search", new AuthorityInfo(null, UserGroup.SUPERADMIN_ONLY, null, "无权搜索运营管理者！"));

			/**
			 * 合作方用户接口操作权限
			 */
			put("main_parterAction_Add", new AuthorityInfo(null, UserGroup.EX_MANAGER_ADD_EDIT, null, "无权添加合作方用户！"));
			put("main_parterAction_Update", new AuthorityInfo(null, UserGroup.EX_MANAGER_ADD_EDIT, null, "无权修改合作方用户！"));
			put("main_parterAction_Delete", new AuthorityInfo(null, UserGroup.EX_MANAGER_ADD_EDIT, null, "无权删除合作方用户！"));
			put("main_parterAction_List", new AuthorityInfo(null, UserGroup.EX_MANAGER_ADD_EDIT, null, "无权管理合作方用户！"));
			put("main_parterAction_Load", new AuthorityInfo(null, UserGroup.EX_MANAGER_ADD_EDIT, null, "无权查看合作方用户！"));
			put("main_parterAction_Search", new AuthorityInfo(null, UserGroup.EX_MANAGER_ADD_EDIT, null, "无权搜索合作方用户！"));
			
			/**
			 * admin_resource start
			 */
			put("main_resourceAction_add", new AuthorityInfo(null, UserGroup.MANAGER_ADD_EDIT, null, "无权添加资源"));
			put("main_resourceAction_edit", new AuthorityInfo(null, UserGroup.MANAGER_ADD_EDIT, null, "无权编辑资源"));
			put("main_resourceAction_delete", new AuthorityInfo(null,UserGroup.MANAGER_ADD_EDIT, null, "无权删除资源"));
			/**
			 * admin_resource end
			 * 
			 */
			
			/**
			 * 分类接口
			 */
			put("main_categoryAction_Add", new AuthorityInfo(null, UserGroup.MANAGER_ADD_EDIT, null, "无权添加分类"));
			put("main_categoryAction_Edit", new AuthorityInfo(null, UserGroup.MANAGER_ADD_EDIT, null, "无权编辑分类"));
			put("main_categoryAction_Delete", new AuthorityInfo(null,UserGroup.MANAGER_ADD_EDIT, null, "无权删除分类"));
			
			/**
			 * 机构接口
			 */
			put("main_organizationAction_Add", new AuthorityInfo(null, UserGroup.MANAGER_ADD_EDIT, null, "无权添加机构"));
			put("main_organizationAction_Edit", new AuthorityInfo(null, UserGroup.MANAGER_ADD_EDIT, null, "无权编辑机构"));
			put("main_organizationAction_Delete", new AuthorityInfo(null,UserGroup.MANAGER_ADD_EDIT, null, "无权删除机构"));
			
			/**
			 * 学科接口
			 */
			put("main_subjectAction_Add", new AuthorityInfo(null, UserGroup.MANAGER_ADD_EDIT, null, "无权添加学科"));
			put("main_subjectAction_Edit", new AuthorityInfo(null, UserGroup.MANAGER_ADD_EDIT, null, "无权编辑学科"));
			put("main_subjectAction_Delete", new AuthorityInfo(null,UserGroup.MANAGER_ADD_EDIT, null, "无权删除学科"));
			
			/**
			 * 清空缓冲
			 */
			put("main_rolefuncationAction_ClearAllCache", new AuthorityInfo(null,UserGroup.SUPERADMIN_ONLY, null, "无权清除缓存"));
			

		}
	};

}
