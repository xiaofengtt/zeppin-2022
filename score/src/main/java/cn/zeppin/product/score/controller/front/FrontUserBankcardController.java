/**
 * 
 */
package cn.zeppin.product.score.controller.front;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.score.controller.base.ActionParam;
import cn.zeppin.product.score.controller.base.ActionParam.DataType;
import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.controller.base.TransactionException;
import cn.zeppin.product.score.entity.Bank;
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.FrontUserBankcard;
import cn.zeppin.product.score.entity.FrontUserBankcard.FrontUserBankcardStatus;
import cn.zeppin.product.score.entity.FrontUserBankcard.FrontUserBankcardType;
import cn.zeppin.product.score.entity.MobileCode;
import cn.zeppin.product.score.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.score.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.service.BankService;
import cn.zeppin.product.score.service.FrontUserBankcardService;
import cn.zeppin.product.score.service.MobileCodeService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.util.Base64Util;
import cn.zeppin.product.score.util.Utlity;


/**
 * 用户绑卡
 */

@Controller
@RequestMapping(value = "/front/bankcard")
public class FrontUserBankcardController extends BaseController {
	
	@Autowired
	private FrontUserBankcardService frontUserBankcardService;
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private MobileCodeService mobileCodeService;
	
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 获取用户绑定的卡列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Result list(HttpServletRequest request) {
		FrontUser fu = getFrontUser(request);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("frontUser", fu.getUuid());
		params.put("status", FrontUserBankcardStatus.NORMAL);
		params.put("type", FrontUserBankcardType.DEBIT);
		List<FrontUserBankcard> list = this.frontUserBankcardService.getListByParams(params);
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		if(list != null && !list.isEmpty()){
			for(FrontUserBankcard fub : list){
				Map<String, Object> result = new HashMap<String, Object>();
				Bank bank = this.bankService.get(fub.getBank());
				if(bank == null){
					return ResultManager.createFailResult("银行不存在");
				}
				result.put("uuid", fub.getUuid());
				result.put("name", bank.getName());
				result.put("shortName", bank.getShortName());
				result.put("bankcard", fub.getAccountNumber().substring(fub.getAccountNumber().length()-4));
				result.put("phone", Utlity.getStarMobile(fub.getPhone()));
				Resource logo = this.resourceService.get(bank.getLogo());
				if(logo != null) {
					result.put("logoUrl", logo.getUrl());
				} else {
					result.put("logoUrl", "");
				}
				listResult.add(result);
			}
		}
		return ResultManager.createDataResult(listResult, listResult.size());
	}
	
	/**
	 * 获取用户绑定的卡
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="银行卡编号", required = true)
	@ResponseBody
	public Result get(String uuid, HttpServletRequest request){
		FrontUser fu = getFrontUser(request);
		
		FrontUserBankcard fub = this.frontUserBankcardService.get(uuid);
		if(!fu.getUuid().equals(fub.getFrontUser())){
			return ResultManager.createFailResult("卡信息错误！");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		Bank bank = this.bankService.get(fub.getBank());
		if(bank == null){
			return ResultManager.createFailResult("银行不存在");
		}
		result.put("uuid", fub.getUuid());
		result.put("name", bank.getName());
		result.put("shortName", bank.getShortName());
		result.put("bankcard", fub.getAccountNumber().substring(fub.getAccountNumber().length()-4));
		result.put("phone", Utlity.getStarMobile(fub.getPhone()));
		Resource logo = this.resourceService.get(bank.getLogo());
		if(logo != null) {
			result.put("logoUrl", logo.getUrl());
		} else {
			result.put("logoUrl", "");
		}
		return ResultManager.createDataResult(result, "获取成功");
	}
	
	/**
	 * 用户绑定银行卡
	 * @param bankcard
	 * @param phone
	 * @param code
	 * @param bank
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "bankcard", type = DataType.STRING, message="银行卡号", required = true)
	@ActionParam(key = "phone", type = DataType.STRING, message="银行预留手机号", required = true)
	@ActionParam(key = "mobileCode", type = DataType.STRING, message="验证码", required = true)
	@ActionParam(key = "bank", type = DataType.STRING, message="银行卡所属行", required = true)
	@ResponseBody
	public Result add(String bankcard, String phone, String mobileCode, String bank, HttpServletRequest request){
		FrontUser fu = getFrontUser(request);
		
		bankcard = Base64Util.getFromBase64(bankcard);
		if(bankcard.length() < 12 || bankcard.length() > 21){
			return ResultManager.createFailResult("请填写正确的银行卡号");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNumber", bankcard);
		params.put("type", FrontUserBankcardType.DEBIT);
		int count = this.frontUserBankcardService.getCountByParams(params);
		if(count > 0){
			return ResultManager.createFailResult("该银行卡已经绑定过！");
		}
		
		phone = Base64Util.getFromBase64(phone);
		if (!Utlity.isMobileNO(phone)) {
			return ResultManager.createFailResult("手机号码非法！");
		}
		mobileCode = Base64Util.getFromBase64(mobileCode);
		
		try {
			params.clear();
			params.put("mobile", phone);
			params.put("type", MobileCodeTypes.FUNDCODE);
			params.put("status", MobileCodeStatus.NORMAL);
			List<MobileCode> lstMobileCode = this.mobileCodeService.getListByParams(params);
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
			if(mobileCode.equals(mc.getCode())){
				FrontUserBankcard fub = new FrontUserBankcard();
				fub.setUuid(UUID.randomUUID().toString());
				fub.setCreatetime(new Timestamp(System.currentTimeMillis()));
				fub.setBank(bank);
				fub.setAccountNumber(bankcard);
				fub.setPhone(phone);
				fub.setAccountHolder(fu.getRealname());
				fub.setStatus(FrontUserBankcardStatus.NORMAL);
				fub.setFrontUser(fu.getUuid());
				fub.setType(FrontUserBankcardType.DEBIT);
				
				mc.setStatus(MobileCodeStatus.DISABLE);
				this.frontUserBankcardService.insertFrontUserBankcard(fub, mc);				
				return ResultManager.createDataResult(fub.getUuid(), "绑定成功");
				
			} else {
				message = "验证码输入错误！";
				throw new TransactionException(message);
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			return ResultManager.createFailResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("操作异常，绑定失败");
		}
	}
	
	/**
	 * 解绑银行卡
	 * @param bankcard
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="银行卡编号", required = true)
	@ResponseBody
	public Result delete(String uuid, HttpServletRequest request){
		FrontUser fu = getFrontUser(request);
		
		FrontUserBankcard fub = this.frontUserBankcardService.get(uuid);
		if(!fu.getUuid().equals(fub.getFrontUser())){
			return ResultManager.createFailResult("卡信息错误！");
		}
		
		fub.setStatus(FrontUserBankcardStatus.DELETE);
		fub.setAccountNumber(fub.getAccountNumber()+"_#"+fub.getUuid());
		
		this.frontUserBankcardService.update(fub);
		return ResultManager.createSuccessResult("解绑成功");
	}
	
}
