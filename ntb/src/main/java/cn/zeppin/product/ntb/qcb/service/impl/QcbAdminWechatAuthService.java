/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.core.entity.QcbAdminWechatAuth;
import cn.zeppin.product.ntb.core.entity.QcbAdminWechatAuth.QcbAdminWechatAuthStatus;
import cn.zeppin.product.ntb.core.entity.QcbAdminWechatAuth.QcbAdminWechatAuthType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayroll;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayroll.QcbCompanyPayrollStatus;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbAdminDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbAdminWechatAuthDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyPayrollDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminWechatAuthService;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 */
@Service
public class QcbAdminWechatAuthService extends BaseService implements IQcbAdminWechatAuthService {

	@Autowired
	private IQcbAdminDAO qcbAdminDAO;
	
	@Autowired
	private IQcbAdminWechatAuthDAO qcbAdminWechatAuthDAO;
	
	@Autowired
	private IQcbCompanyPayrollDAO qcbCompanyPayrollDAO;
	
	@Override
	public QcbAdminWechatAuth insert(QcbAdminWechatAuth qcbAdminWechatAuth) {
		return qcbAdminWechatAuthDAO.insert(qcbAdminWechatAuth);
	}

	@Override
	public QcbAdminWechatAuth delete(QcbAdminWechatAuth qcbAdminWechatAuth) {
		return qcbAdminWechatAuthDAO.delete(qcbAdminWechatAuth);
	}

	@Override
	public QcbAdminWechatAuth update(QcbAdminWechatAuth qcbAdminWechatAuth) {
		return qcbAdminWechatAuthDAO.update(qcbAdminWechatAuth);
	}

	@Override
	public QcbAdminWechatAuth get(String uuid) {
		return qcbAdminWechatAuthDAO.get(uuid);
	}
	
	/**
	 * 身份认证
	 */
	@Override
	public void check(QcbAdminWechatAuth qawa) throws TransactionException{
		QcbAdmin qa = this.qcbAdminDAO.get(qawa.getQcbAdmin());
		if(qa == null){
			throw new TransactionException("管理员信息有误！");
		}
		
		if(QcbAdminWechatAuthType.BIND.equals(qawa.getType())){
			if(qa.getWechatFlag()){
				throw new TransactionException("管理员已绑定微信，请先解绑原微信！");
			}
			
			qa.setWechatNum(qawa.getOpenid());
			qa.setWechatFlag(true);
			this.qcbAdminDAO.update(qa);
			
			qawa.setStatus(QcbAdminWechatAuthStatus.SUCCESS);
			this.qcbAdminWechatAuthDAO.update(qawa);
		}else if(QcbAdminWechatAuthType.REMOVE.equals(qawa.getType())){
			if(!qa.getWechatFlag()){
				throw new TransactionException("管理员未绑定微信！");
			}
			
			qa.setWechatNum(null);
			qa.setWechatFlag(false);
			this.qcbAdminDAO.update(qa);
			
			qawa.setStatus(QcbAdminWechatAuthStatus.SUCCESS);
			this.qcbAdminWechatAuthDAO.update(qawa);
		}else if(QcbAdminWechatAuthType.PAYROLL.equals(qawa.getType())){
			if(!qa.getWechatFlag()){
				throw new TransactionException("管理员未绑定微信！");
			}
			
			if(!qawa.getOpenid().equals(qa.getWechatNum())){
				throw new TransactionException("微信账号不正确！");
			}
			
			if(Utlity.checkStringNull(qawa.getQcbCompanyPayroll())){
				throw new TransactionException("薪资发放信息不正确！");
			}
			
			QcbCompanyPayroll qcp = this.qcbCompanyPayrollDAO.get(qawa.getQcbCompanyPayroll());
			qcp.setStatus(QcbCompanyPayrollStatus.SUBMIT);
			this.qcbCompanyPayrollDAO.update(qcp);
			
			qawa.setStatus(QcbAdminWechatAuthStatus.SUCCESS);
			this.qcbAdminWechatAuthDAO.update(qawa);
		}else if(QcbAdminWechatAuthType.LOGIN.equals(qawa.getType())){
			if(!qa.getWechatFlag()){
				throw new TransactionException("管理员未绑定微信！");
			}
			
			if(!qawa.getOpenid().equals(qa.getWechatNum())){
				throw new TransactionException("微信账号不正确！");
			}
			
			qawa.setStatus(QcbAdminWechatAuthStatus.SUCCESS);
			this.qcbAdminWechatAuthDAO.update(qawa);
		}
	}
}
