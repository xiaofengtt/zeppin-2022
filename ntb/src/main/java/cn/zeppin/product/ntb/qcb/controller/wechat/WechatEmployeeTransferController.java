/**
 * 
 */
package cn.zeppin.product.ntb.qcb.controller.wechat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.CompanyAccount.CompanyAccountUuid;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard.QcbEmployeeBankcardStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard.QcbEmployeeBankcardType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryProcessStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty.QcbOrderinfoByThirdpartyAccountType;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty.QcbOrderinfoByThirdpartyReturnStatus;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty.QcbOrderinfoByThirdpartyType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeBankcardService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.reapal.ReapalUtlity;
import cn.zeppin.product.utility.reapal.data.WithdrawData;
import cn.zeppin.product.utility.reapal.data.WithdrawData.WithdrawDateCurrency;
import cn.zeppin.product.utility.reapal.data.WithdrawData.WithdrawDateProperties;
import cn.zeppin.product.utility.reapal.data.WithdrawDataArray;

/**
 * @author hehe
 *
 * 微信企财宝员工接口
 */

@Controller
@RequestMapping(value = "/qcbWechat/employeeTransfer")
public class WechatEmployeeTransferController extends BaseController {
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	@Autowired
	private IQcbEmployeeHistoryService qcbEmployeeHistoryService;
	
	@Autowired
	private IQcbEmployeeBankcardService qcbEmployeeBankcardService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	/**
	 * 用户提现
	 * @param price
	 * @param bankcard
	 * @param mobileCode
	 * @return
	 */
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	@ActionParam(key = "price", type = DataType.STRING, message = "提现金额", required = true)
	@ActionParam(key = "bankcard", type = DataType.STRING, message = "提现银行卡编号", required = true)
	@ActionParam(key = "mobileCode", type = DataType.STRING, message = "手机验证码", required = true)
	@ResponseBody
	protected Result withdraw(String bankcard, String mobileCode, String price, ServletRequest request){
		
		synchronized(this){
			if(request.getAttribute("employee") == null){
				return ResultManager.createFailResult("用户信息不存在！");
			}
			QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
			
			QcbEmployeeBankcard qcb = this.qcbEmployeeBankcardService.get(bankcard);
			if(qcb == null){
				return ResultManager.createFailResult("未绑定该银行卡!");
			}
			if(!qe.getUuid().equals(qcb.getQcbEmployee())){
				return ResultManager.createFailResult("提现银行卡信息错误!");
			}
			if(!QcbEmployeeBankcardStatus.NORMAL.equals(qcb.getStatus())){
				return ResultManager.createFailResult("提现银行卡信息错误!");
			}
			
			Bank bank = this.bankService.get(qcb.getBank());
			if(bank == null){
				return ResultManager.createFailResult("提现银行错误!");
			}
			mobileCode = Base64Util.getFromBase64(mobileCode);
			
			price = Base64Util.getFromBase64(price);
			if (!Utlity.isPositiveCurrency4Web(price)) {
				return ResultManager.createFailResult("提现金额输入错误!");
			}
			BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));
			pay = pay.divide(BigDecimal.valueOf(100));
			if(pay.compareTo(Utlity.REAPAL_MAX_WITHDRAW) == 1){
				return ResultManager.createFailResult("提现金额不能高于500w!");
			}
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("mobile", qe.getMobile());
			inputParams.put("status", MobileCodeStatus.NORMAL);
			inputParams.put("type", MobileCodeTypes.EMP_WITHDRAW);
			List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(inputParams, -1, -1, null, MobileCode.class);
			MobileCode mc = null;
			if(lstMobileCode != null && lstMobileCode.size() > 0){
				mc = (MobileCode) lstMobileCode.get(0);
			}
			if(mc == null){
				return ResultManager.createFailResult("请先获取验证码");
			}
			if(!MobileCodeTypes.EMP_WITHDRAW.equals(mc.getType())){
				return ResultManager.createFailResult("验证码输入错误！");
			}
			
			if(!mobileCode.equals(mc.getCode())){
				return ResultManager.createFailResult("验证码输入错误！");
			}
			String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_QCB,Utlity.BILL_PAYTYPE_REAPAL,Utlity.BILL_PURPOSE_WITHDRAW);
			if(this.qcbEmployeeHistoryService.checkOrderNum(orderNumStr)){
				return ResultManager.createFailResult("手速太快，服务器未响应！");
			}
			
			//验证账户余额是否充足
			BigDecimal total = qe.getAccountBalance();
			if(total.compareTo(pay.add(Utlity.WITHDRAW_POUNDAGE)) == -1){
				return ResultManager.createFailResult("超出账户余额!");
			}
			
			QcbOrderinfoByThirdparty obtN = new QcbOrderinfoByThirdparty();
			obtN.setUuid(UUID.randomUUID().toString());
			obtN.setType(QcbOrderinfoByThirdpartyType.REAPAL);
			obtN.setAccount(qe.getUuid());
			obtN.setAccountType(QcbOrderinfoByThirdpartyAccountType.EMPLOYEE);
			obtN.setOrderNum(orderNumStr);
			obtN.setBody("融宝支付--用户提现");
			obtN.setTotalFee(pay);
			obtN.setPaySource(MD5.getMD5(qe.getUuid()));

			
			//增加提现记录
			QcbEmployeeHistory qeh = new QcbEmployeeHistory();
			qeh.setUuid(UUID.randomUUID().toString());
			qeh.setQcbEmployee(qe.getUuid());
			qeh.setPay(pay);
			qeh.setIncome(BigDecimal.ZERO);
			qeh.setOrderId(obtN.getUuid());
			qeh.setOrderType(QcbEmployeeHistoryOrderType.PAY_TYPE_REAPAL);
			qeh.setOrderNum(orderNumStr);
			qeh.setType(QcbEmployeeHistoryType.WITHDRAW);
			qeh.setStatus(QcbEmployeeHistoryStatus.TRANSACTING);
			qeh.setCreatetime(new Timestamp(System.currentTimeMillis()));
			qeh.setAccountBalance(total.subtract(pay).subtract(Utlity.WITHDRAW_POUNDAGE));
			qeh.setCompanyAccount(CompanyAccountUuid.REAPAL);
			qeh.setPoundage(Utlity.WITHDRAW_POUNDAGE);
			
			qeh.setBankcard(qcb.getUuid());
			try {
				Map<String, Object> resultBalance = ReapalUtlity.get_balance_Query();
				String balance = resultBalance.get("balance") == null ? "0" : resultBalance.get("balance").toString();
				BigDecimal accountTotal = BigDecimal.valueOf(Double.valueOf(balance));
				if(pay.add(qeh.getPoundage()).compareTo(accountTotal) == 1){
					obtN.setStatus(QcbOrderinfoByThirdpartyReturnStatus.FAIL);
					obtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
					obtN.setErrCode("");
					obtN.setErrCodeDes("提现账户余额不足！");
					obtN.setMessage("提现账户余额不足！");
				} else {
					//银行付款接口调用 返回成功后
					String idcardType = "身份证";
					if(qe.getIdcard().length() != 18 && qe.getIdcard().length() != 15){
						idcardType = "台胞证";
					}
					WithdrawData wd = new WithdrawData(1, qcb.getBindingBankCard(), qcb.getBindingCardCardholder(), bank.getName(), "", "", WithdrawDateProperties.PRIVATE, pay.setScale(2, BigDecimal.ROUND_HALF_UP), WithdrawDateCurrency.CNY, "", "", qcb.getBindingCardPhone(), idcardType, qe.getIdcard(), "", orderNumStr, "提现");
					
					List<WithdrawData> content = new ArrayList<WithdrawData>();
					content.add(wd);
					WithdrawDataArray wda = new WithdrawDataArray(content);
					wda.setBatch_no(orderNumStr);
					wda.setTrans_time(Utlity.timeSpanToString(qeh.getCreatetime()));
					Map<String, Object> result = ReapalUtlity.qcbEmpWithdrawBatchSubmit(wda);
					
					obtN.setStatus(result.get("result_code").toString());
					obtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
					obtN.setErrCode(result.get("result_code") == null ? "" : result.get("result_code").toString());
					obtN.setErrCodeDes(result.get("result_msg") == null ? "" : result.get("result_msg").toString());
					obtN.setMessage(result.get("result_msg") == null ? "" : result.get("result_msg").toString());
				}
				
				if(!QcbOrderinfoByThirdpartyReturnStatus.REAPAL_CHECKED.equals(obtN.getStatus()) && !QcbOrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(obtN.getStatus())){
					qeh.setStatus(QcbEmployeeHistoryStatus.TRANSACTING);
					qeh.setProcessingStatus(QcbEmployeeHistoryProcessStatus.UNPROCESS);//接口调用失败 进入人工处理流程，设置处理状态为未处理
					//减账户余额 和账户总资金
					total = total.subtract(pay).subtract(Utlity.WITHDRAW_POUNDAGE);
					qe.setAccountBalance(total);
					this.qcbEmployeeHistoryService.insertWithdraw(qe, obtN, qeh);
				} else {
					//减账户余额 和账户总资金
					total = total.subtract(pay).subtract(Utlity.WITHDRAW_POUNDAGE);
					qe.setAccountBalance(total);
					this.qcbEmployeeHistoryService.insertWithdraw(qe, obtN, qeh);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult("提现失败，请重新发起！");
			} finally {
				mc.setStatus(MobileCodeStatus.DISABLE);
				this.mobileCodeService.update(mc);
			}
		}
		return ResultManager.createSuccessResult("提现成功！");
	}
	
	/**
	 * 用户信用卡还款
	 * @param price
	 * @param bankcard
	 * @param mobileCode
	 * @return
	 */
	@RequestMapping(value = "/repayment", method = RequestMethod.POST)
	@ActionParam(key = "price", type = DataType.STRING, message = "还款金额", required = true)
	@ActionParam(key = "bankcard", type = DataType.STRING, message = "还款信用卡编号", required = true)
	@ActionParam(key = "mobileCode", type = DataType.STRING, message = "手机验证码", required = true)
	@ResponseBody
	protected Result repayment(String bankcard, String mobileCode, String price, ServletRequest request){
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		QcbEmployeeBankcard qcb = this.qcbEmployeeBankcardService.get(bankcard);
		if(qcb == null){
			return ResultManager.createFailResult("未绑定该银行卡!");
		}
		if(!qe.getUuid().equals(qcb.getQcbEmployee())){
			return ResultManager.createFailResult("还款银行卡信息错误!");
		}
		if(!QcbEmployeeBankcardStatus.NORMAL.equals(qcb.getStatus())){
			return ResultManager.createFailResult("还款银行卡信息错误!");
		}
		if(!QcbEmployeeBankcardType.CREDIT.equals(qcb.getType())){
			return ResultManager.createFailResult("还款银行卡信息错误!");
		}
		
		Bank bank = this.bankService.get(qcb.getBank());
		if(bank == null){
			return ResultManager.createFailResult("还款银行错误!");
		}
		mobileCode = Base64Util.getFromBase64(mobileCode);
		
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency4Web(price)) {
			return ResultManager.createFailResult("还款金额输入错误!");
		}
		BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));
		pay = pay.divide(BigDecimal.valueOf(100));
		if(pay.compareTo(Utlity.REAPAL_MAX_WITHDRAW) == 1){
			return ResultManager.createFailResult("还款金额不能高于500w!");
		}
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("mobile", qe.getMobile());
		inputParams.put("status", MobileCodeStatus.NORMAL);
		inputParams.put("type", MobileCodeTypes.EMP_REPAYMENT);
		List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(inputParams, -1, -1, null, MobileCode.class);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			return ResultManager.createFailResult("请先获取验证码");
		}
		if(!MobileCodeTypes.EMP_REPAYMENT.equals(mc.getType())){
			return ResultManager.createFailResult("验证码输入错误！");
		}
		
		if(!mobileCode.equals(mc.getCode())){
			return ResultManager.createFailResult("验证码输入错误！");
		}
		String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_QCB,Utlity.BILL_PAYTYPE_REAPAL,Utlity.BILL_PURPOSE_REPAYMENT);
		if(this.qcbEmployeeHistoryService.checkOrderNum(orderNumStr)){
			return ResultManager.createFailResult("手速太快，服务器未响应！");
		}
		
		//验证账户余额是否充足
		BigDecimal total = qe.getAccountBalance();
		if(total.compareTo(pay) == -1){
			return ResultManager.createFailResult("超出账户余额!");
		}
		
		QcbOrderinfoByThirdparty obtN = new QcbOrderinfoByThirdparty();
		obtN.setUuid(UUID.randomUUID().toString());
		obtN.setType(QcbOrderinfoByThirdpartyType.REAPAL);
		obtN.setAccount(qe.getUuid());
		obtN.setAccountType(QcbOrderinfoByThirdpartyAccountType.EMPLOYEE);
		obtN.setOrderNum(orderNumStr);
		obtN.setBody("融宝支付--用户信用卡还款");
		obtN.setTotalFee(pay);
		obtN.setPaySource(MD5.getMD5(qe.getUuid()));

		
		//增加交易记录
		QcbEmployeeHistory qeh = new QcbEmployeeHistory();
		qeh.setUuid(UUID.randomUUID().toString());
		qeh.setQcbEmployee(qe.getUuid());
		qeh.setPay(pay);
		qeh.setIncome(BigDecimal.ZERO);
		qeh.setOrderId(obtN.getUuid());
		qeh.setOrderType(QcbEmployeeHistoryOrderType.PAY_TYPE_REAPAL);
		qeh.setOrderNum(orderNumStr);
		qeh.setType(QcbEmployeeHistoryType.REPAYMENT);
		qeh.setStatus(QcbEmployeeHistoryStatus.TRANSACTING);
		qeh.setCreatetime(new Timestamp(System.currentTimeMillis()));
		qeh.setAccountBalance(total.subtract(pay));
		qeh.setCompanyAccount(CompanyAccountUuid.REAPAL);
		qeh.setPoundage(Utlity.WITHDRAW_POUNDAGE);
		
		qeh.setBankcard(qcb.getUuid());
		try {
			Map<String, Object> resultBalance = ReapalUtlity.get_balance_Query();
			String balance = resultBalance.get("balance") == null ? "0" : resultBalance.get("balance").toString();
			BigDecimal accountTotal = BigDecimal.valueOf(Double.valueOf(balance));
			if(pay.add(qeh.getPoundage()).compareTo(accountTotal) == 1){
				obtN.setStatus(QcbOrderinfoByThirdpartyReturnStatus.FAIL);
				obtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
				obtN.setErrCode("");
				obtN.setErrCodeDes("还款账户余额不足！");
				obtN.setMessage("还款账户余额不足！");
			} else {
				//银行付款接口调用 返回成功后
				WithdrawData wd = new WithdrawData(1, qcb.getBindingBankCard(), qcb.getBindingCardCardholder(), bank.getName(), "", "", WithdrawDateProperties.PRIVATE, pay.setScale(2, BigDecimal.ROUND_HALF_UP), WithdrawDateCurrency.CNY, "", "", "", "身份证", qe.getIdcard(), "", orderNumStr, "还款");
				
				List<WithdrawData> content = new ArrayList<WithdrawData>();
				content.add(wd);
				WithdrawDataArray wda = new WithdrawDataArray(content);
				wda.setBatch_no(orderNumStr);
				wda.setTrans_time(Utlity.timeSpanToString(qeh.getCreatetime()));
				Map<String, Object> result = ReapalUtlity.qcbEmpRepaymentBatchSubmit(wda);
				
				obtN.setStatus(result.get("result_code").toString());
				obtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
				obtN.setErrCode(result.get("result_code") == null ? "" : result.get("result_code").toString());
				obtN.setErrCodeDes(result.get("result_msg") == null ? "" : result.get("result_msg").toString());
				obtN.setMessage(result.get("result_msg") == null ? "" : result.get("result_msg").toString());
			}
			
			if(!QcbOrderinfoByThirdpartyReturnStatus.REAPAL_CHECKED.equals(obtN.getStatus()) && !QcbOrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(obtN.getStatus())){
				qeh.setStatus(QcbEmployeeHistoryStatus.TRANSACTING);
				qeh.setProcessingStatus(QcbEmployeeHistoryProcessStatus.UNPROCESS);//接口调用失败 进入人工处理流程，设置处理状态为未处理
				//减账户余额 和账户总资金
//				total = total.subtract(pay).subtract(Utlity.WITHDRAW_POUNDAGE);
				qe.setAccountBalance(total);
				this.qcbEmployeeHistoryService.insertWithdraw(qe, obtN, qeh);
			} else {
				//减账户余额 和账户总资金
//				total = total.subtract(pay).subtract(Utlity.WITHDRAW_POUNDAGE);
				qe.setAccountBalance(total);
				this.qcbEmployeeHistoryService.insertWithdraw(qe, obtN, qeh);
			}
			return ResultManager.createSuccessResult("提现成功！");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("提现失败，请重新发起！");
		} finally {
			mc.setStatus(MobileCodeStatus.DISABLE);
			this.mobileCodeService.update(mc);
		}
	}
}
