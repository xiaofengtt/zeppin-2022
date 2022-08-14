package com.product.worldpay.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.product.worldpay.entity.RoleMenu;
import com.product.worldpay.util.MyMapper;
import com.product.worldpay.vo.system.RoleMenuVO;

public interface RoleMenuMapper extends MyMapper<RoleMenu> {
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<RoleMenu> getListByParams(Map<String,Object> params);
    
    public List<RoleMenuVO> getListForPage(Map<String,Object> params);
    
    public void deleteByRole(@Param("role")String role);
}