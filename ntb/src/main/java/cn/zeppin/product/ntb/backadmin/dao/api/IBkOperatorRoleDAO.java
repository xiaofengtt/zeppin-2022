/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.api;

import java.util.List;

import cn.zeppin.product.ntb.core.dao.base.IBaseDAO;
import cn.zeppin.product.ntb.core.entity.BkOperatorRole;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author Clark.R 2016年9月28日
 *
 */
public interface IBkOperatorRoleDAO extends IBaseDAO<BkOperatorRole, String> {

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
