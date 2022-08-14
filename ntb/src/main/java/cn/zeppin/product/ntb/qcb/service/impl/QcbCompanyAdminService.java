/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IMobileCodeDAO;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdmin;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdmin.QcbCompanyAdminStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdminMenu;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbAdminDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAdminDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAdminMenuDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAdminService;
import cn.zeppin.product.utility.SendSms;

/**
 * @author hehe
 *
 */
@Service
public class QcbCompanyAdminService extends BaseService implements IQcbCompanyAdminService {

	@Autowired
	private IQcbCompanyAdminDAO qcbCompanyAdminDAO;
	
	@Autowired
	private IQcbAdminDAO qcbAdminDAO;
	
	@Autowired
	private IQcbCompanyAccountDAO qcbCompanyAccountDAO;
	
	@Autowired
	private IQcbCompanyAdminMenuDAO qcbCompanyAdminMenuDAO;
	
	@Autowired
	private IMobileCodeDAO mobileCodeDAO;
	
	@Override
	public QcbCompanyAdmin insert(QcbCompanyAdmin qcbCompanyAdmin) {
		return qcbCompanyAdminDAO.insert(qcbCompanyAdmin);
	}

	@Override
	public QcbCompanyAdmin delete(QcbCompanyAdmin qcbCompanyAdmin) {
		qcbCompanyAdmin.setStatus(QcbCompanyAdminStatus.DELETED);
		qcbCompanyAdmin.setQcbAdmin(qcbCompanyAdmin.getQcbAdmin()+"_#"+qcbCompanyAdmin.getUuid());
		qcbCompanyAdmin.setQcbCompany(qcbCompanyAdmin.getQcbCompany()+"_#"+qcbCompanyAdmin.getQcbCompany());
		return qcbCompanyAdminDAO.update(qcbCompanyAdmin);
	}

	@Override
	public QcbCompanyAdmin update(QcbCompanyAdmin qcbCompanyAdmin) {
		return qcbCompanyAdminDAO.update(qcbCompanyAdmin);
	}

	@Override
	public QcbCompanyAdmin get(String uuid) {
		return qcbCompanyAdminDAO.get(uuid);
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
		return qcbCompanyAdminDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbCompanyAdminDAO.getCount(inputParams);
	}

	@Override
	public void insertCompanyAdmin(boolean flag, QcbAdmin qa,
			QcbCompanyAdmin qca, List<QcbCompanyAdminMenu> listMenu,
			MobileCode mc) throws TransactionException {
		// TODO Auto-generated method stub
		if(flag){
			this.qcbAdminDAO.insert(qa);
		}
		this.qcbCompanyAdminDAO.insert(qca);
		if(listMenu != null && listMenu.size() > 0){
			for(QcbCompanyAdminMenu qcam : listMenu){
				this.qcbCompanyAdminMenuDAO.insert(qcam);
			}
		}
		this.insertSmsInfo(mc);
		
	}
	
	public void insertSmsInfo(MobileCode mc) throws TransactionException {
		// TODO Auto-generated method stub
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
	public void update(QcbCompanyAdmin qca, List<QcbCompanyAdminMenu> listMenu) {
		// TODO Auto-generated method stub
		//查询出原有的权限 删除后再把新的添加进去
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbAdmin", qca.getQcbAdmin());
		inputParams.put("qcbCompany", qca.getQcbCompany());
		this.qcbCompanyAdminMenuDAO.delete(inputParams);
		
		if(listMenu != null && listMenu.size() > 0){
			for(QcbCompanyAdminMenu qcam : listMenu){
				this.qcbCompanyAdminMenuDAO.insert(qcam);
			}
		}
		this.qcbCompanyAdminDAO.update(qca);
	}

	@Override
	public void delete(QcbCompanyAdmin qca,
			List<QcbCompanyAdminMenu> listMenu) {
		// TODO Auto-generated method stub
		//删除角色权限（物理）
		if(listMenu != null && listMenu.size() > 0){
			for(QcbCompanyAdminMenu qcam : listMenu){
				this.qcbCompanyAdminMenuDAO.delete(qcam);
			}
		}
		//删除角色关联关系（逻辑）
		qca.setStatus(QcbCompanyAdminStatus.DELETED);
		qca.setQcbAdmin(qca.getQcbAdmin()+"_#"+qca.getUuid());
		qca.setQcbCompany(qca.getQcbCompany()+"_#"+qca.getQcbCompany());
		this.qcbCompanyAdminDAO.update(qca);
	}
}
