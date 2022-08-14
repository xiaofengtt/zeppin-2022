/**
 * 
 */
package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.Bank;
import cn.product.score.util.MyMapper;

/**
 *
 */
public interface BankMapper extends MyMapper<Bank> {
	
	 /**
	  * 根据参数查询结果个数
	  * @return
	  */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 */
	public List<Bank> getListByParams(Map<String, Object> params);
}
