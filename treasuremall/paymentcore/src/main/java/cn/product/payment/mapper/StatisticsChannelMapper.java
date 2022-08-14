/**
 * 
 */
package cn.product.payment.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.product.payment.entity.StatisticsChannel;
import cn.product.payment.util.MyMapper;

public interface StatisticsChannelMapper extends MyMapper<StatisticsChannel> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<StatisticsChannel> getListByParams(Map<String, Object> params);
	
	void statisticsDaily(@Param("dailyDate")String dailyDate);
}
