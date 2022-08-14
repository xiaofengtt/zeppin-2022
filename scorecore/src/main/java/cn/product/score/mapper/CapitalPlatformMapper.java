package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.CapitalPlatform;
import cn.product.score.util.MyMapper;

public interface CapitalPlatformMapper extends MyMapper<CapitalPlatform> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<CapitalPlatform> getListByParams(Map<String, Object> params);
}