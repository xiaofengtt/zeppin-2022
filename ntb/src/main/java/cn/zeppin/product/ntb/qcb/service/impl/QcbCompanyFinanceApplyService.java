/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyFinanceApply;
import cn.zeppin.product.ntb.core.entity.QcbCompanyFinanceApply.QcbCompanyFinanceApplyStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyFinanceApplyDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyFinanceApplyService;

/**
 * @author hehe
 *
 */
@Service
public class QcbCompanyFinanceApplyService extends BaseService implements IQcbCompanyFinanceApplyService {

	@Autowired
	private IQcbCompanyFinanceApplyDAO qcbCompanyFinanceApplyDAO;
	
	@Autowired
	private IQcbCompanyAccountDAO qcbCompanyAccountDAO;
	
	@Override
	public QcbCompanyFinanceApply insert(QcbCompanyFinanceApply qcbCompanyFinanceApply) {
		return qcbCompanyFinanceApplyDAO.insert(qcbCompanyFinanceApply);
	}

	@Override
	public QcbCompanyFinanceApply delete(QcbCompanyFinanceApply qcbCompanyFinanceApply) {
		qcbCompanyFinanceApply.setStatus(QcbCompanyFinanceApplyStatus.DELETED);
		return qcbCompanyFinanceApplyDAO.update(qcbCompanyFinanceApply);
	}

	@Override
	public QcbCompanyFinanceApply update(QcbCompanyFinanceApply qcbCompanyFinanceApply) {
		return qcbCompanyFinanceApplyDAO.update(qcbCompanyFinanceApply);
	}

	@Override
	public QcbCompanyFinanceApply get(String uuid) {
		return qcbCompanyFinanceApplyDAO.get(uuid);
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
		return qcbCompanyFinanceApplyDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbCompanyFinanceApplyDAO.getCount(inputParams);
	}

	@Override
	public void insert(QcbCompanyFinanceApply qcbCompanyFinanceApply,
			QcbCompanyAccount qca) {
		// TODO Auto-generated method stub
		this.qcbCompanyFinanceApplyDAO.insert(qcbCompanyFinanceApply);
		this.qcbCompanyAccountDAO.update(qca);
	}

	@Override
	public List<Entity> getListForFinancePage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return qcbCompanyFinanceApplyDAO.getListForFinancePage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCountForFinance(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return qcbCompanyFinanceApplyDAO.getCountForFinance(inputParams);
	}

	@Override
	public List<Entity> getStatusListForFinance(Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return qcbCompanyFinanceApplyDAO.getStatusListForFinance(resultClass);
	}

	@Override
	public void update(QcbCompanyFinanceApply qcbCompanyFinanceApply,
			QcbCompanyAccount qca) {
		// TODO Auto-generated method stub
		this.qcbCompanyFinanceApplyDAO.update(qcbCompanyFinanceApply);
		this.qcbCompanyAccountDAO.update(qca);
	}

}
