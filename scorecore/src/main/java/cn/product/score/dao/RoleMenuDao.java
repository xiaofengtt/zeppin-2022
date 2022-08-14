package cn.product.score.dao;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.Role;
import cn.product.score.entity.RoleMenu;
import cn.product.score.vo.back.MenuVO;
import cn.product.score.vo.back.RoleMenuVO;

public interface RoleMenuDao extends IDao<RoleMenu>  {
	
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
    public List<RoleMenu> getListByParams(Map<String, Object> params);
    
    /**
     * 关联过去列表
     * @param params
     * @return
     */
    public List<RoleMenuVO> getListForPage(Map<String,Object> params);
    
    /**
     * 批量更新
     * @param role
     * @param menuList
     */
    public void updateAll(Role role, List<MenuVO> menuList);
}
