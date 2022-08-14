/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.jgroups.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountHistoryDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IMobileCodeDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IPlatformAccountDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IQcbCompanyHistoryOperateDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IQcbCompanyHistoryOperateService;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.PlatformAccount;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.PlatformAccount.PlatformAccountUuid;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.QcbCompanyHistoryOperate;
import cn.zeppin.product.ntb.core.entity.QcbCompanyHistoryOperate.QcbCompanyHistoryOperateStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyHistoryOperate.QcbCompanyHistoryOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountHistoryDAO;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.SendSms;
import cn.zeppin.product.utility.Utlity;

/**
 *
 */
@Service
public class QcbCompanyHistoryOperateService extends BaseService implements IQcbCompanyHistoryOperateService {

	@Autowired
	private ICompanyAccountDAO companyAccountDAO;
	
	@Autowired
	private IPlatformAccountDAO platformAccountDAO;
	
	@Autowired
	private IQcbCompanyAccountDAO qcbCompanyAccountDAO;
	
	@Autowired
	private ICompanyAccountHistoryDAO companyAccountHistoryDAO;
	
	@Autowired
	private IQcbCompanyAccountHistoryDAO qcbCompanyAccountHistoryDAO;
	
	@Autowired
	private IQcbCompanyHistoryOperateDAO qcbCompanyHistoryOperateDAO;
	
	@Autowired
	private IMobileCodeDAO mobileCodeDAO;
	
	@Override
	public QcbCompanyHistoryOperate insert(QcbCompanyHistoryOperate qcbCompanyHistoryOperate) {
		return qcbCompanyHistoryOperateDAO.insert(qcbCompanyHistoryOperate);
	}

	@Override
	public QcbCompanyHistoryOperate delete(QcbCompanyHistoryOperate qcbCompanyHistoryOperate) {
		return qcbCompanyHistoryOperateDAO.delete(qcbCompanyHistoryOperate);
	}

	@Override
	public QcbCompanyHistoryOperate update(QcbCompanyHistoryOperate qcbCompanyHistoryOperate) {
		return qcbCompanyHistoryOperateDAO.update(qcbCompanyHistoryOperate);
	}

	@Override
	public QcbCompanyHistoryOperate get(String uuid) {
		return qcbCompanyHistoryOperateDAO.get(uuid);
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
		return qcbCompanyHistoryOperateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbCompanyHistoryOperateDAO.getCount(inputParams);
	}
	
	/**
	 * 审核
	 * @param CompanyAccountOperate
	 * @return result
	 * @throws TransactionException 
	 */
	public void check(QcbCompanyHistoryOperate caho) throws TransactionException {
		//审核通过更新操作数据
		if(QcbCompanyHistoryOperateStatus.CHECKED.equals(caho.getStatus())){
			QcbCompanyAccountHistory cah = JSONUtils.json2obj(caho.getValue(), QcbCompanyAccountHistory.class);
			
			if(QcbCompanyHistoryOperateType.RECHARGE.equals(caho.getType())){
				//充值
				if(cah.getIncome().compareTo(BigDecimal.ZERO) <= 0){
					throw new TransactionException("交易金额应大于0！");
				}
				
				//添加一条交易流水（公司账户）
				CompanyAccountHistory cat = new CompanyAccountHistory();
				cat.setUuid(UUID.randomUUID().toString());
				cat.setCompanyAccountIn(cah.getCompanyAccount());//用户充值
				cat.setType(CompanyAccountHistoryType.QCB_RECHARGE);
				cat.setQcbCompanyAccount(cah.getQcbCompany());
				cat.setQcbCompanyAccountHistory(cah.getUuid());
				cat.setTotalAmount(cah.getIncome());
				cat.setPoundage(cah.getPoundage());
				cat.setPurpose("企业充值");
				cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
				
				cat.setCreator(cah.getQcbCompany());
				cat.setCreatetime(new Timestamp(System.currentTimeMillis()));

				
				CompanyAccount ca = companyAccountDAO.get(cah.getCompanyAccount());
				ca.setAccountBalance(ca.getAccountBalance().add(cah.getIncome()));
				
				//20180622增加记录本次余额信息
				cat.setAccountBalance(ca.getAccountBalance());
				
				this.companyAccountHistoryDAO.insert(cat);
				
				companyAccountDAO.update(ca);
				
				QcbCompanyAccount qca = this.qcbCompanyAccountDAO.get(cah.getQcbCompany());
				qca.setAccountBalance(qca.getAccountBalance().add(cah.getIncome()));
				qca.setAccountPay(qca.getAccountPay().add(cah.getIncome()));//确认后需要修改逻辑
				this.qcbCompanyAccountDAO.update(qca);
				cah.setAccountBalance(qca.getAccountBalance());
				
				PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
				pa.setTotalAmount(pa.getTotalAmount().add(cah.getIncome()));
				platformAccountDAO.update(pa);
				
				//增加短信通知
				MobileCode mc = new MobileCode();
				String content = "尊敬的企财宝用户您好：您本次申请的"+Utlity.numFormat4UnitDetailLess(cah.getIncome())+"元充值已处理成功，请注意查询。";
				String mobile = qca.getAccreditMobile();
				String codeInfo = Utlity.getCaptcha();
				mc.setCode(codeInfo);
				mc.setContent(content);
				mc.setMobile(mobile);
				mc.setCreator(caho.getChecker());
				mc.setStatus(MobileCodeStatus.DISABLE);
				mc.setType(MobileCodeTypes.NOTICE);
				mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
				mc.setCreatorType(MobileCodeCreatorType.ADMIN);
				mc.setUuid(UUID.randomUUID().toString());
				this.insertSmsInfo(mc);
			} else if (QcbCompanyHistoryOperateType.EXPEND.equals(caho.getType())) {
				//充值
				if(cah.getPay().compareTo(BigDecimal.ZERO) <= 0){
					throw new TransactionException("交易金额应大于0！");
				}
				QcbCompanyAccount qca = this.qcbCompanyAccountDAO.get(cah.getQcbCompany());
				qca.setAccountBalance(qca.getAccountBalance().subtract(cah.getPay()));
				qca.setAccountPay(qca.getAccountPay().subtract(cah.getPay()));//确认后需要修改逻辑
				this.qcbCompanyAccountDAO.update(qca);
				cah.setAccountBalance(qca.getAccountBalance());
				
				PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
				pa.setTotalAmount(pa.getTotalAmount().add(cah.getPay()));
				platformAccountDAO.update(pa);
			}
			qcbCompanyAccountHistoryDAO.insert(cah);
		}	
		if(QcbCompanyHistoryOperateStatus.CHECKED.equals(caho.getStatus()) && (caho.getReason() == null || "".equals(caho.getReason()))){
			caho.setReason("审核通过！");
		}else if(QcbCompanyHistoryOperateStatus.UNPASSED.equals(caho.getStatus()) && (caho.getReason() == null || "".equals(caho.getReason()))){
			caho.setReason("审核不通过！");
		}
		qcbCompanyHistoryOperateDAO.update(caho);
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
	
	/**
	 * 获取分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return qcbCompanyHistoryOperateDAO.getStatusList(inputParams, resultClass);
	}
	
	/**
	 * 获取分类型列表
	 * @param inputParams
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getTypeList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return qcbCompanyHistoryOperateDAO.getTypeList(inputParams, resultClass);
	}
}
