package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.product.worldmall.entity.Method;
import cn.product.worldmall.entity.RoleMethod;
import cn.product.worldmall.util.MyMapper;

public interface RoleMethodMapper extends MyMapper<RoleMethod> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<RoleMethod> getListByParams(Map<String,Object> params);
	
	/**
	 * 删除角色权限数据
	 */
	public void deleteByRole(@Param("role")String role);
	
	public List<Method> getMethodListByRole(@Param("role")String role);  
}