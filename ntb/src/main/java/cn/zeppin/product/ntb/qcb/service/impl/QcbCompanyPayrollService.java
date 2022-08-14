/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IMobileCodeDAO;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayroll;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayroll.QcbCompanyPayrollStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollRecords;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollRecords.QcbCompanyPayrollRecordsStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountHistoryDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyPayrollDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyPayrollRecordsDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeHistoryDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyPayrollService;
import cn.zeppin.product.ntb.qcb.utility.QcbMessageUtil;
import cn.zeppin.product.utility.SendSms;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 */
@Service
public class QcbCompanyPayrollService extends BaseService implements IQcbCompanyPayrollService {

	@Autowired
	private IQcbCompanyPayrollDAO qcbCompanyPayrollDAO;
	
	@Autowired
	private IQcbCompanyPayrollRecordsDAO qcbCompanyPayrollRecordsDAO;
	
	@Autowired
	private IQcbCompanyAccountDAO qcbCompanyAccountDAO;
	
	@Autowired
	private IQcbCompanyAccountHistoryDAO qcbCompanyAccountHistoryDAO;
	
	@Autowired
	private IQcbEmployeeDAO qcbEmployeeDAO;
	
	@Autowired
	private IQcbEmployeeHistoryDAO qcbEmployeeHistoryDAO;
	
	@Autowired
	private IMobileCodeDAO mobileCodeDAO;
	
	@Override
	public QcbCompanyPayroll insert(QcbCompanyPayroll qcbCompanyPayroll) {
		return qcbCompanyPayrollDAO.insert(qcbCompanyPayroll);
	}

	@Override
	public QcbCompanyPayroll delete(QcbCompanyPayroll qcbCompanyPayroll) {
		qcbCompanyPayroll.setStatus(QcbCompanyPayrollStatus.DELETED);
		return qcbCompanyPayrollDAO.update(qcbCompanyPayroll);
	}

	@Override
	public QcbCompanyPayroll update(QcbCompanyPayroll qcbCompanyPayroll) {
		return qcbCompanyPayrollDAO.update(qcbCompanyPayroll);
	}

	@Override
	public QcbCompanyPayroll get(String uuid) {
		return qcbCompanyPayrollDAO.get(uuid);
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
		return qcbCompanyPayrollDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbCompanyPayrollDAO.getCount(inputParams);
	}
	
	/**
	 * 插入薪资发放记录
	 * @param qcbCompanyPayroll
	 * @param qcbCompanyPayrollRecordsList
	 */
	public void insert(QcbCompanyPayroll qcp, List<QcbCompanyPayrollRecords> qcprList) throws TransactionException {
		this.qcbCompanyPayrollDAO.insert(qcp);
		for(QcbCompanyPayrollRecords qcpr : qcprList){
			this.qcbCompanyPayrollRecordsDAO.insert(qcpr);
		}
	}
	
	/**
	 * 发放福利金
	 * @param uuid
	 * @throws Exception 
	 */
	public void tryPay(String uuid) throws Exception{
		QcbCompanyPayroll qcp = this.qcbCompanyPayrollDAO.get(uuid);
		
		if(!QcbCompanyPayrollStatus.SUBMIT.equals(qcp.getStatus())){
			return;
		}
		
		QcbCompanyAccount qca = this.qcbCompanyAccountDAO.get(qcp.getQcbCompany());
		if(qca == null){
			throw new Exception();
		}
		
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("qcbCompanyPayroll", qcp.getUuid());
		searchMap.put("status", QcbCompanyPayrollRecordsStatus.DRAFT);
		
		List<Entity> list= this.qcbCompanyPayrollRecordsDAO.getListForPage(searchMap, -1, -1, null, QcbCompanyPayrollRecords.class);
		if(list == null || list.size() == 0){
			throw new Exception();
		}
		
		BigDecimal totalPrice = BigDecimal.ZERO;
		String mobileStr = "";
		List<QcbCompanyPayrollRecords> qcprList = new ArrayList<QcbCompanyPayrollRecords>();
		List<QcbEmployee> qeList = new ArrayList<QcbEmployee>();
		List<QcbEmployeeHistory> qehList = new ArrayList<QcbEmployeeHistory>();
		List<MobileCode> mcList = new ArrayList<MobileCode>();
		List<String[]> templateList = new ArrayList<String[]>();
		
		for(Entity en: list){
			QcbCompanyPayrollRecords enQcpr = (QcbCompanyPayrollRecords) en;
			
			QcbCompanyPayrollRecords qcpr = this.qcbCompanyPayrollRecordsDAO.get(enQcpr.getUuid());
			QcbEmployee qe = this.qcbEmployeeDAO.get(qcpr.getQcbEmployee());
			
			totalPrice = totalPrice.add(qcpr.getPrice());
			
			qe.setAccountBalance(qe.getAccountBalance().add(qcpr.getPrice()));
			qcpr.setStatus(QcbCompanyPayrollRecordsStatus.SUCCESS);
			
			QcbEmployeeHistory qeh = new QcbEmployeeHistory();
			qeh.setUuid(UUID.randomUUID().toString());
			qeh.setQcbEmployee(qe.getUuid());
			//校验订单号是否存在
			String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_QCB,Utlity.BILL_PAYTYPE_NTLC,Utlity.BILL_PURPOSE_NTLC);
			if(this.qcbEmployeeHistoryDAO.checkOrderNum(orderNumStr)){
				throw new Exception();
			}
			qeh.setOrderNum(orderNumStr);
			qeh.setOrderType(QcbEmployeeHistoryOrderType.PAY_TYPE_QCB_COMPANY);
			qeh.setIncome(qcpr.getPrice());
			qeh.setPay(BigDecimal.ZERO);
			qeh.setAccountBalance(qe.getAccountBalance());
			qeh.setType(QcbEmployeeHistoryType.PAYROLL);
			qeh.setQcbCompany(qcpr.getQcbCompany());
			qeh.setQcbCompanyPayrollRecords(qcpr.getUuid());
			qeh.setPoundage(BigDecimal.ZERO);
			qeh.setStatus(QcbEmployeeHistoryStatus.SUCCESS);
			qeh.setCreatetime(new Timestamp(System.currentTimeMillis()));
			if(qcp.getFlagSms()){
				MobileCode mc = new MobileCode();
				String content = "尊敬的用户您好：" + qca.getName() + "于企财宝内发放的" + qcp.getTitle() + "，已处理成功，请注意查询。";
				String mobile = qe.getMobile();
				String codeInfo = Utlity.getCaptcha();
				mc.setCode(codeInfo);
				mc.setContent(content);
				mc.setMobile(mobile);
				mc.setCreator(qcp.getCreator());
				mc.setStatus(MobileCodeStatus.DISABLE);
				mc.setType(MobileCodeTypes.NOTICE);
				mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
				mc.setCreatorType(MobileCodeCreatorType.QCB_ADMIN);
				mc.setUuid(UUID.randomUUID().toString());
				
				mobileStr = mobileStr + qe.getMobile() + ",";
				mcList.add(mc);
			}
			qeList.add(qe);
			qcprList.add(qcpr);
			qehList.add(qeh);
			if(!Utlity.checkStringNull(qe.getOpenid())){
				String[] templateStr= new String[6];
				templateStr[0] = qe.getOpenid();
				templateStr[1] = Utlity.numFormat4UnitDetailLess(qeh.getIncome());
				templateStr[2] = Utlity.timeSpanToStringLess(qcp.getPayTime());
				templateStr[3] = qcp.getTitle();
				templateStr[4] = qcp.getRemark();
				//2018/06/06  新增福利金 详情的uuid
				templateStr[5] = qcpr.getUuid();
				templateList.add(templateStr);
			}
		}
		
		if(qca.getAccountPay().compareTo(totalPrice) < 0){
			throw new Exception();
		}
		
		qca.setAccountBalance(qca.getAccountBalance().subtract(totalPrice));
		qca.setAccountPay(qca.getAccountPay().subtract(totalPrice));
		
		QcbCompanyAccountHistory qcah = new QcbCompanyAccountHistory();
		qcah.setUuid(UUID.randomUUID().toString());
		qcah.setQcbCompany(qcp.getQcbCompany());
		
		String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_QCB,Utlity.BILL_PAYTYPE_NTLC,Utlity.BILL_PURPOSE_NTLC);
		if(this.qcbCompanyAccountHistoryDAO.checkOrderNum(orderNumStr)){
			throw new Exception();
		}
		qcah.setOrderNum(orderNumStr);
		qcah.setOrderType(QcbCompanyAccountHistoryOrderType.PAY_TYPE_QCB_COMPANY);
		qcah.setType(QcbCompanyAccountHistoryType.PAYROLL);
		qcah.setPay(totalPrice);
		qcah.setIncome(BigDecimal.ZERO);
		qcah.setAccountBalance(qca.getAccountBalance());
		qcah.setPoundage(BigDecimal.ZERO);
		qcah.setRemark(qcp.getTitle());
		qcah.setStatus(QcbCompanyAccountHistoryStatus.SUCCESS);
		qcah.setCreator(qcp.getCreator());
		qcah.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		String result = "0";
		if(qcp.getFlagSms()){
			result = SendSms.sendSms4Qcb("尊敬的用户您好：" + qca.getName() + "于企财宝内发放的" + qcp.getTitle() + "，已处理成功，请注意查询。", mobileStr.substring(0,mobileStr.length() - 1));
		}
		
		qcp.setStatus(QcbCompanyPayrollStatus.SUCCESS);
		
		for(QcbCompanyPayrollRecords qcpr : qcprList){
			this.qcbCompanyPayrollRecordsDAO.update(qcpr);
		}
		for(QcbEmployee qe : qeList){
			this.qcbEmployeeDAO.update(qe);
		}
		for(QcbEmployeeHistory qeh : qehList){
			this.qcbEmployeeHistoryDAO.insert(qeh);
		}
		if(qcp.getFlagSms()){
			for(MobileCode mc : mcList){
				if(!"0".equals(result.split("_")[0])){
					mc.setContent(mc.getContent() + "(" + result + ")");
				}
				this.mobileCodeDAO.insert(mc);
			}
		}
		this.qcbCompanyPayrollDAO.update(qcp);
		this.qcbCompanyAccountHistoryDAO.insert(qcah);
		this.qcbCompanyAccountDAO.update(qca);
		for(String[] str : templateList){
			QcbMessageUtil.employeesPayTemplate(str[0], str[1], str[2], str[3], str[4],str[5]);
		}
	}
}
