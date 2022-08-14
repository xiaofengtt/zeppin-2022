package cn.product.payment.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.product.payment.entity.Method;
import cn.product.payment.entity.RoleMethod;
import cn.product.payment.util.MyMapper;

public interface RoleMethodMapper extends MyMapper<RoleMethod> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<RoleMethod> getListByParams(Map<String,Object> params);
    
	public void deleteByRole(@Param("role")String role);
	
	public List<Method> getMethodListByRole(@Param("role")String role);  
}