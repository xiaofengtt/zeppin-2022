package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.product.worldmall.entity.RoleMenu;
import cn.product.worldmall.util.MyMapper;
import cn.product.worldmall.vo.back.RoleMenuVO;

public interface RoleMenuMapper extends MyMapper<RoleMenu> {
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<RoleMenu> getListByParams(Map<String,Object> params);
    
    public List<RoleMenuVO> getListForPage(Map<String,Object> params);
    
    public void deleteByRole(@Param("role")String role);
}