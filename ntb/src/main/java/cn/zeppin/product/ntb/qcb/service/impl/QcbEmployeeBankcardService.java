/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeBankcardDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeBankcardService;
import cn.zeppin.product.ntb.qcb.utility.QcbMessageUtil;
import cn.zeppin.product.ntb.backadmin.dao.api.IBankDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IMobileCodeDAO;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard.QcbEmployeeBankcardStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.utility.SendSms;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 */
@Service
public class QcbEmployeeBankcardService extends BaseService implements IQcbEmployeeBankcardService {

	@Autowired
	private IQcbEmployeeBankcardDAO qcbEmployeeBankcardDAO;
	
	@Autowired
	private IQcbEmployeeDAO qcbEmployeeDAO;
	
	@Autowired
	private IBankDAO bankDAO;
	
	@Autowired
	private IMobileCodeDAO mobileCodeDAO;
	
	@Override
	public QcbEmployeeBankcard insert(QcbEmployeeBankcard qcbEmployeeBankcard) {
		return qcbEmployeeBankcardDAO.insert(qcbEmployeeBankcard);
	}

	@Override
	public QcbEmployeeBankcard delete(QcbEmployeeBankcard qcbEmployeeBankcard) {
		qcbEmployeeBankcard.setStatus(QcbEmployeeBankcardStatus.DELETED);
		return qcbEmployeeBankcardDAO.update(qcbEmployeeBankcard);
	}

	@Override
	public QcbEmployeeBankcard update(QcbEmployeeBankcard qcbEmployeeBankcard) {
		return qcbEmployeeBankcardDAO.update(qcbEmployeeBankcard);
	}

	@Override
	public QcbEmployeeBankcard get(String uuid) {
		return qcbEmployeeBankcardDAO.get(uuid);
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
		return qcbEmployeeBankcardDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * ??????????????????????????????
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbEmployeeBankcardDAO.getCount(inputParams);
	}

	/**
	 * ?????????????????????
	 * @param qeb
	 * @return
	 */
	@Override
	public void sendCreditRemind(QcbEmployeeBankcard qeb) throws Exception{
		QcbEmployee qe = this.qcbEmployeeDAO.get(qeb.getQcbEmployee());
		Bank b= this.bankDAO.get(qeb.getBank());
		String creditcard = qeb.getBindingBankCard().substring(qeb.getBindingBankCard().length()-4, qeb.getBindingBankCard().length());
		
		if(!Utlity.checkStringNull(qe.getOpenid())){
			QcbMessageUtil.creditcardRemindTemplate(qe.getOpenid(), creditcard, b.getName(), qeb.getRemindTime().toString());
		}
		
		MobileCode mc = new MobileCode();
		String content = "???????????????????????????????????????" + b.getName() + "????????????" + creditcard + "????????????????????????" + qeb.getRemindTime() + "??????????????????,????????????????????????????????????????????????!";
		String mobile = qe.getMobile();
		String codeInfo = Utlity.getCaptcha();
		mc.setCode(codeInfo);
		mc.setContent(content);
		mc.setMobile(mobile);
		mc.setCreator(qe.getUuid());
		mc.setStatus(MobileCodeStatus.DISABLE);
		mc.setType(MobileCodeTypes.NOTICE);
		mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
		mc.setCreatorType(MobileCodeCreatorType.QCB_EMP);
		mc.setUuid(UUID.randomUUID().toString());
		
		String result = SendSms.sendSms4Qcb(content , qe.getMobile());
		if (!"0".equals(result.split("_")[0])) {
			throw new Exception();
		}
		
		this.mobileCodeDAO.insert(mc);
	}
}
