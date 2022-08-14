/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.api;

import java.util.List;

import cn.zeppin.product.itic.core.dao.base.IBaseDAO;
import cn.zeppin.product.itic.core.entity.TSsRole;

public interface ITSsRoleDAO extends IBaseDAO<TSsRole, String> {
	
	/**
	 * 获取角色列表
	 * @param resultClass
	 * @return
	 */
	public List<TSsRole> getList();
}
