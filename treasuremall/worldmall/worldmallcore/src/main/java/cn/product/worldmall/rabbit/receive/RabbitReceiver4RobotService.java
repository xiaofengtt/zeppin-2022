package cn.product.worldmall.rabbit.receive;/**
 * Created by Administrator on 2019/6/21.
 */

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
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import cn.product.worldmall.controller.base.TransactionException;
import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserPaymentOrderDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.dao.GoodsIssueSharesnumDao;
import cn.product.worldmall.dao.LuckygameGoodsDao;
import cn.product.worldmall.dao.LuckygameGoodsIssueDao;
import cn.product.worldmall.dao.RobotWinningRateDao;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUser.FrontUserType;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserAccount.FrontUserAccountStatus;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.worldmall.entity.FrontUserPaidNumber;
import cn.product.worldmall.entity.FrontUserPaymentOrder;
import cn.product.worldmall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderGroup;
import cn.product.worldmall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderStatus;
import cn.product.worldmall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderType;
import cn.product.worldmall.entity.Goods;
import cn.product.worldmall.entity.GoodsIssueSharesnum;
import cn.product.worldmall.entity.LuckygameGoods;
import cn.product.worldmall.entity.LuckygameGoods.LuckygameGoodsStatus;
import cn.product.worldmall.entity.LuckygameGoodsIssue;
import cn.product.worldmall.entity.LuckygameGoodsIssue.LuckygameGoodsIssueStatus;
import cn.product.worldmall.entity.RobotSetting;
import cn.product.worldmall.entity.RobotWinningRate;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.rabbit.send.RabbitSenderService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.util.lottery.LuckygameGoodsLotteryUtil;
import cn.product.worldmall.vo.back.SharenumsPKVO;
import cn.product.worldmall.vo.back.SharenumsVO;

/**
 * RabbitMQ接收消息服务
 **/
@Service
public class RabbitReceiver4RobotService {

    public static final Logger log= LoggerFactory.getLogger(RabbitReceiver4RobotService.class);


    @Autowired
    private Environment environment;
    
    @Autowired
    private RabbitSenderService rabbitSenderService;
    
    @Autowired
    private LuckygameGoodsIssueDao luckygameGoodsIssueDao;
    
    @Autowired
    private FrontUserAccountDao frontUserAccountDao;
    
    @Autowired
    private GoodsIssueSharesnumDao goodsIssueSharesnumDao;
    
    @Autowired
    private FrontUserPaymentOrderDao frontUserPaymentOrderDao;
    
    @Autowired
    private FrontUserDao frontUserDao;
    
    @Autowired
    private LuckygameGoodsDao luckygameGoodsDao;
    
    //使用基于zookeeper 有序集群同步锁
    /*
     * 注册curator
     */
    @Autowired
    private CuratorFramework curatorFramework;
    
    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RobotWinningRateDao robotWinningRateDao;

    @Autowired
    private LuckygameGoodsLotteryUtil luckygameGoodsLotteryUtil;
	
    @Autowired
    private GoodsDao goodsDao;

    /**
     * 机器人工作开始内容-监听者
     * @param info
     */
    @RabbitListener(queues = {"${mq.robot.start.queue}"},containerFactory = "singleListenerContainer")
    public void robotWorkStart(RobotSetting rs){
    	List<InterProcessSemaphoreMutex> listMutex = new ArrayList<InterProcessSemaphoreMutex>();//记录每个抽奖拿到的锁
        try {
            log.info("机器人工作开始-监听者-接收消息:{}",rs.getRobotId());
            if (rs!=null){
            	if(Constants.GAME_TYPE_TRADITION.equals(rs.getGameType())) {
            		tradition2WorkStart(rs, listMutex);
            	} else if (Constants.GAME_TYPE_GROUP2.equals(rs.getGameType())) {
            		group2WorkStart(rs, listMutex);
            	}
            }
        }catch (Exception e){
        	e.printStackTrace();
            log.error("机器人工作开始-监听者-发生异常：",e.fillInStackTrace());
            //有异常直接进下次
            this.rabbitSenderService.robotWorkMessageSend(rs.getRobotId());
        } finally {
        	log.info("机器人工作开始-监听者-本次工作结束！");
        	if(listMutex.size() > 0) {//依次解锁
        		for(InterProcessSemaphoreMutex mutex : listMutex) {
        			if (mutex!=null){
                        try {
							mutex.release();
						} catch (Exception e) {
							e.printStackTrace();
						}
                    }
        		}
        	}
        }
    }
    
    public void tradition2WorkStart(RobotSetting rs, List<InterProcessSemaphoreMutex> listMutex) throws NumberFormatException, Exception {
    	log.info("【成功处理】机器人工作开始内容-监听者-接收消息:传统玩法{}",rs.getRobotId());
    	boolean isWork = false;
    	boolean isPush = false;//是否推送数据到前端
    	boolean isLottery = false;
    	String goodsIssue = "";
    	
    	//工作时间计算
    	long currentTime = System.currentTimeMillis() - Utlity.getCurrentTime("yyyy-MM-dd 08:00:00").getTime();
    	long startTime = rs.getWorktimeBegin().getTime();
		long endTime = rs.getWorktimeEnd().getTime();
		if(startTime >= endTime) {
			long oneDay = 1000L*60L*60L*24L;
			endTime += oneDay;
			currentTime += oneDay;
		}
		if((startTime <= currentTime) && (currentTime <= endTime)) {//工作时间内
        	List<FrontUserPaymentOrder> listOrder = new ArrayList<FrontUserPaymentOrder>();
        	List<GoodsIssueSharesnum> listNums = new ArrayList<GoodsIssueSharesnum>();
        	List<FrontUserPaidNumber> listUserNums = new ArrayList<FrontUserPaidNumber>();
        	List<LuckygameGoodsIssue> listGoodsIssue = new ArrayList<LuckygameGoodsIssue>();
        	List<LuckygameGoods> listGoods = new ArrayList<LuckygameGoods>();
        	List<FrontUserHistory> listHistory = new ArrayList<FrontUserHistory>();
        	//业务逻辑
        	//判断用户余额是否充足
        	FrontUserAccount fua = this.frontUserAccountDao.get(rs.getRobotId());
        	FrontUser fu = this.frontUserDao.get(rs.getRobotId());
        	if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {//判断账户状态-账户停用 停止工作 清空队列
        		rabbitAdmin.purgeQueue("worldmall.robot.ready.queue."+rs.getRobotId());//清空队列
        		return;
			}
        	BigDecimal balance = fua.getBalance();
        	BigDecimal totalPayment = fua.getTotalPayment();
        	Integer paymentTimes = fua.getPaymentTimes();
        	//按照规则获取抽奖信息列表
        	String[] goodsArr = rs.getGoods().split(",");//商品ID
        	Map<String, Object> searchMap = new HashMap<String, Object>();
        	searchMap.put("uuid", goodsArr);
        	searchMap.put("goodsPriceMax", rs.getGoodsPriceMax());//商品价格上限
        	searchMap.put("goodsPriceMin", rs.getGoodsPriceMin());//商品价格下限
//        	searchMap.put("maxPay", rs.getMaxPay());//投注上限
//        	searchMap.put("minPay", rs.getMinPay());//投注下限
        	searchMap.put("status", LuckygameGoodsIssueStatus.BETTING);
        	searchMap.put("gameType", rs.getGameType());
        	List<LuckygameGoodsIssue> list = this.luckygameGoodsIssueDao.getListByParams(searchMap);
        	if(list != null && list.size() > 0) {//20200416修改为随机一个商品下单
        		int randomNum = Utlity.getRandomNum(0, list.size());
        		LuckygameGoodsIssue lgi = list.get(randomNum);
        		if(lgi != null) {
        			/*
        			 * 获取锁-声明子节点
        			 */
        			InterProcessSemaphoreMutex mutex=new InterProcessSemaphoreMutex(curatorFramework,Constants.ZK_LOCK_PATHPREFIX+lgi.getUuid()+"-lock");
        			listMutex.add(mutex);
                    log.info("获取zookeeper锁"+lgi.getUuid());
                    
        			if(mutex.acquire(Constants.ZK_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
        				lgi = this.luckygameGoodsIssueDao.get(lgi.getUuid());
        				/*
        				 * 分发抽奖号码
        				 */
        				GoodsIssueSharesnum gis = this.goodsIssueSharesnumDao.get(lgi.getUuid());
        				SharenumsVO svo = JSONUtils.json2obj(gis.getSharenums(), SharenumsVO.class);
        				List<Integer> currentNums = svo.getCurrentNums();
        				Integer maxShares = currentNums.size();
        				if(maxShares > 0) {
            				/*
            				 * 封装投注信息（--注意是否包尾参数）：订单order+抽奖号码分配
            				 * 这里需要集群锁机制进行抽奖号码增减控制 避免资源争抢过度
            				 */
            				//计算本次投注额 = 商品单注额*随机份数
            				int shares = 0;
            				BigDecimal price = BigDecimal.ZERO;
            				
            				//增加机器人投注份额上限判断20200421 总投注份额的40% 向下取整
            				Integer totalShares = BigDecimal.valueOf(0.4).multiply(BigDecimal.valueOf(Double.valueOf(lgi.getShares()))).setScale(0,BigDecimal.ROUND_DOWN).intValue();
//            				if(totalShares > lgi.getRemainShares()) {
//            					totalShares = lgi.getRemainShares();
//            				}
            				Map<String, Object> priceMap = this.getPrice(totalShares, lgi.getBetPerShare(), rs.getMinPay(), rs.getMaxPay());
            				if(priceMap != null) {
            					shares = getMin(Integer.valueOf(priceMap.get("shares").toString()), maxShares);//容易出现包尾的情况
            					price = BigDecimal.valueOf(shares).multiply(lgi.getBetPerShare());
            				} else {//不足一次，按一次算起
            					shares = 1;
            					price = lgi.getBetPerShare();
            				}
            				//判断余额是否充足
            				if(balance.compareTo(price) < 0) {
            					return;//余额不足 结束任务
            				}
            				//余额充足-封装订单
            				isWork = true;
            				FrontUserPaymentOrder fupo = new FrontUserPaymentOrder();
            				fupo.setUuid(UUID.randomUUID().toString());
            				fupo.setOrderNum(Utlity.getOrderNum());//订单号
            				fupo.setFrontUser(fua.getFrontUser());
            				fupo.setFrontUserShowId(fu.getShowId());
            				fupo.setGameType(lgi.getGameType());
            				fupo.setOrderType(FrontUserPaymentOrderType.USER_BET);
            				
            				fupo.setBuyCount(shares);
            				fupo.setdAmount(price);
            				fupo.setIsVoucherUsed(false);
            				fupo.setGoodsIssue(lgi.getUuid());
            				fupo.setGoodsId(lgi.getGoodsId());
            				fupo.setPoundage(BigDecimal.ZERO);
            				fupo.setActualDAmount(price);
            				
            				fupo.setCreatetime(new Timestamp(System.currentTimeMillis()));
            				fupo.setStatus(FrontUserPaymentOrderStatus.SUCCESS);
            				fupo.setRemark(lgi.getShortTitle());
            				fupo.setPaymentGroup(FrontUserPaymentOrderGroup.PERSONAL);

            				fupo.setDemoFlag(lgi.getDemoFlag());
            				fupo.setInternationalInfo(lgi.getInternationalInfo());
            				
            				//账变信息
            				FrontUserHistory fuh = new FrontUserHistory();
            				fuh.setUuid(UUID.randomUUID().toString());
            				fuh.setFrontUser(fupo.getFrontUser());
            				fuh.setOrderNum(fupo.getOrderNum());
            				fuh.setType(FrontUserHistoryType.USER_ADD);
            				fuh.setOrderId(fupo.getUuid());
            				fuh.setOrderType(Constants.ORDER_TYPE_USER_PAYMENT);
            				fuh.setdAmount(fupo.getActualDAmount());
            				fuh.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
            				fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()).subtract(fupo.getActualDAmount()));
            				fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_PAYMENT));
            				fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
            				fuh.setRemark(fupo.getRemark());
            				listHistory.add(fuh);
            				
            				List<Integer> usedNums = svo.getUsedNums();
            				List<Integer> userNums = new ArrayList<Integer>();
            				for(int i = 0; i < shares; i++) {
//            					userNums.add(currentNums.get(0));
//            					usedNums.add(currentNums.get(0));
//            					currentNums.remove(0);
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
            				fupn.setFrontUser(fua.getFrontUser());
            				fupn.setIssueGoods(lgi.getUuid());
            				fupn.setOrderId(fupo.getUuid());
            				SharenumsVO userNum = new SharenumsVO();
            				userNum.setCurrentNums(userNums);
            				fupn.setPaidSharenums(JSONUtils.obj2json(userNum));
            				listUserNums.add(fupn);//保存用户抽奖号码
            			
            				//扣减余额
            				balance = balance.subtract(price);
            				//增加总投注额
            				totalPayment = totalPayment.add(price);
            				//增加总投注次数
            				paymentTimes += 1;
            				
            				//扣减投注份额
            				lgi.setBetShares(lgi.getBetShares().intValue() + shares);
            				lgi.setRemainShares(lgi.getRemainShares().intValue() - shares);

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
//            							int less = Math.abs(luckyNum - key);
            							//计算时间差值：按理应该是等于幸运号的差值。
            							//得出最终时间
            							long timeEnd = fupo.getCreatetime().getTime() - (luckyNum - key);
            							fupo.setCreatetime(new Timestamp(timeEnd));
            							lgi.setLuckyNumber(key);
            						}
            					}
            				}
            				listOrder.add(fupo);//保存订单
            				listGoodsIssue.add(lgi);
            			}
        			}
        		}
        	}
        	fua.setBalance(balance);//更新余额
        	fua.setTotalPayment(totalPayment);
        	fua.setPaymentTimes(paymentTimes);
        	
        	if(isWork) {//入库操作
        		Map<String, Object> paramsMap = new HashMap<String, Object>();
        		paramsMap.put("frontUserAccount", fua);
        		paramsMap.put("luckygameGoodsIssue", listGoodsIssue);
        		paramsMap.put("issueNums", listNums);
        		paramsMap.put("paymentOrder", listOrder);
        		paramsMap.put("userHistory", listHistory);
        		paramsMap.put("frontUserNums", listUserNums);
        		paramsMap.put("luckygameGoods", listGoods);
        		
        		this.frontUserPaymentOrderDao.userBet(paramsMap);
        	}
        	
//        	//此次任务完成后，发送下次工作执行消息到ready队列，等待下次任务执行
//        	this.rabbitSenderService.robotWorkMessageSend(rs.getRobotId());
        	
        	
        	if(isPush) {
				this.rabbitSenderService.pushInfoStartMessageSend();
			}
        	if(isLottery && !Utlity.checkStringNull(goodsIssue)) {
        		//20200417增加异步开奖
				this.rabbitSenderService.lotterySend(goodsIssue);
        	}
		}
    	
    	
    	//此次任务完成后，发送下次工作执行消息到ready队列，等待下次任务执行
    	this.rabbitSenderService.robotWorkMessageSend(rs.getRobotId());
    }
    
    /**
     * 战队玩法机器人投注算法
     * 一期只投注一次
     * @param rs
     * @param listMutex
     * @throws NumberFormatException
     * @throws Exception
     */
    public void group2WorkStart(RobotSetting rs, List<InterProcessSemaphoreMutex> listMutex) throws NumberFormatException, Exception {
    	log.info("【成功处理】机器人工作开始内容-监听者-接收消息:两组PK玩法{}",rs.getRobotId());
    	boolean isWork = false;
    	boolean isPush = false;//是否推送数据到前端
    	boolean isLottery = false;
    	String goodsIssue = "";
    	
    	//工作时间计算
    	long currentTime = System.currentTimeMillis() - Utlity.getCurrentTime("yyyy-MM-dd 08:00:00").getTime();
    	long startTime = rs.getWorktimeBegin().getTime();
		long endTime = rs.getWorktimeEnd().getTime();
		if(startTime >= endTime) {
			long oneDay = 1000L*60L*60L*24L;
			endTime += oneDay;
			currentTime += oneDay;
		}
		if((startTime <= currentTime) && (currentTime <= endTime)) {//工作时间内
        	List<FrontUserPaymentOrder> listOrder = new ArrayList<FrontUserPaymentOrder>();
        	List<GoodsIssueSharesnum> listNums = new ArrayList<GoodsIssueSharesnum>();
        	List<FrontUserPaidNumber> listUserNums = new ArrayList<FrontUserPaidNumber>();
        	List<LuckygameGoodsIssue> listGoodsIssue = new ArrayList<LuckygameGoodsIssue>();
        	List<LuckygameGoods> listGoods = new ArrayList<LuckygameGoods>();
        	List<FrontUserHistory> listHistory = new ArrayList<FrontUserHistory>();
        	//业务逻辑
        	//判断用户余额是否充足
        	FrontUserAccount fua = this.frontUserAccountDao.get(rs.getRobotId());
        	FrontUser fu = this.frontUserDao.get(rs.getRobotId());
        	if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {//判断账户状态-账户停用 停止工作 清空队列
        		rabbitAdmin.purgeQueue("worldmall.robot.ready.queue."+rs.getRobotId());//清空队列
        		return;
			}
        	BigDecimal balance = fua.getBalance();
        	BigDecimal totalPayment = fua.getTotalPayment();
        	Integer paymentTimes = fua.getPaymentTimes();
        	//按照规则获取抽奖信息列表
        	String[] goodsArr = rs.getGoods().split(",");//商品ID
        	Map<String, Object> searchMap = new HashMap<String, Object>();
        	searchMap.put("uuid", goodsArr);
        	searchMap.put("goodsPriceMax", rs.getGoodsPriceMax());//商品价格上限
        	searchMap.put("goodsPriceMin", rs.getGoodsPriceMin());//商品价格下限
//        	searchMap.put("maxPay", rs.getMaxPay());//投注上限
//        	searchMap.put("minPay", rs.getMinPay());//投注下限
        	searchMap.put("status", LuckygameGoodsIssueStatus.BETTING);
        	searchMap.put("gameType", rs.getGameType());
        	List<LuckygameGoodsIssue> list = this.luckygameGoodsIssueDao.getListByParams(searchMap);
        	if(list != null && list.size() > 0) {//20200416修改为随机一个商品下单
        		int randomNum = Utlity.getRandomNum(0, list.size());
        		LuckygameGoodsIssue lgi = list.get(randomNum);
        		if(lgi != null && Constants.GAME_TYPE_GROUP2.equals(lgi.getGameType())) {
        			/*
        			 * 获取锁-声明子节点
        			 */
    				InterProcessSemaphoreMutex mutex=new InterProcessSemaphoreMutex(curatorFramework,Constants.ZK_LOCK_PATHPREFIX+lgi.getUuid()+"-lock");
        			listMutex.add(mutex);
                    log.info("获取zookeeper锁"+lgi.getUuid());
                    
        			if(mutex.acquire(Constants.ZK_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
        				lgi = this.luckygameGoodsIssueDao.get(lgi.getUuid());
	        			//判断是否已投
	        			Boolean isPayment = false;
	        			searchMap.clear();
	        			searchMap.put("frontUser", rs.getRobotId());
	        			searchMap.put("goodsIssue", lgi.getUuid());
	        			Integer count = this.frontUserPaymentOrderDao.getCountByParams(searchMap);
	        			if(count != null && count > 0) {//本期已投-直接下次任务
	        				isPayment = true;
	//        				this.rabbitSenderService.robotWorkMessageSend(rs.getRobotId());
	//        				return;
	        			}
	        			if(!isPayment) {//未投，可继续
	    				
	        				//获取可投参与人次数 并 确定参与战队
	        				GoodsIssueSharesnum gis = this.goodsIssueSharesnumDao.get(lgi.getUuid());
	        				SharenumsPKVO spkvo = JSONUtils.json2obj(gis.getSharenums(), SharenumsPKVO.class);
	        				
	        				SharenumsVO luckynum = spkvo.getLucky();
	        				SharenumsVO raidernum = spkvo.getRaider();
	//            				Integer luckyShares = luckynum.getCurrentNums().size();
	//            				Integer raiderShares = raidernum.getCurrentNums().size();
	        				Map<String, SharenumsVO> groupMap = new HashMap<String, SharenumsVO>();
	        				List<Map<String, SharenumsVO>> groupnums = new ArrayList<Map<String, SharenumsVO>>();
	        				
	        				if(luckynum.getCurrentNums().size() > 0) {
	        					Map<String, SharenumsVO> luckyMap = new HashMap<String, SharenumsVO>();
	        					luckyMap.put(FrontUserPaymentOrderGroup.LUCKY, luckynum);
	        					groupnums.add(luckyMap);
	        				}
	        				if(raidernum.getCurrentNums().size() > 0) {
	        					Map<String, SharenumsVO> raiderMap = new HashMap<String, SharenumsVO>();
	        					raiderMap.put(FrontUserPaymentOrderGroup.RAIDER, raidernum);
	        					groupnums.add(raiderMap);
	        				}
	        				if(!groupnums.isEmpty()) {
	    						randomNum = Utlity.getRandomNum(0, groupnums.size());
	    						groupMap = groupnums.get(randomNum);
	        				} else {
	        					log.info("机器人工作组：暂无可参与次数");
	        					
	        				}
	        				if(!groupMap.isEmpty()) {
	        					SharenumsVO paymentShare = null;
	        					String paymentGroup = "";
	        					for(Map.Entry<String, SharenumsVO> vo : groupMap.entrySet()){//获取投注组和 投注号码集 
	        						paymentShare = vo.getValue();
	        						paymentGroup = vo.getKey();
	        					}
	        					List<Integer> currentNums = paymentShare.getCurrentNums();
	        					Integer maxShares = currentNums.size();//得到总可投次数
	        					
	            				/*
	            				 * 封装投注信息（--注意是否包尾参数）：订单order+抽奖号码分配
	            				 * 这里需要集群锁机制进行抽奖号码增减控制 避免资源争抢过度
	            				 */
	            				//计算本次投注额 = 商品单注额*随机份数
	            				int shares = 0;
	            				BigDecimal price = BigDecimal.ZERO;
	            				
	            				//增加机器人投注份额上限判断20200421 总投注份额的40% 向下取整
	            				Integer totalShares = BigDecimal.valueOf(0.4).multiply(BigDecimal.valueOf(Double.valueOf(lgi.getShares()/2))).setScale(0,BigDecimal.ROUND_DOWN).intValue();
	//                				if(totalShares > lgi.getRemainShares()) {
	//                					totalShares = lgi.getRemainShares();
	//                				}
	            				Map<String, Object> priceMap = this.getPrice(totalShares, lgi.getBetPerShare(), rs.getMinPay(), rs.getMaxPay());
	            				if(priceMap != null) {
	            					shares = getMin(Integer.valueOf(priceMap.get("shares").toString()), maxShares);//容易出现包尾的情况
	            					price = BigDecimal.valueOf(shares).multiply(lgi.getBetPerShare());
	            				} else {//不足一次，按一次算起
	            					shares = 1;
	            					price = lgi.getBetPerShare();
	            				}
	            				//判断余额是否充足
	            				if(balance.compareTo(price) < 0) {
	            					return;//余额不足 结束任务
	            				}
	            				//余额充足-封装订单
	            				isWork = true;
	            				FrontUserPaymentOrder fupo = new FrontUserPaymentOrder();
	            				fupo.setUuid(UUID.randomUUID().toString());
	            				fupo.setOrderNum(Utlity.getOrderNum());//订单号
	            				fupo.setFrontUser(fua.getFrontUser());
	            				fupo.setFrontUserShowId(fu.getShowId());
	            				fupo.setGameType(lgi.getGameType());
	            				fupo.setOrderType(FrontUserPaymentOrderType.USER_BET);
	            				
	            				fupo.setBuyCount(shares);
	            				fupo.setdAmount(price);
	            				fupo.setIsVoucherUsed(false);
	            				fupo.setGoodsIssue(lgi.getUuid());
	            				fupo.setGoodsId(lgi.getGoodsId());
	            				fupo.setPoundage(BigDecimal.ZERO);
	            				fupo.setActualDAmount(price);
	            				
	            				fupo.setCreatetime(new Timestamp(System.currentTimeMillis()));
	            				fupo.setStatus(FrontUserPaymentOrderStatus.SUCCESS);
	            				fupo.setRemark(lgi.getShortTitle());
	            				fupo.setPaymentGroup(paymentGroup);//新增投注战队组

	            				fupo.setDemoFlag(lgi.getDemoFlag());
	            				fupo.setInternationalInfo(lgi.getInternationalInfo());
	            				listOrder.add(fupo);//保存订单
	            				
	            				//账变信息
	            				FrontUserHistory fuh = new FrontUserHistory();
	            				fuh.setUuid(UUID.randomUUID().toString());
	            				fuh.setFrontUser(fupo.getFrontUser());
	            				fuh.setOrderNum(fupo.getOrderNum());
	            				fuh.setType(FrontUserHistoryType.USER_ADD);
	            				fuh.setOrderId(fupo.getUuid());
	            				fuh.setOrderType(Constants.ORDER_TYPE_USER_PAYMENT);
	            				fuh.setdAmount(fupo.getActualDAmount());
	            				fuh.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
	            				fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()).subtract(fupo.getActualDAmount()));
	            				fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_PAYMENT));
	            				fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
	            				fuh.setRemark(fupo.getRemark());
	            				listHistory.add(fuh);
	            				
	            				/*
	            				 * 分发抽奖号码
	            				 */
	//                				GoodsIssueSharesnum gis = this.goodsIssueSharesnumDao.get(lgi.getUuid());
	//                				SharenumsVO svo = JSONUtils.json2obj(gis.getSharenums(), SharenumsVO.class);
	//                				List<Integer> currentNums = paymentShare.getCurrentNums();
	            				List<Integer> usedNums = paymentShare.getUsedNums();
	            				List<Integer> userNums = new ArrayList<Integer>();
	            				for(int i = 0; i < shares; i++) {
	//                					userNums.add(currentNums.get(0));
	//                					usedNums.add(currentNums.get(0));
	//                					currentNums.remove(0);
	            					//20200512改为随机分号
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
	            				fupn.setFrontUser(fua.getFrontUser());
	            				fupn.setIssueGoods(lgi.getUuid());
	            				fupn.setOrderId(fupo.getUuid());
	            				SharenumsVO userNum = new SharenumsVO();
	            				userNum.setCurrentNums(userNums);
	            				fupn.setPaidSharenums(JSONUtils.obj2json(userNum));
	            				listUserNums.add(fupn);//保存用户抽奖号码
	            			
	            				//扣减余额
	            				balance = balance.subtract(price);
	            				//增加总投注额
	            				totalPayment = totalPayment.add(price);
	            				//增加总投注次数
	            				paymentTimes += 1;
	            				
	            				//扣减投注份额
	            				lgi.setBetShares(lgi.getBetShares().intValue() + shares);
	            				lgi.setRemainShares(lgi.getRemainShares().intValue() - shares);
	            				//如果可投注份额清零，则进入待开奖状态
	            				if(lgi.getRemainShares() <= 0) {

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
	            				}
	            				listGoodsIssue.add(lgi);
	            			}
	        			}
        			}
        			
        		}
        	}
        	fua.setBalance(balance);//更新余额
        	fua.setTotalPayment(totalPayment);
        	fua.setPaymentTimes(paymentTimes);
        	
        	if(isWork) {//入库操作
        		Map<String, Object> paramsMap = new HashMap<String, Object>();
        		paramsMap.put("frontUserAccount", fua);
        		paramsMap.put("luckygameGoodsIssue", listGoodsIssue);
        		paramsMap.put("issueNums", listNums);
        		paramsMap.put("paymentOrder", listOrder);
        		paramsMap.put("userHistory", listHistory);
        		paramsMap.put("frontUserNums", listUserNums);
        		paramsMap.put("luckygameGoods", listGoods);
        		
        		this.frontUserPaymentOrderDao.userBet(paramsMap);
        	}
        	
//        	//此次任务完成后，发送下次工作执行消息到ready队列，等待下次任务执行
//        	this.rabbitSenderService.robotWorkMessageSend(rs.getRobotId());
        	
        	
        	if(isPush) {
				this.rabbitSenderService.pushInfoStartMessageSend();
			}
        	if(isLottery && !Utlity.checkStringNull(goodsIssue)) {
        		//20200417增加异步开奖
				this.rabbitSenderService.lotterySend(goodsIssue);
        	}
		}
    	
    	
    	//此次任务完成后，发送下次工作执行消息到ready队列，等待下次任务执行
    	this.rabbitSenderService.robotWorkMessageSend(rs.getRobotId());
    }
    
    /**
     * 根据投注金额范围
     * 计算投注金额和投注份额
     * 
     * 20200423增加算法修改
     *  	商品总人次数=商品金额 / 每人次数金币 (取整，有小数即进位)
			投注人次数最低MI= 最小投注额/每人次数金币
			MI<1 则MI=1，MI>=1， MI取整（舍弃小数点）
			投注人次数最高MA= 最大投注额/每人次数金币
			MA<1 则MA=1,MA>=1，MA取整（进位）
			商品投注每笔上限 LH = 商品总人次数 *40% 取整（舍弃小数点）
										
			X = random（min(MI, LH) , min(MA，LH)）
			N = min (商品剩余人次数, X)
     * @param shares
     * @param betPreShare
     * @param minPay
     * @param maxPay
     * @return
     */
    private Map<String, Object> getPrice(int shares, BigDecimal betPreShare, BigDecimal minPay, BigDecimal maxPay) {
    	List<Map<String, Object>> priceList = new ArrayList<Map<String,Object>>();
    	for(int i = 1; i < shares + 1; i++) {
    		BigDecimal price = betPreShare.multiply(BigDecimal.valueOf(Double.valueOf(i)));
			Map<String, Object> priceMap = new HashMap<String, Object>();
			priceMap.put("shares", i);
			priceMap.put("price", price);
			priceList.add(priceMap);
    	}
    	if(!priceList.isEmpty() && priceList.size() > 0) {
    		int sharesNum = priceList.size();//LH
    		
    		int minShares = minPay.divide(betPreShare).setScale(0, BigDecimal.ROUND_DOWN).intValue() == 0 ? 1 : minPay.divide(betPreShare).setScale(0, BigDecimal.ROUND_DOWN).intValue();
    		int maxShares = maxPay.divide(betPreShare).setScale(0, BigDecimal.ROUND_DOWN).intValue() == 0 ? 1 : maxPay.divide(betPreShare).setScale(0, BigDecimal.ROUND_DOWN).intValue();
    		
    		int ranNum1 = getMin(minShares, sharesNum);
    		int ranNum2 = getMin(maxShares, sharesNum);
    		
    		int ranShares = Utlity.getRandomNum(getMin(ranNum1, ranNum2), getMax(ranNum1, ranNum2));

    		return priceList.get(ranShares - 1);
    	}
		return null;
    }
    
    /**
     * 获取最小值
     * @param num1
     * @param num2
     * @return
     */
    private static int getMin(int num1, int num2) {
    	if(num1 == num2) {
    		return num1;
    	}
    	return num1 > num2 ? num2 : num1;
    }
    
    
    /**
     * 获取最小值
     * @param num1
     * @param num2
     * @return
     */
    private static int getMax(int num1, int num2) {
    	if(num1 == num2) {
    		return num1;
    	}
    	return num1 > num2 ? num1 : num2;
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
	
//    public static void main(String[] args) {
//    	int total = 3000;
//    	int max = BigDecimal.valueOf(total).multiply(BigDecimal.valueOf(0.4)).setScale(0, BigDecimal.ROUND_DOWN).intValue();
//    	int rem = 3000;
//    	int i = 1;
//    	System.out.println(max);
//    	while(rem > 0) {
//    		System.out.println("第"+i+"次投注，rem:"+rem);
//    		Map<String, Object> priceMap = getPrice(max, BigDecimal.TEN, BigDecimal.valueOf(1), BigDecimal.valueOf(999));
//    		System.out.println(priceMap);
//    		int shares = Integer.valueOf(priceMap.get("shares").toString());
//			if(rem < shares) {//容易出现包尾的情况
//				shares = rem;
//			}
//			BigDecimal price = BigDecimal.valueOf(shares).multiply(BigDecimal.ONE);
//			System.out.println("shares:"+shares + "----------price:"+price);
//			rem = (rem-shares);
//			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			i++;
//    	}
//	}
}












