package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.InternationalInfoVersion;
import cn.product.worldmall.util.MyMapper;

public interface InternationalInfoVersionMapper extends MyMapper<InternationalInfoVersion> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<InternationalInfoVersion> getListByParams(Map<String, Object> params);
}