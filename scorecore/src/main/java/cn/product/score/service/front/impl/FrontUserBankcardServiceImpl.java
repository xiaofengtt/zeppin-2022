package cn.product.score.service.front.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.controller.base.TransactionException;
import cn.product.score.dao.BankDao;
import cn.product.score.dao.FrontUserBankcardDao;
import cn.product.score.dao.MobileCodeDao;
import cn.product.score.dao.ResourceDao;
import cn.product.score.entity.Bank;
import cn.product.score.entity.FrontUserBankcard;
import cn.product.score.entity.FrontUserBankcard.FrontUserBankcardStatus;
import cn.product.score.entity.FrontUserBankcard.FrontUserBankcardType;
import cn.product.score.entity.MobileCode;
import cn.product.score.entity.MobileCode.MobileCodeStatus;
import cn.product.score.entity.MobileCode.MobileCodeTypes;
import cn.product.score.entity.Resource;
import cn.product.score.service.front.FrontUserBankcardService;
import cn.product.score.util.Base64Util;
import cn.product.score.util.Utlity;

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
				resultMap.put("bankcard", fub.getAccountNumber().substring(fub.getAccountNumber().length()-4));
				resultMap.put("phone", Utlity.getStarMobile(fub.getPhone()));
				Resource logo = this.resourceDao.get(bank.getLogo());
				if(logo != null) {
					resultMap.put("logoUrl", logo.getUrl());
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
		resultMap.put("bankcard", fub.getAccountNumber().substring(fub.getAccountNumber().length()-4));
		resultMap.put("phone", Utlity.getStarMobile(fub.getPhone()));
		Resource logo = this.resourceDao.get(bank.getLogo());
		if(logo != null) {
			resultMap.put("logoUrl", logo.getUrl());
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
    	
		bankcard = Base64Util.getFromBase64(bankcard);
		if(bankcard.length() < 12 || bankcard.length() > 21){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("请填写正确的银行卡号");
			return;
		}
		
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
				fub.setAccountHolder(realName);
				fub.setStatus(FrontUserBankcardStatus.NORMAL);
				fub.setFrontUser(fu);
				fub.setType(FrontUserBankcardType.DEBIT);
				
				mc.setStatus(MobileCodeStatus.DISABLE);
				this.frontUserBankcardDao.insertFrontUserBankcard(fub, mc);				
				result.setData(fub.getUuid());
				result.setMessage("绑定成功");
				result.setStatus(ResultStatusType.SUCCESS);
				
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
