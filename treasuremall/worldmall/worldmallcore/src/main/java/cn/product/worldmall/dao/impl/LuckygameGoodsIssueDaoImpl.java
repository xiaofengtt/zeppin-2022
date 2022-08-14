package cn.product.worldmall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserPaymentOrderDao;
import cn.product.worldmall.dao.GoodsIssueSharesnumDao;
import cn.product.worldmall.dao.LuckygameGoodsDao;
import cn.product.worldmall.dao.LuckygameGoodsIssueDao;
import cn.product.worldmall.dao.WinningInfoDao;
import cn.product.worldmall.dao.WinningInfoReceiveDao;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserPaymentOrder;
import cn.product.worldmall.entity.LuckygameGoods;
import cn.product.worldmall.entity.LuckygameGoodsIssue;
import cn.product.worldmall.entity.WinningInfo;
import cn.product.worldmall.entity.WinningInfoReceive;
import cn.product.worldmall.mapper.LuckygameGoodsIssueMapper;

@Component
public class LuckygameGoodsIssueDaoImpl implements LuckygameGoodsIssueDao {
	
	@Autowired
    private LuckygameGoodsIssueMapper luckygameGoodsIssueMapper;
	
	@Autowired
    private FrontUserPaymentOrderDao frontUserPaymentOrderDao;
	
	@Autowired
    private WinningInfoDao winningInfoDao;
	
	@Autowired
    private LuckygameGoodsDao luckygameGoodsDao;
	
	@Autowired
    private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
    private GoodsIssueSharesnumDao goodsIssueSharesnumDao;
	
	@Autowired
    private WinningInfoReceiveDao winningInfoReceiveDao;
	
	@Autowired
    private FrontUserHistoryDao frontUserHistoryDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return luckygameGoodsIssueMapper.getCountByParams(params);
	}
	
    @Override
    public List<LuckygameGoodsIssue> getListByParams(Map<String, Object> params){
        return luckygameGoodsIssueMapper.getListByParams(params);
    }

	@Override
	@Cacheable(cacheNames="luckygameGoodsIssue",key="'luckygameGoodsIssue:' + #key")
	public LuckygameGoodsIssue get(String key) {
		return luckygameGoodsIssueMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "luckygameGoodsIssueCurrent", key = "'luckygameGoodsIssueCurrent:' + #luckygameGoodsIssue.luckygameGoods")})
	public int insert(LuckygameGoodsIssue luckygameGoodsIssue) {
		return luckygameGoodsIssueMapper.insert(luckygameGoodsIssue);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "luckygameGoodsIssue", key = "'luckygameGoodsIssue:' + #key"), @CacheEvict(value = "luckygameGoodsIssueCurrent", allEntries=true)})
	public int delete(String key) {
		return luckygameGoodsIssueMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "luckygameGoodsIssue", key = "'luckygameGoodsIssue:' + #luckygameGoodsIssue.uuid")})
	public int update(LuckygameGoodsIssue luckygameGoodsIssue) {
		return luckygameGoodsIssueMapper.updateByPrimaryKey(luckygameGoodsIssue);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "luckygameGoodsIssue", allEntries=true)})
	public void updateSorts(Map<String, Object> params) {
		this.luckygameGoodsIssueMapper.updateSorts(params);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "luckygameGoodsIssue", allEntries=true)})
	public void updateOtherSorts(Map<String, Object> params) {
		this.luckygameGoodsIssueMapper.updateOtherSorts(params);
	}

	@Override
	public void updateGoodsIssue(LuckygameGoods luckygameGoods) {
		this.luckygameGoodsDao.update(luckygameGoods);
	}
	
	@Override
	@Caching(evict={@CacheEvict(value = "luckygameGoodsIssue", allEntries=true),@CacheEvict(value = "luckygameGoodsIssueCurrent", allEntries=true)})
	public void batchUpdateStatus(Map<String, Object> params) {
		this.luckygameGoodsIssueMapper.batchUpdateStatus(params);
	}

	@Override
	@Transactional
	public void taskLottery(Map<String, FrontUserAccount> mapfua, List<FrontUserPaymentOrder> listLuck, List<WinningInfo> listwin,
			List<LuckygameGoodsIssue> listlgi) {
		
		for (Map.Entry<String, FrontUserAccount> m : mapfua.entrySet()) {
	        FrontUserAccount fu = m.getValue();
	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("uuid", m.getKey());
	        params.put("totalWinning", fu.getTotalWinning());
	        params.put("winningTimes", fu.getWinningTimes());
	        this.frontUserAccountDao.updateInfo(fu, params);
	    }
		if(listLuck != null) {
			for(FrontUserPaymentOrder fupo : listLuck) {
				this.frontUserPaymentOrderDao.update(fupo);
			}
		}
		
		if(listwin != null) {
			for(WinningInfo wi : listwin) {
				this.winningInfoDao.insert(wi);
			}
		}
		
		if(listlgi != null) {
			for(LuckygameGoodsIssue lgi : listlgi) {
				this.luckygameGoodsDao.updateIssue(lgi);
			}
		}
	}

	@Override
	public void taskLottery(Map<String, FrontUserAccount> mapfua, List<FrontUserPaymentOrder> listLuck,
			List<WinningInfo> listwin, List<LuckygameGoodsIssue> listlgi, List<WinningInfoReceive> insertReceive, List<FrontUserHistory> insertHistory) {
		for (Map.Entry<String, FrontUserAccount> m : mapfua.entrySet()) {
	        FrontUserAccount fu = m.getValue();
	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("uuid", m.getKey());
	        params.put("balance", fu.getBalance());
	        params.put("totalWinning", fu.getTotalWinning());
	        params.put("winningTimes", fu.getWinningTimes());
	        params.put("totalExchange", fu.getTotalExchange());
	        params.put("exchangeTimes", fu.getExchangeTimes());
	        this.frontUserAccountDao.updateInfo(fu, params);
	    }
		if(listLuck != null) {
			for(FrontUserPaymentOrder fupo : listLuck) {
				this.frontUserPaymentOrderDao.update(fupo);
			}
		}
		
		if(listwin != null) {
			for(WinningInfo wi : listwin) {
				this.winningInfoDao.insert(wi);
			}
		}
		
		if(listlgi != null) {
			for(LuckygameGoodsIssue lgi : listlgi) {
				this.luckygameGoodsDao.updateIssue(lgi);
			}
		}
		
		if(insertReceive != null) {
			for(WinningInfoReceive wir : insertReceive) {
				this.winningInfoReceiveDao.insert(wir);
			}
		}
		
		if(insertHistory != null) {
			for(FrontUserHistory fuh : insertHistory) {
				this.frontUserHistoryDao.insert(fuh);
			}
		}
	}

	@Override
	@Cacheable(cacheNames="luckygameGoodsIssueCurrent",key="'luckygameGoodsIssueCurrent:' + #luckygameGoods")
	public LuckygameGoodsIssue getCurrentIssue(String luckygameGoods) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("luckygameGoods", luckygameGoods);
		searchMap.put("sort", "issue_num desc");
		List<LuckygameGoodsIssue> list = this.luckygameGoodsIssueMapper.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public LuckygameGoodsIssue getById(String uuid) {
		return luckygameGoodsIssueMapper.selectByPrimaryKey(uuid);
	}
}
