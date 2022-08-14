package cn.product.worldmall.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.aws.sns.dao.AwsSnsClientDao;
import cn.product.worldmall.controller.base.TransactionException;
import cn.product.worldmall.dao.FrontDeviceTokenDao;
import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserBankcardDao;
import cn.product.worldmall.dao.FrontUserBlacklistDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserScoreHistoryDao;
import cn.product.worldmall.dao.FrontUserVoucherDao;
import cn.product.worldmall.dao.MobileCodeDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.dao.RobotSettingDao;
import cn.product.worldmall.entity.FrontDeviceToken;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUser.FrontUserLevel;
import cn.product.worldmall.entity.FrontUser.FrontUserStatus;
import cn.product.worldmall.entity.FrontUser.FrontUserType;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserAccount.FrontUserAccountStatus;
import cn.product.worldmall.entity.FrontUserBankcard;
import cn.product.worldmall.entity.FrontUserBankcard.FrontUserBankcardStatus;
import cn.product.worldmall.entity.FrontUserBankcard.FrontUserBankcardType;
import cn.product.worldmall.entity.FrontUserBlacklist;
import cn.product.worldmall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.worldmall.entity.FrontUserScoreHistory;
import cn.product.worldmall.entity.FrontUserVoucher;
import cn.product.worldmall.entity.MobileCode;
import cn.product.worldmall.entity.MobileCode.MobileCodeStatus;
import cn.product.worldmall.entity.RobotSetting.RobotSettingStatus;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.mapper.FrontUserMapper;
import cn.product.worldmall.rabbit.send.RabbitSenderService;
import cn.product.worldmall.util.IDCardUtil;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.util.alipay.check.RealmeCheckUtil;

@Component
public class FrontUserDaoImpl implements FrontUserDao{
	
	@Autowired
	private FrontUserMapper frontUserMapper;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private RobotSettingDao robotSettingDao;
	
	@Autowired
	private MobileCodeDao mobileCodeDao;
	
	@Autowired
	private FrontUserBankcardDao frontUserBankcardDao;
	
	@Autowired
	private FrontUserVoucherDao frontUserVoucherDao;

	@Autowired
	private FrontUserBlacklistDao frontUserBlacklistDao;

	@Autowired
	private FrontUserScoreHistoryDao frontUserScoreHistoryDao;
	
	@Autowired
	private FrontDeviceTokenDao frontDeviceTokenDao;
	
	@Autowired
	private AwsSnsClientDao awsSnsClientDao;
	
	@Autowired
	private RabbitSenderService rabbitSenderService;
	
	@Autowired
	private ResourceDao resourceDao;
	
//	@Autowired
//	private ImageUtils imageUtils;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUser> getListByParams(Map<String, Object> params) {
		return frontUserMapper.getListByParams(params);
	}
	
    @Override
    @Cacheable(cacheNames="frontUser",key="'frontUser:' + #mobile")
    public FrontUser getByMobile(String mobile) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile",mobile);
        params.put("status","login");
        List<FrontUser> frontUserList =  this.frontUserMapper.getListByParams(params);
        if(frontUserList.size()>0){
            return frontUserList.get(0);
        }
        return null;
    }

	@Override
	@Cacheable(cacheNames="frontUser",key="'frontUser:' + #openid")
	public FrontUser getByOpenid(String openid) {
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("openid",openid);
	    params.put("status","login");
	    List<FrontUser> frontUserList =  this.frontUserMapper.getListByParams(params);
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
	@Caching(evict={@CacheEvict(value = "frontUser", key = "'frontUser:' + #frontUser.uuid")
	,@CacheEvict(value = "frontUser", key = "'frontUser:' + #frontUser.mobile")
	,@CacheEvict(value = "frontUser", key = "'frontUser:' + #frontUser.openid")})
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
	@Caching(evict={@CacheEvict(value = "frontUser", key = "'frontUser:' + #fu.mobile")
	,@CacheEvict(value = "frontUser", key = "'frontUser:' + #fu.openid")})
	public FrontUser register(FrontUser fu, MobileCode mc){
		Integer showId = this.getCountAllByParams(new HashMap<String, Object>());
		if(showId != null) {
//			showId += Utlity.FRONT_USER_NORMAL_SHOW_ID_VALUE;
			showId = Integer.valueOf(showId.intValue() + Utlity.FRONT_USER_NORMAL_SHOW_ID_VALUE.intValue());
		}
		
		fu.setUuid(UUID.randomUUID().toString());
		fu.setShowId(showId);
//		fu.setNickname("+"+Utlity.getStarMobile(fu.getMobile()));
		if(Utlity.checkStringNull(fu.getNickname())) {//昵称生成规则，若手机号存在，则是**手机号，否则为showId
			if(Utlity.checkStringNull(fu.getMobile())) {
				fu.setNickname(Utlity.getNickname(fu.getShowId()));
			} else {
				fu.setNickname("+"+Utlity.getStarMobile(fu.getMobile()));
			}
		}
		fu.setRealnameflag(false);
		fu.setLevel(FrontUserLevel.REGISTERED);
		fu.setStatus(FrontUserStatus.NORMAL);
		fu.setType(FrontUserType.NORMAL);
		fu.setCreatetime(new Timestamp(System.currentTimeMillis()));
		fu.setLastonline(fu.getCreatetime());
		
		FrontUserAccount fua = new FrontUserAccount();
		fua.setFrontUser(fu.getUuid());
		fua.setBalance(BigDecimal.ZERO);
		fua.setBalanceLock(BigDecimal.ZERO);
		fua.setVoucherBalance(BigDecimal.ZERO);
		fua.setScoreBalance(BigDecimal.valueOf(100));//初始赠送1000积分，即免费提现额度1000
		fua.setAccountStatus(FrontUserAccountStatus.NORMAL);
		fua.setTotalRecharge(BigDecimal.ZERO);
		fua.setTotalWithdraw(BigDecimal.ZERO);
		fua.setTotalPayment(BigDecimal.ZERO);
		fua.setTotalWinning(BigDecimal.ZERO);
		fua.setTotalDelivery(BigDecimal.ZERO);
		fua.setTotalExchange(BigDecimal.ZERO);
		fua.setPaymentTimes(0);
		fua.setWinningTimes(0);
		fua.setRechargeTimes(0);
		fua.setWithdrawTimes(0);
		fua.setDeliveryTimes(0);
		fua.setExchangeTimes(0);
		
		
		//20200515新增积分记录
		FrontUserScoreHistory fush = new FrontUserScoreHistory();
		fush.setUuid(UUID.randomUUID().toString());
		fush.setFrontUser(fua.getFrontUser());
		fush.setFrontUserShowId(fu.getShowId());
		fush.setOrderNum(Utlity.getOrderNum());
		fush.setType(FrontUserHistoryType.USER_ADD);
		fush.setOrderType(Constants.ORDER_TYPE_USER_REGISTER);
		fush.setsAmount(BigDecimal.valueOf(100));
		fush.setScoreBalanceBefore(fua.getScoreBalance());
		fush.setScoreBalanceAfter(fua.getScoreBalance().add(BigDecimal.valueOf(100)));
		fush.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_REGISTER));
		fush.setCreatetime(fu.getCreatetime());
		fush.setRemark("Register gift");

//		//下载图片，并存储 -- 下载有延迟，暂时不启用
//		if(!Utlity.checkStringNull(fu.getWechaticon())) {
//			String url = fu.getWechaticon();
//			Map<String, Object> result = ImageUtils.downloadPicture(url);
//			if(result != null) {
//				if(result.containsKey(result) && result.get("result") != null) {
//					Resource re = (Resource)result.get("result");
//					if(re != null) {
//						this.resourceDao.insert(re);
//						fu.setImage(re.getUuid());
//					}
//				}
//			}
//		}
		
		if(mc != null) {
			mc.setStatus(MobileCodeStatus.DISABLE);
			this.mobileCodeDao.update(mc);
		}
		
		this.frontUserMapper.insert(fu);
		this.frontUserAccountDao.insert(fua);
		this.frontUserScoreHistoryDao.insert(fush);
		return fu;
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUser", key = "'frontUser:' + #frontUser.uuid")
	,@CacheEvict(value = "frontUser", key = "'frontUser:' + #frontUser.mobile")
	,@CacheEvict(value = "frontUser", key = "'frontUser:' + #frontUser.openid")})
	@Transactional
	public void certification(String bank, String bankcard, String mobile, String idcard, String username
			, FrontUser frontUser, MobileCode mc) throws TransactionException {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> querys = new HashMap<String, String>();
	    querys.put("bankcard", bankcard);
	    querys.put("idcard", idcard);
	    querys.put("mobile", mobile);
	    querys.put("name", username);
		result = RealmeCheckUtil.certification(querys);
		if((Boolean)result.get("result")){
			
			//信息更新+入库
			frontUser.setIdcard(idcard);
			frontUser.setRealname(username);
			//增加性别
			if(IDCardUtil.getSex(idcard) == 1){
				frontUser.setSex(Utlity.SEX_MAN);
			} else {
				frontUser.setSex(Utlity.SEX_WOMAN);
			}
			
			//银行卡信息入库
			FrontUserBankcard fub = new FrontUserBankcard();
			fub.setUuid(UUID.randomUUID().toString());
			fub.setCreatetime(new Timestamp(System.currentTimeMillis()));
			fub.setBank(bank);
			fub.setAccountNumber(bankcard);
			fub.setPhone(mobile);
			fub.setAccountHolder(username);
			fub.setStatus(FrontUserBankcardStatus.NORMAL);
			fub.setFrontUser(frontUser.getUuid());
			fub.setType(FrontUserBankcardType.DEBIT);
			
			//判断是否为实名认证
			if(!frontUser.getRealnameflag()) {//未实名，更新实名信息
				frontUser.setRealnameflag(true);
				this.frontUserMapper.updateByPrimaryKey(frontUser);
			}
			this.frontUserBankcardDao.insert(fub);
			this.mobileCodeDao.update(mc);
		} else {
			throw new TransactionException(result.get("message").toString());
		}
	}
	
	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUser", allEntries=true)})
	public void batchUpdateStatus(Map<String, Object> params) {
		this.frontUserMapper.updateStatus(params);
		this.frontUserAccountDao.updateStatus(params);
		
		//更新机器人工作状态
		if(FrontUserStatus.NORMAL.equals(params.get("status"))) {
			params.put("status", RobotSettingStatus.START);
		} else {
			params.put("status", RobotSettingStatus.STOP);
		}
		this.robotSettingDao.updateStatus(params);
	}

	@Override
	@Transactional
	public void insertRobotUser(FrontUser fu) {
		this.frontUserMapper.insert(fu);
		//用户账户信息入库
		FrontUserAccount fua = new FrontUserAccount();
		fua.setFrontUser(fu.getUuid());
		fua.setAccountStatus(fu.getStatus());
		//非空信息入库
		fua.setBalance(BigDecimal.ZERO);
		fua.setBalanceLock(BigDecimal.ZERO);
		fua.setVoucherBalance(BigDecimal.ZERO);
		fua.setScoreBalance(BigDecimal.ZERO);
		fua.setTotalDelivery(BigDecimal.ZERO);
		fua.setTotalExchange(BigDecimal.ZERO);
		fua.setTotalPayment(BigDecimal.ZERO);
		fua.setTotalRecharge(BigDecimal.ZERO);
		fua.setTotalWinning(BigDecimal.ZERO);
		fua.setTotalWithdraw(BigDecimal.ZERO);
		fua.setPaymentTimes(0);
		fua.setWinningTimes(0);
		fua.setRechargeTimes(0);
		fua.setWithdrawTimes(0);
		fua.setDeliveryTimes(0);
		fua.setExchangeTimes(0);
		
		this.frontUserAccountDao.insert(fua);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUser", key = "'frontUser:' + #frontUser.uuid")
	,@CacheEvict(value = "frontUser", key = "'frontUser:' + #frontUser.mobile", beforeInvocation = true)
	,@CacheEvict(value = "frontUser", key = "'frontUser:' + #mobile", beforeInvocation = true)
	,@CacheEvict(value = "frontUser", key = "'frontUser:' + #frontUser.openid", beforeInvocation = true)})
	public void updateFrontUser(FrontUser frontUser, String mobile, List<MobileCode> codeList) {
		if(!Utlity.checkStringNull(mobile)) {
			frontUser.setMobile(mobile);
		}
		this.frontUserMapper.updateByPrimaryKey(frontUser);
		for(MobileCode mc : codeList){
			this.mobileCodeDao.update(mc);
		}
	}

	@Override
	public Integer getCountAllByParams(Map<String, Object> params) {
		return this.frontUserMapper.getCountAllByParams(params);
	}

	@Override
	@Transactional
	public void updateVoucherTask(List<FrontUserVoucher> update) {
		if(update != null && update.size() > 0) {
			for(FrontUserVoucher fuv : update) {
				frontUserVoucherDao.update(fuv);
			}
		}
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUser", key = "'frontUser:' + #frontUser.uuid")
	,@CacheEvict(value = "frontUser", key = "'frontUser:' + #frontUser.mobile", beforeInvocation = true)
	,@CacheEvict(value = "frontUser", key = "'frontUser:' + #frontUser.openid", beforeInvocation = true)})
	public void updateBlacklist(FrontUser frontUser, FrontUserBlacklist fubl) {
		this.frontUserMapper.updateByPrimaryKey(frontUser);
		this.frontUserBlacklistDao.insert(fubl);		
	}

	@Override
	public List<Map<String, Object>> getRegistListByParams(Map<String, Object> params) {
		return frontUserMapper.getRegistListByParams(params);
	}

	@Override
	public Integer getRobotCountByParams(Map<String, Object> params) {
		return frontUserMapper.getRobotCountByParams(params);
	}

	@Override
	public List<FrontUser> getRobotListByParams(Map<String, Object> params) {
		return frontUserMapper.getRobotListByParams(params);
	}
	
	@Override
	@Transactional
	public void updateOrInsertDeviceToken(Boolean flag, FrontDeviceToken fdk, Map<String, FrontDeviceToken> mapUpdate) {
		
		if(mapUpdate != null && !mapUpdate.isEmpty()) {
			for(Entry<String, FrontDeviceToken> entry : mapUpdate.entrySet()) {
				this.frontDeviceTokenDao.update(entry.getValue());
			}
		}
		
		if(flag != null) {//update 操作
			//更新endpoint
			if(flag) {//异步更新endpoint
				this.rabbitSenderService.endpointarnStartMessageSend(fdk);
			} else {
				fdk.setUpdatetime(new Timestamp(System.currentTimeMillis()));
				this.frontDeviceTokenDao.update(fdk);
			}
		} else {//insert 操作
			this.rabbitSenderService.endpointarnStartMessageSend(fdk);
		}
	}

	@Override
	@Transactional
	public void updateOrInsertDeviceTokenBatch(List<FrontDeviceToken> listInsert, List<FrontDeviceToken> listUpdate) {
		if(listInsert != null && listInsert.size() > 0) {
			for(FrontDeviceToken fdt : listInsert) {
				this.frontDeviceTokenDao.insert(fdt);
			}
		}
		if(listUpdate != null && listUpdate.size() > 0) {
			for(FrontDeviceToken fdt : listUpdate) {
				this.frontDeviceTokenDao.update(fdt);
			}
		}
	}

	@Override
	public FrontUser getByShowId(String showId) {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("showid",showId);
        params.put("status","login");
        List<FrontUser> frontUserList =  this.frontUserMapper.getListByParams(params);
        if(frontUserList.size()>0){
            return frontUserList.get(0);
        }
        return null;
	}
}
