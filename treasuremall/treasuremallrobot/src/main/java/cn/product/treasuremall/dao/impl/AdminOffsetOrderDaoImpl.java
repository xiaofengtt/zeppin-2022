package cn.product.treasuremall.dao.impl;

import java.util.HashMap;
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

import cn.product.treasuremall.dao.ActivityInfoRecommendRankingDao;
import cn.product.treasuremall.dao.AdminOffsetOrderDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.FrontUserRechargeOrderDao;
import cn.product.treasuremall.entity.ActivityInfoRecommendRanking;
import cn.product.treasuremall.entity.AdminOffsetOrder;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserRechargeOrder;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.mapper.AdminOffsetOrderMapper;

@Component
public class AdminOffsetOrderDaoImpl implements AdminOffsetOrderDao{
	
	public static final Logger log= LoggerFactory.getLogger(AdminOffsetOrderDaoImpl.class);

	@Autowired
    private AdminOffsetOrderMapper adminOffsetOrderMapper;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;
	
	@Autowired
	private ActivityInfoRecommendRankingDao activityInfoRecommendRankingDao;
	
    @Autowired
    private CuratorFramework curatorFramework;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return adminOffsetOrderMapper.getCountByParams(params);
	}
	
    @Override
    public List<AdminOffsetOrder> getListByParams(Map<String, Object> params){
        return adminOffsetOrderMapper.getListByParams(params);
    }
    
	@Override
	@Cacheable(cacheNames="adminOffsetOrder",key="'adminOffsetOrder:' + #key")
	public AdminOffsetOrder get(String key) {
		return adminOffsetOrderMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(AdminOffsetOrder adminOffsetOrder) {
		return adminOffsetOrderMapper.insert(adminOffsetOrder);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "adminOffsetOrder", key = "'adminOffsetOrder:' + #key")})
	public int delete(String key) {
		return adminOffsetOrderMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "adminOffsetOrder", key = "'adminOffsetOrder:' + #adminOffsetOrder.uuid")})
	public int update(AdminOffsetOrder adminOffsetOrder) {
		return adminOffsetOrderMapper.updateByPrimaryKey(adminOffsetOrder);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "adminOffsetOrder", key = "'adminOffsetOrder:' + #aoo.uuid")})
	public void check(AdminOffsetOrder aoo, FrontUserAccount fua, FrontUserHistory fuh) {
		adminOffsetOrderMapper.updateByPrimaryKey(aoo);
		frontUserAccountDao.update(fua);
		frontUserHistoryDao.insert(fuh);
	}

	@Override
	public Integer getLeftCountByParams(Map<String, Object> params) {
		return adminOffsetOrderMapper.getLeftCountByParams(params);
	}

	@Override
	public List<AdminOffsetOrder> getLeftListByParams(Map<String, Object> params) {
		return adminOffsetOrderMapper.getLeftListByParams(params);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void recommendTask(Map<String, Object> params) {
		
		if(params.containsKey("adminOffsetOrder")) {
			List<AdminOffsetOrder> listOffset = (List<AdminOffsetOrder>) params.get("adminOffsetOrder");
			if(listOffset != null && listOffset.size() > 0) {
				for(AdminOffsetOrder aoo : listOffset) {
					this.adminOffsetOrderMapper.insert(aoo);
				}
			}
		}
		if(params.containsKey("frontUserHistory")) {
			List<FrontUserHistory> listHistory = (List<FrontUserHistory>) params.get("frontUserHistory");
			if(listHistory != null && listHistory.size() > 0) {
				for(FrontUserHistory fuh : listHistory) {
					this.frontUserHistoryDao.insert(fuh);
				}
			}
		}
		if(params.containsKey("frontUserRechargeOrder")) {
			List<FrontUserRechargeOrder> listOrder = (List<FrontUserRechargeOrder>) params.get("frontUserRechargeOrder");
			if(listOrder != null && listOrder.size() > 0) {
				for(FrontUserRechargeOrder furo : listOrder) {
					this.frontUserRechargeOrderDao.update(furo);
				}
			}
		}

		//注意集群同步，资源争抢问题
		if(params.containsKey("frontUserAccount")) {
			Map<String, FrontUserAccount> fuaMap = (Map<String, FrontUserAccount>) params.get("frontUserAccount");
			if(fuaMap != null && !fuaMap.isEmpty()) {
				for (Map.Entry<String, FrontUserAccount> m : fuaMap.entrySet()) {
			        FrontUserAccount fu = m.getValue();
			        
			        InterProcessMutex mutex=new InterProcessMutex(curatorFramework,Constants.ZK_LOCK_PATHPREFIX+fu.getFrontUser()+"-lock");
					log.info("获取zookeeper锁" + Constants.ZK_LOCK_PATHPREFIX+fu.getFrontUser());
					try {
						if(mutex.acquire(Constants.ZK_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
							Map<String, Object> paMap = new HashMap<String, Object>();
					        paMap.put("uuid", m.getKey());
					        paMap.put("balance", fu.getBalance());
					        this.frontUserAccountDao.updateInfo(fu, paMap);
						}
					} catch (Exception e) {
						log.info("获取zookeeper锁" + Constants.ZK_LOCK_PATHPREFIX+fu.getFrontUser()+"-----异常:"+e.fillInStackTrace());
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
			}
		}
		
		//更新排行榜
		if(params.containsKey("activityInfoRecommendRanking")) {
			Map<String, ActivityInfoRecommendRanking> airrMap = (Map<String, ActivityInfoRecommendRanking>) params.get("activityInfoRecommendRanking");
			if(airrMap != null && !airrMap.isEmpty()) {
				for (Map.Entry<String, ActivityInfoRecommendRanking> m : airrMap.entrySet()) {
					ActivityInfoRecommendRanking airr = m.getValue();
					if(this.activityInfoRecommendRankingDao.getByFrontUser(airr.getFrontUser()) != null) {
						this.activityInfoRecommendRankingDao.update(airr);
					} else {
						this.activityInfoRecommendRankingDao.insert(airr);
					}
				}
			}
		}
	}
	
}