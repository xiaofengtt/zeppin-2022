package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.ActivityInfoScorelotteryPrize;
import cn.product.worldmall.util.MyMapper;

public interface ActivityInfoScorelotteryPrizeMapper extends MyMapper<ActivityInfoScorelotteryPrize> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<ActivityInfoScorelotteryPrize> getListByParams(Map<String,Object> params);
}