package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.CommonPaymentAmount;
import cn.product.worldmall.util.MyMapper;

public interface CommonPaymentAmountMapper extends MyMapper<CommonPaymentAmount> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<CommonPaymentAmount> getListByParams(Map<String, Object> params);
}