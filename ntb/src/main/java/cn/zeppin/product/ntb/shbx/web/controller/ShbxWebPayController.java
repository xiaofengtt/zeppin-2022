package cn.zeppin.product.ntb.shbx.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.dom4j.DocumentException;
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
import cn.zeppin.product.ntb.core.entity.ShbxOrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.ShbxOrderinfoByThirdparty.ShbxOrderinfoByThirdpartyAccountType;
import cn.zeppin.product.ntb.core.entity.ShbxOrderinfoByThirdparty.ShbxOrderinfoByThirdpartyReturnStatus;
import cn.zeppin.product.ntb.core.entity.ShbxOrderinfoByThirdparty.ShbxOrderinfoByThirdpartyType;
import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder;
import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder.ShbxSecurityInsuredType;
import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder.ShbxSecurityOrderStatus;
import cn.zeppin.product.ntb.core.entity.ShbxUser;
import cn.zeppin.product.ntb.core.entity.ShbxUserBankcard;
import cn.zeppin.product.ntb.core.entity.ShbxUserHistory;
import cn.zeppin.product.ntb.core.entity.ShbxUserHistory.ShbxUserHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.ShbxUserHistory.ShbxUserHistoryStatus;
import cn.zeppin.product.ntb.core.entity.ShbxUserHistory.ShbxUserHistoryType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.shbx.service.api.IShbxOrderinfoByThirdpartyService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxSecurityOrderService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserBankcardService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserHistoryService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserService;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.reapal.ReapalConfig;
import cn.zeppin.product.utility.reapal.ReapalUtlity;

/**
 * @author hehe
 *
 * 微信企财宝员工理财产品接口
 */

@Controller
@RequestMapping(value = "/shbxWeb/pay")
public class ShbxWebPayController extends BaseController {
	
	@Autowired
	private IShbxUserService shbxUserService;
	
	@Autowired
	private IShbxUserHistoryService shbxUserHistoryService;
	
	@Autowired
	private IShbxUserBankcardService shbxUserBankcardService;
	
	@Autowired
	private IShbxOrderinfoByThirdpartyService shbxOrderinfoByThirdpartyService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IShbxSecurityOrderService shbxSecurityOrderService;
	
	/**
	 * 融宝支付（缴费）
	 * 参保人ID
	 * 参保类型 new-changein
	 * 公司名称
	 * 缴费月份（参保时间）
	 * 参保时长
	 * 社保基数
	 * 公积金基数
	 * 代缴金额
	 * 服务费
	 * 参保时长
	 * 合计金额
	 * 
	 * @param uuid
	 * @param totalFee（单位 分）
	 * @param body
	 * @param type
	 * @param code
	 * @param orderNum
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@RequestMapping(value = "/rechargeByReapal", method = RequestMethod.POST)
	@ActionParam(key = "bankcard", type = DataType.STRING, message = "用户选择银行卡编号", required = true)
	@ActionParam(key = "type", type = DataType.STRING, message = "接口类型", required = true)
	@ActionParam(key = "shbxInsured", type = DataType.STRING, message = "参保人", required = true)
	@ActionParam(key = "starttime", type = DataType.STRING, message = "缴费月份", required = true)
	@ActionParam(key = "insuredType", type = DataType.STRING, message = "参保类型", required = true)
	@ActionParam(key = "sourceCompany", type = DataType.STRING, message = "原公司名称")
	@ActionParam(key = "duration", type = DataType.STRING, message = "参保时长", required = true)
	@ActionParam(key = "housingFund", type = DataType.STRING, message = "公积金基数")
	@ActionParam(key = "cardinalNumber", type = DataType.STRING, message = "社保基数", required = true)
	@ActionParam(key = "benefits", type = DataType.STRING, message = "代缴金额", required = true)
	@ActionParam(key = "serviceFee", type = DataType.STRING, message = "服务费", required = true)
	@ActionParam(key = "price", type = DataType.STRING, message = "合计金额", required = true)
	@ActionParam(key = "body", type = DataType.STRING, message = "充值描述")
	@ActionParam(key = "code", type = DataType.STRING, message = "短信验证码")
	@ActionParam(key = "orderNum", type = DataType.STRING, message = "send 返回的订单编号")
	@ResponseBody
	protected Result rechargeByReapal(String price, String bankcard, String shbxInsured,
			String starttime, String insuredType, String sourceCompany, String duration, String housingFund,
			String cardinalNumber, String benefits, String serviceFee, String body, String type,
			String code,String orderNum, HttpServletRequest request) throws IOException, DocumentException {
//		if(request.getAttribute("employee") == null){
//			return ResultManager.createFailResult("用户信息不存在！");
//		}
//		ShbxUser qe = (ShbxUser)request.getAttribute("employee");
		
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShbxUser qe = (ShbxUser) session.getAttribute("shbxUser");
		
		//普通转入非空校验
		if(ShbxSecurityInsuredType.CHANGEIN.equals(insuredType)){
			if(Utlity.checkStringNull(sourceCompany)){
				return ResultManager.createFailResult("请输入原公司名称!");
			}
		}
		
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency4Web(price)) {
			return ResultManager.createFailResult("合计金额输入错误!");
		}
		BigDecimal totalFees = BigDecimal.valueOf(Double.parseDouble(price));
		BigDecimal paydivide = totalFees.divide(BigDecimal.valueOf(100));
		
		BigDecimal housingFunds = BigDecimal.ZERO;
		if(!Utlity.checkStringNull(housingFund)){
			housingFund = Base64Util.getFromBase64(housingFund);
			housingFunds = BigDecimal.valueOf(Double.parseDouble(housingFund));
		}
		
		cardinalNumber = Base64Util.getFromBase64(cardinalNumber);
		BigDecimal cardinalNumbers = BigDecimal.valueOf(Double.parseDouble(cardinalNumber)); 
		benefits = Base64Util.getFromBase64(benefits);
		BigDecimal benefitss = BigDecimal.valueOf(Double.parseDouble(benefits));
		serviceFee = Base64Util.getFromBase64(serviceFee);
		BigDecimal serviceFees = BigDecimal.valueOf(Double.parseDouble(serviceFee));
		
		String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_SHBX,Utlity.BILL_PAYTYPE_REAPAL,Utlity.BILL_PURPOSE_BUY);
		if(this.shbxUserHistoryService.checkOrderNum(orderNumStr)){
			return ResultManager.createFailResult("手速太快，服务器未响应！");
		}
		
		try {
			if(Utlity.checkStringNull(body)){
				body = "融宝支付--支付请求";
			} else {
				body = Base64Util.getFromBase64(body);
			}
			ShbxUserBankcard qeb = this.shbxUserBankcardService.get(bankcard);
			if(qeb == null){
				return ResultManager.createFailResult("未找到绑定的银行卡！");
			}
			if(!qeb.getShbxUser().equals(qe.getUuid())){
				return ResultManager.createFailResult("不能使用未绑定的银行卡！");
			}
			Bank bank = this.bankService.get(qeb.getBank());
			if(bank == null){
				return ResultManager.createFailResult("未找到付款银行！");
			}
			
			if("send".equals(type)){
				
				String userIp = Utlity.getIpAddr(request);
				if(Utlity.checkStringNull(qeb.getBindingId())){
					String bankcardNo = qeb.getBindingBankCard();
					String realName = qeb.getBindingCardCardholder();
					String mobile = qeb.getBindingCardPhone();
					String idcard = qe.getIdcard();
					
					Map<String, Object> returnMap = ReapalUtlity.debit(bankcardNo, realName, idcard, mobile, qe.getUuid(), orderNumStr, userIp, totalFees.setScale(0).toString(), ReapalConfig.notify_url_shbx_recharge);
					ShbxOrderinfoByThirdparty qobt = new ShbxOrderinfoByThirdparty();
					qobt.setUuid(UUID.randomUUID().toString());
					qobt.setType(ShbxOrderinfoByThirdpartyType.REAPAL);
					qobt.setAccount(qe.getUuid());
					qobt.setAccountType(ShbxOrderinfoByThirdpartyAccountType.EMPLOYEE);
					qobt.setOrderNum(orderNumStr);
					qobt.setBody(body);
					qobt.setTotalFee(paydivide);
					qobt.setPaySource(MD5.getMD5(qe.getUuid()));
					qobt.setStatus(returnMap.get("result_code").toString());
					qobt.setCreatetime(new Timestamp(System.currentTimeMillis()));
					qobt.setErrCode(returnMap.get("result_code") == null ? "" : returnMap.get("result_code").toString());
					qobt.setErrCodeDes(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					qobt.setMessage(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
//					String certificate = returnMap.get("certificate") == null ? "" : returnMap.get("certificate").toString();
//					System.out.println("certificate:"+certificate);
					if(ShbxOrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(returnMap.get("result_code").toString())){
//						obt.setPrepayId(returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString());
						String bindId = returnMap.get("bind_id") == null ? "" : returnMap.get("bind_id").toString();
						qeb.setBindingId(bindId);
						
						ShbxUserHistory qeh = new ShbxUserHistory();
						qeh.setUuid(UUID.randomUUID().toString());
						qeh.setShbxUser(qe.getUuid());
						qeh.setIncome(BigDecimal.ZERO);
						qeh.setPay(paydivide);
						qeh.setAccountBalance(qe.getAccountBalance().add(paydivide));
						qeh.setOrderId(qobt.getUuid());
						qeh.setOrderType(ShbxUserHistoryOrderType.PAY_TYPE_REAPAL);
						qeh.setOrderNum(qobt.getOrderNum());
						qeh.setType(ShbxUserHistoryType.BUY_SHBX);
						qeh.setStatus(ShbxUserHistoryStatus.TRANSACTING);
						qeh.setCreatetime(new Timestamp(System.currentTimeMillis()));
						qeh.setCompanyAccount(CompanyAccountUuid.REAPAL);//应该是记录跟融宝支付绑定的账号
						qeh.setBankcard(qeb.getUuid());
						
						//记录单笔交易手续费
//						BigDecimal poundage = paydivide.multiply(ReapalUtil.POUNDAGE_FEE).setScale(2,BigDecimal.ROUND_HALF_UP);
//						if(poundage.compareTo(BigDecimal.valueOf(Double.parseDouble("2.0"))) < 0){
//							qeh.setPoundage(BigDecimal.valueOf(Double.parseDouble("2.0")));
//						} else {
//							qeh.setPoundage(poundage);
//						}
						BigDecimal poundage = Utlity.getPoundage4Pay(paydivide, bank.getShortName());
						qeh.setPoundage(poundage);
						
						//记录社保缴纳记录
						ShbxSecurityOrder sso = new ShbxSecurityOrder();
						sso.setUuid(UUID.randomUUID().toString());
						sso.setCreator(qe.getUuid());
						sso.setCreatetime(new Timestamp(System.currentTimeMillis()));
						sso.setStatus(ShbxSecurityOrderStatus.UNPAY);//待支付
						sso.setBenefits(benefitss);
						sso.setCardinalNumber(cardinalNumbers);
						sso.setDuration(duration);
						sso.setHousingFund(housingFunds);
						sso.setInsuredType(insuredType);
						sso.setOrderNumber(orderNumStr);
						sso.setServiceFee(serviceFees);
						sso.setShbxInsured(shbxInsured);
						sso.setShbxUserHistory(qeh.getUuid());
						sso.setSourceCompany(sourceCompany);
						sso.setStarttime(starttime);
						
						//关联记录缴费记录
						qeh.setShbxSecurityOrder(sso.getUuid());
						
						this.shbxUserHistoryService.insertwechart(qobt, qeh, qeb, sso);
						return ResultManager.createDataResult(qobt.getUuid(), "操作成功！");
					} else {
						String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
						qobt.setPrepayId(prepayId);
						this.shbxOrderinfoByThirdpartyService.insert(qobt);
						return ResultManager.createFailResult(qobt.getErrCodeDes());
					}
				} else {
					String bindingId = qeb.getBindingId();
					Map<String, Object> returnMap = ReapalUtlity.bindCard(qe.getUuid(), bindingId, orderNumStr, userIp, totalFees.setScale(0).toString(), ReapalConfig.notify_url_shbx_recharge);
					ShbxOrderinfoByThirdparty qobt = new ShbxOrderinfoByThirdparty();
					qobt.setUuid(UUID.randomUUID().toString());
					qobt.setType(ShbxOrderinfoByThirdpartyType.REAPAL);
					qobt.setAccount(qe.getUuid());
					qobt.setAccountType(ShbxOrderinfoByThirdpartyAccountType.EMPLOYEE);
					qobt.setOrderNum(orderNumStr);
					qobt.setBody(body);
					qobt.setTotalFee(paydivide);
					qobt.setPaySource(MD5.getMD5(qe.getUuid()));
					qobt.setStatus(returnMap.get("result_code").toString());
					qobt.setCreatetime(new Timestamp(System.currentTimeMillis()));
					qobt.setErrCode(returnMap.get("result_code") == null ? "" : returnMap.get("result_code").toString());
					qobt.setErrCodeDes(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					qobt.setMessage(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
//					String certificate = returnMap.get("certificate") == null ? "" : returnMap.get("certificate").toString();
//					System.out.println("certificate:"+certificate);
					if(ShbxOrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(returnMap.get("result_code").toString())){
						
						ShbxUserHistory qeh = new ShbxUserHistory();
						qeh.setUuid(UUID.randomUUID().toString());
						qeh.setShbxUser(qe.getUuid());
						qeh.setIncome(BigDecimal.ZERO);
						qeh.setPay(paydivide);
						qeh.setAccountBalance(qe.getAccountBalance().add(paydivide));
						qeh.setOrderId(qobt.getUuid());
						qeh.setOrderType(ShbxUserHistoryOrderType.PAY_TYPE_REAPAL);
						qeh.setOrderNum(qobt.getOrderNum());
						qeh.setType(ShbxUserHistoryType.BUY_SHBX);
						qeh.setStatus(ShbxUserHistoryStatus.TRANSACTING);
						qeh.setCreatetime(new Timestamp(System.currentTimeMillis()));
						qeh.setCompanyAccount(CompanyAccountUuid.REAPAL);//应该是记录跟融宝支付绑定的账号
						qeh.setBankcard(qeb.getUuid());
						
						//记录单笔交易手续费
//						BigDecimal poundage = paydivide.multiply(ReapalUtil.POUNDAGE_FEE).setScale(2,BigDecimal.ROUND_HALF_UP);
//						if(poundage.compareTo(BigDecimal.valueOf(Double.parseDouble("2.0"))) < 0){
//							qeh.setPoundage(BigDecimal.valueOf(Double.parseDouble("2.0")));
//						} else {
//							qeh.setPoundage(poundage);
//						}
						BigDecimal poundage = Utlity.getPoundage4Pay(paydivide, bank.getShortName());
						qeh.setPoundage(poundage);
						
						//记录社保缴纳记录
						ShbxSecurityOrder sso = new ShbxSecurityOrder();
						sso.setUuid(UUID.randomUUID().toString());
						sso.setCreator(qe.getUuid());
						sso.setCreatetime(new Timestamp(System.currentTimeMillis()));
						sso.setStatus(ShbxSecurityOrderStatus.UNPAY);//待支付
						sso.setBenefits(benefitss);
						sso.setCardinalNumber(cardinalNumbers);
						sso.setDuration(duration);
						sso.setHousingFund(housingFunds);
						sso.setInsuredType(insuredType);
						sso.setOrderNumber(orderNumStr);
						sso.setServiceFee(serviceFees);
						sso.setShbxInsured(shbxInsured);
						sso.setShbxUserHistory(qeh.getUuid());
						sso.setSourceCompany(sourceCompany);
						sso.setStarttime(starttime);
						
						//关联记录缴费记录
						qeh.setShbxSecurityOrder(sso.getUuid());
						
						this.shbxUserHistoryService.insertwechart(qobt, qeh, sso);
						return ResultManager.createDataResult(qobt.getUuid(), "操作成功！");
					} else {
						String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
						qobt.setPrepayId(prepayId);
						this.shbxOrderinfoByThirdpartyService.insert(qobt);
						return ResultManager.createFailResult(qobt.getErrCodeDes());
					}
				}
				
			} else if ("resend".equals(type)) {
				if(Utlity.checkStringNull(orderNum)){
					return ResultManager.createFailResult("操作错误，缺少参数！");
				}
				ShbxOrderinfoByThirdparty qobt = this.shbxOrderinfoByThirdpartyService.get(orderNum);
				if(qobt == null){
					return ResultManager.createFailResult("操作错误，请重新购买！");
				}
				Map<String, Object> returnMap = ReapalUtlity.reSms(orderNum);
				ShbxOrderinfoByThirdparty qobtN = new ShbxOrderinfoByThirdparty();
				qobtN.setUuid(UUID.randomUUID().toString());
				qobtN.setType(ShbxOrderinfoByThirdpartyType.REAPAL);
				qobtN.setAccount(qe.getUuid());
				qobtN.setAccountType(ShbxOrderinfoByThirdpartyAccountType.EMPLOYEE);
				qobtN.setOrderNum(orderNumStr);
				qobtN.setBody(body);
				qobtN.setTotalFee(BigDecimal.ZERO);
				qobtN.setPaySource(MD5.getMD5(qe.getUuid()));
				qobtN.setStatus(returnMap.get("result_code").toString());
				qobtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
				qobtN.setErrCode(returnMap.get("result_code") == null ? "" : returnMap.get("result_code").toString());
				qobtN.setErrCodeDes(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
				qobtN.setMessage(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
				if(ShbxOrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(returnMap.get("result_code").toString())){
					qobtN.setPrepayId(returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString());
				} else {
					String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
					qobtN.setPrepayId(prepayId);
					Map<String, String> inputParams = new HashMap<String, String>();
					inputParams.put("order", qobt.getUuid());
					inputParams.put("shbxUser", qobt.getAccount());
					inputParams.put("orderType", ShbxUserHistoryOrderType.PAY_TYPE_REAPAL);
					inputParams.put("type", ShbxUserHistoryType.BUY_SHBX);
					List<Entity> listHistory = this.shbxUserHistoryService.getListForPage(inputParams, -1, -1, null, ShbxUserHistory.class);
					ShbxUserHistory qeh = null;
					ShbxSecurityOrder sso = null;
					if(listHistory != null && listHistory.size() > 0){
						qeh = (ShbxUserHistory) listHistory.get(0);
						sso = this.shbxSecurityOrderService.get(qeh.getShbxSecurityOrder());
						sso.setStatus(ShbxSecurityOrderStatus.FAIL);
					}
					
					qobt.setStatus(returnMap.get("result_code").toString());
					qobt.setErrCode(returnMap.get("result_code") == null ? "" : returnMap.get("result_code").toString());
					qobt.setErrCodeDes(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					qobt.setMessage(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					qeh.setStatus(ShbxUserHistoryStatus.FAIL);
					this.shbxUserHistoryService.updateWithdraw(qobt, qobtN, qeh, sso);
					return ResultManager.createFailResult("发送失败");
				}
				this.shbxOrderinfoByThirdpartyService.insert(qobtN);
				return ResultManager.createDataResult(qobt.getUuid(), "操作成功！");
			} else if ("check".equals(type)) {
				if(Utlity.checkStringNull(orderNum)){
					return ResultManager.createFailResult("操作错误，缺少参数！");
				}
				ShbxOrderinfoByThirdparty qobt = this.shbxOrderinfoByThirdpartyService.get(orderNum);
				if(qobt == null){
					return ResultManager.createFailResult("操作错误，请重新购买！");
				}
				code = Base64Util.getFromBase64(code);
				Map<String, Object> returnMap = ReapalUtlity.confirmPay(qobt.getOrderNum(), code);
				ShbxOrderinfoByThirdparty qobtN = new ShbxOrderinfoByThirdparty();
				qobtN.setUuid(UUID.randomUUID().toString());
				qobtN.setType(ShbxOrderinfoByThirdpartyType.REAPAL);
				qobtN.setAccount(qe.getUuid());
				qobtN.setAccountType(ShbxOrderinfoByThirdpartyAccountType.EMPLOYEE);
				qobtN.setOrderNum(orderNumStr);
				qobtN.setBody("融宝支付--支付确认");
				qobtN.setTotalFee(BigDecimal.ZERO);
				qobtN.setPaySource(MD5.getMD5(qe.getUuid()));
				qobtN.setStatus(returnMap.get("result_code").toString());
				qobtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
				qobtN.setErrCode(returnMap.get("result_code") == null ? "" : returnMap.get("result_code").toString());
				qobtN.setErrCodeDes(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
				qobtN.setMessage(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
				if(ShbxOrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(returnMap.get("result_code").toString())){
					qobtN.setPrepayId(returnMap.get("trade_no") == null ? "" : returnMap.get("trade_no").toString());
					
					Map<String, String> inputParams = new HashMap<String, String>();
					inputParams.put("order", qobt.getUuid());
					inputParams.put("shbxUser", qobt.getAccount());
					inputParams.put("orderType", ShbxUserHistoryOrderType.PAY_TYPE_REAPAL);
					inputParams.put("type", ShbxUserHistoryType.BUY_SHBX);
					List<Entity> listHistory = this.shbxUserHistoryService.getListForPage(inputParams, -1, -1, null, ShbxUserHistory.class);
					ShbxUserHistory qeh = null;
					ShbxSecurityOrder sso = null;
					if(listHistory != null && listHistory.size() > 0){
						qeh = (ShbxUserHistory) listHistory.get(0);
						sso = this.shbxSecurityOrderService.get(qeh.getShbxSecurityOrder());
						sso.setStatus(ShbxSecurityOrderStatus.PAYED);//已支付
					}
					
//					this.shbxOrderinfoByThirdpartyService.insert(qobtN);
					this.shbxUserHistoryService.updateWithdraw(null, qobtN, null, sso);
					return ResultManager.createSuccessResult("操作成功！");
				} else {
					String prepayId = returnMap.get("trade_no") == null ? "" : returnMap.get("trade_no").toString();
					qobtN.setPrepayId(prepayId);
					Map<String, String> inputParams = new HashMap<String, String>();
					inputParams.put("order", qobt.getUuid());
					inputParams.put("shbxUser", qobt.getAccount());
					inputParams.put("orderType", ShbxUserHistoryOrderType.PAY_TYPE_REAPAL);
					inputParams.put("type", ShbxUserHistoryType.BUY_SHBX);
					List<Entity> listHistory = this.shbxUserHistoryService.getListForPage(inputParams, -1, -1, null, ShbxUserHistory.class);
					ShbxUserHistory qeh = null;
					ShbxSecurityOrder sso = null;
					if(listHistory != null && listHistory.size() > 0){
						qeh = (ShbxUserHistory) listHistory.get(0);
						sso = this.shbxSecurityOrderService.get(qeh.getShbxSecurityOrder());
						sso.setStatus(ShbxSecurityOrderStatus.FAIL);
					}
					
					qobt.setStatus(returnMap.get("result_code").toString());
					qobt.setErrCode(returnMap.get("result_code") == null ? "" : returnMap.get("result_code").toString());
					qobt.setErrCodeDes(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					qobt.setMessage(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					qeh.setStatus(ShbxUserHistoryStatus.FAIL);
					
					this.shbxUserHistoryService.updateWithdraw(qobt, qobtN, qeh, sso);
//					this.shbxOrderinfoByThirdpartyService.insert(qobtN);
					return ResultManager.createFailResult("操作失败，"+qobtN.getErrCodeDes()+"！");
				}
			} else {
				return ResultManager.createFailResult("操作错误！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("操作异常，请稍后再试！");
		}
	}
}
