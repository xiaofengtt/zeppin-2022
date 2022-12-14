package cn.product.treasuremall.service.front.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.controller.base.TransactionException;
import cn.product.treasuremall.dao.FrontUserDailyDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserEditDao;
import cn.product.treasuremall.dao.MobileCodeDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUserDaily;
import cn.product.treasuremall.entity.FrontUserEdit;
import cn.product.treasuremall.entity.MobileCode;
import cn.product.treasuremall.entity.MobileCode.MobileCodeStatus;
import cn.product.treasuremall.entity.MobileCode.MobileCodeTypes;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.entity.FrontUserEdit.FrontUserEditStatus;
import cn.product.treasuremall.entity.FrontUserEdit.FrontUserEditType;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.service.front.FrontUserService;
import cn.product.treasuremall.util.BankCardUtlity;
import cn.product.treasuremall.util.Base64Util;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.front.FrontUserVO;

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

	@Override
	public void rate(InputParams params, DataResult<Object> result) {
		SystemParam sp = this.systemParamDao.get(SystemParamKey.WITHDRAW_POUNDAGE);//???????????????
		SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);//????????????
		SystemParam scorerate = this.systemParamDao.get(SystemParamKey.WITHDRAW_SCOREAMOUNT);//??????????????????
//		Map<String,Object> resultMap = JSONUtils.json2map(sp.getParamValue());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("poundageRate", sp.getParamValue());
		resultMap.put("goldExcRate", sprate.getParamValue());
		resultMap.put("scoreAmountRate", scorerate.getParamValue());
		
		result.setData(resultMap);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	/**
	 * 20200616-2.1???????????????????????????????????????
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
			result.setMessage("????????????????????????????????????????????????");
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
		result.setMessage("??????????????????????????????");
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
		result.setMessage("???????????????");
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
    			result.setMessage("????????????????????????");
    			return;
        	}
    	}
    	
    	
    	fu.setPassword(newPwd);
    	frontUserDao.update(fu);
    	
    	result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("???????????????");
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
			result.setMessage("????????????????????????");
			return;
		}
		
		if(!MobileCodeTypes.FUNDCODE.equals(mc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????");
			return;
		}
		
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????");
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("???????????????");
	}

	@Override
	public void editMobile(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String code = paramsMap.get("code") == null ? "" : paramsMap.get("code").toString();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	String newCode = paramsMap.get("newCode") == null ? "" : paramsMap.get("newCode").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	
    	code = Base64Util.getFromBase64(code);
    	mobile = Base64Util.getFromBase64(mobile);
    	newCode = Base64Util.getFromBase64(newCode);
    	
    	FrontUser fu = this.frontUserDao.get(frontUser);
    	if(mobile.equals(fu.getMobile())){
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????");
			return;
    	}
    	
    	if(this.frontUserDao.isExistFrontUserByMobile(mobile, null)){
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????????????????");
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
			result.setMessage("????????????????????????");
			return;
		}
		
		if(!MobileCodeTypes.FUNDCODE.equals(oldMc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????");
			return;
		}
		
		if((System.currentTimeMillis()-oldMc.getCreatetime().getTime()) > 600000){
			result.setStatus(ResultStatusType.ERROR);
			result.setMessage("?????????????????????????????????");
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
			result.setMessage("????????????????????????");
			return;
		}
		
		if(!MobileCodeTypes.CODE.equals(newMc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????");
			return;
		}
		
		if((System.currentTimeMillis()-newMc.getCreatetime().getTime()) > 300000){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????");
			return;
		}
		
//		fu.setMobile(mobile);
		oldMc.setStatus(MobileCodeStatus.DISABLE);
		newMc.setStatus(MobileCodeStatus.DISABLE);
		List<MobileCode> codeList = new ArrayList<MobileCode>();
		codeList.add(oldMc);
		codeList.add(newMc);
		frontUserDao.updateFrontUser(fu, mobile, codeList);
    	
    	result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("???????????????");
	}
	
	@Override
	public void certification(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
    	String idcard = paramsMap.get("idcard") == null ? "" : paramsMap.get("idcard").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String mobile = paramsMap.get("phone") == null ? "" : paramsMap.get("phone").toString();
//    	String mobileCode = paramsMap.get("mobileCode") == null ? "" : paramsMap.get("mobileCode").toString();
    	String bank = paramsMap.get("bank") == null ? "" : paramsMap.get("bank").toString();
    	String bankcard = paramsMap.get("bankcard") == null ? "" : paramsMap.get("bankcard").toString();
    	
    	bankcard = Base64Util.getFromBase64(bankcard);
		name = Base64Util.getFromBase64(name);
		idcard = Base64Util.getFromBase64(idcard);
		
		if(BankCardUtlity.checkBankCard(bankcard)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????????????????");
			return;
		}
		
		
		idcard = idcard.toUpperCase();
		if(!Utlity.checkIdCard(idcard)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????????????????????????????");
			return;
		}
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("idcard", idcard);
		Integer count = this.frontUserDao.getCountByParams(paramsls);
		if(count > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????????????????????????????????????????");
			return;
		}
		try {
			FrontUser fu = this.frontUserDao.get(frontUser);
			
			this.frontUserDao.certification(bank, bankcard, mobile, idcard, name, fu, null);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("?????????????????????");
			return;
		} catch (TransactionException e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("?????????????????????????????????????????????????????????");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????????????????");
			return;
		}
	}

	@Override
	public void getByMobile(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();		
    	if(Utlity.checkStringNull(mobile)) {
    		result.setStatus(ResultStatusType.ERROR);
			result.setMessage("?????????????????????????????????????????????");
			return;
    	}
    	FrontUser fu = this.frontUserDao.getByMobile(mobile);
    	if(fu == null) {
    		result.setStatus(ResultStatusType.ERROR);
			result.setMessage("?????????????????????????????????????????????");
			return;
    	}
    	SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//????????????
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
    	result.setData(fuvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("???????????????");
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
