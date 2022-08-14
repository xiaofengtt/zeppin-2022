/**
 * 
 */
package cn.product.payment.mapper;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.Company;
import cn.product.payment.util.MyMapper;

/**
 *
 */
public interface CompanyMapper extends MyMapper<Company> {
	
	 /**
	  * 根据参数查询结果个数
	  * @return
	  */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 */
	public List<Company> getListByParams(Map<String, Object> params);
}
