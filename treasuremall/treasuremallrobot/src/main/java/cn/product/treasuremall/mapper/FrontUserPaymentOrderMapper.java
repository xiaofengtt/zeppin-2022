package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserPaymentOrder;
import cn.product.treasuremall.util.MyMapper;

public interface FrontUserPaymentOrderMapper extends MyMapper<FrontUserPaymentOrder> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserPaymentOrder> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getGroupCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<FrontUserPaymentOrder> getGroupListByParams(Map<String, Object> params);
	
	public Integer getLeftCountByParams(Map<String, Object> params);
	
	public List<FrontUserPaymentOrder> getLeftListByParams(Map<String, Object> params);
	
	public List<Map<String, Object>> getPaymentStatistics(Map<String, Object> params);
	
	public Integer getPaymentStatisticsCount(Map<String, Object> params);
}