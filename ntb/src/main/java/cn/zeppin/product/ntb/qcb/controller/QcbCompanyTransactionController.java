/**
 * 
 */
package cn.zeppin.product.ntb.qcb.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.security.SecurityByQcbRealm;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkAreaService;
import cn.zeppin.product.ntb.backadmin.service.api.IBranchBankService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.BranchBank;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.CompanyAccount.CompanyAccountUuid;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryProcessStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyBankcard;
import cn.zeppin.product.ntb.core.entity.QcbCompanyBankcard.QcbCompanyBankcardType;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty.QcbOrderinfoByThirdpartyAccountType;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty.QcbOrderinfoByThirdpartyReturnStatus;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty.QcbOrderinfoByThirdpartyType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyBankcardService;
import cn.zeppin.product.ntb.qcb.vo.AdminVO;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyTransferVO;
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
 * 企财宝企业交易
 */

@Controller
@RequestMapping(value = "/qcb/companyTransaction")
public class QcbCompanyTransactionController extends BaseController {
	
	@Autowired
	private IQcbCompanyBankcardService qcbCompanyBankcardService;
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbCompanyAccountHistoryService qcbCompanyAccountHistoryService;
	
	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private IBkAreaService bkAreaService;
	
	@Autowired
	private IQcbAdminService qcbAdminService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IBranchBankService branchBankService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	
	/**
	 * 企财宝账户交易信息获取
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//校验企业是否存在
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(admin.getQcbCompany());
		if(qca == null){
			return ResultManager.createFailResult("企业不正确！");
		}
		
		QcbCompanyAccountHistory qcah = this.qcbCompanyAccountHistoryService.get(uuid);
		if(qcah == null){
			return ResultManager.createFailResult("账户历史信息不存在！");
		}
		
		if(!qcah.getQcbCompany().equals(qca.getUuid())){
			return ResultManager.createFailResult("无权查看该企业账务信息！");
		}
		
		QcbCompanyTransferVO vo = new QcbCompanyTransferVO(qcah);
		vo.setQcbCompanyId(qca.getMerchantId());
		if(!Utlity.checkStringNull(qcah.getCompanyAccount())){
			CompanyAccount ca = this.companyAccountService.get(qcah.getCompanyAccount());
			if(ca == null){
				return ResultManager.createFailResult("账户信息错误！");
			}
			vo.setCompanyAccountName(ca.getAccountName());
			vo.setCompanyAccountNum(ca.getAccountNum());
		} else {
			vo.setCompanyAccountName("");
			vo.setCompanyAccountNum("");
		}
		
		if(!Utlity.checkStringNull(qcah.getBankcard())){
			QcbCompanyBankcard qcb = this.qcbCompanyBankcardService.get(qcah.getBankcard());
			if(qcb == null){
				return ResultManager.createFailResult("银行卡信息错误！");
			}
			vo.setQcbCompanyBankcardName(qcb.getBindingCardCardholder());
			vo.setQcbCompanyBankcardNum(qcb.getBindingBankCard());
			BranchBank bb = this.branchBankService.get(qcb.getBranchBank());
			if(bb != null){
				vo.setQcbCompanyBankcardAddress(bb.getAddress());
			}
		}
		
		if(!Utlity.checkStringNull(qcah.getCreator())){
			QcbAdmin qa = this.qcbAdminService.get(qcah.getCreator());
			if(qa == null){
				return ResultManager.createFailResult("审批人信息错误！");
			}
			vo.setCreatorName(qa.getName());
		}
		
		return ResultManager.createDataResult(vo);
	}
	/**
	 * 企财宝账户提现
	 * @param bankcard
	 * @param totalAmount
	 * @param remark
	 * @param flagSms
	 * @param code
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	@ActionParam(key = "bankcard", message="银行卡", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "totalAmount", message="提现金额", type = DataType.STRING, required = true)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "flagSms", message="短信通知", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "code", message="短信验证码", type = DataType.STRING, required = true)
	@ActionParam(key = "password", message="密码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result withdraw(String bankcard, String totalAmount, String remark, Boolean flagSms, String code, String password) {
		//验证提现金额
		totalAmount = Base64Util.getFromBase64(totalAmount);
		password = Base64Util.getFromBase64(password);
		
		if (!Utlity.isPositiveCurrency4Web(totalAmount)) {
			return ResultManager.createFailResult("提现金额输入错误!");
		}
		BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(totalAmount));
		pay = pay.divide(BigDecimal.valueOf(100));
		
		if(pay.compareTo(Utlity.REAPAL_MAX_WITHDRAW) == 1){
			return ResultManager.createFailResult("提现金额不能高于500w!");
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		QcbAdmin qa = this.qcbAdminService.get(admin.getUuid());
		
		//密码加密
		String encryptPassword = SecurityByQcbRealm.encrypt(password, ByteSource.Util.bytes(qa.getUuid()));
		if(!qa.getPassword().equals(encryptPassword)){
			return ResultManager.createFailResult("密码输入错误！");
		}
		
		//创建企业提现记录信息
		QcbCompanyAccountHistory qcah = new QcbCompanyAccountHistory();
		qcah.setUuid(UUID.randomUUID().toString());
		
		//校验企业是否存在
		QcbCompanyAccount qc = this.qcbCompanyAccountService.get(admin.getQcbCompany());
		if(qc != null){
			qcah.setQcbCompany(admin.getQcbCompany());
		}
		
		if(!QcbCompanyAccountStatus.NORMAL.equals(qc.getStatus())){
			return ResultManager.createFailResult("企业尚未通过认证，无法进行提现!");
		}
		
		//获取提现手续费
		BigDecimal poundage = Utlity.getPoundage4QcbWithdraw(pay);
		
		//校验余额
		BigDecimal total = qc.getAccountPay();
		if(total.compareTo(pay.add(poundage)) < 0){
			return ResultManager.createFailResult("企业账户余额不足！");
		}
		
		//校验银行卡是否存在
		QcbCompanyBankcard qcb = this.qcbCompanyBankcardService.get(bankcard);
		if(qcb == null){
			return ResultManager.createFailResult("提现银行卡错误!");
		}
		
		Bank bank = this.bankService.get(qcb.getBank());
		if(bank == null){
			return ResultManager.createFailResult("提现银行错误!");
		}
		
		//校验订单号是否存在
		String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_QCB,Utlity.BILL_PAYTYPE_REAPAL,Utlity.BILL_PURPOSE_WITHDRAW);
		if(this.qcbCompanyAccountHistoryService.checkOrderNum(orderNumStr)){
			return ResultManager.createFailResult("服务器繁忙,请稍后再试！");
		}
		
		QcbOrderinfoByThirdparty obtN = new QcbOrderinfoByThirdparty();
		obtN.setUuid(UUID.randomUUID().toString());
		obtN.setType(QcbOrderinfoByThirdpartyType.REAPAL);
		obtN.setAccountType(QcbOrderinfoByThirdpartyAccountType.COMPANY);
		obtN.setAccount(qc.getUuid());
		obtN.setOrderNum(orderNumStr);
		obtN.setBody("融宝支付--企财宝企业提现");
		obtN.setTotalFee(pay);
		obtN.setPaySource(MD5.getMD5(qc.getUuid()));
		
		qcah.setFlagSms(flagSms);
		qcah.setCompanyAccount(CompanyAccountUuid.REAPAL);
		qcah.setOrderId(obtN.getUuid());
		qcah.setOrderNum(orderNumStr);
		qcah.setOrderType(QcbCompanyAccountHistoryOrderType.PAY_TYPE_REAPAL);
		qcah.setType(QcbCompanyAccountHistoryType.WITHDRAW);
		qcah.setPay(pay);
		qcah.setIncome(BigDecimal.ZERO);
		qcah.setAccountBalance(qc.getAccountBalance().subtract(pay.add(poundage)));
		qcah.setPoundage(poundage);
		qcah.setStatus(QcbCompanyAccountHistoryStatus.TRANSACTING);
		qcah.setCreator(admin.getUuid());
		qcah.setCreatetime(new Timestamp(System.currentTimeMillis()));
		qcah.setBankcard(qcb.getUuid());
		//验证短信验证码
		Map<String, String> codeParams = new HashMap<String, String>();
		codeParams.put("mobile", admin.getMobile());
		codeParams.put("status", MobileCodeStatus.NORMAL);
		codeParams.put("type", MobileCodeTypes.QCB_WITHDRAW);
		List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(codeParams, -1, -1, null, MobileCode.class);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			return ResultManager.createFailResult("短信验证码输入错误！");
		}
		
		if(!mc.getMobile().equals(admin.getMobile())){
			return ResultManager.createFailResult("手机号输入错误！");
		}
		
		if(Utlity.checkCodeTime(mc.getCreatetime().getTime(), System.currentTimeMillis())){
			return ResultManager.createFailResult("验证码超时！");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("短信验证码输入错误！");
		}
		
		try {
			Map<String, Object> resultBalance = ReapalUtlity.get_balance_Query();
			String balance = resultBalance.get("balance") == null ? "0" : resultBalance.get("balance").toString();
			BigDecimal accountTotal = BigDecimal.valueOf(Double.valueOf(balance));
			if(qcah.getPay().add(qcah.getPoundage()).compareTo(accountTotal) == 1){
				obtN.setStatus(QcbOrderinfoByThirdpartyReturnStatus.FAIL);
				obtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
				obtN.setErrCode("");
				obtN.setErrCodeDes("提现账户余额不足！");
				obtN.setMessage("提现账户余额不足！");
			} else {
				if(QcbCompanyBankcardType.COMPANY.equals(qcb.getBindingCardType())){
					//支行
					BranchBank bb = this.branchBankService.get(qcb.getBranchBank());
					if(bb == null){
						return ResultManager.createFailResult("提现银行卡支行错误!");
					}
					//地区
					BkArea ba = this.bkAreaService.get(qcb.getBkArea());
					if(ba == null){
						return ResultManager.createFailResult("提现银行卡地区错误!");
					}
					if(ba.getLevel() < 2){
						return ResultManager.createFailResult("提现银行卡地区错误!");
					}
					List<String> areaNames = this.bkAreaService.getFullName(ba.getUuid());
					//银行付款接口调用 返回成功后
					WithdrawData wd = new WithdrawData(1, qcb.getBindingBankCard(), qcb.getBindingCardCardholder(), bank.getName(), bb.getName(), bb.getName(), WithdrawDateProperties.PUBLIC, pay.setScale(2, BigDecimal.ROUND_HALF_UP), WithdrawDateCurrency.CNY, areaNames.get(0), areaNames.get(1), qcb.getBindingCardPhone(), "", "", "", orderNumStr, "提现");
					
					List<WithdrawData> content = new ArrayList<WithdrawData>();
					content.add(wd);
					WithdrawDataArray wda = new WithdrawDataArray(content);
					wda.setBatch_no(orderNumStr);
					wda.setTrans_time(Utlity.timeSpanToString(qcah.getCreatetime()));
					Map<String, Object> result = ReapalUtlity.qcbWithdrawBatchSubmit(wda);
					
					obtN.setStatus(result.get("result_code").toString());
					obtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
					obtN.setErrCode(result.get("result_code") == null ? "" : result.get("result_code").toString());
					obtN.setErrCodeDes(result.get("result_msg") == null ? "" : result.get("result_msg").toString());
					obtN.setMessage(result.get("result_msg") == null ? "" : result.get("result_msg").toString());
				}else{
					return ResultManager.createFailResult("仅支持使用企业对公账户进行提现！");
				}
			}
			
			if(!QcbOrderinfoByThirdpartyReturnStatus.REAPAL_CHECKED.equals(obtN.getStatus()) && !QcbOrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(obtN.getStatus())){
				qcah.setStatus(QcbCompanyAccountHistoryStatus.TRANSACTING);
				qcah.setProcessingStatus(QcbCompanyAccountHistoryProcessStatus.UNPROCESS);//接口调用失败 进入人工处理流程，设置处理状态为未处理
			}
			//减账户余额 和账户总资金
			qc.setAccountPay(qc.getAccountPay().subtract(pay).subtract(poundage));
			qc.setAccountBalance(qc.getAccountBalance().subtract(pay).subtract(poundage));
			this.qcbCompanyAccountHistoryService.insertWithdraw(qc, obtN, qcah);
			return ResultManager.createDataResult(qcah.getUuid(), "发起提现成功");
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
