/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.api;


import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.BkOperatorRole;
import cn.zeppin.product.ntb.core.entity.BkRoleMenuPermission;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */
public interface IBkRoleMenuPermissionDAO extends IBaseDAO<BkRoleMenuPermission, String> {
	/**
	 * 获取角色获取权限列表
	 * @param searchMap
	 * @return
	 */
	List<Entity> getList(Map<String, String> inputParams, Class<? extends Entity> resultClass);
	
	/**
	 * 获取角色获取权限列表
	 * @param searchMap
	 * @return
	 */
	List<Entity> getListForPage(Map<String, String> inputParams, Class<? extends Entity> resultClass);
	
	/**
	 * 删除角色的所有权限
	 * @param role
	 * @return 
	 * @return
	 */
	void deleteByRole(BkOperatorRole role);
	
}
