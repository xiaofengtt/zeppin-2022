package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.Role;

public interface RoleDao extends IDao<Role> {
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<Role> getListByParams(Map<String, Object> params);
}
