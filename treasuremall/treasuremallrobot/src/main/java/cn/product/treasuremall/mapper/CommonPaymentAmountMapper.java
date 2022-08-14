package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.CommonPaymentAmount;
import cn.product.treasuremall.util.MyMapper;

public interface CommonPaymentAmountMapper extends MyMapper<CommonPaymentAmount> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<CommonPaymentAmount> getListByParams(Map<String, Object> params);
}