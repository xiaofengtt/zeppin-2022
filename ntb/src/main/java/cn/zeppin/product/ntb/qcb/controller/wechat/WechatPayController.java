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
 * ???????????????????????????????????????
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
	 * ????????????????????????
	 * ??????????????? ????????????????????????????????????????????????????????????????????????
	 * ?????????
	 * 1.????????????
	 * 4.???????????????????????????????????? ????????????????????????????????????????????????????????????
	 * 2.?????????????????????
	 * 3.??????type ????????????????????????????????????
	 * 5.????????????????????????????????????????????????
	 * 6.?????????????????????????????????????????? ???????????? ??????????????? ?????????????????????????????????
	 * @param price
	 * @param product
	 * @param type ???????????? balance-?????? wechart-?????? alipay-????????? bankcard-?????????
	 * @return
	 */
	@RequestMapping(value = "/productPay", method = RequestMethod.POST)
	@ActionParam(key = "price", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "product", type = DataType.STRING, message = "??????????????????", required = true)
	@ActionParam(key = "type", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "coupon", type = DataType.STRING, message = "?????????")
	@ActionParam(key = "code", type = DataType.STRING, message = "???????????????")
	@ResponseBody
	protected Result productPay(HttpServletRequest request, String price, String product, String type, String coupon, String code){
		
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		QcbEmployee employee = (QcbEmployee)request.getAttribute("employee");
		
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency4Web(price)) {
			return ResultManager.createFailResult("????????????????????????!");
		}
		BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));
		BigDecimal paydivide = pay.divide(BigDecimal.valueOf(100));
		//????????????????????????????????????
		
		synchronized(this){//????????????????????????????????????????????????????????? ?????????????????????
			try {
				BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(product);
				if (bfpp == null) {
					return ResultManager.createFailResult("????????????????????????????????????");
				}
				Bank bank = this.bankService.get(bfpp.getCustodian());
				if(bank == null){
					return ResultManager.createFailResult("?????????????????????????????????");
				}
				/*
				 * 4.???????????????????????????????????? ????????????????????????????????????????????????????????????
				 */
				if(paydivide.compareTo(bfpp.getMinInvestAmount()) == -1){
					return ResultManager.createFailResult("?????????????????????"+bfpp.getMinInvestAmount()+"???");
				}
				
				if((bfpp.getMaxInvestAmount().compareTo(BigDecimal.ZERO)) > 0 && paydivide.compareTo(bfpp.getMaxInvestAmount()) == 1){
					return ResultManager.createFailResult("?????????????????????"+bfpp.getMaxInvestAmount()+"???");
				}
				
				//???????????? ????????????????????????
				//??????????????????????????????-(????????????+????????????-????????????-????????????)
				BigDecimal totalamount = bfpp.getCollectAmount();//??????????????????
				BigDecimal realCollect = bfpp.getRealCollect();//??????????????????
				BigDecimal realAmount = totalamount.subtract(realCollect);
				if(realAmount.compareTo(paydivide) == -1){//?????????????????????????????????
					return ResultManager.createFailResult("??????????????????");
				}
				//service???employee,bfpp,pay,?????????????????? ??????????????????
				if(Utlity.PAY_TYPE_BALANCE.equals(type)){
					String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_QCB,Utlity.BILL_PAYTYPE_BALANCE,Utlity.BILL_PURPOSE_INCOME);
					if(this.qcbEmployeeHistoryService.checkOrderNum(orderNumStr)){
						return ResultManager.createFailResult("????????????????????????????????????");
					}
					//??????????????????????????????
					if(code == null || "".equals(code)){
						return ResultManager.createFailResult("?????????????????????");
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
						return ResultManager.createFailResult("?????????????????????");
					}
					if(!MobileCodeTypes.FUNDCODE.equals(mc.getType())){
						return ResultManager.createFailResult("????????????????????????");
					}
					
					if(!code.equals(mc.getCode())){//????????? ?????????
						return ResultManager.createFailResult("????????????????????????");
					}
					
					/*
					 * ????????????
					 * ??????????????????????????????????????????
					 * ???????????????????????????????????? ???????????????
					 * ????????????????????????
					 * 
					 */
					BigDecimal total = employee.getAccountBalance();
					if(total.compareTo(paydivide) == -1){
						return ResultManager.createFailResult("?????????????????????");
					}
					
					//??????????????? ???????????????
					total = total.subtract(paydivide);
					BigDecimal totalInvest = employee.getTotalInvest();
					totalInvest = totalInvest.add(paydivide);
					employee.setAccountBalance(total);
					employee.setTotalInvest(totalInvest);
					
					Timestamp time = new Timestamp(System.currentTimeMillis());
					
					QcbEmployeeHistory qeh = new QcbEmployeeHistory();
					qeh.setUuid(UUID.randomUUID().toString());
					qeh.setQcbEmployee(employee.getUuid());
					
					//????????????
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
					
					//????????????????????????????????????????????????????????????????????? ????????????update ???????????????????????? ???????????? ?????????????????????????????????
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
					//?????????????????????
					if((bfpp.getMaxInvestAmount().compareTo(BigDecimal.ZERO)) > 0 && (qepb.getTotalAmount().compareTo(bfpp.getMaxInvestAmount())) == 1){
						return ResultManager.createFailResult("????????????????????????????????????");
					}
					qepb.setType(QcbEmployeeProductBuyProductType.BANK_PRODUCT);
					//????????????????????????
					bfpp.setAccountBalance(bfpp.getAccountBalance().add(qeh.getPay()));
					//????????????????????????????????????
					bfpp.setRealCollect(bfpp.getRealCollect().add(qeh.getPay()));
					
					//??????????????????PDF??????????????????
					//????????? ????????????????????????????????????????????????
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
					qepba.setName("??????????????????????????????????????????");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("scode", orderNumStr);
			    	params.put("realname", employee.getRealname());
			    	params.put("phone", Utlity.getStarMobile(employee.getMobile()));
			    	params.put("idcard", Utlity.getStarIdcard(employee.getIdcard()));
			    	params.put("productName", bank.getShortName()+bfpp.getName());
			    	String priceRMB = RMBUtlity.arabNumToChineseRMB(Double.parseDouble(qepb.getTotalAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
			    	String priceStr = "????????????"+Utlity.numFormat4UnitDetailLess(qepb.getTotalAmount())+"??? (?????????"+priceRMB+"???)";
			    	params.put("price", priceStr);
			    	params.put("uuid", qepba.getUuid());
			    	params.put("fileName", "??????????????????????????????????????????"+orderNumStr);
					Map<String, Object> resultPDF = PDFUtlity.ToPdf(params);
					if((Boolean) resultPDF.get("result")){
						qepba.setStatus(QcbEmployeeProductBuyAgreementStatus.SUCCESS);
						qepba.setUrl(resultPDF.get("url")==null?"":resultPDF.get("url").toString());
					} else {
						qepba.setStatus(QcbEmployeeProductBuyAgreementStatus.FAIL);
						qepba.setUrl("");
					}
					//?????????????????????????????????
					MobileCode mcode = new MobileCode();
					String content = "??????????????????????????????"+Utlity.timeSpanToChinaDateString(time)+"????????????"+bank.getShortName()+bfpp.getName()
							+"??????????????????????????????"+Utlity.numFormat4UnitDetailLess(qeh.getPay())+"??????";
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
					
					//??????????????????????????????????????????????????????????????????????????????
					Boolean flagCoupon = Utlity.checkStringNull(coupon);
					if(!flagCoupon){
						QcbEmployeeCoupon qec = this.qcbEmployeeCouponService.get(coupon);
						if(qec != null){
							//???????????????????????????????????????
							Coupon co = this.couponService.get(qec.getCoupon());
							if(co == null){
								return ResultManager.createFailResult("????????????????????????");
							}
							if(!QcbEmployeeCouponStatus.UNUSE.equals(qec.getStatus())){
								return ResultManager.createFailResult("???????????????????????????");
							}
							//?????????????????????
							qec.setStatus(QcbEmployeeCouponStatus.USED);
							//??????????????????????????? ??????????????????
							this.qcbEmployeeHistoryService.insertProductBuy4Balance(mc, bfpp, employee, qeh, qepba, qepb, isUpdate, isUpdate2, mcode, qec);
						} else {
							return ResultManager.createFailResult("???????????????????????????");
						}
					} else {
						this.qcbEmployeeHistoryService.insertProductBuy4Balance(mc, bfpp, employee, qeh, qepba, qepb, isUpdate, isUpdate2, mcode, null);
					}
				} else {
					return ResultManager.createFailResult("?????????????????????");
				}
			} catch (TransactionException te) {
				super.flushAll();
				return ResultManager.createFailResult(te.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult("??????????????????");
			}
		}
		
		return ResultManager.createSuccessResult("????????????");
	}
	
	/**
	 * ????????????????????????
	 * @param uuid
	 * @param totalFee????????? ??????
	 * @param body
	 * @param type
	 * @param code
	 * @param orderNum
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@RequestMapping(value = "/rechargeByReapal", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "price", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "bankcard", type = DataType.STRING, message = "???????????????????????????", required = true)
	@ActionParam(key = "type", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "body", type = DataType.STRING, message = "????????????")
	@ActionParam(key = "code", type = DataType.STRING, message = "???????????????")
	@ActionParam(key = "orderNum", type = DataType.STRING, message = "send ?????????????????????")
	@ResponseBody
	protected Result rechargeByReapal(String uuid, String price, String bankcard, String body, String type,
			String code,String orderNum, HttpServletRequest request) throws IOException, DocumentException {
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency4Web(price)) {
			return ResultManager.createFailResult("????????????????????????!");
		}
		BigDecimal totalFees = BigDecimal.valueOf(Double.parseDouble(price));
		BigDecimal paydivide = totalFees.divide(BigDecimal.valueOf(100));
		
		String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_QCB,Utlity.BILL_PAYTYPE_WECHART,Utlity.BILL_PURPOSE_INCOME);
		if(this.qcbEmployeeHistoryService.checkOrderNum(orderNumStr)){
			return ResultManager.createFailResult("????????????????????????????????????");
		}
		
		try {
			if(Utlity.checkStringNull(body)){
				body = "????????????--????????????";
			} else {
				body = Base64Util.getFromBase64(body);
			}
			QcbEmployeeBankcard qeb = this.qcbEmployeeBankcardService.get(bankcard);
			if(qeb == null){
				return ResultManager.createFailResult("??????????????????????????????");
			}
			if(!qeb.getQcbEmployee().equals(qe.getUuid())){
				return ResultManager.createFailResult("????????????????????????????????????");
			}
			Bank bank = this.bankService.get(qeb.getBank());
			if(bank == null){
				return ResultManager.createFailResult("????????????????????????");
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
						qeh.setCompanyAccount(CompanyAccountUuid.REAPAL);//?????????????????????????????????????????????
						qeh.setBankcard(qeb.getUuid());
						
						//???????????????????????????
//						BigDecimal poundage = paydivide.multiply(ReapalUtil.POUNDAGE_FEE).setScale(2,BigDecimal.ROUND_HALF_UP);
//						qeh.setPoundage(poundage);
						//???????????????????????????
//						BigDecimal poundage = paydivide.multiply(ReapalUtil.POUNDAGE_FEE).setScale(2,BigDecimal.ROUND_HALF_UP);
//						if(poundage.compareTo(BigDecimal.valueOf(Double.parseDouble("2.0"))) < 0){
//							qeh.setPoundage(BigDecimal.valueOf(Double.parseDouble("2.0")));
//						} else {
//							qeh.setPoundage(poundage);
//						}
						BigDecimal poundage = Utlity.getPoundage4Pay(paydivide, bank.getShortName());
						qeh.setPoundage(poundage);
						
						this.qcbEmployeeHistoryService.insertwechart(qobt, qeh, qeb);
						return ResultManager.createDataResult(qobt.getUuid(), "???????????????");
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
						qeh.setCompanyAccount(CompanyAccountUuid.REAPAL);//?????????????????????????????????????????????
						qeh.setBankcard(qeb.getUuid());
						
						//???????????????????????????
//						BigDecimal poundage = paydivide.multiply(ReapalUtil.POUNDAGE_FEE).setScale(2,BigDecimal.ROUND_HALF_UP);
//						qeh.setPoundage(poundage);
						//???????????????????????????
//						BigDecimal poundage = paydivide.multiply(ReapalUtil.POUNDAGE_FEE).setScale(2,BigDecimal.ROUND_HALF_UP);
//						if(poundage.compareTo(BigDecimal.valueOf(Double.parseDouble("2.0"))) < 0){
//							qeh.setPoundage(BigDecimal.valueOf(Double.parseDouble("2.0")));
//						} else {
//							qeh.setPoundage(poundage);
//						}
						BigDecimal poundage = Utlity.getPoundage4Pay(paydivide, bank.getShortName());
						qeh.setPoundage(poundage);
						
						this.qcbEmployeeHistoryService.insertwechart(qobt, qeh);
						return ResultManager.createDataResult(qobt.getUuid(), "???????????????");
					} else {
						String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
						qobt.setPrepayId(prepayId);
						this.qcbOrderinfoByThirdpartyService.insert(qobt);
						return ResultManager.createFailResult(qobt.getErrCodeDes());
					}
				}
				
			} else if ("resend".equals(type)) {
				if(Utlity.checkStringNull(orderNum)){
					return ResultManager.createFailResult("??????????????????????????????");
				}
				QcbOrderinfoByThirdparty qobt = this.qcbOrderinfoByThirdpartyService.get(orderNum);
				if(qobt == null){
					return ResultManager.createFailResult("?????????????????????????????????");
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
					return ResultManager.createFailResult("????????????");
				}
				this.qcbOrderinfoByThirdpartyService.insert(qobtN);
				return ResultManager.createDataResult(qobt.getUuid(), "???????????????");
			} else if ("check".equals(type)) {
				if(Utlity.checkStringNull(orderNum)){
					return ResultManager.createFailResult("??????????????????????????????");
				}
				QcbOrderinfoByThirdparty qobt = this.qcbOrderinfoByThirdpartyService.get(orderNum);
				if(qobt == null){
					return ResultManager.createFailResult("?????????????????????????????????");
				}
				code = Base64Util.getFromBase64(code);
				Map<String, Object> returnMap = ReapalUtlity.confirmPay(qobt.getOrderNum(), code);
				QcbOrderinfoByThirdparty qobtN = new QcbOrderinfoByThirdparty();
				qobtN.setUuid(UUID.randomUUID().toString());
				qobtN.setType(QcbOrderinfoByThirdpartyType.REAPAL);
				qobtN.setAccount(qe.getUuid());
				qobtN.setAccountType(QcbOrderinfoByThirdpartyAccountType.EMPLOYEE);
				qobtN.setOrderNum(orderNumStr);
				qobtN.setBody("????????????--????????????");
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
					return ResultManager.createSuccessResult("???????????????");
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
					return ResultManager.createFailResult("???????????????"+qobtN.getErrCodeDes()+"???");
				}
			} else {
				return ResultManager.createFailResult("???????????????");
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("?????????????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????
	 * @param price
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/currentPay", method = RequestMethod.POST)
	@ActionParam(key = "price", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "code", type = DataType.STRING, message = "???????????????", required = true)
	@ResponseBody
	protected Result currentPay(HttpServletRequest request, String price, String code){
		
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency4Web(price)) {
			return ResultManager.createFailResult("????????????????????????!");
		}
		BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));
		pay = pay.divide(BigDecimal.valueOf(100));
		
		if(code == null || "".equals(code)){
			return ResultManager.createFailResult("?????????????????????");
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
			return ResultManager.createFailResult("?????????????????????");
		}
		if(!MobileCodeTypes.FUNDCODE.equals(mc.getType())){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		FundPublish fp = this.fundPublishService.get(FundPublishUuid.CURRENT);
		if(fp == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		BigDecimal netValue = fp.getNetWorth();
		
		synchronized(this){//????????????????????????????????????????????????????????? ?????????????????????
			if(qe.getAccountBalance().compareTo(pay) == -1){
				return ResultManager.createFailResult("?????????????????????");
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
		
		return ResultManager.createSuccessResult("????????????");
	}
	
	/**
	 * ????????????????????????
	 * @param price
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/currentReturn", method = RequestMethod.POST)
	@ActionParam(key = "price", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "code", type = DataType.STRING, message = "???????????????", required = true)
	@ResponseBody
	protected Result currentReturn(HttpServletRequest request, String price, String code){
		
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency4Web(price)) {
			return ResultManager.createFailResult("????????????????????????!");
		}
		BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));
		pay = pay.divide(BigDecimal.valueOf(100));
		
		if(code == null || "".equals(code)){
			return ResultManager.createFailResult("?????????????????????");
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
			return ResultManager.createFailResult("?????????????????????");
		}
		if(!MobileCodeTypes.FUNDCODE.equals(mc.getType())){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		FundPublish fp = this.fundPublishService.get(FundPublishUuid.CURRENT);
		if(fp == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		BigDecimal netValue = fp.getNetWorth();
		
		synchronized(this){//????????????????????????????????????????????????????????? ?????????????????????
			if(qe.getCurrentAccount().multiply(netValue).compareTo(pay) == -1){
				return ResultManager.createFailResult("?????????????????????");
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
		
		return ResultManager.createSuccessResult("????????????");
	}
	
	/**
	 * ????????????????????????????????????
	 * @param flagCurrent
	 * @return
	 */
	@RequestMapping(value = "/editFlagCurrent", method = RequestMethod.POST)
	@ActionParam(key = "flagCurrent", type = DataType.BOOLEAN, message = "??????????????????", required = true)
	@ResponseBody
	protected Result editFlagCurrent(HttpServletRequest request, Boolean flagCurrent){
		
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		qe.setFlagCurrent(flagCurrent);
		this.qcbEmployeeService.update(qe);
		
		return ResultManager.createSuccessResult("????????????");
	}
}
