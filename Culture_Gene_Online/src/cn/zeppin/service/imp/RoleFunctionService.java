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
	 */
	@Override
	public List<RoleFuncation> getRoleFunctionByRoleid(int role) {
		
		return this.getRoleFunctionDAO().getRoleFunctionByRoleid(role);
	}

}
