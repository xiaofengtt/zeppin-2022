/**
 * 
 */
package cn.zeppin.product.ntb.qcb.controller.wechat;

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
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard.QcbEmployeeBankcardStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard.QcbEmployeeBankcardType;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeBankcardService;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * 微信企财宝员工接口
 */

@Controller
@RequestMapping(value = "/qcbWechat/bankcard")
public class WechatBankcardController extends BaseController {
	
	@Autowired
	private IQcbEmployeeBankcardService qcbEmployeeBankcardService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	@Autowired
	private IResourceService resourceService;
	
	/**
	 * 获取用户绑定的卡列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Result list(ServletRequest request) {
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbEmployee", qe.getUuid());
		inputParams.put("status", QcbEmployeeBankcardStatus.NORMAL);
		inputParams.put("type", QcbEmployeeBankcardType.DEBIT);
		List<Entity> list = this.qcbEmployeeBankcardService.getListForPage(inputParams, -1, -1, null, QcbEmployeeBankcard.class);
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		if(list != null && !list.isEmpty()){
			for(int i = 0; i < list.size(); i++){
				Map<String, Object> result = new HashMap<String, Object>();
				QcbEmployeeBankcard qeb = (QcbEmployeeBankcard) list.get(i);
				Bank bank = this.bankService.get(qeb.getBank());
				if(bank == null){
					return ResultManager.createFailResult("银行不存在");
				}
				result.put("uuid", qeb.getUuid());
				result.put("name", bank.getName());
				result.put("shortName", bank.getShortName());
				result.put("color", bank.getColor());
				result.put("bankcard", qeb.getBindingBankCard().substring(qeb.getBindingBankCard().length()-4));
				result.put("phone", Utlity.getStarMobile(qeb.getBindingCardPhone()));
				Resource icon = this.resourceService.get(bank.getIcon());
				result.put("icon", icon.getUrl());
				Resource iconColor = this.resourceService.get(bank.getIconColor());
				result.put("iconColor", iconColor.getUrl());
				result.put("singleLimit", bank.getSingleLimit() == null ? 0 : bank.getSingleLimit());
				result.put("singleLimitCN", bank.getSingleLimit() == null ? "0.00" : Utlity.numFormat4UnitDetail(bank.getSingleLimit()));
				result.put("dailyLimit", bank.getDailyLimit() == null ? 0 : bank.getDailyLimit());
				result.put("dailyLimitCN", bank.getDailyLimit() == null ? "0.00" : Utlity.numFormat4UnitDetail(bank.getDailyLimit()));
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
	public Result get(String uuid, ServletRequest request){
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		QcbEmployeeBankcard qeb = this.qcbEmployeeBankcardService.get(uuid);
		if(!qe.getUuid().equals(qeb.getQcbEmployee())){
			return ResultManager.createFailResult("卡信息错误！");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		Bank bank = this.bankService.get(qeb.getBank());
		if(bank == null){
			return ResultManager.createFailResult("银行不存在");
		}
		result.put("uuid", qeb.getUuid());
		result.put("name", bank.getName());
		result.put("shortName", bank.getShortName());
		result.put("color", bank.getColor());
		result.put("singleLimit", bank.getSingleLimit() == null ? 0 : bank.getSingleLimit());
		result.put("singleLimitCN", bank.getSingleLimit() == null ? "0.00" : Utlity.numFormat4UnitDetail(bank.getSingleLimit()));
		result.put("dailyLimit", bank.getDailyLimit() == null ? 0 : bank.getDailyLimit());
		result.put("dailyLimitCN", bank.getDailyLimit() == null ? "0.00" : Utlity.numFormat4UnitDetail(bank.getDailyLimit()));
		result.put("flagRemind", qeb.getFlagRemind());
		result.put("remindTime", qeb.getRemindTime());
		result.put("bankcard", qeb.getBindingBankCard().substring(qeb.getBindingBankCard().length()-4));
		result.put("phone", Utlity.getStarMobile(qeb.getBindingCardPhone()));
		Resource icon = this.resourceService.get(bank.getIcon());
		result.put("icon", icon.getUrl());
		Resource iconColor = this.resourceService.get(bank.getIconColor());
		result.put("iconColor", iconColor.getUrl());
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
	public Result add(String bankcard, String phone, String mobileCode, String bank, ServletRequest request){
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		bankcard = Base64Util.getFromBase64(bankcard);
		if(bankcard.length() < 12 || bankcard.length() > 21){
			return ResultManager.createFailResult("请填写正确的银行卡号");
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("bindingBankCard", bankcard);
		inputParams.put("type", QcbEmployeeBankcardType.DEBIT);
		int count = this.qcbEmployeeBankcardService.getCount(inputParams);
		if(count > 0){
			return ResultManager.createFailResult("该银行卡已经绑定过！");
		}
		
		phone = Base64Util.getFromBase64(phone);
		if (!Utlity.isMobileNO(phone)) {
			return ResultManager.createFailResult("手机号码非法！");
		}
		mobileCode = Base64Util.getFromBase64(mobileCode);
		
		try {
			inputParams.clear();
			inputParams.put("mobile", phone);
			inputParams.put("type", MobileCodeTypes.EMP_BANKCARD_ADD);
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
			
			if(!MobileCodeTypes.EMP_BANKCARD_ADD.equals(mc.getType())){
				message = "验证码输入错误！";
				throw new TransactionException(message);
			}
			
			if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
				message = "验证码超时！";
				throw new TransactionException(message);
			}
			if(mobileCode.equals(mc.getCode())){
				QcbEmployeeBankcard qeb = new QcbEmployeeBankcard();
				qeb.setUuid(UUID.randomUUID().toString());
				qeb.setCreatetime(new Timestamp(System.currentTimeMillis()));
				qeb.setBank(bank);
				qeb.setBankAccountName("");
				qeb.setBindingBankCard(bankcard);
				qeb.setBindingCardPhone(phone);
				qeb.setBindingCardCardholder(qe.getRealname());
				qeb.setStatus(QcbEmployeeBankcardStatus.NORMAL);
				qeb.setQcbEmployee(qe.getUuid());
				qeb.setType(QcbEmployeeBankcardType.DEBIT);
				this.qcbEmployeeBankcardService.insert(qeb);
				mc.setStatus(MobileCodeStatus.DISABLE);
				this.mobileCodeService.update(mc);
				
				return ResultManager.createDataResult(qeb.getUuid(), "绑定成功");
				
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
	 * 解绑银行卡
	 * @param bankcard
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ActionParam(key = "mobileCode", type = DataType.STRING, message="验证码", required = true)
	@ActionParam(key = "uuid", type = DataType.STRING, message="银行卡编号", required = true)
	@ResponseBody
	public Result delete(String uuid, String mobileCode, ServletRequest request){
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		QcbEmployeeBankcard qeb = this.qcbEmployeeBankcardService.get(uuid);
		if(!qe.getUuid().equals(qeb.getQcbEmployee())){
			return ResultManager.createFailResult("卡信息错误！");
		}
		
		String phone = qe.getMobile();
		mobileCode = Base64Util.getFromBase64(mobileCode);
		
		//验证验证码 或发送给银行接口执行操作
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("mobile", phone);
		inputParams.put("status", MobileCodeStatus.NORMAL);
		inputParams.put("type", MobileCodeTypes.EMP_BANKCARD_DELETE);
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
		
		if(!MobileCodeTypes.EMP_BANKCARD_DELETE.equals(mc.getType())){
			return ResultManager.createFailResult("验证码输入错误");
		}
		
		if(!mobileCode.equals(mc.getCode())){
			return ResultManager.createFailResult("验证码输入错误");
		}
		try {
			qeb.setStatus(QcbEmployeeBankcardStatus.DISABLE);
			qeb.setBindingBankCard(qeb.getBindingBankCard()+"_#"+qeb.getUuid());
			this.qcbEmployeeBankcardService.update(qeb);
			
			mc.setStatus(MobileCodeStatus.DISABLE);
			this.mobileCodeService.update(mc);
			
			return ResultManager.createSuccessResult("解绑成功");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("操作异常，解绑失败！");
		}
	}
	
	/**
	 * 用户绑定信用卡
	 * @param bankcard
	 * @param cardholder
	 * @param phone
	 * @param bank
	 * @return
	 */
	@RequestMapping(value = "/creditAdd", method = RequestMethod.POST)
	@ActionParam(key = "bankcard", type = DataType.STRING, message="银行卡号", required = true)
	@ActionParam(key = "cardholder", type = DataType.STRING, message="持卡人", required = true)
	@ActionParam(key = "phone", type = DataType.STRING, message="银行预留手机号", required = true)
	@ActionParam(key = "bank", type = DataType.STRING, message="银行卡所属行", required = true)
	@ActionParam(key = "flagRemind", type = DataType.BOOLEAN, message="还款提醒", required = true)
	@ActionParam(key = "remindTime", type = DataType.NUMBER, message="还款日期", required = true)
	@ResponseBody
	public Result creditAdd(String bankcard, String cardholder, String phone, String bank, Boolean flagRemind, Integer remindTime, ServletRequest request){
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		bankcard = Base64Util.getFromBase64(bankcard);
		if(bankcard.length() < 12 || bankcard.length() > 21){
			return ResultManager.createFailResult("请填写正确的银行卡号");
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("bindingBankCard", bankcard);
		inputParams.put("qcbEmployee", qe.getUuid());
		inputParams.put("type", QcbEmployeeBankcardType.DEBIT);
		int count = this.qcbEmployeeBankcardService.getCount(inputParams);
		if(count > 0){
			return ResultManager.createFailResult("该银行卡已经绑定过！");
		}
		
		phone = Base64Util.getFromBase64(phone);
		if (!Utlity.isMobileNO(phone)) {
			return ResultManager.createFailResult("手机号码非法！");
		}
		
		QcbEmployeeBankcard qeb = new QcbEmployeeBankcard();
		qeb.setUuid(UUID.randomUUID().toString());
		qeb.setCreatetime(new Timestamp(System.currentTimeMillis()));
		qeb.setBank(bank);
		qeb.setBankAccountName("");
		qeb.setBindingBankCard(bankcard);
		qeb.setBindingCardPhone(phone);
		qeb.setBindingCardCardholder(cardholder);
		qeb.setStatus(QcbEmployeeBankcardStatus.NORMAL);
		qeb.setQcbEmployee(qe.getUuid());
		qeb.setType(QcbEmployeeBankcardType.CREDIT);
		qeb.setFlagRemind(flagRemind);
		qeb.setRemindTime(remindTime);
		this.qcbEmployeeBankcardService.insert(qeb);
		
		return ResultManager.createDataResult(qeb.getUuid(), "添加成功");	
	}
	
	/**
	 * 获取用户绑定的卡列表
	 * @return
	 */
	@RequestMapping(value = "/creditList", method = RequestMethod.GET)
	@ResponseBody
	public Result creditList(ServletRequest request) {
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbEmployee", qe.getUuid());
		inputParams.put("status", QcbEmployeeBankcardStatus.NORMAL);
		inputParams.put("type", QcbEmployeeBankcardType.CREDIT);
		List<Entity> list = this.qcbEmployeeBankcardService.getListForPage(inputParams, -1, -1, null, QcbEmployeeBankcard.class);
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		if(list != null && !list.isEmpty()){
			for(int i = 0; i < list.size(); i++){
				Map<String, Object> result = new HashMap<String, Object>();
				QcbEmployeeBankcard qeb = (QcbEmployeeBankcard) list.get(i);
				Bank bank = this.bankService.get(qeb.getBank());
				if(bank == null){
					return ResultManager.createFailResult("银行不存在");
				}
				result.put("uuid", qeb.getUuid());
				result.put("name", bank.getName());
				result.put("shortName", bank.getShortName());
				result.put("color", bank.getColor());
				result.put("bankcard", qeb.getBindingBankCard().substring(qeb.getBindingBankCard().length()-4));
				result.put("phone", Utlity.getStarMobile(qeb.getBindingCardPhone()));
				Resource icon = this.resourceService.get(bank.getIcon());
				result.put("icon", icon.getUrl());
				Resource iconColor = this.resourceService.get(bank.getIconColor());
				result.put("iconColor", iconColor.getUrl());
				result.put("flagRemind", qeb.getFlagRemind());
				result.put("remindTime", qeb.getRemindTime());
				listResult.add(result);
			}
		}
		return ResultManager.createDataResult(listResult, listResult.size());
	}
	
	/**
	 * 修改信用卡还款提醒
	 * @param uuid
	 * @param flagRemind
	 * @return
	 */
	@RequestMapping(value = "/creditRemind", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="银行卡编号", required = true)
	@ActionParam(key = "flagRemind", type = DataType.BOOLEAN, message="还款提醒", required = true)
	@ResponseBody
	public Result creditRemind(String uuid, Boolean flagRemind, ServletRequest request){
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		QcbEmployeeBankcard qeb = this.qcbEmployeeBankcardService.get(uuid);
		if(!qe.getUuid().equals(qeb.getQcbEmployee())){
			return ResultManager.createFailResult("卡信息错误！");
		}
		
		qeb.setFlagRemind(flagRemind);
		this.qcbEmployeeBankcardService.update(qeb);
		
		return ResultManager.createSuccessResult("修改成功");
	}
	
	/**
	 * 删除信用卡
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/creditDelete", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="银行卡编号", required = true)
	@ResponseBody
	public Result creditDelete(String uuid, ServletRequest request){
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		QcbEmployeeBankcard qeb = this.qcbEmployeeBankcardService.get(uuid);
		if(!qe.getUuid().equals(qeb.getQcbEmployee())){
			return ResultManager.createFailResult("卡信息错误！");
		}
		
		qeb.setStatus(QcbEmployeeBankcardStatus.DISABLE);
		qeb.setBindingBankCard(qeb.getBindingBankCard()+"_#"+qeb.getUuid());
		this.qcbEmployeeBankcardService.update(qeb);
		
		return ResultManager.createSuccessResult("删除成功");
	}
}
