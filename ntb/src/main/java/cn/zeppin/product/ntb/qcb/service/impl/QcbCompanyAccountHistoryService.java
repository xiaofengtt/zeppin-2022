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
import cn.zeppin.product.ntb.backadmin.dao.api.IBkAreaDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBranchBankDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountHistoryDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IMobileCodeDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IPlatformAccountDAO;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.BranchBank;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.PlatformAccount;
import cn.zeppin.product.ntb.core.entity.PlatformAccount.PlatformAccountUuid;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryProcessStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyBankcard;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty.QcbOrderinfoByThirdpartyAccountType;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty.QcbOrderinfoByThirdpartyReturnStatus;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty.QcbOrderinfoByThirdpartyType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyAccountHistoryDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbCompanyBankcardDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbOrderinfoByThirdpartyDAO;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountHistoryService;
import cn.zeppin.product.ntb.qcb.utility.QcbMessageUtil;
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
public class QcbCompanyAccountHistoryService extends BaseService implements IQcbCompanyAccountHistoryService {

	@Autowired
	private IQcbCompanyAccountHistoryDAO qcbCompanyAccountHistoryDAO;
	
	@Autowired
	private IQcbOrderinfoByThirdpartyDAO qcbOrderinfoByThirdpartyDAO;
	
	@Autowired
	private IQcbCompanyAccountDAO qcbCompanyAccountDAO;
	
	@Autowired
	private IQcbCompanyBankcardDAO qcbCompanyBankcardDAO;
	
	@Autowired
	private IBankDAO bankDAO;
	
	@Autowired
	private IBranchBankDAO branchBankDAO;
	
	@Autowired
	private IBkAreaDAO bkAreaDAO;
	
	@Autowired
	private IMobileCodeDAO mobileCodeDAO;
	
	@Autowired
	private ICompanyAccountDAO companyAccountDAO;
	
	@Autowired
	private ICompanyAccountHistoryDAO companyAccountHistoryDAO;
	
	@Autowired
	private IPlatformAccountDAO platformAccountDAO;
	
	@Override
	public QcbCompanyAccountHistory insert(QcbCompanyAccountHistory qcbCompanyAccountHistory) {
		return qcbCompanyAccountHistoryDAO.insert(qcbCompanyAccountHistory);
	}

	@Override
	public QcbCompanyAccountHistory delete(QcbCompanyAccountHistory qcbCompanyAccountHistory) {
		return qcbCompanyAccountHistoryDAO.delete(qcbCompanyAccountHistory);
	}

	@Override
	public QcbCompanyAccountHistory update(QcbCompanyAccountHistory qcbCompanyAccountHistory) {
		return qcbCompanyAccountHistoryDAO.update(qcbCompanyAccountHistory);
	}

	@Override
	public QcbCompanyAccountHistory get(String uuid) {
		return qcbCompanyAccountHistoryDAO.get(uuid);
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
		return qcbCompanyAccountHistoryDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return qcbCompanyAccountHistoryDAO.getCount(inputParams);
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
		return qcbCompanyAccountHistoryDAO.getListForWithdrawPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}
	
	/**
	 * 校验订单号是否已存在
	 * @param orderNum
	 * @return
	 */
	@Override
	public Boolean checkOrderNum(String orderNum) {
		return qcbCompanyAccountHistoryDAO.checkOrderNum(orderNum);
	}
	
	/**
	 * 企财宝企业提现
	 * @param qcbCompanyAccount
	 * @param qcbOrderinfoByThirdparty
	 * @param qcbCompanyAccountHistory
	 */
	@Override
	public void insertWithdraw(QcbCompanyAccount qca, QcbOrderinfoByThirdparty qobt, QcbCompanyAccountHistory qcah) throws ParseException, TransactionException{
		this.qcbOrderinfoByThirdpartyDAO.insert(qobt);
		this.qcbCompanyAccountHistoryDAO.insert(qcah);
		this.qcbCompanyAccountDAO.update(qca);
		
		QcbMessageUtil.withdrawNoticeToAdmin(qcah.getOrderNum(), qca.getName(), Utlity.numFormat4UnitDetail(qcah.getPay()), qcah.getUuid(), "qcbCompany");
	}
	
	public void insertSmsInfo(MobileCode mc) throws TransactionException {
		String phone = mc.getMobile();
		String content = mc.getContent();
		String result = SendSms.sendSms4Qcb(content, phone);
		if (result != null && "0".equals(result.split("_")[0])) {
			this.mobileCodeDAO.insert(mc);
		}
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
//							obt.setPayNum(transaction_id);
					obt.setPaytime(new Timestamp(System.currentTimeMillis()));
					obt.setStatus(status);
//							obt.setErrCode(err_code == null ? "" : err_code);
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
			inputParams.put("qcbCompany", obt.getAccount());
			inputParams.put("orderType", QcbCompanyAccountHistoryOrderType.PAY_TYPE_REAPAL);
			inputParams.put("type", QcbCompanyAccountHistoryType.WITHDRAW);
			List<Entity> listHistory = this.qcbCompanyAccountHistoryDAO.getListForPage(inputParams, -1, -1, null, QcbCompanyAccountHistory.class);
			if(listHistory != null && listHistory.size() > 0){
				QcbCompanyAccountHistory qcah = (QcbCompanyAccountHistory) listHistory.get(0);
				if(qcah == null){
					message = "订单不存在！"; 
					throw new TransactionException(message);
				}
				qcah = this.qcbCompanyAccountHistoryDAO.get(qcah.getUuid());
				
				QcbCompanyAccount qca = this.qcbCompanyAccountDAO.get(qcah.getQcbCompany());
				if(!QcbOrderinfoByThirdpartyReturnStatus.SUCCESS.equals(status)){//不是成功状态 算支付失败 进入人工付款流程
					if(QcbOrderinfoByThirdpartyReturnStatus.FAIL.equals(status) || QcbOrderinfoByThirdpartyReturnStatus.RETURN_TICKET.equals(status)){
						qcah.setProcessingStatus(QcbCompanyAccountHistoryProcessStatus.UNPROCESS);//接口调用失败 进入人工处理流程，设置处理状态为未处理
						qcah.setStatus(QcbCompanyAccountHistoryStatus.TRANSACTING);
						this.qcbCompanyAccountHistoryDAO.update(qcah);
						message = "OK";
						resultFlag = true;
						result.put("result", resultFlag);
						result.put("message", message);
						return result;
					} else if (QcbOrderinfoByThirdpartyReturnStatus.WITHDRAWAL_SUBMITTED.equals(status)) {
						qcah.setStatus(QcbCompanyAccountHistoryStatus.TRANSACTING);
						message = "交易进行中"; 
						throw new TransactionException(message);
					}
					
					
				}
				
				if(!QcbCompanyAccountHistoryStatus.SUCCESS.equals(qcah.getStatus())){
					if(QcbOrderinfoByThirdpartyReturnStatus.SUCCESS.equals(status)){
						qcah.setStatus(QcbCompanyAccountHistoryStatus.SUCCESS);
						if(QcbCompanyAccountHistoryType.WITHDRAW.equals(qcah.getType())){
							//更新企业账户余额
							CompanyAccount ca = this.companyAccountDAO.get(qcah.getCompanyAccount());
							PlatformAccount pa = this.platformAccountDAO.get(PlatformAccountUuid.TOTAL);
							if(ca == null){
								message = "账户信息错误";
								throw new TransactionException(message);
							}
							
							BigDecimal accountTotal = ca.getAccountBalance();
							accountTotal = accountTotal.subtract(total_fee);
							
							BigDecimal totalAmount = pa.getTotalAmount();
							totalAmount = totalAmount.subtract(total_fee);
							
							if(qcah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
								accountTotal = accountTotal.subtract(qcah.getPoundage());
								totalAmount = totalAmount.subtract(qcah.getPoundage());
							}
							
							ca.setAccountBalance(accountTotal);
							//更新系统总帐户余额
							pa.setTotalAmount(totalAmount);
							
							//增加公司账户交易记录--企财宝企业提现
							CompanyAccountHistory cat = new CompanyAccountHistory();
							cat.setUuid(UUID.randomUUID().toString());
							cat.setCompanyAccountOut(qcah.getCompanyAccount());
							cat.setType(CompanyAccountHistoryType.QCB_TAKEOUT);
							cat.setQcbCompanyAccount(qcah.getQcbCompany());
							cat.setTotalAmount(total_fee.add(qcah.getPoundage()));
							cat.setPoundage(qcah.getPoundage());
							cat.setPurpose("企财宝企业提现");
							cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
							
							cat.setCreator(qcah.getCreator());
							cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
							
							cat.setQcbCompanyAccountHistory(qcah.getUuid());
							
							//20180622增加记录本次余额信息
							cat.setAccountBalance(ca.getAccountBalance());
							
							this.companyAccountDAO.update(ca);
							this.platformAccountDAO.update(pa);
							this.companyAccountHistoryDAO.insert(cat);
							//提现成功，发送通知短信
							if(qcah.getFlagSms()){
								MobileCode mc = new MobileCode();
								String content = "尊敬的用户您好：贵公司" + qca.getName() + "于企财宝"+Utlity.timeSpanToChinaDateString(qcah.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(qcah.getPay())+"元提现申请已处理成功，请注意查询。";
								String mobile = qca.getAccreditMobile();
								String codeInfo = Utlity.getCaptcha();
								mc.setCode(codeInfo);
								mc.setContent(content);
								mc.setMobile(mobile);
								mc.setCreator(qcah.getCreator());
								mc.setStatus(MobileCodeStatus.DISABLE);
								mc.setType(MobileCodeTypes.NOTICE);
								mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
								mc.setCreatorType(MobileCodeCreatorType.QCB_ADMIN);
								mc.setUuid(UUID.randomUUID().toString());
								this.insertSmsInfo(mc);
							}
						} else {
							message = "账单类型错误";
							throw new TransactionException(message);
						}
					} else {
						message = "状态错误";
						throw new TransactionException(message);
					}
					this.qcbCompanyAccountHistoryDAO.update(qcah);
					
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
	public List<Entity> getListForCountPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		return this.qcbCompanyAccountHistoryDAO.getListForCountPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}
	

	/**
	 * 企财宝员工提现状态列表
	 * @param searchMap
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getWithdrawStatusList(Map<String, String> searchMap, Class<? extends Entity> resultClass){
		return this.qcbCompanyAccountHistoryDAO.getWithdrawStatusList(searchMap, resultClass);
	}
	
	/**
	 * 企财宝员工提现批量重试
	 * @param qcahList
	 * @return
	 */
	@Override
	public HashMap<String, Object> updateWithdrawBatch(List<QcbCompanyAccountHistory> qcahList) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		Integer count = 0;
		for(QcbCompanyAccountHistory qcah : qcahList){
			QcbCompanyBankcard qcab = this.qcbCompanyBankcardDAO.get(qcah.getBankcard());
			String bankcardnum = qcab.getBindingBankCard().split("_#")[0];
			Bank bank = this.bankDAO.get(qcab.getBank());
			String orderNumStr = Utlity.getOrderNumStr(qcah.getOrderNum().substring(0, 1),Utlity.BILL_PAYTYPE_REAPAL,Utlity.BILL_PURPOSE_WITHDRAW);
			if(this.qcbCompanyAccountHistoryDAO.checkOrderNum(orderNumStr)){
				message = "订单编号生成失败，请稍后重试！";
				throw new TransactionException(message);
			}
			BigDecimal pay = qcah.getPay();
			QcbOrderinfoByThirdparty qobtN = new QcbOrderinfoByThirdparty();
			qobtN.setUuid(UUID.randomUUID().toString());
			qobtN.setType(QcbOrderinfoByThirdpartyType.REAPAL);
			qobtN.setAccount(qcah.getQcbCompany());
			qobtN.setAccountType(QcbOrderinfoByThirdpartyAccountType.COMPANY);
			qobtN.setOrderNum(orderNumStr);
			qobtN.setBody("融宝支付--企财宝企业提现");
			qobtN.setTotalFee(pay);
			qobtN.setPaySource(MD5.getMD5(qcah.getQcbCompany()));
			
			Map<String, Object> resultBalance = ReapalUtlity.get_balance_Query();
			String balance = resultBalance.get("balance") == null ? "0" : resultBalance.get("balance").toString();
			BigDecimal accountTotal = BigDecimal.valueOf(Double.valueOf(balance));
			if(pay.add(qcah.getPoundage()).compareTo(accountTotal) == 1){
				qobtN.setStatus(QcbOrderinfoByThirdpartyReturnStatus.FAIL);
				qobtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
				qobtN.setErrCode("");
				qobtN.setErrCodeDes("提现账户余额不足！");
				qobtN.setMessage("提现账户余额不足！");
			} else {
				//支行
				BranchBank bb = this.branchBankDAO.get(qcab.getBranchBank());
				if(bb == null){
					message = "提现银行卡支行错误!";
					throw new TransactionException(message);
				}
				//地区
				BkArea ba = this.bkAreaDAO.get(qcab.getBkArea());
				if(ba == null){
					message = "提现银行卡地区错误!";
					throw new TransactionException(message);
				}
				List<String> areaNames = this.bkAreaDAO.getFullName(ba.getUuid());
				
				//银行付款接口调用 返回成功后
				WithdrawData wd = new WithdrawData(1, bankcardnum, qcab.getBindingCardCardholder(), bank.getName(), bb.getName(), bb.getName(), WithdrawDateProperties.PUBLIC, pay.setScale(2, BigDecimal.ROUND_HALF_UP), WithdrawDateCurrency.CNY, areaNames.get(0), areaNames.get(1), qcab.getBindingCardPhone(), "", "", "", orderNumStr, "提现");
				
				List<WithdrawData> content = new ArrayList<WithdrawData>();
				content.add(wd);
				WithdrawDataArray wda = new WithdrawDataArray(content);
				wda.setBatch_no(orderNumStr);
				wda.setTrans_time(Utlity.timeSpanToString(qcah.getCreatetime()));
				
				Map<String, Object> result2 = ReapalUtlity.qcbWithdrawBatchSubmit(wda);
				qobtN.setStatus(result2.get("result_code").toString());
				qobtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
				qobtN.setErrCode(result2.get("result_code") == null ? "" : result2.get("result_code").toString());
				qobtN.setErrCodeDes(result2.get("result_msg") == null ? "" : result2.get("result_msg").toString());
				qobtN.setMessage(result2.get("result_msg") == null ? "" : result2.get("result_msg").toString());
			}
			qcah.setOrderId(qobtN.getUuid());
			qcah.setOrderNum(orderNumStr);
			qcah.setStatus(QcbCompanyAccountHistoryStatus.TRANSACTING);
			if(!QcbOrderinfoByThirdpartyReturnStatus.REAPAL_CHECKED.equals(qobtN.getStatus()) && !QcbOrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(qobtN.getStatus())){
				qcah.setProcessingStatus(QcbCompanyAccountHistoryProcessStatus.UNPROCESS);//接口调用失败 进入人工处理流程，设置处理状态为未处理
				count++;
			} else {
				qcah.setProcessingStatus(QcbCompanyAccountHistoryProcessStatus.PROCESSED);
			}
			this.qcbOrderinfoByThirdpartyDAO.insert(qobtN);
			this.qcbCompanyAccountHistoryDAO.update(qcah);
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
	public void updateBatch(List<QcbCompanyAccountHistory> listUpdate) {
		for(QcbCompanyAccountHistory qcah : listUpdate){
			this.qcbCompanyAccountHistoryDAO.update(qcah);
		}
	}
	
	@Override
	public void updateWithdraw(List<QcbCompanyAccountHistory> qcahList) throws TransactionException, Exception {
		
		List<CompanyAccount> caList = new ArrayList<CompanyAccount>();
		List<CompanyAccountHistory> cahList = new ArrayList<CompanyAccountHistory>();
		Map<String, CompanyAccount> companyAccountMap = new HashMap<String, CompanyAccount>();
		PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
		List<MobileCode> codeList = new ArrayList<MobileCode>();
		
		for(QcbCompanyAccountHistory qcah :qcahList){
			CompanyAccount ca = null;
			if(companyAccountMap.containsKey(qcah.getProcessCompanyAccount())){
				ca = companyAccountMap.get(qcah.getProcessCompanyAccount());
			} else {
				ca = this.companyAccountDAO.get(qcah.getProcessCompanyAccount());
			}
			
			BigDecimal accountTotal = ca.getAccountBalance();
			accountTotal = accountTotal.subtract(qcah.getPay());
			
			BigDecimal totalAmount = pa.getTotalAmount();
			totalAmount = totalAmount.subtract(qcah.getPay());
			
			if(qcah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
				accountTotal = accountTotal.subtract(qcah.getPoundage());
				totalAmount = totalAmount.subtract(qcah.getPoundage());
			}
			
			ca.setAccountBalance(accountTotal);
			//更新系统总帐户余额
			pa.setTotalAmount(totalAmount);
			
			//增加公司账户交易记录--企财宝企业提现
			CompanyAccountHistory cat = new CompanyAccountHistory();
			cat.setUuid(UUID.randomUUID().toString());
			cat.setCompanyAccountOut(qcah.getProcessCompanyAccount());//企财宝企业提现
			cat.setType(CompanyAccountHistoryType.TAKEOUT);
			cat.setQcbCompanyAccount(qcah.getQcbCompany());
			cat.setTotalAmount(qcah.getPay().add(qcah.getPoundage()));
			cat.setPoundage(qcah.getPoundage());
			cat.setPurpose("企财宝企业提现");
			cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
			
			cat.setCreator(qcah.getCreator());
			cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
			cat.setQcbCompanyAccountHistory(qcah.getUuid());
			
			//20180622增加记录本次余额信息
			cat.setAccountBalance(ca.getAccountBalance());
			
			companyAccountMap.put(ca.getUuid(), ca);
			cahList.add(cat);
			
			QcbCompanyAccount qca = this.qcbCompanyAccountDAO.get(qcah.getQcbCompany());
			if(qca == null){
				throw new TransactionException("该企财宝企业不存在！");
			}
			
			//提现成功，发送通知短信
			if(qcah.getFlagSms()){
				MobileCode mc = new MobileCode();
				String content = "尊敬的用户您好：贵公司" + qca.getName() + "于企财宝"+Utlity.timeSpanToChinaDateString(qcah.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(qcah.getPay())+"元提现申请已处理成功，请注意查询。";
				String mobile = qca.getAccreditMobile();
				String codeInfo = Utlity.getCaptcha();
				mc.setCode(codeInfo);
				mc.setContent(content);
				mc.setMobile(mobile);
				mc.setCreator(qcah.getCreator());
				mc.setStatus(MobileCodeStatus.DISABLE);
				mc.setType(MobileCodeTypes.NOTICE);
				mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
				mc.setCreatorType(MobileCodeCreatorType.QCB_ADMIN);
				mc.setUuid(UUID.randomUUID().toString());
				codeList.add(mc);
			}
		}
		
		if(!companyAccountMap.isEmpty()){
			for(CompanyAccount ca : companyAccountMap.values()){
				caList.add(ca);
			}
		}
		this.platformAccountDAO.update(pa);
		if(!qcahList.isEmpty()){
			for(QcbCompanyAccountHistory qcah: qcahList){
				this.qcbCompanyAccountHistoryDAO.update(qcah);
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
	public void updateWithdrawBatchRevoke(List<QcbCompanyAccountHistory> qehList)
			throws Exception {
		// TODO Auto-generated method stub
		for(QcbCompanyAccountHistory qeh : qehList){
			this.qcbCompanyAccountHistoryDAO.update(qeh);
			
			//返还用户资金
			QcbCompanyAccount qe = this.qcbCompanyAccountDAO.get(qeh.getQcbCompany());
			if(qe != null){
				BigDecimal accountBalance = qe.getAccountBalance();
				
				qe.setAccountBalance(accountBalance.add(qeh.getPay().add(qeh.getPoundage())));
				qe.setAccountPay(accountBalance.add(qeh.getPay().add(qeh.getPoundage())));
				this.qcbCompanyAccountDAO.update(qe);
			} else {
				throw new TransactionException("企业信息不存在！");
			}
		}
	}
}
