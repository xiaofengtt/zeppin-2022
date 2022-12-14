package cn.zeppin.product.ntb.shbx.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IAliCertificationService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkAreaService;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.ShbxInsured;
import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder;
import cn.zeppin.product.ntb.core.entity.ShbxUser;
import cn.zeppin.product.ntb.core.entity.ShbxUserBankcard;
import cn.zeppin.product.ntb.core.entity.ShbxUserBankcard.ShbxUserBankcardStatus;
import cn.zeppin.product.ntb.core.entity.ShbxUserBankcard.ShbxUserBankcardType;
import cn.zeppin.product.ntb.core.entity.ShbxUserHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.shbx.service.api.IShbxInsuredService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxOrderinfoByThirdpartyService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxSecurityOrderService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserBankcardService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserHistoryService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserService;
import cn.zeppin.product.ntb.shbx.vo.BankVO;
import cn.zeppin.product.ntb.shbx.vo.ShbxSecurityOrderInfoDetailVO;
import cn.zeppin.product.ntb.shbx.vo.ShbxSecurityOrderInfoVO;
import cn.zeppin.product.ntb.shbx.vo.ShbxUserVO;
import cn.zeppin.product.utility.BankCardUtlity;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.SendSmsNew;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.reapal.ReapalUtil;
import cn.zeppin.product.utility.reapal.ReapalUtlity;

@Controller
@RequestMapping(value = "/shbxWeb/user")
public class ShbxWebUserController extends BaseController{
	
	@Autowired
	private IShbxUserService shbxUserService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IShbxUserBankcardService shbxUserBankcardService;
	
	@Autowired
	private IShbxUserHistoryService shbxUserHistoryService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IAliCertificationService aliCertificationService;
	
	@Autowired
	private IShbxOrderinfoByThirdpartyService shbxOrderinfoByThirdpartyService;
	
	@Autowired
	private IShbxSecurityOrderService shbxSecurityOrderService;
	
	@Autowired
	private IShbxInsuredService shbxInsuredService;
	
	@Autowired
	private IBkAreaService bkAreaService;
	
	/**
	 * ????????????
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/certification", method = RequestMethod.POST)
	@ActionParam(key = "name", type = DataType.STRING, message="????????????", required = true)
	@ActionParam(key = "idcard", type = DataType.STRING, message="??????????????????", required = true)
	@ResponseBody
	public Result certification(String uuid, String name, String idcard){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShbxUser shbxUser = (ShbxUser) session.getAttribute("shbxUser");
		
		if(shbxUser == null){
			return ResultManager.createFailResult("???????????????????????????????????????");
		}
		name = Base64Util.getFromBase64(name);
		idcard = Base64Util.getFromBase64(idcard);
		idcard = idcard.toUpperCase();
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("idcard", idcard);
		Integer count = this.shbxUserService.getCount(inputParams);
		if(count > 0){
			return ResultManager.createFailResult("????????????????????????????????????????????????????????????");
		}
		try {
			this.shbxUserService.logincertification(idcard, name, shbxUser);
			return ResultManager.createSuccessResult("?????????????????????");
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("?????????????????????????????????????????????????????????");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("????????????????????????????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Result get(){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShbxUser shbxUser = (ShbxUser) session.getAttribute("shbxUser");
		
		ShbxUserVO ivo = new ShbxUserVO(shbxUser);
		//????????????????????????????????????????????????????????????
		try {
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("shbxUser", shbxUser.getUuid());
			inputParams.put("status", ShbxUserBankcardStatus.NORMAL);
			Integer count = this.shbxUserBankcardService.getCount(inputParams);
			ivo.setBankcardCount(count == null ? "0" : String.valueOf(count));
			
			return ResultManager.createDataResult(ivo, "????????????");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("???????????????????????????");
		}
	}
	
	/**
	 * ???????????????????????????
	 * @param bankcard
	 * @return
	 */
	@RequestMapping(value = "/bindingCheckCard", method = RequestMethod.GET)
	@ActionParam(key = "bankcard", type = DataType.STRING, message="????????????", required = true)
	@ResponseBody
	public Result bindingCheckCard(String bankcard){
		bankcard = Base64Util.getFromBase64(bankcard);
		if(bankcard.length() < 12 || bankcard.length() > 21){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		boolean flag = BankCardUtlity.checkBankCard(bankcard);
		if(!flag){
			return ResultManager.createErrorResult("405", "??????????????????????????????");
		}
		
		String bankName = BankCardUtlity.getname(bankcard);
		if(bankName == null){
			return ResultManager.createErrorResult("405", "??????????????????????????????");
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
			return ResultManager.createDataResult(result, "????????????");
		} else {
			return ResultManager.createErrorResult("405", "???????????????????????????");
		}
	}
	
	/**
	 * ???????????????????????????
	 * @param uuid
	 * @param bankcard
	 * @param phone
	 * @param bank
	 * @param cardholder
	 * @return
	 */
	@RequestMapping(value = "/bindingSendCode", method = RequestMethod.POST)
	@ActionParam(key = "bankcard", type = DataType.STRING, message="????????????", required = true)
	@ActionParam(key = "phone", type = DataType.STRING, message="?????????????????????", required = true)
	@ActionParam(key = "bank", type = DataType.STRING, message="??????????????????", required = true)
//	@ActionParam(key = "cardholder", type = DataType.STRING, message="?????????", required = true)
	@ResponseBody
	public Result bindingSendCode(String bankcard, String phone, String bank){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShbxUser shbxUser = (ShbxUser) session.getAttribute("shbxUser");
		if(shbxUser == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		String cardholder = shbxUser.getRealname();
		bankcard = Base64Util.getFromBase64(bankcard);
		if(bankcard.length() < 12 || bankcard.length() > 21){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		boolean flag = BankCardUtlity.checkBankCard(bankcard);
		if(!flag){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		String bankName = BankCardUtlity.getname(bankcard);
		Bank bankInfo = this.bankService.get(bank);
		if(bankInfo == null){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		if (bankName != null && !"".equals(bankName)) {
			if((bankName.indexOf(bankInfo.getName()) == -1) && (bankInfo.getName().indexOf(bankName) == -1 )
					&& (bankName.indexOf(bankInfo.getShortName()) == -1) && (bankInfo.getShortName().indexOf(bankName) == -1 )){
				return ResultManager.createFailResult("????????????????????????????????????");
			}
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("bindingBankCard", bankcard);
		int count = this.shbxUserBankcardService.getCount(inputParams);
		if(count > 0){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		phone = Base64Util.getFromBase64(phone);
		if (!Utlity.isMobileNO(phone)) {
			return ResultManager.createFailResult("?????????????????????");
		}
//		cardholder = Base64Util.getFromBase64(cardholder);
//		token = Base64Util.getFromBase64(token);
//		String deviceNumber = token.substring(0,2);
//		String device = "";
//		if(Utlity.DEVICE_NUMBER_WECHAT.equals(deviceNumber)){
//			device = Utlity.BILL_DEVICE_WECHAT;
//		} else if (Utlity.DEVICE_NUMBER_ANDROID.equals(deviceNumber)) {
//			device = Utlity.BILL_DEVICE_ANDROID;
//		} else if (Utlity.DEVICE_NUMBER_IOS.equals(deviceNumber)) {
//			device = Utlity.BILL_DEVICE_IOS;
//		} else if (Utlity.DEVICE_NUMBER_WEB.equals(deviceNumber)) {
//			device = Utlity.BILL_DEVICE_WEB;
//		}
		String device = Utlity.BILL_DEVICE_SHBX;
		String orderNumStr = Utlity.getOrderNumStr(device,Utlity.BILL_PAYTYPE_OHER,Utlity.BILL_PURPOSE_CHANPAY_BINDING);
		if(this.shbxUserHistoryService.checkOrderNum(orderNumStr)){
			return ResultManager.createFailResult("????????????????????????????????????");
		}
		HashMap<String, Object> result = null;
		//??????????????????????????????????????????????????????????????????????????????
		try {
			//????????????????????????????????????????????? ?????????????????????????????????/?????????
			Map<String, Object> cardResult = ReapalUtlity.bankCardQuery(bankcard);
			if(cardResult != null && cardResult.containsKey("bank_card_type")){
				String cardType = cardResult.get("bank_card_type").toString();
				if(!ReapalUtil.card_type_deposit.equals(cardType)){
					return ResultManager.createFailResult("??????????????????/??????????????????????????????");
				}
			}
			
			result = this.shbxUserService.updateBindingBankcard(phone, bankcard, shbxUser.getIdcard(), cardholder, shbxUser, orderNumStr);
			if((Boolean)result.get("result")){
				//?????????????????????????????????????????????????????????
//				ChanPayUtil.nmg_biz_api_auth_req(shbxUser.getIdcard(), cardholder, phone, bankcard);
				return ResultManager.createDataResult(result.get("orderNum"), "?????????????????????");
				
			} else {
				return ResultManager.createFailResult(result.get("message") == null ? "???????????????" : result.get("message").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("??????????????????????????????");
		}
	}

	/**
	 * ?????????????????????
	 * @param uuid
	 * @param bankcard
	 * @param phone
	 * @param code
	 * @param bank
	 * @return
	 */
	@RequestMapping(value = "/bindingCard", method = RequestMethod.POST)
	@ActionParam(key = "bankcard", type = DataType.STRING, message="????????????", required = true)
	@ActionParam(key = "phone", type = DataType.STRING, message="?????????????????????", required = true)
	@ActionParam(key = "code", type = DataType.STRING, message="?????????", required = true)
	@ActionParam(key = "bank", type = DataType.STRING, message="??????????????????", required = true)
//	@ActionParam(key = "cardholder", type = DataType.STRING, message="?????????", required = true)
	@ActionParam(key = "orderNum", type = DataType.STRING, message="?????????????????????????????????")
	@ResponseBody
	public Result bindingCard(String bankcard, String phone, String code, String bank, String orderNum){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShbxUser shbxUser = (ShbxUser) session.getAttribute("shbxUser");
		if(shbxUser == null){
			return ResultManager.createFailResult("????????????????????????");
		}
//		String cardholder = shbxUser.getRealname();
		bankcard = Base64Util.getFromBase64(bankcard);
		if(bankcard.length() < 12 || bankcard.length() > 21){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("bindingBankCard", bankcard);
		int count = this.shbxUserBankcardService.getCount(inputParams);
		if(count > 0){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		phone = Base64Util.getFromBase64(phone);
		if (!Utlity.isMobileNO(phone)) {
			return ResultManager.createFailResult("?????????????????????");
		}
		code = Base64Util.getFromBase64(code);
//		cardholder = Base64Util.getFromBase64(cardholder);
		
//		token = Base64Util.getFromBase64(token);
//		String device = Utlity.getDeviceStr(token);
		
//		OrderinfoByThirdparty obt = this.orderinfoByThirdpartyService.get(orderNum);
//		if(obt == null){
//			return ResultManager.createFailResult("?????????????????????");
//		}
		try {
//			String orderNumStr = Utlity.getOrderNumStr(device,Utlity.BILL_PAYTYPE_OHER,Utlity.BILL_PURPOSE_CHANPAY_BINDING);
//			if(this.shbxUserHistoryService.getCheckOrderNum(orderNumStr)){
//				return ResultManager.createFailResult("????????????????????????????????????");
//			}
//			HashMap<String, Object> result = this.shbxUserService.updateBindingBankcard(bank, bankcard, phone, obt.getOrderNum(), code, shbxUser, orderNumStr);
//			if((Boolean)result.get("result")){
//				String inInfo = result.get("ibInfo").toString();
//				return ResultManager.createDataResult(inInfo, "????????????");
//				
//			} else {
//				return ResultManager.createFailResult(result.get("message") == null ? "???????????????" : result.get("message").toString());
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
				message = "????????????????????????";
				throw new TransactionException(message);
			}
			
			if(!mc.getMobile().equals(phone)){
				message = "????????????????????????";
				throw new TransactionException(message);
			}
			
			if(!MobileCodeTypes.FUNDCODE.equals(mc.getType())){
				message = "????????????????????????";
				throw new TransactionException(message);
			}
			
			if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
				message = "??????????????????";
				throw new TransactionException(message);
			}
			if(code.equals(mc.getCode())){
				ShbxUserBankcard ib = new ShbxUserBankcard();
				ib.setUuid(UUID.randomUUID().toString());
				ib.setCreatetime(new Timestamp(System.currentTimeMillis()));
				ib.setBank(bank);
				ib.setBankAccountName("");
				ib.setBindingBankCard(bankcard);
				ib.setBindingCardPhone(phone);
				ib.setBindingCardCardholder(shbxUser.getRealname());
				ib.setStatus(ShbxUserBankcardStatus.NORMAL);
				ib.setShbxUser(shbxUser.getUuid());
				ib.setType(ShbxUserBankcardType.DEBIT);
				this.shbxUserBankcardService.insert(ib);
				return ResultManager.createDataResult(ib.getUuid(), "????????????");
				
			} else {
				message = "????????????????????????";
				throw new TransactionException(message);
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("???????????????????????????");
		}
	}
	
	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/getBindingCard", method = RequestMethod.GET)
	@ResponseBody
	public Result getBindingCard(){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShbxUser shbxUser = (ShbxUser) session.getAttribute("shbxUser");
		if(shbxUser == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("shbxUser", shbxUser.getUuid());
		inputParams.put("status", ShbxUserBankcardStatus.NORMAL);
		List<Entity> list = this.shbxUserBankcardService.getListForPage(inputParams, -1, -1, null, ShbxUserBankcard.class);
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		if(list != null && !list.isEmpty()){
			for(int i = 0; i < list.size(); i++){
				Map<String, Object> result = new HashMap<String, Object>();
				ShbxUserBankcard ib = (ShbxUserBankcard) list.get(i);
				Bank bank = this.bankService.get(ib.getBank());
				if(bank == null){
					return ResultManager.createFailResult("???????????????");
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
	 * ?????????????????????
	 * @param uuid
	 * @param bankcard
	 * @return
	 */
	@RequestMapping(value = "/bindingCardInfo", method = RequestMethod.GET)
	@ActionParam(key = "bankcard", type = DataType.STRING, message="???????????????", required = true)
	@ResponseBody
	public Result bindingCardInfo(String bankcard){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShbxUser shbxUser = (ShbxUser) session.getAttribute("shbxUser");
		if(shbxUser == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		ShbxUserBankcard ib = this.shbxUserBankcardService.get(bankcard);
		if(!shbxUser.getUuid().equals(ib.getShbxUser())){
			return ResultManager.createFailResult("??????????????????");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		Bank bank = this.bankService.get(ib.getBank());
		if(bank == null){
			return ResultManager.createFailResult("???????????????");
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
		return ResultManager.createDataResult(result, "????????????");
	}
	
	/**
	 * ?????????????????????????????????
	 * @param uuid
	 * @param bankcard
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/unbindSendCode", method = RequestMethod.POST)
	@ActionParam(key = "bankcard", type = DataType.STRING, message="???????????????", required = true)
//	@ActionParam(key = "phone", type = DataType.STRING, message="?????????????????????", required = true)
	@ResponseBody
	public Result unbindSendCode(String bankcard){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShbxUser shbxUser = (ShbxUser) session.getAttribute("shbxUser");
		if(shbxUser == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		ShbxUserBankcard ib = this.shbxUserBankcardService.get(bankcard);
		if(!shbxUser.getUuid().equals(ib.getShbxUser())){
			return ResultManager.createFailResult("??????????????????");
		}
		//?????????????????????????????????????????????????????????
		String codestr = Utlity.getCaptcha();
		String content = "?????????????????????????????????"+codestr+"?????????????????????????????????????????????????????????5??????????????????";
		
		String phone = shbxUser.getMobile();
		try {
			String result = SendSmsNew.sendSms4Shbx(content, phone);
			if ("0".equals(result.split("_")[0])) {
				
				Map<String, String> inputParams = new HashMap<String, String>();
				inputParams.put("mobile", phone);
				inputParams.put("status", MobileCodeStatus.NORMAL);
				List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(inputParams, -1, -1, null, MobileCode.class);

				// ???????????????????????????????????????????????????
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
				return ResultManager.createSuccessResult("?????????????????????");
			}
			
			return ResultManager.createFailResult("????????????????????????");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("???????????????????????????????????????");
		}
		
	}
	
	/**
	 * ???????????????
	 * @param uuid
	 * @param bankcard
	 * @return
	 */
	@RequestMapping(value = "/unbindBankcard", method = RequestMethod.POST)
//	@ActionParam(key = "phone", type = DataType.STRING, message="?????????????????????", required = true)
	@ActionParam(key = "code", type = DataType.STRING, message="?????????", required = true)
	@ActionParam(key = "bankcard", type = DataType.STRING, message="???????????????", required = true)
	@ResponseBody
	public Result unbindBankcard(String bankcard, String code){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShbxUser shbxUser = (ShbxUser) session.getAttribute("shbxUser");
		if(shbxUser == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		ShbxUserBankcard ib = this.shbxUserBankcardService.get(bankcard);
		if(!shbxUser.getUuid().equals(ib.getShbxUser())){
			return ResultManager.createFailResult("??????????????????");
		}
		String phone = shbxUser.getMobile();
		if (!Utlity.isMobileNO(phone)) {
			return ResultManager.createFailResult("?????????????????????");
		}
		code = Base64Util.getFromBase64(code);
		//??????????????? ????????????????????????????????????
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("mobile", phone);
		inputParams.put("status", MobileCodeStatus.NORMAL);
		List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(inputParams, -1, -1, null, MobileCode.class);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			return ResultManager.createFailResult("?????????????????????");
		}
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		if(!MobileCodeTypes.FUNDCODE.equals(mc.getType())){
			return ResultManager.createFailResult("?????????????????????");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("?????????????????????");
		}
		
//		token = Base64Util.getFromBase64(token);
//		String device = Utlity.getDeviceStr(token);
		
		try {
//			String orderNumStr = Utlity.getOrderNumStr(device,Utlity.BILL_PAYTYPE_OHER,Utlity.BILL_PURPOSE_CHANPAY_BINDING);
//			if(this.shbxUserHistoryService.getCheckOrderNum(orderNumStr)){
//				return ResultManager.createFailResult("????????????????????????????????????");
//			}
//			HashMap<String, Object> result = this.shbxUserService.updateunBindingBankcard(mc, ib, shbxUser, orderNumStr);
//			if((Boolean)result.get("result")){
//				return ResultManager.createSuccessResult("????????????");
//				
//			} else {
//				return ResultManager.createFailResult(result.get("message") == null ? "???????????????" : result.get("message").toString());
//			}
			ib.setStatus(ShbxUserBankcardStatus.DISABLE);
			ib.setBindingBankCard(ib.getBindingBankCard()+"_#"+ib.getUuid());
			this.shbxUserBankcardService.update(ib);
			return ResultManager.createSuccessResult("????????????");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("??????????????????????????????");
		}

//		ib.setStatus(ShbxUserBankcardStatus.DISABLE);
//		ib.setBindingBankCard(ib.getBindingBankCard()+ib.getUuid());
//		this.shbxUserBankcardService.update(ib);
//		return ResultManager.createSuccessResult("????????????");
	}
	
	/**
	 * ??????????????????
	 * ?????????
	 * @param uuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getShbxBill", method = RequestMethod.GET)
	@ResponseBody
	public Result getShbxBill(){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShbxUser shbxUser = (ShbxUser) session.getAttribute("shbxUser");
		if(shbxUser == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("shbxUser", shbxUser.getUuid());
		List<Entity> list = this.shbxSecurityOrderService.getListForPage(inputParams, -1, -1, null, ShbxSecurityOrder.class);
		List<ShbxSecurityOrderInfoVO> listResult = new ArrayList<ShbxSecurityOrderInfoVO>();
		for(Entity entity : list){
			ShbxSecurityOrder iah = (ShbxSecurityOrder)entity;
			ShbxSecurityOrderInfoVO iahVO = new ShbxSecurityOrderInfoVO(iah);
			listResult.add(iahVO);
		}
		
		List<Map<String, Object>> listMonth = new ArrayList<Map<String,Object>>();
		for(ShbxSecurityOrderInfoVO vo : listResult){
//			if (ShbxUserHistoryType.BUY.equals(vo.getType()) || ShbxUserHistoryType.DIVIDEND.equals(vo.getType()) 
//					|| ShbxUserHistoryType.RETURN.equals(vo.getType())) {
//				BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(vo.getProduct());
//				Bank bank = this.bankService.get(bfpp.getCustodian());
//				String remark = "";
//				if(bfpp != null && bank != null){
//					remark = "???"+bank.getShortName()+"???"+bfpp.getShortname();
//				}
//				
////				}
//				vo.setRemark(vo.getRemark()+remark);
//			}
			
			//?????????
			if(!Utlity.checkStringNull(vo.getShbxUserHistory())){
				ShbxUserHistory suh = this.shbxUserHistoryService.get(vo.getShbxUserHistory());
				if(suh != null){
					vo.setTotalAmount(suh.getPay());
					vo.setTotalAmountCN(Utlity.numFormat4UnitDetail(suh.getPay()));
				}
			}
			
			//???????????????
			ShbxInsured si = this.shbxInsuredService.get(vo.getShbxInsured());
			if(si != null){
				vo.setShbxInsuredName(si.getName());
			}
			
			if(listMonth.isEmpty()){
				Map<String, Object> data = new HashMap<String, Object>();
				List<ShbxSecurityOrderInfoVO> childlistvo = new ArrayList<ShbxSecurityOrderInfoVO>();
				childlistvo.add(vo);
				data.put("time", vo.getTime());
				data.put("dataList", childlistvo);
				listMonth.add(data);
			} else {
				boolean flag = false;
				for(Map<String, Object> map : listMonth){
					if(map.get("time") != null && vo.getTime().equals(map.get("time").toString())){
						flag = true;
						List<ShbxSecurityOrderInfoVO> childlistvo = (List<ShbxSecurityOrderInfoVO>) map.get("dataList");
						childlistvo.add(vo);
						map.put("dataList", childlistvo);
					}
				}
				if(!flag){
					Map<String, Object> data = new HashMap<String, Object>();
					List<ShbxSecurityOrderInfoVO> childlistvo = new ArrayList<ShbxSecurityOrderInfoVO>();
					childlistvo.add(vo);
					data.put("time", vo.getTime());
					data.put("dataList", childlistvo);
					listMonth.add(data);
				}
			}
			
		}
		return ResultManager.createDataResult(listMonth, list.size());
	}
	
	/**
	 * ????????????
	 * @param uuid
	 * @param billid
	 * @return
	 */
	@RequestMapping(value = "/getBillInfo", method = RequestMethod.GET)
	@ActionParam(key = "billid", type = DataType.STRING, message="????????????", required = true)
	@ResponseBody
	public Result getBillInfo(String billid){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShbxUser shbxUser = (ShbxUser) session.getAttribute("shbxUser");
		if(shbxUser == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		ShbxSecurityOrder sso = this.shbxSecurityOrderService.get(billid);
		if (sso == null) {
			return ResultManager.createFailResult("??????????????????????????????");
		}
		if(!shbxUser.getUuid().equals(sso.getCreator())){
			return ResultManager.createFailResult("???????????????????????????");
		}
		ShbxSecurityOrderInfoDetailVO vo = new ShbxSecurityOrderInfoDetailVO(sso);
		//?????????
		if(!Utlity.checkStringNull(sso.getShbxUserHistory())){
			ShbxUserHistory suh = this.shbxUserHistoryService.get(sso.getShbxUserHistory());
			if(suh != null){
				vo.setTotalAmount(suh.getPay());
				vo.setTotalAmountCN(Utlity.numFormat4UnitDetail(suh.getPay()));
			}
		}
		
		//????????????
		ShbxUser su = this.shbxUserService.get(sso.getCreator());
		if(su != null){
			vo.setCreatorName(su.getRealname());
		}
		
		//???????????????
		ShbxInsured si = this.shbxInsuredService.get(sso.getShbxInsured());
		if(si != null){
			vo.setShbxInsuredName(si.getName());
			vo.setShbxInsuredEducation(si.getEducation());
			vo.setShbxInsuredEmail(si.getEmail());
			vo.setShbxInsuredHouseholdarea(si.getHouseholdarea());
			if(si.getHouseholdarea() != null){
				List<String> areaNames = this.bkAreaService.getFullName(si.getHouseholdarea());
				if(areaNames != null && areaNames.size() >= 2){
					vo.setShbxInsuredHouseholdareaCN(areaNames.get(0) + " " + areaNames.get(1));
				}
			}
			vo.setShbxInsuredHouseholdtype(si.getHouseholdtype());
			vo.setShbxInsuredIdcard(si.getIdcard());
			vo.setShbxInsuredMobile(si.getMobile());
			vo.setShbxInsuredNationality(si.getNationality());
			vo.setWorktime(si.getWorktime());
		}
		
//		//???????????????
//		if(!Utlity.checkStringNull(sso.getProcessCreator())){
//			BkOperator op = this.bkOperatorService.get(sso.getProcessCreator());
//			if(op != null){
//				vo.setProcessCreatorName(op.getRealname());
//			}
//		}
		
		//????????????
		if(!Utlity.checkStringNull(sso.getReceipt())){
			String[] recArr = sso.getReceipt().split(",");
			List<Object> listReceipt = new ArrayList<Object>();
			for(String str : recArr){
				Resource res = this.resourceService.get(str);
				if(res != null){
					listReceipt.add(res);
				}
			}
			vo.setReceipts(listReceipt);		
		}
		return ResultManager.createDataResult(vo, "????????????");
	}

	/**
	 * ????????????
	 * @param encrypt ??????Base64(timestamps+md5(pwd)+MD5(key+time+md5(pwd)+md5(?????????)))
	 * @return
	 */
	@RequestMapping(value = "/resetpwd", method = RequestMethod.POST)
	@ActionParam(key = "encrypt", type = DataType.STRING, message="????????????????????????", required = true)
	@ResponseBody
	public Result resetpwd(String encrypt){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShbxUser shbxUser = (ShbxUser) session.getAttribute("shbxUser");
		if(shbxUser == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		try {
			String message = "??????????????????";
			encrypt = Base64Util.getFromBase64(encrypt);
			
			String timestamp = encrypt.substring(0,13);
			if(timestamp == null || "".equals(timestamp)){
				return ResultManager.createFailResult(message);
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkTime(time, nowTime)){
					message = message+"????????????";
					return ResultManager.createFailResult(message);
				}
			} else {
				message = message+"????????????";
				return ResultManager.createFailResult(message);
			}
			String pwd = encrypt.substring(13, 45);
			if(pwd == null || "".equals(pwd)){
				message = "???????????????????????????????????????";
				return ResultManager.createFailResult(message);
			}
			
			String md5Str = encrypt.substring(45);
			if(md5Str == null || "".equals(md5Str)){
				return ResultManager.createFailResult(message);
			}
			String repwd = shbxUser.getLoginPassword() == null ? "" : shbxUser.getLoginPassword();
			String realMD5Str = MD5.getMD5(Utlity.KEY_QCB+time+pwd+repwd);
			
			if(realMD5Str.equals(md5Str)){//??????
				shbxUser.setLoginPassword(pwd);
				this.shbxUserService.update(shbxUser);
				return ResultManager.createSuccessResult("??????????????????");
			} else {
				return ResultManager.createFailResult(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("??????????????????????????????");
		}
	}
}
