package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.product.score.entity.RoleMenu;
import cn.product.score.util.MyMapper;
import cn.product.score.vo.back.RoleMenuVO;

public interface RoleMenuMapper extends MyMapper<RoleMenu> {
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<RoleMenu> getListByParams(Map<String,Object> params);
    
    public List<RoleMenuVO> getListForPage(Map<String,Object> params);
    
    public void deleteByRole(@Param("role")String role);
}