package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.CapitalAccountStatistics;
import cn.product.treasuremall.util.MyMapper;

public interface CapitalAccountStatisticsMapper extends MyMapper<CapitalAccountStatistics> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<CapitalAccountStatistics> getListByParams(Map<String, Object> params);
	
	public void dailyRefrush();
}