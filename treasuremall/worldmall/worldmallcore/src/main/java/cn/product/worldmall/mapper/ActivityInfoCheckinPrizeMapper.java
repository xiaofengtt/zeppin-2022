package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.ActivityInfoCheckinPrize;
import cn.product.worldmall.util.MyMapper;

public interface ActivityInfoCheckinPrizeMapper extends MyMapper<ActivityInfoCheckinPrize> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<ActivityInfoCheckinPrize> getListByParams(Map<String,Object> params);
}