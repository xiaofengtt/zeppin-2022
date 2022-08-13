/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.ntb.backadmin.dao.api.IBkRoleControllerPermissionDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.BkOperatorRole;
import cn.zeppin.product.ntb.core.entity.BkRoleControllerPermission;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 */

@Repository
public class BkRoleControllerPermissionDAO extends BaseDAO<BkRoleControllerPermission, String>
		implements IBkRoleControllerPermissionDAO {

	/**
	 * 删除角色的所有权限
	 */
	@Override
	@Caching(evict = { @CacheEvict(value = "permissions", allEntries = true),
			@CacheEvict(value = "role_permissions", allEntries = true) })
	public void deleteByRole(BkOperatorRole role) {
		StringBuilder builder = new StringBuilder();
		builder.append(" delete from bk_role_controller_permission where role='").append(role.getUuid()).append("'");
		this.executeSQL(builder.toString());
	}

	/**
	 * 通过角色获取权限
	 */
	@Override
//	@Cacheable(cacheNames = "role_permissions", key = "#role.uuid")
	public List<BkRoleControllerPermission> getByRole(BkOperatorRole role) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" from BkRoleControllerPermission where role=?0");
		Object[] paras = { role.getUuid() };
		List<BkRoleControllerPermission> permissions = this.getByHQL(buffer.toString(), paras);
		return permissions;
	}
	
	/**
	 * 新增一条角色method权限信息
	 */
	@Override
	@Caching(put = { @CachePut(value = "permissions", key = "#permission.uuid") }, evict = {
			@CacheEvict(value = "role_permissions", key = "#permission.role") })
	public BkRoleControllerPermission insert(BkRoleControllerPermission permission) {
		return super.insert(permission);
	}

	/**
	 * 删除一条角色method权限信息
	 */
	@Override
	@Caching(evict = { @CacheEvict(value = "permissions", key = "#permission.uuid"),
			@CacheEvict(value = "role_permissions", key = "#permission.role") })
	public BkRoleControllerPermission delete(BkRoleControllerPermission permission) {
		return super.delete(permission);
	}

	/**
	 * 修改一条角色method权限信息
	 */
	@Override
	@Caching(put = { @CachePut(value = "permissions", key = "#permission.uuid")}, evict = {
			@CacheEvict(value = "role_permissions", key = "#permission.role") })
	public BkRoleControllerPermission update(BkRoleControllerPermission permission) {
		return super.update(permission);
	}

	/**
	 * 通过唯一标识UUID得到一条method权限信息
	 */
	@Override
	@Cacheable(cacheNames = "permissions", key = "#uuid")
	public BkRoleControllerPermission get(String uuid) {
		return super.get(uuid);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select rm.uuid, rm.role, rm.method ,m.name, m.controller ");
		builder.append(" from bk_role_controller_permission rm join bk_controller_method m on rm.method=m.uuid ");
		builder.append(" where 1=1 ");
		//角色
		if (inputParams.get("role") != null && !"".equals(inputParams.get("role"))) {
			builder.append(" and rm.role='" + inputParams.get("role") + "' ");
		}
		if (inputParams.get("controller") != null && !"".equals(inputParams.get("controller"))) {
			builder.append(" and rm.controller='" + inputParams.get("controller") + "' ");
		}
		if (inputParams.get("method") != null && !"".equals(inputParams.get("method"))) {
			builder.append(" and m.uuid='" + inputParams.get("method") + "' ");
		}
		builder.append(" order by rm.controller,rm.method");
		List<Entity> permissions = this.getBySQL(builder.toString(), resultClass);
		return permissions;
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(*) ");
		builder.append(" from bk_role_controller_permission rm join bk_controller_method m on rm.method=m.uuid ");
		builder.append(" where 1=1 ");
		//角色
		if (inputParams.get("role") != null && !"".equals(inputParams.get("role"))) {
			builder.append(" and rm.role='" + inputParams.get("role") + "' ");
		}
		if (inputParams.get("controller") != null && !"".equals(inputParams.get("controller"))) {
			builder.append(" and rm.controller='" + inputParams.get("controller") + "' ");
		}
		if (inputParams.get("method") != null && !"".equals(inputParams.get("method"))) {
			builder.append(" and m.uuid='" + inputParams.get("method") + "' ");
		}
		return Integer.valueOf(super.getResultBySQL(builder.toString()).toString());
	}

}
