/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.qcb.dao.api.IQcbAdminDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAdminMenuService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAdminService;
import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.core.entity.QcbAdmin.QcbAdminStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdmin;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdminMenu;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class QcbAdminService extends BaseService implements IQcbAdminService {

	@Autowired
	private IQcbAdminDAO qcbAdminDAO;
	
	@Autowired
	private IQcbCompanyAccountDAO qcbCompanyAccountDAO;
	
	@Autowired
	private IQcbCompanyAdminMenuService qcbCompanyAdminMenuDAO;
	
	@Autowired
	private IQcbCompanyAdminService qcbCompanyAdminDAO;
	
	@Override
	public QcbAdmin insert(QcbAdmin qcbAdmin) {
		return qcbAdminDAO.insert(qcbAdmin);
	}

	@Override
	public QcbAdmin delete(QcbAdmin qcbAdmin) {
		qcbAdmin.setStatus(QcbAdminStatus.DELETED);
		return qcbAdminDAO.update(qcbAdmin);
	}

	@Override
	public QcbAdmin update(QcbAdmin qcbAdmin) {
		return qcbAdminDAO.update(qcbAdmin);
	}

	@Override
	public QcbAdmin get(String uuid) {
		return qcbAdminDAO.get(uuid);
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
		return qcbAdminDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbAdminDAO.getCount(inputParams);
	}

	@Override
	public QcbAdmin getByMobile(String mobile) {
		return qcbAdminDAO.getByMobile(mobile);
	}

	@Override
	public boolean isExistQcbAdminByMobile(String mobile, String uuid) {
		return qcbAdminDAO.isExistQcbAdminByMobile(mobile, uuid);
	}

	@Override
	public void insertRegisterInfo(boolean flag, QcbAdmin qa, QcbCompanyAccount qca,
			QcbCompanyAdmin qcAdmin, List<QcbCompanyAdminMenu> listQcaMenu) {
		// TODO Auto-generated method stub
		if(flag){
			this.qcbAdminDAO.insert(qa);
		} else {
			this.qcbAdminDAO.update(qa);
		}
		this.qcbCompanyAccountDAO.insert(qca);
		this.qcbCompanyAdminDAO.insert(qcAdmin);
		if(listQcaMenu != null && listQcaMenu.size() > 0){
			for(QcbCompanyAdminMenu qcam : listQcaMenu){
				this.qcbCompanyAdminMenuDAO.insert(qcam);
			}
		}
	}

	@Override
	public void update(boolean flag, QcbAdmin qa, QcbCompanyAccount qca) {
		// TODO Auto-generated method stub
		this.qcbAdminDAO.update(qa);
		if(flag){
			this.qcbCompanyAccountDAO.update(qca);
		}
	}
}
