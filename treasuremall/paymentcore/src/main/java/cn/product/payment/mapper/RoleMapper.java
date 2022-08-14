package cn.product.payment.mapper;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.Role;
import cn.product.payment.util.MyMapper;

public interface RoleMapper extends MyMapper<Role> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Role> getListByParams(Map<String, Object> params);
}