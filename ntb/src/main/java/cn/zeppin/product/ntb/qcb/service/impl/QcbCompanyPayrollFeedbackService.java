/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyPayrollFeedbackDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyPayrollFeedbackService;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollFeedback;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollFeedback.QcbCompanyPayrollFeedbackStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class QcbCompanyPayrollFeedbackService extends BaseService implements IQcbCompanyPayrollFeedbackService {

	@Autowired
	private IQcbCompanyPayrollFeedbackDAO qcbCompanyPayrollFeedbackDAO;
	
	@Override
	public QcbCompanyPayrollFeedback insert(QcbCompanyPayrollFeedback qcbCompanyPayrollFeedback) {
		return qcbCompanyPayrollFeedbackDAO.insert(qcbCompanyPayrollFeedback);
	}

	@Override
	public QcbCompanyPayrollFeedback delete(QcbCompanyPayrollFeedback qcbCompanyPayrollFeedback) {
		qcbCompanyPayrollFeedback.setStatus(QcbCompanyPayrollFeedbackStatus.DELETED);
		return qcbCompanyPayrollFeedbackDAO.update(qcbCompanyPayrollFeedback);
	}

	@Override
	public QcbCompanyPayrollFeedback update(QcbCompanyPayrollFeedback qcbCompanyPayrollFeedback) {
		return qcbCompanyPayrollFeedbackDAO.update(qcbCompanyPayrollFeedback);
	}

	@Override
	public QcbCompanyPayrollFeedback get(String uuid) {
		return qcbCompanyPayrollFeedbackDAO.get(uuid);
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
		return qcbCompanyPayrollFeedbackDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbCompanyPayrollFeedbackDAO.getCount(inputParams);
	}
}
