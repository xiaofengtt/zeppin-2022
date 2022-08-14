package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserPaidNumber;
import cn.product.worldmall.util.MyMapper;

public interface FrontUserPaidNumberMapper extends MyMapper<FrontUserPaidNumber> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserPaidNumber> getListByParams(Map<String, Object> params);
}