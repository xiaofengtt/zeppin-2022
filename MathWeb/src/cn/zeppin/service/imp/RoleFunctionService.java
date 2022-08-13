package cn.zeppin.service.imp;

import java.util.List;

import cn.zeppin.dao.api.IRoleFuncationDAO;
import cn.zeppin.entity.RoleFuncation;
import cn.zeppin.service.api.IRoleFunctionService;

public class RoleFunctionService implements IRoleFunctionService {

	
	private IRoleFuncationDAO roleFunctionDAO;
	
	public IRoleFuncationDAO getRoleFunctionDAO() {
		return roleFunctionDAO;
	}

	public void setRoleFunctionDAO(IRoleFuncationDAO roleFunctionDAO) {
		this.roleFunctionDAO = roleFunctionDAO;
	}



	/**
	 * 根据角色获取功能
	 * @author Administrator
	 * @date: 2014年7月24日 下午1:39:47 <br/> 
	 * @param role
	 * @return
	 */
	@Override
	public List<RoleFuncation> getRoleFunctionByRoleid(int role) {
		
		return this.getRoleFunctionDAO().getRoleFunctionByRoleid(role);
	}

}
