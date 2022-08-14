package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserPaidNumber;

public interface FrontUserPaidNumberDao extends IDao<FrontUserPaidNumber>{
	
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
	public List<FrontUserPaidNumber> getListByParams(Map<String, Object> params);
	
}
