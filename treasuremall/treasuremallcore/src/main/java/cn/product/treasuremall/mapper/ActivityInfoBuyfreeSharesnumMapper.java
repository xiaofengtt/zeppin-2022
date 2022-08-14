package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.ActivityInfoBuyfreeSharesnum;
import cn.product.treasuremall.util.MyMapper;

public interface ActivityInfoBuyfreeSharesnumMapper extends MyMapper<ActivityInfoBuyfreeSharesnum> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<ActivityInfoBuyfreeSharesnum> getListByParams(Map<String,Object> params);
}