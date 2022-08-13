package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBkRoleControllerPermissionDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBkRoleControllerPermissionService;
import cn.zeppin.product.ntb.core.entity.BkControllerMethod;
import cn.zeppin.product.ntb.core.entity.BkOperatorRole;
import cn.zeppin.product.ntb.core.entity.BkRoleControllerPermission;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author Clark.R 2016年9月21日
 *
 */

@Service
public class BkRoleControllerPermissionService extends BaseService implements IBkRoleControllerPermissionService {	
	
	@Autowired
	private IBkRoleControllerPermissionDAO bkRoleControllerPermissionDAO;
	
	/**
	 * 更新所有的角色权限数据
	 */
	@Override
	public void updateAll(BkOperatorRole role, List<BkControllerMethod> methodList) {
		//删除现有权限
		bkRoleControllerPermissionDAO.deleteByRole(role);
		//循环添加权限
		for(BkControllerMethod method : methodList){
			BkRoleControllerPermission roleMethod = new BkRoleControllerPermission();
			roleMethod.setUuid(UUID.randomUUID().toString());
			roleMethod.setController(method.getController());
			roleMethod.setMethod(method.getUuid());
			roleMethod.setRole(role.getUuid());
			bkRoleControllerPermissionDAO.insert(roleMethod);
		}
	}
	
	/**
	 * 添加角色的控制器方法权限
	 * 在系统开发中，要将Controller和Method添加到数据库中，并通过系统建立每个方法的角色权限
	 * @param permission
	 * @return
	 */
	@Override
	public BkRoleControllerPermission insert(BkRoleControllerPermission permission) {
		return bkRoleControllerPermissionDAO.insert(permission);
	}

	/**
	 * 通过角色获取权限
	 * @param role
	 * @return
	 */
	@Override
	public List<BkRoleControllerPermission> getByRole(BkOperatorRole role) {
		return bkRoleControllerPermissionDAO.getByRole(role);
	}

	@Override
	public BkRoleControllerPermission delete(BkRoleControllerPermission role) {
		return bkRoleControllerPermissionDAO.delete(role);
	}

	@Override
	public BkRoleControllerPermission update(BkRoleControllerPermission role) {
		return bkRoleControllerPermissionDAO.update(role);
	}

	@Override
	public BkRoleControllerPermission get(String uuid) {
		return bkRoleControllerPermissionDAO.get(uuid);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return bkRoleControllerPermissionDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return bkRoleControllerPermissionDAO.getCount(inputParams);
	}

}
