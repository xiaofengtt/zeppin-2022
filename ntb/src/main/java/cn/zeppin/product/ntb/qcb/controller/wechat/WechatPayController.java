package cn.zeppin.product.ntb.qcb.controller.wechat;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.ICouponService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.CompanyAccount.CompanyAccountUuid;
import cn.zeppin.product.ntb.core.entity.Coupon;
import cn.zeppin.product.ntb.core.entity.FundPublish;
import cn.zeppin.product.ntb.core.entity.FundPublish.FundPublishUuid;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeCoupon;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeCoupon.QcbEmployeeCouponStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy.QcbEmployeeProductBuyProductType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy.QcbEmployeeProductBuyStage;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuyAgreement;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuyAgreement.QcbEmployeeProductBuyAgreementStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuyAgreement.QcbEmployeeProductBuyAgreementType;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty.QcbOrderinfoByThirdpartyAccountType;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty.QcbOrderinfoByThirdpartyReturnStatus;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty.QcbOrderinfoByThirdpartyType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeBankcardService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeCouponService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeProductBuyAgreementService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeProductBuyService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbOrderinfoByThirdpartyService;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.PDFUtlity;
import cn.zeppin.product.utility.RMBUtlity;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.reapal.ReapalConfig;
import cn.zeppin.product.utility.reapal.ReapalUtlity;

/**
 * @author hehe
 *
 * 微信企财宝员工理财产品接口
 */

@Controller
@RequestMapping(value = "/qcbWechat/pay")
public class WechatPayController extends BaseController {
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IQcbEmployeeHistoryService qcbEmployeeHistoryService;
	
	@Autowired
	private IQcbEmployeeProductBuyService qcbEmployeeProductBuyService;
	
	@Autowired
	private IQcbEmployeeProductBuyAgreementService qcbEmployeeProductBuyAgreementService;
	
	@Autowired
	private IQcbEmployeeCouponService qcbEmployeeCouponService;
	
	@Autowired
	private IQcbEmployeeBankcardService qcbEmployeeBankcardService;
	
	@Autowired
	private IQcbOrderinfoByThirdpartyService qcbOrderinfoByThirdpartyService;
	
	@Autowired
	private ICouponService couponService;
	
	@Autowired
	private IFundPublishService fundPublishService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	@Autowired
	private IBankService bankService;
	
	/**
	 * 购买银行理财产品
	 * 按支付方式 分为余额支付、微信支付、银行卡支付、支付宝支付等
	 * 步骤：
	 * 1.验证用户
	 * 4.验证银行理财产品募集金额 最小投资金额、最大投资金额、最小投资递增
	 * 2.验证短信验证码
	 * 3.判断type 余额支付需要验证账户余额
	 * 5.创建预支付订单和用户支付账单记录
	 * 6.创建用户银行理财产品购买记录 注意状态 置为交易中 支付成功后置为支付成功
	 * @param price
	 * @param product
	 * @param type 支付方式 balance-余额 wechart-微信 alipay-支付宝 bankcard-银行卡
	 * @return
	 */
	@RequestMapping(value = "/productPay", method = RequestMethod.POST)
	@ActionParam(key = "price", type = DataType.STRING, message = "买入金额", required = true)
	@ActionParam(key = "product", type = DataType.STRING, message = "买入产品编号", required = true)
	@ActionParam(key = "type", type = DataType.STRING, message = "支付方式", required = true)
	@ActionParam(key = "coupon", type = DataType.STRING, message = "优惠券")
	@ActionParam(key = "code", type = DataType.STRING, message = "短信验证码")
	@ResponseBody
	protected Result productPay(HttpServletRequest request, String price, String product, String type, String coupon, String code){
		
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee employee = (QcbEmployee)request.getAttribute("employee");
		
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency4Web(price)) {
			return ResultManager.createFailResult("买入金额输入错误!");
		}
		BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));
		BigDecimal paydivide = pay.divide(BigDecimal.valueOf(100));
		//获取银行理财产品发布信息
		
		synchronized(this){//购买需要线程同步机制，防止出现募集金额 余量不足的情况
			try {
				BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(product);
				if (bfpp == null) {
					return ResultManager.createFailResult("银行理财产品信息不存在！");
				}
				Bank bank = this.bankService.get(bfpp.getCustodian());
				if(bank == null){
					return ResultManager.createFailResult("银行理财产品信息错误！");
				}
				/*
				 * 4.验证银行理财产品募集金额 最小投资金额、最大投资金额、最小投资递增
				 */
				if(paydivide.compareTo(bfpp.getMinInvestAmount()) == -1){
					return ResultManager.createFailResult("买入金额不少于"+bfpp.getMinInvestAmount()+"元");
				}
				
				if((bfpp.getMaxInvestAmount().compareTo(BigDecimal.ZERO)) > 0 && paydivide.compareTo(bfpp.getMaxInvestAmount()) == 1){
					return ResultManager.createFailResult("买入金额不大于"+bfpp.getMaxInvestAmount()+"元");
				}
				
				//查寻余量 不能超出所剩余量
				//计算公式：总募集金额-(账户余额+投资金额-实际收益-赎回金额)
				BigDecimal totalamount = bfpp.getCollectAmount();//计划募集金额
				BigDecimal realCollect = bfpp.getRealCollect();//实际募集金额
				BigDecimal realAmount = totalamount.subtract(realCollect);
				if(realAmount.compareTo(paydivide) == -1){//余额不足以支持用户购买
					return ResultManager.createFailResult("产品余额不足");
				}
				//service中employee,bfpp,pay,根据支付类型 处理支付逻辑
				if(Utlity.PAY_TYPE_BALANCE.equals(type)){
					String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_QCB,Utlity.BILL_PAYTYPE_BALANCE,Utlity.BILL_PURPOSE_INCOME);
					if(this.qcbEmployeeHistoryService.checkOrderNum(orderNumStr)){
						return ResultManager.createFailResult("手速太快，服务器未响应！");
					}
					//余额支付有验证码验证
					if(code == null || "".equals(code)){
						return ResultManager.createFailResult("请先获取验证码");
					}
					code = Base64Util.getFromBase64(code);
					Map<String, String> inputParams = new HashMap<String, String>();
					inputParams.put("mobile", employee.getMobile());
					inputParams.put("status", MobileCodeStatus.NORMAL);
					inputParams.put("type", MobileCodeTypes.FUNDCODE);
					List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(inputParams, -1, -1, null, MobileCode.class);
					MobileCode mc = null;
					if(lstMobileCode != null && lstMobileCode.size() > 0){
						mc = (MobileCode) lstMobileCode.get(0);
					}
					if(mc == null){
						return ResultManager.createFailResult("请先获取验证码");
					}
					if(!MobileCodeTypes.FUNDCODE.equals(mc.getType())){
						return ResultManager.createFailResult("验证码输入错误！");
					}
					
					if(!code.equals(mc.getCode())){//测试中 不校验
						return ResultManager.createFailResult("验证码输入错误！");
					}
					
					/*
					 * 余额支付
					 * 验证账户余额是否满足支付金额
					 * 新增一条余额支付张单记录 并消减余额
					 * 新增用户买入记录
					 * 
					 */
					BigDecimal total = employee.getAccountBalance();
					if(total.compareTo(paydivide) == -1){
						return ResultManager.createFailResult("用户余额不足！");
					}
					
					//减账户余额 加理财金额
					total = total.subtract(paydivide);
					BigDecimal totalInvest = employee.getTotalInvest();
					totalInvest = totalInvest.add(paydivide);
					employee.setAccountBalance(total);
					employee.setTotalInvest(totalInvest);
					
					Timestamp time = new Timestamp(System.currentTimeMillis());
					
					QcbEmployeeHistory qeh = new QcbEmployeeHistory();
					qeh.setUuid(UUID.randomUUID().toString());
					qeh.setQcbEmployee(employee.getUuid());
					
					//账户支出
					qeh.setIncome(BigDecimal.ZERO);
					qeh.setPay(paydivide);
					
					qeh.setAccountBalance(employee.getAccountBalance());
					qeh.setOrderId(employee.getUuid());
					qeh.setOrderType(QcbEmployeeHistoryOrderType.PAY_TYPE_BALANCE);
					qeh.setOrderNum(orderNumStr);
					qeh.setType(QcbEmployeeHistoryType.BUY);
					qeh.setStatus(QcbEmployeeHistoryStatus.SUCCESS);
					qeh.setCreatetime(time);
					qeh.setCompanyAccount(CompanyAccountUuid.WECHART);
					qeh.setProduct(product);
					qeh.setProductType(QcbEmployeeProductBuyProductType.BANK_PRODUCT);
					qeh.setPoundage(BigDecimal.ZERO);
					
					//入库之前需要检查之前有没有买过该产品，如果买过 那么主表update 从表增加一条记录 如果没有 主表从表都增加一条记录
					QcbEmployeeProductBuy qepb = new QcbEmployeeProductBuy();
					inputParams.clear();
					inputParams.put("qcbEmployee", employee.getUuid());
					inputParams.put("product", product);
					List<Entity> listBuy = this.qcbEmployeeProductBuyService.getListForPage(inputParams, -1, -1, null, QcbEmployeeProductBuy.class);
					Boolean isUpdate = true;
					if(listBuy != null && !listBuy.isEmpty()){
						qepb = (QcbEmployeeProductBuy) listBuy.get(0);
						BigDecimal totalAmount = qepb.getTotalAmount();
						qepb.setTotalAmount(totalAmount.add(paydivide));
						qepb.setAccountBalance(qepb.getAccountBalance().add(paydivide));
						qepb.setCreatetime(new Timestamp(System.currentTimeMillis()));
					} else {
						isUpdate = false;
						qepb.setUuid(UUID.randomUUID().toString());
						qepb.setCreatetime(new Timestamp(System.currentTimeMillis()));
						qepb.setQcbEmployee(employee.getUuid());
						qepb.setProduct(product);
						qepb.setStage(QcbEmployeeProductBuyStage.CONFIRMING);
						qepb.setTotalAmount(paydivide);
						qepb.setAccountBalance(paydivide);
						qepb.setTotalRedeem(BigDecimal.ZERO);
						qepb.setTotalReturn(BigDecimal.ZERO);
					}
					//检查可买入金额
					if((bfpp.getMaxInvestAmount().compareTo(BigDecimal.ZERO)) > 0 && (qepb.getTotalAmount().compareTo(bfpp.getMaxInvestAmount())) == 1){
						return ResultManager.createFailResult("您的剩余可买入金额不足！");
					}
					qepb.setType(QcbEmployeeProductBuyProductType.BANK_PRODUCT);
					//募集产品余额增加
					bfpp.setAccountBalance(bfpp.getAccountBalance().add(qeh.getPay()));
					//募集产品实际募集金额增加
					bfpp.setRealCollect(bfpp.getRealCollect().add(qeh.getPay()));
					
					//生成电子合同PDF，并入库存储
					//先检查 电子合同是否存在，如果存在则更新
					inputParams.clear();
					inputParams.put("qcbEmployee", employee.getUuid());
					inputParams.put("records", product);
					QcbEmployeeProductBuyAgreement qepba = new QcbEmployeeProductBuyAgreement();
					List<Entity> listAgreement = this.qcbEmployeeProductBuyAgreementService.getListForPage(inputParams, -1, -1, null, QcbEmployeeProductBuyAgreement.class);
					Boolean isUpdate2 = true;
					if(listAgreement != null && !listAgreement.isEmpty()){
						qepba = (QcbEmployeeProductBuyAgreement) listAgreement.get(0);
					} else {
						isUpdate2 = false;
						qepba.setUuid(UUID.randomUUID().toString());
					}
					qepba.setCreatetime(time);
					qepba.setQcbEmployee(employee.getUuid());
					qepba.setType(QcbEmployeeProductBuyAgreementType.BANKPRODUCT);
					qepba.setRecords(qepb.getProduct());
					qepba.setScode(orderNumStr);
					qepba.setName("牛投理财定向委托投资管理协议");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("scode", orderNumStr);
			    	params.put("realname", employee.getRealname());
			    	params.put("phone", Utlity.getStarMobile(employee.getMobile()));
			    	params.put("idcard", Utlity.getStarIdcard(employee.getIdcard()));
			    	params.put("productName", bank.getShortName()+bfpp.getName());
			    	String priceRMB = RMBUtlity.arabNumToChineseRMB(Double.parseDouble(qepb.getTotalAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
			    	String priceStr = "人民币："+Utlity.numFormat4UnitDetailLess(qepb.getTotalAmount())+"元 (大写："+priceRMB+"整)";
			    	params.put("price", priceStr);
			    	params.put("uuid", qepba.getUuid());
			    	params.put("fileName", "牛投理财定向委托投资管理协议"+orderNumStr);
					Map<String, Object> resultPDF = PDFUtlity.ToPdf(params);
					if((Boolean) resultPDF.get("result")){
						qepba.setStatus(QcbEmployeeProductBuyAgreementStatus.SUCCESS);
						qepba.setUrl(resultPDF.get("url")==null?"":resultPDF.get("url").toString());
					} else {
						qepba.setStatus(QcbEmployeeProductBuyAgreementStatus.FAIL);
						qepba.setUrl("");
					}
					//买入成功，发送短信通知
					MobileCode mcode = new MobileCode();
					String content = "尊敬的用户您好：您于"+Utlity.timeSpanToChinaDateString(time)+"成功购买"+bank.getShortName()+bfpp.getName()
							+"理财产品，买入金额："+Utlity.numFormat4UnitDetailLess(qeh.getPay())+"元。";
					String mobile = employee.getMobile();
					String codeInfo = Utlity.getCaptcha();
					mcode.setCode(codeInfo);
					mcode.setContent(content);
					mcode.setMobile(mobile);
					mcode.setCreator(employee.getUuid());
					mcode.setStatus(MobileCodeStatus.DISABLE);
					mcode.setType(MobileCodeTypes.NOTICE);
					mcode.setCreatetime(new Timestamp(System.currentTimeMillis()));
					mcode.setCreatorType(MobileCodeCreatorType.INVESTOR);
					mcode.setUuid(UUID.randomUUID().toString());
					
//					EmployeeInformation iii = new EmployeeInformation();
//					iii.setCreator(employee.getUuid());
//					iii.setStatus(EmployeeInformationStatus.UNREAD);
//					iii.setCreatetime(new Timestamp(System.currentTimeMillis()));
//					iii.setUuid(UUID.randomUUID().toString());
//					iii.setInfoText(content);
//					iii.setInfoTitle(EmployeeInformationTitle.BUY);
//					iii.setEmployee(employee.getUuid());
					
					//判断有没有使用优惠券，如果使用了，添加优惠券使用记录
					Boolean flagCoupon = Utlity.checkStringNull(coupon);
					if(!flagCoupon){
						QcbEmployeeCoupon qec = this.qcbEmployeeCouponService.get(coupon);
						if(qec != null){
							//判断优惠券是否符合使用条件
							Coupon co = this.couponService.get(qec.getCoupon());
							if(co == null){
								return ResultManager.createFailResult("优惠券信息错误！");
							}
							if(!QcbEmployeeCouponStatus.UNUSE.equals(qec.getStatus())){
								return ResultManager.createFailResult("不能使用该优惠券！");
							}
							//修改优惠券状态
							qec.setStatus(QcbEmployeeCouponStatus.USED);
							//生成优惠券使用记录 添加购买记录
							this.qcbEmployeeHistoryService.insertProductBuy4Balance(mc, bfpp, employee, qeh, qepba, qepb, isUpdate, isUpdate2, mcode, qec);
						} else {
							return ResultManager.createFailResult("优惠券信息不存在！");
						}
					} else {
						this.qcbEmployeeHistoryService.insertProductBuy4Balance(mc, bfpp, employee, qeh, qepba, qepb, isUpdate, isUpdate2, mcode, null);
					}
				} else {
					return ResultManager.createFailResult("支付类型错误！");
				}
			} catch (TransactionException te) {
				super.flushAll();
				return ResultManager.createFailResult(te.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult("服务器异常！");
			}
		}
		
		return ResultManager.createSuccessResult("操作成功");
	}
	
	/**
	 * 融宝支付（充值）
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
	@ActionParam(key = "uuid", type = DataType.STRING, message = "用户编号", required = true)
	@ActionParam(key = "price", type = DataType.STRING, message = "充值金额", required = true)
	@ActionParam(key = "bankcard", type = DataType.STRING, message = "用户选择银行卡编号", required = true)
	@ActionParam(key = "type", type = DataType.STRING, message = "接口类型", required = true)
	@ActionParam(key = "body", type = DataType.STRING, message = "充值描述")
	@ActionParam(key = "code", type = DataType.STRING, message = "短信验证码")
	@ActionParam(key = "orderNum", type = DataType.STRING, message = "send 返回的订单编号")
	@ResponseBody
	protected Result rechargeByReapal(String uuid, String price, String bankcard, String body, String type,
			String code,String orderNum, HttpServletRequest request) throws IOException, DocumentException {
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency4Web(price)) {
			return ResultManager.createFailResult("充值金额输入错误!");
		}
		BigDecimal totalFees = BigDecimal.valueOf(Double.parseDouble(price));
		BigDecimal paydivide = totalFees.divide(BigDecimal.valueOf(100));
		
		String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_QCB,Utlity.BILL_PAYTYPE_WECHART,Utlity.BILL_PURPOSE_INCOME);
		if(this.qcbEmployeeHistoryService.checkOrderNum(orderNumStr)){
			return ResultManager.createFailResult("手速太快，服务器未响应！");
		}
		
		try {
			if(Utlity.checkStringNull(body)){
				body = "融宝支付--支付请求";
			} else {
				body = Base64Util.getFromBase64(body);
			}
			QcbEmployeeBankcard qeb = this.qcbEmployeeBankcardService.get(bankcard);
			if(qeb == null){
				return ResultManager.createFailResult("未找到绑定的银行卡！");
			}
			if(!qeb.getQcbEmployee().equals(qe.getUuid())){
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
					
					Map<String, Object> returnMap = ReapalUtlity.debit(bankcardNo, realName, idcard, mobile, qe.getUuid(), orderNumStr, userIp, totalFees.setScale(0).toString(), ReapalConfig.notify_url_qcb_recharge);
					QcbOrderinfoByThirdparty qobt = new QcbOrderinfoByThirdparty();
					qobt.setUuid(UUID.randomUUID().toString());
					qobt.setType(QcbOrderinfoByThirdpartyType.REAPAL);
					qobt.setAccount(qe.getUuid());
					qobt.setAccountType(QcbOrderinfoByThirdpartyAccountType.EMPLOYEE);
					qobt.setOrderNum(orderNumStr);
					qobt.setBody(body);
					qobt.setTotalFee(paydivide);
					qobt.setPaySource(MD5.getMD5(qe.getUuid()));
					qobt.setStatus(returnMap.get("result_code").toString());
					qobt.setCreatetime(new Timestamp(System.currentTimeMillis()));
					qobt.setErrCode(returnMap.get("result_code") == null ? "" : returnMap.get("result_code").toString());
					qobt.setErrCodeDes(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					qobt.setMessage(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					String certificate = returnMap.get("certificate") == null ? "" : returnMap.get("certificate").toString();
					System.out.println("certificate:"+certificate);
					if(QcbOrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(returnMap.get("result_code").toString())){
//						obt.setPrepayId(returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString());
						String bindId = returnMap.get("bind_id") == null ? "" : returnMap.get("bind_id").toString();
						qeb.setBindingId(bindId);
						
						QcbEmployeeHistory qeh = new QcbEmployeeHistory();
						qeh.setUuid(UUID.randomUUID().toString());
						qeh.setQcbEmployee(qe.getUuid());
						qeh.setIncome(paydivide);
						qeh.setPay(BigDecimal.ZERO);
						qeh.setAccountBalance(qe.getAccountBalance().add(paydivide));
						qeh.setOrderId(qobt.getUuid());
						qeh.setOrderType(QcbEmployeeHistoryOrderType.PAY_TYPE_REAPAL);
						qeh.setOrderNum(qobt.getOrderNum());
						qeh.setType(QcbEmployeeHistoryType.INCOME);
						qeh.setStatus(QcbEmployeeHistoryStatus.TRANSACTING);
						qeh.setCreatetime(new Timestamp(System.currentTimeMillis()));
						qeh.setCompanyAccount(CompanyAccountUuid.REAPAL);//应该是记录跟畅捷支付绑定的账号
						qeh.setBankcard(qeb.getUuid());
						
						//记录单笔交易手续费
//						BigDecimal poundage = paydivide.multiply(ReapalUtil.POUNDAGE_FEE).setScale(2,BigDecimal.ROUND_HALF_UP);
//						qeh.setPoundage(poundage);
						//记录单笔交易手续费
//						BigDecimal poundage = paydivide.multiply(ReapalUtil.POUNDAGE_FEE).setScale(2,BigDecimal.ROUND_HALF_UP);
//						if(poundage.compareTo(BigDecimal.valueOf(Double.parseDouble("2.0"))) < 0){
//							qeh.setPoundage(BigDecimal.valueOf(Double.parseDouble("2.0")));
//						} else {
//							qeh.setPoundage(poundage);
//						}
						BigDecimal poundage = Utlity.getPoundage4Pay(paydivide, bank.getShortName());
						qeh.setPoundage(poundage);
						
						this.qcbEmployeeHistoryService.insertwechart(qobt, qeh, qeb);
						return ResultManager.createDataResult(qobt.getUuid(), "操作成功！");
					} else {
						String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
						qobt.setPrepayId(prepayId);
						this.qcbOrderinfoByThirdpartyService.insert(qobt);
						return ResultManager.createFailResult(qobt.getErrCodeDes());
					}
				} else {
					String bindingId = qeb.getBindingId();
					Map<String, Object> returnMap = ReapalUtlity.bindCard(qe.getUuid(), bindingId, orderNumStr, userIp, totalFees.setScale(0).toString(), ReapalConfig.notify_url_qcb_recharge);
					QcbOrderinfoByThirdparty qobt = new QcbOrderinfoByThirdparty();
					qobt.setUuid(UUID.randomUUID().toString());
					qobt.setType(QcbOrderinfoByThirdpartyType.REAPAL);
					qobt.setAccount(qe.getUuid());
					qobt.setAccountType(QcbOrderinfoByThirdpartyAccountType.EMPLOYEE);
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
					if(QcbOrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(returnMap.get("result_code").toString())){
						
						QcbEmployeeHistory qeh = new QcbEmployeeHistory();
						qeh.setUuid(UUID.randomUUID().toString());
						qeh.setQcbEmployee(qe.getUuid());
						qeh.setIncome(paydivide);
						qeh.setPay(BigDecimal.ZERO);
						qeh.setAccountBalance(qe.getAccountBalance().add(paydivide));
						qeh.setOrderId(qobt.getUuid());
						qeh.setOrderType(QcbEmployeeHistoryOrderType.PAY_TYPE_REAPAL);
						qeh.setOrderNum(qobt.getOrderNum());
						qeh.setType(QcbEmployeeHistoryType.INCOME);
						qeh.setStatus(QcbEmployeeHistoryStatus.TRANSACTING);
						qeh.setCreatetime(new Timestamp(System.currentTimeMillis()));
						qeh.setCompanyAccount(CompanyAccountUuid.REAPAL);//应该是记录跟融宝支付绑定的账号
						qeh.setBankcard(qeb.getUuid());
						
						//记录单笔交易手续费
//						BigDecimal poundage = paydivide.multiply(ReapalUtil.POUNDAGE_FEE).setScale(2,BigDecimal.ROUND_HALF_UP);
//						qeh.setPoundage(poundage);
						//记录单笔交易手续费
//						BigDecimal poundage = paydivide.multiply(ReapalUtil.POUNDAGE_FEE).setScale(2,BigDecimal.ROUND_HALF_UP);
//						if(poundage.compareTo(BigDecimal.valueOf(Double.parseDouble("2.0"))) < 0){
//							qeh.setPoundage(BigDecimal.valueOf(Double.parseDouble("2.0")));
//						} else {
//							qeh.setPoundage(poundage);
//						}
						BigDecimal poundage = Utlity.getPoundage4Pay(paydivide, bank.getShortName());
						qeh.setPoundage(poundage);
						
						this.qcbEmployeeHistoryService.insertwechart(qobt, qeh);
						return ResultManager.createDataResult(qobt.getUuid(), "操作成功！");
					} else {
						String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
						qobt.setPrepayId(prepayId);
						this.qcbOrderinfoByThirdpartyService.insert(qobt);
						return ResultManager.createFailResult(qobt.getErrCodeDes());
					}
				}
				
			} else if ("resend".equals(type)) {
				if(Utlity.checkStringNull(orderNum)){
					return ResultManager.createFailResult("操作错误，缺少参数！");
				}
				QcbOrderinfoByThirdparty qobt = this.qcbOrderinfoByThirdpartyService.get(orderNum);
				if(qobt == null){
					return ResultManager.createFailResult("操作错误，请重新购买！");
				}
				Map<String, Object> returnMap = ReapalUtlity.reSms(orderNum);
				QcbOrderinfoByThirdparty qobtN = new QcbOrderinfoByThirdparty();
				qobtN.setUuid(UUID.randomUUID().toString());
				qobtN.setType(QcbOrderinfoByThirdpartyType.REAPAL);
				qobtN.setAccount(qe.getUuid());
				qobtN.setAccountType(QcbOrderinfoByThirdpartyAccountType.EMPLOYEE);
				qobtN.setOrderNum(orderNumStr);
				qobtN.setBody(body);
				qobtN.setTotalFee(BigDecimal.ZERO);
				qobtN.setPaySource(MD5.getMD5(qe.getUuid()));
				qobtN.setStatus(returnMap.get("result_code").toString());
				qobtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
				qobtN.setErrCode(returnMap.get("result_code") == null ? "" : returnMap.get("result_code").toString());
				qobtN.setErrCodeDes(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
				qobtN.setMessage(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
				if(QcbOrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(returnMap.get("result_code").toString())){
					qobtN.setPrepayId(returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString());
				} else {
					String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
					qobtN.setPrepayId(prepayId);
					Map<String, String> inputParams = new HashMap<String, String>();
					inputParams.put("order", qobt.getUuid());
					inputParams.put("qcbEmployee", qobt.getAccount());
					inputParams.put("orderType", QcbEmployeeHistoryOrderType.PAY_TYPE_REAPAL);
					inputParams.put("type", QcbEmployeeHistoryType.INCOME);
					List<Entity> listHistory = this.qcbEmployeeHistoryService.getListForPage(inputParams, -1, -1, null, QcbEmployeeHistory.class);
					QcbEmployeeHistory qeh = null;
					if(listHistory != null && listHistory.size() > 0){
						qeh = (QcbEmployeeHistory) listHistory.get(0);
					}
					
					qobt.setStatus(returnMap.get("result_code").toString());
					qobt.setErrCode(returnMap.get("result_code") == null ? "" : returnMap.get("result_code").toString());
					qobt.setErrCodeDes(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					qobt.setMessage(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					qeh.setStatus(QcbEmployeeHistoryStatus.FAIL);
					this.qcbEmployeeHistoryService.updateWithdraw(qobt, qobtN, qeh);
					return ResultManager.createFailResult("发送失败");
				}
				this.qcbOrderinfoByThirdpartyService.insert(qobtN);
				return ResultManager.createDataResult(qobt.getUuid(), "操作成功！");
			} else if ("check".equals(type)) {
				if(Utlity.checkStringNull(orderNum)){
					return ResultManager.createFailResult("操作错误，缺少参数！");
				}
				QcbOrderinfoByThirdparty qobt = this.qcbOrderinfoByThirdpartyService.get(orderNum);
				if(qobt == null){
					return ResultManager.createFailResult("操作错误，请重新购买！");
				}
				code = Base64Util.getFromBase64(code);
				Map<String, Object> returnMap = ReapalUtlity.confirmPay(qobt.getOrderNum(), code);
				QcbOrderinfoByThirdparty qobtN = new QcbOrderinfoByThirdparty();
				qobtN.setUuid(UUID.randomUUID().toString());
				qobtN.setType(QcbOrderinfoByThirdpartyType.REAPAL);
				qobtN.setAccount(qe.getUuid());
				qobtN.setAccountType(QcbOrderinfoByThirdpartyAccountType.EMPLOYEE);
				qobtN.setOrderNum(orderNumStr);
				qobtN.setBody("融宝支付--支付确认");
				qobtN.setTotalFee(BigDecimal.ZERO);
				qobtN.setPaySource(MD5.getMD5(qe.getUuid()));
				qobtN.setStatus(returnMap.get("result_code").toString());
				qobtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
				qobtN.setErrCode(returnMap.get("result_code") == null ? "" : returnMap.get("result_code").toString());
				qobtN.setErrCodeDes(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
				qobtN.setMessage(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
				if(QcbOrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(returnMap.get("result_code").toString())){
					qobtN.setPrepayId(returnMap.get("trade_no") == null ? "" : returnMap.get("trade_no").toString());
					
					this.qcbOrderinfoByThirdpartyService.insert(qobtN);
					return ResultManager.createSuccessResult("操作成功！");
				} else {
					String prepayId = returnMap.get("trade_no") == null ? "" : returnMap.get("trade_no").toString();
					qobtN.setPrepayId(prepayId);
					Map<String, String> inputParams = new HashMap<String, String>();
					inputParams.put("order", qobt.getUuid());
					inputParams.put("qcbEmployee", qobt.getAccount());
					inputParams.put("orderType", QcbEmployeeHistoryOrderType.PAY_TYPE_REAPAL);
					inputParams.put("type", QcbEmployeeHistoryType.INCOME);
					List<Entity> listHistory = this.qcbEmployeeHistoryService.getListForPage(inputParams, -1, -1, null, QcbEmployeeHistory.class);
					QcbEmployeeHistory qeh = null;
					if(listHistory != null && listHistory.size() > 0){
						qeh = (QcbEmployeeHistory) listHistory.get(0);
					}
					
					qobt.setStatus(returnMap.get("result_code").toString());
					qobt.setErrCode(returnMap.get("result_code") == null ? "" : returnMap.get("result_code").toString());
					qobt.setErrCodeDes(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					qobt.setMessage(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					qeh.setStatus(QcbEmployeeHistoryStatus.FAIL);
					this.qcbEmployeeHistoryService.updateWithdraw(qobt, qobtN, qeh);
//					this.qcbOrderinfoByThirdpartyService.insert(qobtN);
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
	
	/**
	 * 购买活期理财产品
	 * @param price
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/currentPay", method = RequestMethod.POST)
	@ActionParam(key = "price", type = DataType.STRING, message = "买入金额", required = true)
	@ActionParam(key = "code", type = DataType.STRING, message = "短信验证码", required = true)
	@ResponseBody
	protected Result currentPay(HttpServletRequest request, String price, String code){
		
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency4Web(price)) {
			return ResultManager.createFailResult("买入金额输入错误!");
		}
		BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));
		pay = pay.divide(BigDecimal.valueOf(100));
		
		if(code == null || "".equals(code)){
			return ResultManager.createFailResult("请先获取验证码");
		}
		code = Base64Util.getFromBase64(code);
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("mobile", qe.getMobile());
		inputParams.put("status", MobileCodeStatus.NORMAL);
		inputParams.put("type", MobileCodeTypes.FUNDCODE);
		List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(inputParams, -1, -1, null, MobileCode.class);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			return ResultManager.createFailResult("请先获取验证码");
		}
		if(!MobileCodeTypes.FUNDCODE.equals(mc.getType())){
			return ResultManager.createFailResult("验证码输入错误！");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("验证码输入错误！");
		}
		
		FundPublish fp = this.fundPublishService.get(FundPublishUuid.CURRENT);
		if(fp == null){
			return ResultManager.createFailResult("该功能暂未开通！");
		}
		BigDecimal netValue = fp.getNetWorth();
		
		synchronized(this){//购买需要线程同步机制，防止出现募集金额 余量不足的情况
			if(qe.getAccountBalance().compareTo(pay) == -1){
				return ResultManager.createFailResult("用户余额不足！");
			}
			
			BigDecimal currentAccountAdd = pay.divide(netValue, 8, BigDecimal.ROUND_HALF_UP);
			
			QcbEmployeeHistory qeh = new QcbEmployeeHistory();
			qeh.setUuid(UUID.randomUUID().toString());
			qeh.setQcbEmployee(qe.getUuid());
			qeh.setOrderId(qeh.getUuid());
			qeh.setOrderNum(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_OHER,Utlity.BILL_PAYTYPE_BALANCE,Utlity.BILL_PURPOSE_BUY));
			qeh.setPay(pay);
			qeh.setIncome(BigDecimal.ZERO);
			qeh.setPoundage(BigDecimal.ZERO);
			qeh.setAccountBalance(qe.getAccountBalance().subtract(pay));
			qeh.setStatus(QcbEmployeeHistoryStatus.SUCCESS);
			qeh.setType(QcbEmployeeHistoryType.CURRENT_BUY);
			qeh.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			qe.setCurrentAccount(qe.getCurrentAccount().add(currentAccountAdd));
			qe.setAccountBalance(qe.getAccountBalance().subtract(pay));
			this.qcbEmployeeService.updateCurrentPay(qe, qeh);
		}
		
		return ResultManager.createSuccessResult("操作成功");
	}
	
	/**
	 * 赎回活期理财产品
	 * @param price
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/currentReturn", method = RequestMethod.POST)
	@ActionParam(key = "price", type = DataType.STRING, message = "买入金额", required = true)
	@ActionParam(key = "code", type = DataType.STRING, message = "短信验证码", required = true)
	@ResponseBody
	protected Result currentReturn(HttpServletRequest request, String price, String code){
		
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency4Web(price)) {
			return ResultManager.createFailResult("赎回金额输入错误!");
		}
		BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));
		pay = pay.divide(BigDecimal.valueOf(100));
		
		if(code == null || "".equals(code)){
			return ResultManager.createFailResult("请先获取验证码");
		}
		code = Base64Util.getFromBase64(code);
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("mobile", qe.getMobile());
		inputParams.put("status", MobileCodeStatus.NORMAL);
		inputParams.put("type", MobileCodeTypes.FUNDCODE);
		List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(inputParams, -1, -1, null, MobileCode.class);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			return ResultManager.createFailResult("请先获取验证码");
		}
		if(!MobileCodeTypes.FUNDCODE.equals(mc.getType())){
			return ResultManager.createFailResult("验证码输入错误！");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("验证码输入错误！");
		}
		
		FundPublish fp = this.fundPublishService.get(FundPublishUuid.CURRENT);
		if(fp == null){
			return ResultManager.createFailResult("该功能暂未开通！");
		}
		BigDecimal netValue = fp.getNetWorth();
		
		synchronized(this){//购买需要线程同步机制，防止出现募集金额 余量不足的情况
			if(qe.getCurrentAccount().multiply(netValue).compareTo(pay) == -1){
				return ResultManager.createFailResult("理财余额不足！");
			}
			
			BigDecimal currentAccountSub = pay.divide(netValue, 8, BigDecimal.ROUND_HALF_UP);
			
			QcbEmployeeHistory qeh = new QcbEmployeeHistory();
			qeh.setUuid(UUID.randomUUID().toString());
			qeh.setQcbEmployee(qe.getUuid());
			qeh.setOrderId(qeh.getUuid());
			qeh.setOrderNum(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_OHER,Utlity.BILL_PAYTYPE_BALANCE,Utlity.BILL_PURPOSE_DIVIDEND));
			qeh.setPay(BigDecimal.ZERO);
			qeh.setIncome(pay);
			qeh.setPoundage(BigDecimal.ZERO);
			qeh.setAccountBalance(qe.getAccountBalance().add(pay));
			qeh.setStatus(QcbEmployeeHistoryStatus.SUCCESS);
			qeh.setType(QcbEmployeeHistoryType.CURRENT_RETURN);
			qeh.setCreatetime(new Timestamp(System.currentTimeMillis()));
			if(qe.getCurrentAccount().subtract(currentAccountSub).multiply(netValue).compareTo(new BigDecimal(0.01)) == -1){
				qe.setCurrentAccount(BigDecimal.ZERO);
			}else{
				qe.setCurrentAccount(qe.getCurrentAccount().subtract(currentAccountSub));
			}
			qe.setAccountBalance(qe.getAccountBalance().add(pay));
			this.qcbEmployeeService.updateCurrentPay(qe, qeh);
		}
		
		return ResultManager.createSuccessResult("操作成功");
	}
	
	/**
	 * 变更活期理财自动买入状态
	 * @param flagCurrent
	 * @return
	 */
	@RequestMapping(value = "/editFlagCurrent", method = RequestMethod.POST)
	@ActionParam(key = "flagCurrent", type = DataType.BOOLEAN, message = "是否自动买入", required = true)
	@ResponseBody
	protected Result editFlagCurrent(HttpServletRequest request, Boolean flagCurrent){
		
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		qe.setFlagCurrent(flagCurrent);
		this.qcbEmployeeService.update(qe);
		
		return ResultManager.createSuccessResult("操作成功");
	}
}
