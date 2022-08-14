package cn.product.score.dao;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.InfoMatch;

public interface InfoMatchDao extends IDao<InfoMatch>{
	
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
	public List<InfoMatch> getListByParams(Map<String, Object> params);
}
