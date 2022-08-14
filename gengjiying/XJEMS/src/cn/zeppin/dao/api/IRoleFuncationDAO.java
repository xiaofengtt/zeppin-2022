package cn.zeppin.dao.api;

import java.util.List;

import cn.zeppin.entity.RoleFuncation;

/**
 * ClassName: IRoleFuncationDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public interface IRoleFuncationDAO extends IBaseDAO<RoleFuncation, Integer> {
	
	/**
	 * 根据角色获取功能
	 * @author Administrator
	 * @param role
	 * @return
	 */
	List<RoleFuncation> getRoleFunctionByRoleid(int role);
	
}
