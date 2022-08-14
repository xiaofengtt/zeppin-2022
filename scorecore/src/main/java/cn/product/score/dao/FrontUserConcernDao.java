package cn.product.score.dao;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.FrontUserConcern;

public interface FrontUserConcernDao extends IDao<FrontUserConcern>{
	
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
	public List<FrontUserConcern> getListByParams(Map<String, Object> params);
}
