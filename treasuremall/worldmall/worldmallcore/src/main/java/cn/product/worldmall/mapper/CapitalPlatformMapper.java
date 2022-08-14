package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.CapitalPlatform;
import cn.product.worldmall.util.MyMapper;

public interface CapitalPlatformMapper extends MyMapper<CapitalPlatform> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<CapitalPlatform> getListByParams(Map<String, Object> params);
}