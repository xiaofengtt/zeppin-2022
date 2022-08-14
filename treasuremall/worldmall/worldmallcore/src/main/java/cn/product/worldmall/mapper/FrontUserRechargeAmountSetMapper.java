package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserRechargeAmountSet;
import cn.product.worldmall.util.MyMapper;

public interface FrontUserRechargeAmountSetMapper extends MyMapper<FrontUserRechargeAmountSet> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserRechargeAmountSet> getListByParams(Map<String, Object> params);
}