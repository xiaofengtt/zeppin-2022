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
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.MobileCodeDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.MobileCode;
import cn.product.treasuremall.entity.MobileCode.MobileCodeStatus;
import cn.product.treasuremall.entity.MobileCode.MobileCodeTypes;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.entity.SystemParam;
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
	private MobileCodeDao mobileCodeDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void rate(InputParams params, DataResult<Object> result) {
		SystemParam sp = this.systemParamDao.get(SystemParamKey.WITHDRAW_POUNDAGE);
		SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
//		Map<String,Object> resultMap = JSONUtils.json2map(sp.getParamValue());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("poundageRate", sp.getParamValue());
		resultMap.put("goldExcRate", sprate.getParamValue());
		result.setData(resultMap);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void editNickname(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String nickname = paramsMap.get("nickname") == null ? "" : paramsMap.get("nickname").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	
    	FrontUser fu = this.frontUserDao.get(frontUser);
    	fu.setNickname(nickname);
    	frontUserDao.update(fu);
    	
    	result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
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
		result.setMessage("操作成功！");
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
    			result.setMessage("原密码输入错误！");
    			return;
        	}
    	}
    	
    	
    	fu.setPassword(newPwd);
    	frontUserDao.update(fu);
    	
    	result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
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
			result.setMessage("验证码输入错误！");
			return;
		}
		
		if(!MobileCodeTypes.FUNDCODE.equals(mc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("验证码输入错误！");
			return;
		}
		
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("验证码超时！");
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("验证成功！");
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
			result.setMessage("手机号码未变化！");
			return;
    	}
    	
    	if(this.frontUserDao.isExistFrontUserByMobile(mobile, null)){
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("新手机号码已被注册！");
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
			result.setMessage("验证码输入错误！");
			return;
		}
		
		if(!MobileCodeTypes.FUNDCODE.equals(oldMc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("验证码输入错误！");
			return;
		}
		
		if((System.currentTimeMillis()-oldMc.getCreatetime().getTime()) > 600000){
			result.setStatus(ResultStatusType.ERROR);
			result.setMessage("操作超时，请重新验证！");
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
			result.setMessage("验证码输入错误！");
			return;
		}
		
		if(!MobileCodeTypes.CODE.equals(newMc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("验证码输入错误！");
			return;
		}
		
		if((System.currentTimeMillis()-newMc.getCreatetime().getTime()) > 300000){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("验证码超时！");
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
		result.setMessage("操作成功！");
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
			result.setMessage("请填写正确的银行卡号");
			return;
		}
		
		
		idcard = idcard.toUpperCase();
		if(!Utlity.checkIdCard(idcard)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("认证失败，请填写正确的身份证号。");
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
		try {
			FrontUser fu = this.frontUserDao.get(frontUser);
			
			this.frontUserDao.certification(bank, bankcard, mobile, idcard, name, fu, null);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("实名认证成功！");
			return;
		} catch (TransactionException e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("你输入的身份认证信息有误，请重新认证。");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常，实名认证失败！");
			return;
		}
	}

	@Override
	public void getByMobile(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();		
    	if(Utlity.checkStringNull(mobile)) {
    		result.setStatus(ResultStatusType.ERROR);
			result.setMessage("用户登录状态异常，请重新登录！");
			return;
    	}
    	FrontUser fu = this.frontUserDao.getByMobile(mobile);
    	if(fu == null) {
    		result.setStatus(ResultStatusType.ERROR);
			result.setMessage("用户登录状态异常，请重新登录！");
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
    	result.setData(fuvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("查询成功！");
		return;
	}
}
