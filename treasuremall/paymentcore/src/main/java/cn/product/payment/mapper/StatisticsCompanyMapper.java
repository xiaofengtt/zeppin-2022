/**
 * 
 */
package cn.product.payment.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.product.payment.entity.StatisticsCompany;
import cn.product.payment.util.MyMapper;

public interface StatisticsCompanyMapper extends MyMapper<StatisticsCompany> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<StatisticsCompany> getListByParams(Map<String, Object> params);
	
	public void statisticsDaily(@Param("dailyDate")String dailyDate);
	
	public List<Map<String, Object>> getDailyStatisticsByParams(Map<String, Object> params);
	
	public List<Map<String, Object>> getCompanyStatisticsByParams(Map<String, Object> params);
}
