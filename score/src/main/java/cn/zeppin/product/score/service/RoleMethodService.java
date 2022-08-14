package cn.zeppin.product.score.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.Method;
import cn.zeppin.product.score.entity.Role;
import cn.zeppin.product.score.entity.RoleMethod;

public interface RoleMethodService extends IService<RoleMethod>  {
	
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
