package cn.product.worldmall.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserRechargeOrder;
import cn.product.worldmall.util.MyMapper;

public interface FrontUserRechargeOrderMapper extends MyMapper<FrontUserRechargeOrder> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserRechargeOrder> getListByParams(Map<String, Object> params);
	
	public List<Map<String, Object>> getFirstListByParams(Map<String, Object> params);

	public BigDecimal getAmountByParams(Map<String, Object> params);
}