package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.Role;
import cn.product.worldmall.util.MyMapper;

public interface RoleMapper extends MyMapper<Role> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Role> getListByParams(Map<String, Object> params);
}