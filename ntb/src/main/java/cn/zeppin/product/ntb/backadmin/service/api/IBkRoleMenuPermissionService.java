/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.backadmin.vo.MenuVO;
import cn.zeppin.product.ntb.core.entity.BkOperatorRole;
import cn.zeppin.product.ntb.core.entity.BkRoleMenuPermission;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */
public interface IBkRoleMenuPermissionService {
	
	/**
	 * 获取角色获取权限列表
	 * @param inputParams
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getListForPage(Map<String, String> inputParams, Class<? extends Entity> resultClass);
	
	/**
	 * 更新角色权限顺序
	 * @param type
	 * @param menus
	 * @return
	 */
	public void updateSort (BkRoleMenuPermission roleMenu, String type);
	
	/**
	 * 更新角色权限列表
	 * @param role
	 * @param menus
	 * @return
	 */
	public void updateAll (BkOperatorRole role, List<MenuVO> menuList);
	
	/**
	 * 获取角色获取权限
	 * @param uuid
	 * @return
	 */
	public BkRoleMenuPermission get(String uuid);

}
