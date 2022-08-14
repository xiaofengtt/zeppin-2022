package cn.zeppin.service.api;

import java.util.List;

import cn.zeppin.entity.RoleFuncation;

public interface IRoleFunctionService {
	
	/**
	 * 根据角色获取功能
	 * @author Administrator
	 * @param role
	 * @return
	 */
	List<RoleFuncation> getRoleFunctionByRoleid(int role);
	
}
