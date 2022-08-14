package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserPaidNumber;
import cn.product.treasuremall.util.MyMapper;

public interface FrontUserPaidNumberMapper extends MyMapper<FrontUserPaidNumber> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserPaidNumber> getListByParams(Map<String, Object> params);
}