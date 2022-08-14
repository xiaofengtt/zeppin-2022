/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyOperateDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbVirtualAccountsDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperate;
import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class QcbCompanyAccountService extends BaseService implements IQcbCompanyAccountService {

	@Autowired
	private IQcbCompanyAccountDAO qcbCompanyAccountDAO;
	
	@Autowired
	private IQcbVirtualAccountsDAO qcbVirtualAccountsDAO;
	
	@Autowired
	private IQcbCompanyOperateDAO qcbCompanyOperateDAO;
	
	@Override
	public QcbCompanyAccount insert(QcbCompanyAccount qcbCompanyAccount) {
		return qcbCompanyAccountDAO.insert(qcbCompanyAccount);
	}

	@Override
	public QcbCompanyAccount delete(QcbCompanyAccount qcbCompanyAccount) {
		qcbCompanyAccount.setStatus(QcbCompanyAccountStatus.DELETED);
		return qcbCompanyAccountDAO.update(qcbCompanyAccount);
	}

	@Override
	public QcbCompanyAccount update(QcbCompanyAccount qcbCompanyAccount) {
		return qcbCompanyAccountDAO.update(qcbCompanyAccount);
	}

	@Override
	public QcbCompanyAccount get(String uuid) {
		return qcbCompanyAccountDAO.get(uuid);
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
		return qcbCompanyAccountDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbCompanyAccountDAO.getCount(inputParams);
	}

	@Override
	public QcbCompanyAccount getByMerchantId(String merchantId) {
		return qcbCompanyAccountDAO.getByMerchantId(merchantId);
	}

	@Override
	public boolean isExistQcbCompanyAccountByMerchantId(String merchantId, String uuid) {
		return qcbCompanyAccountDAO.isExistQcbCompanyAccountByMerchantId(merchantId, uuid);
	}

	@Override
	public boolean isExistQcbCompanyAccountByName(String name, String uuid) {
		return qcbCompanyAccountDAO.isExistQcbCompanyAccountByName(name, uuid);
	}

	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		return qcbCompanyAccountDAO.getStatusList(resultClass);
	}
	
	/**
	 * 更新绑定虚拟账户
	 * @param qcbCompanyAccount
	 * @param qcbVirtualAccounts
	 */
	public void updateVirtualAccount(QcbCompanyAccount qca, QcbVirtualAccounts qva){
		this.qcbCompanyAccountDAO.update(qca);
		this.qcbVirtualAccountsDAO.update(qva);
	}

	@Override
	public void update(QcbCompanyAccount qca, QcbCompanyOperate qco) {
		// TODO Auto-generated method stub
		this.qcbCompanyAccountDAO.update(qca);
		this.qcbCompanyOperateDAO.insert(qco);
	}

	@Override
	public List<Entity> getCheckStatusList(Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return this.qcbCompanyAccountDAO.getCheckStatusList(resultClass);
	}

	@Override
	public BigDecimal getTotalBalacneByEmp(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return this.qcbCompanyAccountDAO.getTotalBalacneByEmp(inputParams);
	}
}
