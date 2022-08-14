package cn.product.payment.mapper;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.Menu;
import cn.product.payment.util.MyMapper;

public interface MenuMapper extends MyMapper<Menu> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<Menu> getListByParams(Map<String,Object> params);
    
    public List<Menu> getListByRole(Map<String,Object> params);
}