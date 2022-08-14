package cn.product.payment.dao;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.Menu;

public interface MenuDao extends IDao<Menu> {
	
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
    public List<Menu> getListByParams(Map<String, Object> params);
    
    /**
     * 获取用户权限
     * @param admin
     * @return
     */
    public List<Menu> getListByRole(Map<String,Object> params);
}
