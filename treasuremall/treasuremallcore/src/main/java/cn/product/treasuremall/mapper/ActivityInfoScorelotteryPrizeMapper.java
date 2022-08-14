package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.ActivityInfoScorelotteryPrize;
import cn.product.treasuremall.util.MyMapper;

public interface ActivityInfoScorelotteryPrizeMapper extends MyMapper<ActivityInfoScorelotteryPrize> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<ActivityInfoScorelotteryPrize> getListByParams(Map<String,Object> params);
}