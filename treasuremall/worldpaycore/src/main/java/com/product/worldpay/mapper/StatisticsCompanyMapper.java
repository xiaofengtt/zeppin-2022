/**
 * 
 */
package com.product.worldpay.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.product.worldpay.entity.StatisticsCompany;
import com.product.worldpay.util.MyMapper;

public interface StatisticsCompanyMapper extends MyMapper<StatisticsCompany> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<StatisticsCompany> getListByParams(Map<String, Object> params);
	
	public void statisticsDaily(@Param("dailyDate")String dailyDate);
	
	public List<Map<String, Object>> getDailyStatisticsByParams(Map<String, Object> params);
	
	public List<Map<String, Object>> getCompanyStatisticsByParams(Map<String, Object> params);
}
