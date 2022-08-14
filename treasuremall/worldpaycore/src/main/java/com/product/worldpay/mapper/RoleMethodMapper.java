package com.product.worldpay.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.product.worldpay.entity.Method;
import com.product.worldpay.entity.RoleMethod;
import com.product.worldpay.util.MyMapper;

public interface RoleMethodMapper extends MyMapper<RoleMethod> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<RoleMethod> getListByParams(Map<String,Object> params);
    
	public void deleteByRole(@Param("role")String role);
	
	public List<Method> getMethodListByRole(@Param("role")String role);  
}