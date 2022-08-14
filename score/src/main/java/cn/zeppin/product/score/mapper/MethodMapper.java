package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.Method;
import cn.zeppin.product.score.util.MyMapper;

public interface MethodMapper extends MyMapper<Method> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<Method> getListByParams(Map<String,Object> params);
    
    public List<Method> getListByAdmin(String admin);
}