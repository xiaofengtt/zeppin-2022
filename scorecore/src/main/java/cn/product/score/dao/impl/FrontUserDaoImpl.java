package cn.product.score.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.score.controller.base.TransactionException;
import cn.product.score.dao.FrontUserAccountDao;
import cn.product.score.dao.FrontUserDao;
import cn.product.score.entity.FrontUser;
import cn.product.score.entity.FrontUser.FrontUserStatus;
import cn.product.score.entity.FrontUser.FrontUserType;
import cn.product.score.entity.FrontUserAccount;
import cn.product.score.entity.FrontUserAccount.FrontUserAccountStatus;
import cn.product.score.entity.MobileCode;
import cn.product.score.entity.MobileCode.MobileCodeStatus;
import cn.product.score.entity.MobileCode.MobileCodeTypes;
import cn.product.score.mapper.FrontUserMapper;
import cn.product.score.mapper.MobileCodeMapper;
import cn.product.score.util.IDCardUtil;
import cn.product.score.util.JuHeUtility;
import cn.product.score.util.MD5;
import cn.product.score.util.Utlity;

@Component
public class FrontUserDaoImpl implements FrontUserDao{
	
	@Autowired
	private FrontUserMapper frontUserMapper;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private MobileCodeMapper mobileCodeMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUser> getListByParams(Map<String, Object> params) {
		return frontUserMapper.getListByParams(params);
	}
	
    @Override
    public FrontUser getByMobile(String mobile) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile",mobile);
        params.put("status",FrontUserStatus.NORMAL);
        List<FrontUser> frontUserList =  this.getListByParams(params);
        if(frontUserList.size()>0){
            return frontUserList.get(0);
        }
            return null;
    }
    
    @Override
	@Cacheable(cacheNames="frontUser",key="'frontUser:' + #key")
	public FrontUser get(String key) {
		return frontUserMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUser frontUser) {
		return frontUserMapper.insert(frontUser);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUser", key = "'frontUser:' + #key")})
	public int delete(String key) {
		return frontUserMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUser", key = "'frontUser:' + #frontUser.uuid")})
	public int update(FrontUser frontUser) {
		return frontUserMapper.updateByPrimaryKey(frontUser);
	}

	@Override
	public Boolean isExistFrontUserByMobile(String mobile, String uuid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobile", mobile);
		params.put("nuuid", uuid);
		Integer count = this.frontUserMapper.getCountByParams(params);
		
		if(count != null && count > 0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	@Transactional
	public HashMap<String, Object> register(String token,String mobile) throws TransactionException{
		HashMap<String,Object> result = new HashMap<String,Object>();
		String message = "";
		String deviceNumber = token.substring(0,2);
		if(deviceNumber == null || "".equals(deviceNumber)){
			message = "登录失败，未识别设备号";
			throw new TransactionException(message);
		}
		
		if (!Utlity.isMobileNO(mobile)) {
			message = "手机号非法！";
			throw new TransactionException(message);
		}
		
		String timestamp = token.substring(2,15);
		if(timestamp == null || "".equals(timestamp)){
			throw new TransactionException(message);
		}
		long time = Long.parseLong(timestamp);
		long nowTime = System.currentTimeMillis();
		if(time <= nowTime){
			if(Utlity.checkTime(time, nowTime)){
				message = "登录失败，登录时间超时";
				throw new TransactionException(message);
			}
		} else {
			message = "登录失败，登录时间超时";
			throw new TransactionException(message);
		}
		
		if(this.isExistFrontUserByMobile(mobile, null)){
			message = "用户已注册！";
			throw new TransactionException(message);
		}
		
		String md5Str = token.substring(15);
		if(md5Str == null || "".equals(md5Str)){
			throw new TransactionException(message);
		}
		
		Map<String, Object> inputParams = new HashMap<String, Object>();
		inputParams.put("mobile", mobile);
		inputParams.put("status", MobileCodeStatus.NORMAL);
		List<MobileCode> lstMobileCode = this.mobileCodeMapper.getListByParams(inputParams);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			message = "验证码输入错误！";
			throw new TransactionException(message);
		}
		
		if(!mc.getMobile().equals(mobile)){
			message = "手机号输入错误！";
			throw new TransactionException(message);
		}
		
		if(!MobileCodeTypes.CODE.equals(mc.getType())){
			message = "验证码输入错误！";
			throw new TransactionException(message);
		}
		
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			message = "验证码超时！";
			throw new TransactionException(message);
		}
		
		String realMD5Str = MD5.getMD5(Utlity.KEY_FRONT_MD5+time+mc.getMobile()+mc.getCode());
		if(realMD5Str.equals(md5Str)){//成功
			
			FrontUser fu = new FrontUser();
			fu.setUuid(UUID.randomUUID().toString());
			fu.setMobile(mobile);
			fu.setNickname(mobile);
			fu.setRealnameflag(false);
			fu.setStatus(FrontUserStatus.NORMAL);
			fu.setType(FrontUserType.NORMAL);
			fu.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			FrontUserAccount fua = new FrontUserAccount();
			fua.setUuid(UUID.randomUUID().toString());
			fua.setFrontUser(fu.getUuid());
			fua.setStatus(FrontUserAccountStatus.NORMAL);
			fua.setBalanceFree(BigDecimal.ZERO);
			fua.setBalanceLock(BigDecimal.ZERO);
			fua.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			this.frontUserMapper.insert(fu);
			this.frontUserAccountDao.insert(fua);
			
			mc.setStatus(MobileCodeStatus.DISABLE);
			this.mobileCodeMapper.updateByPrimaryKey(mc);
			message = "登录成功!";
			result.put("result", true);
			result.put("frontUser", fu);
			result.put("message", message);
			return result;
		} else {
			message = "登录失败，验证码输入错误！";
			throw new TransactionException(message);
		}
	}
	
	@Override
	public HashMap<String, Object> loginByCode(String token, String phone) throws TransactionException {
		HashMap<String,Object> result = new HashMap<String,Object>();
		String message = "手机验证码不正确";
		String deviceNumber = token.substring(0,2);
		if(deviceNumber == null || "".equals(deviceNumber)){
			message = "登录失败，未识别的设备号";
			throw new TransactionException(message);
		}
		
		String timestamp = token.substring(2,15);
		if(timestamp == null || "".equals(timestamp)){
			throw new TransactionException(message);
		}
		long time = Long.parseLong(timestamp);
		long nowTime = System.currentTimeMillis();
		if(time <= nowTime){
			if(Utlity.checkLessTime(time, nowTime)){
				throw new TransactionException(message);
			}
		} else {
			throw new TransactionException(message);
		}
		
		FrontUser su = this.getByMobile(phone);
		if(su == null){
			message = "用户未注册";
			throw new TransactionException(message);
		}
		String md5Str = token.substring(15);
		if(md5Str == null || "".equals(md5Str)){
			throw new TransactionException(message);
		}
		
		Map<String, Object> inputParams = new HashMap<String, Object>();
		inputParams.put("mobile", phone);
		inputParams.put("status", MobileCodeStatus.NORMAL);
		List<MobileCode> lstMobileCode = this.mobileCodeMapper.getListByParams(inputParams);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			message = "验证码输入错误！";
			throw new TransactionException(message);
		}
		
		if(!mc.getMobile().equals(phone)){
			message = "手机号输入错误！";
			throw new TransactionException(message);
		}
		
		if(!MobileCodeTypes.CODE.equals(mc.getType())){
			message = "验证码输入错误！";
			throw new TransactionException(message);
		}
		
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			message = "验证码超时！";
			throw new TransactionException(message);
		}
		
		String realMD5Str = MD5.getMD5(Utlity.KEY_FRONT_MD5+time+mc.getMobile()+mc.getCode());
		if(md5Str.equals(realMD5Str)){//登录成功
			message = "登录成功";
			result.put("result", true);
			result.put("message", message);
			result.put("frontUser", su);
			return result;
		} else {
			throw new TransactionException(message);
		}
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUser", key = "'frontUser:' + #frontUser.uuid")})
	public void certification(String idcard, String username, FrontUser frontUser) throws TransactionException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(!frontUser.getRealnameflag()){
			result = JuHeUtility.certification(idcard, username);
			if((Boolean)result.get("result")){
				//信息更新+入库
				frontUser.setIdcard(idcard);
				frontUser.setRealname(username);
				frontUser.setRealnameflag(true);
				//增加性别
				if(IDCardUtil.getSex(idcard) == 1){
					frontUser.setSex(Utlity.SEX_MAN);
				} else {
					frontUser.setSex(Utlity.SEX_WOMAN);
				}
				this.update(frontUser);
			} else {
				throw new TransactionException(result.get("message").toString());
			}
		}
	}
}
