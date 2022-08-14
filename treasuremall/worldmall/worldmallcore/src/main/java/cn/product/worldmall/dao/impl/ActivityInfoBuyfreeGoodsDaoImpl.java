package cn.product.worldmall.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.dao.ActivityInfoBuyfreeDao;
import cn.product.worldmall.dao.ActivityInfoBuyfreeGoodsDao;
import cn.product.worldmall.dao.ActivityInfoBuyfreeSharesnumDao;
import cn.product.worldmall.entity.ActivityInfoBuyfree;
import cn.product.worldmall.entity.ActivityInfoBuyfree.ActivityInfoBuyfreeStatus;
import cn.product.worldmall.entity.ActivityInfoBuyfreeGoods;
import cn.product.worldmall.entity.ActivityInfoBuyfreeSharesnum;
import cn.product.worldmall.mapper.ActivityInfoBuyfreeGoodsMapper;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.SharenumsVO;

@Component
public class ActivityInfoBuyfreeGoodsDaoImpl implements ActivityInfoBuyfreeGoodsDao {
	
	@Autowired
    private ActivityInfoBuyfreeGoodsMapper activityInfoBuyfreeGoodsMapper;
	
	@Autowired
    private ActivityInfoBuyfreeDao activityInfoBuyfreeDao;
	
	@Autowired
    private ActivityInfoBuyfreeSharesnumDao activityInfoBuyfreeSharesnumDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return activityInfoBuyfreeGoodsMapper.getCountByParams(params);
	}
	
    @Override
    public List<ActivityInfoBuyfreeGoods> getListByParams(Map<String, Object> params){
        return activityInfoBuyfreeGoodsMapper.getListByParams(params);
    }

	@Override
	@Cacheable(cacheNames="activityInfoBuyfreeGoods",key="'activityInfoBuyfreeGoods:' + #key")
	public ActivityInfoBuyfreeGoods get(String key) {
		return activityInfoBuyfreeGoodsMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoBuyfreeGoodsList", allEntries = true)})
	public int insert(ActivityInfoBuyfreeGoods activityInfoBuyfreeGoods) {
		return activityInfoBuyfreeGoodsMapper.insert(activityInfoBuyfreeGoods);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoBuyfreeGoods", key = "'activityInfoBuyfreeGoods:' + #key"),
			@CacheEvict(value = "activityInfoBuyfreeGoodsList", allEntries = true)})
	public int delete(String key) {
		return activityInfoBuyfreeGoodsMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfoBuyfreeGoods", key = "'activityInfoBuyfreeGoods:' + #activityInfoBuyfreeGoods.uuid"),
			@CacheEvict(value = "activityInfoBuyfreeGoodsList", allEntries = true)})
	public int update(ActivityInfoBuyfreeGoods activityInfoBuyfreeGoods) {
		return activityInfoBuyfreeGoodsMapper.updateByPrimaryKey(activityInfoBuyfreeGoods);
	}

	@Override
	@Cacheable(cacheNames="activityInfoBuyfreeGoodsList",key="'activityInfoBuyfreeGoodsList:' + #activity")
	public List<ActivityInfoBuyfreeGoods> getListByActivityInfo(String activity) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("activityInfo", activity);
		return this.activityInfoBuyfreeGoodsMapper.getListByParams(searchMap);
	}
	
	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "activityInfoBuyfreeGoods", key = "'activityInfoBuyfreeGoods:' + #activityInfoBuyfreeGoods.uuid"),
			@CacheEvict(value = "activityInfoBuyfreeGoodsList", allEntries = true)})
	public void updateStatus(ActivityInfoBuyfreeGoods activityInfoBuyfreeGoods, ActivityInfoBuyfree aibf) {
		this.activityInfoBuyfreeGoodsMapper.updateByPrimaryKey(activityInfoBuyfreeGoods);
		this.activityInfoBuyfreeDao.insert(aibf);
		//生成抽奖号码
		/*
		 * 生成指定个数的抽奖号码
		 * 抽奖号码长度8位
		 */
		List<Integer> nums = Utlity.createNumbersByLength(Utlity.LUCKY_NUM_START, aibf.getShares());
		ActivityInfoBuyfreeSharesnum aibfs = new ActivityInfoBuyfreeSharesnum();
		SharenumsVO svo = new SharenumsVO();
		svo.setCurrentNums(nums);
		svo.setUsedNums(new ArrayList<Integer>());
		aibfs.setActivityInfoBuyfree(aibf.getUuid());
		aibfs.setSharenums(JSONUtils.obj2json(svo));
		this.activityInfoBuyfreeSharesnumDao.insert(aibfs);
	}

	@Override
	@Transactional
	public void updateSort(ActivityInfoBuyfreeGoods activityInfoBuyfreeGoods, Map<String, Object> params) {
		String sorttype = params.get("sorttype").toString();
//		this.activityInfoBuyfreeGoodsMapper.updateByPrimaryKey(activityInfoBuyfreeGoods);
		List<ActivityInfoBuyfreeGoods> listGoods = this.activityInfoBuyfreeGoodsMapper.getListByParams(new HashMap<String, Object>());
		if(listGoods != null && listGoods.size() > 0) {
			if("up".equals(sorttype)) {//向上
				if(activityInfoBuyfreeGoods.getSort() < listGoods.size()) {
					for(ActivityInfoBuyfreeGoods lg : listGoods) {
						if(lg.getSort().intValue() == activityInfoBuyfreeGoods.getSort().intValue() + 1) {
							lg.setSort(lg.getSort().intValue() - 1);
							this.activityInfoBuyfreeDao.updateGoodsIssue(lg);
							params.put("activityInfoBuyfreeGoods", lg.getUuid());
							params.put("sort", lg.getSort());
							this.activityInfoBuyfreeDao.updateSorts(params);
						} else if (lg.getUuid().equals(activityInfoBuyfreeGoods.getUuid())) {
							lg.setSort(lg.getSort().intValue() + 1);
							this.activityInfoBuyfreeDao.updateGoodsIssue(lg);
							params.put("activityInfoBuyfreeGoods", lg.getUuid());
							params.put("sort", lg.getSort());
							this.activityInfoBuyfreeDao.updateSorts(params);
						}
					}
				}
			} else if ("down".equals(sorttype)) {
				if(activityInfoBuyfreeGoods.getSort() > 0) {
					for(ActivityInfoBuyfreeGoods lg : listGoods) {
						if(lg.getSort().intValue() == activityInfoBuyfreeGoods.getSort().intValue() - 1) {
							lg.setSort(lg.getSort().intValue() + 1);
							this.activityInfoBuyfreeDao.updateGoodsIssue(lg);
							params.put("activityInfoBuyfreeGoods", lg.getUuid());
							params.put("sort", lg.getSort());
							this.activityInfoBuyfreeDao.updateSorts(params);
						} else if (lg.getUuid().equals(activityInfoBuyfreeGoods.getUuid())) {
							lg.setSort(lg.getSort().intValue() - 1);
							this.activityInfoBuyfreeDao.updateGoodsIssue(lg);
							params.put("activityInfoBuyfreeGoods", lg.getUuid());
							params.put("sort", lg.getSort());
							this.activityInfoBuyfreeDao.updateSorts(params);
						}
					}
				}
			} else {//置顶 
				if(activityInfoBuyfreeGoods.getSort().intValue() < listGoods.size()) {
					for(ActivityInfoBuyfreeGoods lg : listGoods) {
						if(lg.getUuid().equals(activityInfoBuyfreeGoods.getUuid())) {
							lg.setSort(listGoods.size());
							this.activityInfoBuyfreeDao.updateGoodsIssue(lg);
							params.put("activityInfoBuyfreeGoods", lg.getUuid());
							params.put("sort", lg.getSort());
							this.activityInfoBuyfreeDao.updateSorts(params);
						} else {
							if(lg.getSort().intValue() > activityInfoBuyfreeGoods.getSort().intValue()) {
								lg.setSort(lg.getSort().intValue() - 1);
								this.activityInfoBuyfreeDao.updateGoodsIssue(lg);
								params.put("activityInfoBuyfreeGoods", lg.getUuid());
								params.put("sort", lg.getSort());
								this.activityInfoBuyfreeDao.updateSorts(params);
							}
						}
					}
				}
			}
		}
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "activityInfoBuyfreeGoods", key = "'activityInfoBuyfreeGoods:' + #activityInfoBuyfreeGoods.uuid"),
			@CacheEvict(value = "activityInfoBuyfreeGoodsList", allEntries = true)})
	public void delete(ActivityInfoBuyfreeGoods activityInfoBuyfreeGoods, Map<String, Object> params) {
		this.activityInfoBuyfreeGoodsMapper.updateByPrimaryKey(activityInfoBuyfreeGoods);
		this.activityInfoBuyfreeDao.batchUpdateStatus(params);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "activityInfoBuyfreeGoods", key = "'activityInfoBuyfreeGoods:' + #activityInfoBuyfreeGoods.uuid"),
			@CacheEvict(value = "activityInfoBuyfreeGoodsList", allEntries = true)})
	public void updateStatus(ActivityInfoBuyfreeGoods activityInfoBuyfreeGoods, List<ActivityInfoBuyfree> list) {
		this.activityInfoBuyfreeGoodsMapper.updateByPrimaryKey(activityInfoBuyfreeGoods);
		if(list != null) {
			for(ActivityInfoBuyfree lgi : list) {
				lgi.setStatus(ActivityInfoBuyfreeStatus.DELETE);
				this.activityInfoBuyfreeDao.update(lgi);
			}
		}
	}

	@Override
	public void updateIssue(ActivityInfoBuyfree activityInfoBuyfree) {
		this.activityInfoBuyfreeDao.update(activityInfoBuyfree);	
	}
}
