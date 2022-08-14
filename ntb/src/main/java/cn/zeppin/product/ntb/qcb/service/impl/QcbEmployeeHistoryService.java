/**
 * 
 */
package cn.zeppin.product.ntb.qcb.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductPublishDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountHistoryDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICouponStrategyDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IMobileCodeDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IPlatformAccountDAO;
import cn.zeppin.product.ntb.backadmin.vo.CouponLessVO;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.CouponStrategy;
import cn.zeppin.product.ntb.core.entity.CouponStrategy.CouponStrategyStatus;
import cn.zeppin.product.ntb.core.entity.CouponStrategy.CouponStrategyUuid;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.PlatformAccount;
import cn.zeppin.product.ntb.core.entity.PlatformAccount.PlatformAccountUuid;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyEmployee;
import cn.zeppin.product.ntb.core.entity.QcbCompanyEmployee.QcbCompanyEmployeeStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeCoupon;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeCoupon.QcbEmployeeCouponStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeCouponHistory;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeCouponHistory.QcbEmployeeCouponHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryProcessStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuyAgreement;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty.QcbOrderinfoByThirdpartyAccountType;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty.QcbOrderinfoByThirdpartyReturnStatus;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty.QcbOrderinfoByThirdpartyType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyEmployeeDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeBankcardDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeCouponDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeCouponHistoryDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeHistoryDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeProductBuyAgreementDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeProductBuyDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbOrderinfoByThirdpartyDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeHistoryService;
import cn.zeppin.product.ntb.qcb.utility.QcbMessageUtil;
import cn.zeppin.product.utility.DataTimeConvert;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.SendSms;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.reapal.ReapalUtlity;
import cn.zeppin.product.utility.reapal.data.WithdrawData;
import cn.zeppin.product.utility.reapal.data.WithdrawData.WithdrawDateCurrency;
import cn.zeppin.product.utility.reapal.data.WithdrawData.WithdrawDateProperties;
import cn.zeppin.product.utility.reapal.data.WithdrawDataArray;

/**
 * @author hehe
 *
 */
@Service
public class QcbEmployeeHistoryService extends BaseService implements IQcbEmployeeHistoryService {

	@Autowired
	private IQcbEmployeeHistoryDAO qcbEmployeeHistoryDAO;
	
	@Autowired
	private IQcbEmployeeDAO qcbEmployeeDAO;
	
	@Autowired
	private IQcbCompanyAccountDAO qcbCompanyAccountDAO;
	
	@Autowired
	private IQcbCompanyEmployeeDAO qcbCompanyEmployeeDAO;
	
	@Autowired
	private IQcbEmployeeProductBuyDAO qcbEmployeeProductBuyDAO;
	
	@Autowired
	private IQcbEmployeeProductBuyAgreementDAO qcbEmployeeProductBuyAgreementDAO;
	
	@Autowired
	private IQcbEmployeeBankcardDAO qcbEmployeeBankcardDAO;
	
	@Autowired
	private IQcbEmployeeCouponDAO qcbEmployeeCouponDAO;
	
	@Autowired
	private IQcbEmployeeCouponHistoryDAO qcbEmployeeCouponHistoryDAO;
	
	@Autowired
	private ICouponStrategyDAO couponStrategyDAO;
	
	@Autowired
	private IQcbOrderinfoByThirdpartyDAO qcbOrderinfoByThirdpartyDAO;
	
	@Autowired
	private IBankFinancialProductPublishDAO bankFinancialProductPublishDAO;
	
	@Autowired
	private ICompanyAccountDAO companyAccountDAO;
	
	@Autowired
	private ICompanyAccountHistoryDAO companyAccountHistoryDAO;
	
	@Autowired
	private IPlatformAccountDAO platformAccountDAO;
	
	@Autowired
	private IBankDAO bankDAO;
	
	@Autowired
	private IMobileCodeDAO mobileCodeDAO;
	
	@Override
	public QcbEmployeeHistory insert(QcbEmployeeHistory qcbEmployeeHistory) {
		return qcbEmployeeHistoryDAO.insert(qcbEmployeeHistory);
	}

	@Override
	public QcbEmployeeHistory delete(QcbEmployeeHistory qcbEmployeeHistory) {
		return qcbEmployeeHistoryDAO.delete(qcbEmployeeHistory);
	}

	@Override
	public QcbEmployeeHistory update(QcbEmployeeHistory qcbEmployeeHistory) {
		return qcbEmployeeHistoryDAO.update(qcbEmployeeHistory);
	}

	@Override
	public QcbEmployeeHistory get(String uuid) {
		return qcbEmployeeHistoryDAO.get(uuid);
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
		return qcbEmployeeHistoryDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询提现结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForWithdrawPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return qcbEmployeeHistoryDAO.getListForWithdrawPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}
	
	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbEmployeeHistoryDAO.getCount(inputParams);
	}
	
	/**
	 * 校验订单号是否已存在
	 * @param orderNum
	 * @return
	 */
	@Override
	public Boolean checkOrderNum(String orderNum) {
		return qcbEmployeeHistoryDAO.checkOrderNum(orderNum);
	}
	
	/**
	 * 用户提现
	 * @param qcbEmployee
	 * @param qcbOrderinfoByThirdparty
	 * @param qcbEmployeeHistory
	 * @return
	 */
	public void insertWithdraw(QcbEmployee qe, QcbOrderinfoByThirdparty obt, QcbEmployeeHistory qeh){
		this.qcbOrderinfoByThirdpartyDAO.insert(obt);
		this.qcbEmployeeHistoryDAO.insert(qeh);
		this.qcbEmployeeDAO.update(qe);
		
		String name = qe.getRealname();
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbEmployee", qe.getUuid());
		inputParams.put("status", QcbCompanyEmployeeStatus.NORMAL);
		List<Entity> list = this.qcbCompanyEmployeeDAO.getListForPage(inputParams, -1, -1, null, QcbCompanyEmployee.class);
		if(list != null && list.size() > 0){
			QcbCompanyEmployee qce = (QcbCompanyEmployee) list.get(0);
			QcbCompanyAccount qca = this.qcbCompanyAccountDAO.get(qce.getQcbCompany());
			if(qca != null){
				name = qca.getName() + "员工" + qe.getRealname();	
			}
		}
		
		QcbMessageUtil.withdrawNoticeToAdmin(qeh.getOrderNum(), name, Utlity.numFormat4UnitDetail(qeh.getPay()), qeh.getUuid(), "qcbEmployee");
	}
	
	@Override
	public HashMap<String, Object> insertReapalNotice4Pay(Map<String, Object> map) 
			throws ParseException, TransactionException, NumberFormatException, Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		String data = map.get("data") == null ? "" : map.get("data").toString();
		if(!Utlity.checkStringNull(data)){
			String resultArr[] = data.split(",");
			String status = "";
			if("成功".equals(resultArr[13])){
				status = QcbOrderinfoByThirdpartyReturnStatus.SUCCESS;
			} else {
				status = QcbOrderinfoByThirdpartyReturnStatus.FAIL;
			}
			String reason = "";
			if(resultArr.length > 14){
				reason = resultArr[14];
			}
			
			String orderNum = resultArr[12];
			String fee = resultArr[9];
			
			BigDecimal total_fee = BigDecimal.valueOf(Double.parseDouble(fee));
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("orderNum", orderNum);
			List<Entity> list = this.qcbOrderinfoByThirdpartyDAO.getListForPage(inputParams, -1, -1, null, QcbOrderinfoByThirdparty.class);
			QcbOrderinfoByThirdparty obt = null;
			if(list != null && list.size() > 0){
				obt = (QcbOrderinfoByThirdparty) list.get(0);
				if(!QcbOrderinfoByThirdpartyReturnStatus.SUCCESS.equals(obt.getStatus())){
					obt.setPaytime(new Timestamp(System.currentTimeMillis()));
					obt.setStatus(status);
					obt.setErrCodeDes(reason == null ? "" : reason);
					obt.setMessage(reason == null ? "" : reason);
					this.qcbOrderinfoByThirdpartyDAO.update(obt);
				}
			} else {
				message = "订单未找到";
				throw new TransactionException(message);
			}
			
			inputParams.clear();
			inputParams.put("order", obt.getUuid());
			inputParams.put("qcbEmployee", obt.getAccount());
			inputParams.put("orderType", QcbEmployeeHistoryOrderType.PAY_TYPE_REAPAL);
			inputParams.put("type", QcbEmployeeHistoryType.WITHDRAW);
			List<Entity> listHistory = this.qcbEmployeeHistoryDAO.getListForPage(inputParams, -1, -1, null, QcbEmployeeHistory.class);
			if(listHistory != null && listHistory.size() > 0){
				QcbEmployeeHistory qeh = (QcbEmployeeHistory) listHistory.get(0);
				if(qeh == null){
					message = "订单不存在！"; 
					throw new TransactionException(message);
				}
				qeh = this.qcbEmployeeHistoryDAO.get(qeh.getUuid());
				
				QcbEmployee qe = this.qcbEmployeeDAO.get(qeh.getQcbEmployee());
				if(!QcbOrderinfoByThirdpartyReturnStatus.SUCCESS.equals(status)){//不是成功状态 算支付失败 进入人工付款流程
					if(QcbOrderinfoByThirdpartyReturnStatus.FAIL.equals(status) || QcbOrderinfoByThirdpartyReturnStatus.RETURN_TICKET.equals(status)){
						qeh.setProcessingStatus(QcbEmployeeHistoryProcessStatus.UNPROCESS);//接口调用失败 进入人工处理流程，设置处理状态为未处理
						qeh.setStatus(QcbEmployeeHistoryStatus.TRANSACTING);
						this.qcbEmployeeHistoryDAO.update(qeh);
						
						message = "OK";
						resultFlag = true;
						result.put("result", resultFlag);
						result.put("message", message);
						return result;
					} else if (QcbOrderinfoByThirdpartyReturnStatus.WITHDRAWAL_SUBMITTED.equals(status)) {
						qeh.setStatus(QcbEmployeeHistoryStatus.TRANSACTING);
						message = "交易进行中"; 
						throw new TransactionException(message);
					}
				}
				
				if(!QcbEmployeeHistoryStatus.SUCCESS.equals(qeh.getStatus())){
					if(QcbOrderinfoByThirdpartyReturnStatus.SUCCESS.equals(status)){
						qeh.setStatus(QcbEmployeeHistoryStatus.SUCCESS);
						qeh.setProcessCreatetime(new Timestamp(System.currentTimeMillis()));
						if(QcbEmployeeHistoryType.WITHDRAW.equals(qeh.getType())){
							//更新企业账户余额
							CompanyAccount ca = this.companyAccountDAO.get(qeh.getCompanyAccount());
							PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
							if(ca == null){
								message = "账户信息错误";
								throw new TransactionException(message);
							}
							
							BigDecimal accountTotal = ca.getAccountBalance();
							accountTotal = accountTotal.subtract(total_fee);
							
							BigDecimal totalAmount = pa.getTotalAmount();
							totalAmount = totalAmount.subtract(total_fee);
							
							//20180228 更新用户提现手续费扣除用户余额
							if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
								accountTotal = accountTotal.subtract(qeh.getPoundage());
								totalAmount = totalAmount.subtract(qeh.getPoundage());
							}
							
							ca.setAccountBalance(accountTotal);
							//更新系统总帐户余额
							pa.setTotalAmount(totalAmount);
							
							//增加公司账户交易记录--用户提现
							CompanyAccountHistory cat = new CompanyAccountHistory();
							cat.setUuid(UUID.randomUUID().toString());
							cat.setCompanyAccountOut(qeh.getCompanyAccount());//用户提现
							cat.setType(CompanyAccountHistoryType.EMP_TAKEOUT);
							cat.setQcbEmployee(qe.getUuid());
							cat.setTotalAmount(total_fee.add(qeh.getPoundage()));
							cat.setPoundage(qeh.getPoundage());
							cat.setPurpose("用户提现");
							cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
							
							cat.setCreator(qe.getUuid());
							cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
							
							cat.setQcbEmployeeHistory(qeh.getUuid());
							
							//20180622增加记录本次余额信息
							cat.setAccountBalance(ca.getAccountBalance());
							
							this.companyAccountDAO.update(ca);
							this.platformAccountDAO.update(pa);
							this.companyAccountHistoryDAO.insert(cat);
							//提现成功，发送通知短信
							MobileCode mc = new MobileCode();
							String content = "尊敬的用户您好：您于"+Utlity.timeSpanToChinaDateString(qeh.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(qeh.getPay())+"元提现申请已处理成功，请注意查询。";
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
							this.insertSmsInfo(mc);
							
							if(!Utlity.checkStringNull(qe.getOpenid())){
								QcbEmployeeBankcard qeb = this.qcbEmployeeBankcardDAO.get(qeh.getBankcard());
								if(qeb != null){
									Bank b = this.bankDAO.get(qeb.getBank());
									String bankcard = qeb.getBindingBankCard();
									QcbMessageUtil.withdrawApplyTemplate(qe.getOpenid(), Utlity.numFormat4UnitDetail(qeh.getPay()), 
											Utlity.timeSpanToString(new Timestamp(System.currentTimeMillis())), 
											b.getShortName() + "尾号(" + bankcard.substring(bankcard.length() - 4, bankcard.length()) + ")");
								}
							}
						} else {
							message = "账单类型错误";
							throw new TransactionException(message);
						}
					} else {
						message = "状态错误";
						throw new TransactionException(message);
					}
					this.qcbEmployeeHistoryDAO.update(qeh);
				}else{
					message = "账单错误";
					throw new TransactionException(message);
				}
			} else {
				message = "账单错误";
				throw new TransactionException(message);
			}
			System.out.println("成功");
			result.put("result", resultFlag);
			result.put("message", message);
			return result;
		} else {
			message = "发生错误";
			throw new TransactionException(message);
		}
	}
	
	@Override
	public void insertSmsInfo(MobileCode mc) throws TransactionException {
		String phone = mc.getMobile();
		String content = mc.getContent();
		String result = SendSms.sendSms4Qcb(content, phone);
		if (result != null && "0".equals(result.split("_")[0])) {
			this.mobileCodeDAO.insert(mc);
		} else {
			throw new TransactionException("短信验证码发送失败！");
		}
	}
	
	/**
	 * 企财宝员工提现状态列表
	 * @param searchMap
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getWithdrawStatusList(Map<String, String> searchMap, Class<? extends Entity> resultClass){
		return this.qcbEmployeeHistoryDAO.getWithdrawStatusList(searchMap, resultClass);
	}
	
	/**
	 * 企财宝员工提现批量重试
	 * @param qehList
	 * @return
	 */
	@Override
	public HashMap<String, Object> updateWithdrawBatch(List<QcbEmployeeHistory> qehList) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		Integer count = 0;
		for(QcbEmployeeHistory qeh : qehList){
			QcbEmployeeBankcard qeb = this.qcbEmployeeBankcardDAO.get(qeh.getBankcard());
			String bankcardnum = qeb.getBindingBankCard().split("_#")[0];
			Bank bank = this.bankDAO.get(qeb.getBank());
			QcbEmployee qe = this.qcbEmployeeDAO.get(qeh.getQcbEmployee());
			String orderNumStr = Utlity.getOrderNumStr(qeh.getOrderNum().substring(0, 1),Utlity.BILL_PAYTYPE_REAPAL,Utlity.BILL_PURPOSE_WITHDRAW);
			if(this.qcbEmployeeHistoryDAO.checkOrderNum(orderNumStr)){
				message = "订单编号生成失败，请稍后重试！";
				throw new TransactionException(message);
			}
			BigDecimal pay = qeh.getPay();
			QcbOrderinfoByThirdparty qobtN = new QcbOrderinfoByThirdparty();
			qobtN.setUuid(UUID.randomUUID().toString());
			qobtN.setType(QcbOrderinfoByThirdpartyType.REAPAL);
			qobtN.setAccount(qeh.getQcbEmployee());
			qobtN.setAccountType(QcbOrderinfoByThirdpartyAccountType.EMPLOYEE);
			qobtN.setOrderNum(orderNumStr);
			qobtN.setBody("融宝支付--用户提现");
			qobtN.setTotalFee(pay);
			qobtN.setPaySource(MD5.getMD5(qeh.getQcbEmployee()));
			
			Map<String, Object> resultBalance = ReapalUtlity.get_balance_Query();
			String balance = resultBalance.get("balance") == null ? "0" : resultBalance.get("balance").toString();
			BigDecimal accountTotal = BigDecimal.valueOf(Double.valueOf(balance));
			if(pay.add(qeh.getPoundage()).compareTo(accountTotal) == 1){
				qobtN.setStatus(QcbOrderinfoByThirdpartyReturnStatus.FAIL);
				qobtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
				qobtN.setErrCode("");
				qobtN.setErrCodeDes("提现账户余额不足！");
				qobtN.setMessage("提现账户余额不足！");
			} else {
				//银行付款接口调用 返回成功后
				WithdrawData wd = new WithdrawData(1, bankcardnum, qeb.getBindingCardCardholder(), bank.getName(), "", "", WithdrawDateProperties.PRIVATE, pay.setScale(2, BigDecimal.ROUND_HALF_UP), WithdrawDateCurrency.CNY, "", "", qeb.getBindingCardPhone(), "身份证", qe.getIdcard(), "", orderNumStr, "提现");
				
				List<WithdrawData> content = new ArrayList<WithdrawData>();
				content.add(wd);
				WithdrawDataArray wda = new WithdrawDataArray(content);
				wda.setBatch_no(orderNumStr);
				wda.setTrans_time(Utlity.timeSpanToString(qeh.getCreatetime()));
				
				Map<String, Object> result2 = ReapalUtlity.qcbEmpWithdrawBatchSubmit(wda);
				qobtN.setStatus(result2.get("result_code").toString());
				qobtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
				qobtN.setErrCode(result2.get("result_code") == null ? "" : result2.get("result_code").toString());
				qobtN.setErrCodeDes(result2.get("result_msg") == null ? "" : result2.get("result_msg").toString());
				qobtN.setMessage(result2.get("result_msg") == null ? "" : result2.get("result_msg").toString());
			}
			qeh.setOrderId(qobtN.getUuid());
			qeh.setOrderNum(orderNumStr);
			qeh.setStatus(QcbEmployeeHistoryStatus.TRANSACTING);
			if(!QcbOrderinfoByThirdpartyReturnStatus.REAPAL_CHECKED.equals(qobtN.getStatus()) && !QcbOrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(qobtN.getStatus())){
				qeh.setProcessingStatus(QcbEmployeeHistoryProcessStatus.UNPROCESS);//接口调用失败 进入人工处理流程，设置处理状态为未处理
				count++;
			} else {
				qeh.setProcessingStatus(QcbEmployeeHistoryProcessStatus.PROCESSED);
			}
			this.qcbOrderinfoByThirdpartyDAO.insert(qobtN);
			this.qcbEmployeeHistoryDAO.update(qeh);
		}
		result.put("result", resultFlag);
		result.put("message", message);
		result.put("count", count);
		return result;
	}
	
	/**
	 * 批量更新操作
	 */
	@Override
	public void updateBatch(List<QcbEmployeeHistory> listUpdate) {
		for(QcbEmployeeHistory qeh : listUpdate){
			this.qcbEmployeeHistoryDAO.update(qeh);
		}
	}
	
	@Override
	public void updateWithdraw(List<QcbEmployeeHistory> qehList) throws TransactionException, Exception {
		
		List<CompanyAccount> caList = new ArrayList<CompanyAccount>();
		List<CompanyAccountHistory> cahList = new ArrayList<CompanyAccountHistory>();
		Map<String, CompanyAccount> companyAccountMap = new HashMap<String, CompanyAccount>();
		PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
		PlatformAccount paf = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
		List<MobileCode> codeList = new ArrayList<MobileCode>();
		
		for(QcbEmployeeHistory qeh :qehList){
			CompanyAccount ca = null;
			if(companyAccountMap.containsKey(qeh.getProcessCompanyAccount())){
				ca = companyAccountMap.get(qeh.getProcessCompanyAccount());
			} else {
				ca = this.companyAccountDAO.get(qeh.getProcessCompanyAccount());
			}
			BigDecimal totalAmountf = paf.getTotalAmount();
			
			BigDecimal accountTotal = ca.getAccountBalance();
			accountTotal = accountTotal.subtract(qeh.getPay());
			
			BigDecimal totalAmount = pa.getTotalAmount();
			totalAmount = totalAmount.subtract(qeh.getPay());
			
			if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
				accountTotal = accountTotal.subtract(qeh.getPoundage());
				totalAmount = totalAmount.subtract(qeh.getPoundage());
				totalAmountf = totalAmountf.subtract(qeh.getPoundage());
			}
			
			ca.setAccountBalance(accountTotal);
			//更新系统总帐户余额
			pa.setTotalAmount(totalAmount);
			paf.setTotalAmount(totalAmountf);
			
			//增加公司账户交易记录--用户提现
			CompanyAccountHistory cat = new CompanyAccountHistory();
			cat.setUuid(UUID.randomUUID().toString());
			cat.setCompanyAccountOut(qeh.getProcessCompanyAccount());//用户提现
			cat.setType(CompanyAccountHistoryType.TAKEOUT);
			cat.setQcbEmployee(qeh.getQcbEmployee());
			cat.setTotalAmount(qeh.getPay());
			cat.setPoundage(qeh.getPoundage());
			cat.setPurpose("用户提现");
			cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
			
			cat.setCreator(qeh.getQcbEmployee());
			cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			cat.setQcbEmployeeHistory(qeh.getUuid());
			
			//20180622增加记录本次余额信息
			cat.setAccountBalance(ca.getAccountBalance().add(qeh.getPoundage()));
			
			if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
				//增加公司账户交易记录--费用录入（扣除交易手续费）
				CompanyAccountHistory catp = new CompanyAccountHistory();
				catp.setUuid(UUID.randomUUID().toString());
				catp.setCompanyAccountOut(qeh.getProcessCompanyAccount());//用户充值
				catp.setType(CompanyAccountHistoryType.EXPEND);
				catp.setQcbEmployee(qeh.getQcbEmployee());
				catp.setTotalAmount(qeh.getPoundage());
				catp.setPoundage(BigDecimal.ZERO);
				catp.setPurpose("用户提现--手续费扣除");
				catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
				
				catp.setCreator(qeh.getQcbEmployee());
				catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
				catp.setQcbEmployeeHistory(qeh.getUuid());
				catp.setRelated(cat.getUuid());
				cat.setRelated(catp.getUuid());
				
				//20180622增加记录本次余额信息
				catp.setAccountBalance(ca.getAccountBalance());
				
				cahList.add(catp);
			}
			companyAccountMap.put(ca.getUuid(), ca);
			cahList.add(cat);
			
			QcbEmployee qe = this.qcbEmployeeDAO.get(qeh.getQcbEmployee());
			if(qe == null){
				throw new TransactionException("该用户不存在！");
			}
			
			MobileCode mc = new MobileCode();
			String content = "尊敬的用户您好：您于"+Utlity.timeSpanToChinaDateString(qeh.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(qeh.getPay())+"元提现申请已处理成功，请注意查询。";
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
			codeList.add(mc);
		}
		
		if(!companyAccountMap.isEmpty()){
			for(CompanyAccount ca : companyAccountMap.values()){
				caList.add(ca);
			}
		}
		this.platformAccountDAO.update(pa);
		this.platformAccountDAO.update(paf);
		if(!qehList.isEmpty()){
			for(QcbEmployeeHistory qeh: qehList){
				this.qcbEmployeeHistoryDAO.update(qeh);
			}
		}
		if(!caList.isEmpty()){
			for(CompanyAccount ca: caList){
				this.companyAccountDAO.update(ca);
			}
		}
		
		if(!cahList.isEmpty()){
			for(CompanyAccountHistory cah: cahList){
				this.companyAccountHistoryDAO.insert(cah);
			}
		}
		if(!codeList.isEmpty()){
			for(MobileCode mc: codeList){
				Thread.sleep(10);
				this.insertSmsInfo(mc);
			}
		}
	}

	@Override
	public void insertProductBuy4Balance(MobileCode omc, BankFinancialProductPublish bfpp, QcbEmployee qe, QcbEmployeeHistory qeh, QcbEmployeeProductBuyAgreement qepba, QcbEmployeeProductBuy qepb, Boolean isUpdate, Boolean isUpdate2, MobileCode mc, QcbEmployeeCoupon qec) throws TransactionException {
		omc.setStatus(MobileCodeStatus.DISABLE);
		this.mobileCodeDAO.update(omc);
		
		this.bankFinancialProductPublishDAO.update(bfpp);
		this.qcbEmployeeDAO.update(qe);
		this.qcbEmployeeHistoryDAO.insert(qeh);
		
		if(isUpdate){
			this.qcbEmployeeProductBuyDAO.update(qepb);
			
		} else {
			this.qcbEmployeeProductBuyDAO.insert(qepb);
		}
		if(qcbEmployeeProductBuyAgreementDAO.getCheckScode(qepba.getScode())){
			throw new TransactionException("手速太快，服务器未响应！");
		}
		if(isUpdate2){
			this.qcbEmployeeProductBuyAgreementDAO.update(qepba);
		} else {
			this.qcbEmployeeProductBuyAgreementDAO.insert(qepba);
		}
//		this.qcbEmployeeInformationDAO.insert(iii);
		
		if(qec != null){
			this.qcbEmployeeCouponDAO.update(qec);
			QcbEmployeeCouponHistory qech = new QcbEmployeeCouponHistory();
			qech.setCoupon(qec.getCoupon());
			qech.setCreatetime(new Timestamp(System.currentTimeMillis()));
			qech.setCreator(qe.getUuid());
			qech.setPrice(qeh.getPay());
			qech.setStatus(QcbEmployeeCouponHistoryStatus.NORMAL);
			qech.setUuid(UUID.randomUUID().toString());
			qech.setQcbEmployeeProductBuy(qepb.getUuid());
			qech.setQcbEmployeeHistory(qeh.getUuid());
			qech.setDividend(BigDecimal.ZERO);
			this.qcbEmployeeCouponHistoryDAO.insert(qech);
		}
		this.checkCouponStrategy(qe);
		this.insertSmsInfo(mc);
	}
	
	private void checkCouponStrategy(QcbEmployee qe){
		//注册成功检查此处优惠券策略是否生效，将优惠券添加到用户账户中 同步生成应用内消息
		CouponStrategy cs = this.couponStrategyDAO.get(CouponStrategyUuid.INVESTED);
		if(cs != null && CouponStrategyStatus.OPEN.equals(cs.getStatus())){
			Timestamp now = new Timestamp(System.currentTimeMillis());
			Map<String, Object> coupon = JSONUtils.json2map(cs.getCoupon());
			List<CouponLessVO> list = JSONUtils.json2list(coupon.get("couponList").toString(), CouponLessVO.class);
			for(CouponLessVO vo : list){
				QcbEmployeeCoupon qec = new QcbEmployeeCoupon();
				qec.setUuid(UUID.randomUUID().toString());
				qec.setCoupon(vo.getUuid());
				qec.setCreator(qe.getUuid());
				qec.setCreatetime(now);
				qec.setStatus(QcbEmployeeCouponStatus.UNUSE);
				//计算优惠券截止日期
				long day = 1000*60*60*24;
				if(vo.getExpiryDate() != null){//有截止日期
					if((now.getTime()-vo.getExpiryDate().getTime()) > 0){//超出截止日期
						//优惠券失效 不发放给用户
						continue;
					}
					//未超出截止日期，判断是否有使用期限限制
					if(vo.getDeadline() > 0){
						long addTime = day*vo.getDeadline();
						if((now.getTime()+addTime-vo.getExpiryDate().getTime()) > 0){
							qec.setExpiryDate(vo.getExpiryDate());
						} else {
							Timestamp exp = DataTimeConvert.stringToTimeStamp(Utlity.timeSpanToDateString(now)+" 00:00:00");
							qec.setExpiryDate(new Timestamp(exp.getTime()+addTime));
						}
					} else {
						qec.setExpiryDate(vo.getExpiryDate());
					}
				} else {
					//没有截止日期 按照期限计算
					long addTime = day*vo.getDeadline();
					Timestamp exp = DataTimeConvert.stringToTimeStamp(Utlity.timeSpanToDateString(now)+" 00:00:00");
					qec.setExpiryDate(new Timestamp(exp.getTime()+addTime));
				}
				qec.setQcbEmployee(qe.getUuid());
				this.qcbEmployeeCouponDAO.insert(qec);
				
//				QcbEmployeeInformation qei = new QcbEmployeeInformation();
//				String price = "";
//				if(CouponType.CASH.equals(vo.getCouponType())){
//					price =Utlity.numFormat4UnitDetailLess(vo.getCouponPrice())+"元的现金增值券";
//				} else if (CouponType.INTERESTS.equals(vo.getCouponType())) {
//					price =Utlity.numFormat4UnitDetailLess(vo.getCouponPrice())+"%的加息券";
//				}
//				String content = "尊敬的用户您好：您于"+Utlity.timeSpanToDateString(now)+"获得1张"+price+"，您可在我的优惠券内查询。";
//				qei.setCreator(qe.getUuid());
//				qei.setStatus(QcbEmployeeInformationStatus.UNREAD);
//				qei.setCreatetime(new Timestamp(System.currentTimeMillis()));
//				qei.setUuid(UUID.randomUUID().toString());
//				qei.setInfoText(content);
//				qei.setInfoTitle(QcbEmployeeInformationTitle.COUPONADD);
//				qei.setQcbEmployee(qe.getUuid());
//				this.qcbEmployeeInformationDAO.insert(qei);
			}
		}
	}
	
	@Override
	public void updateWithdrawBatchRevoke(
			List<QcbEmployeeHistory> qehList) throws Exception {
		for(QcbEmployeeHistory qeh : qehList){
			this.qcbEmployeeHistoryDAO.update(qeh);
			
			//返还用户资金
			QcbEmployee qe = this.qcbEmployeeDAO.get(qeh.getQcbEmployee());
			if(qe != null){
				BigDecimal accountBalance = qe.getAccountBalance();
				qe.setAccountBalance(accountBalance.add(qeh.getPay().add(qeh.getPoundage())));
				this.qcbEmployeeDAO.update(qe);
				
				String message = "";
				if(!Utlity.checkStringNull(qeh.getOrderId())){
					QcbOrderinfoByThirdparty order = this.qcbOrderinfoByThirdpartyDAO.get(qeh.getOrderId());
					message = order.getMessage();
				}
				
				QcbMessageUtil.withdrawFailedTemplate(qe.getOpenid(), qeh.getOrderNum(), qe.getRealname(), Utlity.numFormat4UnitDetail(qeh.getPay()), message);
			} else {
				throw new TransactionException("员工信息不存在！");
			}
		}
	}
	
	@Override
	public List<Entity> getListForCountPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return this.qcbEmployeeHistoryDAO.getListForCountPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public HashMap<String, Object> insertReapalNotice4RePay(Map<String, Object> map) throws ParseException, TransactionException, NumberFormatException, Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		String data = map.get("data") == null ? "" : map.get("data").toString();
		if(!Utlity.checkStringNull(data)){
			String resultArr[] = data.split(",");
			String status = "";
			if("成功".equals(resultArr[13])){
				status = QcbOrderinfoByThirdpartyReturnStatus.SUCCESS;
			} else {
				status = QcbOrderinfoByThirdpartyReturnStatus.FAIL;
			}
			String reason = "";
			if(resultArr.length > 14){
				reason = resultArr[14];
			}
			
			String orderNum = resultArr[12];
			String fee = resultArr[9];
			
			BigDecimal total_fee = BigDecimal.valueOf(Double.parseDouble(fee));
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("orderNum", orderNum);
			List<Entity> list = this.qcbOrderinfoByThirdpartyDAO.getListForPage(inputParams, -1, -1, null, QcbOrderinfoByThirdparty.class);
			QcbOrderinfoByThirdparty obt = null;
			if(list != null && list.size() > 0){
				obt = (QcbOrderinfoByThirdparty) list.get(0);
				if(!QcbOrderinfoByThirdpartyReturnStatus.SUCCESS.equals(obt.getStatus())){
					obt.setPaytime(new Timestamp(System.currentTimeMillis()));
					obt.setStatus(status);
					obt.setErrCodeDes(reason == null ? "" : reason);
					obt.setMessage(reason == null ? "" : reason);
					this.qcbOrderinfoByThirdpartyDAO.update(obt);
				}
			} else {
				message = "订单未找到";
				throw new TransactionException(message);
			}
			
			inputParams.clear();
			inputParams.put("order", obt.getUuid());
			inputParams.put("qcbEmployee", obt.getAccount());
			inputParams.put("orderType", QcbEmployeeHistoryOrderType.PAY_TYPE_REAPAL);
			inputParams.put("type", QcbEmployeeHistoryType.REPAYMENT);
			List<Entity> listHistory = this.qcbEmployeeHistoryDAO.getListForPage(inputParams, -1, -1, null, QcbEmployeeHistory.class);
			if(listHistory != null && listHistory.size() > 0){
				QcbEmployeeHistory qeh = (QcbEmployeeHistory) listHistory.get(0);
				if(qeh == null){
					message = "订单不存在！"; 
					throw new TransactionException(message);
				}
				qeh = this.qcbEmployeeHistoryDAO.get(qeh.getUuid());
				
				QcbEmployee qe = this.qcbEmployeeDAO.get(qeh.getQcbEmployee());
				if(!QcbOrderinfoByThirdpartyReturnStatus.SUCCESS.equals(status)){//不是成功状态 算支付失败 进入人工付款流程
					if(QcbOrderinfoByThirdpartyReturnStatus.FAIL.equals(status) || QcbOrderinfoByThirdpartyReturnStatus.RETURN_TICKET.equals(status)){
						qeh.setProcessingStatus(QcbEmployeeHistoryProcessStatus.UNPROCESS);//接口调用失败 进入人工处理流程，设置处理状态为未处理
						qeh.setStatus(QcbEmployeeHistoryStatus.TRANSACTING);
						this.qcbEmployeeHistoryDAO.update(qeh);
						
						message = "OK";
						resultFlag = true;
						result.put("result", resultFlag);
						result.put("message", message);
						return result;
					} else if (QcbOrderinfoByThirdpartyReturnStatus.WITHDRAWAL_SUBMITTED.equals(status)) {
						qeh.setStatus(QcbEmployeeHistoryStatus.TRANSACTING);
						message = "交易进行中"; 
						throw new TransactionException(message);
					}
				}
				
				if(!QcbEmployeeHistoryStatus.SUCCESS.equals(qeh.getStatus())){
					if(QcbOrderinfoByThirdpartyReturnStatus.SUCCESS.equals(status)){
						qeh.setStatus(QcbEmployeeHistoryStatus.SUCCESS);
						if(QcbEmployeeHistoryType.WITHDRAW.equals(qeh.getType())){
							//更新企业账户余额
							CompanyAccount ca = this.companyAccountDAO.get(qeh.getCompanyAccount());
							PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
							if(ca == null){
								message = "账户信息错误";
								throw new TransactionException(message);
							}
							
							//更新系统可用余额
							PlatformAccount paf = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
							BigDecimal totalAmountf = paf.getTotalAmount();
							
							BigDecimal accountTotal = ca.getAccountBalance();
							accountTotal = accountTotal.subtract(total_fee);
							
							BigDecimal totalAmount = pa.getTotalAmount();
							totalAmount = totalAmount.subtract(total_fee);
							
							//20180228 更新用户提现手续费扣除用户余额
							if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
								accountTotal = accountTotal.subtract(qeh.getPoundage());
								totalAmount = totalAmount.subtract(qeh.getPoundage());
								totalAmountf = totalAmountf.subtract(qeh.getPoundage());
							}
							
							ca.setAccountBalance(accountTotal);
							//更新系统总帐户余额
							pa.setTotalAmount(totalAmount);
							paf.setTotalAmount(totalAmountf);
							
							//增加公司账户交易记录--用户提现
							CompanyAccountHistory cat = new CompanyAccountHistory();
							cat.setUuid(UUID.randomUUID().toString());
							cat.setCompanyAccountOut(qeh.getCompanyAccount());//用户提现
							cat.setType(CompanyAccountHistoryType.EMP_TAKEOUT);
							cat.setQcbEmployee(qe.getUuid());
							cat.setTotalAmount(total_fee);
							cat.setPoundage(qeh.getPoundage());
							cat.setPurpose("用户信用卡还款");
							cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
							
							cat.setCreator(qe.getUuid());
							cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
							
							cat.setQcbEmployeeHistory(qeh.getUuid());
							
							//20180622增加记录本次余额信息
							cat.setAccountBalance(ca.getAccountBalance().add(qeh.getPoundage()));
							
							this.companyAccountDAO.update(ca);
							this.platformAccountDAO.update(pa);
							this.platformAccountDAO.update(paf);
							if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
								//增加公司账户交易记录--费用录入（扣除交易手续费）
								CompanyAccountHistory catp = new CompanyAccountHistory();
								catp.setUuid(UUID.randomUUID().toString());
								catp.setCompanyAccountOut(qeh.getCompanyAccount());
								catp.setType(CompanyAccountHistoryType.EXPEND);
								catp.setQcbEmployee(qe.getUuid());
								catp.setTotalAmount(qeh.getPoundage());
								catp.setPoundage(BigDecimal.ZERO);
								catp.setPurpose("企财宝员工信用卡还款--手续费扣除");
								catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
								
								catp.setCreator(qe.getUuid());
								catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
								catp.setQcbEmployeeHistory(qeh.getUuid());
								catp.setRelated(cat.getUuid());
								cat.setRelated(catp.getUuid());
								//20180622增加记录本次余额信息
								catp.setAccountBalance(ca.getAccountBalance());
								
								this.companyAccountHistoryDAO.insert(cat);
								this.companyAccountHistoryDAO.insert(catp);
							}else{
								this.companyAccountHistoryDAO.insert(cat);
							}
							//提现成功，发送通知短信
							MobileCode mc = new MobileCode();
							String content = "尊敬的用户您好：您于"+Utlity.timeSpanToChinaDateString(qeh.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(qeh.getPay())+"元信用卡还款申请已处理成功，请注意查询。";
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
							this.insertSmsInfo(mc);
							
							if(!Utlity.checkStringNull(qe.getOpenid())){
								QcbEmployeeBankcard qeb = this.qcbEmployeeBankcardDAO.get(qeh.getBankcard());
								if(qeb != null){
									String bankcard = qeb.getBindingBankCard();
									QcbMessageUtil.repaymentApplyTemplate(qe.getOpenid(), Utlity.numFormat4UnitDetail(qeh.getPay()), 
											bankcard.substring(bankcard.length() - 4, bankcard.length()));
								}
							}
						} else {
							message = "账单类型错误";
							throw new TransactionException(message);
						}
					} else {
						message = "状态错误";
						throw new TransactionException(message);
					}
					this.qcbEmployeeHistoryDAO.update(qeh);
				}else{
					message = "账单错误";
					throw new TransactionException(message);
				}
			} else {
				message = "账单错误";
				throw new TransactionException(message);
			}
			System.out.println("成功");
			result.put("result", resultFlag);
			result.put("message", message);
			return result;
		} else {
			message = "发生错误";
			throw new TransactionException(message);
		}
	}
	
	@Override
	public void insertwechart(QcbOrderinfoByThirdparty qobt, QcbEmployeeHistory qeh, QcbEmployeeBankcard qeb) {
		this.qcbOrderinfoByThirdpartyDAO.insert(qobt);
		this.qcbEmployeeHistoryDAO.insert(qeh);
		this.qcbEmployeeBankcardDAO.update(qeb);
	}
	
	@Override
	public void insertwechart(QcbOrderinfoByThirdparty qobt, QcbEmployeeHistory qeh) {
		this.qcbOrderinfoByThirdpartyDAO.insert(qobt);
		this.qcbEmployeeHistoryDAO.insert(qeh);
	}

	@Override
	public HashMap<String, Object> insertReapalNotice4Recharge(Map<String, Object> map) throws ParseException, TransactionException, NumberFormatException, Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		String out_trade_no = map.get("order_no") == null ? "" : map.get("order_no").toString();
		String transaction_id = map.get("trade_no") == null ? "" : map.get("trade_no").toString();
		String result_status = map.get("status") == null ? "" : map.get("status").toString();
		String result_code = map.get("result_code") == null ? "" : map.get("result_code").toString();
		String return_msg = map.get("result_msg") == null ? "" : map.get("result_msg").toString();
		String fee = map.get("total_fee") == null ? "" : map.get("total_fee").toString();//单位是分
		BigDecimal total_fee = BigDecimal.valueOf(Double.parseDouble(fee)).divide(BigDecimal.valueOf(100));
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("orderNum", out_trade_no);
		List<Entity> list = this.qcbOrderinfoByThirdpartyDAO.getListForPage(inputParams, -1, -1, null, QcbOrderinfoByThirdparty.class);
		QcbOrderinfoByThirdparty qobt = null;
		if(list != null && list.size() > 0){
			qobt = (QcbOrderinfoByThirdparty) list.get(0);
			if(!QcbOrderinfoByThirdpartyReturnStatus.TRADE_FINISHED.equals(qobt.getStatus())){
				qobt.setPayNum(transaction_id);
//				obt.setPaytime(new Timestamp(Utlity.stringToDateFull(time_end).getTime()));
				qobt.setStatus(result_status);
				qobt.setErrCode(result_code);
				qobt.setErrCodeDes(return_msg);
				qobt.setMessage(return_msg == null ? "" : return_msg);
				this.qcbOrderinfoByThirdpartyDAO.update(qobt);
			}
		} else {
			message = "订单未找到";
			throw new TransactionException(message);
		}

		inputParams.clear();
		inputParams.put("order", qobt.getUuid());
		inputParams.put("qcbEmployee", qobt.getAccount());
		inputParams.put("orderType", QcbEmployeeHistoryOrderType.PAY_TYPE_REAPAL);
		inputParams.put("type", QcbEmployeeHistoryType.INCOME);
		List<Entity> listHistory = this.qcbEmployeeHistoryDAO.getListForPage(inputParams, -1, -1, null, QcbEmployeeHistory.class);
		if(listHistory != null && listHistory.size() > 0){
			QcbEmployeeHistory qeh = (QcbEmployeeHistory) listHistory.get(0);
			if(qeh == null){
				message = "账单错误";
				throw new TransactionException(message);
			}
			
			if(!QcbEmployeeHistoryStatus.SUCCESS.equals(qeh.getStatus())){
				QcbEmployee qe = this.qcbEmployeeDAO.get(qeh.getQcbEmployee());
				if(QcbOrderinfoByThirdpartyReturnStatus.TRADE_SUCCESS.equals(result_status) || 
						QcbOrderinfoByThirdpartyReturnStatus.TRADE_FINISHED.equals(result_status)){
					qeh.setStatus(QcbEmployeeHistoryStatus.SUCCESS);
					//只更新账户总资产
					BigDecimal total = qe.getAccountBalance();
					total = total.add(total_fee);
					qe.setAccountBalance(total);//更新余额
					
					//更新企业账户余额
					CompanyAccount ca = this.companyAccountDAO.get(qeh.getCompanyAccount());
					PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
					if(ca == null){
						message = "账户信息错误";
						throw new TransactionException(message);
					}
					//更新系统可用余额
					PlatformAccount paf = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
					BigDecimal totalAmountf = paf.getTotalAmount();
//					totalAmountf = totalAmountf.add(total_fee);
					
					BigDecimal accountTotal = ca.getAccountBalance();
					accountTotal = accountTotal.add(total_fee);
					
					BigDecimal totalAmount = pa.getTotalAmount();
					totalAmount = totalAmount.add(total_fee);
					
					if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
						accountTotal = accountTotal.subtract(qeh.getPoundage());
						totalAmount = totalAmount.subtract(qeh.getPoundage());
						totalAmountf = totalAmountf.subtract(qeh.getPoundage());
					}
					ca.setAccountBalance(accountTotal);
					//更新系统总帐户余额
					pa.setTotalAmount(totalAmount);
					paf.setTotalAmount(totalAmountf);
					
					//增加公司账户交易记录--用户充值
					CompanyAccountHistory cat = new CompanyAccountHistory();
					cat.setUuid(UUID.randomUUID().toString());
					cat.setCompanyAccountIn(qeh.getCompanyAccount());//用户充值
					cat.setType(CompanyAccountHistoryType.EMP_FILLIN);
					cat.setQcbEmployee(qe.getUuid());
					cat.setTotalAmount(total_fee);
					cat.setPoundage(qeh.getPoundage());
					cat.setPurpose("企财宝员工充值");
					cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
					
					cat.setCreator(qe.getUuid());
					cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
					cat.setQcbEmployeeHistory(qeh.getUuid());
					
					//20180622增加记录本次余额信息
					cat.setAccountBalance(ca.getAccountBalance().add(qeh.getPoundage()));
					
					this.qcbEmployeeDAO.update(qe);
					this.companyAccountDAO.update(ca);
					this.platformAccountDAO.update(pa);
					this.platformAccountDAO.update(paf);
					this.companyAccountHistoryDAO.insert(cat);
					
					if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
						//增加公司账户交易记录--费用录入（扣除交易手续费）
						CompanyAccountHistory catp = new CompanyAccountHistory();
						catp.setUuid(UUID.randomUUID().toString());
						catp.setCompanyAccountOut(qeh.getCompanyAccount());//用户充值
						catp.setType(CompanyAccountHistoryType.EXPEND);
						catp.setQcbEmployee(qe.getUuid());
						catp.setTotalAmount(qeh.getPoundage());
						catp.setPoundage(BigDecimal.ZERO);
						catp.setPurpose("企财宝员工充值--手续费扣除");
						catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
						
						catp.setCreator(qe.getUuid());
						catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
						catp.setQcbEmployeeHistory(qeh.getUuid());
						catp.setRelated(cat.getUuid());
						cat.setRelated(catp.getUuid());
						//20180622增加记录本次余额信息
						catp.setAccountBalance(ca.getAccountBalance());
						
						this.companyAccountHistoryDAO.insert(cat);
						this.companyAccountHistoryDAO.insert(catp);
					}else{
						this.companyAccountHistoryDAO.insert(cat);
					}
					
					//充值成功，发送通知短信
					MobileCode mc = new MobileCode();
					String content = "尊敬的用户您好：您于"+Utlity.timeSpanToChinaDateString(qeh.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(qeh.getIncome())+"元充值申请已处理成功，请注意查询余额。";
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
					this.insertSmsInfo(mc);
					
//					content = "【牛投理财】"+content;
//					InvestorInformation ii = new InvestorInformation();
//					ii.setCreator(qe.getUuid());
//					ii.setStatus(InvestorInformationStatus.UNREAD);
//					ii.setCreatetime(new Timestamp(System.currentTimeMillis()));
//					ii.setUuid(UUID.randomUUID().toString());
//					ii.setInfoText(content);
//					ii.setInfoTitle(InvestorInformationTitle.RECHARGE);
//					ii.set-(investor.getUuid());
//					this.investorInformationDAO.insert(ii);
				} else {
					if(QcbOrderinfoByThirdpartyReturnStatus.TRADE_CLOSED.equals(result_status)){
						qeh.setStatus(QcbEmployeeHistoryStatus.CLOSED);
					} else {
						qeh.setStatus(QcbEmployeeHistoryStatus.FAIL);
					}
				}
				this.qcbEmployeeHistoryDAO.update(qeh);
			}else{
				message = "账单错误"; 
				throw new TransactionException(message);
			}
			System.out.println("成功");
			result.put("result", resultFlag);
			result.put("message", message);
			return result;
			
		} else {
			message = "账单错误";
			throw new TransactionException(message);
		}
		
	}

	@Override
	public void updateWithdraw(QcbOrderinfoByThirdparty obt,
			QcbOrderinfoByThirdparty obtN, QcbEmployeeHistory qeh) {
		this.qcbOrderinfoByThirdpartyDAO.update(obt);
		this.qcbOrderinfoByThirdpartyDAO.insert(obtN);
		if(qeh != null){
			this.qcbEmployeeHistoryDAO.update(qeh);
		}
	}

	@Override
	public Integer getCountForWithdraw(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return this.qcbEmployeeHistoryDAO.getCountForWithdraw(inputParams);
	}
}
