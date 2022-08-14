package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.ActivityInfoRechargePrize;
import cn.product.treasuremall.util.MyMapper;

public interface ActivityInfoRechargePrizeMapper extends MyMapper<ActivityInfoRechargePrize> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<ActivityInfoRechargePrize> getListByParams(Map<String,Object> params);
}