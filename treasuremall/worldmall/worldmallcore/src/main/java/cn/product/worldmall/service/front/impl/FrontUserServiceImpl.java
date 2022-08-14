package cn.product.worldmall.service.front.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.controller.base.TransactionException;
import cn.product.worldmall.dao.FrontUserDailyDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserEditDao;
import cn.product.worldmall.dao.InternationalInfoDao;
import cn.product.worldmall.dao.InternationalRateDao;
import cn.product.worldmall.dao.MobileCodeDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserDaily;
import cn.product.worldmall.entity.FrontUserEdit;
import cn.product.worldmall.entity.FrontUserEdit.FrontUserEditStatus;
import cn.product.worldmall.entity.FrontUserEdit.FrontUserEditType;
import cn.product.worldmall.entity.InternationalInfo;
import cn.product.worldmall.entity.InternationalRate;
import cn.product.worldmall.entity.MobileCode;
import cn.product.worldmall.entity.MobileCode.MobileCodeStatus;
import cn.product.worldmall.entity.MobileCode.MobileCodeTypes;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.service.front.FrontUserService;
import cn.product.worldmall.util.Base64Util;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.front.FrontUserVO;
import cn.product.worldmall.vo.front.InternationalRateVO;

@Service("frontUserService")
public class FrontUserServiceImpl implements FrontUserService{
	
	@Autowired
	private FrontUserDao frontUserDao;

	@Autowired
	private FrontUserDailyDao frontUserDailyDao;
	
	@Autowired
	private MobileCodeDao mobileCodeDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private FrontUserEditDao frontUserEditDao;
	
	@Autowired
	private InternationalInfoDao internationalInfoDao;
	
	@Autowired
	private InternationalRateDao internationalRateDao;

	@Override
	public void rate(InputParams params, DataResult<Object> result) {
		SystemParam sp = this.systemParamDao.get(SystemParamKey.WITHDRAW_POUNDAGE);//提现手续费
		SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);//金币汇率
		SystemParam scorerate = this.systemParamDao.get(SystemParamKey.WITHDRAW_SCOREAMOUNT);//提现积分消耗
//		Map<String,Object> resultMap = JSONUtils.json2map(sp.getParamValue());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("poundageRate", sp.getParamValue());
		resultMap.put("goldExcRate", sprate.getParamValue());
		resultMap.put("scoreAmountRate", scorerate.getParamValue());
		
		//国际货币汇率
		List<InternationalRate> listRate = internationalRateDao.getCurrencyListByParams();
		List<InternationalRateVO> listRatevo = new ArrayList<InternationalRateVO>();
		if(listRate != null && listRate.size() > 0){
			//获取货币和符号
			SystemParam spcurrency = this.systemParamDao.get(SystemParamKey.INTERNATIONAL_CURRENCIES_SYMBOL);
			String symbol = "";
			for(InternationalRate ir : listRate) {
				InternationalRateVO irvo = new InternationalRateVO(ir); 
				if(spcurrency != null) {
					Map<String, Object> currencies = JSONUtils.json2map(spcurrency.getParamValue());
					if(currencies != null && !currencies.isEmpty()) {
						symbol = currencies.get(ir.getCurrencyCode()) == null ? "" : currencies.get(ir.getCurrencyCode()).toString();
					}
				}
				if(Utlity.checkStringNull(symbol)) {
					symbol = ir.getCurrencyCode();
				}
				irvo.setSymbol(symbol);
				listRatevo.add(irvo);
			}
		}
					
		resultMap.put("exchangeRate", listRatevo);
		
		result.setData(resultMap);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	/**
	 * 20200616-2.1需求，增加昵称修改审核流程
	 */
	@Override
	public void editNickname(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String nickname = paramsMap.get("nickname") == null ? "" : paramsMap.get("nickname").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	
    	FrontUser fu = this.frontUserDao.get(frontUser);
    	
    	FrontUserEdit fue = this.frontUserEditDao.getByEditType(frontUser, FrontUserEditType.NICKNAME);
    	if(fue != null) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Nickname change is being reviewed, please do not repeat!");
			return;
    	}
    	
    	fue = new FrontUserEdit();
    	fue.setUuid(UUID.randomUUID().toString());
    	fue.setFrontUser(frontUser);
    	fue.setInfoBefore(fu.getNickname());
    	fue.setInfoAfter(nickname);
    	fue.setStatus(FrontUserEditStatus.NORMAL);
    	fue.setType(FrontUserEditType.NICKNAME);
    	fue.setCreatetime(new Timestamp(System.currentTimeMillis()));
    	
    	this.frontUserEditDao.insert(fue);
//    	fu.setNickname(nickname);
//    	frontUserDao.update(fu);
    	
    	result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Operation successful, waiting for review!");
		return;
	}

	@Override
	public void editImage(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String image = paramsMap.get("image") == null ? "" : paramsMap.get("image").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	
    	FrontUser fu = this.frontUserDao.get(frontUser);
    	fu.setImage(image);
    	frontUserDao.update(fu);
    	
    	result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
	}

	@Override
	public void editPwd(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String pwd = paramsMap.get("pwd") == null ? "" : paramsMap.get("pwd").toString();
    	String newPwd = paramsMap.get("newPwd") == null ? "" : paramsMap.get("newPwd").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	
    	pwd = Base64Util.getFromBase64(pwd);
    	newPwd = Base64Util.getFromBase64(newPwd);
    	
    	FrontUser fu = this.frontUserDao.get(frontUser);
    	if(!Utlity.checkStringNull(fu.getPassword())) {
    		if(!pwd.equals(fu.getPassword())){
        		result.setStatus(ResultStatusType.FAILED);
    			result.setMessage("Original password input error!");
    			return;
        	}
    	}
    	
    	
    	fu.setPassword(newPwd);
    	frontUserDao.update(fu);
    	
    	result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
	}

	@Override
	public void checkMobile(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String code = paramsMap.get("code") == null ? "" : paramsMap.get("code").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	
    	code = Base64Util.getFromBase64(code);
    	FrontUser fu = this.frontUserDao.get(frontUser);
    	
    	Map<String, Object> searchMap = new HashMap<String, Object>();
    	searchMap.put("mobile", fu.getMobile());
    	searchMap.put("status", MobileCodeStatus.NORMAL);
    	searchMap.put("type", MobileCodeTypes.FUNDCODE);
		List<MobileCode> lstMobileCode = this.mobileCodeDao.getListByParams(searchMap);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect verification code!");
			return;
		}
		
		if(!MobileCodeTypes.FUNDCODE.equals(mc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect verification code!");
			return;
		}
		
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Verification code timed out!");
			return;
		}
		
		if(!code.equals(mc.getCode())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect verification code!");
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
	}

	@Override
	public void editMobile(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String code = paramsMap.get("code") == null ? "" : paramsMap.get("code").toString();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	String newCode = paramsMap.get("newCode") == null ? "" : paramsMap.get("newCode").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String country = paramsMap.get("country") == null ? "" : paramsMap.get("country").toString();
    	
    	code = Base64Util.getFromBase64(code);
    	mobile = Base64Util.getFromBase64(mobile);
    	newCode = Base64Util.getFromBase64(newCode);
    	
    	FrontUser fu = this.frontUserDao.get(frontUser);
    	if(mobile.equals(fu.getMobile())){
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Mobile number has not changed!");
			return;
    	}
    	
    	if(this.frontUserDao.isExistFrontUserByMobile(mobile, null)){
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The new mobile number has been registered!");
			return;
    	}
    	
    	Map<String, Object> searchMap = new HashMap<String, Object>();
    	searchMap.put("mobile", fu.getMobile());
    	searchMap.put("status", MobileCodeStatus.NORMAL);
    	searchMap.put("type", MobileCodeTypes.FUNDCODE);
		List<MobileCode> oldList = this.mobileCodeDao.getListByParams(searchMap);
		MobileCode oldMc = null;
		if(oldList != null && oldList.size() > 0){
			oldMc = (MobileCode) oldList.get(0);
		}
		if(oldMc == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect verification code!");
			return;
		}
		
		if(!MobileCodeTypes.FUNDCODE.equals(oldMc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect verification code!");
			return;
		}
		
		if((System.currentTimeMillis()-oldMc.getCreatetime().getTime()) > 600000){
			result.setStatus(ResultStatusType.ERROR);
			result.setMessage("Timed out, please re-verify!");
			return;
		}
		searchMap.put("mobile", mobile);
		searchMap.put("type", MobileCodeTypes.CODE);
		List<MobileCode> newList = this.mobileCodeDao.getListByParams(searchMap);
		MobileCode newMc = null;
		if(newList != null && newList.size() > 0){
			newMc = (MobileCode) newList.get(0);
		}
		if(newMc == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect verification code!");
			return;
		}
		
		if(!MobileCodeTypes.CODE.equals(newMc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect verification code!");
			return;
		}
		
		if((System.currentTimeMillis()-newMc.getCreatetime().getTime()) > 300000){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Verification code timed out!");
			return;
		}
		
		if(!newCode.equals(newMc.getCode())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect verification code!");
			return;
		}
		
		try {
//			fu.setMobile(mobile);
			oldMc.setStatus(MobileCodeStatus.DISABLE);
			newMc.setStatus(MobileCodeStatus.DISABLE);
			List<MobileCode> codeList = new ArrayList<MobileCode>();
			codeList.add(oldMc);
			codeList.add(newMc);
			
			//国家信息
			if(!Utlity.checkStringNull(country)) {
				InternationalInfo ii = this.internationalInfoDao.get(country);
				if(ii == null) {
					throw new TransactionException("Country code error!");
				}
				if(mobile.indexOf(ii.getTelCode()) < 0) {
					throw new TransactionException("Mobile number info error!");
				}
				fu.setCountry(ii.getUuid());
			}
			frontUserDao.updateFrontUser(fu, mobile, codeList);

	    	result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("Successful!");
			
		} catch (TransactionException te) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(te.getMessage() == null ? "An error occurred!" : te.getMessage());
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Login failed,Service exception!");
			return;
		}
	}

	@Override
	public void editMobilePassword(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String code = paramsMap.get("code") == null ? "" : paramsMap.get("code").toString();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String country = paramsMap.get("country") == null ? "" : paramsMap.get("country").toString();
    	String password = paramsMap.get("password") == null ? "" : paramsMap.get("password").toString();
    	
    	code = Base64Util.getFromBase64(code);
    	mobile = Base64Util.getFromBase64(mobile);
    	password = Base64Util.getFromBase64(password);
    	
    	FrontUser fu = this.frontUserDao.get(frontUser);
    	if(mobile.equals(fu.getMobile())){
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Mobile number has not changed!");
			return;
    	}
    	
    	if(this.frontUserDao.isExistFrontUserByMobile(mobile, null)){
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The new mobile number has been registered!");
			return;
    	}
    	
    	Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("mobile", mobile);
		searchMap.put("status", MobileCodeStatus.NORMAL);
		searchMap.put("type", MobileCodeTypes.CODE);
		List<MobileCode> newList = this.mobileCodeDao.getListByParams(searchMap);
		MobileCode newMc = null;
		if(newList != null && newList.size() > 0){
			newMc = (MobileCode) newList.get(0);
		}
		if(newMc == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect verification code!");
			return;
		}
		
		if(!MobileCodeTypes.CODE.equals(newMc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect verification code!");
			return;
		}
		
		if((System.currentTimeMillis()-newMc.getCreatetime().getTime()) > 300000){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Verification code timed out!");
			return;
		}
		
		if(!code.equals(newMc.getCode())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect verification code!");
			return;
		}
		
		try {
//			fu.setMobile(mobile);
//			oldMc.setStatus(MobileCodeStatus.DISABLE);
			newMc.setStatus(MobileCodeStatus.DISABLE);
			List<MobileCode> codeList = new ArrayList<MobileCode>();
//			codeList.add(oldMc);
			codeList.add(newMc);
			
			//国家信息
			if(!Utlity.checkStringNull(country)) {
				InternationalInfo ii = this.internationalInfoDao.get(country);
				if(ii == null) {
					throw new TransactionException("Country code error!");
				}
				if(mobile.indexOf(ii.getTelCode()) < 0) {
					throw new TransactionException("Mobile number info error!");
				}
				fu.setCountry(ii.getUuid());
			}
			//设置密码
			fu.setPassword(password);
			frontUserDao.updateFrontUser(fu, mobile, codeList);

	    	result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("Successful!");
			
		} catch (TransactionException te) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(te.getMessage() == null ? "An error occurred!" : te.getMessage());
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Login failed,Service exception!");
			return;
		}
	}
	

	@Override
	public void getByMobile(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();		
    	if(Utlity.checkStringNull(uuid)) {
    		result.setStatus(ResultStatusType.ERROR);
			result.setMessage("User login status is abnormal, please log in again!");
			return;
    	}
    	FrontUser fu = this.frontUserDao.get(uuid);
    	if(fu == null) {
    		result.setStatus(ResultStatusType.ERROR);
			result.setMessage("User login status is abnormal, please log in again!");
			return;
    	}
    	SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
    	FrontUserVO fuvo = new FrontUserVO(fu);
    	if(!Utlity.checkStringNull(fu.getImage())) {
    		Resource re = this.resourceDao.get(fu.getImage());
    		if(re != null) {
    			fuvo.setImageURL(pathUrl + re.getUrl());
    		}
    	}
    	if(!Utlity.checkStringNull(fu.getCountry())) {
    		InternationalInfo ii = this.internationalInfoDao.get(fu.getCountry());
    		if(ii != null) {
    			fuvo.setAreaCode(ii.getTelCode());
    		}
    	}
    	result.setData(fuvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
		return;
	}

	@Override
	public void dailyStatistics(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	if(Utlity.checkStringNull(frontUser)){
    		return;
    	}
    	String dailyDate = Utlity.timestampFormat(new Timestamp(System.currentTimeMillis()), "yyyy-MM-dd");
    	FrontUserDaily fud = this.frontUserDailyDao.get(frontUser + dailyDate);
    	if(fud == null){
    		fud = new FrontUserDaily();
    		fud.setUuid(frontUser + dailyDate);
    		fud.setDailyDate(dailyDate);
    		fud.setFrontUser(frontUser);
    		try{
    			this.frontUserDailyDao.insert(fud);
    		}catch(Exception e){
    			return;
    		}
    	}
	}
}
