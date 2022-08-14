/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.qcb.dao.api.IQcbMenuDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbMenuService;
import cn.zeppin.product.ntb.core.entity.QcbMenu;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class QcbMenuService extends BaseService implements IQcbMenuService {

	@Autowired
	private IQcbMenuDAO qcbVirtualAccountsDAO;
	
	@Override
	public QcbMenu insert(QcbMenu qcbVirtualAccounts) {
		return qcbVirtualAccountsDAO.insert(qcbVirtualAccounts);
	}

	@Override
	public QcbMenu delete(QcbMenu qcbVirtualAccounts) {
		return qcbVirtualAccountsDAO.delete(qcbVirtualAccounts);
	}

	@Override
	public QcbMenu update(QcbMenu qcbVirtualAccounts) {
		return qcbVirtualAccountsDAO.update(qcbVirtualAccounts);
	}

	@Override
	public QcbMenu get(String uuid) {
		return qcbVirtualAccountsDAO.get(uuid);
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
		return qcbVirtualAccountsDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbVirtualAccountsDAO.getCount(inputParams);
	}
}
