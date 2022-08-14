package com.product.worldpay.mapper;

import java.util.List;
import java.util.Map;

import com.product.worldpay.entity.Method;
import com.product.worldpay.util.MyMapper;

public interface MethodMapper extends MyMapper<Method> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<Method> getListByParams(Map<String,Object> params);
}