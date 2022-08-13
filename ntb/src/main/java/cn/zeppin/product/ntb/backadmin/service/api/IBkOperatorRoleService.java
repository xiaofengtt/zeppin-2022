/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.util.List;

import cn.zeppin.product.ntb.core.entity.BkOperatorRole;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * @author Clark.R 2016年9月28日
 *
 */
public interface IBkOperatorRoleService extends IBaseService<BkOperatorRole, String> {

	/**
	 * 通过名称获得角色对象
	 * @param name
	 * @return
	 */
	public BkOperatorRole getByName(String name);
	
	/**
	 * 获取角色列表
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getList(Class<? extends Entity> resultClass);
}
