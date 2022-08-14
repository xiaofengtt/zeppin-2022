package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserBlacklist;
import cn.product.worldmall.util.MyMapper;

public interface FrontUserBlacklistMapper extends MyMapper<FrontUserBlacklist> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserBlacklist> getListByParams(Map<String, Object> params);
}