package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.Area;
import cn.product.worldmall.util.MyMapper;

public interface AreaMapper extends MyMapper<Area> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Area> getListByParams(Map<String, Object> params);
}