package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.ActivityInfoFirstchargePrize;
import cn.product.treasuremall.util.MyMapper;

public interface ActivityInfoFirstchargePrizeMapper extends MyMapper<ActivityInfoFirstchargePrize> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<ActivityInfoFirstchargePrize> getListByParams(Map<String,Object> params);
}