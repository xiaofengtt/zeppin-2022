package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.AdminRole;
import cn.product.score.util.MyMapper;

public interface AdminRoleMapper extends MyMapper<AdminRole> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<AdminRole> getListByParams(Map<String, Object> params);
	
    public List<String> getListByRole(String role);
}