package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.Method;
import cn.product.treasuremall.util.MyMapper;

public interface MethodMapper extends MyMapper<Method> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<Method> getListByParams(Map<String,Object> params);
}