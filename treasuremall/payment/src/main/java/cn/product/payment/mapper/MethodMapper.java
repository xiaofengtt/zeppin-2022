package cn.product.payment.mapper;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.Method;
import cn.product.payment.util.MyMapper;

public interface MethodMapper extends MyMapper<Method> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<Method> getListByParams(Map<String,Object> params);
    
    public List<Method> getListByAdmin(String admin);
}