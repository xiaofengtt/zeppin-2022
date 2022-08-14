package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.Role;
import cn.product.score.util.MyMapper;

public interface RoleMapper extends MyMapper<Role> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Role> getListByParams(Map<String, Object> params);
}