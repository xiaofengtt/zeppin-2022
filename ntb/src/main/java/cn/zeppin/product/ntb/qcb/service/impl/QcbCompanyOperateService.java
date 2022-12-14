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
	 * ??????????????????????????????(??????????????????),
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
	 * ??????????????????????????????
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbCompanyOperateDAO.getCount(inputParams);
	}
	
	/**
	 * ????????????????????????
	 * @param qcbCompany
	 * @return
	 */
	@Override
	public QcbCompanyOperate getByQcbCompany(String qcbCompany){
		return qcbCompanyOperateDAO.getByQcbCompany(qcbCompany);
	}
	
	/**
	 * ???????????????????????????
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
	 * ???????????????????????????
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
	 * ???????????????????????????
	 * @param qcbCompanyOperateCheck
	 * @return
	 */
	public void check(QcbCompanyOperate qco) throws TransactionException {
		QcbCompanyAccount qca = this.qcbCompanyAccountDAO.get(qco.getQcbCompany());
		if(qca == null){
			throw new TransactionException("?????????????????????????????????");
		}
		
//		if(QcbCompanyOperateCheckStatus.CHECKED.equals(qcoc.getStatus())){
//			qco.setStatus(QcbCompanyOperateStatus.CHECKED);
//			qca.setStatus(QcbCompanyAccountStatus.NORMAL);
//		}else if(QcbCompanyOperateCheckStatus.UNPASSED.equals(qcoc.getStatus())){
//			qco.setStatus(QcbCompanyOperateStatus.UNPASSED);
//			qca.setStatus(QcbCompanyAccountStatus.NOPASS);
//		}
		String content = "";
		if(QcbCompanyOperateStatus.CHECKED.equals(qco.getStatus())){//????????????
			if(!QcbCompanyAccountStatus.NORMAL.equals(qca.getStatus())){
				qca.setStatus(QcbCompanyAccountStatus.NORMAL);
			}
			qca.setAuthStatus(QcbCompanyAccountAuthStatus.NORMAL);
			QcbCompanyAccount newData = JSONUtils.json2obj(qco.getValue(), QcbCompanyAccount.class);
			//????????????
			qca.setName(newData.getName());
			qca.setBkArea(newData.getBkArea());
			qca.setAddress(newData.getAddress());
			qca.setPhone(newData.getPhone());
			qca.setCorporation(newData.getCorporation());
			qca.setConnectionCompany(newData.getConnectionCompany());
			qca.setLogo(newData.getLogo());
			
			//????????????????????????
			qca.setTaxIdentificationNum(newData.getTaxIdentificationNum());
			qca.setTaxAddress(newData.getTaxAddress());
			qca.setTaxCompany(newData.getTaxCompany());
			qca.setTaxPhone(newData.getTaxPhone());
			qca.setOpenBank(newData.getOpenBank());
			qca.setOpenBankCardnum(newData.getOpenBankCardnum());
			
			//???????????????????????????
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
					throw new TransactionException("???????????????????????????????????????");
				}
				
				QcbVirtualAccounts qva = (QcbVirtualAccounts)list.get(new Random().nextInt(list.size()));
				qva = this.qcbVirtualAccountsDAO.get(qva.getUuid());
				qva.setStatus(QcbVirtualAccountsStatus.USED);
				qva.setQcbCompany(qca.getUuid());
				qca.setQcbVirtualAccounts(qva.getUuid());
				qca.setVirtualAccountType(QcbCompanyAccountVirtualAccountType.VIRTUAL);
				this.qcbVirtualAccountsDAO.update(qva);
			}
			content = "?????????????????????????????????2018???4???20????????????????????????????????????????????????"+qca.getName()+"?????????????????????????????????????????????????????????????????????";
		} else if (QcbCompanyOperateStatus.UNPASSED.equals(qco.getStatus())) {
			if(!QcbCompanyAccountStatus.NORMAL.equals(qca.getStatus())){
				qca.setStatus(QcbCompanyAccountStatus.NOPASS);
			}
			qca.setAuthStatus(QcbCompanyAccountAuthStatus.NOPASS);
			content = "?????????????????????????????????2018???4???20????????????????????????????????????????????????"+qca.getName()+"??????????????????????????????????????????????????????????????????????????????????????????????????????????????????";
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
