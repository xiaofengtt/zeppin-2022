package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserRechargeAmountSet;
import cn.product.treasuremall.util.MyMapper;

public interface FrontUserRechargeAmountSetMapper extends MyMapper<FrontUserRechargeAmountSet> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserRechargeAmountSet> getListByParams(Map<String, Object> params);
}