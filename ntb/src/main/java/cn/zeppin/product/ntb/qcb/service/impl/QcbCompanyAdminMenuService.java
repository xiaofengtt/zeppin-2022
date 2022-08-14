/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.core.entity.QcbCompanyAdminMenu;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAdminMenuDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAdminMenuService;

/**
 * @author hehe
 *
 */
@Service
public class QcbCompanyAdminMenuService extends BaseService implements IQcbCompanyAdminMenuService {

	@Autowired
	private IQcbCompanyAdminMenuDAO qcbCompanyAdminMenuDAO;
	
	@Override
	public QcbCompanyAdminMenu insert(QcbCompanyAdminMenu qcbCompanyAdminMenu) {
		return qcbCompanyAdminMenuDAO.insert(qcbCompanyAdminMenu);
	}

	@Override
	public QcbCompanyAdminMenu delete(QcbCompanyAdminMenu qcbCompanyAdminMenu) {
		return qcbCompanyAdminMenuDAO.delete(qcbCompanyAdminMenu);
	}

	@Override
	public QcbCompanyAdminMenu update(QcbCompanyAdminMenu qcbCompanyAdminMenu) {
		return qcbCompanyAdminMenuDAO.update(qcbCompanyAdminMenu);
	}

	@Override
	public QcbCompanyAdminMenu get(String uuid) {
		return qcbCompanyAdminMenuDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return qcbCompanyAdminMenuDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbCompanyAdminMenuDAO.getCount(inputParams);
	}
}
