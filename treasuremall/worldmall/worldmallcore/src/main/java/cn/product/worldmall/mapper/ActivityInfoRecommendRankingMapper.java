package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.ActivityInfoRecommendRanking;
import cn.product.worldmall.util.MyMapper;

public interface ActivityInfoRecommendRankingMapper extends MyMapper<ActivityInfoRecommendRanking> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<ActivityInfoRecommendRanking> getListByParams(Map<String,Object> params);
}