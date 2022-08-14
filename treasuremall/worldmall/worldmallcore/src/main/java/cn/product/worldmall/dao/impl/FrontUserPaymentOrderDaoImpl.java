package cn.product.worldmall.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserPaidNumberDao;
import cn.product.worldmall.dao.FrontUserPaymentOrderDao;
import cn.product.worldmall.dao.FrontUserScoreHistoryDao;
import cn.product.worldmall.dao.FrontUserVoucherDao;
import cn.product.worldmall.dao.GoodsIssueSharesnumDao;
import cn.product.worldmall.dao.LuckygameGoodsDao;
import cn.product.worldmall.dao.LuckygameGoodsIssueDao;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserPaidNumber;
import cn.product.worldmall.entity.FrontUserPaymentOrder;
import cn.product.worldmall.entity.FrontUserScoreHistory;
import cn.product.worldmall.entity.FrontUserVoucher;
import cn.product.worldmall.entity.GoodsIssueSharesnum;
import cn.product.worldmall.entity.LuckygameGoods;
import cn.product.worldmall.entity.LuckygameGoodsIssue;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.mapper.FrontUserPaymentOrderMapper;

@Component
public class FrontUserPaymentOrderDaoImpl implements FrontUserPaymentOrderDao{
	
	public static final Logger log= LoggerFactory.getLogger(FrontUserPaymentOrderDaoImpl.class);
	
	@Autowired
	private FrontUserPaymentOrderMapper frontUserPaymentOrderMapper;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private GoodsIssueSharesnumDao goodsIssueSharesnumDao;
	
	@Autowired
	private FrontUserPaidNumberDao frontUserPaidNumberDao;
	
	@Autowired
	private LuckygameGoodsDao luckygameGoodsDao;	
	
	@Autowired
	private LuckygameGoodsIssueDao luckygameGoodsIssueDao;	
	
	@Autowired
	private FrontUserVoucherDao frontUserVoucherDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
    @Autowired
    private CuratorFramework curatorFramework;
    
    @Autowired
    private FrontUserScoreHistoryDao frontUserScoreHistoryDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserPaymentOrderMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserPaymentOrder> getListByParams(Map<String, Object> params) {
		return frontUserPaymentOrderMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserPaymentOrder",key="'frontUserPaymentOrder:' + #key")
	public FrontUserPaymentOrder get(String key) {
		return frontUserPaymentOrderMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserPaymentOrder frontUserPaymentOrder) {
		return frontUserPaymentOrderMapper.insert(frontUserPaymentOrder);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserPaymentOrder", key = "'frontUserPaymentOrder:' + #key")})
	public int delete(String key) {
		return frontUserPaymentOrderMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserPaymentOrder", key = "'frontUserPaymentOrder:' + #frontUserPaymentOrder.uuid")})
	public int update(FrontUserPaymentOrder frontUserPaymentOrder) {
		return frontUserPaymentOrderMapper.updateByPrimaryKey(frontUserPaymentOrder);
	}

	/**
	 * 机器人抽奖投注入库处理
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void userBet(Map<String, Object> params) {
		
		if(params.get("frontUserAccount") != null) {//机器人用户不需要同步到前端数据
			FrontUserAccount fua = (FrontUserAccount)params.get("frontUserAccount");
			InterProcessMutex mutex=new InterProcessMutex(curatorFramework,Constants.ZK_LOCK_PATHPREFIX+fua.getFrontUser()+"-lock");
			log.info("获取zookeeper锁" + Constants.ZK_LOCK_PATHPREFIX+fua.getFrontUser());
			try {
				if(mutex.acquire(Constants.ZK_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
					this.frontUserAccountDao.update(fua);
				}
			} catch (Exception e) {
				log.info("获取zookeeper锁" + Constants.ZK_LOCK_PATHPREFIX+fua.getFrontUser()+"-----异常:"+e.fillInStackTrace());
			} finally {
    			if (mutex!=null){
                    try {
						mutex.release();//释放锁
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
	        }
		}
		//账变入库
		if(params.get("userHistory") != null) {
			List<FrontUserHistory> list = (List<FrontUserHistory>) params.get("userHistory");
			for(FrontUserHistory fuh : list) {
				this.frontUserHistoryDao.insert(fuh);
			}
		}
		
		//更新下架商品
		if(params.get("luckygameGoods") != null) {
			List<LuckygameGoods> list = (List<LuckygameGoods>) params.get("luckygameGoods");
			for(LuckygameGoods lgi : list) {
				this.luckygameGoodsDao.update(lgi);
			}
		}
		
		//抽奖信息更新，抽奖信息需要同步到前端
		if(params.get("luckygameGoodsIssue") != null) {
			List<LuckygameGoodsIssue> list = (List<LuckygameGoodsIssue>) params.get("luckygameGoodsIssue");
			for(LuckygameGoodsIssue lgi : list) {
				this.luckygameGoodsIssueDao.update(lgi);
			}
		}
		if(params.get("issueNums") != null) {
			List<GoodsIssueSharesnum> list = (List<GoodsIssueSharesnum>) params.get("issueNums");
			for(GoodsIssueSharesnum gis : list) {
				this.goodsIssueSharesnumDao.update(gis);
			}
		}
		
		//订单入库
		if(params.get("paymentOrder") != null) {
			List<FrontUserPaymentOrder> list = (List<FrontUserPaymentOrder>) params.get("paymentOrder");
			for(FrontUserPaymentOrder fupo : list) {
				this.frontUserPaymentOrderMapper.insert(fupo);
			}
		}
		if(params.get("frontUserNums") != null) {
			List<FrontUserPaidNumber> list = (List<FrontUserPaidNumber>) params.get("frontUserNums");
			for(FrontUserPaidNumber fupn : list) {
				this.frontUserPaidNumberDao.insert(fupn);
			}
		}
		if(params.containsKey("voucher")) {
			FrontUserVoucher fuv = (FrontUserVoucher)params.get("voucher");
			this.frontUserVoucherDao.update(fuv);
		}
		//20200518增加积分入库
		if(params.containsKey("scoreHistory")) {
			List<FrontUserScoreHistory> list = (List<FrontUserScoreHistory>) params.get("scoreHistory");
			for(FrontUserScoreHistory fush : list) {
				this.frontUserScoreHistoryDao.insert(fush);
			}
		}
	}

	@Override
	public Integer getGroupCountByParams(Map<String, Object> params) {
		return this.frontUserPaymentOrderMapper.getGroupCountByParams(params);
	}

	@Override
	public List<FrontUserPaymentOrder> getGroupListByParams(Map<String, Object> params) {
		return this.frontUserPaymentOrderMapper.getGroupListByParams(params);
	}

	@Override
	public Integer getLeftCountByParams(Map<String, Object> params) {
		return this.frontUserPaymentOrderMapper.getLeftCountByParams(params);
	}

	@Override
	public List<FrontUserPaymentOrder> getLeftListByParams(Map<String, Object> params) {
		return this.frontUserPaymentOrderMapper.getLeftListByParams(params);
	}

	@Override
	public Integer getPaymentStatisticsCount(Map<String, Object> params) {
		return this.frontUserPaymentOrderMapper.getPaymentStatisticsCount(params);
	}
	
	@Override
	public List<Map<String, Object>> getPaymentStatistics(Map<String, Object> params) {
		return this.frontUserPaymentOrderMapper.getPaymentStatistics(params);
	}
}
