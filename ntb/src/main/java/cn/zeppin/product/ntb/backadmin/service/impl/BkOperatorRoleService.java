/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBkOperatorRoleDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorRoleService;
import cn.zeppin.product.ntb.core.entity.BkOperatorRole;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author Clark.R 2016年9月28日
 *
 */

@Service
public class BkOperatorRoleService extends BaseService implements IBkOperatorRoleService {

	@Autowired
	private IBkOperatorRoleDAO bkOperatorRoleDAO;
	
	
	@Override
	public BkOperatorRole insert(BkOperatorRole role) {
		return bkOperatorRoleDAO.insert(role);
	}

	@Override
	public BkOperatorRole delete(BkOperatorRole role) {
		return bkOperatorRoleDAO.delete(role);
	}

	@Override
	public BkOperatorRole update(BkOperatorRole role) {
		return bkOperatorRoleDAO.update(role);
	}

	@Override
	public BkOperatorRole get(String uuid) {
		return bkOperatorRoleDAO.get(uuid);
	}
	
	/**
	 * 通过名称获得角色对象
	 * @param name
	 * @return
	 */
	@Override
	public BkOperatorRole getByName(String name) {
		return bkOperatorRoleDAO.getByName(name);
	}
	
	/**
	 * 获取角色列表
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getList(Class<? extends Entity> resultClass){
		return bkOperatorRoleDAO.getList(resultClass);
	}
}
