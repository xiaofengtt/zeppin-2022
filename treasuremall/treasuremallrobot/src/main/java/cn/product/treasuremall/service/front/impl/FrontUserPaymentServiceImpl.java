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
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.CapitalAccountDao;
import cn.product.treasuremall.dao.CapitalPlatformDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.FrontUserPaymentOrderDao;
import cn.product.treasuremall.dao.FrontUserVoucherDao;
import cn.product.treasuremall.dao.GoodsIssueSharesnumDao;
import cn.product.treasuremall.dao.LuckygameGoodsDao;
import cn.product.treasuremall.dao.LuckygameGoodsIssueDao;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUser.FrontUserStatus;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserAccount.FrontUserAccountStatus;
import cn.product.treasuremall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserPaidNumber;
import cn.product.treasuremall.entity.FrontUserPaymentOrder;
import cn.product.treasuremall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderStatus;
import cn.product.treasuremall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderType;
import cn.product.treasuremall.entity.FrontUserVoucher;
import cn.product.treasuremall.entity.FrontUserVoucher.FrontUserVoucherStatus;
import cn.product.treasuremall.entity.GoodsIssueSharesnum;
import cn.product.treasuremall.entity.LuckygameGoods;
import cn.product.treasuremall.entity.LuckygameGoods.LuckygameGoodsStatus;
import cn.product.treasuremall.entity.LuckygameGoodsIssue;
import cn.product.treasuremall.entity.LuckygameGoodsIssue.LuckygameGoodsIssueStatus;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.rabbit.send.RabbitSenderService;
import cn.product.treasuremall.service.front.FrontUserPaymentService;
import cn.product.treasuremall.util.Base64Util;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.SharenumsVO;

@Service("frontUserPaymentService")
public class FrontUserPaymentServiceImpl implements FrontUserPaymentService{
	
	public static final Logger log= LoggerFactory.getLogger(FrontUserPaymentServiceImpl.class);
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
	private CapitalPlatformDao capitalPlatformDao;
	
	@Autowired
	private CapitalAccountDao capitalAccountDao;
	
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
    
	/**
	 * 下单
	 * 下单逻辑：参照秒杀逻辑
	 * 对下单的商品，开启zk同步锁，顺序秒杀下单
	 * 获取前端下单参数：奖品ID、参与次数（buyCount）、金币总额、红包ID、实际支付金币额
	 * 需要判断：用户余额是否充足、商品剩余参与次数是否满足
	 * 需要的操作：创建订单、扣减账户余额、扣将商品次数、商品次数归零判断是否需要开启下一期、保存订单、保存账户信息、保存商品信息
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
    	
    	/*
		 * 获取锁-声明子节点
		 */
		InterProcessMutex mutex=new InterProcessMutex(curatorFramework,Constants.ZK_LOCK_PATHPREFIX+uuid+"-lock");
        log.info("获取zookeeper锁");
    	try {
    		List<FrontUserPaymentOrder> listOrder = new ArrayList<FrontUserPaymentOrder>();
        	List<GoodsIssueSharesnum> listNums = new ArrayList<GoodsIssueSharesnum>();
        	List<FrontUserPaidNumber> listUserNums = new ArrayList<FrontUserPaidNumber>();
        	List<LuckygameGoodsIssue> listGoodsIssue = new ArrayList<LuckygameGoodsIssue>();
        	List<LuckygameGoods> listGoods = new ArrayList<LuckygameGoods>();
        	List<FrontUserHistory> listHistory = new ArrayList<FrontUserHistory>();
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
    				
    			if(!FrontUserStatus.NORMAL.equals(fu.getStatus())) {
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
    	    	if(isAll) {
    	    		//重新计算下单金额
	    			buyCount = lgi.getRemainShares();
	    			dAmount = lgi.getBetPerShare().multiply(BigDecimal.valueOf(Double.valueOf(buyCount)));
    	    	}
    	    	
    	    	//增加包尾判断
    	    	if(lgi.getRemainShares().intValue() < buyCount.intValue()) {//次数不够
    	    		
    	    		if(!isAll) {
    	    			result.setMessage("当前抽奖可参与次数不足，请重新下单！");
        	    		result.setStatus(ResultStatusType.FAILED);
        	    		return;
    	    		} else {//包尾
    	    			//重新计算下单金额
    	    			buyCount = lgi.getRemainShares();
    	    			dAmount = lgi.getBetPerShare().multiply(BigDecimal.valueOf(Double.valueOf(buyCount)));
    	    		}
    	    		
    	    	}
    	    	
    	    	BigDecimal balance = account.getBalance();
            	BigDecimal totalPayment = account.getTotalPayment();
            	Integer paymentTimes = account.getPaymentTimes();
    	    	
    	    	//判断金额计算
    	    	BigDecimal betPreAmount = lgi.getBetPerShare();
    	    	if(betPreAmount.multiply(BigDecimal.valueOf(Double.valueOf(buyCount))).compareTo(dAmount) != 0) {
    	    		result.setMessage("下单金额错误！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    	    	}
    	    	
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
        	    	if(isAll) {
        	    		actualDAmount = dAmount.subtract(fuv.getdAmount());
        	    	}
        	    	
        	    	if(actualDAmount.compareTo(dAmount.subtract(fuv.getdAmount())) != 0) {
        	    		result.setMessage("下单金额错误！");
        	    		result.setStatus(ResultStatusType.FAILED);
        	    		return;
        	    	}
        	    	fuv.setStatus(FrontUserVoucherStatus.USED);
        	    	doMap.put("voucher", fuv);
    	    	}
    	    	
    	    	
    	    	//判断账户信息
    	    	if(balance.compareTo(actualDAmount) < 0) {
    	    		result.setMessage("账户余额不足，请先充值！");
    	    		result.setStatus(ResultStatusType.FAILED);
    	    		return;
    	    	}
    	    	FrontUserPaymentOrder fupo = new FrontUserPaymentOrder();
				fupo.setUuid(UUID.randomUUID().toString());
				fupo.setOrderNum(Utlity.getOrderNum());//订单号
				fupo.setFrontUser(account.getFrontUser());
				fupo.setFrontUserShowId(fu.getShowId());
				fupo.setGameType(lgi.getGameType());
				fupo.setOrderType(FrontUserPaymentOrderType.USER_BET);
				
				fupo.setBuyCount(buyCount);
				fupo.setdAmount(dAmount);
				fupo.setIsVoucherUsed(false);
				fupo.setGoodsIssue(lgi.getUuid());
				fupo.setGoodsId(lgi.getGoodsId());
				fupo.setPoundage(BigDecimal.ZERO);
				fupo.setActualDAmount(actualDAmount);
				
				fupo.setCreatetime(new Timestamp(System.currentTimeMillis()));
				fupo.setStatus(FrontUserPaymentOrderStatus.SUCCESS);
				fupo.setRemark("");
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
				fuh.setBalanceBefore(account.getBalance().add(account.getBalanceLock()));
				fuh.setBalanceAfter(account.getBalance().add(account.getBalanceLock()).subtract(fupo.getActualDAmount()));
				fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_PAYMENT));
				fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
				fuh.setRemark(fupo.getRemark());
				listHistory.add(fuh);
				
				/*
				 * 分发抽奖号码
				 */
				GoodsIssueSharesnum gis = this.goodsIssueSharesnumDao.get(lgi.getUuid());
				SharenumsVO svo = JSONUtils.json2obj(gis.getSharenums(), SharenumsVO.class);
				List<Integer> currentNums = svo.getCurrentNums();
				List<Integer> usedNums = svo.getUsedNums();
				List<Integer> userNums = new ArrayList<Integer>();
				for(int i = 0; i < buyCount; i++) {
					userNums.add(currentNums.get(0));
					usedNums.add(currentNums.get(0));
					currentNums.remove(0);
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
				
				//扣减投注份额
				lgi.setBetShares(lgi.getBetShares().intValue() + buyCount);
				lgi.setRemainShares(lgi.getRemainShares().intValue() - buyCount);
				//如果可投注份额清零，则进入待开奖状态
				if(lgi.getRemainShares() == 0) {
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
						//同步推送即将开奖消息到前端
						this.rabbitSenderService.pushInfoStartMessageSend();
					}
				}
				listGoodsIssue.add(lgi);
				
				account.setBalance(balance);//更新余额
				account.setTotalPayment(totalPayment);
				account.setPaymentTimes(paymentTimes);
				
				doMap.put("frontUserAccount", account);
				doMap.put("luckygameGoodsIssue", listGoodsIssue);
				doMap.put("issueNums", listNums);
				doMap.put("paymentOrder", listOrder);
				doMap.put("userHistory", listHistory);
				doMap.put("frontUserNums", listUserNums);
				doMap.put("luckygameGoods", listGoods);
        		
        		this.frontUserPaymentOrderDao.userBet(doMap);
        		result.setMessage("下单成功！");
        		result.setStatus(ResultStatusType.SUCCESS);
        		return;
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


}
