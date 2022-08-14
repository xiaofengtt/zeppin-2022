package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserScoreHistory;

public interface FrontUserScoreHistoryDao extends IDao<FrontUserScoreHistory>{
	
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
	public List<FrontUserScoreHistory> getListByParams(Map<String, Object> params);

	/**
	 * 根据订单查询流水
	 * @param orderId
	 * @return
	 */
	public FrontUserScoreHistory getByOrderId(String orderId); 
}
