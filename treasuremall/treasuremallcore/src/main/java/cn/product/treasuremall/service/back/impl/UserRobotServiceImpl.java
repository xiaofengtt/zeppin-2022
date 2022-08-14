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
	 * 获取用户基本信息
	 */
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		FrontUser fu = frontUserDao.get(uuid);
		if(fu == null || FrontUserStatus.DELETE.equals(fu.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户不存在");
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
	 * 获取列表信息
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
		String gameType = paramsMap.get("gameType") == null ? "" : paramsMap.get("gameType").toString();
		
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("realname", realname);
		searchMap.put("mobile", mobile);
		searchMap.put("status", status);  
		searchMap.put("type", FrontUserType.ROBOT);
		searchMap.put("showid", showid);
		searchMap.put("gameType", gameType);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = frontUserDao.getRobotCountByParams(searchMap);
		List<FrontUser> fuList = frontUserDao.getRobotListByParams(searchMap);
		
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
	 * 批量启用停用
	 * 启用时，判断是否有工作信息设置，如没有，提示去设置
	 */
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String[] uuids = paramsMap.get("uuids") == null ? null : (String[])paramsMap.get("uuids");
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		List<String> uuidList = new ArrayList<String>();
		if(!FrontUserStatus.NORMAL.equals(status) && !FrontUserStatus.DISABLE.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("变更状态有误");
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
						result.setMessage("用户不存在");
						return;
					}
					if(!FrontUserStatus.NORMAL.equals(fu.getStatus())) {
						uuidList.add(ids);
					}
				}
				
				updateMap.put("uuid", uuids);
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户参数错误");
				return;
			}
			
			this.frontUserDao.batchUpdateStatus(updateMap);
			//异步启动机器人工作
			if(uuids != null) {
				if(FrontUserStatus.NORMAL.equals(status)) {
					StringBuilder sb = new StringBuilder();
					if(uuidList != null && uuidList.size() > 0) {
						for(String ids : uuidList) {
							sb.append(ids);
							sb.append(",");
						}
						sb.delete(sb.length() - 1, sb.length());
						String[] uuidStart = sb.toString().split(",");
						this.robotSettingDao.robotWorkReady(uuidStart);
					}
				} else {
					this.robotSettingDao.robotWorkStop(uuids);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("修改过程异常！");
			return;
		}

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}

	/**
	 * 删除
	 */
	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		FrontUser fu = frontUserDao.get(uuid);
		if(fu == null || FrontUserStatus.DELETE.equals(fu.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户不存在");
			return;
		}
		
		if(FrontUserType.NORMAL.equals(fu.getType())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户类型异常");
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
			result.setMessage("删除过程异常！");
			return;
		}
	}

	/**
	 * 添加机器人用户
	 * 	 * @param mobile
	 * @param nickname 不填默认手机号加密后信息，如186****1111
	 * @param image
	 * @param pwdstr 不填写默认自动生成8位随机密码
	 * @param ipstr 不填写默认自动生成国内随机省份IP
	 */
	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
		if(Utlity.checkStringNull(mobile)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("请输入用户手机号！");
			return;
		}
		
		if(!Utlity.isMobileNO(mobile)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户手机号格式错误！");
			return;
		}
		
		//判断手机号是否重复
		if(this.frontUserDao.isExistFrontUserByMobile(mobile, null)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("手机号已存在！");
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
			ipstr = ipUtil.getRandomIp();//生成随机IP地址方法
		}
		try {
			//生成密码
//			DESUtil de = new DESUtil();
			if(Utlity.checkStringNull(pwdstr)) {
				pwdstr = Utlity.generateShortUUID();//生成随机8位密码方法
			}
			
			//生成showID
			Integer showId = this.frontUserDao.getCountAllByParams(new HashMap<String, Object>());
			if(showId != null) {
//				showId += Utlity.FRONT_USER_ROBOT_SHOW_ID_VALUE;
				showId = Integer.valueOf(showId.intValue() + Utlity.FRONT_USER_NORMAL_SHOW_ID_VALUE.intValue());
			}
			//用户基本信息入库
			FrontUser fu = new FrontUser();
			fu.setUuid(UUID.randomUUID().toString());
			fu.setMobile(mobile);
			fu.setNickname(nickname);
			if(!Utlity.checkStringNull(image)) {
				fu.setImage(image);
			}
			fu.setPassword(DESUtil.encryptStr(pwdstr));
			fu.setIp(ipstr);
			fu.setArea(ipUtil.getAreaByIp(ipstr));//根据IP生成
			fu.setCreatetime(new Timestamp(System.currentTimeMillis()));
			fu.setShowId(showId);//生成用户ID算法
			fu.setRealnameflag(false);//默认未实名
			fu.setType(FrontUserType.ROBOT);
			fu.setLevel(FrontUserLevel.REGISTERED);
			fu.setStatus(FrontUserStatus.DISABLE);//初始未启用 需要手动启用
			
			//记录渠道信息
			Channel ch = this.channelDao.getById(ChannelId.CHANNEL_DEFAULT);
			if(ch != null) {
				fu.setRegisterChannel(ch.getUuid());//未知渠道--如果有需要，将未知渠道UUID写入常量池
			}
			this.frontUserDao.insertRobotUser(fu);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("保存过程异常！");
			return;
		}
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	/**
	 * 编辑机器人用户--基本信息
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
			//用户基本信息入库
			FrontUser fu = this.frontUserDao.get(uuid);
			if(fu == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户不存在！");
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
				fu.setArea(ipUtil.getAreaByIp(ipstr));//根据IP生成
			}
			
			this.frontUserDao.update(fu);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("保存过程异常！");
			return;
		}
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	/**
	 * 设置机器人工作信息
	 */
	@Override
	public void settingadd(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String[] uuids = paramsMap.get("uuids") == null ? null : (String[])paramsMap.get("uuids");
		RobotSetting robot = paramsMap.get("robot") == null ? null : (RobotSetting)paramsMap.get("robot");
		if(robot == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("参数异常！");
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
						result.setMessage("用户不存在");
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
					
					//判断是否存在
					if(this.robotSettingDao.get(ids) != null) {
						update.add(rs);
					} else {
						insert.add(rs);
					}
				}
				this.robotSettingDao.batchSetting(insert, update);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("设置成功！");
				
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户参数错误");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常！");
			return;
		}
	}

	/**
	 * 是否包尾
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
				result.setMessage("机器人工作信息不存在！");
				return;
			}
			rs.setIsAll(isAll);
			this.robotSettingDao.update(rs);
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("设置成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("保存过程异常！");
			return;
		}
	}
	
	/**
	 * 查询机器人设置信息
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
					result.setMessage("查询成功！");
					return;
				} else {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("机器人设置信息不存在");
					return;
				}
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户参数错误");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常！");
			return;
		}
	}

	/**
	 * 系统加币
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
					//查询当前账户对象
					FrontUserAccount fua = this.frontUserAccountDao.get(ids);
					if(fua == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("用户账户不存在！");
						return;
					}
//					if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
//			    		result.setStatus(ResultStatusType.FAILED);
//						result.setMessage("用户账户异常，操作失败");
//						return;
//			    	}
					
					//封装订单
					AdminOffsetOrder aoo = new AdminOffsetOrder();
					aoo.setUuid(UUID.randomUUID().toString());
					aoo.setFrontUser(ids);
					aoo.setCreatetime(new Timestamp(System.currentTimeMillis()));
					aoo.setOrderNum(Utlity.getOrderNum());
					aoo.setOrderType(Constants.ORDER_TYPE_SYSTEM_ADD);
					aoo.setType(AdminOffsetOrderType.ADMIN_ADD);//待改动
					aoo.setdAmount(fee);
					aoo.setRemark(Constants.ORDER_REASON_SYSTEM_ADD);
					aoo.setOperator(admin);
					aoo.setOperattime(new Timestamp(System.currentTimeMillis()));
					aoo.setReason(Constants.ORDER_REASON_SYSTEM_ADD);
					aoo.setStatus(AdminOffsetOrderStatus.CHECKED);
					
					//封装交易流水
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
					//更新账户余额
					fua.setBalance(fua.getBalance().add(fee));//加币 lock
					fua.setTotalRecharge(fua.getTotalRecharge().add(fee));
					
					listAccount.add(fua);
					listHistory.add(fuh);
					listOrder.add(aoo);
				}
				//入库保存
				this.frontUserHistoryDao.recharge(listOrder, listHistory, listAccount);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("操作成功！");
				return;
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("参数异常！");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("保存过程异常！");
			return;
		}
	}

	/**
	 * 系统减币
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
					//查询当前账户对象
					FrontUserAccount fua = this.frontUserAccountDao.get(ids);
					if(fua == null) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("用户账户不存在！");
						return;
					}
//					if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
//			    		result.setStatus(ResultStatusType.FAILED);
//						result.setMessage("用户账户异常，操作失败");
//						return;
//			    	}
					//判断是否有余额可扣减
					if(fee.compareTo(fua.getBalance()) > 0) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("用户账户余额不足！");
						return;
					}
					
					//封装订单
					AdminOffsetOrder aoo = new AdminOffsetOrder();
					aoo.setUuid(UUID.randomUUID().toString());
					aoo.setFrontUser(ids);
					aoo.setCreatetime(new Timestamp(System.currentTimeMillis()));
					aoo.setOrderNum(Utlity.getOrderNum());
					aoo.setOrderType(Constants.ORDER_TYPE_SYSTEM_SUB);
					aoo.setType(AdminOffsetOrderType.ADMIN_SUB);//待改动
					aoo.setdAmount(fee);
					aoo.setRemark(Constants.ORDER_REASON_SYSTEM_SUB);
					aoo.setOperator(admin);
					aoo.setOperattime(new Timestamp(System.currentTimeMillis()));
					aoo.setReason(Constants.ORDER_REASON_SYSTEM_SUB);
					aoo.setStatus(AdminOffsetOrderStatus.CHECKED);
					
					//封装交易流水
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
					//更新账户余额
					fua.setBalance(fua.getBalance().subtract(fee));//直接减 不lock
					fua.setTotalWithdraw(fua.getTotalWithdraw().add(fee));
					
					listAccount.add(fua);
					listHistory.add(fuh);
					listOrder.add(aoo);
				}
				//入库保存
				this.frontUserHistoryDao.recharge(listOrder, listHistory, listAccount);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("操作成功！");
				return;
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("参数异常！");
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("保存过程异常！");
			return;
		}
	}
}
