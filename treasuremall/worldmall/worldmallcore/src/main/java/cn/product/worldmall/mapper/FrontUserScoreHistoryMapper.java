package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserScoreHistory;
import cn.product.worldmall.util.MyMapper;

public interface FrontUserScoreHistoryMapper extends MyMapper<FrontUserScoreHistory> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserScoreHistory> getListByParams(Map<String, Object> params);
	
}