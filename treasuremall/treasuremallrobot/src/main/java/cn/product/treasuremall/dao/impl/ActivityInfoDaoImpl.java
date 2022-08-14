package cn.product.treasuremall.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.treasuremall.dao.ActivityInfoBuyfreeDao;
import cn.product.treasuremall.dao.ActivityInfoBuyfreeGoodsDao;
import cn.product.treasuremall.dao.ActivityInfoBuyfreeSharesnumDao;
import cn.product.treasuremall.dao.ActivityInfoCheckinPrizeDao;
import cn.product.treasuremall.dao.ActivityInfoDao;
import cn.product.treasuremall.dao.ActivityInfoFirstchargePrizeDao;
import cn.product.treasuremall.dao.ActivityInfoRechargePrizeDao;
import cn.product.treasuremall.dao.ActivityInfoScorelotteryPrizeDao;
import cn.product.treasuremall.entity.ActivityInfo;
import cn.product.treasuremall.entity.ActivityInfoBuyfree;
import cn.product.treasuremall.entity.ActivityInfoBuyfreeGoods;
import cn.product.treasuremall.entity.ActivityInfoBuyfreeSharesnum;
import cn.product.treasuremall.entity.ActivityInfoCheckinPrize;
import cn.product.treasuremall.entity.ActivityInfoFirstchargePrize;
import cn.product.treasuremall.entity.ActivityInfoRechargePrize;
import cn.product.treasuremall.entity.ActivityInfoScorelotteryPrize;
import cn.product.treasuremall.mapper.ActivityInfoMapper;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.SharenumsVO;

/**
 */
@Component
public class ActivityInfoDaoImpl implements ActivityInfoDao{
	
	private final static Logger log = LoggerFactory.getLogger(ActivityInfoDaoImpl.class);
	
	@Autowired
	private ActivityInfoMapper activityInfoMapper;
	
	@Autowired
    private ActivityInfoBuyfreeGoodsDao activityInfoBuyfreeGoodsDao;
	
	@Autowired
    private ActivityInfoBuyfreeDao activityInfoBuyfreeDao;
	
	@Autowired
    private ActivityInfoBuyfreeSharesnumDao activityInfoBuyfreeSharesnumDao;
	
	@Autowired
    private ActivityInfoCheckinPrizeDao activityInfoCheckinPrizeDao;
	
	@Autowired
    private ActivityInfoScorelotteryPrizeDao activityInfoScorelotteryPrizeDao;
	
	@Autowired
    private ActivityInfoFirstchargePrizeDao activityInfoFirstchargePrizeDao;
	
	@Autowired
    private ActivityInfoRechargePrizeDao activityInfoRechargePrizeDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return activityInfoMapper.getCountByParams(params);
	}
	
	@Override
	public List<ActivityInfo> getListByParams(Map<String, Object> params) {
		return activityInfoMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="activityInfo",key="'activityInfo:' + #key")
	public ActivityInfo get(String key) {
		return activityInfoMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(ActivityInfo activityInfo) {
		return activityInfoMapper.insert(activityInfo);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfo", key = "'activityInfo:' + #key")})
	public int delete(String key) {
		return activityInfoMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "activityInfo", key = "'activityInfo:' + #activityInfo.name")})
	public int update(ActivityInfo activityInfo) {
		return activityInfoMapper.updateByPrimaryKey(activityInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Caching(evict={@CacheEvict(value = "activityInfo", key = "'activityInfo:' + #activityInfo.name")})
	@Transactional
	public void editBuyfree(ActivityInfo activityInfo, Map<String, Object> editMap) {
		this.activityInfoMapper.updateByPrimaryKey(activityInfo);
		if(editMap.containsKey("add")) {
			List<ActivityInfoBuyfreeGoods> addList = editMap.get("add") == null ? null : (List<ActivityInfoBuyfreeGoods>)editMap.get("add");
			if(addList != null && addList.size() > 0) {
				for(ActivityInfoBuyfreeGoods aibg : addList) {
					this.activityInfoBuyfreeGoodsDao.insert(aibg);
				}
			}
		}
		
		if(editMap.containsKey("edit")) {
			List<ActivityInfoBuyfreeGoods> editList = editMap.get("edit") == null ? null : (List<ActivityInfoBuyfreeGoods>)editMap.get("edit");
			if(editList != null && editList.size() > 0) {
				for(ActivityInfoBuyfreeGoods aibg : editList) {
					this.activityInfoBuyfreeGoodsDao.update(aibg);
				}
			}
		} 

		if(editMap.containsKey("delete")) {
			List<ActivityInfoBuyfreeGoods> deleteList = editMap.get("delete") == null ? null : (List<ActivityInfoBuyfreeGoods>)editMap.get("delete");
			if(deleteList != null && deleteList.size() > 0) {
				for(ActivityInfoBuyfreeGoods aibg : deleteList) {
					this.activityInfoBuyfreeGoodsDao.update(aibg);
				}
			}
		}
		
		if(editMap.containsKey("issueAdd")) {
			List<ActivityInfoBuyfree> issueAddList = editMap.get("issueAdd") == null ? null : (List<ActivityInfoBuyfree>)editMap.get("issueAdd");
			if(issueAddList != null && issueAddList.size() > 0) {
				for(ActivityInfoBuyfree aib : issueAddList) {
					this.activityInfoBuyfreeDao.insert(aib);
					
					//生成抽奖号码
					/*
					 * 生成指定个数的抽奖号码
					 * 抽奖号码长度8位
					 */
					List<Integer> nums = Utlity.createNumbersByLength(Utlity.LUCKY_NUM_START, aib.getShares());
					ActivityInfoBuyfreeSharesnum aibs = new ActivityInfoBuyfreeSharesnum();
					SharenumsVO svo = new SharenumsVO();
					svo.setCurrentNums(nums);
					svo.setUsedNums(new ArrayList<Integer>());
					aibs.setActivityInfoBuyfree(aib.getUuid());
					aibs.setSharenums(JSONUtils.obj2json(svo));
					this.activityInfoBuyfreeSharesnumDao.insert(aibs);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Caching(evict={@CacheEvict(value = "activityInfo", key = "'activityInfo:' + #activityInfo.name")})
	@Transactional
	public void editCheckin(ActivityInfo activityInfo, Map<String, Object> editMap) {
		this.activityInfoMapper.updateByPrimaryKey(activityInfo);
		if(editMap.containsKey("add")) {
			List<ActivityInfoCheckinPrize> addList = editMap.get("add") == null ? null : (List<ActivityInfoCheckinPrize>)editMap.get("add");
			if(addList != null && addList.size() > 0) {
				for(ActivityInfoCheckinPrize aicp : addList) {
					this.activityInfoCheckinPrizeDao.insert(aicp);
				}
			}
		}
		
		if(editMap.containsKey("edit")) {
			List<ActivityInfoCheckinPrize> editList = editMap.get("edit") == null ? null : (List<ActivityInfoCheckinPrize>)editMap.get("edit");
			if(editList != null && editList.size() > 0) {
				for(ActivityInfoCheckinPrize aicp : editList) {
					this.activityInfoCheckinPrizeDao.update(aicp);
				}
			}
		} 

		if(editMap.containsKey("delete")) {
			List<ActivityInfoCheckinPrize> deleteList = editMap.get("delete") == null ? null : (List<ActivityInfoCheckinPrize>)editMap.get("delete");
			if(deleteList != null && deleteList.size() > 0) {
				for(ActivityInfoCheckinPrize aicp : deleteList) {
					this.activityInfoCheckinPrizeDao.update(aicp);
				}
			}
		}
	}
	

	@SuppressWarnings("unchecked")
	@Override
	@Caching(evict={@CacheEvict(value = "activityInfo", key = "'activityInfo:' + #activityInfo.name")})
	@Transactional
	public void editScorelottery(ActivityInfo activityInfo, Map<String, Object> editMap) {
		this.activityInfoMapper.updateByPrimaryKey(activityInfo);
		if(editMap.containsKey("add")) {
			List<ActivityInfoScorelotteryPrize> addList = editMap.get("add") == null ? null : (List<ActivityInfoScorelotteryPrize>)editMap.get("add");
			if(addList != null && addList.size() > 0) {
				for(ActivityInfoScorelotteryPrize aisp : addList) {
					this.activityInfoScorelotteryPrizeDao.insert(aisp);
				}
			}
		}
		
		if(editMap.containsKey("edit")) {
			List<ActivityInfoScorelotteryPrize> editList = editMap.get("edit") == null ? null : (List<ActivityInfoScorelotteryPrize>)editMap.get("edit");
			if(editList != null && editList.size() > 0) {
				for(ActivityInfoScorelotteryPrize aisp : editList) {
					this.activityInfoScorelotteryPrizeDao.update(aisp);
				}
			}
		} 

		if(editMap.containsKey("delete")) {
			List<ActivityInfoScorelotteryPrize> deleteList = editMap.get("delete") == null ? null : (List<ActivityInfoScorelotteryPrize>)editMap.get("delete");
			if(deleteList != null && deleteList.size() > 0) {
				for(ActivityInfoScorelotteryPrize aisp : deleteList) {
					this.activityInfoScorelotteryPrizeDao.update(aisp);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Caching(evict={@CacheEvict(value = "activityInfo", key = "'activityInfo:' + #activityInfo.name")})
	@Transactional
	public void editFirstcharge(ActivityInfo activityInfo, Map<String, Object> editMap) {
		this.activityInfoMapper.updateByPrimaryKey(activityInfo);
		if(editMap.containsKey("add")) {
			List<ActivityInfoFirstchargePrize> addList = editMap.get("add") == null ? null : (List<ActivityInfoFirstchargePrize>)editMap.get("add");
			if(addList != null && addList.size() > 0) {
				for(ActivityInfoFirstchargePrize aifp : addList) {
					this.activityInfoFirstchargePrizeDao.insert(aifp);
				}
			}
		}
		
		if(editMap.containsKey("edit")) {
			List<ActivityInfoFirstchargePrize> editList = editMap.get("edit") == null ? null : (List<ActivityInfoFirstchargePrize>)editMap.get("edit");
			if(editList != null && editList.size() > 0) {
				for(ActivityInfoFirstchargePrize aifp : editList) {
					this.activityInfoFirstchargePrizeDao.update(aifp);
				}
			}
		} 

		if(editMap.containsKey("delete")) {
			List<ActivityInfoFirstchargePrize> deleteList = editMap.get("delete") == null ? null : (List<ActivityInfoFirstchargePrize>)editMap.get("delete");
			if(deleteList != null && deleteList.size() > 0) {
				for(ActivityInfoFirstchargePrize aifp : deleteList) {
					this.activityInfoFirstchargePrizeDao.update(aifp);
				}
			}
		}		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Caching(evict={@CacheEvict(value = "activityInfo", key = "'activityInfo:' + #activityInfo.name")})
	@Transactional
	public void editRecharge(ActivityInfo activityInfo, Map<String, Object> editMap) {
		this.activityInfoMapper.updateByPrimaryKey(activityInfo);
		if(editMap.containsKey("add")) {
			List<ActivityInfoRechargePrize> addList = editMap.get("add") == null ? null : (List<ActivityInfoRechargePrize>)editMap.get("add");
			if(addList != null && addList.size() > 0) {
				for(ActivityInfoRechargePrize airp : addList) {
					this.activityInfoRechargePrizeDao.insert(airp);
				}
			}
		}
		
		if(editMap.containsKey("edit")) {
			List<ActivityInfoRechargePrize> editList = editMap.get("edit") == null ? null : (List<ActivityInfoRechargePrize>)editMap.get("edit");
			if(editList != null && editList.size() > 0) {
				for(ActivityInfoRechargePrize airp : editList) {
					this.activityInfoRechargePrizeDao.update(airp);
				}
			}
		} 

		if(editMap.containsKey("delete")) {
			List<ActivityInfoRechargePrize> deleteList = editMap.get("delete") == null ? null : (List<ActivityInfoRechargePrize>)editMap.get("delete");
			if(deleteList != null && deleteList.size() > 0) {
				for(ActivityInfoRechargePrize airp : deleteList) {
					this.activityInfoRechargePrizeDao.update(airp);
				}
			}
		}				
	}
}
