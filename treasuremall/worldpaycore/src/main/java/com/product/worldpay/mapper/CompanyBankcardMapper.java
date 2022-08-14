/**
 * 
 */
package com.product.worldpay.mapper;

import java.util.List;
import java.util.Map;

import com.product.worldpay.entity.CompanyBankcard;
import com.product.worldpay.util.MyMapper;

/**
 *
 */
public interface CompanyBankcardMapper extends MyMapper<CompanyBankcard> {
	
	 /**
	  * 根据参数查询结果个数
	  * @return
	  */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数查询结果列表(带分页、排序)
	 */
	public List<CompanyBankcard> getListByParams(Map<String, Object> params);
}
