package cn.product.worldmall.aotutask;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserRanklistDao;
import cn.product.worldmall.dao.FrontUserVoucherDao;
import cn.product.worldmall.dao.GoodsIssueSharesnumDao;
import cn.product.worldmall.dao.LuckygameGoodsIssueDao;
import cn.product.worldmall.dao.NoticePublicDao;
import cn.product.worldmall.dao.WinningInfoDao;
import cn.product.worldmall.dao.WinningInfoReceiveDao;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUser.FrontUserType;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.worldmall.entity.FrontUserVoucher;
import cn.product.worldmall.entity.FrontUserVoucher.FrontUserVoucherStatus;
import cn.product.worldmall.entity.GoodsIssueSharesnum;
import cn.product.worldmall.entity.LuckygameGoodsIssue;
import cn.product.worldmall.entity.NoticePublic;
import cn.product.worldmall.entity.NoticePublic.NoticePublicStatus;
import cn.product.worldmall.entity.WinningInfo;
import cn.product.worldmall.entity.WinningInfo.WinningInfoType;
import cn.product.worldmall.entity.WinningInfoReceive;
import cn.product.worldmall.entity.WinningInfoReceive.WinningInfoReceiveStatus;
import cn.product.worldmall.entity.WinningInfoReceive.WinningInfoReceiveType;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.util.IpUtil;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.SharenumsVO;

@Component
public class DailyTask {
	
	private final static Logger log = LoggerFactory.getLogger(DailyTask.class);
	
	@Autowired
	private FrontUserRanklistDao frontUserRanklistDao;
	
	@Autowired
    private WinningInfoDao winningInfoDao;
	
	@Autowired
    private WinningInfoReceiveDao winningInfoReceiveDao;

	@Autowired
    private LuckygameGoodsIssueDao luckygameGoodsIssueDao;

	@Autowired
    private GoodsIssueSharesnumDao goodsIssueSharesnumDao;

	@Autowired
    private FrontUserDao frontUserDao;

	@Autowired
    private NoticePublicDao noticePublicDao;

	@Autowired
    private FrontUserVoucherDao frontUserVoucherDao;

	@Autowired
    private FrontUserAccountDao frontUserAccountDao;

	@Autowired
    private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
    private IpUtil ipUtil;
	/**
	 * 公告自动上线
	 */
	@Scheduled(cron="0 0/2 *  * * * ")
	public void noticePublicTash() {
		try {
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("status", NoticePublicStatus.OFFLINE);
			
			//查询符合条件的公告信息列表
			List<NoticePublic> list = noticePublicDao.getListByParams(searchMap);
			List<NoticePublic> update = new ArrayList<NoticePublic>();
			if(list != null && list.size() > 0) {
				for(NoticePublic np : list) {
					//判断日期
					long nowTime = System.currentTimeMillis();
					long onlineTime = np.getOnlinetime().getTime();
					if(onlineTime > nowTime) {
						np.setStatus(NoticePublicStatus.ONLINE);
						update.add(np);
					}
				}
			}
			this.noticePublicDao.updateTask(update);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 红包处理（过期--生效）
	 */
	@Scheduled(cron="0 0/1 *  * * * ")
	public void voucherTask() {
		try {
			//查询normal和unstart的红包列表
			//normal--检查过期的 置为disable
			//unstart--检查预计到期的置为normal
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("statusArr", "('"+FrontUserVoucherStatus.UNSTART+"','"+FrontUserVoucherStatus.NORMAL+"')");
			searchMap.put("sort", "createtime desc");
			
			List<FrontUserVoucher> list = frontUserVoucherDao.getListByParams(searchMap);
//			Integer totalCount = this.frontUserVoucherDao.getCountByParams(searchMap);
			List<FrontUserVoucher> update = new ArrayList<FrontUserVoucher>();
			if(list != null && list.size() > 0) {
				for(FrontUserVoucher fuv : list) {
					//判断日期
					long nowTime = System.currentTimeMillis();
					long starttime = fuv.getStarttime() == null ? 0L : fuv.getStarttime().getTime();
					long endtime = fuv.getEndtime() == null ? 0L : fuv.getEndtime().getTime();
					if(FrontUserVoucherStatus.UNSTART.equals(fuv.getStatus())) {
						if(endtime == 0) {
							fuv.setStatus(FrontUserVoucherStatus.NORMAL);
							update.add(fuv);
							continue;
						}
						if(nowTime >= starttime && endtime >= nowTime ) {
							fuv.setStatus(FrontUserVoucherStatus.NORMAL);
							update.add(fuv);
						} else if(endtime < nowTime) {
							fuv.setStatus(FrontUserVoucherStatus.OVERTIME);
							update.add(fuv);
						}
					} else {
						if(endtime != 0 && endtime < nowTime) {
							fuv.setStatus(FrontUserVoucherStatus.OVERTIME);
							update.add(fuv);
						}
					}
				}
			}
			this.frontUserDao.updateVoucherTask(update);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 机器人自动兑奖
	 */
	@Scheduled(cron="0 0 23  * * ? ")
	public void robotReceiveTask() {
		try {
			List<WinningInfo> updateWin = new ArrayList<WinningInfo>();
			List<WinningInfoReceive> insertReceive = new ArrayList<WinningInfoReceive>();
			List<FrontUserHistory> insertHistory = new ArrayList<FrontUserHistory>();
			Map<String, FrontUserAccount> fuMap = new HashMap<String, FrontUserAccount>();
			//查询normal中奖列表
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("frontUserType", FrontUserType.ROBOT);
			searchMap.put("type", WinningInfoType.NORMAL);
			List<WinningInfo> list = this.winningInfoDao.getListByRobotParams(searchMap);
			//全部兑换金币
			if(list != null && list.size() > 0) {
				for(WinningInfo wi : list) {
					FrontUser fu = this.frontUserDao.get(wi.getFrontUser());
					LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(wi.getGoodsIssue());
					if(lgi == null) {
						log.info("--------------兑奖商品信息错误---------------");
						continue;
					}
					
					WinningInfoReceive wir = new WinningInfoReceive();
					wir.setWinningInfo(wi.getUuid());
					wir.setCreatetime(new Timestamp(System.currentTimeMillis()));
					wir.setFrontUser(wi.getFrontUser());
					wir.setGoodsId(lgi.getGoodsId());
					wir.setIp(fu.getIp());
					wir.setOrderId(wi.getOrderId());
					wir.setShowId(fu.getShowId());
					wir.setStatus(WinningInfoReceiveStatus.FINISHED);
					wir.setType(WinningInfoReceiveType.GOLD);
					
					wi.setType(WinningInfoType.GOLD);
					
					//查询当前账户对象
					FrontUserAccount fuacc = null;
					if(fuMap.containsKey(wi.getFrontUser())) {
						fuacc = fuMap.get(wi.getFrontUser());
					} else {
						fuacc = this.frontUserAccountDao.get(wi.getFrontUser());
					}
					if(fuacc == null) {
						log.info("--------------用户账户错误---------------");
						continue;
					}
					
					//封装交易流水
					FrontUserHistory fuh = new FrontUserHistory();
					fuh.setUuid(UUID.randomUUID().toString());
					fuh.setFrontUser(wi.getFrontUser());
					fuh.setOrderNum(Utlity.getOrderNum());//
					fuh.setType(FrontUserHistoryType.USER_ADD);
					fuh.setOrderType(Constants.ORDER_TYPE_USER_EXCHANGE);
					fuh.setOrderNum(Utlity.getOrderNum());//生成字符串订单号
					fuh.setOrderId(wi.getOrderId());
					fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_EXCHANGE));
					fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
					fuh.setdAmount(wi.getDealPrice());
					fuh.setBalanceBefore(fuacc.getBalance().add(fuacc.getBalanceLock()));
					fuh.setBalanceAfter(fuacc.getBalance().add(fuacc.getBalanceLock()).add(wi.getDealPrice()));
					fuh.setRemark(Constants.ORDER_REASON_SYSTEM_ADD);
					//更新账户余额
					fuacc.setBalance(fuacc.getBalance().add(wi.getDealPrice()));//加币
					fuacc.setTotalExchange(fuacc.getTotalRecharge().add(wi.getDealPrice()));//总兑奖金额
					fuacc.setExchangeTimes(fuacc.getExchangeTimes() + 1);
					
					fuMap.put(fuacc.getFrontUser(), fuacc);
					updateWin.add(wi);
					insertReceive.add(wir);
					insertHistory.add(fuh);
				}
				this.frontUserHistoryDao.receiveByRobot(fuMap, updateWin, insertReceive, insertHistory);
			}
			//入账
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加机器人
	 * @throws Exception 
	 */
//	@Scheduled(cron="0 0/2 *  * * * ")
	public void addRobot() throws Exception {
//		String[] mobileArr = {};
//		int i = 0;
//		for(String mobile : mobileArr) {
//			i++;
//			System.out.println(i);
//			//生成密码
////			DESUtil de = new DESUtil();
//			String pwdstr = Utlity.generateShortUUID();//生成随机8位密码方法
//			String nickname = Utlity.getStarMobile(mobile);
//			String ipstr = ipUtil.getRandomIp();
//			//生成showID
//			Integer showId = this.frontUserDao.getCountAllByParams(new HashMap<String, Object>());
//			if(showId != null) {
////				showId += Utlity.FRONT_USER_ROBOT_SHOW_ID_VALUE;
//				showId = Integer.valueOf(showId.intValue() + 1000035);
//			}
//			//用户基本信息入库
//			FrontUser fu = new FrontUser();
//			fu.setUuid(UUID.randomUUID().toString());
//			fu.setMobile(mobile);
//			fu.setNickname(nickname);
//			fu.setPassword(DESUtil.encryptStr(pwdstr));
//			fu.setIp(ipstr);
//			fu.setArea(ipUtil.getAreaByIp(ipstr));//根据IP生成
//			fu.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			fu.setShowId(showId);//生成用户ID算法
//			fu.setRealnameflag(false);//默认未实名
//			fu.setType(FrontUserType.ROBOT);
//			fu.setLevel(FrontUserLevel.REGISTERED);
//			fu.setStatus(FrontUserStatus.DISABLE);//初始未启用 需要手动启用
////			fu.setRegisterChannel("");//未知渠道--如果有需要，将未知渠道UUID写入常量池
//			this.frontUserDao.insertRobotUser(fu);
//		}
	}
	

	/**
	 * 添加机器人
	 * @throws Exception 
	 */
//	@Scheduled(cron="0 0/1 *  * * * ")
	public void check() throws Exception {
		List<LuckygameGoodsIssue> lgiList = this.luckygameGoodsIssueDao.getListByParams(new HashMap<String, Object>());
		for(LuckygameGoodsIssue lgi : lgiList) {
			if(lgi.getRemainShares() > 0) {
				GoodsIssueSharesnum gis = this.goodsIssueSharesnumDao.get(lgi.getUuid());
				SharenumsVO svo = JSONUtils.json2obj(gis.getSharenums(), SharenumsVO.class);
				List<Integer> currentNums = svo.getCurrentNums();
//				List<Integer> usedNums = svo.getUsedNums();
				if(currentNums.size() == 0) {
					System.out.println("----------666------------"+lgi.getUuid());
				}
			}
		}
	}
}