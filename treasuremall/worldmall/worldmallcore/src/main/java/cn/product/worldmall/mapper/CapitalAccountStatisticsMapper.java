package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.CapitalAccountStatistics;
import cn.product.worldmall.util.MyMapper;

public interface CapitalAccountStatisticsMapper extends MyMapper<CapitalAccountStatistics> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<CapitalAccountStatistics> getListByParams(Map<String, Object> params);
	
	public void dailyRefrush();
}