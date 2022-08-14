package com.product.worldpay.mapper;

import java.util.List;
import java.util.Map;

import com.product.worldpay.entity.Menu;
import com.product.worldpay.util.MyMapper;

public interface MenuMapper extends MyMapper<Menu> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<Menu> getListByParams(Map<String,Object> params);
    
    public List<Menu> getListByRole(Map<String,Object> params);
}