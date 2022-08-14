package cn.product.worldmall.rabbit.receive;/**
 * Created by Administrator on 2019/6/21.
 */

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import cn.product.worldmall.controller.base.TransactionException;
import cn.product.worldmall.dao.ActivityInfoBuyfreeGoodsDao;
import cn.product.worldmall.dao.ActivityInfoDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserMessageDao;
import cn.product.worldmall.dao.FrontUserVoucherDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.dao.LuckygameGoodsDao;
import cn.product.worldmall.dao.MobileCodeDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.dao.VoucherDao;
import cn.product.worldmall.entity.ActivityInfo;
import cn.product.worldmall.entity.ActivityInfo.ActivityInfoName;
import cn.product.worldmall.entity.ActivityInfo.ActivityInfoStatus;
import cn.product.worldmall.entity.ActivityInfoBuyfree;
import cn.product.worldmall.entity.ActivityInfoBuyfree.ActivityInfoBuyfreeStatus;
import cn.product.worldmall.entity.ActivityInfoBuyfreeGoods;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserVoucher;
import cn.product.worldmall.entity.FrontUserVoucher.FrontUserVoucherStatus;
import cn.product.worldmall.entity.LuckygameGoods;
import cn.product.worldmall.entity.LuckygameGoodsIssue;
import cn.product.worldmall.entity.LuckygameGoodsIssue.LuckygameGoodsIssueStatus;
import cn.product.worldmall.entity.Voucher;
import cn.product.worldmall.entity.Voucher.VoucherStatus;
import cn.product.worldmall.rabbit.send.RabbitSenderService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.SocketSendUtil;
import cn.product.worldmall.util.Utlity;

/**
 * RabbitMQ接收消息服务
 **/
@Service
public class RabbitReceiverService {

    public static final Logger log= LoggerFactory.getLogger(RabbitReceiverService.class);


    @Autowired
    private Environment environment;
    
    @Autowired
    private RabbitSenderService rabbitSenderService;

    @Autowired
    private LuckygameGoodsDao luckygameGoodsDao;
    
    @Autowired
    private FrontUserMessageDao frontUserMessageDao;
    
    @Autowired
    private GoodsDao goodsDao;
    
    @Autowired
    private MobileCodeDao mobileCodeDao;
    
    @Autowired
    private SystemParamDao systemParamDao;
    
    //使用基于zookeeper 有序集群同步锁
    /*
     * 注册curator
     */
    @Autowired
    private CuratorFramework curatorFramework;
	
	@Autowired
	private ActivityInfoBuyfreeGoodsDao activityInfoBuyfreeGoodsDao;
	
	@Autowired
	private ActivityInfoDao activityInfoDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private VoucherDao voucherDao;
	
	@Autowired
	private FrontUserVoucherDao frontUserVoucherDao;
    /**
     * 自动开启下一期抽奖
     * @param lg
     */
    @RabbitListener(queues = {"${mq.goodsIssus.start.queue}"},containerFactory = "multiListenerContainer")
    public void goodsIssueStart(LuckygameGoods lg) {
    	try {
    		LuckygameGoods lgRe = this.luckygameGoodsDao.get(lg.getUuid());
        	if(lgRe == null) {
        		throw new TransactionException("抽奖信息错误！");
        	}
        	lgRe.setCurrentIssueNum(lgRe.getCurrentIssueNum() + 1);
        	
        	//重新计算
//        	Goods goods = this.goodsDao.get(lgRe.getGoodsId());
//        	if(goods != null) {
//        		lgRe.setdPrice(goods.getPrice());
//        		BigDecimal total = lgRe.getdPrice().multiply(lgRe.getProfitRate()).setScale(0, BigDecimal.ROUND_UP);
//        		lgRe.setShares(total.divide(lgRe.getBetPerShare()).setScale(0, BigDecimal.ROUND_UP).intValue());
//        		lgRe.setTitle(goods.getName());
//        		lgRe.setShortTitle(goods.getShortname());
//        	}
    		
    		//封装抽奖期数信息
    		LuckygameGoodsIssue lginew = new LuckygameGoodsIssue();
    		lginew.setUuid(UUID.randomUUID().toString());
    		lginew.setLuckygameGoods(lgRe.getUuid());
    		lginew.setGoodsId(lgRe.getGoodsId());
    		lginew.setGoodsType(lgRe.getGoodsType());
    		lginew.setGameType(lgRe.getGameType());
    		lginew.setTitle(lgRe.getTitle());
    		lginew.setShortTitle(lgRe.getShortTitle());
    		lginew.setdPrice(lgRe.getdPrice());
    		lginew.setProfitRate(lgRe.getProfitRate());
    		lginew.setSort(lgRe.getSort());
    		lginew.setPromotionFlag(lgRe.getPromotionFlag());
    		
    		lginew.setCreatetime(new Timestamp(System.currentTimeMillis()));
    		lginew.setStatus(LuckygameGoodsIssueStatus.BETTING);
    		lginew.setShares(lgRe.getShares());
    		lginew.setBetPerShare(lgRe.getBetPerShare());
    		lginew.setBetShares(0);
    		lginew.setRemainShares(lgRe.getShares());
    		lginew.setIssueNum(lgRe.getCurrentIssueNum());//当前期数

    		lginew.setDemoFlag(lgRe.getDemoFlag());
    		lginew.setInternationalInfo(lgRe.getInternationalInfo());
    		
    		this.luckygameGoodsDao.updateStatus(lgRe, lginew);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("自动开启下一期抽奖-监听者-发生异常：",e.fillInStackTrace());
			return;
		}
    }
    
    /**
     * 自动开启下一期抽奖
     * @param lg
     */
    @RabbitListener(queues = {"${mq.buyfree.start.queue}"},containerFactory = "multiListenerContainer")
    public void buyfreegoodsIssueStart(ActivityInfoBuyfreeGoods aibg) {
    	try {
    		ActivityInfoBuyfreeGoods aibgRe = this.activityInfoBuyfreeGoodsDao.get(aibg.getUuid());
        	if(aibgRe == null) {
        		throw new TransactionException("0元购奖品信息错误！");
        	}
        	aibgRe.setCurrentIssueNum(aibgRe.getCurrentIssueNum() + 1);
        	
        	//重新计算
//        	Goods goods = this.goodsDao.get(lgRe.getGoodsId());
//        	if(goods != null) {
//        		lgRe.setdPrice(goods.getPrice());
//        		BigDecimal total = lgRe.getdPrice().multiply(lgRe.getProfitRate()).setScale(0, BigDecimal.ROUND_UP);
//        		lgRe.setShares(total.divide(lgRe.getBetPerShare()).setScale(0, BigDecimal.ROUND_UP).intValue());
//        		lgRe.setTitle(goods.getName());
//        		lgRe.setShortTitle(goods.getShortname());
//        	}
    		
        	//封装期数信息
			ActivityInfoBuyfree aib = new ActivityInfoBuyfree();
			aib.setUuid(UUID.randomUUID().toString());
			aib.setActivityInfo(aibgRe.getActivityInfo());
			aib.setActivityInfoBuyfreeGoods(aibgRe.getUuid());
			aib.setBetShares(0);
			aib.setCreatetime(new Timestamp(System.currentTimeMillis()));
			aib.setGoodsCover(aibgRe.getGoodsCover());
			aib.setGoodsId(aibgRe.getGoodsId());
			aib.setGoodsPrice(aibgRe.getGoodsPrice());
			aib.setGoodsTitle(aibgRe.getGoodsTitle());
			aib.setGoodsShortTitle(aibgRe.getGoodsShortTitle());
			aib.setIssueNum(aibgRe.getCurrentIssueNum());
			aib.setRemainShares(aibgRe.getShares());
			aib.setShares(aibgRe.getShares());
			aib.setSort(aibgRe.getSort());
			aib.setStatus(ActivityInfoBuyfreeStatus.BETTING);
    		
    		this.activityInfoBuyfreeGoodsDao.updateStatus(aibgRe, aib);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("自动开启下一期0元购-监听者-发生异常：",e.fillInStackTrace());
			return;
		}
    }
    
    /**
     * 发送请求到web层
     * @param lg
     */
    @RabbitListener(queues = {"${mq.pushInfo.start.queue}"},containerFactory = "multiListenerContainer")
    public void pushInfoStart(Map<String, String> result) {
    	try {
    		//发送请求到web层的/socket/websocket/pushInfo
    		Map<String, Object> resultMap = SocketSendUtil.pushWin(result);
    		if((Boolean)resultMap.get("request")) {
    			log.info("----------------成功---------------："+resultMap.get("message"));
    		} else {
    			log.info("----------------失败暂不重发---------------："+resultMap.get("message"));
    		}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("发送请求到web层-监听者-发生异常：",e.fillInStackTrace());
			return;
		}
    }
    
    
    @RabbitListener(queues = {"${mq.recommend.start.queue}"},containerFactory = "singleListenerContainer")
    public void recommendVoucherStart(FrontUser fu) {
    	try {
    		ActivityInfo ai = this.activityInfoDao.get(ActivityInfoName.RECOMMEND);
        	if(ai != null && ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {//发送开启下一期消息
        		FrontUser frontUser = this.frontUserDao.get(fu.getUuid());
        		if(frontUser != null) {
        			Map<String, Object> detailMap = JSONUtils.json2map(ai.getConfiguration());
        			if(detailMap.get("voucher") == null) {
        				return;
        			}
        			List<FrontUserVoucher> fuvList = new ArrayList<FrontUserVoucher>();
        			String voucher = detailMap.get("voucher").toString();
					String[] vouchers = voucher.split(",");
					for(String vou : vouchers){
						Voucher v = this.voucherDao.get(vou);
						if(v == null || !VoucherStatus.NORMAL.equals(v.getStatus())){
							return;
						}
						FrontUserVoucher fuv = new FrontUserVoucher();
						fuv.setUuid(UUID.randomUUID().toString());
						fuv.setFrontUser(frontUser.getUuid());
						fuv.setVoucher(v.getUuid());
						fuv.setTitle(v.getTitle());
						fuv.setDiscription(v.getDiscription());
						fuv.setdAmount(v.getdAmount());
						fuv.setPayMin(v.getPayMin());
						fuv.setGoodsType(v.getGoodsType());
						fuv.setGoods(v.getGoods());
						
						//按照voucher设定的起止时间，计算初始时间和结束时间
						fuv.setStarttime(Utlity.getTime(v.getStarttime()) == null ? new Timestamp(System.currentTimeMillis()) : Utlity.getTime(v.getStarttime()));//未设置代表当前时间生效
						fuv.setEndtime(Utlity.getTime(v.getEndtime()));
						fuv.setCreatetime(new Timestamp(System.currentTimeMillis()));
						fuv.setStatus(FrontUserVoucherStatus.UNSTART);
						fuv.setOperattime(fuv.getCreatetime());
						
						fuvList.add(fuv);
					}
					this.frontUserVoucherDao.insert(fuvList);
        		} else {//未注册，重新发送
        			this.rabbitSenderService.recommendStartMessageSend(fu);
        		}
        	}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("用户邀新注册，赠送金券-发生异常：",e.fillInStackTrace());
			//发生异常，重新发起
			this.rabbitSenderService.recommendStartMessageSend(fu);
			return;
		}
    	
    }
    
    /**
     * 用户秒杀成功后超时未支付-监听者
     * @param info
     */
    @RabbitListener(queues = {"${mq.kill.item.success.kill.dead.real.queue}"},containerFactory = "singleListenerContainer")
    public void consumeExpireOrder(String demoStr){
        try {
            log.info("用户秒杀成功后超时未支付-监听者-接收消息:{}",demoStr);

            if (demoStr!=null){
            	log.info("【成功处理】用户秒杀成功后超时未支付-监听者-接收消息:{}",demoStr);
            	//业务逻辑
            }
        }catch (Exception e){
            log.error("用户秒杀成功后超时未支付-监听者-发生异常：",e.fillInStackTrace());
        }
    }
}












