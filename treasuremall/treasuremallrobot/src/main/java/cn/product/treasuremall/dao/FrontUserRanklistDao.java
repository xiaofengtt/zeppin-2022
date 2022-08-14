package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserRanklist;

public interface FrontUserRanklistDao extends IDao<FrontUserRanklist>{
	
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
	public List<FrontUserRanklist> getListByParams(Map<String, Object> params);
	
	/**
	 * 清空表数据
	 */
	public void truncate();
	/**
	 * 更新表数据
	 */
	public void insertInfoList();
}
