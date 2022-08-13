/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.BkControllerMethod;
import cn.zeppin.product.ntb.core.entity.BkOperatorRole;
import cn.zeppin.product.ntb.core.entity.BkRoleControllerPermission;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author elegantclack
 *
 */
public interface IBkRoleControllerPermissionService extends IBaseService<BkRoleControllerPermission, String> {

	/**
	 * 添加角色的控制器方法权限
	 * @param permission
	 * @return
	 */
	public BkRoleControllerPermission insert(BkRoleControllerPermission permission);

	/**
	 * 通过角色获取权限
	 * @param role
	 * @return
	 */
	public List<BkRoleControllerPermission> getByRole(BkOperatorRole role);
	
	/**
	 * 更新角色权限列表
	 * @param role
	 * @param methodList
	 * @return
	 */
	public void updateAll (BkOperatorRole role, List<BkControllerMethod> methodList);
	
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
