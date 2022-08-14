/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IMobileCodeDAO;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountAuthStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountVirtualAccountType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperate;
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperate.QcbCompanyOperateStatus;
import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts;
import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts.QcbVirtualAccountsStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyOperateCheckDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyOperateDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbVirtualAccountsDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyOperateService;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.SendSms;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 */
@Service
public class QcbCompanyOperateService extends BaseService implements IQcbCompanyOperateService {

	@Autowired
	private IQcbCompanyOperateDAO qcbCompanyOperateDAO;
	
	@Autowired
	private IQcbCompanyOperateCheckDAO qcbCompanyOperateCheckDAO;
	
	@Autowired
	private IQcbCompanyAccountDAO qcbCompanyAccountDAO;
	
	@Autowired
	private IQcbVirtualAccountsDAO qcbVirtualAccountsDAO;
	
	@Autowired
	private IMobileCodeDAO mobileCodeDAO;
	
	@Override
	public QcbCompanyOperate insert(QcbCompanyOperate qcbCompanyOperate) {
		return qcbCompanyOperateDAO.insert(qcbCompanyOperate);
	}

	@Override
	public QcbCompanyOperate delete(QcbCompanyOperate qcbCompanyOperate) {
		qcbCompanyOperate.setStatus(QcbCompanyOperateStatus.DELETED);
		return qcbCompanyOperateDAO.update(qcbCompanyOperate);
	}

	@Override
	public QcbCompanyOperate update(QcbCompanyOperate qcbCompanyOperate) {
		return qcbCompanyOperateDAO.update(qcbCompanyOperate);
	}

	@Override
	public QcbCompanyOperate get(String uuid) {
		return qcbCompanyOperateDAO.get(uuid);
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
		return qcbCompanyOperateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbCompanyOperateDAO.getCount(inputParams);
	}
	
	/**
	 * 根据企业获取信息
	 * @param qcbCompany
	 * @return
	 */
	@Override
	public QcbCompanyOperate getByQcbCompany(String qcbCompany){
		return qcbCompanyOperateDAO.getByQcbCompany(qcbCompany);
	}
	
	/**
	 * 企财宝添加认证信息
	 * @param qcbCompanyOperate
	 * @return
	 */
	@Override
	public QcbCompanyOperate qcbCompanyInsert(QcbCompanyOperate qcbCompanyOperate) {
		QcbCompanyAccount qca = this.qcbCompanyAccountDAO.get(qcbCompanyOperate.getQcbCompany());
		qca.setStatus(QcbCompanyAccountStatus.UNCHECK);
		qcbCompanyAccountDAO.update(qca);
		return qcbCompanyOperateDAO.insert(qcbCompanyOperate);
	}
	
	/**
	 * 企财宝更新认证信息
	 * @param qcbCompanyOperate
	 * @return
	 */
	@Override
	public QcbCompanyOperate qcbCompanyUpdate(QcbCompanyOperate qcbCompanyOperate) {
		QcbCompanyAccount qca = this.qcbCompanyAccountDAO.get(qcbCompanyOperate.getQcbCompany());
		qca.setStatus(QcbCompanyAccountStatus.UNCHECK);
		qcbCompanyAccountDAO.update(qca);
		return qcbCompanyOperateDAO.update(qcbCompanyOperate);
	}

	/**
	 * 企财宝企业认证审核
	 * @param qcbCompanyOperateCheck
	 * @return
	 */
	public void check(QcbCompanyOperate qco) throws TransactionException {
		QcbCompanyAccount qca = this.qcbCompanyAccountDAO.get(qco.getQcbCompany());
		if(qca == null){
			throw new TransactionException("企财宝企业信息不正确！");
		}
		
//		if(QcbCompanyOperateCheckStatus.CHECKED.equals(qcoc.getStatus())){
//			qco.setStatus(QcbCompanyOperateStatus.CHECKED);
//			qca.setStatus(QcbCompanyAccountStatus.NORMAL);
//		}else if(QcbCompanyOperateCheckStatus.UNPASSED.equals(qcoc.getStatus())){
//			qco.setStatus(QcbCompanyOperateStatus.UNPASSED);
//			qca.setStatus(QcbCompanyAccountStatus.NOPASS);
//		}
		String content = "";
		if(QcbCompanyOperateStatus.CHECKED.equals(qco.getStatus())){//审核通过
			if(!QcbCompanyAccountStatus.NORMAL.equals(qca.getStatus())){
				qca.setStatus(QcbCompanyAccountStatus.NORMAL);
			}
			qca.setAuthStatus(QcbCompanyAccountAuthStatus.NORMAL);
			QcbCompanyAccount newData = JSONUtils.json2obj(qco.getValue(), QcbCompanyAccount.class);
			//更新信息
			qca.setName(newData.getName());
			qca.setBkArea(newData.getBkArea());
			qca.setAddress(newData.getAddress());
			qca.setPhone(newData.getPhone());
			qca.setCorporation(newData.getCorporation());
			qca.setConnectionCompany(newData.getConnectionCompany());
			qca.setLogo(newData.getLogo());
			
			//编辑企业开票信息
			qca.setTaxIdentificationNum(newData.getTaxIdentificationNum());
			qca.setTaxAddress(newData.getTaxAddress());
			qca.setTaxCompany(newData.getTaxCompany());
			qca.setTaxPhone(newData.getTaxPhone());
			qca.setOpenBank(newData.getOpenBank());
			qca.setOpenBankCardnum(newData.getOpenBankCardnum());
			
			//编辑企业联系人信息
			qca.setContacts(newData.getContacts());
			qca.setContactsMobile(newData.getContactsMobile());
			qca.setContactsEmail(newData.getContactsEmail());
			qca.setContactsIdcard(newData.getContactsIdcard());
			
			qca.setBusinessLicence(newData.getBusinessLicence());
			qca.setEvidence(newData.getEvidence());
			qca.setIdcardFace(newData.getIdcardFace());
			qca.setIdcardBack(newData.getIdcardBack());
			
			if(Utlity.checkStringNull(qca.getQcbVirtualAccounts())){
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("status", QcbVirtualAccountsStatus.NORMAL);
				List<Entity> list = qcbVirtualAccountsDAO.getListForPage(searchMap, 1, 10, null, QcbVirtualAccounts.class);
				if(list == null || list.size() == 0){
					throw new TransactionException("没有未分配的银行虚拟账户！");
				}
				
				QcbVirtualAccounts qva = (QcbVirtualAccounts)list.get(new Random().nextInt(list.size()));
				qva = this.qcbVirtualAccountsDAO.get(qva.getUuid());
				qva.setStatus(QcbVirtualAccountsStatus.USED);
				qva.setQcbCompany(qca.getUuid());
				qca.setQcbVirtualAccounts(qva.getUuid());
				qca.setVirtualAccountType(QcbCompanyAccountVirtualAccountType.VIRTUAL);
				this.qcbVirtualAccountsDAO.update(qva);
			}
			content = "尊敬的企财宝用户，您于2018年4月20日提交的企业认证申请（企业名称："+qca.getName()+"）已审核通过，请您重新登录企财宝平台查看详情。";
		} else if (QcbCompanyOperateStatus.UNPASSED.equals(qco.getStatus())) {
			if(!QcbCompanyAccountStatus.NORMAL.equals(qca.getStatus())){
				qca.setStatus(QcbCompanyAccountStatus.NOPASS);
			}
			qca.setAuthStatus(QcbCompanyAccountAuthStatus.NOPASS);
			content = "尊敬的企财宝用户，您于2018年4月20日提交的企业认证申请（企业名称："+qca.getName()+"）审核未通过，请您重新登录企财宝平台查看未通过原因并修改认证资料后再次提交。";
		}
		
		this.qcbCompanyOperateDAO.update(qco);
		this.qcbCompanyAccountDAO.update(qca);
		
		MobileCode mc = new MobileCode();
		String mobile = qca.getAccreditMobile();
		String codeInfo = Utlity.getCaptcha();
		mc.setCode(codeInfo);
		mc.setContent(content);
		mc.setMobile(mobile);
		mc.setCreator(qco.getChecker());
		mc.setStatus(MobileCodeStatus.DISABLE);
		mc.setType(MobileCodeTypes.NOTICE);
		mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
		mc.setCreatorType(MobileCodeCreatorType.ADMIN);
		mc.setUuid(UUID.randomUUID().toString());
		this.insertSmsInfo(mc);
	}
	
	public void insertSmsInfo(MobileCode mc) throws TransactionException {
		boolean flag = true;
		String phone = mc.getMobile();
		String content = mc.getContent();
		String result = SendSms.sendSms4Qcb(content, phone);
		if (result != null && !"0".equals(result.split("_")[0])) {
			flag = false;
		}
		if(flag){
			this.mobileCodeDAO.insert(mc);
		}
	}
}
