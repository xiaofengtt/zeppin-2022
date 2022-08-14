package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserScoreHistory;
import cn.product.treasuremall.util.MyMapper;

public interface FrontUserScoreHistoryMapper extends MyMapper<FrontUserScoreHistory> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserScoreHistory> getListByParams(Map<String, Object> params);
	
}