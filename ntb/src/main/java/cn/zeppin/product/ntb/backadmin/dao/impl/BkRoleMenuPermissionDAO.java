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

import cn.zeppin.product.ntb.backadmin.dao.api.IBkRoleMenuPermissionDAO;
import cn.zeppin.product.ntb.core.dao.base.BaseDAO;
import cn.zeppin.product.ntb.core.entity.BkOperatorRole;
import cn.zeppin.product.ntb.core.entity.BkRoleMenuPermission;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 */

@Repository
public class BkRoleMenuPermissionDAO extends BaseDAO<BkRoleMenuPermission, String>
		implements IBkRoleMenuPermissionDAO {
	/**
	 * 获取角色获取权限列表
	 */
	@Override
	public List<Entity> getList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select rm.* ");
		builder.append(" from bk_role_menu_permission rm join bk_menu m on rm.menu=m.uuid ");
		builder.append(" where 1=1 ");
		//角色
		if (inputParams.get("role") != null && !"".equals(inputParams.get("role"))) {
			builder.append(" and rm.role='" + inputParams.get("role") + "' ");
		}
		//目录级别
		if (inputParams.get("level") != null && !"".equals(inputParams.get("level"))) {
			builder.append(" and m.level=" + inputParams.get("level") + " ");
		}
		//角色
		if (inputParams.get("pid") != null && !"".equals(inputParams.get("pid"))) {
			builder.append(" and m.pid='" + inputParams.get("pid") + "' ");
		}
		builder.append(" order by m.level, rm.sort");
		List<Entity> permissions = this.getBySQL(builder.toString(), resultClass);
		return permissions;
	}

	/**
	 * 获取角色获取权限列表
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select rm.uuid, rm.role, rm.menu, rm.sort ,m.title, m.level, m.pid, m.icon ");
		builder.append(" from bk_role_menu_permission rm join bk_menu m on rm.menu=m.uuid ");
		builder.append(" where 1=1 ");
		//角色
		if (inputParams.get("role") != null && !"".equals(inputParams.get("role"))) {
			builder.append(" and rm.role='" + inputParams.get("role") + "' ");
		}
		//目录级别
		if (inputParams.get("level") != null && !"".equals(inputParams.get("level"))) {
			builder.append(" and m.level=" + inputParams.get("level") + " ");
		}
		if (inputParams.get("menu") != null && !"".equals(inputParams.get("menu"))) {
			builder.append(" and m.uuid='" + inputParams.get("menu") + "' ");
		}
		builder.append(" order by m.level, rm.sort");
		List<Entity> permissions = this.getBySQL(builder.toString(), resultClass);
		return permissions;
	}
	
	/**
	 * 删除角色的所有页面权限
	 */
	@Override
	public void deleteByRole(BkOperatorRole role) {
		StringBuilder builder = new StringBuilder();
		builder.append(" delete from bk_role_menu_permission where role='").append(role.getUuid()).append("'");
		this.executeSQL(builder.toString());
	}
	
	/**
	 * 新增一条角色页面权限信息
	 */
	@Override
	@Caching(put = { @CachePut(value = "permissions", key = "#permission.uuid") }, evict = {
			@CacheEvict(value = "role_permissions", key = "#permission.role") })
	public BkRoleMenuPermission insert(BkRoleMenuPermission permission) {
		return super.insert(permission);
	}

	/**
	 * 删除一条角色页面权限信息
	 */
	@Override
	@Caching(evict = { @CacheEvict(value = "permissions", key = "#permission.uuid"),
			@CacheEvict(value = "role_permissions", key = "#permission.role") })
	public BkRoleMenuPermission delete(BkRoleMenuPermission permission) {
		return super.delete(permission);
	}

	/**
	 * 修改一条角色页面权限信息
	 */
	@Override
	@Caching(put = { @CachePut(value = "permissions", key = "#permission.uuid")}, evict = {
			@CacheEvict(value = "role_permissions", key = "#permission.role") })
	public BkRoleMenuPermission update(BkRoleMenuPermission permission) {
		return super.update(permission);
	}

	/**
	 * 通过唯一标识UUID得到一条页面权限信息
	 */
	@Override
	@Cacheable(cacheNames = "permissions", key = "#uuid")
	public BkRoleMenuPermission get(String uuid) {
		return super.get(uuid);
	}

}
