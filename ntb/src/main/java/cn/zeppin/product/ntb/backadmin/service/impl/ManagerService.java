/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IManagerDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IManagerService;
import cn.zeppin.product.ntb.core.entity.Manager;
import cn.zeppin.product.ntb.core.entity.Manager.ManagerStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class ManagerService extends BaseService implements IManagerService {

	@Autowired
	private IManagerDAO managerDAO;

	
	@Override
	public Manager insert(Manager manager) {
		return managerDAO.insert(manager);
	}

	@Override
	public Manager delete(Manager manager) {
		manager.setStatus(ManagerStatus.DELETED);
		manager.setIdcard(manager.getIdcard() + "_#" + manager.getUuid());
		manager.setMobile(manager.getMobile() + "_#" + manager.getUuid());
		return managerDAO.update(manager);
	}

	@Override
	public Manager update(Manager manager) {
		return managerDAO.update(manager);
	}

	@Override
	public Manager get(String uuid) {
		return managerDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询Manager结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return managerDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return managerDAO.getCount(inputParams);
	}

	/**
	 * 验证同身份证的主理人信息是否已经存在
	 * @param idcard
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistManagerByIdcard(String idcard, String uuid) {
		return managerDAO.isExistManagerByIdcard(idcard, uuid);
	}

	/**
	 * 验证同手机的主理人信息是否已经存在
	 * @param mobile
	 * @param uuid
	 * @return
	 */
	@Override
	public boolean isExistManagerByMobile(String mobile, String uuid) {
		return managerDAO.isExistManagerByMobile(mobile, uuid);
	}
	
	/**
	 * 删除主理人
	 * @param uuid
	 */
	@Override
	public Manager deleteManager(Manager manager) {
		manager.setStatus(ManagerStatus.DELETED);
		manager.setIdcard(manager.getIdcard()+"_"+manager.getUuid());
		manager.setMobile(manager.getMobile()+"_"+manager.getUuid());
		return managerDAO.update(manager);
	}
}
