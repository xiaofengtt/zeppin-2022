package cn.zeppin.product.score.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.Admin;

public interface AdminService extends IService<Admin>{
	
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
