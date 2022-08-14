package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.zeppin.product.score.entity.RoleMethod;
import cn.zeppin.product.score.util.MyMapper;

public interface RoleMethodMapper extends MyMapper<RoleMethod> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<RoleMethod> getListByParams(Map<String,Object> params);
	
	/**
	 * 删除角色权限数据
	 */
	public void deleteByRole(@Param("role")String role);
}