package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.InternationalRate;

public interface InternationalRateDao extends IDao<InternationalRate> {
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
	public List<InternationalRate> getListByParams(Map<String, Object> params);
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	public List<InternationalRate> getCurrencyListByParams();
	
	/**
	 * 
	 * @param currencyCode
	 * @return
	 */
	public InternationalRate getByCurrency(String currencyCode);
	
	/**
	 * 
	 * @param currencyCode
	 * @return
	 */
	public void updateDaily(Map<String, Object> params);
	
}
