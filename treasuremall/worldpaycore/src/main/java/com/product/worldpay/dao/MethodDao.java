package com.product.worldpay.dao;

import java.util.List;
import java.util.Map;

import com.product.worldpay.entity.Method;

public interface MethodDao extends IDao<Method> {
	
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
    public List<Method> getListByParams(Map<String, Object> params);
    
    /**
     * 初始化权限
     */
    public Map<String, String> loadFilterChainDefinitions();
}
