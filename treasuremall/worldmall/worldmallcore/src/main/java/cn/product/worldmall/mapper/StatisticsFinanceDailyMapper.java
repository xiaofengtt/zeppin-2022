/**
 * 
 */
package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.StatisticsFinanceDaily;
import cn.product.worldmall.util.MyMapper;

/**
 *
 */
public interface StatisticsFinanceDailyMapper extends MyMapper<StatisticsFinanceDaily> {
	
	 /**
	  * 根据参数查询结果个数
	  * @return
	  */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 */
	public List<StatisticsFinanceDaily> getListByParams(Map<String, Object> params);
}
