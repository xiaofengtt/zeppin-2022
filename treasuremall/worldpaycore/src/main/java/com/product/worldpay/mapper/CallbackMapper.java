package com.product.worldpay.mapper;

import java.util.List;
import java.util.Map;

import com.product.worldpay.entity.Callback;
import com.product.worldpay.util.MyMapper;

public interface CallbackMapper extends MyMapper<Callback> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Callback> getListByParams(Map<String, Object> params);
}