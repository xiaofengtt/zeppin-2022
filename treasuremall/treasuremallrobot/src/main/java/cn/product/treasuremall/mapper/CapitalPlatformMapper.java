package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.CapitalPlatform;
import cn.product.treasuremall.util.MyMapper;

public interface CapitalPlatformMapper extends MyMapper<CapitalPlatform> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<CapitalPlatform> getListByParams(Map<String, Object> params);
}