package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.Role;
import cn.product.treasuremall.util.MyMapper;

public interface RoleMapper extends MyMapper<Role> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Role> getListByParams(Map<String, Object> params);
}