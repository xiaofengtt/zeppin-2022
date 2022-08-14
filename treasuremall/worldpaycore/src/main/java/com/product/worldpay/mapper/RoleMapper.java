package com.product.worldpay.mapper;

import java.util.List;
import java.util.Map;

import com.product.worldpay.entity.Role;
import com.product.worldpay.util.MyMapper;

public interface RoleMapper extends MyMapper<Role> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Role> getListByParams(Map<String, Object> params);
}