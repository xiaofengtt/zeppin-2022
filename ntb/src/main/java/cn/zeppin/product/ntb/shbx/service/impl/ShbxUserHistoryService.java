/**
 * 
 */
package cn.zeppin.product.ntb.shbx.service.impl;

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
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.PlatformAccount;
import cn.zeppin.product.ntb.core.entity.PlatformAccount.PlatformAccountUuid;
import cn.zeppin.product.ntb.core.entity.ShbxOrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.ShbxOrderinfoByThirdparty.ShbxOrderinfoByThirdpartyReturnStatus;
import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder;
import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder.ShbxSecurityOrderStatus;
import cn.zeppin.product.ntb.core.entity.ShbxUser;
import cn.zeppin.product.ntb.core.entity.ShbxUserBankcard;
import cn.zeppin.product.ntb.core.entity.ShbxUserHistory;
import cn.zeppin.product.ntb.core.entity.ShbxUserHistory.ShbxUserHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.ShbxUserHistory.ShbxUserHistoryProcessStatus;
import cn.zeppin.product.ntb.core.entity.ShbxUserHistory.ShbxUserHistoryStatus;
import cn.zeppin.product.ntb.core.entity.ShbxUserHistory.ShbxUserHistoryType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.shbx.dao.api.IShbxOrderinfoByThirdpartyDAO;
import cn.zeppin.product.ntb.shbx.dao.api.IShbxSecurityOrderDAO;
import cn.zeppin.product.ntb.shbx.dao.api.IShbxUserBankcardDAO;
import cn.zeppin.product.ntb.shbx.dao.api.IShbxUserDAO;
import cn.zeppin.product.ntb.shbx.dao.api.IShbxUserHistoryDAO;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserHistoryService;
import cn.zeppin.product.utility.SendSmsNew;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 */
@Service
public class ShbxUserHistoryService extends BaseService implements IShbxUserHistoryService {

	@Autowired
	private IShbxUserHistoryDAO shbxUserHistoryDAO;
	
	@Autowired
	private IShbxUserDAO shbxUserDAO;
	
	@Autowired
	private IShbxUserBankcardDAO shbxUserBankcardDAO;
	
	@Autowired
	private ICouponStrategyDAO couponStrategyDAO;
	
	@Autowired
	private IShbxOrderinfoByThirdpartyDAO shbxOrderinfoByThirdpartyDAO;
	
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
	
	@Autowired
	private IShbxSecurityOrderDAO shbxSecurityOrderDAO;
	
	@Override
	public ShbxUserHistory insert(ShbxUserHistory shbxUserHistory) {
		return shbxUserHistoryDAO.insert(shbxUserHistory);
	}

	@Override
	public ShbxUserHistory delete(ShbxUserHistory shbxUserHistory) {
		return shbxUserHistoryDAO.delete(shbxUserHistory);
	}

	@Override
	public ShbxUserHistory update(ShbxUserHistory shbxUserHistory) {
		return shbxUserHistoryDAO.update(shbxUserHistory);
	}

	@Override
	public ShbxUserHistory get(String uuid) {
		return shbxUserHistoryDAO.get(uuid);
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
		return shbxUserHistoryDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
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
		return shbxUserHistoryDAO.getListForWithdrawPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}
	
	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return shbxUserHistoryDAO.getCount(inputParams);
	}
	
	/**
	 * 校验订单号是否已存在
	 * @param orderNum
	 * @return
	 */
	@Override
	public Boolean checkOrderNum(String orderNum) {
		return shbxUserHistoryDAO.checkOrderNum(orderNum);
	}
	
//	/**
//	 * 用户提现
//	 * @param shbxUser
//	 * @param ShbxOrderinfoByThirdparty
//	 * @param shbxUserHistory
//	 * @return
//	 */
//	public void insertWithdraw(ShbxUser qe, ShbxOrderinfoByThirdparty obt, ShbxUserHistory qeh){
//		this.ShbxOrderinfoByThirdpartyDAO.insert(obt);
//		this.shbxUserHistoryDAO.insert(qeh);
//		this.shbxUserDAO.update(qe);
//		
//		String name = qe.getRealname();
//		Map<String, String> inputParams = new HashMap<String, String>();
//		inputParams.put("shbxUser", qe.getUuid());
//		inputParams.put("status", ShbxCompanyEmployeeStatus.NORMAL);
//		List<Entity> list = this.ShbxCompanyEmployeeDAO.getListForPage(inputParams, -1, -1, null, ShbxCompanyEmployee.class);
//		if(list != null && list.size() > 0){
//			ShbxCompanyEmployee qce = (ShbxCompanyEmployee) list.get(0);
//			ShbxCompanyAccount qca = this.ShbxCompanyAccountDAO.get(qce.getShbxCompany());
//			if(qca != null){
//				name = qca.getName() + "社保熊用户" + qe.getRealname();	
//			}
//		}
//		
//		ShbxMessageUtil.withdrawNoticeToAdmin(qeh.getOrderNum(), name, Utlity.numFormat4UnitDetail(qeh.getPay()), qeh.getUuid(), "shbxUser");
//	}
	
	@Override
	public HashMap<String, Object> insertReapalNotice4Pay(Map<String, Object> map) 
			throws ParseException, TransactionException, NumberFormatException, Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		String data = map.get("data") == null ? "" : map.get("data").toString();
		if(!Utlity.checkStringNull(data)){
//			String resultArr[] = data.split(",");
//			String status = "";
//			if("成功".equals(resultArr[13])){
//				status = ShbxOrderinfoByThirdpartyReturnStatus.SUCCESS;
//			} else {
//				status = ShbxOrderinfoByThirdpartyReturnStatus.FAIL;
//			}
//			String reason = "";
//			if(resultArr.length > 14){
//				reason = resultArr[14];
//			}
//			
//			String orderNum = resultArr[12];
//			String fee = resultArr[9];
//			
//			BigDecimal total_fee = BigDecimal.valueOf(Double.parseDouble(fee));
//			Map<String, String> inputParams = new HashMap<String, String>();
//			inputParams.put("orderNum", orderNum);
//			List<Entity> list = this.ShbxOrderinfoByThirdpartyDAO.getListForPage(inputParams, -1, -1, null, ShbxOrderinfoByThirdparty.class);
//			ShbxOrderinfoByThirdparty obt = null;
//			if(list != null && list.size() > 0){
//				obt = (ShbxOrderinfoByThirdparty) list.get(0);
//				if(!ShbxOrderinfoByThirdpartyReturnStatus.SUCCESS.equals(obt.getStatus())){
//					obt.setPaytime(new Timestamp(System.currentTimeMillis()));
//					obt.setStatus(status);
//					obt.setErrCodeDes(reason == null ? "" : reason);
//					obt.setMessage(reason == null ? "" : reason);
//					this.ShbxOrderinfoByThirdpartyDAO.update(obt);
//				}
//			} else {
//				message = "订单未找到";
//				throw new TransactionException(message);
//			}
//			
//			inputParams.clear();
//			inputParams.put("order", obt.getUuid());
//			inputParams.put("shbxUser", obt.getAccount());
//			inputParams.put("orderType", ShbxUserHistoryOrderType.PAY_TYPE_REAPAL);
//			inputParams.put("type", ShbxUserHistoryType.WITHDRAW);
//			List<Entity> listHistory = this.shbxUserHistoryDAO.getListForPage(inputParams, -1, -1, null, ShbxUserHistory.class);
//			if(listHistory != null && listHistory.size() > 0){
//				ShbxUserHistory qeh = (ShbxUserHistory) listHistory.get(0);
//				if(qeh == null){
//					message = "订单不存在！"; 
//					throw new TransactionException(message);
//				}
//				qeh = this.shbxUserHistoryDAO.get(qeh.getUuid());
//				
//				ShbxUser qe = this.shbxUserDAO.get(qeh.getShbxUser());
//				if(!ShbxOrderinfoByThirdpartyReturnStatus.SUCCESS.equals(status)){//不是成功状态 算支付失败 进入人工付款流程
//					if(ShbxOrderinfoByThirdpartyReturnStatus.FAIL.equals(status) || ShbxOrderinfoByThirdpartyReturnStatus.RETURN_TICKET.equals(status)){
//						qeh.setProcessingStatus(ShbxUserHistoryProcessStatus.UNPROCESS);//接口调用失败 进入人工处理流程，设置处理状态为未处理
//						qeh.setStatus(ShbxUserHistoryStatus.TRANSACTING);
//						this.shbxUserHistoryDAO.update(qeh);
//						
//						message = "OK";
//						resultFlag = true;
//						result.put("result", resultFlag);
//						result.put("message", message);
//						return result;
//					} else if (ShbxOrderinfoByThirdpartyReturnStatus.WITHDRAWAL_SUBMITTED.equals(status)) {
//						qeh.setStatus(ShbxUserHistoryStatus.TRANSACTING);
//						message = "交易进行中"; 
//						throw new TransactionException(message);
//					}
//				}
//				
//				if(!ShbxUserHistoryStatus.SUCCESS.equals(qeh.getStatus())){
//					if(ShbxOrderinfoByThirdpartyReturnStatus.SUCCESS.equals(status)){
//						qeh.setStatus(ShbxUserHistoryStatus.SUCCESS);
//						if(ShbxUserHistoryType.WITHDRAW.equals(qeh.getType())){
//							//更新企业账户余额
//							CompanyAccount ca = this.companyAccountDAO.get(qeh.getCompanyAccount());
//							PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
//							if(ca == null){
//								message = "账户信息错误";
//								throw new TransactionException(message);
//							}
//							
//							BigDecimal accountTotal = ca.getAccountBalance();
//							accountTotal = accountTotal.subtract(total_fee);
//							
//							BigDecimal totalAmount = pa.getTotalAmount();
//							totalAmount = totalAmount.subtract(total_fee);
//							
//							//20180228 更新用户提现手续费扣除用户余额
//							if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
//								accountTotal = accountTotal.subtract(qeh.getPoundage());
//								totalAmount = totalAmount.subtract(qeh.getPoundage());
//							}
//							
//							ca.setAccountBalance(accountTotal);
//							//更新系统总帐户余额
//							pa.setTotalAmount(totalAmount);
//							
//							//增加公司账户交易记录--用户提现
//							CompanyAccountHistory cat = new CompanyAccountHistory();
//							cat.setUuid(UUID.randomUUID().toString());
//							cat.setCompanyAccountOut(qeh.getCompanyAccount());//用户提现
//							cat.setType(CompanyAccountHistoryType.EMP_TAKEOUT);
//							cat.setShbxUser(qe.getUuid());
//							cat.setTotalAmount(total_fee.add(qeh.getPoundage()));
//							cat.setPoundage(qeh.getPoundage());
//							cat.setPurpose("用户提现");
//							cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
//							
//							cat.setCreator(qe.getUuid());
//							cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
//							
//							cat.setShbxUserHistory(qeh.getUuid());
//							
//							//20180622增加记录本次余额信息
//							cat.setAccountBalance(ca.getAccountBalance());
//							
//							this.companyAccountDAO.update(ca);
//							this.platformAccountDAO.update(pa);
//							this.companyAccountHistoryDAO.insert(cat);
//							//提现成功，发送通知短信
//							MobileCode mc = new MobileCode();
//							String content = "尊敬的用户您好：您于"+Utlity.timeSpanToChinaDateString(qeh.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(qeh.getPay())+"元提现申请已处理成功，请注意查询。";
//							String mobile = qe.getMobile();
//							String codeInfo = Utlity.getCaptcha();
//							mc.setCode(codeInfo);
//							mc.setContent(content);
//							mc.setMobile(mobile);
//							mc.setCreator(qe.getUuid());
//							mc.setStatus(MobileCodeStatus.DISABLE);
//							mc.setType(MobileCodeTypes.NOTICE);
//							mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
//							mc.setCreatorType(MobileCodeCreatorType.Shbx_EMP);
//							mc.setUuid(UUID.randomUUID().toString());
//							this.insertSmsInfo(mc);
//							
//							if(!Utlity.checkStringNull(qe.getOpenid())){
//								ShbxUserBankcard qeb = this.shbxUserBankcardDAO.get(qeh.getBankcard());
//								if(qeb != null){
//									Bank b = this.bankDAO.get(qeb.getBank());
//									String bankcard = qeb.getBindingBankCard();
//									ShbxMessageUtil.withdrawApplyTemplate(qe.getOpenid(), Utlity.numFormat4UnitDetail(qeh.getPay()), 
//											Utlity.timeSpanToString(new Timestamp(System.currentTimeMillis())), 
//											b.getShortName() + "尾号(" + bankcard.substring(bankcard.length() - 4, bankcard.length()) + ")");
//								}
//							}
//						} else {
//							message = "账单类型错误";
//							throw new TransactionException(message);
//						}
//					} else {
//						message = "状态错误";
//						throw new TransactionException(message);
//					}
//					this.shbxUserHistoryDAO.update(qeh);
//				}else{
//					message = "账单错误";
//					throw new TransactionException(message);
//				}
//			} else {
//				message = "账单错误";
//				throw new TransactionException(message);
//			}
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
		String result = SendSmsNew.sendSms4Shbx(content, phone);
		if (result != null && "0".equals(result.split("_")[0])) {
			this.mobileCodeDAO.insert(mc);
		} else {
			throw new TransactionException("短信验证码发送失败！");
		}
	}
	
	/**
	 * 社保熊用户提现状态列表
	 * @param searchMap
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getWithdrawStatusList(Map<String, String> searchMap, Class<? extends Entity> resultClass){
		return this.shbxUserHistoryDAO.getWithdrawStatusList(searchMap, resultClass);
	}
	
	/**
	 * 社保熊用户提现批量重试
	 * @param qehList
	 * @return
	 */
	@Override
	public HashMap<String, Object> updateWithdrawBatch(List<ShbxUserHistory> qehList) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		Integer count = 0;
//		for(ShbxUserHistory qeh : qehList){
//			ShbxUserBankcard qeb = this.shbxUserBankcardDAO.get(qeh.getBankcard());
//			String bankcardnum = qeb.getBindingBankCard().split("_#")[0];
//			Bank bank = this.bankDAO.get(qeb.getBank());
//			ShbxUser qe = this.shbxUserDAO.get(qeh.getShbxUser());
//			String orderNumStr = Utlity.getOrderNumStr(qeh.getOrderNum().substring(0, 1),Utlity.BILL_PAYTYPE_REAPAL,Utlity.BILL_PURPOSE_WITHDRAW);
//			if(this.shbxUserHistoryDAO.checkOrderNum(orderNumStr)){
//				message = "订单编号生成失败，请稍后重试！";
//				throw new TransactionException(message);
//			}
//			BigDecimal pay = qeh.getPay();
//			ShbxOrderinfoByThirdparty qobtN = new ShbxOrderinfoByThirdparty();
//			qobtN.setUuid(UUID.randomUUID().toString());
//			qobtN.setType(ShbxOrderinfoByThirdpartyType.REAPAL);
//			qobtN.setAccount(qeh.getShbxUser());
//			qobtN.setAccountType(ShbxOrderinfoByThirdpartyAccountType.EMPLOYEE);
//			qobtN.setOrderNum(orderNumStr);
//			qobtN.setBody("融宝支付--用户提现");
//			qobtN.setTotalFee(pay);
//			qobtN.setPaySource(MD5.getMD5(qeh.getShbxUser()));
//			
//			Map<String, Object> resultBalance = ReapalUtlity.get_balance_Query();
//			String balance = resultBalance.get("balance") == null ? "0" : resultBalance.get("balance").toString();
//			BigDecimal accountTotal = BigDecimal.valueOf(Double.valueOf(balance));
//			if(pay.add(qeh.getPoundage()).compareTo(accountTotal) == 1){
//				qobtN.setStatus(ShbxOrderinfoByThirdpartyReturnStatus.FAIL);
//				qobtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
//				qobtN.setErrCode("");
//				qobtN.setErrCodeDes("提现账户余额不足！");
//				qobtN.setMessage("提现账户余额不足！");
//			} else {
//				//银行付款接口调用 返回成功后
//				WithdrawData wd = new WithdrawData(1, bankcardnum, qeb.getBindingCardCardholder(), bank.getName(), "", "", WithdrawDateProperties.PRIVATE, pay.setScale(2, BigDecimal.ROUND_HALF_UP), WithdrawDateCurrency.CNY, "", "", qeb.getBindingCardPhone(), "身份证", qe.getIdcard(), "", orderNumStr, "提现");
//				
//				List<WithdrawData> content = new ArrayList<WithdrawData>();
//				content.add(wd);
//				WithdrawDataArray wda = new WithdrawDataArray(content);
//				wda.setBatch_no(orderNumStr);
//				wda.setTrans_time(Utlity.timeSpanToString(qeh.getCreatetime()));
//				
//				Map<String, Object> result2 = ReapalUtlity.ShbxEmpWithdrawBatchSubmit(wda);
//				qobtN.setStatus(result2.get("result_code").toString());
//				qobtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
//				qobtN.setErrCode(result2.get("result_code") == null ? "" : result2.get("result_code").toString());
//				qobtN.setErrCodeDes(result2.get("result_msg") == null ? "" : result2.get("result_msg").toString());
//				qobtN.setMessage(result2.get("result_msg") == null ? "" : result2.get("result_msg").toString());
//			}
//			qeh.setOrderId(qobtN.getUuid());
//			qeh.setOrderNum(orderNumStr);
//			qeh.setStatus(ShbxUserHistoryStatus.TRANSACTING);
//			if(!ShbxOrderinfoByThirdpartyReturnStatus.REAPAL_CHECKED.equals(qobtN.getStatus()) && !ShbxOrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(qobtN.getStatus())){
//				qeh.setProcessingStatus(ShbxUserHistoryProcessStatus.UNPROCESS);//接口调用失败 进入人工处理流程，设置处理状态为未处理
//				count++;
//			} else {
//				qeh.setProcessingStatus(ShbxUserHistoryProcessStatus.PROCESSED);
//			}
//			this.ShbxOrderinfoByThirdpartyDAO.insert(qobtN);
//			this.shbxUserHistoryDAO.update(qeh);
//		}
		result.put("result", resultFlag);
		result.put("message", message);
		result.put("count", count);
		return result;
	}
	
	/**
	 * 批量更新操作
	 */
	@Override
	public void updateBatch(List<ShbxUserHistory> listUpdate) {
		for(ShbxUserHistory qeh : listUpdate){
			this.shbxUserHistoryDAO.update(qeh);
		}
	}
	
	@Override
	public void updateWithdraw(List<ShbxUserHistory> qehList) throws TransactionException, Exception {
		
		List<CompanyAccount> caList = new ArrayList<CompanyAccount>();
		List<CompanyAccountHistory> cahList = new ArrayList<CompanyAccountHistory>();
		Map<String, CompanyAccount> companyAccountMap = new HashMap<String, CompanyAccount>();
		PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
		PlatformAccount paf = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
		List<MobileCode> codeList = new ArrayList<MobileCode>();
		
//		for(ShbxUserHistory qeh :qehList){
//			CompanyAccount ca = null;
//			if(companyAccountMap.containsKey(qeh.getProcessCompanyAccount())){
//				ca = companyAccountMap.get(qeh.getProcessCompanyAccount());
//			} else {
//				ca = this.companyAccountDAO.get(qeh.getProcessCompanyAccount());
//			}
//			BigDecimal totalAmountf = paf.getTotalAmount();
//			
//			BigDecimal accountTotal = ca.getAccountBalance();
//			accountTotal = accountTotal.subtract(qeh.getPay());
//			
//			BigDecimal totalAmount = pa.getTotalAmount();
//			totalAmount = totalAmount.subtract(qeh.getPay());
//			
//			if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
//				accountTotal = accountTotal.subtract(qeh.getPoundage());
//				totalAmount = totalAmount.subtract(qeh.getPoundage());
//				totalAmountf = totalAmountf.subtract(qeh.getPoundage());
//			}
//			
//			ca.setAccountBalance(accountTotal);
//			//更新系统总帐户余额
//			pa.setTotalAmount(totalAmount);
//			paf.setTotalAmount(totalAmountf);
//			
//			//增加公司账户交易记录--用户提现
//			CompanyAccountHistory cat = new CompanyAccountHistory();
//			cat.setUuid(UUID.randomUUID().toString());
//			cat.setCompanyAccountOut(qeh.getProcessCompanyAccount());//用户提现
//			cat.setType(CompanyAccountHistoryType.TAKEOUT);
//			cat.setShbxUser(qeh.getShbxUser());
//			cat.setTotalAmount(qeh.getPay());
//			cat.setPoundage(qeh.getPoundage());
//			cat.setPurpose("用户提现");
//			cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
//			
//			cat.setCreator(qeh.getShbxUser());
//			cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			
//			cat.setShbxUserHistory(qeh.getUuid());
//			
//			//20180622增加记录本次余额信息
//			cat.setAccountBalance(ca.getAccountBalance().add(qeh.getPoundage()));
//			
//			if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
//				//增加公司账户交易记录--费用录入（扣除交易手续费）
//				CompanyAccountHistory catp = new CompanyAccountHistory();
//				catp.setUuid(UUID.randomUUID().toString());
//				catp.setCompanyAccountOut(qeh.getProcessCompanyAccount());//用户充值
//				catp.setType(CompanyAccountHistoryType.EXPEND);
//				catp.setShbxUser(qeh.getShbxUser());
//				catp.setTotalAmount(qeh.getPoundage());
//				catp.setPoundage(BigDecimal.ZERO);
//				catp.setPurpose("用户提现--手续费扣除");
//				catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
//				
//				catp.setCreator(qeh.getShbxUser());
//				catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
//				catp.setShbxUserHistory(qeh.getUuid());
//				catp.setRelated(cat.getUuid());
//				cat.setRelated(catp.getUuid());
//				
//				//20180622增加记录本次余额信息
//				catp.setAccountBalance(ca.getAccountBalance());
//				
//				cahList.add(catp);
//			}
//			companyAccountMap.put(ca.getUuid(), ca);
//			cahList.add(cat);
//			
//			ShbxUser qe = this.shbxUserDAO.get(qeh.getShbxUser());
//			if(qe == null){
//				throw new TransactionException("该用户不存在！");
//			}
//			
//			MobileCode mc = new MobileCode();
//			String content = "尊敬的用户您好：您于"+Utlity.timeSpanToChinaDateString(qeh.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(qeh.getPay())+"元提现申请已处理成功，请注意查询。";
//			String mobile = qe.getMobile();
//			String codeInfo = Utlity.getCaptcha();
//			mc.setCode(codeInfo);
//			mc.setContent(content);
//			mc.setMobile(mobile);
//			mc.setCreator(qe.getUuid());
//			mc.setStatus(MobileCodeStatus.DISABLE);
//			mc.setType(MobileCodeTypes.NOTICE);
//			mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			mc.setCreatorType(MobileCodeCreatorType.Shbx_EMP);
//			mc.setUuid(UUID.randomUUID().toString());
//			codeList.add(mc);
//		}
		
		if(!companyAccountMap.isEmpty()){
			for(CompanyAccount ca : companyAccountMap.values()){
				caList.add(ca);
			}
		}
		this.platformAccountDAO.update(pa);
		this.platformAccountDAO.update(paf);
		if(!qehList.isEmpty()){
			for(ShbxUserHistory qeh: qehList){
				this.shbxUserHistoryDAO.update(qeh);
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

//	@Override
//	public void insertProductBuy4Balance(MobileCode omc, BankFinancialProductPublish bfpp, ShbxUser qe, ShbxUserHistory qeh, ShbxUserProductBuyAgreement qepba, ShbxUserProductBuy qepb, Boolean isUpdate, Boolean isUpdate2, MobileCode mc, ShbxUserCoupon qec) throws TransactionException {
//		omc.setStatus(MobileCodeStatus.DISABLE);
//		this.mobileCodeDAO.update(omc);
//		
//		this.bankFinancialProductPublishDAO.update(bfpp);
//		this.shbxUserDAO.update(qe);
//		this.shbxUserHistoryDAO.insert(qeh);
//		
//		if(isUpdate){
//			this.shbxUserProductBuyDAO.update(qepb);
//			
//		} else {
//			this.shbxUserProductBuyDAO.insert(qepb);
//		}
//		if(shbxUserProductBuyAgreementDAO.getCheckScode(qepba.getScode())){
//			throw new TransactionException("手速太快，服务器未响应！");
//		}
//		if(isUpdate2){
//			this.shbxUserProductBuyAgreementDAO.update(qepba);
//		} else {
//			this.shbxUserProductBuyAgreementDAO.insert(qepba);
//		}
////		this.shbxUserInformationDAO.insert(iii);
//		
//		if(qec != null){
//			this.shbxUserCouponDAO.update(qec);
//			ShbxUserCouponHistory qech = new ShbxUserCouponHistory();
//			qech.setCoupon(qec.getCoupon());
//			qech.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			qech.setCreator(qe.getUuid());
//			qech.setPrice(qeh.getPay());
//			qech.setStatus(ShbxUserCouponHistoryStatus.NORMAL);
//			qech.setUuid(UUID.randomUUID().toString());
//			qech.setShbxUserProductBuy(qepb.getUuid());
//			qech.setShbxUserHistory(qeh.getUuid());
//			qech.setDividend(BigDecimal.ZERO);
//			this.shbxUserCouponHistoryDAO.insert(qech);
//		}
//		this.checkCouponStrategy(qe);
//		this.insertSmsInfo(mc);
//	}
	
	
	@Override
	public void updateWithdrawBatchRevoke(
			List<ShbxUserHistory> qehList) throws Exception {
		for(ShbxUserHistory qeh : qehList){
			this.shbxUserHistoryDAO.update(qeh);
			
			//返还用户资金
			ShbxUser qe = this.shbxUserDAO.get(qeh.getShbxUser());
			if(qe != null){
				BigDecimal accountBalance = qe.getAccountBalance();
				qe.setAccountBalance(accountBalance.add(qeh.getPay().add(qeh.getPoundage())));
				this.shbxUserDAO.update(qe);
			} else {
				throw new TransactionException("社保熊用户信息不存在！");
			}
		}
	}
	
	@Override
	public List<Entity> getListForCountPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return this.shbxUserHistoryDAO.getListForCountPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@SuppressWarnings("unused")
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
				status = ShbxOrderinfoByThirdpartyReturnStatus.SUCCESS;
			} else {
				status = ShbxOrderinfoByThirdpartyReturnStatus.FAIL;
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
			List<Entity> list = this.shbxOrderinfoByThirdpartyDAO.getListForPage(inputParams, -1, -1, null, ShbxOrderinfoByThirdparty.class);
			ShbxOrderinfoByThirdparty obt = null;
			if(list != null && list.size() > 0){
				obt = (ShbxOrderinfoByThirdparty) list.get(0);
				if(!ShbxOrderinfoByThirdpartyReturnStatus.SUCCESS.equals(obt.getStatus())){
					obt.setPaytime(new Timestamp(System.currentTimeMillis()));
					obt.setStatus(status);
					obt.setErrCodeDes(reason == null ? "" : reason);
					obt.setMessage(reason == null ? "" : reason);
					this.shbxOrderinfoByThirdpartyDAO.update(obt);
				}
			} else {
				message = "订单未找到";
				throw new TransactionException(message);
			}
			
			inputParams.clear();
			inputParams.put("order", obt.getUuid());
			inputParams.put("shbxUser", obt.getAccount());
			inputParams.put("orderType", ShbxUserHistoryOrderType.PAY_TYPE_REAPAL);
			inputParams.put("type", ShbxUserHistoryType.REPAYMENT);
			List<Entity> listHistory = this.shbxUserHistoryDAO.getListForPage(inputParams, -1, -1, null, ShbxUserHistory.class);
			if(listHistory != null && listHistory.size() > 0){
				ShbxUserHistory qeh = (ShbxUserHistory) listHistory.get(0);
				if(qeh == null){
					message = "订单不存在！"; 
					throw new TransactionException(message);
				}
				qeh = this.shbxUserHistoryDAO.get(qeh.getUuid());
				
				ShbxUser qe = this.shbxUserDAO.get(qeh.getShbxUser());
				if(!ShbxOrderinfoByThirdpartyReturnStatus.SUCCESS.equals(status)){//不是成功状态 算支付失败 进入人工付款流程
					if(ShbxOrderinfoByThirdpartyReturnStatus.FAIL.equals(status) || ShbxOrderinfoByThirdpartyReturnStatus.RETURN_TICKET.equals(status)){
						qeh.setProcessingStatus(ShbxUserHistoryProcessStatus.UNPROCESS);//接口调用失败 进入人工处理流程，设置处理状态为未处理
						qeh.setStatus(ShbxUserHistoryStatus.TRANSACTING);
						this.shbxUserHistoryDAO.update(qeh);
						
						message = "OK";
						resultFlag = true;
						result.put("result", resultFlag);
						result.put("message", message);
						return result;
					} else if (ShbxOrderinfoByThirdpartyReturnStatus.WITHDRAWAL_SUBMITTED.equals(status)) {
						qeh.setStatus(ShbxUserHistoryStatus.TRANSACTING);
						message = "交易进行中"; 
						throw new TransactionException(message);
					}
				}
				
				if(!ShbxUserHistoryStatus.SUCCESS.equals(qeh.getStatus())){
					if(ShbxOrderinfoByThirdpartyReturnStatus.SUCCESS.equals(status)){
//						qeh.setStatus(ShbxUserHistoryStatus.SUCCESS);
//						if(ShbxUserHistoryType.WITHDRAW.equals(qeh.getType())){
//							//更新企业账户余额
//							CompanyAccount ca = this.companyAccountDAO.get(qeh.getCompanyAccount());
//							PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
//							if(ca == null){
//								message = "账户信息错误";
//								throw new TransactionException(message);
//							}
//							
//							//更新系统可用余额
//							PlatformAccount paf = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
//							BigDecimal totalAmountf = paf.getTotalAmount();
//							
//							BigDecimal accountTotal = ca.getAccountBalance();
//							accountTotal = accountTotal.subtract(total_fee);
//							
//							BigDecimal totalAmount = pa.getTotalAmount();
//							totalAmount = totalAmount.subtract(total_fee);
//							
//							//20180228 更新用户提现手续费扣除用户余额
//							if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
//								accountTotal = accountTotal.subtract(qeh.getPoundage());
//								totalAmount = totalAmount.subtract(qeh.getPoundage());
//								totalAmountf = totalAmountf.subtract(qeh.getPoundage());
//							}
//							
//							ca.setAccountBalance(accountTotal);
//							//更新系统总帐户余额
//							pa.setTotalAmount(totalAmount);
//							paf.setTotalAmount(totalAmountf);
//							
//							//增加公司账户交易记录--用户提现
//							CompanyAccountHistory cat = new CompanyAccountHistory();
//							cat.setUuid(UUID.randomUUID().toString());
//							cat.setCompanyAccountOut(qeh.getCompanyAccount());//用户提现
//							cat.setType(CompanyAccountHistoryType.EMP_TAKEOUT);
//							cat.setShbxUser(qe.getUuid());
//							cat.setTotalAmount(total_fee);
//							cat.setPoundage(qeh.getPoundage());
//							cat.setPurpose("用户信用卡还款");
//							cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
//							
//							cat.setCreator(qe.getUuid());
//							cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
//							
//							cat.setShbxUserHistory(qeh.getUuid());
//							
//							//20180622增加记录本次余额信息
//							cat.setAccountBalance(ca.getAccountBalance().add(qeh.getPoundage()));
//							
//							this.companyAccountDAO.update(ca);
//							this.platformAccountDAO.update(pa);
//							this.platformAccountDAO.update(paf);
//							if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
//								//增加公司账户交易记录--费用录入（扣除交易手续费）
//								CompanyAccountHistory catp = new CompanyAccountHistory();
//								catp.setUuid(UUID.randomUUID().toString());
//								catp.setCompanyAccountOut(qeh.getCompanyAccount());
//								catp.setType(CompanyAccountHistoryType.EXPEND);
//								catp.setShbxUser(qe.getUuid());
//								catp.setTotalAmount(qeh.getPoundage());
//								catp.setPoundage(BigDecimal.ZERO);
//								catp.setPurpose("社保熊用户信用卡还款--手续费扣除");
//								catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
//								
//								catp.setCreator(qe.getUuid());
//								catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
//								catp.setShbxUserHistory(qeh.getUuid());
//								catp.setRelated(cat.getUuid());
//								cat.setRelated(catp.getUuid());
//								//20180622增加记录本次余额信息
//								catp.setAccountBalance(ca.getAccountBalance());
//								
//								this.companyAccountHistoryDAO.insert(cat);
//								this.companyAccountHistoryDAO.insert(catp);
//							}else{
//								this.companyAccountHistoryDAO.insert(cat);
//							}
//							//提现成功，发送通知短信
//							MobileCode mc = new MobileCode();
//							String content = "尊敬的用户您好：您于"+Utlity.timeSpanToChinaDateString(qeh.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(qeh.getPay())+"元信用卡还款申请已处理成功，请注意查询。";
//							String mobile = qe.getMobile();
//							String codeInfo = Utlity.getCaptcha();
//							mc.setCode(codeInfo);
//							mc.setContent(content);
//							mc.setMobile(mobile);
//							mc.setCreator(qe.getUuid());
//							mc.setStatus(MobileCodeStatus.DISABLE);
//							mc.setType(MobileCodeTypes.NOTICE);
//							mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
//							mc.setCreatorType(MobileCodeCreatorType.Shbx_EMP);
//							mc.setUuid(UUID.randomUUID().toString());
//							this.insertSmsInfo(mc);
//							
//							if(!Utlity.checkStringNull(qe.getOpenid())){
//								ShbxUserBankcard qeb = this.shbxUserBankcardDAO.get(qeh.getBankcard());
//								if(qeb != null){
//									String bankcard = qeb.getBindingBankCard();
//									ShbxMessageUtil.repaymentApplyTemplate(qe.getOpenid(), Utlity.numFormat4UnitDetail(qeh.getPay()), 
//											bankcard.substring(bankcard.length() - 4, bankcard.length()));
//								}
//							}
//						} else {
//							message = "账单类型错误";
//							throw new TransactionException(message);
//						}
					} else {
						message = "状态错误";
						throw new TransactionException(message);
					}
					this.shbxUserHistoryDAO.update(qeh);
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
	public void insertwechart(ShbxOrderinfoByThirdparty qobt, ShbxUserHistory qeh, ShbxUserBankcard qeb, ShbxSecurityOrder sso) {
		this.shbxOrderinfoByThirdpartyDAO.insert(qobt);
		this.shbxUserHistoryDAO.insert(qeh);
		this.shbxUserBankcardDAO.update(qeb);
		this.shbxSecurityOrderDAO.insert(sso);
	}
	
	@Override
	public void insertwechart(ShbxOrderinfoByThirdparty qobt, ShbxUserHistory qeh, ShbxSecurityOrder sso) {
		this.shbxOrderinfoByThirdpartyDAO.insert(qobt);
		this.shbxUserHistoryDAO.insert(qeh);
		this.shbxSecurityOrderDAO.insert(sso);
	}

	@SuppressWarnings("unused")
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
		List<Entity> list = this.shbxOrderinfoByThirdpartyDAO.getListForPage(inputParams, -1, -1, null, ShbxOrderinfoByThirdparty.class);
		ShbxOrderinfoByThirdparty qobt = null;
		if(list != null && list.size() > 0){
			qobt = (ShbxOrderinfoByThirdparty) list.get(0);
			if(!ShbxOrderinfoByThirdpartyReturnStatus.TRADE_FINISHED.equals(qobt.getStatus())){
				qobt.setPayNum(transaction_id);
//				obt.setPaytime(new Timestamp(Utlity.stringToDateFull(time_end).getTime()));
				qobt.setStatus(result_status);
				qobt.setErrCode(result_code);
				qobt.setErrCodeDes(return_msg);
				qobt.setMessage(return_msg == null ? "" : return_msg);
				this.shbxOrderinfoByThirdpartyDAO.update(qobt);
			}
		} else {
			message = "订单未找到";
			throw new TransactionException(message);
		}

		inputParams.clear();
		inputParams.put("order", qobt.getUuid());
		inputParams.put("shbxUser", qobt.getAccount());
		inputParams.put("orderType", ShbxUserHistoryOrderType.PAY_TYPE_REAPAL);
		inputParams.put("type", ShbxUserHistoryType.BUY_SHBX);
		List<Entity> listHistory = this.shbxUserHistoryDAO.getListForPage(inputParams, -1, -1, null, ShbxUserHistory.class);
		if(listHistory != null && listHistory.size() > 0){
			ShbxUserHistory qeh = (ShbxUserHistory) listHistory.get(0);
			if(qeh == null){
				message = "账单错误";
				throw new TransactionException(message);
			}
			
			if(!ShbxUserHistoryStatus.SUCCESS.equals(qeh.getStatus())){
//				ShbxUser qe = this.shbxUserDAO.get(qeh.getShbxUser());
//				if(ShbxOrderinfoByThirdpartyReturnStatus.TRADE_SUCCESS.equals(result_status) || 
//						ShbxOrderinfoByThirdpartyReturnStatus.TRADE_FINISHED.equals(result_status)){
//					qeh.setStatus(ShbxUserHistoryStatus.SUCCESS);
//					//只更新账户总资产
//					BigDecimal total = qe.getAccountBalance();
//					total = total.add(total_fee);
//					qe.setAccountBalance(total);//更新余额
//					
//					//更新企业账户余额
//					CompanyAccount ca = this.companyAccountDAO.get(qeh.getCompanyAccount());
//					PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
//					if(ca == null){
//						message = "账户信息错误";
//						throw new TransactionException(message);
//					}
//					//更新系统可用余额
//					PlatformAccount paf = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
//					BigDecimal totalAmountf = paf.getTotalAmount();
////					totalAmountf = totalAmountf.add(total_fee);
//					
//					BigDecimal accountTotal = ca.getAccountBalance();
//					accountTotal = accountTotal.add(total_fee);
//					
//					BigDecimal totalAmount = pa.getTotalAmount();
//					totalAmount = totalAmount.add(total_fee);
//					
//					if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
//						accountTotal = accountTotal.subtract(qeh.getPoundage());
//						totalAmount = totalAmount.subtract(qeh.getPoundage());
//						totalAmountf = totalAmountf.subtract(qeh.getPoundage());
//					}
//					ca.setAccountBalance(accountTotal);
//					//更新系统总帐户余额
//					pa.setTotalAmount(totalAmount);
//					paf.setTotalAmount(totalAmountf);
//					
//					//增加公司账户交易记录--用户充值
//					CompanyAccountHistory cat = new CompanyAccountHistory();
//					cat.setUuid(UUID.randomUUID().toString());
//					cat.setCompanyAccountIn(qeh.getCompanyAccount());//用户充值
//					cat.setType(CompanyAccountHistoryType.EMP_FILLIN);
//					cat.setShbxUser(qe.getUuid());
//					cat.setTotalAmount(total_fee);
//					cat.setPoundage(qeh.getPoundage());
//					cat.setPurpose("社保熊用户充值");
//					cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
//					
//					cat.setCreator(qe.getUuid());
//					cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
//					cat.setShbxUserHistory(qeh.getUuid());
//					
//					//20180622增加记录本次余额信息
//					cat.setAccountBalance(ca.getAccountBalance().add(qeh.getPoundage()));
//					
//					this.shbxUserDAO.update(qe);
//					this.companyAccountDAO.update(ca);
//					this.platformAccountDAO.update(pa);
//					this.platformAccountDAO.update(paf);
//					this.companyAccountHistoryDAO.insert(cat);
//					
//					if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
//						//增加公司账户交易记录--费用录入（扣除交易手续费）
//						CompanyAccountHistory catp = new CompanyAccountHistory();
//						catp.setUuid(UUID.randomUUID().toString());
//						catp.setCompanyAccountOut(qeh.getCompanyAccount());//用户充值
//						catp.setType(CompanyAccountHistoryType.EXPEND);
//						catp.setShbxUser(qe.getUuid());
//						catp.setTotalAmount(qeh.getPoundage());
//						catp.setPoundage(BigDecimal.ZERO);
//						catp.setPurpose("社保熊用户充值--手续费扣除");
//						catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
//						
//						catp.setCreator(qe.getUuid());
//						catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
//						catp.setShbxUserHistory(qeh.getUuid());
//						catp.setRelated(cat.getUuid());
//						cat.setRelated(catp.getUuid());
//						//20180622增加记录本次余额信息
//						catp.setAccountBalance(ca.getAccountBalance());
//						
//						this.companyAccountHistoryDAO.insert(cat);
//						this.companyAccountHistoryDAO.insert(catp);
//					}else{
//						this.companyAccountHistoryDAO.insert(cat);
//					}
//					
//					//充值成功，发送通知短信
//					MobileCode mc = new MobileCode();
//					String content = "尊敬的用户您好：您于"+Utlity.timeSpanToChinaDateString(qeh.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(qeh.getIncome())+"元充值申请已处理成功，请注意查询余额。";
//					String mobile = qe.getMobile();
//					String codeInfo = Utlity.getCaptcha();
//					mc.setCode(codeInfo);
//					mc.setContent(content);
//					mc.setMobile(mobile);
//					mc.setCreator(qe.getUuid());
//					mc.setStatus(MobileCodeStatus.DISABLE);
//					mc.setType(MobileCodeTypes.NOTICE);
//					mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
//					mc.setCreatorType(MobileCodeCreatorType.Shbx_EMP);
//					mc.setUuid(UUID.randomUUID().toString());
//					this.insertSmsInfo(mc);
//					
////					content = "【牛投理财】"+content;
////					InvestorInformation ii = new InvestorInformation();
////					ii.setCreator(qe.getUuid());
////					ii.setStatus(InvestorInformationStatus.UNREAD);
////					ii.setCreatetime(new Timestamp(System.currentTimeMillis()));
////					ii.setUuid(UUID.randomUUID().toString());
////					ii.setInfoText(content);
////					ii.setInfoTitle(InvestorInformationTitle.RECHARGE);
////					ii.set-(investor.getUuid());
////					this.investorInformationDAO.insert(ii);
//				} else {
//					if(ShbxOrderinfoByThirdpartyReturnStatus.TRADE_CLOSED.equals(result_status)){
//						qeh.setStatus(ShbxUserHistoryStatus.CLOSED);
//					} else {
//						qeh.setStatus(ShbxUserHistoryStatus.FAIL);
//					}
//				}
//				this.shbxUserHistoryDAO.update(qeh);
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
	public void updateWithdraw(ShbxOrderinfoByThirdparty obt,
			ShbxOrderinfoByThirdparty obtN, ShbxUserHistory qeh, ShbxSecurityOrder sso) {
		if(obt != null){
			this.shbxOrderinfoByThirdpartyDAO.update(obt);
		}
		this.shbxOrderinfoByThirdpartyDAO.insert(obtN);
		if(qeh != null){
			this.shbxUserHistoryDAO.update(qeh);
		}
		if(sso != null){
			this.shbxSecurityOrderDAO.update(sso);
		}
	}

	@Override
	public Integer getCountForWithdraw(Map<String, String> inputParams) {
		return this.shbxUserHistoryDAO.getCountForWithdraw(inputParams);
	}

	@Override
	public HashMap<String, Object> insertReapalNotice4RechargeShbx(
			Map<String, Object> map) throws ParseException,
			TransactionException, NumberFormatException, Exception {
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
		List<Entity> list = this.shbxOrderinfoByThirdpartyDAO.getListForPage(inputParams, -1, -1, null, ShbxOrderinfoByThirdparty.class);
		ShbxOrderinfoByThirdparty qobt = null;
		if(list != null && list.size() > 0){
			qobt = (ShbxOrderinfoByThirdparty) list.get(0);
			if(!ShbxOrderinfoByThirdpartyReturnStatus.TRADE_FINISHED.equals(qobt.getStatus())){
				qobt.setPayNum(transaction_id);
				qobt.setStatus(result_status);
				qobt.setErrCode(result_code);
				qobt.setErrCodeDes(return_msg);
				qobt.setMessage(return_msg == null ? "" : return_msg);
				this.shbxOrderinfoByThirdpartyDAO.update(qobt);
			}
		} else {
			message = "订单未找到";
			throw new TransactionException(message);
		}

		inputParams.clear();
		inputParams.put("order", qobt.getUuid());
		inputParams.put("shbxUser", qobt.getAccount());
		inputParams.put("orderType", ShbxUserHistoryOrderType.PAY_TYPE_REAPAL);
		inputParams.put("type", ShbxUserHistoryType.BUY_SHBX);
		List<Entity> listHistory = this.shbxUserHistoryDAO.getListForPage(inputParams, -1, -1, null, ShbxUserHistory.class);
		if(listHistory != null && listHistory.size() > 0){
			ShbxUserHistory qeh = (ShbxUserHistory) listHistory.get(0);
			if(qeh == null){
				message = "账单错误";
				throw new TransactionException(message);
			}
			
			if(!ShbxUserHistoryStatus.SUCCESS.equals(qeh.getStatus())){
				ShbxUser qe = this.shbxUserDAO.get(qeh.getShbxUser());
				if(ShbxOrderinfoByThirdpartyReturnStatus.TRADE_SUCCESS.equals(result_status) || 
						ShbxOrderinfoByThirdpartyReturnStatus.TRADE_FINISHED.equals(result_status)){
					qeh.setStatus(ShbxUserHistoryStatus.SUCCESS);
//					//只更新账户总资产
//					BigDecimal total = qe.getAccountBalance();
//					total = total.add(total_fee);
//					qe.setAccountBalance(total);//更新余额
					
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
					cat.setType(CompanyAccountHistoryType.SHBX_PAY_SHEBAO);
					cat.setShbxUser(qe.getUuid());
					cat.setTotalAmount(total_fee);
					cat.setPoundage(qeh.getPoundage());
					cat.setPurpose("社保熊用户缴费");
					cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
					
					cat.setCreator(qe.getUuid());
					cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
					cat.setShbxUserHistory(qeh.getUuid());
					
					//20180622增加记录本次余额信息
					cat.setAccountBalance(ca.getAccountBalance().add(qeh.getPoundage()));
					
					//更新缴费信息状态
					ShbxSecurityOrder sso = this.shbxSecurityOrderDAO.get(qeh.getShbxSecurityOrder());
					if(sso != null){
						sso.setStatus(ShbxSecurityOrderStatus.UNPROCESS);
						this.shbxSecurityOrderDAO.update(sso);
					}
					
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
						catp.setShbxUser(qe.getUuid());
						catp.setTotalAmount(qeh.getPoundage());
						catp.setPoundage(BigDecimal.ZERO);
						catp.setPurpose("社保熊用户充值--手续费扣除");
						catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
						
						catp.setCreator(qe.getUuid());
						catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
						catp.setShbxUserHistory(qeh.getUuid());
						catp.setRelated(cat.getUuid());
						cat.setRelated(catp.getUuid());
						//20180622增加记录本次余额信息
						catp.setAccountBalance(ca.getAccountBalance());
						
						this.companyAccountHistoryDAO.insert(cat);
						this.companyAccountHistoryDAO.insert(catp);
					}else{
						this.companyAccountHistoryDAO.insert(cat);
					}
					
				} else {
					if(ShbxOrderinfoByThirdpartyReturnStatus.TRADE_CLOSED.equals(result_status)){
						qeh.setStatus(ShbxUserHistoryStatus.CLOSED);
						//更新缴费信息状态
						ShbxSecurityOrder sso = this.shbxSecurityOrderDAO.get(qeh.getShbxSecurityOrder());
						if(sso != null){
							sso.setStatus(ShbxSecurityOrderStatus.DELETED);
							this.shbxSecurityOrderDAO.update(sso);
						}
					} else {
						qeh.setStatus(ShbxUserHistoryStatus.FAIL);
						//更新缴费信息状态
						ShbxSecurityOrder sso = this.shbxSecurityOrderDAO.get(qeh.getShbxSecurityOrder());
						if(sso != null){
							sso.setStatus(ShbxSecurityOrderStatus.FAIL);
							this.shbxSecurityOrderDAO.update(sso);
						}
					}
				}
				this.shbxUserHistoryDAO.update(qeh);
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
}
