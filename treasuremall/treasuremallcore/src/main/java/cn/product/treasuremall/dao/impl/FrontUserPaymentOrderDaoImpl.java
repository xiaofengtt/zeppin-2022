package cn.product.treasuremall.dao.impl;

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

import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.FrontUserPaidNumberDao;
import cn.product.treasuremall.dao.FrontUserPaymentOrderDao;
import cn.product.treasuremall.dao.FrontUserScoreHistoryDao;
import cn.product.treasuremall.dao.FrontUserVoucherDao;
import cn.product.treasuremall.dao.GoodsIssueSharesnumDao;
import cn.product.treasuremall.dao.LuckygameGoodsDao;
import cn.product.treasuremall.dao.LuckygameGoodsIssueDao;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserPaidNumber;
import cn.product.treasuremall.entity.FrontUserPaymentOrder;
import cn.product.treasuremall.entity.FrontUserScoreHistory;
import cn.product.treasuremall.entity.FrontUserVoucher;
import cn.product.treasuremall.entity.GoodsIssueSharesnum;
import cn.product.treasuremall.entity.LuckygameGoods;
import cn.product.treasuremall.entity.LuckygameGoodsIssue;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.mapper.FrontUserPaymentOrderMapper;

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
	 * ?????????????????????????????????
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void userBet(Map<String, Object> params) {
		
		if(params.get("frontUserAccount") != null) {//?????????????????????????????????????????????
			FrontUserAccount fua = (FrontUserAccount)params.get("frontUserAccount");
			InterProcessMutex mutex=new InterProcessMutex(curatorFramework,Constants.ZK_LOCK_PATHPREFIX+fua.getFrontUser()+"-lock");
			log.info("??????zookeeper???" + Constants.ZK_LOCK_PATHPREFIX+fua.getFrontUser());
			try {
				if(mutex.acquire(Constants.ZK_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
					this.frontUserAccountDao.update(fua);
				}
			} catch (Exception e) {
				log.info("??????zookeeper???" + Constants.ZK_LOCK_PATHPREFIX+fua.getFrontUser()+"-----??????:"+e.fillInStackTrace());
			} finally {
    			if (mutex!=null){
                    try {
						mutex.release();//?????????
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
	        }
		}
		//????????????
		if(params.get("userHistory") != null) {
			List<FrontUserHistory> list = (List<FrontUserHistory>) params.get("userHistory");
			for(FrontUserHistory fuh : list) {
				this.frontUserHistoryDao.insert(fuh);
			}
		}
		
		//??????????????????
		if(params.get("luckygameGoods") != null) {
			List<LuckygameGoods> list = (List<LuckygameGoods>) params.get("luckygameGoods");
			for(LuckygameGoods lgi : list) {
				this.luckygameGoodsDao.update(lgi);
			}
		}
		
		//??????????????????????????????????????????????????????
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
		
		//????????????
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
		//20200518??????????????????
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
