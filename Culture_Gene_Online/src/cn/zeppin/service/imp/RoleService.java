package cn.zeppin.service.imp;


import cn.zeppin.dao.api.IRoleDAO;
import cn.zeppin.entity.Role;
import cn.zeppin.service.api.IRoleService;

public class RoleService implements IRoleService {

	private IRoleDAO roleDAO;

	public IRoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(IRoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	@Override
	public Role getRoleById(Integer id) {
		// TODO Auto-generated method stub
		return this.roleDAO.get(id);
	}
	

}
