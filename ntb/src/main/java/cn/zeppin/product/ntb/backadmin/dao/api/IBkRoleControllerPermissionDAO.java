/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.api;


import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.BkOperatorRole;
import cn.zeppin.product.ntb.core.entity.BkRoleControllerPermission;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */
public interface IBkRoleControllerPermissionDAO extends IBaseDAO<BkRoleControllerPermission, String> {

	/**
	 * 删除角色权限数据
	 */
	public void deleteByRole(BkOperatorRole role);
	
	/**
	 * 通过角色获取权限
	 * @param role
	 * @return
	 */
	public List<BkRoleControllerPermission> getByRole(BkOperatorRole role);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	 List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);
	 
	 /**
	  * 根据参数查询结果个数
	  * @param inputParams
	  * @return
	  */
	Integer getCount(Map<String, String> inputParams);
	
}
