package cn.product.treasuremall.service.front.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.controller.base.TransactionException;
import cn.product.treasuremall.dao.BankDao;
import cn.product.treasuremall.dao.FrontUserBankcardDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.MobileCodeDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.entity.Bank;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUserBankcard;
import cn.product.treasuremall.entity.FrontUserBankcard.FrontUserBankcardStatus;
import cn.product.treasuremall.entity.FrontUserBankcard.FrontUserBankcardType;
import cn.product.treasuremall.entity.MobileCode;
import cn.product.treasuremall.entity.MobileCode.MobileCodeStatus;
import cn.product.treasuremall.entity.MobileCode.MobileCodeTypes;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.service.front.FrontUserBankcardService;
import cn.product.treasuremall.util.Base64Util;
import cn.product.treasuremall.util.Utlity;

@Service("frontUserBankcardService")
public class FrontUserBankcardServiceImpl implements FrontUserBankcardService{
	
	@Autowired
	private FrontUserBankcardDao frontUserBankcardDao;
	
	@Autowired
	private BankDao bankDao;
	
	@Autowired
	private MobileCodeDao mobileCodeDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private FrontUserDao frontUserDao;

	@Autowired
	private SystemParamDao systemParamDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
    	
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("frontUser", fu);
		paramsls.put("status", FrontUserBankcardStatus.NORMAL);
		paramsls.put("type", FrontUserBankcardType.DEBIT);
		List<FrontUserBankcard> list = this.frontUserBankcardDao.getListByParams(paramsls);
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		if(list != null && !list.isEmpty()){
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			for(FrontUserBankcard fub : list){
				Map<String, Object> resultMap = new HashMap<String, Object>();
				Bank bank = this.bankDao.get(fub.getBank());
				if(bank == null){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("银行不存在");
					return;
				}
				resultMap.put("uuid", fub.getUuid());
				resultMap.put("name", bank.getName());
				resultMap.put("shortName", bank.getShortName());
				resultMap.put("bankcard", fub.getAccountNumber());
				resultMap.put("phone", Utlity.getStarMobile(fub.getPhone()));
				Resource logo = this.resourceDao.get(bank.getLogo());
				if(logo != null) {
					resultMap.put("logoUrl", pathUrl + logo.getUrl());
				} else {
					resultMap.put("logoUrl", "");
				}
				listResult.add(resultMap);
			}
		}
		result.setData(listResult);
		result.setTotalResultCount(listResult.size());
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		FrontUserBankcard fub = this.frontUserBankcardDao.get(uuid);
		if(!fu.equals(fub.getFrontUser())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("卡信息错误！");
			return;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Bank bank = this.bankDao.get(fub.getBank());
		if(bank == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("银行不存在");
			return;
		}
		resultMap.put("uuid", fub.getUuid());
		resultMap.put("name", bank.getName());
		resultMap.put("shortName", bank.getShortName());
		resultMap.put("bankcard", fub.getAccountNumber());
		resultMap.put("accountHolder", fub.getAccountHolder());
		resultMap.put("phone", Utlity.getStarMobile(fub.getPhone()));
		resultMap.put("createtime", fub.getCreatetime());
		
		SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		Resource logo = this.resourceDao.get(bank.getLogo());
		if(logo != null) {
			resultMap.put("logoUrl", pathUrl + logo.getUrl());
		} else {
			resultMap.put("logoUrl", "");
		}
		result.setData(resultMap);
		result.setMessage("获取成功");
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
    	String bankcard = paramsMap.get("bankcard") == null ? "" : paramsMap.get("bankcard").toString();
    	String phone = paramsMap.get("phone") == null ? "" : paramsMap.get("phone").toString();
    	String mobileCode = paramsMap.get("mobileCode") == null ? "" : paramsMap.get("mobileCode").toString();
    	String bank = paramsMap.get("bank") == null ? "" : paramsMap.get("bank").toString();
    	String realName = paramsMap.get("realName") == null ? "" : paramsMap.get("realName").toString();
    	String idcard = paramsMap.get("idcard") == null ? "" : paramsMap.get("idcard").toString();
    	
		bankcard = Base64Util.getFromBase64(bankcard);
//		if(BankCardUtlity.checkBankCard(bankcard)) {
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("请填写正确的银行卡号");
//			return;
//		}
		
		FrontUser frontUser = this.frontUserDao.get(fu);
    	if(frontUser == null) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("认证失败，用户信息有误。");
			return;
    	}
    	
    	//判断是否为实名认证，即是否第一次绑卡
    	if(!frontUser.getRealnameflag()) {//未实名，为实名认证，第一次绑卡，需要校验身份证号
    		if(Utlity.checkStringNull(idcard)) {
    			result.setStatus(ResultStatusType.FAILED);
    			result.setMessage("认证失败，请输入身份证号。");
    			return;
    		}
    		
    		idcard = Base64Util.getFromBase64(idcard);
    		idcard = idcard.toUpperCase();
    		if(!Utlity.checkIdCard(idcard)) {
    			result.setStatus(ResultStatusType.FAILED);
    			result.setMessage("认证失败，请填写正确的身份证号。");
    			return;
    		}
    		
    		if(Utlity.checkStringNull(realName)) {
    			result.setStatus(ResultStatusType.FAILED);
    			result.setMessage("认证失败，请输入持卡人。");
    			return;
    		}
    		
    		Map<String, Object> paramsls = new HashMap<String, Object>();
    		paramsls.put("idcard", idcard);
    		Integer count = this.frontUserDao.getCountByParams(paramsls);
    		if(count > 0){
    			result.setStatus(ResultStatusType.FAILED);
    			result.setMessage("认证失败，该身份证信息已被其他用户认证。");
    			return;
    		}
    	} else {//只能绑定本人银行卡
//    		if(!frontUser.getIdcard().equals(idcard)) {
//    			result.setStatus(ResultStatusType.FAILED);
//    			result.setMessage("认证失败，请填写本人身份证号。");
//    			return;
//    		}
    		idcard = frontUser.getIdcard();
    		realName = frontUser.getRealname();
    	}
//		if(bankcard.length() < 12 || bankcard.length() > 21){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("请填写正确的银行卡号");
//			return;
//		}
		
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("accountNumber", bankcard);
		paramsls.put("type", FrontUserBankcardType.DEBIT);
		int count = this.frontUserBankcardDao.getCountByParams(paramsls);
		if(count > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该银行卡已经绑定过！");
			return;
		}
		
		phone = Base64Util.getFromBase64(phone);
		if (!Utlity.isMobileNO(phone)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("手机号码非法！");
			return;
		}
		mobileCode = Base64Util.getFromBase64(mobileCode);
		
		try {
			paramsls.clear();
			paramsls.put("mobile", phone);
			paramsls.put("type", MobileCodeTypes.FUNDCODE);
			paramsls.put("status", MobileCodeStatus.NORMAL);
			List<MobileCode> lstMobileCode = this.mobileCodeDao.getListByParams(paramsls);
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
			
			if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 150000){
				message = "验证码超时！";
				throw new TransactionException(message);
			}
			if(mobileCode.equals(mc.getCode())){//验证码校验通过
				//调用certificate接口
				
				this.frontUserDao.certification(bank, bankcard, phone, idcard, realName, frontUser, mc);
				
				result.setMessage("绑定成功");
				result.setStatus(ResultStatusType.SUCCESS);
				return;
			} else {
				message = "验证码输入错误！";
				throw new TransactionException(message);
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(e.getMessage());
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常，绑定失败");
			return;
		}
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		FrontUserBankcard fub = this.frontUserBankcardDao.get(uuid);
		if(!fu.equals(fub.getFrontUser())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("卡信息错误！");
			return;
		}
		
		fub.setStatus(FrontUserBankcardStatus.DELETE);
		fub.setAccountNumber(fub.getAccountNumber()+"_#"+fub.getUuid());
		
		this.frontUserBankcardDao.update(fub);
		result.setMessage("解绑成功");
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
}
