package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.Version;
import cn.product.worldmall.util.MyMapper;

public interface VersionMapper extends MyMapper<Version> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Version> getListByParams(Map<String, Object> params);
}

