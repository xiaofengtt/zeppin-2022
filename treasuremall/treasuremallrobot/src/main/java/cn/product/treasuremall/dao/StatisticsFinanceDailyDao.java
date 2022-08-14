/**
 * 
 */
package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.StatisticsFinanceDaily;

/**
 *
 */
public interface StatisticsFinanceDailyDao extends IDao<StatisticsFinanceDaily> {
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @return
	 */
	public List<StatisticsFinanceDaily> getListByParams(Map<String, Object> params);

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 批量更新
	 * @param listUpdate
	 * @param listInsert
	 */
	public void insert(List<StatisticsFinanceDaily> listUpdate, List<StatisticsFinanceDaily> listInsert);

}
