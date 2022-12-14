package cn.zeppin.product.ntb.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import cn.zeppin.product.ntb.backadmin.service.api.IAliQrcodeService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkPaymentService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountHistoryService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.ICouponService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorAccountHistoryService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorBankcardService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorCouponService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorProductBuyAgreementService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorProductBuyService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorRedPacketService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.backadmin.service.api.IOrderinfoByThirdpartyService;
import cn.zeppin.product.ntb.backadmin.service.api.IPlatformAccountService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.BkPayment;
import cn.zeppin.product.ntb.core.entity.CompanyAccount.CompanyAccountUuid;
import cn.zeppin.product.ntb.core.entity.Coupon;
import cn.zeppin.product.ntb.core.entity.FundPublish;
import cn.zeppin.product.ntb.core.entity.FundPublish.FundPublishUuid;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryProcessStatus;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.InvestorBankcard;
import cn.zeppin.product.ntb.core.entity.InvestorBankcard.InvestorBankcardStatus;
import cn.zeppin.product.ntb.core.entity.InvestorCoupon;
import cn.zeppin.product.ntb.core.entity.InvestorCoupon.InvestorCouponStatus;
import cn.zeppin.product.ntb.core.entity.InvestorInformation;
import cn.zeppin.product.ntb.core.entity.InvestorInformation.InvestorInformationStatus;
import cn.zeppin.product.ntb.core.entity.InvestorInformation.InvestorInformationTitle;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy.InvestorProductBuyProductType;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy.InvestorProductBuyStage;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuyAgreement;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuyAgreement.InvestorProductBuyAgreementStatus;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuyAgreement.InvestorProductBuyAgreementType;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.OrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.OrderinfoByThirdparty.OrderinfoByThirdpartyResultStatus;
import cn.zeppin.product.ntb.core.entity.OrderinfoByThirdparty.OrderinfoByThirdpartyReturnStatus;
import cn.zeppin.product.ntb.core.entity.OrderinfoByThirdparty.OrderinfoByThirdpartyType;
import cn.zeppin.product.ntb.core.entity.PlatformAccount;
import cn.zeppin.product.ntb.core.entity.PlatformAccount.PlatformAccountUuid;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.web.vo.AliQrcodeVO;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.HttpUtility;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.PDFUtlity;
import cn.zeppin.product.utility.RMBUtlity;
import cn.zeppin.product.utility.SendSms;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.XMLUtils;
import cn.zeppin.product.utility.chanpay.ChanPayUtil;
import cn.zeppin.product.utility.reapal.ReapalConfig;
import cn.zeppin.product.utility.reapal.ReapalUtlity;
import cn.zeppin.product.utility.reapal.data.WithdrawData;
import cn.zeppin.product.utility.reapal.data.WithdrawData.WithdrawDateCurrency;
import cn.zeppin.product.utility.reapal.data.WithdrawData.WithdrawDateProperties;
import cn.zeppin.product.utility.reapal.data.WithdrawDataArray;

@Controller
@RequestMapping(value = "/web/pay")
public class PayController extends BaseController{
	
	@Autowired
	private IInvestorService investorService;
	
	@Autowired
	private IOrderinfoByThirdpartyService orderinfoByThirdpartyService;
	
	@Autowired
	private IInvestorAccountHistoryService investorAccountHistoryService;
	
	@Autowired
	private IInvestorBankcardService investorBankcardService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IInvestorProductBuyService investorProductBuyService;
	
	@Autowired
	private IInvestorProductBuyAgreementService investorProductBuyAgreementService;
	
	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private ICompanyAccountHistoryService companyAccountHistoryService;
	
	@Autowired
	private IPlatformAccountService platformAccountService;
	
	@Autowired
	private IFundPublishService fundPublishService;
	
	@Autowired
	private IAliQrcodeService aliQrcodeService;
	
	@Autowired
	private IBkPaymentService bkPaymentService;
	
	@Autowired
	private IInvestorCouponService inestorCouponService;
	
	@Autowired
	private ICouponService couponService;
	
	@Autowired
	private IInvestorRedPacketService investorRedPacketService;
	
	/**
	 * ???????????????????????????????????????
	 * @param openid
	 * @param totalFee
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/wechart", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "price", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "body", type = DataType.STRING, message = "????????????")
	@ActionParam(key = "code", type = DataType.STRING, message = "???????????????", required = true)
	@ResponseBody
	protected Result wechart(String uuid, String price, String body, String code) throws IOException, DocumentException {
		
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("???????????????");
		}
		if(!investor.getRealnameAuthFlag()){
			return ResultManager.createFailResult("???????????????");
		}
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency4Web(price)) {
			return ResultManager.createFailResult("????????????????????????!");
		}
		BigDecimal totalFees = BigDecimal.valueOf(Double.parseDouble(price));
		code = Base64Util.getFromBase64(code);
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("mobile", investor.getMobile());
		inputParams.put("status", MobileCodeStatus.NORMAL);
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
		
		String url = Utlity.WX_PAY_URL;
		if(Utlity.checkStringNull(body)){
			body = "????????????-????????????";
		} else {
			body = Base64Util.getFromBase64(body);
		}
		
		String openid = investor.getOpenid();
		Map<String,Object> params = new HashMap<String,Object>();
		String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_WECHAT,Utlity.BILL_PAYTYPE_WECHART,Utlity.BILL_PURPOSE_INCOME);
		if(this.investorAccountHistoryService.getCheckOrderNum(orderNumStr)){
			return ResultManager.createFailResult("????????????????????????????????????");
		}
			
		String nonceStr = UUID.randomUUID().toString().replace("-", "");
		StringBuffer signString = new StringBuffer();
		signString.append("appid=").append(Utlity.WX_APPID);
		signString.append("&body=").append(body);
		signString.append("&limit_pay=").append("no_credit");
		signString.append("&mch_id=").append(Utlity.WX_MCH_ID);
		signString.append("&nonce_str=").append(nonceStr);
		signString.append("&notify_url=").append(Utlity.NOTIFY_URL);
		signString.append("&openid=").append(openid);
		signString.append("&out_trade_no=").append(orderNumStr);
		signString.append("&sign_type=").append("MD5");
		signString.append("&total_fee=").append(price);
		signString.append("&trade_type=").append("JSAPI");
		signString.append("&key=").append(Utlity.WX_KEY);
		
		
		params.put("appid", Utlity.WX_APPID);
		params.put("body", body);
		params.put("limit_pay", "no_credit");
		params.put("mch_id", Utlity.WX_MCH_ID);
		params.put("nonce_str", nonceStr);
//		params.put("sign", MD5.getMD5(signString.toString()).toUpperCase());
		params.put("sign", MD5.MD5Encode(signString.toString(), "utf-8").toUpperCase());
		params.put("out_trade_no", orderNumStr);
		params.put("total_fee", price);
		params.put("spbill_create_ip", Utlity.SERVER_IP);
		params.put("notify_url", Utlity.NOTIFY_URL);
		params.put("trade_type", "JSAPI");
		params.put("sign_type", "MD5");
		params.put("openid", openid);
		String xmls = XMLUtils.getRequestXml(params);
		System.out.println(xmls);
		String xml = HttpUtility.postXml(url, xmls);
		System.out.println(xml);
		Map<String, Object> dataMap = XMLUtils.doXMLParse(xml);
		System.out.println(dataMap);
		if(OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(dataMap.get("return_code").toString())){
			
			OrderinfoByThirdparty obt = new OrderinfoByThirdparty();
			obt.setUuid(UUID.randomUUID().toString());
			obt.setType(OrderinfoByThirdpartyType.WEXIN);
			obt.setInvestor(investor.getUuid());
			obt.setOrderNum(orderNumStr);
			obt.setBody(body);
			obt.setTotalFee(totalFees.divide(BigDecimal.valueOf(100)));
			obt.setPaySource(openid);
			obt.setStatus(dataMap.get("result_code").toString());
			obt.setCreatetime(new Timestamp(System.currentTimeMillis()));
			if(OrderinfoByThirdpartyResultStatus.SUCCESS.equals(dataMap.get("result_code").toString())){
				obt.setPrepayId(dataMap.get("prepay_id").toString());
			}
			obt.setErrCode(dataMap.get("err_code") == null ? "" : dataMap.get("err_code").toString());
			obt.setErrCodeDes(dataMap.get("err_code_des") == null ? "" : dataMap.get("err_code_des").toString());
			obt.setMessage(dataMap.get("return_msg") == null ? "" : dataMap.get("return_msg").toString());
//			this.orderinfoByThirdpartyService.insert(obt);
			
			InvestorAccountHistory iah = new InvestorAccountHistory();
			iah.setUuid(UUID.randomUUID().toString());
			iah.setInvestor(investor.getUuid());
			iah.setIncome(totalFees.divide(BigDecimal.valueOf(100)));
			iah.setPay(BigDecimal.ZERO);
			iah.setAccountBalance(investor.getAccountBalance().add(totalFees.divide(BigDecimal.valueOf(100))));
			iah.setOrderId(obt.getUuid());
			iah.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_WECHART);
			iah.setOrderNum(orderNumStr);
			iah.setType(InvestorAccountHistoryType.INCOME);
			iah.setStatus(InvestorAccountHistoryStatus.TRANSACTING);
			iah.setCreatetime(new Timestamp(System.currentTimeMillis()));
			iah.setCompanyAccount(CompanyAccountUuid.WECHART);
			BigDecimal poundage = totalFees.divide(BigDecimal.valueOf(100)).multiply(Utlity.POUNDAGE_FEE_WECHAT).setScale(2,BigDecimal.ROUND_HALF_UP);
			iah.setPoundage(poundage);
//			iah.setPoundage(BigDecimal.ZERO);
//			this.investorAccountHistoryService.insert(iah);
			try {
				this.investorAccountHistoryService.insertwechart(obt, iah);
				String timestamp = System.currentTimeMillis()+"";
				dataMap.put("timestamp", timestamp);
				//???????????????????????????
				StringBuffer signStringForX = new StringBuffer();
				signStringForX.append("appId=").append(Utlity.WX_APPID);
				signStringForX.append("&nonceStr=").append(dataMap.get("nonce_str").toString());
				signStringForX.append("&package=").append("prepay_id=").append(obt.getPrepayId());
				signStringForX.append("&signType=").append("MD5");
				signStringForX.append("&timeStamp=").append(timestamp);
				signStringForX.append("&key=").append(Utlity.WX_KEY);
				String sign = MD5.MD5Encode(signStringForX.toString(), "utf-8").toUpperCase();
				dataMap.put("sign4Wechart", sign);
				return ResultManager.createDataResult(dataMap,"???????????????");
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult("???????????????");
			}
		} else {
			return ResultManager.createFailResult(dataMap.get("return_msg").toString());
		}
		
	}
	
	/**
	 * ????????????????????????
	 * @param uuid
	 * @param totalFee????????? ??????
	 * @param body
	 * @param type
	 * @param code
	 * @param orderNum
	 * @param token
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@RequestMapping(value = "/rechargeByChanpay", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "price", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "bankcard", type = DataType.STRING, message = "???????????????????????????", required = true)
	@ActionParam(key = "type", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "body", type = DataType.STRING, message = "????????????")
	@ActionParam(key = "code", type = DataType.STRING, message = "???????????????")
	@ActionParam(key = "orderNum", type = DataType.STRING, message = "send ?????????????????????")
	@ActionParam(key = "token", type = DataType.STRING)
	@ResponseBody
	protected Result rechargeByChanpay(String uuid, String price, String bankcard, String body, String type,
			String code,String orderNum, String token, HttpServletRequest request) throws IOException, DocumentException {
		
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("???????????????");
		}
		if(!investor.getRealnameAuthFlag()){
			return ResultManager.createFailResult("???????????????");
		}
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency4Web(price)) {
			return ResultManager.createFailResult("????????????????????????!");
		}
		BigDecimal totalFees = BigDecimal.valueOf(Double.parseDouble(price));
		BigDecimal paydivide = totalFees.divide(BigDecimal.valueOf(100));
		
		token = Base64Util.getFromBase64(token);
		String device = Utlity.getDeviceStr(token);
		
		
		String orderNumStr = Utlity.getOrderNumStr(device,Utlity.BILL_PAYTYPE_WECHART,Utlity.BILL_PURPOSE_INCOME);
		if(this.investorAccountHistoryService.getCheckOrderNum(orderNumStr)){
			return ResultManager.createFailResult("????????????????????????????????????");
		}
		try {
			if(Utlity.checkStringNull(body)){
				body = "????????????--????????????";
			} else {
				body = Base64Util.getFromBase64(body);
			}
			InvestorBankcard ib = this.investorBankcardService.get(bankcard);
			if(ib == null){
				return ResultManager.createFailResult("??????????????????????????????");
			}
			if(!ib.getInvestor().equals(investor.getUuid())){
				return ResultManager.createFailResult("????????????????????????????????????");
			}
			Bank bank = this.bankService.get(ib.getBank());
			if(bank == null){
				return ResultManager.createFailResult("????????????????????????");
			}
			
			if("send".equals(type)){
				
				String userIp = Utlity.getIpAddr(request);
				if(Utlity.checkStringNull(ib.getBindingId())){
					String bankcardNo = ib.getBindingBankCard();
					String realName = ib.getBindingCardCardholder();
					String mobile = ib.getBindingCardPhone();
					String idcard = investor.getIdcard();
					
					Map<String, Object> returnMap = ReapalUtlity.debit(bankcardNo, realName, idcard, mobile, investor.getUuid(), orderNumStr, userIp, totalFees.setScale(0).toString(), ReapalConfig.notify_url_recharge);
					OrderinfoByThirdparty obt = new OrderinfoByThirdparty();
					obt.setUuid(UUID.randomUUID().toString());
					obt.setType(OrderinfoByThirdpartyType.REAPAL);
					obt.setInvestor(investor.getUuid());
					obt.setOrderNum(orderNumStr);
					obt.setBody(body);
					obt.setTotalFee(paydivide);
					obt.setPaySource(MD5.getMD5(investor.getUuid()));
					obt.setStatus(returnMap.get("result_code").toString());
					obt.setCreatetime(new Timestamp(System.currentTimeMillis()));
					obt.setErrCode(returnMap.get("result_code") == null ? "" : returnMap.get("result_code").toString());
					obt.setErrCodeDes(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					obt.setMessage(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					String certificate = returnMap.get("certificate") == null ? "" : returnMap.get("certificate").toString();
					System.out.println("certificate:"+certificate);
					if(OrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(returnMap.get("result_code").toString())){
//						obt.setPrepayId(returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString());
						String bindId = returnMap.get("bind_id") == null ? "" : returnMap.get("bind_id").toString();
						ib.setBindingId(bindId);
						
						InvestorAccountHistory iah = new InvestorAccountHistory();
						iah.setUuid(UUID.randomUUID().toString());
						iah.setInvestor(investor.getUuid());
						iah.setIncome(paydivide);
						iah.setPay(BigDecimal.ZERO);
						iah.setAccountBalance(investor.getAccountBalance().add(paydivide));
						iah.setOrderId(obt.getUuid());
						iah.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_REAPAL);
						iah.setOrderNum(obt.getOrderNum());
						iah.setType(InvestorAccountHistoryType.INCOME);
						iah.setStatus(InvestorAccountHistoryStatus.TRANSACTING);
						iah.setCreatetime(new Timestamp(System.currentTimeMillis()));
						iah.setCompanyAccount(CompanyAccountUuid.REAPAL);//?????????????????????????????????????????????
						iah.setBankcard(ib.getUuid());
						
						//???????????????????????????
//						BigDecimal poundage = paydivide.multiply(ReapalUtil.POUNDAGE_FEE).setScale(2,BigDecimal.ROUND_HALF_UP);
//						iah.setPoundage(poundage);
						//???????????????????????????
//						BigDecimal poundage = paydivide.multiply(ReapalUtil.POUNDAGE_FEE).setScale(2,BigDecimal.ROUND_HALF_UP);
//						if(poundage.compareTo(BigDecimal.valueOf(Double.parseDouble("2.0"))) < 0){
//							iah.setPoundage(BigDecimal.valueOf(Double.parseDouble("2.0")));
//						} else {
//							iah.setPoundage(poundage);
//						}
						BigDecimal poundage = Utlity.getPoundage4Pay(paydivide, bank.getShortName());
						iah.setPoundage(poundage);
						
						this.investorAccountHistoryService.insertwechart(obt, iah, ib);
						return ResultManager.createDataResult(obt.getUuid(), "???????????????");
					} else {
						String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
						obt.setPrepayId(prepayId);
						this.orderinfoByThirdpartyService.insert(obt);
						return ResultManager.createFailResult(obt.getErrCodeDes());
					}
				} else {
					String bindingId = ib.getBindingId();
					Map<String, Object> returnMap = ReapalUtlity.bindCard(investor.getUuid(), bindingId, orderNumStr, userIp, totalFees.setScale(0).toString(), ReapalConfig.notify_url_recharge);
					OrderinfoByThirdparty obt = new OrderinfoByThirdparty();
					obt.setUuid(UUID.randomUUID().toString());
					obt.setType(OrderinfoByThirdpartyType.REAPAL);
					obt.setInvestor(investor.getUuid());
					obt.setOrderNum(orderNumStr);
					obt.setBody(body);
					obt.setTotalFee(paydivide);
					obt.setPaySource(MD5.getMD5(investor.getUuid()));
					obt.setStatus(returnMap.get("result_code").toString());
					obt.setCreatetime(new Timestamp(System.currentTimeMillis()));
					obt.setErrCode(returnMap.get("result_code") == null ? "" : returnMap.get("result_code").toString());
					obt.setErrCodeDes(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					obt.setMessage(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
//					String certificate = returnMap.get("certificate") == null ? "" : returnMap.get("certificate").toString();
//					System.out.println("certificate:"+certificate);
					if(OrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(returnMap.get("result_code").toString())){
						
						InvestorAccountHistory iah = new InvestorAccountHistory();
						iah.setUuid(UUID.randomUUID().toString());
						iah.setInvestor(investor.getUuid());
						iah.setIncome(paydivide);
						iah.setPay(BigDecimal.ZERO);
						iah.setAccountBalance(investor.getAccountBalance().add(paydivide));
						iah.setOrderId(obt.getUuid());
						iah.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_REAPAL);
						iah.setOrderNum(obt.getOrderNum());
						iah.setType(InvestorAccountHistoryType.INCOME);
						iah.setStatus(InvestorAccountHistoryStatus.TRANSACTING);
						iah.setCreatetime(new Timestamp(System.currentTimeMillis()));
						iah.setCompanyAccount(CompanyAccountUuid.REAPAL);//?????????????????????????????????????????????
						iah.setBankcard(ib.getUuid());
						
						//???????????????????????????
//						BigDecimal poundage = paydivide.multiply(ReapalUtil.POUNDAGE_FEE).setScale(2,BigDecimal.ROUND_HALF_UP);
//						iah.setPoundage(poundage);
						//???????????????????????????
//						BigDecimal poundage = paydivide.multiply(ReapalUtil.POUNDAGE_FEE).setScale(2,BigDecimal.ROUND_HALF_UP);
//						if(poundage.compareTo(BigDecimal.valueOf(Double.parseDouble("2.0"))) < 0){
//							iah.setPoundage(BigDecimal.valueOf(Double.parseDouble("2.0")));
//						} else {
//							iah.setPoundage(poundage);
//						}
						BigDecimal poundage = Utlity.getPoundage4Pay(paydivide, bank.getShortName());
						iah.setPoundage(poundage);
						
						this.investorAccountHistoryService.insertwechart(obt, iah);
						return ResultManager.createDataResult(obt.getUuid(), "???????????????");
					} else {
						String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
						obt.setPrepayId(prepayId);
						this.orderinfoByThirdpartyService.insert(obt);
						return ResultManager.createFailResult(obt.getErrCodeDes());
					}
				}
				
			} else if ("resend".equals(type)) {
				if(Utlity.checkStringNull(orderNum)){
					return ResultManager.createFailResult("??????????????????????????????");
				}
				OrderinfoByThirdparty obt = this.orderinfoByThirdpartyService.get(orderNum);
				if(obt == null){
					return ResultManager.createFailResult("?????????????????????????????????");
				}
				Map<String, Object> returnMap = ReapalUtlity.reSms(orderNum);
				OrderinfoByThirdparty obtN = new OrderinfoByThirdparty();
				obtN.setUuid(UUID.randomUUID().toString());
				obtN.setType(OrderinfoByThirdpartyType.REAPAL);
				obtN.setInvestor(investor.getUuid());
				obtN.setOrderNum(orderNumStr);
				obtN.setBody(body);
				obtN.setTotalFee(BigDecimal.ZERO);
				obtN.setPaySource(MD5.getMD5(investor.getUuid()));
				obtN.setStatus(returnMap.get("result_code").toString());
				obtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
				obtN.setErrCode(returnMap.get("result_code") == null ? "" : returnMap.get("result_code").toString());
				obtN.setErrCodeDes(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
				obtN.setMessage(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
				if(OrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(returnMap.get("result_code").toString())){
					obtN.setPrepayId(returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString());
				} else {
					String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
					obtN.setPrepayId(prepayId);
//					this.orderinfoByThirdpartyService.insert(obtN);
					Map<String, String> inputParams = new HashMap<String, String>();
					inputParams.put("order", obt.getUuid());
					inputParams.put("investor", obt.getInvestor());
					inputParams.put("orderType", InvestorAccountHistoryOrderType.PAY_TYPE_REAPAL);
					inputParams.put("type", InvestorAccountHistoryType.INCOME);
					List<Entity> listHistory = this.investorAccountHistoryService.getListForPage(inputParams, -1, -1, null, InvestorAccountHistory.class);
					InvestorAccountHistory iah = null;
					if(listHistory != null && listHistory.size() > 0){
						iah = (InvestorAccountHistory) listHistory.get(0);
					}
					obt.setStatus(returnMap.get("result_code").toString());
					obt.setErrCode(returnMap.get("result_code") == null ? "" : returnMap.get("result_code").toString());
					obt.setErrCodeDes(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					obt.setMessage(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					iah.setStatus(InvestorAccountHistoryStatus.FAIL);
					this.investorAccountHistoryService.updatewechart(obt, obtN, iah);
					return ResultManager.createFailResult("????????????");
				}
				this.orderinfoByThirdpartyService.insert(obtN);
				return ResultManager.createDataResult(obt.getUuid(), "???????????????");
			} else if ("check".equals(type)) {
				if(Utlity.checkStringNull(orderNum)){
					return ResultManager.createFailResult("??????????????????????????????");
				}
				OrderinfoByThirdparty obt = this.orderinfoByThirdpartyService.get(orderNum);
				if(obt == null){
					return ResultManager.createFailResult("?????????????????????????????????");
				}
				code = Base64Util.getFromBase64(code);
				Map<String, Object> returnMap = ReapalUtlity.confirmPay(obt.getOrderNum(), code);
				OrderinfoByThirdparty obtN = new OrderinfoByThirdparty();
				obtN.setUuid(UUID.randomUUID().toString());
				obtN.setType(OrderinfoByThirdpartyType.REAPAL);
				obtN.setInvestor(investor.getUuid());
				obtN.setOrderNum(orderNumStr);
				obtN.setBody("????????????--????????????");
				obtN.setTotalFee(BigDecimal.ZERO);
				obtN.setPaySource(MD5.getMD5(investor.getUuid()));
				obtN.setStatus(returnMap.get("result_code").toString());
				obtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
				obtN.setErrCode(returnMap.get("result_code") == null ? "" : returnMap.get("result_code").toString());
				obtN.setErrCodeDes(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
				obtN.setMessage(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
				if(OrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(returnMap.get("result_code").toString())){
					obtN.setPrepayId(returnMap.get("trade_no") == null ? "" : returnMap.get("trade_no").toString());
					
					this.orderinfoByThirdpartyService.insert(obtN);
					return ResultManager.createSuccessResult("???????????????");
				} else {
					String prepayId = returnMap.get("trade_no") == null ? "" : returnMap.get("trade_no").toString();
					obtN.setPrepayId(prepayId);
					
//					this.orderinfoByThirdpartyService.insert(obtN);
					Map<String, String> inputParams = new HashMap<String, String>();
					inputParams.put("order", obt.getUuid());
					inputParams.put("investor", obt.getInvestor());
					inputParams.put("orderType", InvestorAccountHistoryOrderType.PAY_TYPE_REAPAL);
					inputParams.put("type", InvestorAccountHistoryType.INCOME);
					List<Entity> listHistory = this.investorAccountHistoryService.getListForPage(inputParams, -1, -1, null, InvestorAccountHistory.class);
					InvestorAccountHistory iah = null;
					if(listHistory != null && listHistory.size() > 0){
						iah = (InvestorAccountHistory) listHistory.get(0);
					}
					obt.setStatus(returnMap.get("result_code").toString());
					obt.setErrCode(returnMap.get("result_code") == null ? "" : returnMap.get("result_code").toString());
					obt.setErrCodeDes(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					obt.setMessage(returnMap.get("result_msg") == null ? "" : returnMap.get("result_msg").toString());
					iah.setStatus(InvestorAccountHistoryStatus.FAIL);
					this.investorAccountHistoryService.updatewechart(obt, obtN, iah);
					return ResultManager.createFailResult("???????????????"+obtN.getErrCodeDes()+"???");
				}
			} else {
				return ResultManager.createFailResult("???????????????");
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("?????????????????????????????????");
		}
		
//		return ResultManager.createFailResult("???????????????");
//		if("send".equals(type)){
//			if(Utlity.checkStringNull(body)){
//				body = "????????????--????????????";
//			} else {
//				body = Base64Util.getFromBase64(body);
//			}
//			InvestorBankcard ib = this.investorBankcardService.get(bankcard);
//			if(ib == null){
//				return ResultManager.createFailResult("??????????????????????????????");
//			}
//			if(!ib.getInvestor().equals(investor.getUuid())){
//				return ResultManager.createFailResult("????????????????????????????????????");
//			}
//			String bankcardNum = ib.getBindingBankCard();
//			String cardbegin = bankcardNum.substring(0,6);
//			String cardend = bankcardNum.substring(bankcardNum.length()-4);
//			String priceStr = paydivide.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
//			Map<String, Object> returnMap = ChanPayUtil.nmg_biz_api_quick_payment(ChanPayUtil.noticeRechargeURL, "??????", investor.getUuid(), cardbegin, cardend, priceStr, orderNumStr);
//			OrderinfoByThirdparty obt = new OrderinfoByThirdparty();
//			obt.setUuid(UUID.randomUUID().toString());
//			obt.setType(OrderinfoByThirdpartyType.CHANPAY);
//			obt.setInvestor(investor.getUuid());
//			obt.setOrderNum(orderNumStr);
//			obt.setBody(body);
//			obt.setTotalFee(paydivide);
//			obt.setPaySource(MD5.getMD5(investor.getUuid()));
//			obt.setStatus(returnMap.get("Status").toString());
//			obt.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			obt.setErrCode(returnMap.get("RetCode") == null ? "" : returnMap.get("RetCode").toString());
//			obt.setErrCodeDes(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
//			obt.setMessage(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
//			if(ChanPayUtil.RETSTATUS_SUCCESS.equals(returnMap.get("Status").toString()) || ChanPayUtil.RETSTATUS_RROSESS.equals(returnMap.get("Status").toString())){
//				obt.setPrepayId(returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString());
//				InvestorAccountHistory iah = new InvestorAccountHistory();
//				iah.setUuid(UUID.randomUUID().toString());
//				iah.setInvestor(investor.getUuid());
//				iah.setIncome(paydivide);
//				iah.setPay(BigDecimal.ZERO);
//				iah.setAccountBalance(investor.getAccountBalance().add(paydivide));
//				iah.setOrderId(obt.getUuid());
//				iah.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_CHANPAY);
//				iah.setOrderNum(obt.getOrderNum());
//				iah.setType(InvestorAccountHistoryType.INCOME);
//				iah.setStatus(InvestorAccountHistoryStatus.TRANSACTING);
//				iah.setCreatetime(new Timestamp(System.currentTimeMillis()));
//				iah.setCompanyAccount(CompanyAccountUuid.CHANPAY);//?????????????????????????????????????????????
//				
//				//20171106-????????????
//				iah.setBankcard(ib.getUuid());
////				iah.set
//				//???????????????????????????
//				BigDecimal poundage = paydivide.multiply(ChanPayUtil.POUNDAGE_FEE).setScale(2,BigDecimal.ROUND_HALF_UP);
//				iah.setPoundage(poundage);
//				this.investorAccountHistoryService.insertwechart(obt, iah);
//				return ResultManager.createDataResult(obt.getUuid(), "???????????????");
//			} else {
//				String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
//				obt.setPrepayId(prepayId);
//				this.orderinfoByThirdpartyService.insert(obt);
//				return ResultManager.createFailResult(obt.getErrCodeDes());
//			}
//			
//		} else if ("resend".equals(type)) {
//			if(Utlity.checkStringNull(body)){
//				body = "????????????--????????????(????????????)";
//			} else {
//				body = Base64Util.getFromBase64(body);
//			}
//			if(Utlity.checkStringNull(orderNum)){
//				return ResultManager.createFailResult("??????????????????????????????");
//			}
//			OrderinfoByThirdparty obt = this.orderinfoByThirdpartyService.get(orderNum);
//			if(obt == null){
//				return ResultManager.createFailResult("?????????????????????????????????");
//			}
//			Map<String, Object> returnMap = ChanPayUtil.nmg_sms_resend("??????", obt.getOrderNum(), ChanPayUtil.TYPE_PAY_ORDER, orderNumStr);
//			OrderinfoByThirdparty obtN = new OrderinfoByThirdparty();
//			obtN.setUuid(UUID.randomUUID().toString());
//			obtN.setType(OrderinfoByThirdpartyType.CHANPAY);
//			obtN.setInvestor(investor.getUuid());
//			obtN.setOrderNum(orderNumStr);
//			obtN.setBody(body);
//			obtN.setTotalFee(BigDecimal.ZERO);
//			obtN.setPaySource(MD5.getMD5(investor.getUuid()));
//			obtN.setStatus(returnMap.get("Status").toString());
//			obtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			obtN.setErrCode(returnMap.get("RetCode") == null ? "" : returnMap.get("RetCode").toString());
//			obtN.setErrCodeDes(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
//			obtN.setMessage(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
//			if(ChanPayUtil.RETSTATUS_SUCCESS.equals(returnMap.get("Status").toString()) || ChanPayUtil.RETSTATUS_RROSESS.equals(returnMap.get("Status").toString())){
//				obtN.setPrepayId(returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString());
//			} else {
//				String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
//				obtN.setPrepayId(prepayId);
//			}
//			this.orderinfoByThirdpartyService.insert(obtN);
//			return ResultManager.createDataResult(obt.getUuid(), "???????????????");
//		} else if ("check".equals(type)) {
//			if(Utlity.checkStringNull(body)){
//				body = "????????????--????????????";
//			} else {
//				body = Base64Util.getFromBase64(body);
//			}
//			if(Utlity.checkStringNull(orderNum)){
//				return ResultManager.createFailResult("??????????????????????????????");
//			}
//			OrderinfoByThirdparty obt = this.orderinfoByThirdpartyService.get(orderNum);
//			if(obt == null){
//				return ResultManager.createFailResult("?????????????????????????????????");
//			}
//			code = Base64Util.getFromBase64(code);
//			Map<String, Object> returnMap = ChanPayUtil.nmg_api_quick_payment_smsconfirm("??????", obt.getOrderNum(), code, orderNumStr);
//			OrderinfoByThirdparty obtN = new OrderinfoByThirdparty();
//			obtN.setUuid(UUID.randomUUID().toString());
//			obtN.setType(OrderinfoByThirdpartyType.CHANPAY);
//			obtN.setInvestor(investor.getUuid());
//			obtN.setOrderNum(orderNumStr);
//			obtN.setBody("????????????--????????????");
//			obtN.setTotalFee(BigDecimal.ZERO);
//			obtN.setPaySource(MD5.getMD5(investor.getUuid()));
//			obtN.setStatus(returnMap.get("Status").toString());
//			obtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			obtN.setErrCode(returnMap.get("RetCode") == null ? "" : returnMap.get("RetCode").toString());
//			obtN.setErrCodeDes(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
//			obtN.setMessage(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
//			if(ChanPayUtil.RETSTATUS_SUCCESS.equals(returnMap.get("Status").toString()) || ChanPayUtil.RETSTATUS_RROSESS.equals(returnMap.get("Status").toString())){
//				obtN.setPrepayId(returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString());
//				
//				this.orderinfoByThirdpartyService.insert(obtN);
//				return ResultManager.createSuccessResult("???????????????");
//			} else {
//				String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
//				obtN.setPrepayId(prepayId);
//				this.orderinfoByThirdpartyService.insert(obtN);
//				return ResultManager.createFailResult("???????????????"+obtN.getErrCodeDes()+"???");
//			}
//			
//		} else {
//			return ResultManager.createFailResult("???????????????");
//		}
			
		
	}
	
	/**
	 * ????????????
	 * @param uuid
	 * @param bankcard
	 * @param code
	 * @param price
	 * @return
	 */
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "price", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "bankcard", type = DataType.STRING, message = "?????????????????????", required = true)
	@ActionParam(key = "code", type = DataType.STRING, message = "???????????????", required = true)
	@ActionParam(key = "token", type = DataType.STRING)
	@ResponseBody
	protected Result withdraw(String uuid, String bankcard, String code, String price, String token){
		
		synchronized(this){
			Investor investor = this.investorService.get(uuid);
			if(investor == null){
				return ResultManager.createFailResult("?????????????????????!");
			}
			if(!investor.getRealnameAuthFlag()){
				return ResultManager.createFailResult("???????????????");
			}
			InvestorBankcard ib = this.investorBankcardService.get(bankcard);
			if(ib == null){
				return ResultManager.createFailResult("?????????????????????!");
			}
			if(!uuid.equals(ib.getInvestor())){
				return ResultManager.createFailResult("???????????????????????????!");
			}
			if(!InvestorBankcardStatus.NORMAL.equals(ib.getStatus())){
				return ResultManager.createFailResult("???????????????????????????!");
			}
			
			Bank bank = this.bankService.get(ib.getBank());
			if(bank == null){
				return ResultManager.createFailResult("??????????????????!");
			}
			code = Base64Util.getFromBase64(code);
			
			price = Base64Util.getFromBase64(price);
			if (!Utlity.isPositiveCurrency4Web(price)) {
				return ResultManager.createFailResult("????????????????????????!");
			}
			BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));
			pay = pay.divide(BigDecimal.valueOf(100));
			if(pay.compareTo(Utlity.REAPAL_MAX_WITHDRAW) == 1){
				return ResultManager.createFailResult("????????????????????????500w!");
			}
			
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("mobile", investor.getMobile());
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
			token = Base64Util.getFromBase64(token);
			String device = Utlity.getDeviceStr(token);
			String orderNumStr = Utlity.getOrderNumStr(device,Utlity.BILL_PAYTYPE_REAPAL,Utlity.BILL_PURPOSE_WITHDRAW);
			if(this.investorAccountHistoryService.getCheckOrderNum(orderNumStr)){
				return ResultManager.createFailResult("????????????????????????????????????");
			}
			
			//??????????????????????????????
			BigDecimal total = investor.getAccountBalance();
			if(total.compareTo(pay) == -1){
				return ResultManager.createFailResult("??????????????????!");
			}
			
			OrderinfoByThirdparty obtN = new OrderinfoByThirdparty();
			obtN.setUuid(UUID.randomUUID().toString());
			obtN.setType(OrderinfoByThirdpartyType.REAPAL);
			obtN.setInvestor(investor.getUuid());
			obtN.setOrderNum(orderNumStr);
//			obtN.setBody("????????????--????????????");
			obtN.setBody("????????????--????????????");
			obtN.setTotalFee(pay);
			obtN.setPaySource(MD5.getMD5(investor.getUuid()));

			
			//??????????????????
			InvestorAccountHistory iah = new InvestorAccountHistory();
			iah.setUuid(UUID.randomUUID().toString());
			iah.setInvestor(investor.getUuid());//?????????????????????
			iah.setPay(pay);
			iah.setIncome(BigDecimal.ZERO);
			iah.setOrderId(obtN.getUuid());
			iah.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_REAPAL);
			iah.setOrderNum(orderNumStr);
			iah.setType(InvestorAccountHistoryType.WITHDRAW);
			iah.setStatus(InvestorAccountHistoryStatus.TRANSACTING);
			iah.setCreatetime(new Timestamp(System.currentTimeMillis()));
			iah.setAccountBalance(total.subtract(pay));
			iah.setCompanyAccount(CompanyAccountUuid.REAPAL);
			iah.setPoundage(ChanPayUtil.POUNDAGE);
			
			iah.setBankcard(ib.getUuid());
//			iah.setProcessingStatus(InvestorAccountHistoryProcessStatus.UNPROCESS);
			
			
//			//????????????????????????
//			CompanyAccount ca = this.companyAccountService.get(iah.getCompanyAccount());
//			if(ca == null){
//				return ResultManager.createFailResult("??????????????????!");
//			}
//			BigDecimal accountTotal = ca.getAccountBalance();
//			if(accountTotal.compareTo(pay) == -1){
//				return ResultManager.createFailResult("????????????????????????!");
//			}
			
			//????????????????????????????????????????????????????????????????????????????????????????????????
//			Map<String, Object> resultBalance = ChanPay4UserUtil.checkBalance();
			

			try {
				Map<String, Object> resultBalance = ReapalUtlity.get_balance_Query();
				String balance = resultBalance.get("balance") == null ? "0" : resultBalance.get("balance").toString();
				BigDecimal accountTotal = BigDecimal.valueOf(Double.valueOf(balance));
				if(pay.add(iah.getPoundage()).compareTo(accountTotal) == 1){
					obtN.setStatus(OrderinfoByThirdpartyReturnStatus.FAIL);
					obtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
					obtN.setErrCode("");
					obtN.setErrCodeDes("???????????????????????????");
					obtN.setMessage("???????????????????????????");
				} else {
					//???????????????????????? ???????????????
//					Map<String, Object> result = ChanPay4UserUtil.pay2card("??????", orderNumStr, ib.getBindingBankCard(), investor.getRealname(), bank.getCode(), bank.getName(), "??????????????????????????????????????????", "?????????", "?????????", pay.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
					WithdrawData wd = new WithdrawData(1, ib.getBindingBankCard(), ib.getBindingCardCardholder(), bank.getName(), "", "", WithdrawDateProperties.PRIVATE, pay.setScale(2, BigDecimal.ROUND_HALF_UP), WithdrawDateCurrency.CNY, "", "", ib.getBindingCardPhone(), "?????????", investor.getIdcard(), "", orderNumStr, "??????");
					
					List<WithdrawData> content = new ArrayList<WithdrawData>();
					content.add(wd);
					WithdrawDataArray wda = new WithdrawDataArray(content);
					wda.setBatch_no(orderNumStr);
					wda.setTrans_time(Utlity.timeSpanToString(iah.getCreatetime()));
					Map<String, Object> result = ReapalUtlity.withdrawBatchSubmit(wda);
//					if(!"T".equals(result.get("is_success").toString())){
//						return ResultManager.createFailResult("????????????,??????????????????");
//					}
					obtN.setStatus(result.get("result_code").toString());
					obtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
					obtN.setErrCode(result.get("result_code") == null ? "" : result.get("result_code").toString());
					obtN.setErrCodeDes(result.get("result_msg") == null ? "" : result.get("result_msg").toString());
					obtN.setMessage(result.get("result_msg") == null ? "" : result.get("result_msg").toString());
				}
				
//				if(OrderinfoByThirdpartyReturnStatus.WITHDRAW_FAIL.equals(obtN.getStatus())){
				if(!OrderinfoByThirdpartyReturnStatus.REAPAL_CHECKED.equals(obtN.getStatus()) && !OrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(obtN.getStatus())){
					iah.setStatus(InvestorAccountHistoryStatus.TRANSACTING);
					iah.setProcessingStatus(InvestorAccountHistoryProcessStatus.UNPROCESS);//?????????????????? ?????????????????????????????????????????????????????????
					//??????????????? ??????????????????
					total = total.subtract(pay);
					investor.setAccountBalance(total);
					this.investorAccountHistoryService.insertWithdraw(investor, obtN, iah);
//					return ResultManager.createFailResult("???????????????");
				} else {
					//??????????????? ??????????????????
					total = total.subtract(pay);
					investor.setAccountBalance(total);
					this.investorAccountHistoryService.insertWithdraw(investor, obtN, iah);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult("?????????????????????????????????");
			} finally {
				mc.setStatus(MobileCodeStatus.DISABLE);
				this.mobileCodeService.update(mc);
			}
		}
		return ResultManager.createSuccessResult("???????????????");
	}
	
	/**
	 * @return
	 */
	@RequestMapping(value = "/sendCodeById", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "price", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "product", type = DataType.STRING, message = "??????????????????", required = true)
	@ResponseBody
	protected Result sendCodeById(String uuid, String price, String product){
		
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("???????????????");
		}
		if(!investor.getRealnameAuthFlag()){
			return ResultManager.createFailResult("???????????????");
		}
		

		String phone = investor.getMobile();
		
		if (!Utlity.isMobileNO(phone)) {
			return ResultManager.createFailResult("??????????????????");
		}
		
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency4Web(price)) {
			return ResultManager.createFailResult("????????????????????????!");
		}
		BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));
		BigDecimal paydivide = pay.divide(BigDecimal.valueOf(100));
		//????????????????????????????????????
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
		
		BigDecimal totalamount = bfpp.getCollectAmount();//??????????????????
		BigDecimal realCollect = bfpp.getRealCollect();//??????????????????
		BigDecimal realAmount = totalamount.subtract(realCollect);
		if(realAmount.compareTo(paydivide) == -1){//?????????????????????????????????
			return ResultManager.createFailResult("??????????????????");
		}
		
		BigDecimal total = investor.getAccountBalance();
		if(total.compareTo(paydivide) == -1){
			return ResultManager.createFailResult("?????????????????????");
		}
		
		String codestr = Utlity.getCaptcha();
		String content = "????????????"+codestr+"???????????????"+bank.getName()+bfpp.getName()+"???????????????"+Utlity.numFormat4UnitDetailLess(paydivide)+"?????????????????????5??????????????????";
		
		
		try {
			String result = SendSms.sendSms(content, phone);
			if ("0".equals(result.split("_")[0])) {
				
//				Map<String, String> inputParams = new HashMap<String, String>();
//				inputParams.put("mobile", phone);
//				inputParams.put("status", MobileCodeStatus.NORMAL);
//				List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(inputParams, -1, -1, null, MobileCode.class);
//
//				// ???????????????????????????????????????????????????
//				if (lstMobileCode != null && lstMobileCode.size() > 0) {
//					for(Entity entity: lstMobileCode){
//						MobileCode code = (MobileCode)entity;
//						code.setStatus(MobileCodeStatus.DISABLE);
//						this.mobileCodeService.update(code);
//					}
//				}
				
				MobileCode code = new MobileCode();
				code.setUuid(UUID.randomUUID().toString());
				code.setCode(codestr);
				code.setContent(content);
				code.setCreatetime(new Timestamp(System.currentTimeMillis()));
				code.setCreatorType(MobileCodeCreatorType.INVESTOR);
				code.setMobile(phone);
				code.setStatus(MobileCodeStatus.NORMAL);
				code.setType(MobileCodeTypes.FUNDCODE);
//				this.mobileCodeService.insert(code);
				this.mobileCodeService.insertMobileCode(code);
				return ResultManager.createSuccessResult("???????????????????????????");
			}
			
			return ResultManager.createFailResult("??????????????????????????????");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
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
	 * @param uuid
	 * @param price
	 * @param product
	 * @param type ???????????? balance-?????? wechart-?????? alipay-????????? bankcard-?????????
	 * @return
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/productPay", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "price", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "product", type = DataType.STRING, message = "??????????????????", required = true)
	@ActionParam(key = "type", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "coupon", type = DataType.STRING, message = "?????????")
	@ActionParam(key = "code", type = DataType.STRING, message = "???????????????")
	@ActionParam(key = "token", type = DataType.STRING)
	@ResponseBody
	protected Result productPay(String uuid, String price, String product, String type, String coupon, String code, String token) throws IOException, DocumentException{
		
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("???????????????");
		}
		if(!investor.getRealnameAuthFlag()){
			return ResultManager.createFailResult("???????????????");
		}
		
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency4Web(price)) {
			return ResultManager.createFailResult("????????????????????????!");
		}
		BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));
		BigDecimal paydivide = pay.divide(BigDecimal.valueOf(100));
		//????????????????????????????????????
//		System.out.println(product);
		
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
				
				token = Base64Util.getFromBase64(token);
				String device = Utlity.getDeviceStr(token);
				//???????????? ????????????????????????
				//??????????????????????????????-(????????????+????????????-????????????-????????????)
//				BigDecimal totalamount = bfpp.getTotalAmount();//???????????????
				BigDecimal totalamount = bfpp.getCollectAmount();//??????????????????
				BigDecimal realCollect = bfpp.getRealCollect();//??????????????????
				BigDecimal realAmount = totalamount.subtract(realCollect);
				if(realAmount.compareTo(paydivide) == -1){//?????????????????????????????????
					return ResultManager.createFailResult("??????????????????");
				}
				//service???investor,bfpp,pay,?????????????????? ??????????????????
				if(Utlity.PAY_TYPE_BALANCE.equals(type)){
					String orderNumStr = Utlity.getOrderNumStr(device,Utlity.BILL_PAYTYPE_BALANCE,Utlity.BILL_PURPOSE_INCOME);
					if(this.investorAccountHistoryService.getCheckOrderNum(orderNumStr)){
						return ResultManager.createFailResult("????????????????????????????????????");
					}
					//??????????????????????????????
					if(code == null || "".equals(code)){
						return ResultManager.createFailResult("?????????????????????");
					}
					code = Base64Util.getFromBase64(code);
					Map<String, String> inputParams = new HashMap<String, String>();
					inputParams.put("mobile", investor.getMobile());
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
					BigDecimal total = investor.getAccountBalance();
					if(total.compareTo(paydivide) == -1){
						return ResultManager.createFailResult("?????????????????????");
					}
					
					//??????????????? ???????????????
					total = total.subtract(paydivide);
					BigDecimal totalInvest = investor.getTotalInvest();
					totalInvest = totalInvest.add(paydivide);
					investor.setAccountBalance(total);
					investor.setTotalInvest(totalInvest);
//					this.investorService.update(investor);
					PlatformAccount pai = this.platformAccountService.get(PlatformAccountUuid.INVESTOR);
					BigDecimal totalAmounti = pai.getTotalAmount();
					totalAmounti = totalAmounti.subtract(paydivide);
					pai.setTotalAmount(totalAmounti);
					
					//????????????????????????
					BigDecimal totalnvestment = pai.getInvestment();
					totalnvestment = totalnvestment.add(paydivide);
					pai.setInvestment(totalnvestment);
					
					Timestamp time = new Timestamp(System.currentTimeMillis());
					
					InvestorAccountHistory iah = new InvestorAccountHistory();
					iah.setUuid(UUID.randomUUID().toString());
					iah.setInvestor(investor.getUuid());
					
					//????????????
					iah.setIncome(BigDecimal.ZERO);
					iah.setPay(paydivide);
					
					iah.setAccountBalance(investor.getAccountBalance());
					iah.setOrderId(investor.getUuid());
					iah.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_BALANCE);
					iah.setOrderNum(orderNumStr);
					iah.setType(InvestorAccountHistoryType.BUY);
					iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
					iah.setCreatetime(time);
					iah.setCompanyAccount(CompanyAccountUuid.WECHART);
					iah.setProduct(product);
					iah.setProductType(InvestorProductBuyProductType.BANK_PRODUCT);
					iah.setPoundage(BigDecimal.ZERO);
//					this.investorAccountHistoryService.insert(iah);
					
					//????????????????????????????????????????????????????????????????????? ????????????update ???????????????????????? ???????????? ?????????????????????????????????
					InvestorProductBuy ipb = new InvestorProductBuy();
					inputParams.clear();
					inputParams.put("investor", investor.getUuid());
					inputParams.put("product", product);
					List<Entity> listBuy = this.investorProductBuyService.getListForPage(inputParams, -1, -1, null, InvestorProductBuy.class);
					Boolean isUpdate = true;
					if(listBuy != null && !listBuy.isEmpty()){
						ipb = (InvestorProductBuy) listBuy.get(0);
						BigDecimal totalAmount = ipb.getTotalAmount();
						ipb.setTotalAmount(totalAmount.add(paydivide));
						ipb.setAccountBalance(ipb.getAccountBalance().add(paydivide));
						ipb.setCreatetime(new Timestamp(System.currentTimeMillis()));
					} else {
						isUpdate = false;
						ipb.setUuid(UUID.randomUUID().toString());
						ipb.setCreatetime(new Timestamp(System.currentTimeMillis()));
						ipb.setInvestor(investor.getUuid());
						ipb.setProduct(product);
						ipb.setStage(InvestorProductBuyStage.CONFIRMING);
						ipb.setTotalAmount(paydivide);
						ipb.setAccountBalance(paydivide);
						ipb.setTotalRedeem(BigDecimal.ZERO);
						ipb.setTotalReturn(BigDecimal.ZERO);
					}
					//?????????????????????
					if((bfpp.getMaxInvestAmount().compareTo(BigDecimal.ZERO)) > 0 && (ipb.getTotalAmount().compareTo(bfpp.getMaxInvestAmount())) == 1){
						return ResultManager.createFailResult("????????????????????????????????????");
					}
					ipb.setType(InvestorProductBuyProductType.BANK_PRODUCT);
					//????????????????????????
					bfpp.setAccountBalance(bfpp.getAccountBalance().add(iah.getPay()));
					//????????????????????????????????????
					bfpp.setRealCollect(bfpp.getRealCollect().add(iah.getPay()));
//					InvestorProductBuyRecords ipbr = new InvestorProductBuyRecords();
//					ipbr.setUuid(UUID.randomUUID().toString());
//					ipbr.setBill(iah.getUuid());
//					ipbr.setInvestor(investor.getUuid());
//					ipbr.setPaytime(time);
//					ipbr.setPrice(paydivide);
//					ipbr.setProduct(product);
//					ipbr.setStatus(InvestorProductBuyRecordsStatus.SUCCESS);
//					ipbr.setStage(InvestorProductBuyRecordsStage.CONFIRMING);
//					this.investorProductBuyRecordsService.insert(ipbr);
					
					//??????????????????PDF??????????????????
					//????????? ????????????????????????????????????????????????
					inputParams.clear();
					inputParams.put("investor", investor.getUuid());
					inputParams.put("records", product);
					InvestorProductBuyAgreement ipba = new InvestorProductBuyAgreement();
					List<Entity> listAgreement = this.investorProductBuyAgreementService.getListForPage(inputParams, -1, -1, null, InvestorProductBuyAgreement.class);
					Boolean isUpdate2 = true;
					if(listAgreement != null && !listAgreement.isEmpty()){
						ipba = (InvestorProductBuyAgreement) listAgreement.get(0);
					} else {
						isUpdate2 = false;
						ipba.setUuid(UUID.randomUUID().toString());
					}
					ipba.setCreatetime(time);
					ipba.setInvestor(investor.getUuid());
					ipba.setType(InvestorProductBuyAgreementType.BANKPRODUCT);
					ipba.setRecords(ipb.getProduct());
					ipba.setScode(orderNumStr);
					ipba.setName("??????????????????????????????????????????");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("scode", orderNumStr);
			    	params.put("realname", investor.getRealname());
			    	params.put("phone", Utlity.getStarMobile(investor.getMobile()));
			    	params.put("idcard", Utlity.getStarIdcard(investor.getIdcard()));
			    	params.put("productName", bank.getShortName()+bfpp.getName());
			    	String priceRMB = RMBUtlity.arabNumToChineseRMB(Double.parseDouble(ipb.getTotalAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
			    	String priceStr = "????????????"+Utlity.numFormat4UnitDetailLess(ipb.getTotalAmount())+"??? (?????????"+priceRMB+"???)";
			    	params.put("price", priceStr);
			    	params.put("uuid", ipba.getUuid());
			    	params.put("fileName", "??????????????????????????????????????????"+orderNumStr);
					Map<String, Object> resultPDF = PDFUtlity.ToPdf(params);
					if((Boolean) resultPDF.get("result")){
						ipba.setStatus(InvestorProductBuyAgreementStatus.SUCCESS);
						ipba.setUrl(resultPDF.get("url")==null?"":resultPDF.get("url").toString());
					} else {
						ipba.setStatus(InvestorProductBuyAgreementStatus.FAIL);
						ipba.setUrl("");
					}
					//?????????????????????????????????
					MobileCode mcode = new MobileCode();
					String content = "??????????????????????????????"+Utlity.timeSpanToChinaDateString(time)+"????????????"+bank.getShortName()+bfpp.getName()
							+"??????????????????????????????"+Utlity.numFormat4UnitDetailLess(iah.getPay())+"??????";
					String mobile = investor.getMobile();
					String codeInfo = Utlity.getCaptcha();
					mcode.setCode(codeInfo);
					mcode.setContent(content);
					mcode.setMobile(mobile);
					mcode.setCreator(investor.getUuid());
					mcode.setStatus(MobileCodeStatus.DISABLE);
					mcode.setType(MobileCodeTypes.NOTICE);
					mcode.setCreatetime(new Timestamp(System.currentTimeMillis()));
					mcode.setCreatorType(MobileCodeCreatorType.INVESTOR);
					mcode.setUuid(UUID.randomUUID().toString());
					
//					content = "??????????????????"+content;
					InvestorInformation iii = new InvestorInformation();
					iii.setCreator(investor.getUuid());
					iii.setStatus(InvestorInformationStatus.UNREAD);
					iii.setCreatetime(new Timestamp(System.currentTimeMillis()));
					iii.setUuid(UUID.randomUUID().toString());
					iii.setInfoText(content);
					iii.setInfoTitle(InvestorInformationTitle.BUY);
					iii.setInvestor(investor.getUuid());
					
					//??????????????????????????????????????????????????????????????????????????????
					Boolean flagCoupon = Utlity.checkStringNull(coupon);
					if(!flagCoupon){
						InvestorCoupon ic = this.inestorCouponService.get(coupon);
						if(ic != null){
							//???????????????????????????????????????
							Coupon co = this.couponService.get(ic.getCoupon());
							if(co == null){
								return ResultManager.createFailResult("????????????????????????");
							}
							if(!InvestorCouponStatus.UNUSE.equals(ic.getStatus())){
								return ResultManager.createFailResult("???????????????????????????");
							}
							//?????????????????????
							ic.setStatus(InvestorCouponStatus.USED);
							//??????????????????????????? ??????????????????
							this.investorAccountHistoryService.insertProductBuy4Balance(mc, bfpp, investor, iah, ipba, ipb, isUpdate, isUpdate2, pai, mcode, iii, ic);
						} else {
							return ResultManager.createFailResult("???????????????????????????");
						}
					} else {
						this.investorAccountHistoryService.insertProductBuy4Balance(mc, bfpp, investor, iah, ipba, ipb, isUpdate, isUpdate2, pai, mcode, iii, null);
					}
					
					
					
				} else if (Utlity.PAY_TYPE_WECHART.equals(type)) {
					/*
					 * ????????????
					 * ?????????
					 * ??????????????????????????? ??????????????????
					 * ???????????????????????????????????????????????????
					 */
					String orderNumStr = Utlity.getOrderNumStr(device,Utlity.BILL_PAYTYPE_WECHART,Utlity.BILL_PURPOSE_INCOME);
					if(this.investorAccountHistoryService.getCheckOrderNum(orderNumStr)){
						return ResultManager.createFailResult("????????????????????????????????????");
					}
					String body = "????????????-??????"+bfpp.getShortname()+"??????????????????";
					String url = Utlity.WX_PAY_URL;
					
					String openid = investor.getOpenid();
					Map<String,Object> params = new HashMap<String,Object>();
					String orderNumStrBuy = Utlity.getOrderNumStr(device,Utlity.BILL_PAYTYPE_WECHART,Utlity.BILL_PURPOSE_BUY);	
					if(this.investorAccountHistoryService.getCheckOrderNum(orderNumStrBuy)){
						return ResultManager.createFailResult("????????????????????????????????????");
					}
					String nonceStr = UUID.randomUUID().toString().replace("-", "");
					StringBuffer signString = new StringBuffer();
					signString.append("appid=").append(Utlity.WX_APPID);
					signString.append("&body=").append(body);
					signString.append("&limit_pay=").append("no_credit");
					signString.append("&mch_id=").append(Utlity.WX_MCH_ID);
					signString.append("&nonce_str=").append(nonceStr);
					signString.append("&notify_url=").append(Utlity.NOTIFY_URL_BUY);
					signString.append("&openid=").append(openid);
					signString.append("&out_trade_no=").append(orderNumStr);
					signString.append("&sign_type=").append("MD5");
					signString.append("&total_fee=").append(price);
					signString.append("&trade_type=").append("JSAPI");
					signString.append("&key=").append(Utlity.WX_KEY);
					
					
					params.put("appid", Utlity.WX_APPID);
					params.put("body", body);
					params.put("limit_pay", "no_credit");
					params.put("mch_id", Utlity.WX_MCH_ID);
					params.put("nonce_str", nonceStr);
//					params.put("sign", MD5.getMD5(signString.toString()).toUpperCase());
					params.put("sign", MD5.MD5Encode(signString.toString(), "utf-8").toUpperCase());
					params.put("out_trade_no", orderNumStr);
					params.put("total_fee", price);
					params.put("spbill_create_ip", Utlity.SERVER_IP);
					params.put("notify_url", Utlity.NOTIFY_URL_BUY);
					params.put("trade_type", "JSAPI");
					params.put("sign_type", "MD5");
					params.put("openid", openid);
					String xmls = XMLUtils.getRequestXml(params);
					System.out.println(xmls);
					String xml = HttpUtility.postXml(url, xmls);
					System.out.println(xml);
					Map<String, Object> dataMap = XMLUtils.doXMLParse(xml);
					System.out.println(dataMap);
					if(OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(dataMap.get("return_code").toString())){
						
						OrderinfoByThirdparty obt = new OrderinfoByThirdparty();
						obt.setUuid(UUID.randomUUID().toString());
						obt.setType(OrderinfoByThirdpartyType.WEXIN);
						obt.setInvestor(investor.getUuid());
						obt.setOrderNum(orderNumStr);
						obt.setBody(body);
						obt.setTotalFee(paydivide);
						obt.setPaySource(openid);
						obt.setStatus(dataMap.get("result_code").toString());
						obt.setCreatetime(new Timestamp(System.currentTimeMillis()));
						if(OrderinfoByThirdpartyResultStatus.SUCCESS.equals(dataMap.get("result_code").toString())){
							obt.setPrepayId(dataMap.get("prepay_id").toString());
						}
						obt.setErrCode(dataMap.get("err_code") == null ? "" : dataMap.get("err_code").toString());
						obt.setErrCodeDes(dataMap.get("err_code_des") == null ? "" : dataMap.get("err_code_des").toString());
						obt.setMessage(dataMap.get("return_msg") == null ? "" : dataMap.get("return_msg").toString());
						
						InvestorAccountHistory iah = new InvestorAccountHistory();
						iah.setUuid(UUID.randomUUID().toString());
						iah.setInvestor(investor.getUuid());
						iah.setIncome(paydivide);
						iah.setPay(BigDecimal.ZERO);
						iah.setAccountBalance(investor.getAccountBalance().add(paydivide));
						iah.setOrderId(obt.getUuid());
						iah.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_WECHART);
						iah.setOrderNum(orderNumStr);
						iah.setType(InvestorAccountHistoryType.INCOME);
						iah.setStatus(InvestorAccountHistoryStatus.TRANSACTING);
						iah.setCreatetime(new Timestamp(System.currentTimeMillis()));
						iah.setCompanyAccount(CompanyAccountUuid.WECHART);
//						iah.setPoundage(BigDecimal.ZERO);
						BigDecimal poundage = paydivide.multiply(Utlity.POUNDAGE_FEE_WECHAT).setScale(2,BigDecimal.ROUND_HALF_UP);
						iah.setPoundage(poundage);
						
						
						Timestamp time = new Timestamp(System.currentTimeMillis());
						
						InvestorAccountHistory iahBuy = new InvestorAccountHistory();
						iahBuy.setUuid(UUID.randomUUID().toString());
						iahBuy.setInvestor(investor.getUuid());
						
						//????????????
						iahBuy.setIncome(BigDecimal.ZERO);
						iahBuy.setPay(paydivide);
						
						iahBuy.setAccountBalance(investor.getAccountBalance());
						iahBuy.setOrderId(obt.getUuid());
						iahBuy.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_WECHART);
						iahBuy.setOrderNum(orderNumStrBuy);
						iahBuy.setType(InvestorAccountHistoryType.BUY);
						iahBuy.setStatus(InvestorAccountHistoryStatus.TRANSACTING);
						iahBuy.setCreatetime(time);
						iahBuy.setCompanyAccount(CompanyAccountUuid.WECHART);
						iahBuy.setProduct(product);
						iahBuy.setProductType(InvestorProductBuyProductType.BANK_PRODUCT);
						iahBuy.setPoundage(BigDecimal.ZERO);
						
						//????????????????????????
						bfpp.setAccountBalance(bfpp.getAccountBalance().add(iahBuy.getPay()));
//						InvestorProductBuyRecords ipbr = new InvestorProductBuyRecords();
//						ipbr.setUuid(UUID.randomUUID().toString());
//						ipbr.setBill(iahBuy.getUuid());
//						ipbr.setInvestor(investor.getUuid());
//						ipbr.setPaytime(time);
//						ipbr.setPrice(paydivide);
//						ipbr.setProduct(product);
//						ipbr.setStatus(InvestorProductBuyRecordsStatus.TRANSACTING);
//						ipbr.setStage(InvestorProductBuyRecordsStage.CONFIRMING);
						
//						//??????????????????PDF??????????????????
//						InvestorProductBuyAgreement ipba = new InvestorProductBuyAgreement();
//						ipba.setUuid(UUID.randomUUID().toString());
//						ipba.setCreatetime(time);
//						ipba.setInvestor(investor.getUuid());
//						ipba.setType(InvestorProductBuyAgreementType.BANKPRODUCT);
//						ipba.setRecords(ipbr.getUuid());
//						ipba.setScode(orderNumStrBuy);
//						ipba.setName("??????????????????????????????"+orderNumStr);
//						Map<String, Object> paramss = new HashMap<String, Object>();
//						params.put("scode", orderNumStr);
//				    	params.put("realname", investor.getRealname());
//				    	params.put("phone", Utlity.getStarMobile(investor.getMobile()));
//				    	params.put("idcard", Utlity.getStarIdcard(investor.getIdcard()));
//				    	params.put("productName", bank.getShortName()+bfpp.getName());
//				    	String priceRMB = RMBUtlity.arabNumToChineseRMB(Double.parseDouble(ipbr.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
//				    	String priceStr = "????????????"+Utlity.numFormat4UnitDetail(ipbr.getPrice())+"(?????????"+priceRMB+"???)";
//				    	params.put("price", priceStr);
//				    	params.put("uuid", ipba.getUuid());
//				    	params.put("fileName", "??????????????????????????????"+orderNumStrBuy);
//						Map<String, Object> resultPDF = PDFUtlity.ToPdf(paramss);
//						if((Boolean) resultPDF.get("result")){
//							ipba.setStatus(InvestorProductBuyAgreementStatus.SUCCESS);
//							ipba.setUrl(resultPDF.get("url")==null?"":resultPDF.get("url").toString());
//						} else {
//							ipba.setStatus(InvestorProductBuyAgreementStatus.FAIL);
//							ipba.setUrl("");
//						}
						
						this.investorAccountHistoryService.insertProductBuy4Wechart(bfpp, obt, iah, iahBuy);
						String timestamp = System.currentTimeMillis()+"";
						dataMap.put("timestamp", timestamp);
						//???????????????????????????
						StringBuffer signStringForX = new StringBuffer();
						signStringForX.append("appId=").append(Utlity.WX_APPID);
						signStringForX.append("&nonceStr=").append(dataMap.get("nonce_str").toString());
						signStringForX.append("&package=").append("prepay_id=").append(obt.getPrepayId());
						signStringForX.append("&signType=").append("MD5");
						signStringForX.append("&timeStamp=").append(timestamp);
						signStringForX.append("&key=").append(Utlity.WX_KEY);
						String sign = MD5.MD5Encode(signStringForX.toString(), "utf-8").toUpperCase();
						dataMap.put("sign4Wechart", sign);
						return ResultManager.createDataResult(dataMap,"???????????????");
					} else {
						return ResultManager.createFailResult(dataMap.get("return_msg").toString());
					}
				} else if (Utlity.PAY_TYPE_ALIPAY.equals(type)) {
					/*
					 * ???????????????
					 */
					
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
	 * ????????????????????????--????????????
	 * @param uuid
	 * @param price
	 * @param product
	 * @param type ???????????? send-??????????????????????????? check-?????? resend-?????????????????????orderNum???
	 * @return
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/productPayByChanpay", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "bankcard", type = DataType.STRING, message = "???????????????????????????", required = true)
	@ActionParam(key = "price", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "product", type = DataType.STRING, message = "??????????????????", required = true)
	@ActionParam(key = "type", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "code", type = DataType.STRING, message = "???????????????")
	@ActionParam(key = "orderNum", type = DataType.STRING, message = "send ?????????????????????")
	@ActionParam(key = "token", type = DataType.STRING)
	@ResponseBody
	protected Result productPayByChanpay(String uuid, String bankcard, String price, String product, String type, String code,String orderNum, String token) throws IOException, DocumentException{
		
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("???????????????");
		}
		if(!investor.getRealnameAuthFlag()){
			return ResultManager.createFailResult("???????????????");
		}
		
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency4Web(price)) {
			return ResultManager.createFailResult("????????????????????????!");
		}
		BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));
		BigDecimal paydivide = pay.divide(BigDecimal.valueOf(100));
		//????????????????????????????????????
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
		
		if(paydivide.compareTo(bfpp.getMaxInvestAmount()) == 1){
			return ResultManager.createFailResult("?????????????????????"+bfpp.getMinInvestAmount()+"???");
		}
		
		token = Base64Util.getFromBase64(token);
		String device = Utlity.getDeviceStr(token);
		synchronized(this){//????????????????????????????????????????????????????????? ?????????????????????
			try {
				String orderNumStr = Utlity.getOrderNumStr(device,Utlity.BILL_PAYTYPE_CHANPAY,Utlity.BILL_PURPOSE_INCOME);
				if(this.investorAccountHistoryService.getCheckOrderNum(orderNumStr)){
					return ResultManager.createFailResult("????????????????????????????????????");
				}
				//service???investor,bfpp,pay,?????????????????? ??????????????????
				if("send".equals(type)){
					//???????????? ????????????????????????
					//???????????? ????????????????????????
					//??????????????????????????????-(????????????+????????????-????????????-????????????)
//					BigDecimal totalamount = bfpp.getTotalAmount();//???????????????
					BigDecimal totalamount = bfpp.getCollectAmount();//??????????????????
					BigDecimal accountBalance = bfpp.getAccountBalance();//????????????
					BigDecimal investment = bfpp.getInvestment();//????????????
					BigDecimal totalReturn = bfpp.getTotalReturn();//????????????
					BigDecimal totalRedeem = bfpp.getTotalRedeem();
					BigDecimal realAmount = totalamount.subtract(accountBalance.add(investment).subtract(totalReturn).subtract(totalRedeem));
					if(realAmount.compareTo(paydivide) == -1){//?????????????????????????????????
						return ResultManager.createFailResult("??????????????????");
					}
					
					InvestorBankcard ib = this.investorBankcardService.get(bankcard);
					if(ib == null){
						return ResultManager.createFailResult("??????????????????????????????");
					}
					if(!ib.getInvestor().equals(investor.getUuid())){
						return ResultManager.createFailResult("????????????????????????????????????");
					}
					String bankcardNum = ib.getBindingBankCard();
					String cardbegin = bankcardNum.substring(0,6);
					String cardend = bankcardNum.substring(bankcardNum.length()-4);
					String priceStr = paydivide.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
					Map<String, Object> returnMap = ChanPayUtil.nmg_biz_api_quick_payment(ChanPayUtil.noticeBuyURL, "??????", investor.getUuid(), cardbegin, cardend, priceStr, orderNumStr);
					OrderinfoByThirdparty obt = new OrderinfoByThirdparty();
					obt.setUuid(UUID.randomUUID().toString());
					obt.setType(OrderinfoByThirdpartyType.CHANPAY);
					obt.setInvestor(investor.getUuid());
					obt.setOrderNum(orderNumStr);
					obt.setBody("????????????--????????????");
					obt.setTotalFee(paydivide);
					obt.setPaySource(MD5.getMD5(investor.getUuid()));
					obt.setStatus(returnMap.get("Status").toString());
					obt.setCreatetime(new Timestamp(System.currentTimeMillis()));
					obt.setErrCode(returnMap.get("RetCode") == null ? "" : returnMap.get("RetCode").toString());
					obt.setErrCodeDes(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
					obt.setMessage(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
					if(ChanPayUtil.RETSTATUS_SUCCESS.equals(returnMap.get("Status").toString()) || ChanPayUtil.RETSTATUS_RROSESS.equals(returnMap.get("Status").toString())){
						obt.setPrepayId(returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString());
						InvestorAccountHistory iah = new InvestorAccountHistory();
						iah.setUuid(UUID.randomUUID().toString());
						iah.setInvestor(investor.getUuid());
						iah.setIncome(paydivide);
						iah.setPay(BigDecimal.ZERO);
						iah.setAccountBalance(investor.getAccountBalance().add(paydivide));
						iah.setOrderId(obt.getUuid());
						iah.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_CHANPAY);
						iah.setOrderNum(obt.getOrderNum());
						iah.setType(InvestorAccountHistoryType.INCOME);
						iah.setStatus(InvestorAccountHistoryStatus.TRANSACTING);
						iah.setCreatetime(new Timestamp(System.currentTimeMillis()));
						iah.setCompanyAccount(CompanyAccountUuid.CHANPAY);//?????????????????????????????????????????????
						//???????????????????????????
						BigDecimal poundage = paydivide.multiply(ChanPayUtil.POUNDAGE_FEE).setScale(2,BigDecimal.ROUND_HALF_UP);
						iah.setPoundage(poundage);
						
						Timestamp time = new Timestamp(System.currentTimeMillis());
						
						InvestorAccountHistory iahBuy = new InvestorAccountHistory();
						iahBuy.setUuid(UUID.randomUUID().toString());
						iahBuy.setInvestor(investor.getUuid());
						
						//????????????
						iahBuy.setIncome(BigDecimal.ZERO);
						iahBuy.setPay(paydivide);
						
						iahBuy.setAccountBalance(investor.getAccountBalance());
						iahBuy.setOrderId(obt.getUuid());
						iahBuy.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_CHANPAY);
						iahBuy.setOrderNum(orderNumStr);
						iahBuy.setType(InvestorAccountHistoryType.BUY);
						iahBuy.setStatus(InvestorAccountHistoryStatus.TRANSACTING);
						iahBuy.setCreatetime(time);
						iahBuy.setCompanyAccount(CompanyAccountUuid.CHANPAY);//?????????????????????????????????????????????
						iahBuy.setProduct(product);
						iahBuy.setProductType(InvestorProductBuyProductType.BANK_PRODUCT);
						iahBuy.setPoundage(BigDecimal.ZERO);
						
						//????????????????????????
						bfpp.setAccountBalance(bfpp.getAccountBalance().add(iahBuy.getPay()));
						
						this.investorAccountHistoryService.insertProductBuy4Wechart(bfpp, obt, iah, iahBuy);
						return ResultManager.createDataResult(obt.getUuid(), "???????????????");
					} else {
						String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
						obt.setPrepayId(prepayId);
						this.orderinfoByThirdpartyService.insert(obt);
						return ResultManager.createFailResult("???????????????");
					}
					
				} else if ("resend".equals(type)) {
					if(Utlity.checkStringNull(orderNum)){
						return ResultManager.createFailResult("??????????????????????????????");
					}
					OrderinfoByThirdparty obt = this.orderinfoByThirdpartyService.get(orderNum);
					if(obt == null){
						return ResultManager.createFailResult("?????????????????????????????????");
					}
					Map<String, Object> returnMap = ChanPayUtil.nmg_sms_resend("??????", obt.getOrderNum(), ChanPayUtil.TYPE_PAY_ORDER, orderNumStr);
					OrderinfoByThirdparty obtN = new OrderinfoByThirdparty();
					obtN.setUuid(UUID.randomUUID().toString());
					obtN.setType(OrderinfoByThirdpartyType.CHANPAY);
					obtN.setInvestor(investor.getUuid());
					obtN.setOrderNum(orderNumStr);
					obtN.setBody("????????????--????????????(????????????)");
					obtN.setTotalFee(BigDecimal.ZERO);
					obtN.setPaySource(MD5.getMD5(investor.getUuid()));
					obtN.setStatus(returnMap.get("Status").toString());
					obtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
					obtN.setErrCode(returnMap.get("RetCode") == null ? "" : returnMap.get("RetCode").toString());
					obtN.setErrCodeDes(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
					obtN.setMessage(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
					if(ChanPayUtil.RETSTATUS_SUCCESS.equals(returnMap.get("Status").toString()) || ChanPayUtil.RETSTATUS_RROSESS.equals(returnMap.get("Status").toString())){
						obtN.setPrepayId(returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString());
					} else {
						String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
						obtN.setPrepayId(prepayId);
					}
					this.orderinfoByThirdpartyService.insert(obtN);
					return ResultManager.createDataResult(obt.getUuid(), "???????????????");
				} else if ("check".equals(type)) {
					if(Utlity.checkStringNull(orderNum)){
						return ResultManager.createFailResult("??????????????????????????????");
					}
					OrderinfoByThirdparty obt = this.orderinfoByThirdpartyService.get(orderNum);
					if(obt == null){
						return ResultManager.createFailResult("?????????????????????????????????");
					}
					code = Base64Util.getFromBase64(code);
					
					Map<String, Object> returnMap = ChanPayUtil.nmg_api_quick_payment_smsconfirm("??????", obt.getOrderNum(), code, orderNumStr);
					OrderinfoByThirdparty obtN = new OrderinfoByThirdparty();
					obtN.setUuid(UUID.randomUUID().toString());
					obtN.setType(OrderinfoByThirdpartyType.CHANPAY);
					obtN.setInvestor(investor.getUuid());
					obtN.setOrderNum(orderNumStr);
					obtN.setBody("????????????--????????????");
					obtN.setTotalFee(BigDecimal.ZERO);
					obtN.setPaySource(MD5.getMD5(investor.getUuid()));
					obtN.setStatus(returnMap.get("Status").toString());
					obtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
					obtN.setErrCode(returnMap.get("RetCode") == null ? "" : returnMap.get("RetCode").toString());
					obtN.setErrCodeDes(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
					obtN.setMessage(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
					if(ChanPayUtil.RETSTATUS_SUCCESS.equals(returnMap.get("Status").toString()) || ChanPayUtil.RETSTATUS_RROSESS.equals(returnMap.get("Status").toString())){
						obtN.setPrepayId(returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString());
						
						this.orderinfoByThirdpartyService.insert(obtN);
						return ResultManager.createSuccessResult("????????????");
					} else {
						String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
						obtN.setPrepayId(prepayId);
						this.orderinfoByThirdpartyService.insert(obtN);
						return ResultManager.createFailResult("???????????????"+obtN.getErrCodeDes()+"???");
					}
					
				} else {
					return ResultManager.createFailResult("???????????????");
				}
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult("??????????????????");
			}
		}
	}
	
	/**
	 * ????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/getAliQrCode", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "????????????", type = DataType.STRING)
	@ResponseBody
	protected Result getAliQrCode(String uuid, Integer pageNum, Integer pageSize, String sorts){
		//??????????????????
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("???????????????");
		}
		if(!investor.getRealnameAuthFlag()){
			return ResultManager.createFailResult("???????????????");
		}
		 
		pageNum = pageNum == null ? -1 : pageNum;
		pageSize = pageSize == null ? -1 : pageSize;
		
		Map<String, String> inputParams = new HashMap<String, String>();
		Integer totalcount = this.aliQrcodeService.getCount(inputParams);
		List<Entity> list = this.aliQrcodeService.getListForPage(inputParams, pageNum, pageSize, sorts, AliQrcodeVO.class);
		
		
		return ResultManager.createDataResult(list, pageNum, pageSize, totalcount);
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param uuid
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/getPaymentList", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "????????????", type = DataType.STRING)
	@ResponseBody
	protected Result getPaymentList(String uuid, Integer pageNum, Integer pageSize, String sorts){
		//??????????????????
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("???????????????");
		}
		if(!investor.getRealnameAuthFlag()){
			return ResultManager.createFailResult("???????????????");
		}
		
		pageNum = pageNum == null ? -1 : pageNum;
		pageSize = pageSize == null ? -1 : pageSize;
		Map<String, String> inputParams = new HashMap<String, String>();
		Integer totalcount = this.bkPaymentService.getCount(inputParams);
		List<Entity> list = this.bkPaymentService.getListForPage(inputParams, pageNum, pageSize, sorts, BkPayment.class);
		
		return ResultManager.createDataResult(list, pageNum, pageSize, totalcount);
	}
	
//	/**
//	 * ??????????????????
//	 * ????????? ?????????????????????????????????
//	 * @param uuid
//	 * @param flagShare
//	 * @param redPacket
//	 * @return
//	 */
//	@RequestMapping(value = "/redPacket", method = RequestMethod.POST)
//	@ActionParam(key = "uuid", type = DataType.STRING, message = "????????????", required = true)
//	@ActionParam(key = "flagShare", type = DataType.BOOLEAN, message = "????????????", required = true)
//	@ActionParam(key = "redPacket", type = DataType.STRING, message = "????????????", required = true)
//	@ResponseBody
//	protected Result redPacket(String uuid, Boolean flagShare, String redPacket) {
//		Investor investor = this.investorService.get(uuid);
//		if(investor == null){
//			return ResultManager.createFailResult("???????????????");
//		}
//		InvestorRedPacket irp = this.investorRedPacketService.get(redPacket);
//		if(irp == null){
//			return ResultManager.createFailResult("?????????????????????");
//		}
//		try {
//			if(flagShare){
//				irp.setPirce(irp.getPirce().add(BigDecimal.valueOf(2)));
//			}
//			this.investorAccountHistoryService.insertRedPacket(irp, investor);
//		} catch (TransactionException e) {
//			e.printStackTrace();
//			return ResultManager.createFailResult(e.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResultManager.createFailResult("?????????????????????");
//		}
//		return ResultManager.createSuccessResult("???????????????");
//	}
	
	/**
	 * ????????????????????????
	 * @param uuid
	 * @param price
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/currentPay", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "price", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "code", type = DataType.STRING, message = "???????????????", required = true)
	@ResponseBody
	protected Result currentPay(String uuid, String price, String code){
		
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("???????????????");
		}
		if(!investor.getRealnameAuthFlag()){
			return ResultManager.createFailResult("???????????????");
		}
		
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
		inputParams.put("mobile", investor.getMobile());
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
			if(investor.getAccountBalance().compareTo(pay) == -1){
				return ResultManager.createFailResult("?????????????????????");
			}
			
			BigDecimal currentAccountAdd = pay.divide(netValue, 8, BigDecimal.ROUND_HALF_UP);
			
			InvestorAccountHistory iah = new InvestorAccountHistory();
			iah.setUuid(UUID.randomUUID().toString());
			iah.setInvestor(investor.getUuid());
			iah.setOrderId(iah.getUuid());
			iah.setOrderNum(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_OHER,Utlity.BILL_PAYTYPE_BALANCE,Utlity.BILL_PURPOSE_BUY));
			iah.setPay(pay);
			iah.setIncome(BigDecimal.ZERO);
			iah.setPoundage(BigDecimal.ZERO);
			iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
			iah.setType(InvestorAccountHistoryType.CURRENT_BUY);
			iah.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			investor.setCurrentAccount(investor.getCurrentAccount().add(currentAccountAdd));
			investor.setAccountBalance(investor.getAccountBalance().subtract(pay));
			this.investorService.updateCurrentPay(investor, iah);
		}
		
		return ResultManager.createSuccessResult("????????????");
	}
	
	/**
	 * ????????????????????????
	 * @param uuid
	 * @param price
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/currentReturn", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "price", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "code", type = DataType.STRING, message = "???????????????", required = true)
	@ResponseBody
	protected Result currentReturn(String uuid, String price, String code){
		
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("???????????????");
		}
		if(!investor.getRealnameAuthFlag()){
			return ResultManager.createFailResult("???????????????");
		}
		
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
		inputParams.put("mobile", investor.getMobile());
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
			if(investor.getCurrentAccount().multiply(netValue).compareTo(pay) == -1){
				return ResultManager.createFailResult("?????????????????????");
			}
			
			BigDecimal currentAccountSub = pay.divide(netValue, 8, BigDecimal.ROUND_HALF_UP);
			
			InvestorAccountHistory iah = new InvestorAccountHistory();
			iah.setUuid(UUID.randomUUID().toString());
			iah.setInvestor(investor.getUuid());
			iah.setOrderId(iah.getUuid());
			iah.setOrderNum(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_OHER,Utlity.BILL_PAYTYPE_BALANCE,Utlity.BILL_PURPOSE_DIVIDEND));
			iah.setPay(BigDecimal.ZERO);
			iah.setIncome(pay);
			iah.setPoundage(BigDecimal.ZERO);
			iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
			iah.setType(InvestorAccountHistoryType.CURRENT_RETURN);
			iah.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			investor.setCurrentAccount(investor.getCurrentAccount().subtract(currentAccountSub));
			investor.setAccountBalance(investor.getAccountBalance().add(pay));
			this.investorService.updateCurrentPay(investor, iah);
		}
		
		return ResultManager.createSuccessResult("????????????");
	}
	
	/**
	 * ????????????????????????????????????
	 * @param uuid
	 * @param flagCurrent
	 * @return
	 */
	@RequestMapping(value = "/editFlagCurrent", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "????????????", required = true)
	@ActionParam(key = "flagCurrent", type = DataType.BOOLEAN, message = "??????????????????", required = true)
	@ResponseBody
	protected Result editFlagCurrent(String uuid, Boolean flagCurrent){
		
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("???????????????");
		}
		if(!investor.getRealnameAuthFlag()){
			return ResultManager.createFailResult("???????????????");
		}
		
		investor.setFlagCurrent(flagCurrent);
		this.investorService.update(investor);
		
		return ResultManager.createSuccessResult("????????????");
	}
	
	
	
	public static void main(String[] args) {
		String nonceStr = UUID.randomUUID().toString().replace("-", "");
		StringBuilder signString = new StringBuilder();
		signString.append("appid=").append(Utlity.WX_APPID);
		signString.append("&body=").append("????????????-????????????");
		signString.append("&mch_id=").append(Utlity.WX_MCH_ID);
		signString.append("&nonce_str=").append("972d1b7d03834b7787f979e2178c0b8f");
		signString.append("&key=").append(Utlity.WX_KEY);
		System.out.println(signString);
		System.out.println(MD5.getMD5(signString.toString()).toUpperCase());
		System.out.println(MD5.MD5Encode(signString.toString(), "utf-8").toUpperCase());
		System.out.println(nonceStr);
		System.out.println(nonceStr.length());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		return sdf.format(ts);
		System.out.println((System.currentTimeMillis()+"").length());
		String rom = Utlity.getRomNumStr(4);
		String time = sdf.format(new Timestamp(System.currentTimeMillis()));
		System.out.println(time);
		System.out.println(time.length());
		time += rom;
		System.out.println(time);
		System.out.println(time.length());
		System.out.println(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_WECHAT,Utlity.BILL_PAYTYPE_WECHART,Utlity.BILL_PURPOSE_INCOME));
		System.out.println(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_WECHAT,Utlity.BILL_PAYTYPE_WECHART,Utlity.BILL_PURPOSE_INCOME).length());
		
		System.out.println(Base64Util.getBase64("test"));
	}
}
