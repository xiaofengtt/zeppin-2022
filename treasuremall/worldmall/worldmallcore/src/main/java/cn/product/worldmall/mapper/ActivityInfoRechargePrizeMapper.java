package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.ActivityInfoRechargePrize;
import cn.product.worldmall.util.MyMapper;

public interface ActivityInfoRechargePrizeMapper extends MyMapper<ActivityInfoRechargePrize> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<ActivityInfoRechargePrize> getListByParams(Map<String,Object> params);
}