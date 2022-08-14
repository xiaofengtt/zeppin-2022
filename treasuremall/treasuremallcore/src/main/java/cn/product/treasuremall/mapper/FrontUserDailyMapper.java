package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserDaily;
import cn.product.treasuremall.util.MyMapper;

public interface FrontUserDailyMapper extends MyMapper<FrontUserDaily> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserDaily> getListByParams(Map<String, Object> params);
	
	public List<Map<String, Object>> getActiveListByParams(Map<String, Object> params);
}