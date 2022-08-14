package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.ActivityInfoBuyfreeSharesnum;
import cn.product.worldmall.util.MyMapper;

public interface ActivityInfoBuyfreeSharesnumMapper extends MyMapper<ActivityInfoBuyfreeSharesnum> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<ActivityInfoBuyfreeSharesnum> getListByParams(Map<String,Object> params);
}