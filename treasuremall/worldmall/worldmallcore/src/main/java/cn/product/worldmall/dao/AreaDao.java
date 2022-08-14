package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.Area;

public interface AreaDao extends IDao<Area> {
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
	public List<Area> getListByParams(Map<String, Object> params);
	
	/**
	 * 递归获取各级地区名称
	 * @param uuid
	 * @return
	 */
	public List<String> getFullName(String uuid);
	
	/**
	 * 根据code获取信息
	 * @param scode
	 * @return
	 */
	public Area getByScode(String scode);
}
