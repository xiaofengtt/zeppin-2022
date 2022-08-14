package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.Menu;
import cn.zeppin.product.score.util.MyMapper;

public interface MenuMapper extends MyMapper<Menu> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<Menu> getListByParams(Map<String,Object> params);
    
    public List<Menu> getListByRole(Map<String,Object> params);
}