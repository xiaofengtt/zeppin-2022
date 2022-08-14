/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IAliCertificationDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.core.entity.AliCertification;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.QcbCompanyEmployee;
import cn.zeppin.product.ntb.core.entity.QcbCompanyEmployee.QcbCompanyEmployeeStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyEmployeeDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyEmployeeService;
import cn.zeppin.product.utility.SendSms;

/**
 * @author hehe
 *
 */
@Service
public class QcbCompanyEmployeeService extends BaseService implements IQcbCompanyEmployeeService {

	@Autowired
	private IQcbCompanyEmployeeDAO qcbCompanyEmployeeDAO;
	
	@Autowired
	private IQcbEmployeeDAO qcbEmployeeDAO;
	
	@Autowired
	private IAliCertificationDAO aliCertificationDAO;
	
	@Autowired
	private IMobileCodeService mobileCodeDAO;
	
	@Override
	public QcbCompanyEmployee insert(QcbCompanyEmployee qcbCompanyEmployee) {
		return qcbCompanyEmployeeDAO.insert(qcbCompanyEmployee);
	}

	@Override
	public QcbCompanyEmployee delete(QcbCompanyEmployee qcbCompanyEmployee) {
		qcbCompanyEmployee.setQcbCompany(qcbCompanyEmployee.getQcbCompany()+"_#"+qcbCompanyEmployee.getUuid());
		qcbCompanyEmployee.setQcbEmployee(qcbCompanyEmployee.getQcbEmployee()+"_#"+qcbCompanyEmployee.getUuid());
		qcbCompanyEmployee.setStatus(QcbCompanyEmployeeStatus.DELETED);
		return qcbCompanyEmployeeDAO.update(qcbCompanyEmployee);
	}

	@Override
	public QcbCompanyEmployee update(QcbCompanyEmployee qcbCompanyEmployee) {
		return qcbCompanyEmployeeDAO.update(qcbCompanyEmployee);
	}

	@Override
	public QcbCompanyEmployee get(String uuid) {
		return qcbCompanyEmployeeDAO.get(uuid);
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
		return qcbCompanyEmployeeDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbCompanyEmployeeDAO.getCount(inputParams);
	}

	@Override
	public List<Entity> getListForListPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		return qcbCompanyEmployeeDAO.getListForListPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCountForList(Map<String, String> inputParams) {
		return qcbCompanyEmployeeDAO.getCountForList(inputParams);
	}

	@Override
	public void insert(boolean flag, QcbEmployee qe, QcbCompanyEmployee qce,
			AliCertification ac, MobileCode mc) throws TransactionException {
		if(!flag){
			this.qcbEmployeeDAO.insert(qe);
		}
		this.qcbCompanyEmployeeDAO.insert(qce);
		if(ac != null){
			this.aliCertificationDAO.insert(ac);
		}
		this.insertSmsInfo(mc);
	}
	
	public void insertSmsInfo(MobileCode mc) throws TransactionException {
		String phone = mc.getMobile();
		String content = mc.getContent();
		String result = SendSms.sendSms4Qcb(content, phone);
		if (result != null && "0".equals(result.split("_")[0])) {
			this.mobileCodeDAO.insert(mc);
		} else {
			throw new TransactionException("短信通知发送失败！");
		}
	}

	@Override
	public void update(QcbCompanyEmployee qce, QcbEmployee qe) {
		this.qcbCompanyEmployeeDAO.update(qce);
		this.qcbEmployeeDAO.update(qe);
	}
}
