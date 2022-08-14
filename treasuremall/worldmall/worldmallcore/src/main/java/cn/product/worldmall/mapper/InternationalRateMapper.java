package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.InternationalRate;
import cn.product.worldmall.util.MyMapper;

public interface InternationalRateMapper extends MyMapper<InternationalRate> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<InternationalRate> getListByParams(Map<String, Object> params);
}