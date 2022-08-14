package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.ActivityInfoFirstchargePrize;
import cn.product.worldmall.util.MyMapper;

public interface ActivityInfoFirstchargePrizeMapper extends MyMapper<ActivityInfoFirstchargePrize> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<ActivityInfoFirstchargePrize> getListByParams(Map<String,Object> params);
}