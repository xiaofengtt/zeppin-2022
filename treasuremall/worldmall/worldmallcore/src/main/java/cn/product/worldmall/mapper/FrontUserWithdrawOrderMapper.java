package cn.product.worldmall.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserWithdrawOrder;
import cn.product.worldmall.util.MyMapper;

public interface FrontUserWithdrawOrderMapper extends MyMapper<FrontUserWithdrawOrder> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserWithdrawOrder> getListByParams(Map<String, Object> params);

	public BigDecimal getAmountByParams(Map<String, Object> params);
}