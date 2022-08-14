package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.ActivityInfoRecommendRanking;
import cn.product.treasuremall.util.MyMapper;

public interface ActivityInfoRecommendRankingMapper extends MyMapper<ActivityInfoRecommendRanking> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<ActivityInfoRecommendRanking> getListByParams(Map<String,Object> params);
}