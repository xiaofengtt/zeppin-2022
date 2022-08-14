/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.api;

import java.util.List;

import cn.zeppin.product.itic.core.entity.TSsRole;
import cn.zeppin.product.itic.core.service.base.IBaseService;

public interface ITSsRoleService extends IBaseService<TSsRole, String> {
	
	/**
	 * 获取角色列表
	 * @param resultClass
	 * @return
	 */
	public List<TSsRole> getList();
}
