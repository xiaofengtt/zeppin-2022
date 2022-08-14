/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;



import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsRoleDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TSsRole;


@Repository
public class TSsRoleDAO extends BaseDAO<TSsRole, String> implements ITSsRoleDAO {

	/**
	 * 获取角色列表
	 * @param resultClass
	 * @return
	 */
	public List<TSsRole> getList(){
		String hql = "from TSsRole";
		return super.getByHQL(hql.toString());
	}
	
	@Override
	public TSsRole insert(TSsRole role) {
		return super.insert(role);
	}
	
	@Override
	public TSsRole delete(TSsRole role) {
		return super.delete(role);
	}
	
	@Override
	public TSsRole update(TSsRole role) {
		return super.update(role);
	}
	
	@Override
	public TSsRole get(String id) {
		return super.get(id);
	}

}
