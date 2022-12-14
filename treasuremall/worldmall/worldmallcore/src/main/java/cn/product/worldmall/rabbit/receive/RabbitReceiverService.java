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
 * RabbitMQ??????????????????
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
    
    //????????????zookeeper ?????????????????????
    /*
     * ??????curator
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
     * ???????????????????????????
     * @param lg
     */
    @RabbitListener(queues = {"${mq.goodsIssus.start.queue}"},containerFactory = "multiListenerContainer")
    public void goodsIssueStart(LuckygameGoods lg) {
    	try {
    		LuckygameGoods lgRe = this.luckygameGoodsDao.get(lg.getUuid());
        	if(lgRe == null) {
        		throw new TransactionException("?????????????????????");
        	}
        	lgRe.setCurrentIssueNum(lgRe.getCurrentIssueNum() + 1);
        	
        	//????????????
//        	Goods goods = this.goodsDao.get(lgRe.getGoodsId());
//        	if(goods != null) {
//        		lgRe.setdPrice(goods.getPrice());
//        		BigDecimal total = lgRe.getdPrice().multiply(lgRe.getProfitRate()).setScale(0, BigDecimal.ROUND_UP);
//        		lgRe.setShares(total.divide(lgRe.getBetPerShare()).setScale(0, BigDecimal.ROUND_UP).intValue());
//        		lgRe.setTitle(goods.getName());
//        		lgRe.setShortTitle(goods.getShortname());
//        	}
    		
    		//????????????????????????
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
    		lginew.setIssueNum(lgRe.getCurrentIssueNum());//????????????

    		lginew.setDemoFlag(lgRe.getDemoFlag());
    		lginew.setInternationalInfo(lgRe.getInternationalInfo());
    		
    		this.luckygameGoodsDao.updateStatus(lgRe, lginew);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("???????????????????????????-?????????-???????????????",e.fillInStackTrace());
			return;
		}
    }
    
    /**
     * ???????????????????????????
     * @param lg
     */
    @RabbitListener(queues = {"${mq.buyfree.start.queue}"},containerFactory = "multiListenerContainer")
    public void buyfreegoodsIssueStart(ActivityInfoBuyfreeGoods aibg) {
    	try {
    		ActivityInfoBuyfreeGoods aibgRe = this.activityInfoBuyfreeGoodsDao.get(aibg.getUuid());
        	if(aibgRe == null) {
        		throw new TransactionException("0???????????????????????????");
        	}
        	aibgRe.setCurrentIssueNum(aibgRe.getCurrentIssueNum() + 1);
        	
        	//????????????
//        	Goods goods = this.goodsDao.get(lgRe.getGoodsId());
//        	if(goods != null) {
//        		lgRe.setdPrice(goods.getPrice());
//        		BigDecimal total = lgRe.getdPrice().multiply(lgRe.getProfitRate()).setScale(0, BigDecimal.ROUND_UP);
//        		lgRe.setShares(total.divide(lgRe.getBetPerShare()).setScale(0, BigDecimal.ROUND_UP).intValue());
//        		lgRe.setTitle(goods.getName());
//        		lgRe.setShortTitle(goods.getShortname());
//        	}
    		
        	//??????????????????
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
			log.error("?????????????????????0??????-?????????-???????????????",e.fillInStackTrace());
			return;
		}
    }
    
    /**
     * ???????????????web???
     * @param lg
     */
    @RabbitListener(queues = {"${mq.pushInfo.start.queue}"},containerFactory = "multiListenerContainer")
    public void pushInfoStart(Map<String, String> result) {
    	try {
    		//???????????????web??????/socket/websocket/pushInfo
    		Map<String, Object> resultMap = SocketSendUtil.pushWin(result);
    		if((Boolean)resultMap.get("request")) {
    			log.info("----------------??????---------------???"+resultMap.get("message"));
    		} else {
    			log.info("----------------??????????????????---------------???"+resultMap.get("message"));
    		}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("???????????????web???-?????????-???????????????",e.fillInStackTrace());
			return;
		}
    }
    
    
    @RabbitListener(queues = {"${mq.recommend.start.queue}"},containerFactory = "singleListenerContainer")
    public void recommendVoucherStart(FrontUser fu) {
    	try {
    		ActivityInfo ai = this.activityInfoDao.get(ActivityInfoName.RECOMMEND);
        	if(ai != null && ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {//???????????????????????????
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
						
						//??????voucher?????????????????????????????????????????????????????????
						fuv.setStarttime(Utlity.getTime(v.getStarttime()) == null ? new Timestamp(System.currentTimeMillis()) : Utlity.getTime(v.getStarttime()));//?????????????????????????????????
						fuv.setEndtime(Utlity.getTime(v.getEndtime()));
						fuv.setCreatetime(new Timestamp(System.currentTimeMillis()));
						fuv.setStatus(FrontUserVoucherStatus.UNSTART);
						fuv.setOperattime(fuv.getCreatetime());
						
						fuvList.add(fuv);
					}
					this.frontUserVoucherDao.insert(fuvList);
        		} else {//????????????????????????
        			this.rabbitSenderService.recommendStartMessageSend(fu);
        		}
        	}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("?????????????????????????????????-???????????????",e.fillInStackTrace());
			//???????????????????????????
			this.rabbitSenderService.recommendStartMessageSend(fu);
			return;
		}
    	
    }
    
    /**
     * ????????????????????????????????????-?????????
     * @param info
     */
    @RabbitListener(queues = {"${mq.kill.item.success.kill.dead.real.queue}"},containerFactory = "singleListenerContainer")
    public void consumeExpireOrder(String demoStr){
        try {
            log.info("????????????????????????????????????-?????????-????????????:{}",demoStr);

            if (demoStr!=null){
            	log.info("??????????????????????????????????????????????????????-?????????-????????????:{}",demoStr);
            	//????????????
            }
        }catch (Exception e){
            log.error("????????????????????????????????????-?????????-???????????????",e.fillInStackTrace());
        }
    }
}












