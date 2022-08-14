package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.Admin;

public interface AdminDao extends IDao<Admin> {
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
	public List<Admin> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据用户名获取管理员
	 * @param name
	 * @return
	 */
	public Admin getByUsername(String name);
}
