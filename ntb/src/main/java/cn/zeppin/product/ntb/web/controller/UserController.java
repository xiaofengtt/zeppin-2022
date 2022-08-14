package cn.zeppin.product.ntb.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IAliCertificationService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorAccountHistoryService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorBankcardService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorCouponService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorIdcardImgService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorInformationService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.backadmin.service.api.IOrderinfoByThirdpartyService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.InvestorBankcard;
import cn.zeppin.product.ntb.core.entity.InvestorBankcard.InvestorBankcardStatus;
import cn.zeppin.product.ntb.core.entity.InvestorCoupon.InvestorCouponStatus;
import cn.zeppin.product.ntb.core.entity.InvestorIdcardImg;
import cn.zeppin.product.ntb.core.entity.InvestorIdcardImg.InvestorIdcardImgStatus;
import cn.zeppin.product.ntb.core.entity.InvestorInformation.InvestorInformationStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.web.vo.BankVO;
import cn.zeppin.product.ntb.web.vo.InvestorAccountHistoryVO;
import cn.zeppin.product.ntb.web.vo.InvestorVO;
import cn.zeppin.product.utility.AliUtlity;
import cn.zeppin.product.utility.BankCardUtlity;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.SendSms;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.reapal.ReapalUtil;
import cn.zeppin.product.utility.reapal.ReapalUtlity;

@Controller
@RequestMapping(value = "/web/user")
public class UserController extends BaseController{
	
	@Autowired
	private IInvestorService investorService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IInvestorIdcardImgService investorIdcardImgService;
	
	@Autowired
	private IInvestorBankcardService investorBankcardService;
	
	@Autowired
	private IInvestorAccountHistoryService investorAccountHistoryService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IAliCertificationService aliCertificationService;
	
	@Autowired
	private IOrderinfoByThirdpartyService orderinfoByThirdpartyService;
	
	@Autowired
	private IInvestorInformationService investorInformationService;
	
	@Autowired
	private IInvestorCouponService investorCouponService;
	
	/**
	 * 实名认证
	 * @param name
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/certification", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ActionParam(key = "name", type = DataType.STRING, message="用户姓名", required = true)
	@ActionParam(key = "idcard", type = DataType.STRING, message="用户身份证号", required = true)
	@ActionParam(key = "imgface", type = DataType.STRING, message="用户身份证号正面照片")
	@ActionParam(key = "imgback", type = DataType.STRING, message="用户身份证号反面照片")
	@ResponseBody
	public Result certification(String uuid, String name, String idcard, String imgface, String imgback){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("认证失败，用户信息不存在。");
		}
		name = Base64Util.getFromBase64(name);
		idcard = Base64Util.getFromBase64(idcard);
		idcard = idcard.toUpperCase();
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("idcard", idcard);
		Integer count = this.investorService.getCount(inputParams);
		if(count > 0){
			return ResultManager.createFailResult("认证失败，该身份证信息已被其他用户认证。");
		}
		try {
			this.investorService.logincertification(idcard, name, investor, imgface, imgback);
			return ResultManager.createSuccessResult("实名认证成功！");
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("你输入的身份认证信息有误，请重新认证。");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("操作异常，实名认证失败！");
		}
	}
	
	/**
	 * 实名认证（证件照片认证）
	 * @param name
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/certificationImg", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ActionParam(key = "imgface", type = DataType.STRING, message="用户身份证号正面照片")
	@ActionParam(key = "imgback", type = DataType.STRING, message="用户身份证号反面照片")
	@ResponseBody
	public Result certificationImg(String uuid, String imgface, String imgback){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("证件照片上传失败，用户信息不存在！");
		}
		if(!investor.getRealnameAuthFlag()){
			return ResultManager.createFailResult("证件照片上传失败，用户未进行实名认证！");
		}
		if(!Utlity.checkStringNull(imgface) && !Utlity.checkStringNull(imgback)){
			Resource reImgface = this.resourceService.get(imgface);
			if(reImgface == null){
				return ResultManager.createFailResult("认证失败，未上传证件正面照片！");
			}
			
			Resource reImgback = this.resourceService.get(imgback);
			if(reImgback == null){
				return ResultManager.createFailResult("认证失败，未上传证件反面照片！");
			}
			try {
				this.investorService.logincertification(investor.getIdcard(), investor.getRealname(), investor, imgface, imgback);
				return ResultManager.createSuccessResult("证件照片上传成功！");
			} catch (TransactionException e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult("操作异常，证件照片上传失败！");
			}
		} else {
			return ResultManager.createFailResult("证件照片上传失败，未上传完整的证件照片！");
		}
	}
	
	/**
	 * 获取用户信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ResponseBody
	public Result get(String uuid){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		InvestorVO ivo = new InvestorVO(investor);
		String idcardImgStatus = InvestorIdcardImgStatus.NOTUPLOAD;
		String idcardImgStatusCN = "未上传";
		//查看证件审核状态
		if(investor.getIdcardImg() != null){
			InvestorIdcardImg idmg = this.investorIdcardImgService.get(investor.getIdcardImg());
			if(idmg != null){
				idcardImgStatus = idmg.getStatus();
			}
		}
		if(InvestorIdcardImgStatus.UNCHECKED.equals(idcardImgStatus)){
			idcardImgStatusCN = "审核中";
		} else if (InvestorIdcardImgStatus.UNPASSED.equals(idcardImgStatus)) {
			idcardImgStatusCN = "未通过";
		} else if (InvestorIdcardImgStatus.CHECKED.equals(idcardImgStatus)) {
			idcardImgStatusCN = "已通过";
		}
		ivo.setIdcardImgStatus(idcardImgStatus);
		ivo.setIdcardImgStatusCN(idcardImgStatusCN);
		//查看银行卡绑定状态（获取绑定银行卡数目）
		try {
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("investor", uuid);
			inputParams.put("status", InvestorBankcardStatus.NORMAL);
			Integer count = this.investorBankcardService.getCount(inputParams);
			ivo.setBankcardCount(count == null ? "0" : String.valueOf(count));
			
			//获取优惠券数目和是否有未读消息
			inputParams.clear();
			inputParams.put("investor", uuid);
			inputParams.put("status", InvestorInformationStatus.UNREAD);
			
			Integer countMsg = this.investorInformationService.getCount(inputParams);
			if(countMsg != null && countMsg > 0){
				ivo.setMessageFlag(true);
			} else {
				ivo.setMessageFlag(false);
			}
			
			inputParams.clear();
			inputParams.put("investor", uuid);
			inputParams.put("status", "'"+InvestorCouponStatus.UNUSE+"'");
			Integer countCoupon = this.investorCouponService.getCount(inputParams);
			ivo.setCouponCount(countCoupon);
			
			return ResultManager.createDataResult(ivo, "获取成功");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("用户信息查询失败！");
		}
	}
	
	/**
	 * 检查银行卡所属银行
	 * @param bankcard
	 * @return
	 */
	@RequestMapping(value = "/bindingCheckCard", method = RequestMethod.GET)
	@ActionParam(key = "bankcard", type = DataType.STRING, message="银行卡号", required = true)
	@ResponseBody
	public Result bindingCheckCard(String bankcard){
		bankcard = Base64Util.getFromBase64(bankcard);
		if(bankcard.length() < 12 || bankcard.length() > 21){
			return ResultManager.createFailResult("请填写正确的银行卡号");
		}
		boolean flag = BankCardUtlity.checkBankCard(bankcard);
		if(!flag){
			return ResultManager.createErrorResult("405", "请填写正确的银行卡号");
		}
		
		String bankName = BankCardUtlity.getname(bankcard);
		if(bankName == null){
			return ResultManager.createErrorResult("405", "请填写正确的银行卡号");
		}
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("bankName", bankName);
		inputParams.put("flagBinding", "1");
		List<Entity> list = this.bankService.getListForWebPage(inputParams, -1, -1, null, BankVO.class);
		if(list != null && list.size() > 0){
			BankVO bank = (BankVO) list.get(0);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("uuid", bank.getUuid());
			result.put("name", bank.getName());
			result.put("shortName", bank.getShortName());
			result.put("iconColor", bank.getIconColorUrl());
			return ResultManager.createDataResult(result, "操作成功");
		} else {
			return ResultManager.createErrorResult("405", "暂不支持所填银行卡");
		}
	}
	
	/**
	 * 发送银行短信验证码
	 * @param uuid
	 * @param bankcard
	 * @param phone
	 * @param bank
	 * @param cardholder
	 * @return
	 */
	@RequestMapping(value = "/bindingSendCode", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ActionParam(key = "bankcard", type = DataType.STRING, message="银行卡号", required = true)
	@ActionParam(key = "phone", type = DataType.STRING, message="银行预留手机号", required = true)
	@ActionParam(key = "bank", type = DataType.STRING, message="银行卡所属行", required = true)
	@ActionParam(key = "cardholder", type = DataType.STRING, message="持卡人", required = true)
//	@ActionParam(key = "token", type = DataType.STRING, message="校验参数")
	@ResponseBody
	public Result bindingSendCode(String uuid, String bankcard, String phone, String bank, String cardholder, String token){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		bankcard = Base64Util.getFromBase64(bankcard);
		if(bankcard.length() < 12 || bankcard.length() > 21){
			return ResultManager.createFailResult("请填写正确的银行卡号");
		}
		boolean flag = BankCardUtlity.checkBankCard(bankcard);
		if(!flag){
			return ResultManager.createFailResult("请填写正确的银行卡号");
		}
		String bankName = BankCardUtlity.getname(bankcard);
		Bank bankInfo = this.bankService.get(bank);
		if(bankInfo == null){
			return ResultManager.createFailResult("银行卡所属银行错误！");
		}
		if (bankName != null && !"".equals(bankName)) {
			if((bankName.indexOf(bankInfo.getName()) == -1) && (bankInfo.getName().indexOf(bankName) == -1 )
					&& (bankName.indexOf(bankInfo.getShortName()) == -1) && (bankInfo.getShortName().indexOf(bankName) == -1 )){
				return ResultManager.createFailResult("银行卡与所属银行不匹配！");
			}
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("bindingBankCard", bankcard);
		int count = this.investorBankcardService.getCount(inputParams);
		if(count > 0){
			return ResultManager.createFailResult("该银行卡已经绑定过！");
		}
		
		phone = Base64Util.getFromBase64(phone);
		if (!Utlity.isMobileNO(phone)) {
			return ResultManager.createFailResult("手机号码非法！");
		}
		cardholder = Base64Util.getFromBase64(cardholder);
		token = Base64Util.getFromBase64(token);
		String deviceNumber = token.substring(0,2);
		String device = "";
		if(Utlity.DEVICE_NUMBER_WECHAT.equals(deviceNumber)){
			device = Utlity.BILL_DEVICE_WECHAT;
		} else if (Utlity.DEVICE_NUMBER_ANDROID.equals(deviceNumber)) {
			device = Utlity.BILL_DEVICE_ANDROID;
		} else if (Utlity.DEVICE_NUMBER_IOS.equals(deviceNumber)) {
			device = Utlity.BILL_DEVICE_IOS;
		} else if (Utlity.DEVICE_NUMBER_WEB.equals(deviceNumber)) {
			device = Utlity.BILL_DEVICE_WEB;
		}
		String orderNumStr = Utlity.getOrderNumStr(device,Utlity.BILL_PAYTYPE_OHER,Utlity.BILL_PURPOSE_CHANPAY_BINDING);
		if(this.investorAccountHistoryService.getCheckOrderNum(orderNumStr)){
			return ResultManager.createFailResult("手速太快，服务器未响应！");
		}
		HashMap<String, Object> result = null;
		//调用支付宝欺诈信息校验接口校验手机号、持卡人是否正确
		try {
			//调起融宝支付银行卡信息查询接口 判断卡类型是否为信用卡/贷记卡
			Map<String, Object> cardResult = ReapalUtlity.bankCardQuery(bankcard);
			if(cardResult != null && cardResult.containsKey("bank_card_type")){
				String cardType = cardResult.get("bank_card_type").toString();
				if(!ReapalUtil.card_type_deposit.equals(cardType)){
					return ResultManager.createFailResult("不支持贷记卡/信用卡类型的银行卡！");
				}
			}
			
			result = this.investorService.updateBindingBankcard(phone, bankcard, investor.getIdcard(), cardholder, investor, orderNumStr);
			if((Boolean)result.get("result")){
//				return ResultManager.createSuccessResult(result.get("message") == null ? "实名认证成功！" : result.get("message").toString());
				//调用畅捷支付绑卡请求接口发送绑卡验证码
//				ChanPayUtil.nmg_biz_api_auth_req(investor.getIdcard(), cardholder, phone, bankcard);
				return ResultManager.createDataResult(result.get("orderNum"), "验证码发送成功");
				
			} else {
				return ResultManager.createFailResult(result.get("message") == null ? "验证失败！" : result.get("message").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("操作异常，验证失败！");
		}
	}

	/**
	 * 用户绑定银行卡
	 * @param uuid
	 * @param bankcard
	 * @param phone
	 * @param code
	 * @param bank
	 * @return
	 */
	@RequestMapping(value = "/bindingCard", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ActionParam(key = "bankcard", type = DataType.STRING, message="银行卡号", required = true)
	@ActionParam(key = "phone", type = DataType.STRING, message="银行预留手机号", required = true)
	@ActionParam(key = "code", type = DataType.STRING, message="验证码", required = true)
	@ActionParam(key = "bank", type = DataType.STRING, message="银行卡所属行", required = true)
	@ActionParam(key = "cardholder", type = DataType.STRING, message="持卡人", required = true)
//	@ActionParam(key = "orderNum", type = DataType.STRING, message="短信接口返回的订单编号", required = true)
	@ActionParam(key = "orderNum", type = DataType.STRING, message="短信接口返回的订单编号")
	@ActionParam(key = "token", type = DataType.STRING, message="校验参数")
	@ResponseBody
	public Result bindingCard(String uuid, String bankcard, String phone, String code, String bank, String cardholder, String orderNum, String token){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		bankcard = Base64Util.getFromBase64(bankcard);
		if(bankcard.length() < 12 || bankcard.length() > 21){
			return ResultManager.createFailResult("请填写正确的银行卡号");
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("bindingBankCard", bankcard);
		int count = this.investorBankcardService.getCount(inputParams);
		if(count > 0){
			return ResultManager.createFailResult("该银行卡已经绑定过！");
		}
		
		phone = Base64Util.getFromBase64(phone);
		if (!Utlity.isMobileNO(phone)) {
			return ResultManager.createFailResult("手机号码非法！");
		}
		code = Base64Util.getFromBase64(code);
		cardholder = Base64Util.getFromBase64(cardholder);
		
		token = Base64Util.getFromBase64(token);
//		String device = Utlity.getDeviceStr(token);
		
//		OrderinfoByThirdparty obt = this.orderinfoByThirdpartyService.get(orderNum);
//		if(obt == null){
//			return ResultManager.createFailResult("绑卡信息不存在");
//		}
		try {
//			String orderNumStr = Utlity.getOrderNumStr(device,Utlity.BILL_PAYTYPE_OHER,Utlity.BILL_PURPOSE_CHANPAY_BINDING);
//			if(this.investorAccountHistoryService.getCheckOrderNum(orderNumStr)){
//				return ResultManager.createFailResult("手速太快，服务器未响应！");
//			}
//			HashMap<String, Object> result = this.investorService.updateBindingBankcard(bank, bankcard, phone, obt.getOrderNum(), code, investor, orderNumStr);
//			if((Boolean)result.get("result")){
//				String inInfo = result.get("ibInfo").toString();
//				return ResultManager.createDataResult(inInfo, "绑定成功");
//				
//			} else {
//				return ResultManager.createFailResult(result.get("message") == null ? "验证失败！" : result.get("message").toString());
//			}
			inputParams.clear();
			inputParams.put("mobile", phone);
			inputParams.put("status", MobileCodeStatus.NORMAL);
			List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(inputParams, -1, -1, null, MobileCode.class);
			MobileCode mc = null;
			if(lstMobileCode != null && lstMobileCode.size() > 0){
				mc = (MobileCode) lstMobileCode.get(0);
			}
			String message = "";
			if(mc == null){
				message = "验证码输入错误！";
				throw new TransactionException(message);
			}
			
			if(!mc.getMobile().equals(phone)){
				message = "手机号输入错误！";
				throw new TransactionException(message);
			}
			
			if(!MobileCodeTypes.FUNDCODE.equals(mc.getType())){
				message = "验证码输入错误！";
				throw new TransactionException(message);
			}
			
			if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
				message = "验证码超时！";
				throw new TransactionException(message);
			}
			if(code.equals(mc.getCode())){
				InvestorBankcard ib = new InvestorBankcard();
				ib.setUuid(UUID.randomUUID().toString());
				ib.setBandingtime(new Timestamp(System.currentTimeMillis()));
				ib.setBank(bank);
				ib.setBankAccountName("");
				ib.setBindingBankCard(bankcard);
				ib.setBindingCardPhone(phone);
				ib.setBindingCardCardholder(investor.getRealname());
				ib.setStatus(InvestorBankcardStatus.NORMAL);
				ib.setInvestor(investor.getUuid());
				this.investorBankcardService.insert(ib);
				return ResultManager.createDataResult(ib.getUuid(), "绑定成功");
				
			} else {
				message = "验证码输入错误！";
				throw new TransactionException(message);
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("操作异常，绑定失败");
		}
	}
	
	/**
	 * 获取用户绑定的卡列表
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/getBindingCard", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ResponseBody
	public Result getBindingCard(String uuid){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("investor", uuid);
		inputParams.put("status", InvestorBankcardStatus.NORMAL);
		List<Entity> list = this.investorBankcardService.getListForPage(inputParams, -1, -1, null, InvestorBankcard.class);
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		if(list != null && !list.isEmpty()){
			for(int i = 0; i < list.size(); i++){
				Map<String, Object> result = new HashMap<String, Object>();
				InvestorBankcard ib = (InvestorBankcard) list.get(i);
				Bank bank = this.bankService.get(ib.getBank());
				if(bank == null){
					return ResultManager.createFailResult("银行不存在");
				}
				result.put("uuid", ib.getUuid());
				result.put("name", bank.getName());
				result.put("shortName", bank.getShortName());
				result.put("color", bank.getColor());
				result.put("bankcard", ib.getBindingBankCard().substring(ib.getBindingBankCard().length()-4));
				result.put("phone", Utlity.getStarMobile(ib.getBindingCardPhone()));
				Resource icon = this.resourceService.get(bank.getIcon());
				result.put("icon", icon.getUrl());
				Resource iconColor = this.resourceService.get(bank.getIconColor());
				result.put("iconColor", iconColor.getUrl());
				result.put("singleLimit", bank.getSingleLimit());
				result.put("dailyLimit", bank.getDailyLimit());
				listResult.add(result);
			}
		}
		return ResultManager.createDataResult(listResult, listResult.size());
	}
	
	/**
	 * 
	 * @param uuid
	 * @param bankcard
	 * @return
	 */
	@RequestMapping(value = "/bindingCardInfo", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ActionParam(key = "bankcard", type = DataType.STRING, message="银行卡编号", required = true)
	@ResponseBody
	public Result bindingCardInfo(String uuid, String bankcard){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		InvestorBankcard ib = this.investorBankcardService.get(bankcard);
		if(!uuid.equals(ib.getInvestor())){
			return ResultManager.createFailResult("卡信息错误！");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		Bank bank = this.bankService.get(ib.getBank());
		if(bank == null){
			return ResultManager.createFailResult("银行不存在");
		}
		result.put("uuid", ib.getUuid());
		result.put("name", bank.getName());
		result.put("shortName", bank.getShortName());
		result.put("color", bank.getColor());
		result.put("singleLimit", bank.getSingleLimit() == null ? 0 : bank.getSingleLimit());
		result.put("dailyLimit", bank.getDailyLimit() == null ? 0 : bank.getDailyLimit());
		result.put("bankcard", ib.getBindingBankCard().substring(ib.getBindingBankCard().length()-4));
		result.put("phone", Utlity.getStarMobile(ib.getBindingCardPhone()));
		Resource icon = this.resourceService.get(bank.getIcon());
		result.put("icon", icon.getUrl());
		return ResultManager.createDataResult(result, "获取成功");
	}
	
	
	/**
	 * 发送解绑银行短信验证码
	 * @param uuid
	 * @param bankcard
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/unbindSendCode", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ActionParam(key = "bankcard", type = DataType.STRING, message="银行卡编号", required = true)
//	@ActionParam(key = "phone", type = DataType.STRING, message="银行预留手机号", required = true)
	@ResponseBody
	public Result unbindSendCode(String uuid, String bankcard){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		InvestorBankcard ib = this.investorBankcardService.get(bankcard);
		if(!uuid.equals(ib.getInvestor())){
			return ResultManager.createFailResult("卡信息错误！");
		}
		//调用银行接口校验手机号、持卡人是否正确
		String codestr = Utlity.getCaptcha();
		String content = "您本次操作的验证码为："+codestr+"，请及时使用，且勿告知他人，验证码将在5分钟后失效！";
		
		String phone = investor.getMobile();
		try {
			String result = SendSms.sendSms(content, phone);
			if ("0".equals(result.split("_")[0])) {
				
				Map<String, String> inputParams = new HashMap<String, String>();
				inputParams.put("mobile", phone);
				inputParams.put("status", MobileCodeStatus.NORMAL);
				List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(inputParams, -1, -1, null, MobileCode.class);

				// 如果之前存在验证码，设置验证码失效
				if (lstMobileCode != null && lstMobileCode.size() > 0) {
					for(Entity entity: lstMobileCode){
						MobileCode code = (MobileCode)entity;
						code.setStatus(MobileCodeStatus.DISABLE);
						this.mobileCodeService.update(code);
					}
				}
				
				MobileCode code = new MobileCode();
				code.setUuid(UUID.randomUUID().toString());
				code.setCode(codestr);
				code.setContent(content);
				code.setCreatetime(new Timestamp(System.currentTimeMillis()));
				code.setCreatorType(MobileCodeCreatorType.INVESTOR);
				code.setMobile(phone);
				code.setStatus(MobileCodeStatus.NORMAL);
				code.setType(MobileCodeTypes.FUNDCODE);
				this.mobileCodeService.insert(code);
				return ResultManager.createSuccessResult("验证码发送成功");
			}
			
			return ResultManager.createFailResult("验证码发送失败！");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("操作异常，验证码发送失败！");
		}
		
	}
	
	/**
	 * 解绑银行卡
	 * @param uuid
	 * @param bankcard
	 * @return
	 */
	@RequestMapping(value = "/unbindBankcard", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
//	@ActionParam(key = "phone", type = DataType.STRING, message="银行预留手机号", required = true)
	@ActionParam(key = "code", type = DataType.STRING, message="验证码", required = true)
	@ActionParam(key = "bankcard", type = DataType.STRING, message="银行卡编号", required = true)
//	@ActionParam(key = "token", type = DataType.STRING, message="校验参数")
	@ResponseBody
	public Result unbindBankcard(String uuid, String bankcard, String code){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		InvestorBankcard ib = this.investorBankcardService.get(bankcard);
		if(!uuid.equals(ib.getInvestor())){
			return ResultManager.createFailResult("卡信息错误！");
		}
		String phone = investor.getMobile();
		if (!Utlity.isMobileNO(phone)) {
			return ResultManager.createFailResult("手机号码非法！");
		}
		code = Base64Util.getFromBase64(code);
		//验证验证码 或发送给银行接口执行操作
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("mobile", phone);
		inputParams.put("status", MobileCodeStatus.NORMAL);
		List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(inputParams, -1, -1, null, MobileCode.class);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			return ResultManager.createFailResult("验证码输入错误");
		}
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			return ResultManager.createFailResult("验证码超时请重新获取");
		}
		
		if(!MobileCodeTypes.FUNDCODE.equals(mc.getType())){
			return ResultManager.createFailResult("验证码输入错误");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("验证码输入错误");
		}
		
//		token = Base64Util.getFromBase64(token);
//		String device = Utlity.getDeviceStr(token);
		
		try {
//			String orderNumStr = Utlity.getOrderNumStr(device,Utlity.BILL_PAYTYPE_OHER,Utlity.BILL_PURPOSE_CHANPAY_BINDING);
//			if(this.investorAccountHistoryService.getCheckOrderNum(orderNumStr)){
//				return ResultManager.createFailResult("手速太快，服务器未响应！");
//			}
//			HashMap<String, Object> result = this.investorService.updateunBindingBankcard(mc, ib, investor, orderNumStr);
//			if((Boolean)result.get("result")){
//				return ResultManager.createSuccessResult("解绑成功");
//				
//			} else {
//				return ResultManager.createFailResult(result.get("message") == null ? "解绑失败！" : result.get("message").toString());
//			}
			ib.setStatus(InvestorBankcardStatus.DISABLE);
			ib.setBindingBankCard(ib.getBindingBankCard()+"_#"+ib.getUuid());
			this.investorBankcardService.update(ib);
			return ResultManager.createSuccessResult("解绑成功");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("操作异常，解绑失败！");
		}

//		ib.setStatus(InvestorBankcardStatus.DISABLE);
//		ib.setBindingBankCard(ib.getBindingBankCard()+ib.getUuid());
//		this.investorBankcardService.update(ib);
//		return ResultManager.createSuccessResult("解绑成功");
	}
	
	/**
	 * 获取账单列表
	 * 按月份
	 * @param uuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getBill", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ResponseBody
	public Result getBill(String uuid){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("investor", investor.getUuid());
		List<Entity> list = this.investorAccountHistoryService.getListForPage(inputParams, -1, -1, null, InvestorAccountHistory.class);
		List<InvestorAccountHistoryVO> listVO = new ArrayList<InvestorAccountHistoryVO>();
		for(Entity entity : list){
			InvestorAccountHistory iah = (InvestorAccountHistory)entity;
			InvestorAccountHistoryVO iahVO = new InvestorAccountHistoryVO(iah);
			listVO.add(iahVO);
		}
		
		List<Map<String, Object>> listMonth = new ArrayList<Map<String,Object>>();
		for(InvestorAccountHistoryVO vo : listVO){
			if (InvestorAccountHistoryType.BUY.equals(vo.getType()) || InvestorAccountHistoryType.DIVIDEND.equals(vo.getType()) 
					|| InvestorAccountHistoryType.RETURN.equals(vo.getType())) {
//				inputParams.clear();
//				inputParams.put("bill", vo.getUuid());
//				inputParams.put("investor", vo.getInvestor());
//				List<Entity> listProductRecords = this.investorProductBuyRecordsService.getListForPage(inputParams, -1, -1, null, InvestorProductBuyRecords.class);
//				String remark = "";
//				if(listProductRecords != null && listProductRecords.size() > 0){
//					InvestorProductBuyRecords ipbr = (InvestorProductBuyRecords) listProductRecords.get(0);
				BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(vo.getProduct());
				Bank bank = this.bankService.get(bfpp.getCustodian());
				String remark = "";
				if(bfpp != null && bank != null){
					remark = "【"+bank.getShortName()+"】"+bfpp.getShortname();
				}
				
//				}
				vo.setRemark(vo.getRemark()+remark);
			}
			if(listMonth.isEmpty()){
				Map<String, Object> data = new HashMap<String, Object>();
				List<InvestorAccountHistoryVO> childlistvo = new ArrayList<InvestorAccountHistoryVO>();
				childlistvo.add(vo);
				data.put("time", vo.getTime());
				data.put("dataList", childlistvo);
				listMonth.add(data);
			} else {
				boolean flag = false;
				for(Map<String, Object> map : listMonth){
					if(map.get("time") != null && vo.getTime().equals(map.get("time").toString())){
						flag = true;
						List<InvestorAccountHistoryVO> childlistvo = (List<InvestorAccountHistoryVO>) map.get("dataList");
						childlistvo.add(vo);
						map.put("dataList", childlistvo);
					}
				}
				if(!flag){
					Map<String, Object> data = new HashMap<String, Object>();
					List<InvestorAccountHistoryVO> childlistvo = new ArrayList<InvestorAccountHistoryVO>();
					childlistvo.add(vo);
					data.put("time", vo.getTime());
					data.put("dataList", childlistvo);
					listMonth.add(data);
				}
			}
			
		}
		return ResultManager.createDataResult(listMonth, listMonth.size());
	}
	
	/**
	 * 获取详情
	 * @param uuid
	 * @param billid
	 * @return
	 */
	@RequestMapping(value = "/getBillInfo", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ActionParam(key = "billid", type = DataType.STRING, message="账单编号", required = true)
	@ResponseBody
	public Result getBillInfo(String uuid, String billid){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		
		InvestorAccountHistory iah = this.investorAccountHistoryService.get(billid);
		if(iah == null){
			return ResultManager.createFailResult("账单信息不存在！");
		}
		if(!uuid.equals(iah.getInvestor())){
			return ResultManager.createFailResult("账单信息错误！");
		}
		InvestorAccountHistoryVO vo = new InvestorAccountHistoryVO(iah);
		if (InvestorAccountHistoryType.BUY.equals(vo.getType()) || InvestorAccountHistoryType.DIVIDEND.equals(vo.getType()) 
				|| InvestorAccountHistoryType.RETURN.equals(vo.getType())) {
//			vo.setRemark(vo.getRemark()+"【招商银行】聚宝财富季享盈1号1724期人民币理财产品");
//			Map<String, String> inputParams = new HashMap<String, String>();
//			inputParams.clear();
//			inputParams.put("bill", vo.getUuid());
//			inputParams.put("investor", vo.getInvestor());
//			List<Entity> listProductRecords = this.investorProductBuyRecordsService.getListForPage(inputParams, -1, -1, null, InvestorProductBuyRecords.class);
//			String remark = "";
//			if(listProductRecords != null && listProductRecords.size() > 0){
//				InvestorProductBuyRecords ipbr = (InvestorProductBuyRecords) listProductRecords.get(0);
//				BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(ipbr.getProduct());
//				Bank bank = this.bankService.get(bfpp.getCustodian());
//				remark = "【"+bank.getShortName()+"】"+bfpp.getShortname();
//			}
			BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(vo.getProduct());
			Bank bank = this.bankService.get(bfpp.getCustodian());
			String remark = "";
			if(bfpp != null && bank != null){
				remark = "【"+bank.getShortName()+"】"+bfpp.getShortname();
			}
			vo.setRemark(vo.getRemark()+remark);
		}
		return ResultManager.createDataResult(vo, "查询成功");
	}
	
	/**
	 * 获取授权登录签名验证信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/getAuthInfo4Ali", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "用户编号", required = true)
	@ResponseBody
	protected Result getAuthInfo4Ali(String uuid){
		//用户信息校验
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户不存在");
		}
		if(!investor.getRealnameAuthFlag()){
			return ResultManager.createFailResult("用户未实名");
		}
		
		//为APP封装支付宝的authInfo
        String authInfo = AliUtlity.getAuthInfo();
		if(Utlity.checkStringNull(authInfo)){
			return ResultManager.createFailResult("获取失败");
		}
		return ResultManager.createDataResult(authInfo, "成功");
	}
	
	/**
	 * 获取授权登录用户支付宝信息
	 * @param uuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/bindingUserInfoByAli", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "用户编号", required = true)
	@ActionParam(key = "code", type = DataType.STRING, message = "授权code", required = true)
	@ResponseBody
	protected Result bindingUserInfoByAli(String uuid, String code){
		//用户信息校验
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户不存在");
		}
		if(!investor.getRealnameAuthFlag()){
			return ResultManager.createFailResult("用户未实名");
		}
		
		//为APP封装支付宝的authInfo
//        String authInfo = AliUtlity.getAuthInfo();
		Map<String, Object> result = AliUtlity.getUserToken(code, AliUtlity.GRANT_TYPE_AUTHORIZATION_CODE);
		if((Boolean)result.get("request")){
			if((Boolean)result.get("result")){
				Map<String, Object> responseMap = (Map<String, Object>) result.get("response");
				String accessToken = responseMap.get("access_token") == null ? "" : responseMap.get("access_token").toString();
				if("".equals(accessToken)){
					return ResultManager.createFailResult("支付宝鉴权登录失败，绑定失败！");
				}
				//判断支付宝用户是否已绑定过
				String userid = responseMap.get("user_id") == null ? "" : responseMap.get("user_id").toString();
				if("".equals(userid)){
					return ResultManager.createFailResult("支付宝鉴权登录失败，绑定失败！");
				}
				investor.setAliUserid(userid);
				
				Map<String, Object> resultBinding = this.investorService.updateBindingAliUserInfo(investor, accessToken);
				if((Boolean)resultBinding.get("result")){
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("uuid", investor.getUuid());
					dataMap.put("aliUserid", resultBinding.get("aliUserid"));
					dataMap.put("aliNickname", resultBinding.get("aliNickname"));
					return ResultManager.createDataResult(dataMap, "绑定成功！");
				} else {
					return ResultManager.createFailResult(result.get("message") == null ? "绑定失败！" : result.get("message").toString());
				}
			} else {
				return ResultManager.createFailResult(result.get("message") == null ? "绑定失败！" : result.get("message").toString());
			}
		} else {
			return ResultManager.createFailResult("绑定失败！");
		}
		
	}

	/**
	 * 设置密码
	 * @param uuid
	 * @param encrypt 规则Base64(timestamps+md5(pwd)+MD5(key+time+md5(pwd)+md5(原密码)))
	 * @return
	 */
	@RequestMapping(value = "/resetpwd", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ActionParam(key = "encrypt", type = DataType.STRING, message="用户密码加密信息", required = true)
	@ResponseBody
	public Result resetpwd(String uuid, String encrypt){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		try {
			String message = "密码设置失败";
			encrypt = Base64Util.getFromBase64(encrypt);
			
			String timestamp = encrypt.substring(0,13);
			if(timestamp == null || "".equals(timestamp)){
				return ResultManager.createFailResult(message);
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkTime(time, nowTime)){
					message = message+"时间超时";
					return ResultManager.createFailResult(message);
				}
			} else {
				message = message+"时间超时";
				return ResultManager.createFailResult(message);
			}
			String pwd = encrypt.substring(13, 45);
			if(pwd == null || "".equals(pwd)){
				message = "密码设置失败，请检查新密码";
				return ResultManager.createFailResult(message);
			}
			
			String md5Str = encrypt.substring(45);
			if(md5Str == null || "".equals(md5Str)){
				return ResultManager.createFailResult(message);
			}
			
			String realMD5Str = "";
			if(!Utlity.checkStringNull(investor.getLoginPassword())){
				String repwd = investor.getLoginPassword();
				message = "密码设置失败，原密码错误！";
				realMD5Str = MD5.getMD5(Utlity.KEY+time+pwd+repwd);
			} else {
				message = "密码设置失败，验证信息错误！";
				realMD5Str = MD5.getMD5(Utlity.KEY+time+pwd);
			}
			
			if(realMD5Str.equals(md5Str)){//成功
				investor.setLoginPassword(pwd);
				this.investorService.update(investor);
				return ResultManager.createSuccessResult("密码设置成功");
			} else {
				return ResultManager.createFailResult(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("操作异常，解绑失败！");
		}
		
	}
}
