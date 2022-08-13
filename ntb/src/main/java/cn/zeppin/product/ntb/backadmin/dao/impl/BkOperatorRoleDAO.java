/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;



import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.IBkOperatorRoleDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.BkOperatorRole;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author Clark.R 2016年9月28日
 *
 */

@Repository
public class BkOperatorRoleDAO extends BaseDAO<BkOperatorRole, String> implements IBkOperatorRoleDAO {

	/**
	 * 通过名称获取唯一角色信息
	 */
	@Override
	public BkOperatorRole getByName(String name) {
		String sql = "select uuid, name from bk_operator_role where name=?0";
		Object[] paras = {name};
		return (BkOperatorRole) super.getResultBySQL(sql, paras, BkOperatorRole.class);
	}

	/**
	 * 获取角色列表
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getList(Class<? extends Entity> resultClass){
		String sql = "select * from bk_operator_role order by weight";
		return super.getBySQL(sql.toString(), resultClass);
	}
	
	/**
	 * 增加一条后台操作员角色信息
	 */
	@Override
	@CachePut(value = "roles", key = "#role.uuid")
	public BkOperatorRole insert(BkOperatorRole role) {
		return super.insert(role);
	}
	
	/**
	 * 删除一条后台操作员角色信息
	 */
	@Override
	@CacheEvict(value = "roles", key = "#role.uuid")
	public BkOperatorRole delete(BkOperatorRole role) {
		return super.delete(role);
	}
	
	/**
	 * 修改一条后台操作员角色信息
	 */
	@Override
	@CachePut(value = "roles", key = "#role.uuid")
	public BkOperatorRole update(BkOperatorRole role) {
		return super.update(role);
	}
	
	/**
	 * 通过唯一标识UUID找到操作员角色信息
	 */
	@Override
	@Cacheable(cacheNames = "roles", key = "#uuid")
	public BkOperatorRole get(String uuid) {
		return super.get(uuid);
	}

}
