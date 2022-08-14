/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsRoleDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITSsRoleService;
import cn.zeppin.product.itic.core.entity.TSsRole;
import cn.zeppin.product.itic.core.service.base.BaseService;


@Service
public class TSsRoleService extends BaseService implements ITSsRoleService {

	@Autowired
	private ITSsRoleDAO tSsRoleDAO;
	
	
	@Override
	public TSsRole insert(TSsRole role) {
		return tSsRoleDAO.insert(role);
	}

	@Override
	public TSsRole delete(TSsRole role) {
		return tSsRoleDAO.delete(role);
	}

	@Override
	public TSsRole update(TSsRole role) {
		return tSsRoleDAO.update(role);
	}

	@Override
	public TSsRole get(String uuid) {
		return tSsRoleDAO.get(uuid);
	}
	
	/**
	 * 获取角色列表
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<TSsRole> getList(){
		return tSsRoleDAO.getList();
	}
}
