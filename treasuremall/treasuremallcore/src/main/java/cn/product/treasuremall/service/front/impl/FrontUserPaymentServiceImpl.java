package cn.product.treasuremall.service.front.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.controller.base.TransactionException;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserPaymentOrderDao;
import cn.product.treasuremall.dao.FrontUserVoucherDao;
import cn.product.treasuremall.dao.GoodsDao;
import cn.product.treasuremall.dao.GoodsIssueSharesnumDao;
import cn.product.treasuremall.dao.LuckygameGoodsDao;
import cn.product.treasuremall.dao.LuckygameGoodsIssueDao;
import cn.product.treasuremall.dao.RobotWinningRateDao;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUser.FrontUserStatus;
import cn.product.treasuremall.entity.FrontUser.FrontUserType;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserAccount.FrontUserAccountStatus;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.treasuremall.entity.FrontUserPaidNumber;
import cn.product.treasuremall.entity.FrontUserPaymentOrder;
import cn.product.treasuremall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderGroup;
import cn.product.treasuremall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderStatus;
import cn.product.treasuremall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderType;
import cn.product.treasuremall.entity.FrontUserScoreHistory;
import cn.product.treasuremall.entity.FrontUserScoreHistory.FrontUserScoreHistoryType;
import cn.product.treasuremall.entity.FrontUserVoucher;
import cn.product.treasuremall.entity.FrontUserVoucher.FrontUserVoucherStatus;
import cn.product.treasuremall.entity.Goods;
import cn.product.treasuremall.entity.GoodsIssueSharesnum;
import cn.product.treasuremall.entity.LuckygameGoods;
import cn.product.treasuremall.entity.LuckygameGoods.LuckygameGoodsStatus;
import cn.product.treasuremall.entity.LuckygameGoodsIssue;
import cn.product.treasuremall.entity.LuckygameGoodsIssue.LuckygameGoodsIssueStatus;
import cn.product.treasuremall.entity.RobotWinningRate;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.rabbit.send.RabbitSenderService;
import cn.product.treasuremall.service.front.FrontUserPaymentService;
import cn.product.treasuremall.util.Base64Util;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.util.lottery.LuckygameGoodsLotteryUtil;
import cn.product.treasuremall.vo.back.SharenumsPKVO;
import cn.product.treasuremall.vo.back.SharenumsVO;

@Service("frontUserPaymentService")
public class FrontUserPaymentServiceImpl implements FrontUserPaymentService{
	
	public static final Logger log= LoggerFactory.getLogger(FrontUserPaymentServiceImpl.class);
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
    @Autowired
    private GoodsDao goodsDao;
	
    @Autowired
    private LuckygameGoodsDao luckygameGoodsDao;
    
    @Autowired
    private LuckygameGoodsIssueDao luckygameGoodsIssueDao;
	
	@Autowired
	private FrontUserPaymentOrderDao frontUserPaymentOrderDao;
	
    @Autowired
    private CuratorFramework curatorFramework;
	
    @Autowired
    private FrontUserVoucherDao frontUserVoucherDao;

    @Autowired
    private GoodsIssueSharesnumDao goodsIssueSharesnumDao;

    @Autowired 
    private RabbitSenderService rabbitSenderService;

//    @Autowired
//    private FrontUserMessageDao frontUserMessageDao;

    @Autowired
    private RobotWinningRateDao robotWinningRateDao;

    @Autowired
    private LuckygameGoodsLotteryUtil luckygameGoodsLotteryUtil;
    
	/**
	 * 下单
	 * 下单逻辑：参照秒杀逻辑
	 * 对下单的商品，开启zk同步锁，顺序秒杀下单
	 * 获取前端下单参数：奖品ID、参与次数（buyCount）、金币总额、红包ID、实际支付金币额
	 * 需要判断：用户余额是否充足、商品剩余参与次数是否满足
	 * 需要的操作：创建订单、扣减账户余额、扣将商品次数、商品次数归零判断是否需要开启下一期、保存订单、保存账户信息、保存商品信息
	 * 20200722--增加战队玩法相关算法逻辑
	 */
	@Override
	public void placeOrder(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String buyCountStr = paramsMap.get("buyCount") == null ? "" : paramsMap.get("buyCount").toString();
    	String dAmountStr = paramsMap.get("dAmount") == null ? "" : paramsMap.get("dAmount").toString();
    	String actualDAmountStr = paramsMap.get("actualDAmount") == null ? "" : paramsMap.get("actualDAmount").toString();
    	String voucher = paramsMap.get("voucher") == null ? "" : paramsMap.get("voucher").toString();
    	Boolean isAll = paramsMap.get("isAll") == null ? false : Boolean.valueOf(paramsMap.get("isAll").toString());
    	String paymentGroup = paramsMap.get("paymentGroup") == null ? "" : paramsMap.get("paymentGroup").toString();
    	
    	/*
		 * 获取锁-声明子节点
		 * 20200806改为不可重入锁
		 */
    	InterProcessSemaphoreMutex mutex=new InterProcessSemaphoreMutex(curatorFramework,Constants.ZK_LOCK_PATHPREFIX+uuid+"-lock");
        log.info("获取zookeeper锁");
    	try {
    		List<FrontUserPaymentOrder> listOrder = new ArrayList<FrontUserPaymentOrder>();
        	List<GoodsIssueSharesnum> listNums = new ArrayList<GoodsIssueSharesnum>();
        	List<FrontUserPaidNumber> listUserNums = new ArrayList<FrontUserPaidNumber>();
        	List<LuckygameGoodsIssue> listGoodsIssue = new ArrayList<LuckygameGoodsIssue>();
        	List<LuckygameGoods> listGoods = new ArrayList<LuckygameGoods>();
        	List<FrontUserHistory> listHistory = new ArrayList<FrontUserHistory>();
        	List<FrontUserScoreHistory> listScoreHistory = new ArrayList<FrontUserScoreHistory>();
        	Map<String, Object> doMap = new HashMap<String, Object>();
    		//解析参数
    		Integer buyCount = Integer.valueOf(Base64Util.getFromBase64(buyCountStr));
    		BigDecimal dAmount = BigDecimal.valueOf(Double.valueOf(Base64Util.getFromBase64(dAmountStr)));
    		BigDecimal actualDAmount = BigDecimal.valueOf(Double.valueOf(Base64Util.getFromBase64(actualDAmountStr)));
    		
    		if(mutex.acquire(Constants.ZK_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
    			log.info("获取zookeeper锁-----------------生效");
    			FrontUser fu = this.frontUserDao.get(frontUser);
    			if(fu == null) {
    				result.setMessage("用户信息错误，请稍后重试！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    				
    			if(!FrontUserStatus.NORMAL.equals(fu.getStatus()) && !FrontUserStatus.HIGHRISK.equals(fu.getStatus())) {
    				result.setMessage("用户状态错误，请联系客服！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			FrontUserAccount account = this.frontUserAccountDao.get(frontUser);
    			if(account == null) {
    				result.setMessage("用户账户信息错误，请稍后重试！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			if(!FrontUserAccountStatus.NORMAL.equals(account.getAccountStatus())) {
    				result.setMessage("用户账户状态错误，请联系客服！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    			}
    			LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(uuid);
    	    	if(lgi == null) {
    	    		result.setMessage("商品信息错误，请稍后重试！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    	    	}
    	    	if(!LuckygameGoodsIssueStatus.BETTING.equals(lgi.getStatus())) {
    	    		result.setMessage("商品信息错误，请稍后重试！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    	    	}
    	    	
    	    	//判断玩法类型，区分金额计算算法以及所属paymentGroup
    	    	if(Constants.GAME_TYPE_TRADITION.equals(lgi.getGameType())) {//战队玩法
    	    		this.tradition2placeOrder(result, isAll, buyCount, voucher, dAmount, actualDAmount, lgi, fu, account, doMap, 
    	    				listOrder, listNums, listUserNums, listGoodsIssue, listGoods, listHistory, listScoreHistory);
    	    	} else if (Constants.GAME_TYPE_GROUP2.equals(lgi.getGameType())) {//传统玩法
    	    		if(Utlity.checkStringNull(paymentGroup)) {
    	    			result.setMessage("请选择战队！");
        	    		result.setStatus(ResultStatusType.FAILED);
        	    		return;
    	    		}
    	    		this.group2placeOrder(result, isAll, buyCount, voucher, dAmount, actualDAmount, paymentGroup, lgi, fu, account, 
    	    				doMap, listOrder, listNums, listUserNums, listGoodsIssue, listGoods, listHistory, listScoreHistory);    	    		
    	    	} else {
    	    		result.setMessage("网络异常，请稍后重试！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    	    	}
    		}
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("操作异常，请稍后重试！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
		} finally {
			if (mutex!=null){
                try {
					mutex.release();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }
	}
	
	/**
	 * 传统玩法下单投注
	 * 
	 * 20201111增加前置中奖率判断，
	 * 1、先判断这个商品设置的机器人中奖几率，如果未命中，则正常开奖。
		2、如果命中机器人中奖，如有机器人下单，则随机找出一个机器人作为准备中奖的用户，
		并挑选这个中奖机器人的幸运号作为预备中奖号。判断所有之前的所有订单的时间和这笔订单的时间的开奖结果，
		用中奖号反向计算出这个最后一笔投注的时间的最后两位是多少。录入这笔订单和订单时间。
		如没有机器人下单，则正常开奖。
		拦截，超过30%就失效
		最大999，超过1000份数的，有可能改不了
		改不了就不改了
		幸运号选择离原中奖号最近的
		如果超过900毫秒就过了，不改了
		按最后一笔的服务器时间，先弄个中奖号出来，然后再根据幸运号差值，减去相应秒数
	 * @param result
	 * @param isAll
	 * @param buyCount
	 * @param voucher
	 * @param dAmount
	 * @param actualDAmount
	 * @param lgi
	 * @param fu
	 * @param account
	 * @param doMap
	 * @param listOrder
	 * @param listNums
	 * @param listUserNums
	 * @param listGoodsIssue
	 * @param listGoods
	 * @param listHistory
	 * @param listScoreHistory
	 * @throws TransactionException 
	 */
	public void tradition2placeOrder(DataResult<Object> result, Boolean isAll, Integer buyCount, String voucher,
			BigDecimal dAmount, BigDecimal actualDAmount, LuckygameGoodsIssue lgi, FrontUser fu, FrontUserAccount account, 
			Map<String, Object> doMap, List<FrontUserPaymentOrder> listOrder, List<GoodsIssueSharesnum> listNums, List<FrontUserPaidNumber> listUserNums, 
			List<LuckygameGoodsIssue> listGoodsIssue, List<LuckygameGoods> listGoods, List<FrontUserHistory> listHistory, List<FrontUserScoreHistory> listScoreHistory) throws TransactionException {
		
		GoodsIssueSharesnum gis = this.goodsIssueSharesnumDao.get(lgi.getUuid());
		SharenumsVO svo = JSONUtils.json2obj(gis.getSharenums(), SharenumsVO.class);
		List<Integer> currentNums = svo.getCurrentNums();
		Integer maxShares = currentNums.size();
		if(isAll) {
    		//重新计算下单金额
			buyCount = maxShares;
			dAmount = lgi.getBetPerShare().multiply(BigDecimal.valueOf(Double.valueOf(buyCount)));
    	}
    	
    	if(maxShares.intValue() <= 0) {
    		result.setMessage("当前抽奖可参与次数不足，请重新下单！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	
    	//增加包尾判断
    	if(maxShares.intValue() < buyCount.intValue()) {//次数不够
    		
    		if(!isAll) {
    			result.setMessage("当前抽奖可参与次数不足，请重新下单！");
	    		result.setStatus(ResultStatusType.FAILED);
	    		return;
    		} else {//包尾
    			//重新计算下单金额
    			buyCount = maxShares;
    			dAmount = lgi.getBetPerShare().multiply(BigDecimal.valueOf(Double.valueOf(buyCount)));
    		}
    		
    	}
    	
    	BigDecimal balance = account.getBalance();
    	BigDecimal totalPayment = account.getTotalPayment();
    	Integer paymentTimes = account.getPaymentTimes();
    	BigDecimal scoreBalance = account.getScoreBalance();
    	
    	//判断金额计算
    	BigDecimal betPreAmount = lgi.getBetPerShare();
    	if(buyCount == 0) {
    		result.setMessage("参与人次数错误，下单失败！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	if(betPreAmount.multiply(BigDecimal.valueOf(Double.valueOf(buyCount))).compareTo(dAmount) != 0) {
    		result.setMessage("下单金额错误！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	FrontUserPaymentOrder fupo = new FrontUserPaymentOrder();
    	fupo.setUuid(UUID.randomUUID().toString());
    	fupo.setIsVoucherUsed(false);
    	//判断优惠券
    	if(!Utlity.checkStringNull(voucher)) {
    		FrontUserVoucher fuv = this.frontUserVoucherDao.get(voucher);
	    	if(fuv == null) {
	    		result.setMessage("优惠券信息错误，请稍后重试！");
	    		result.setStatus(ResultStatusType.FAILED);
	    		return;
	    	}
	    	if(!FrontUserVoucherStatus.NORMAL.equals(fuv.getStatus())) {
	    		result.setMessage("优惠券不能使用！");
	    		result.setStatus(ResultStatusType.FAILED);
	    		return;
	    	}
	    	if(!Utlity.checkStringNull(fuv.getGoods())) {
	    		if(fuv.getGoods().indexOf(lgi.getGoodsId()) < 0) {
	    			result.setMessage("该商品不能使用次优惠券！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
	    		}
	    	}
	    	if(!Utlity.checkStringNull(fuv.getGoodsType())) {
	    		if(fuv.getGoodsType().indexOf(lgi.getGoodsType()) < 0) {
	    			result.setMessage("该商品不能使用次优惠券！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
	    		}
	    	}
	    	if(fuv.getPayMin().compareTo(dAmount) > 0) {
	    		result.setMessage("优惠券不满足最低使用限额！");
	    		result.setStatus(ResultStatusType.FAILED);
	    		return;
	    	}
//	    	if(isAll) {
//	    		actualDAmount = dAmount.subtract(fuv.getdAmount());
//	    	}
	    	if(fuv.getdAmount().compareTo(dAmount) > 0) {//红包金额大于需支付金额 那么需支付金额为0
	    		actualDAmount = BigDecimal.ZERO;
	    	} else {
	    		actualDAmount = dAmount.subtract(fuv.getdAmount());
	    	}
	    	
	    	fuv.setOrderId(fupo.getUuid());
	    	fuv.setStatus(FrontUserVoucherStatus.USED);
	    	doMap.put("voucher", fuv);
	    	fupo.setIsVoucherUsed(true);
	    	fupo.setVoucher(fuv.getUuid());
    	} else {
    		actualDAmount = dAmount;
    	}
    	
    	
    	//判断账户信息
    	if(balance.compareTo(actualDAmount) < 0) {
    		result.setMessage("账户余额不足，请先充值！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	
		fupo.setOrderNum(Utlity.getOrderNum());//订单号
		fupo.setFrontUser(account.getFrontUser());
		fupo.setFrontUserShowId(fu.getShowId());
		fupo.setGameType(lgi.getGameType());
		fupo.setOrderType(FrontUserPaymentOrderType.USER_BET);
		
		fupo.setBuyCount(buyCount);
		fupo.setdAmount(dAmount);
		fupo.setGoodsIssue(lgi.getUuid());
		fupo.setGoodsId(lgi.getGoodsId());
		fupo.setPoundage(BigDecimal.ZERO);
		fupo.setActualDAmount(actualDAmount);
		
		fupo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		fupo.setStatus(FrontUserPaymentOrderStatus.SUCCESS);
		fupo.setRemark(lgi.getShortTitle());
		fupo.setPaymentGroup(FrontUserPaymentOrderGroup.PERSONAL);
		
		//账变信息
		FrontUserHistory fuh = new FrontUserHistory();
		fuh.setUuid(UUID.randomUUID().toString());
		fuh.setFrontUser(fupo.getFrontUser());
		fuh.setOrderNum(fupo.getOrderNum());
		fuh.setType(FrontUserHistoryType.USER_SUB);
		fuh.setOrderId(fupo.getUuid());
		fuh.setOrderType(Constants.ORDER_TYPE_USER_PAYMENT);
		fuh.setdAmount(fupo.getActualDAmount());
		fuh.setBalanceBefore(account.getBalance().add(account.getBalanceLock()));
		fuh.setBalanceAfter(account.getBalance().add(account.getBalanceLock()).subtract(fupo.getActualDAmount()));
		fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_PAYMENT));
		fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
		fuh.setRemark(fupo.getRemark());
		listHistory.add(fuh);
		
		/*
		 * 分发抽奖号码
		 */
		List<Integer> usedNums = svo.getUsedNums();
		List<Integer> userNums = new ArrayList<Integer>();
		for(int i = 0; i < buyCount; i++) {
//			userNums.add(currentNums.get(0));
//			usedNums.add(currentNums.get(0));
//			currentNums.remove(0);
			//20200512改为随机分号
			int ramNum = Utlity.getRandomNum(0, currentNums.size());
			userNums.add(currentNums.get(ramNum));
			usedNums.add(currentNums.get(ramNum));
			currentNums.remove(ramNum);
		}
		svo.setCurrentNums(currentNums);
		svo.setUsedNums(usedNums);
		gis.setSharenums(JSONUtils.obj2json(svo));
		listNums.add(gis);//保存抽奖号码
		
		FrontUserPaidNumber fupn = new FrontUserPaidNumber();
		fupn.setFrontUser(account.getFrontUser());
		fupn.setIssueGoods(lgi.getUuid());
		fupn.setOrderId(fupo.getUuid());
		SharenumsVO userNum = new SharenumsVO();
		userNum.setCurrentNums(userNums);
		fupn.setPaidSharenums(JSONUtils.obj2json(userNum));
		listUserNums.add(fupn);//保存用户抽奖号码
		
		//扣减余额
		balance = balance.subtract(actualDAmount);
		//增加总投注额
		totalPayment = totalPayment.add(actualDAmount);
		//增加总投注次数
		paymentTimes += 1;
		//20200421新增下单增金币等额积分
		scoreBalance = scoreBalance.add(actualDAmount);
		
		//20200518增加积分消费记录
		FrontUserScoreHistory fush = new FrontUserScoreHistory();
		fush.setUuid(UUID.randomUUID().toString());
		fush.setFrontUser(account.getFrontUser());
		fush.setFrontUserShowId(fupo.getFrontUserShowId());
		fush.setOrderNum(fupo.getOrderNum());
		fush.setOrderId(fupo.getUuid());
		fush.setType(FrontUserScoreHistoryType.USER_ADD);
		fush.setOrderType(Constants.ORDER_TYPE_USER_PAYMENT);
		fush.setsAmount(actualDAmount);
		fush.setScoreBalanceBefore(account.getScoreBalance());
		fush.setScoreBalanceAfter(account.getScoreBalance().add(actualDAmount));
		fush.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_PAYMENT));
		fush.setCreatetime(fupo.getCreatetime());
		fush.setRemark("用户下单赠送消费金币等额积分！");
		
		//扣减投注份额
		lgi.setBetShares(lgi.getBetShares().intValue() + buyCount);
		lgi.setRemainShares(lgi.getRemainShares().intValue() - buyCount);
		boolean isPush = false;//是否推送数据到前端
		boolean isLottery = false;
    	String goodsIssue = "";
    	boolean isRobotWinning = false;
		//如果可投注份额清零，则进入待开奖状态
		if(currentNums.size() <= 0) {
			lgi.setBetShares(lgi.getShares());
			lgi.setRemainShares(0);
			
			isLottery = true;
			goodsIssue = lgi.getUuid();
			isPush = true;
			lgi.setStatus(LuckygameGoodsIssueStatus.LOTTERY);
			lgi.setLotterytime(new Timestamp(System.currentTimeMillis()));
			
			//投注清零，开展下一期
			//查询奖品状态-看是否要开展下一期
			LuckygameGoods lg = this.luckygameGoodsDao.get(lgi.getLuckygameGoods());
			boolean flag = true;
			if(lg != null && LuckygameGoodsStatus.NORMAL.equals(lg.getStatus())) {//正常状态，开展下一期
				//查看剩余期数是否充足,如果充足则开下一期 否则忽略
				if((lg.getTotalIssueNum() != -1) && (lg.getTotalIssueNum() <= lgi.getIssueNum())) {
					flag = false;
				}
			} else {
				flag = false;
			}
			if(!flag) {//不符合开展下一期的条件，自动下架
				lg.setStatus(LuckygameGoodsStatus.DISABLE);
				listGoods.add(lg);
			} else {//异步开启下一期
				this.rabbitSenderService.goodsIssueStartMessageSend(lg.getUuid());
			}
			
			//增加前置中奖判断
			isRobotWinning = isRobotWinning(lgi);
			log.info("前置中奖判断:--------------------------------------------"+isRobotWinning+"-----------------------------");
		}
		
		if(isRobotWinning) {//机器人中奖
			//先确定幸运号，
			//第一次计算中奖号
			Map<String, Integer> realBuyCountMap = new HashMap<String, Integer>();
			Map<Integer, FrontUserPaymentOrder> realOrderMap = new HashMap<Integer, FrontUserPaymentOrder>();
			List<FrontUserPaymentOrder> listrealOrder = new ArrayList<FrontUserPaymentOrder>();
			int luckyNum = this.luckygameGoodsLotteryUtil.getLuckyNum(lgi, realOrderMap, realBuyCountMap, listrealOrder);
			//补充本单的抽奖号信息到realOrderMap中
			for(Integer num : userNums) {
				realOrderMap.put(num, fupo);
			}
			//判断是否机器人订单，若是则继续
			FrontUserPaymentOrder realFupo = realOrderMap.get(luckyNum);
			if(realFupo == null) {
				throw new TransactionException("订单异常，请稍候再试！");
			}
			FrontUser orderFu = this.frontUserDao.get(realFupo.getFrontUser());
			
			boolean isRobot = false;
			if(orderFu != null) {
				isRobot = orderFu.getType() == FrontUserType.ROBOT;
			}
			log.info("是否机器人订单1:--------------------------------------------"+luckyNum+"-----------------------------");
			log.info("是否机器人订单2:--------------------------------------------"+isRobot+"-----------------------------");
			if(isRobot) {//是机器人订单
				lgi.setLuckyNumber(luckyNum);
			} else {
				//否则根据幸运号和中奖号的差值，逆推出订单时间差值，然后更新订单时间
				//从机器人订单中，获取幸运号
				//先分离机器人订单
				
				Map<Integer, FrontUserPaymentOrder> robotMap = new HashMap<Integer, FrontUserPaymentOrder>();
				List<Integer> keyList = new ArrayList<Integer>();
				for(Map.Entry<Integer, FrontUserPaymentOrder> entry : realOrderMap.entrySet()) {
					FrontUserPaymentOrder order = entry.getValue();
					FrontUser user = this.frontUserDao.get(order.getFrontUser());
					if(user != null && FrontUserType.ROBOT.equals(user.getType())) {
						//再从机器人订单中，获取与中奖号相近的幸运号，原则上是中奖号钱后999差值中间的号 取值范围要判断是否超出界限
						int minNum = (luckyNum - 999) < Utlity.LUCKY_NUM_START ? Utlity.LUCKY_NUM_START : (luckyNum - 999);
						int maxNum = (luckyNum + 999) > (Utlity.LUCKY_NUM_START + lgi.getShares().intValue() - 1) 
								? (Utlity.LUCKY_NUM_START + lgi.getShares().intValue() - 1) : (luckyNum + 999);
						if(entry.getKey() >= minNum && entry.getKey() <= maxNum) {
							keyList.add(entry.getKey());
							robotMap.put(entry.getKey(), order);
						}
					}
				}
				
				if(!robotMap.isEmpty()) {
					//随机抽取一个即可
					Integer key = keyList.get(Utlity.getRandomNum(0, keyList.size()));

					log.info("是否机器人订单3:--------------------------------------------"+key+"-----------------------------");
					//计算出差值
//					int less = Math.abs(luckyNum - key);
					//计算时间差值：按理应该是等于幸运号的差值。
					//得出最终时间
					long timeEnd = fupo.getCreatetime().getTime() - (luckyNum - key);
					fupo.setCreatetime(new Timestamp(timeEnd));
					fush.setCreatetime(fupo.getCreatetime());
					lgi.setLuckyNumber(key);
				}
			}
		}
		listOrder.add(fupo);//保存订单
		listScoreHistory.add(fush);
		listGoodsIssue.add(lgi);
		
		account.setBalance(balance);//更新余额
		account.setTotalPayment(totalPayment);
		account.setPaymentTimes(paymentTimes);
		account.setScoreBalance(scoreBalance);
		
		doMap.put("frontUserAccount", account);
		doMap.put("luckygameGoodsIssue", listGoodsIssue);
		doMap.put("issueNums", listNums);
		doMap.put("paymentOrder", listOrder);
		doMap.put("userHistory", listHistory);
		doMap.put("frontUserNums", listUserNums);
		doMap.put("luckygameGoods", listGoods);
		doMap.put("scoreHistory", listScoreHistory);
		
		this.frontUserPaymentOrderDao.userBet(doMap);
		
		//20210304-去掉下单成功的消息通知
//		//下单通知消息
//		FrontUserMessage fum = new FrontUserMessage();
//		fum.setUuid(UUID.randomUUID().toString());
//		fum.setFrontUser(fu.getUuid());
//		fum.setFrontUserShowId(fu.getShowId());
//		fum.setTitle("提示，下单成功");
//		fum.setContent("您在"+Utlity.timeSpanToChinaDateString(fupo.getCreatetime())+"参与的"+lgi.getShortTitle()+"商品抽奖下单成功！");
//		fum.setSourceId(fupo.getUuid());
//		fum.setStatus(FrontUserMessageStatus.NORMAL);
//		fum.setType(FrontUserMessageType.USER_ORDER);
//		fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_PAYMENT);
//		fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
//		this.frontUserMessageDao.sendMessage(fum);
		
		if(isPush) {
			this.rabbitSenderService.pushInfoStartMessageSend();
		}
		if(isLottery && !Utlity.checkStringNull(goodsIssue)) {
    		//20200417增加异步开奖
			this.rabbitSenderService.lotterySend(goodsIssue);
    	}
		result.setMessage("下单成功！");
		result.setStatus(ResultStatusType.SUCCESS);
		return;
	}
	
	/**
	 * 战队玩法投注
	 * @param result
	 * @param isAll
	 * @param buyCount
	 * @param voucher
	 * @param dAmount
	 * @param actualDAmount
	 * @param paymentGroup
	 * @param lgi
	 * @param fu
	 * @param account
	 * @param doMap
	 * @param listOrder
	 * @param listNums
	 * @param listUserNums
	 * @param listGoodsIssue
	 * @param listGoods
	 * @param listHistory
	 * @param listScoreHistory
	 */
	public void group2placeOrder(DataResult<Object> result, Boolean isAll, Integer buyCount, String voucher,
			BigDecimal dAmount, BigDecimal actualDAmount, String paymentGroup, LuckygameGoodsIssue lgi, FrontUser fu, FrontUserAccount account, 
			Map<String, Object> doMap, List<FrontUserPaymentOrder> listOrder, List<GoodsIssueSharesnum> listNums, List<FrontUserPaidNumber> listUserNums, 
			List<LuckygameGoodsIssue> listGoodsIssue, List<LuckygameGoods> listGoods, List<FrontUserHistory> listHistory, List<FrontUserScoreHistory> listScoreHistory) {
		//获取参与战队的抽奖号信息
		GoodsIssueSharesnum gis = this.goodsIssueSharesnumDao.get(lgi.getUuid());
		SharenumsPKVO spkvo = JSONUtils.json2obj(gis.getSharenums(), SharenumsPKVO.class);
		SharenumsVO paymentShare = null;
		if(FrontUserPaymentOrderGroup.LUCKY.equals(paymentGroup)) {//幸运对
			paymentShare = spkvo.getLucky();
		} else if (FrontUserPaymentOrderGroup.RAIDER.equals(paymentGroup)) {//夺宝队
			paymentShare = spkvo.getRaider();
		}
		if(paymentShare == null) {
			result.setMessage("当前战队可参与次数不足，请重新选择战队下单！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
		}
		
		List<Integer> currentNums = paymentShare.getCurrentNums();
		List<Integer> usedNums = paymentShare.getUsedNums();
		Integer maxShares = currentNums.size();
		
		if(isAll) {
    		//重新计算下单金额
			buyCount = maxShares;
			dAmount = lgi.getBetPerShare().multiply(BigDecimal.valueOf(Double.valueOf(buyCount)));
    	}
    	
    	if(maxShares.intValue() <= 0) {
    		result.setMessage("当前战队无可参与次数，请重新选择战队下单！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	
    	//增加包尾判断
    	if(maxShares.intValue() < buyCount.intValue()) {//次数不够
    		
    		if(!isAll) {
    			result.setMessage("当前抽奖可参与次数不足，请重新下单！");
	    		result.setStatus(ResultStatusType.FAILED);
	    		return;
    		} else {//包尾
    			//重新计算下单金额
    			buyCount = maxShares;
    			dAmount = lgi.getBetPerShare().multiply(BigDecimal.valueOf(Double.valueOf(buyCount)));
    		}
    		
    	}
    	
    	BigDecimal balance = account.getBalance();
    	BigDecimal totalPayment = account.getTotalPayment();
    	Integer paymentTimes = account.getPaymentTimes();
    	BigDecimal scoreBalance = account.getScoreBalance();
    	
    	//判断金额计算
    	BigDecimal betPreAmount = lgi.getBetPerShare();
    	if(buyCount == 0) {
    		result.setMessage("参与人次数错误，下单失败！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	if(betPreAmount.multiply(BigDecimal.valueOf(Double.valueOf(buyCount))).compareTo(dAmount) != 0) {
    		result.setMessage("下单金额错误！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	FrontUserPaymentOrder fupo = new FrontUserPaymentOrder();
    	fupo.setUuid(UUID.randomUUID().toString());
    	fupo.setIsVoucherUsed(false);
    	//判断优惠券
    	if(!Utlity.checkStringNull(voucher)) {
    		FrontUserVoucher fuv = this.frontUserVoucherDao.get(voucher);
	    	if(fuv == null) {
	    		result.setMessage("优惠券信息错误，请稍后重试！");
	    		result.setStatus(ResultStatusType.FAILED);
	    		return;
	    	}
	    	if(!FrontUserVoucherStatus.NORMAL.equals(fuv.getStatus())) {
	    		result.setMessage("优惠券不能使用！");
	    		result.setStatus(ResultStatusType.FAILED);
	    		return;
	    	}
	    	if(!Utlity.checkStringNull(fuv.getGoods())) {
	    		if(fuv.getGoods().indexOf(lgi.getGoodsId()) < 0) {
	    			result.setMessage("该商品不能使用次优惠券！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
	    		}
	    	}
	    	if(!Utlity.checkStringNull(fuv.getGoodsType())) {
	    		if(fuv.getGoodsType().indexOf(lgi.getGoodsType()) < 0) {
	    			result.setMessage("该商品不能使用次优惠券！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
	    		}
	    	}
	    	if(fuv.getPayMin().compareTo(dAmount) > 0) {
	    		result.setMessage("优惠券不满足最低使用限额！");
	    		result.setStatus(ResultStatusType.FAILED);
	    		return;
	    	}
//	    	if(isAll) {
//	    		actualDAmount = dAmount.subtract(fuv.getdAmount());
//	    	}
	    	if(fuv.getdAmount().compareTo(dAmount) > 0) {//红包金额大于需支付金额 那么需支付金额为0
	    		actualDAmount = BigDecimal.ZERO;
	    	} else {
	    		actualDAmount = dAmount.subtract(fuv.getdAmount());
	    	}
	    	
	    	fuv.setOrderId(fupo.getUuid());
	    	fuv.setStatus(FrontUserVoucherStatus.USED);
	    	doMap.put("voucher", fuv);
	    	fupo.setIsVoucherUsed(true);
	    	fupo.setVoucher(fuv.getUuid());
    	} else {
    		actualDAmount = dAmount;
    	}
    	
    	
    	//判断账户信息
    	if(balance.compareTo(actualDAmount) < 0) {
    		result.setMessage("账户余额不足，请先充值！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	
		fupo.setOrderNum(Utlity.getOrderNum());//订单号
		fupo.setFrontUser(account.getFrontUser());
		fupo.setFrontUserShowId(fu.getShowId());
		fupo.setGameType(lgi.getGameType());
		fupo.setOrderType(FrontUserPaymentOrderType.USER_BET);
		
		fupo.setBuyCount(buyCount);
		fupo.setdAmount(dAmount);
		fupo.setGoodsIssue(lgi.getUuid());
		fupo.setGoodsId(lgi.getGoodsId());
		fupo.setPoundage(BigDecimal.ZERO);
		fupo.setActualDAmount(actualDAmount);
		
		fupo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		fupo.setStatus(FrontUserPaymentOrderStatus.SUCCESS);
		fupo.setRemark(lgi.getShortTitle());
		
		fupo.setPaymentGroup(paymentGroup);//所选战队
		listOrder.add(fupo);//保存订单
		
		//账变信息
		FrontUserHistory fuh = new FrontUserHistory();
		fuh.setUuid(UUID.randomUUID().toString());
		fuh.setFrontUser(fupo.getFrontUser());
		fuh.setOrderNum(fupo.getOrderNum());
		fuh.setType(FrontUserHistoryType.USER_SUB);
		fuh.setOrderId(fupo.getUuid());
		fuh.setOrderType(Constants.ORDER_TYPE_USER_PAYMENT);
		fuh.setdAmount(fupo.getActualDAmount());
		fuh.setBalanceBefore(account.getBalance().add(account.getBalanceLock()));
		fuh.setBalanceAfter(account.getBalance().add(account.getBalanceLock()).subtract(fupo.getActualDAmount()));
		fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_PAYMENT));
		fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
		fuh.setRemark(fupo.getRemark());
		listHistory.add(fuh);
		
		/*
		 * 分发抽奖号码
		 */
		List<Integer> userNums = new ArrayList<Integer>();
		for(int i = 0; i < buyCount; i++) {
			int ramNum = Utlity.getRandomNum(0, currentNums.size());
			userNums.add(currentNums.get(ramNum));
			usedNums.add(currentNums.get(ramNum));
			currentNums.remove(ramNum);
		}
		paymentShare.setCurrentNums(currentNums);
		paymentShare.setUsedNums(usedNums);
		
		//更新group2战队玩法抽奖号
		if(FrontUserPaymentOrderGroup.LUCKY.equals(paymentGroup)) {
			spkvo.setLucky(paymentShare);
		} else if(FrontUserPaymentOrderGroup.RAIDER.equals(paymentGroup)) {
			spkvo.setRaider(paymentShare);
		}
		gis.setSharenums(JSONUtils.obj2json(spkvo));
		listNums.add(gis);//保存抽奖号码
		
		FrontUserPaidNumber fupn = new FrontUserPaidNumber();
		fupn.setFrontUser(account.getFrontUser());
		fupn.setIssueGoods(lgi.getUuid());
		fupn.setOrderId(fupo.getUuid());
		SharenumsVO userNum = new SharenumsVO();
		userNum.setCurrentNums(userNums);
		fupn.setPaidSharenums(JSONUtils.obj2json(userNum));
		listUserNums.add(fupn);//保存用户抽奖号码
		
		//扣减余额
		balance = balance.subtract(actualDAmount);
		//增加总投注额
		totalPayment = totalPayment.add(actualDAmount);
		//增加总投注次数
		paymentTimes += 1;
		//20200421新增下单增金币等额积分
		scoreBalance = scoreBalance.add(actualDAmount);
		
		//20200518增加积分消费记录
		FrontUserScoreHistory fush = new FrontUserScoreHistory();
		fush.setUuid(UUID.randomUUID().toString());
		fush.setFrontUser(account.getFrontUser());
		fush.setFrontUserShowId(fupo.getFrontUserShowId());
		fush.setOrderNum(fupo.getOrderNum());
		fush.setOrderId(fupo.getUuid());
		fush.setType(FrontUserScoreHistoryType.USER_ADD);
		fush.setOrderType(Constants.ORDER_TYPE_USER_PAYMENT);
		fush.setsAmount(actualDAmount);
		fush.setScoreBalanceBefore(account.getScoreBalance());
		fush.setScoreBalanceAfter(account.getScoreBalance().add(actualDAmount));
		fush.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_PAYMENT));
		fush.setCreatetime(fupo.getCreatetime());
		fush.setRemark("用户下单赠送消费金币等额积分！");
		listScoreHistory.add(fush);
		
		//扣减投注份额
		lgi.setBetShares(lgi.getBetShares().intValue() + buyCount);
		lgi.setRemainShares(lgi.getRemainShares().intValue() - buyCount);
//		boolean isPush = false;//是否推送数据到前端
		boolean isLottery = false;
    	String goodsIssue = "";
		//如果可投注份额清零，则进入待开奖状态
		if(lgi.getRemainShares() <= 0) {
			//扣减投注份额
			lgi.setBetShares(lgi.getShares());
			lgi.setRemainShares(0);
			
			isLottery = true;
			goodsIssue = lgi.getUuid();
//			isPush = true;
			lgi.setStatus(LuckygameGoodsIssueStatus.LOTTERY);
			lgi.setLotterytime(new Timestamp(System.currentTimeMillis()));
			
			//投注清零，开展下一期
			//查询奖品状态-看是否要开展下一期
			LuckygameGoods lg = this.luckygameGoodsDao.get(lgi.getLuckygameGoods());
			boolean flag = true;
			if(lg != null && LuckygameGoodsStatus.NORMAL.equals(lg.getStatus())) {//正常状态，开展下一期
				//查看剩余期数是否充足,如果充足则开下一期 否则忽略
				if((lg.getTotalIssueNum() != -1) && (lg.getTotalIssueNum() <= lgi.getIssueNum())) {
					flag = false;
				}
			} else {
				flag = false;
			}
			if(!flag) {//不符合开展下一期的条件，自动下架
				lg.setStatus(LuckygameGoodsStatus.DISABLE);
				listGoods.add(lg);
			} else {//异步开启下一期
				this.rabbitSenderService.goodsIssueStartMessageSend(lg.getUuid());
			}
		}
		listGoodsIssue.add(lgi);
		
		account.setBalance(balance);//更新余额
		account.setTotalPayment(totalPayment);
		account.setPaymentTimes(paymentTimes);
		account.setScoreBalance(scoreBalance);
		
		doMap.put("frontUserAccount", account);
		doMap.put("luckygameGoodsIssue", listGoodsIssue);
		doMap.put("issueNums", listNums);
		doMap.put("paymentOrder", listOrder);
		doMap.put("userHistory", listHistory);
		doMap.put("frontUserNums", listUserNums);
		doMap.put("luckygameGoods", listGoods);
		doMap.put("scoreHistory", listScoreHistory);
		
		this.frontUserPaymentOrderDao.userBet(doMap);
		
//		String groupStr = "";
//		if(FrontUserPaymentOrderGroup.LUCKY.equals(paymentGroup)) {//幸运对
//			groupStr = "幸运队";
//		} else if (FrontUserPaymentOrderGroup.RAIDER.equals(paymentGroup)) {//夺宝队
//			groupStr = "夺宝队";
//		}
		
		//20210304-去掉下单成功的消息通知
//		//下单通知消息
//		FrontUserMessage fum = new FrontUserMessage();
//		fum.setUuid(UUID.randomUUID().toString());
//		fum.setFrontUser(fu.getUuid());
//		fum.setFrontUserShowId(fu.getShowId());
//		fum.setTitle("提示，下单成功");
//		fum.setContent("您在"+Utlity.timeSpanToChinaDateString(fupo.getCreatetime())+"参与的战队PK"+lgi.getShortTitle()+"商品抽奖加入" + groupStr + "成功！");
//		fum.setSourceId(fupo.getUuid());
//		fum.setStatus(FrontUserMessageStatus.NORMAL);
//		fum.setType(FrontUserMessageType.USER_ORDER);
//		fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_PAYMENT);
//		fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
//		this.frontUserMessageDao.sendMessage(fum);
		
//		if(isPush) {
//			this.rabbitSenderService.pushInfoStartMessageSend();
//		}
		if(isLottery && !Utlity.checkStringNull(goodsIssue)) {
    		//20200417增加异步开奖
			this.rabbitSenderService.lotterySend(goodsIssue);
    	}
		result.setMessage("下单成功！");
		result.setStatus(ResultStatusType.SUCCESS);
		return;
	}
	
	/**
	 * 随机数抽奖算法，预判是否机器人中奖
	 * 若为true，则需要先开奖，后更新入库
	 * 否则按照正常流程进行
	 * @param lgi
	 * @return
	 */
	public boolean isRobotWinning(LuckygameGoodsIssue lgi) {
		Goods goods = this.goodsDao.get(lgi.getGoodsId());
		if(goods != null) {
			RobotWinningRate rwr = this.robotWinningRateDao.getByPirice(goods.getPrice());
			//非空判断
			if(rwr != null) {
				BigDecimal winningRate = rwr.getWinningRate();
				int lotteryNum = Utlity.getRandomNum(1, 1000001);
	    		int totalNum = 0;
				int interval = winningRate.multiply(BigDecimal.valueOf(10000)).intValue();
				for(int i = 1; i <= interval; i++) {
					totalNum++;
					if(lotteryNum == totalNum) {//中奖
						return true;
					}
				}
			}
		}
		
		return false;
	}
}
