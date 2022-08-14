package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.Menu;
import cn.product.score.util.MyMapper;

public interface MenuMapper extends MyMapper<Menu> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<Menu> getListByParams(Map<String,Object> params);
    
    public List<Menu> getListByRole(Map<String,Object> params);
}