package com.product.worldpay.mapper;

import java.util.List;
import java.util.Map;

import com.product.worldpay.entity.Admin;
import com.product.worldpay.util.MyMapper;

public interface AdminMapper extends MyMapper<Admin> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Admin> getListByParams(Map<String, Object> params);
}