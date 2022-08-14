/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyPayrollRecordsDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyPayrollRecordsService;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollRecords;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollRecords.QcbCompanyPayrollRecordsStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class QcbCompanyPayrollRecordsService extends BaseService implements IQcbCompanyPayrollRecordsService {

	@Autowired
	private IQcbCompanyPayrollRecordsDAO qcbCompanyPayrollRecordsDAO;
	
	@Override
	public QcbCompanyPayrollRecords insert(QcbCompanyPayrollRecords qcbCompanyPayrollRecords) {
		return qcbCompanyPayrollRecordsDAO.insert(qcbCompanyPayrollRecords);
	}

	@Override
	public QcbCompanyPayrollRecords delete(QcbCompanyPayrollRecords qcbCompanyPayrollRecords) {
		qcbCompanyPayrollRecords.setStatus(QcbCompanyPayrollRecordsStatus.DELETED);
		return qcbCompanyPayrollRecordsDAO.update(qcbCompanyPayrollRecords);
	}

	@Override
	public QcbCompanyPayrollRecords update(QcbCompanyPayrollRecords qcbCompanyPayrollRecords) {
		return qcbCompanyPayrollRecordsDAO.update(qcbCompanyPayrollRecords);
	}

	@Override
	public QcbCompanyPayrollRecords get(String uuid) {
		return qcbCompanyPayrollRecordsDAO.get(uuid);
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
		return qcbCompanyPayrollRecordsDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbCompanyPayrollRecordsDAO.getCount(inputParams);
	}

	@Override
	public List<Entity> getListForWebPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return qcbCompanyPayrollRecordsDAO.getListForWebPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCountForWeb(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return qcbCompanyPayrollRecordsDAO.getCountForWeb(inputParams);
	}
}
