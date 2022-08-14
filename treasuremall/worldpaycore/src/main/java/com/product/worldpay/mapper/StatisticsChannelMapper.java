/**
 * 
 */
package com.product.worldpay.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.product.worldpay.entity.StatisticsChannel;
import com.product.worldpay.util.MyMapper;

public interface StatisticsChannelMapper extends MyMapper<StatisticsChannel> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<StatisticsChannel> getListByParams(Map<String, Object> params);
	
	void statisticsDaily(@Param("dailyDate")String dailyDate);
}
