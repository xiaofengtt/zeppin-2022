package cn.zeppin.dao.api;

import java.util.List;

import cn.zeppin.entity.RoleFuncation;

public interface IRoleFuncationDAO extends IBaseDAO<RoleFuncation, Integer> {
	
	/**
	 * 根据角色获取功能
	 */
	List<RoleFuncation> getRoleFunctionByRoleid(int role);
	
}
