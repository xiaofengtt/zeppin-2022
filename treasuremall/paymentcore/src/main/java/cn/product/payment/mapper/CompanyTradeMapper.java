/**
 * 
 */
package cn.product.payment.mapper;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.CompanyTrade;
import cn.product.payment.util.MyMapper;

/**
 *
 */
public interface CompanyTradeMapper extends MyMapper<CompanyTrade> {
	
	 /**
	  * 根据参数查询结果个数
	  * @return
	  */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 */
	public List<CompanyTrade> getListByParams(Map<String, Object> params);
}
