package cn.product.treasuremall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.treasuremall.dao.ActivityInfoBuyfreeDao;
import cn.product.treasuremall.dao.ActivityInfoBuyfreeGoodsDao;
import cn.product.treasuremall.dao.ActivityInfoBuyfreeSharesnumDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserBuyfreeOrderDao;
import cn.product.treasuremall.dao.WinningInfoDao;
import cn.product.treasuremall.entity.ActivityInfoBuyfree;
import cn.product.treasuremall.entity.ActivityInfoBuyfreeGoods;
import cn.product.treasuremall.entity.ActivityInfoBuyfreeSharesnum;
import cn.product.treasuremall.entity.FrontUserBuyfreeOrder;
import cn.product.treasuremall.mapper.ActivityInfoBuyfreeMapper;

@Component
public class ActivityInfoBuyfreeDaoImpl implements ActivityInfoBuyfreeDao {
	
	@Autowired
    private ActivityInfoBuyfreeMapper activityInfoBuyfreeMapper;
	
	@Autowired
    private FrontUserBuyfreeOrderDao frontUserPaymentOrderDao;
	
	@Autowired
    private WinningInfoDao winningInfoDao;
	
	@Autowired
    private ActivityInfoBuyfreeGoodsDao activityInfoBuyfreeGoodsDao;
	
	@Autowired
    private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
    private ActivityInfoBuyfreeSharesnumDao activityInfoBuyfreeSharesnumDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return activityInfoBuyfreeMapper.getCountByParams(params);
	}
	
    @Override
    public List<ActivityInfoBuyfree> getListByParams(Map<String, Object> params){
        return activityInfoBuyfreeMapper.getListByParams(params);
    }

	@Override
	@Cacheable(cacheNames="activityInfoBuyfree",key="'activityInfoBuyfree:' + #key")
	public ActivityInfoBuyfree get(String key) {
		return activityInfoBuyfreeMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(ActivityInfoBuyfree activityInfoBuyfree) {
		return activityInfoBuyfreeMapper.insert(activityInfoBuyfree);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoBuyfree", key = "'activityInfoBuyfree:' + #key")})
	public int delete(String key) {
		return activityInfoBuyfreeMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoBuyfree", key = "'activityInfoBuyfree:' + #activityInfoBuyfree.uuid")})
	public int update(ActivityInfoBuyfree activityInfoBuyfree) {
		return activityInfoBuyfreeMapper.updateByPrimaryKey(activityInfoBuyfree);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoBuyfree", allEntries=true)})
	public void updateSorts(Map<String, Object> params) {
		this.activityInfoBuyfreeMapper.updateSorts(params);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoBuyfree", allEntries=true)})
	public void updateOtherSorts(Map<String, Object> params) {
		this.activityInfoBuyfreeMapper.updateOtherSorts(params);
	}

	@Override
	public void updateGoodsIssue(ActivityInfoBuyfreeGoods activityInfoBuyfreeGoods) {
		this.activityInfoBuyfreeGoodsDao.update(activityInfoBuyfreeGoods);
	}
	
	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoBuyfree", allEntries=true)})
	public void batchUpdateStatus(Map<String, Object> params) {
		this.activityInfoBuyfreeMapper.batchUpdateStatus(params);
	}

	@Override
	@Transactional
	public void taskLottery(List<FrontUserBuyfreeOrder> listLuck, List<ActivityInfoBuyfree> listaibf,
			List<ActivityInfoBuyfree> listaibfnew, List<ActivityInfoBuyfreeGoods> listaibfg,
			List<ActivityInfoBuyfreeSharesnum> listaibfs) {
		if(listLuck != null) {
			for(FrontUserBuyfreeOrder fubo : listLuck) {
				this.frontUserPaymentOrderDao.update(fubo);
			}
		}
		
		if(listaibf != null) {
			for(ActivityInfoBuyfree lgi : listaibf) {
				this.activityInfoBuyfreeGoodsDao.updateIssue(lgi);
			}
		}
		
		if(listaibfnew != null) {
			for(ActivityInfoBuyfree lgi : listaibfnew) {
				this.activityInfoBuyfreeMapper.insert(lgi);
			}
		}
		
		if(listaibfs != null) {
			for(ActivityInfoBuyfreeSharesnum aibfs : listaibfs) {
				this.activityInfoBuyfreeSharesnumDao.insert(aibfs);
			}
		}
		
		if(listaibfg != null) {
			for(ActivityInfoBuyfreeGoods aibfg : listaibfg) {
				this.activityInfoBuyfreeGoodsDao.update(aibfg);
			}
		}		
	}


}
