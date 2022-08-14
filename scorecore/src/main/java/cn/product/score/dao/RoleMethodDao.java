package cn.product.score.dao;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.Method;
import cn.product.score.entity.Role;
import cn.product.score.entity.RoleMethod;

public interface RoleMethodDao extends IDao<RoleMethod>  {
	
	public Integer getCountByParams(Map<String, Object> params);
	
    public List<RoleMethod> getListByParams(Map<String,Object> params);    
    
    /**
	 * 更新角色权限列表
	 * @param role
	 * @param methodList
	 * @return
	 */
	public void updateAll (Role role, List<Method> methodList);
}
