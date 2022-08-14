package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.Area;
import cn.product.treasuremall.util.MyMapper;

public interface AreaMapper extends MyMapper<Area> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Area> getListByParams(Map<String, Object> params);
}