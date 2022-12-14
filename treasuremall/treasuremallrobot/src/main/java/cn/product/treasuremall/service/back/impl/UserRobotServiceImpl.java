package cn.product.treasuremall.service.back.impl;

import java.math.BigDecimal;
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
import cn.product.treasuremall.dao.AdminDao;
import cn.product.treasuremall.dao.ChannelDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.dao.RobotSettingDao;
import cn.product.treasuremall.entity.AdminOffsetOrder;
import cn.product.treasuremall.entity.AdminOffsetOrder.AdminOffsetOrderStatus;
import cn.product.treasuremall.entity.AdminOffsetOrder.AdminOffsetOrderType;
import cn.product.treasuremall.entity.Channel;
import cn.product.treasuremall.entity.Channel.ChannelId;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUser.FrontUserLevel;
import cn.product.treasuremall.entity.FrontUser.FrontUserStatus;
import cn.product.treasuremall.entity.FrontUser.FrontUserType;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.entity.RobotSetting;
import cn.product.treasuremall.entity.RobotSetting.RobotSettingStatus;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.service.back.UserRobotService;
import cn.product.treasuremall.util.DESUtil;
import cn.product.treasuremall.util.IpUtil;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.FrontUserRobotVO;

@Service("userRobotService")
public class UserRobotServiceImpl implements UserRobotService{
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
    private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
    private AdminDao adminDao;
	
	@Autowired
    private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
    private RobotSettingDao robotSettingDao;
	
	@Autowired
    private ResourceDao resourceDao;
	
	@Autowired
    private ChannelDao channelDao;
	
	@Autowired
    private IpUtil ipUtil;

	/**
	 * ????????????????????????
	 */
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		FrontUser fu = frontUserDao.get(uuid);
		if(fu == null || FrontUserStatus.DELETE.equals(fu.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????");
			return;
		}
		
		FrontUserAccount fua = this.frontUserAccountDao.get(fu.getUuid());
		FrontUserRobotVO fuvo = new FrontUserRobotVO(fu, fua);
		
		if(!Utlity.checkStringNull(fu.getImage())) {
			Resource image = resourceDao.get(fu.getImage());
			if(image != null) {
				fuvo.setImageURL(image.getUrl());
			}
		}
		
		result.setData(fuvo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	/**
	 * ??????????????????
	 */
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String realname = paramsMap.get("realname") == null ? "" : paramsMap.get("realname").toString();
		String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String showid = paramsMap.get("showid") == null ? "" : paramsMap.get("showid").toString();
		
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("realname", realname);
		searchMap.put("mobile", mobile);
		searchMap.put("status", status);  
		searchMap.put("type", FrontUserType.ROBOT);
		searchMap.put("showid", showid);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = frontUserDao.getCountByParams(searchMap);
		List<FrontUser> fuList = frontUserDao.getListByParams(searchMap);
		
		List<FrontUserRobotVO> voList = new ArrayList<FrontUserRobotVO>();
		for(FrontUser fu : fuList){
			FrontUserAccount fua = this.frontUserAccountDao.get(fu.getUuid());
			RobotSetting rs = this.robotSettingDao.get(fu.getUuid());
			
			FrontUserRobotVO fuvo = null;
			if(rs != null) {
				fuvo = new FrontUserRobotVO(fu, fua, rs);
			} else {
				fuvo = new FrontUserRobotVO(fu, fua);
			}
			
			if(!Utlity.checkStringNull(fu.getImage())) {
				Resource image = resourceDao.get(fu.getImage());
				if(image != null) {
					fuvo.setImageURL(image.getUrl());
				}
			}
			voList.add(fuvo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	/**
	 * ??????????????????
	 * ???????????????????????????????????????????????????????????????????????????
	 */
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String[] uuids = paramsMap.get("uuids") == null ? null : (String[])paramsMap.get("uuids");
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		if(!FrontUserStatus.NORMAL.equals(status) && !FrontUserStatus.DISABLE.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????");
			return;
		}
		try {
			Map<String, Object> updateMap = new HashMap<String, Object>();
			updateMap.put("status", status);
			
			if(uuids != null) {
				for(String ids : uuids) {
					FrontUser fu = frontUserDao.get(ids);
					if(fu == null || FrontUserStatus.DELETE.equals(fu.getStatus())){
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("???????????????");
						return;
					}
				}
				updateMap.put("uuid", uuids);
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("??????????????????");
				return;
			}
			
			this.frontUserDao.batchUpdateStatus(updateMap);
			//???????????????????????????
			if(uuids != null) {
				if(FrontUserStatus.NORMAL.equals(status)) {
					this.robotSettingDao.robotWorkReady(uuids);
				} else {
					this.robotSettingDao.robotWorkStop(uuids);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("?????????????????????");
			return;
		}

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("???????????????");
	}

	/**
	 * ??????
	 */
	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		FrontUser fu = frontUserDao.get(uuid);
		if(fu == null || FrontUserStatus.DELETE.equals(fu.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????");
			return;
		}
		
		if(FrontUserType.NORMAL.equals(fu.getType())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????");
			return;
		}
		
		try {
			Map<String, Object> updateMap = new HashMap<String, Object>();
			updateMap.put("status", FrontUserStatus.DELETE);
			updateMap.put("uuid", uuid.split(","));
			this.frontUserDao.batchUpdateStatus(updateMap);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("?????????????????????");
			return;
		}
	}

	/**
	 * ?????????????????????
	 * 	 * @param mobile
	 * @param nickname ??????????????????????????????????????????186****1111
	 * @param image
	 * @param pwdstr ???????????????????????????8???????????????
	 * @param ipstr ?????????????????????????????????????????????IP
	 */
	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
		if(Utlity.checkStringNull(mobile)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????????????????");
			return;
		}
		
		if(!Utlity.isMobileNO(mobile)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????????????????");
			return;
		}
		
		//???????????????????????????
		if(this.frontUserDao.isExistFrontUserByMobile(mobile, null)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("?????????????????????");
			return;
		}
		String pwdstr = paramsMap.get("pwdstr") == null ? "" : paramsMap.get("pwdstr").toString();
		
		String nickname = paramsMap.get("nickname") == null ? "" : paramsMap.get("nickname").toString();
		if(Utlity.checkStringNull(nickname)) {
			nickname = Utlity.getStarMobile(mobile);
		}
		String image = paramsMap.get("image") == null ? "" : paramsMap.get("image").toString();
		
		String ipstr = paramsMap.get("ipstr") == null ? "" : paramsMap.get("ipstr").toString();
		if(Utlity.checkStringNull(ipstr)) {
			ipstr = ipUtil.getRandomIp();//????????????IP????????????
		}
		try {
			//????????????
//			DESUtil de = new DESUtil();
			if(Utlity.checkStringNull(pwdstr)) {
				pwdstr = Utlity.generateShortUUID();//????????????8???????????????
			}
			
			//??????showID
			Integer showId = this.frontUserDao.getCountAllByParams(new HashMap<String, Object>());
			if(showId != null) {
//				showId += Utlity.FRONT_USER_ROBOT_SHOW_ID_VALUE;
				showId = Integer.valueOf(showId.intValue() + Utlity.FRONT_USER_ROBOT_SHOW_ID_VALUE.intValue());
			}
			//????????????????????????
			FrontUser fu = new FrontUser();
			fu.setUuid(UUID.randomUUID().toString());
			fu.setMobile(mobile);
			fu.setNickname(nickname);
			if(!Utlity.checkStringNull(image)) {
				fu.setImage(image);
			}
			fu.setPassword(DESUtil.encryptStr(pwdstr));
			fu.setIp(ipstr);
			fu.setArea(ipUtil.getAreaByIp(ipstr));//??????IP??????
			fu.setCreatetime(new Timestamp(System.currentTimeMillis()));
			fu.setShowId(showId);//????????????ID??????
			fu.setRealnameflag(false);//???????????????
			fu.setType(FrontUserType.ROBOT);
			fu.setLevel(FrontUserLevel.REGISTERED);
			fu.setStatus(FrontUserStatus.DISABLE);//??????????????? ??????????????????
			
			//??????????????????
			Channel ch = this.channelDao.getById(ChannelId.CHANNEL_DEFAULT);
			if(ch != null) {
				fu.setRegisterChannel(ch.getUuid());//????????????--?????????????????????????????????UUID???????????????
			}
			this.frontUserDao.insertRobotUser(fu);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("?????????????????????");
			return;
		}
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("???????????????");
	}

	/**
	 * ?????????????????????--????????????
	 */
	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String nickname = paramsMap.get("nickname") == null ? "" : paramsMap.get("nickname").toString();
		String image = paramsMap.get("image") == null ? "" : paramsMap.get("image").toString();
		String pwdstr = paramsMap.get("pwdstr") == null ? "" : paramsMap.get("pwdstr").toString();
		String ipstr = paramsMap.get("ipstr") == null ? "" : paramsMap.get("ipstr").toString();
		try {
			//????????????????????????
			FrontUser fu = this.frontUserDao.get(uuid);
			if(fu == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("??????????????????");
				return;
			}
			if(Utlity.checkStringNull(nickname)) {
				nickname = Utlity.getStarMobile(fu.getMobile());
			}
			fu.setNickname(nickname);
			
			if(!Utlity.checkStringNull(image)) {
				fu.setImage(image);
			}
			
			if(!Utlity.checkStringNull(pwdstr) && !pwdstr.equals(fu.getPassword())) {
//				DESUtil de = new DESUtil();
				fu.setPassword(DESUtil.encryptStr(pwdstr));
			}
			if(!Utlity.checkStringNull(ipstr) && !ipstr.equals(fu.getIp())) {
				fu.setIp(ipstr);
				fu.setArea(ipUtil.getAreaByIp(ipstr));//??????IP??????
			}
			
			this.frontUserDao.update(fu);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("?????????????????????");
			return;
		}
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("???????????????");
	}

	/**
	 * ???????????????????????????
	 */
	@Override
	public void settingadd(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String[] uuids = paramsMap.get("uuids") == null ? null : (String[])paramsMap.get("uuids");
		RobotSetting robot = paramsMap.get("robot") == null ? null : (RobotSetting)paramsMap.get("robot");
		if(robot == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????");
			return;
		}
		
		try {
			if(uuids != null) {
				List<RobotSetting> insert = new ArrayList<RobotSetting>();
				List<RobotSetting> update = new ArrayList<RobotSetting>();
				for(String ids : uuids) {
					FrontUser fu = frontUserDao.get(ids);
					if(fu == null || FrontUserStatus.DELETE.equals(fu.getStatus())){
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("???????????????");
						return;
					}
					RobotSetting rs = new RobotSetting();
					rs.setRobotId(ids);
					rs.setGameType(robot.getGameType());
					rs.setGoods(robot.getGoods());
					rs.setGoodsPriceMin(robot.getGoodsPriceMin());
					rs.setGoodsPriceMax(robot.getGoodsPriceMax());
					rs.setMaxPay(robot.getMaxPay());
					rs.setMinPay(robot.getMinPay());
					rs.setPeriodMin(robot.getPeriodMin());
					rs.setPeriodRandom(robot.getPeriodRandom());
					rs.setWorktimeBegin(robot.getWorktimeBegin());
					rs.setWorktimeEnd(robot.getWorktimeEnd());
					rs.setStatus(RobotSettingStatus.STOP);
					rs.setIsAll(false);
					
					//??????????????????
					if(this.robotSettingDao.get(ids) != null) {
						update.add(rs);
					} else {
						insert.add(rs);
					}
				}
				this.robotSettingDao.batchSetting(insert, update);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("???????????????");
				
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("??????????????????");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????");
			return;
		}
	}

	/**
	 * ????????????
	 */
	@Override
	public void isAll(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();		
		Boolean isAll = paramsMap.get("isAll") == null ? false : Boolean.valueOf(paramsMap.get("isAll").toString());
		try {
			RobotSetting rs = this.robotSettingDao.get(uuid);
			if(rs == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("?????????????????????????????????");
				return;
			}
			rs.setIsAll(isAll);
			this.robotSettingDao.update(rs);
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("???????????????");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("?????????????????????");
			return;
		}
	}
	
	/**
	 * ???????????????????????????
	 */
	@Override
	public void settingget(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String[] uuids = paramsMap.get("uuids") == null ? null : (String[])paramsMap.get("uuids");
		
		try {
			if(uuids != null) {
				String ids = uuids[0];
				RobotSetting rs = this.robotSettingDao.get(ids);
				if(rs != null) {
					result.setData(rs);
					result.setStatus(ResultStatusType.SUCCESS);
					result.setMessage("???????????????");
					return;
				} else {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("??????????????????????????????");
					return;
				}
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("??????????????????");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????");
			return;
		}
	}

	/**
	 * ????????????
	 */
	@Override
	public void goldadd(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String[] uuids = paramsMap.get("uuids") == null ? null : (String[])paramsMap.get("uuids");
		BigDecimal fee = paramsMap.get("fee") == null ? null : BigDecimal.valueOf(Double.valueOf(paramsMap.get("fee").toString()));
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		try {
			if(uuids != null) {
				List<FrontUserAccount> listAccount = new ArrayList<FrontUserAccount>();
				List<FrontUserHistory> listHistory = new ArrayList<FrontUserHistory>();
				List<AdminOffsetOrder> listOrder = new ArrayList<AdminOffsetOrder>();
				for(String ids : uuids) {
					//????????????????????????
					FrontUserAccount fua = this.frontUserAccountDao.get(ids);
					if(fua == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("????????????????????????");
						return;
					}
//					if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
//			    		result.setStatus(ResultStatusType.FAILED);
//						result.setMessage("?????????????????????????????????");
//						return;
//			    	}
					
					//????????????
					AdminOffsetOrder aoo = new AdminOffsetOrder();
					aoo.setUuid(UUID.randomUUID().toString());
					aoo.setFrontUser(ids);
					aoo.setCreatetime(new Timestamp(System.currentTimeMillis()));
					aoo.setOrderNum(Utlity.getOrderNum());
					aoo.setOrderType(Constants.ORDER_TYPE_SYSTEM_ADD);
					aoo.setType(AdminOffsetOrderType.ADMIN_ADD);//?????????
					aoo.setdAmount(fee);
					aoo.setRemark(Constants.ORDER_REASON_SYSTEM_ADD);
					aoo.setOperator(admin);
					aoo.setOperattime(new Timestamp(System.currentTimeMillis()));
					aoo.setReason(Constants.ORDER_REASON_SYSTEM_ADD);
					aoo.setStatus(AdminOffsetOrderStatus.CHECKED);
					
					//??????????????????
					FrontUserHistory fuh = new FrontUserHistory();
					fuh.setUuid(UUID.randomUUID().toString());
					fuh.setFrontUser(ids);
					fuh.setOrderNum(aoo.getOrderNum());//
					fuh.setOrderId(aoo.getUuid());
					fuh.setType(FrontUserHistoryType.USER_ADD);
					fuh.setOrderType(Constants.ORDER_TYPE_SYSTEM_ADD);
					fuh.setReason(Constants.ORDER_REASON_SYSTEM_ADD);
					fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
					fuh.setdAmount(fee);
					fuh.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
					fuh.setBalanceAfter(fua.getBalance().add(fua.getBalance()).add(fee));
					
					fuh.setRemark(Constants.ORDER_REASON_SYSTEM_ADD);
					//??????????????????
					fua.setBalance(fua.getBalance().add(fee));//?????? lock
					fua.setTotalRecharge(fua.getTotalRecharge().add(fee));
					
					listAccount.add(fua);
					listHistory.add(fuh);
					listOrder.add(aoo);
				}
				//????????????
				this.frontUserHistoryDao.recharge(listOrder, listHistory, listAccount);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("???????????????");
				return;
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("?????????????????????");
			return;
		}
	}

	/**
	 * ????????????
	 */
	@Override
	public void goldsub(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String[] uuids = paramsMap.get("uuids") == null ? null : (String[])paramsMap.get("uuids");
		BigDecimal fee = paramsMap.get("fee") == null ? null : BigDecimal.valueOf(Double.valueOf(paramsMap.get("fee").toString()));
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		try {
			if(uuids != null) {
				List<FrontUserAccount> listAccount = new ArrayList<FrontUserAccount>();
				List<FrontUserHistory> listHistory = new ArrayList<FrontUserHistory>();
				List<AdminOffsetOrder> listOrder = new ArrayList<AdminOffsetOrder>();
				for(String ids : uuids) {
					//????????????????????????
					FrontUserAccount fua = this.frontUserAccountDao.get(ids);
					if(fua == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("????????????????????????");
						return;
					}
//					if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
//			    		result.setStatus(ResultStatusType.FAILED);
//						result.setMessage("?????????????????????????????????");
//						return;
//			    	}
					//??????????????????????????????
					if(fee.compareTo(fua.getBalance()) > 0) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("???????????????????????????");
						return;
					}
					
					//????????????
					AdminOffsetOrder aoo = new AdminOffsetOrder();
					aoo.setUuid(UUID.randomUUID().toString());
					aoo.setFrontUser(ids);
					aoo.setCreatetime(new Timestamp(System.currentTimeMillis()));
					aoo.setOrderNum(Utlity.getOrderNum());
					aoo.setOrderType(Constants.ORDER_TYPE_SYSTEM_SUB);
					aoo.setType(AdminOffsetOrderType.ADMIN_SUB);//?????????
					aoo.setdAmount(fee);
					aoo.setRemark(Constants.ORDER_REASON_SYSTEM_SUB);
					aoo.setOperator(admin);
					aoo.setOperattime(new Timestamp(System.currentTimeMillis()));
					aoo.setReason(Constants.ORDER_REASON_SYSTEM_SUB);
					aoo.setStatus(AdminOffsetOrderStatus.CHECKED);
					
					//??????????????????
					FrontUserHistory fuh = new FrontUserHistory();
					fuh.setUuid(UUID.randomUUID().toString());
					fuh.setFrontUser(ids);
					fuh.setOrderNum(aoo.getOrderNum());//
					fuh.setOrderId(aoo.getUuid());
					fuh.setType(FrontUserHistoryType.USER_SUB);
					fuh.setOrderType(Constants.ORDER_TYPE_SYSTEM_SUB);
					fuh.setReason(Constants.ORDER_REASON_SYSTEM_SUB);
					fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
					fuh.setdAmount(fee);
					fuh.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
					fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()).subtract(fee));
					
					fuh.setRemark(Constants.ORDER_REASON_SYSTEM_SUB);
					//??????????????????
					fua.setBalance(fua.getBalance().subtract(fee));//????????? ???lock
					fua.setTotalWithdraw(fua.getTotalWithdraw().add(fee));
					
					listAccount.add(fua);
					listHistory.add(fuh);
					listOrder.add(aoo);
				}
				//????????????
				this.frontUserHistoryDao.recharge(listOrder, listHistory, listAccount);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("???????????????");
				return;
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("???????????????");
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("?????????????????????");
			return;
		}
	}
}
