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

import cn.product.worldmall.dao.GoodsIssueSharesnumDao;
import cn.product.worldmall.dao.LuckygameGoodsDao;
import cn.product.worldmall.dao.LuckygameGoodsIssueDao;
import cn.product.worldmall.entity.GoodsIssueSharesnum;
import cn.product.worldmall.entity.LuckygameGoods;
import cn.product.worldmall.entity.LuckygameGoodsIssue;
import cn.product.worldmall.entity.LuckygameGoodsIssue.LuckygameGoodsIssueStatus;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.mapper.LuckygameGoodsMapper;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.SharenumsPKVO;
import cn.product.worldmall.vo.back.SharenumsVO;

@Component
public class LuckygameGoodsDaoImpl implements LuckygameGoodsDao {
	
	@Autowired
    private LuckygameGoodsMapper luckygameGoodsMapper;
	
	@Autowired
    private LuckygameGoodsIssueDao luckygameGoodsIssueDao;
	
	@Autowired
    private GoodsIssueSharesnumDao goodsIssueSharesnumDao;

	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return luckygameGoodsMapper.getCountByParams(params);
	}
	
    @Override
    public List<LuckygameGoods> getListByParams(Map<String, Object> params){
        return luckygameGoodsMapper.getListByParams(params);
    }

	@Override
	@Cacheable(cacheNames="luckygameGoods",key="'luckygameGoods:' + #key")
	public LuckygameGoods get(String key) {
		return luckygameGoodsMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(LuckygameGoods luckygameGoods) {
		return luckygameGoodsMapper.insert(luckygameGoods);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "luckygameGoods", key = "'luckygameGoods:' + #key")})
	public int delete(String key) {
		return luckygameGoodsMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "luckygameGoods", key = "'luckygameGoods:' + #luckygameGoods.uuid")})
	public int update(LuckygameGoods luckygameGoods) {
		return luckygameGoodsMapper.updateByPrimaryKey(luckygameGoods);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "luckygameGoods", key = "'luckygameGoods:' + #luckygameGoods.uuid")})
	public void updateStatus(LuckygameGoods luckygameGoods, LuckygameGoodsIssue lgi) {
		this.luckygameGoodsMapper.updateByPrimaryKey(luckygameGoods);
		this.luckygameGoodsIssueDao.insert(lgi);
		//生成抽奖号码
		/*
		 * 生成指定个数的抽奖号码
		 * 抽奖号码长度8位
		 */
		List<Integer> nums = Utlity.createNumbersByLength(Utlity.LUCKY_NUM_START, lgi.getShares());
		GoodsIssueSharesnum gis = new GoodsIssueSharesnum();
		gis.setGoodsIssue(lgi.getUuid());
		if(Constants.GAME_TYPE_TRADITION.equals(luckygameGoods.getGameType())) {
			SharenumsVO svo = new SharenumsVO();
			svo.setCurrentNums(nums);
			svo.setUsedNums(new ArrayList<Integer>());
			gis.setSharenums(JSONUtils.obj2json(svo));
		} else if(Constants.GAME_TYPE_GROUP2.equals(luckygameGoods.getGameType())) {//两组战队玩法
			SharenumsPKVO svo = new SharenumsPKVO();
			List<Integer> raidernums = new ArrayList<Integer>();
			for(int i = 0; i < luckygameGoods.getShares()/2; i++) {
				int ramNum = Utlity.getRandomNum(0, nums.size());
				raidernums.add(nums.get(ramNum));
				nums.remove(ramNum);
			}
			SharenumsVO lucky = new SharenumsVO();
			lucky.setCurrentNums(nums);
			lucky.setUsedNums(new ArrayList<Integer>());
			svo.setLucky(lucky);
			SharenumsVO raider = new SharenumsVO();
			raider.setCurrentNums(raidernums);
			raider.setUsedNums(new ArrayList<Integer>());
			svo.setRaider(raider);
			gis.setSharenums(JSONUtils.obj2json(svo));
		}
		this.goodsIssueSharesnumDao.insert(gis);
	}
	
	@Override
	@Transactional
	public void updateSort(LuckygameGoods luckygameGoods, Map<String, Object> params) {
		String sorttype = params.get("sorttype").toString();
//		this.luckygameGoodsMapper.updateByPrimaryKey(luckygameGoods);
		List<LuckygameGoods> listGoods = this.luckygameGoodsMapper.getListByParams(new HashMap<String, Object>());
		if(listGoods != null && listGoods.size() > 0) {
			if("up".equals(sorttype)) {//向上
				if(luckygameGoods.getSort() < listGoods.size()) {
					for(LuckygameGoods lg : listGoods) {
						if(lg.getSort().intValue() == luckygameGoods.getSort().intValue() + 1) {
							lg.setSort(lg.getSort().intValue() - 1);
							this.luckygameGoodsIssueDao.updateGoodsIssue(lg);
							params.put("luckygameGoods", lg.getUuid());
							params.put("sort", lg.getSort());
							this.luckygameGoodsIssueDao.updateSorts(params);
						} else if (lg.getUuid().equals(luckygameGoods.getUuid())) {
							lg.setSort(lg.getSort().intValue() + 1);
							this.luckygameGoodsIssueDao.updateGoodsIssue(lg);
							params.put("luckygameGoods", lg.getUuid());
							params.put("sort", lg.getSort());
							this.luckygameGoodsIssueDao.updateSorts(params);
						}
					}
				}
			} else if ("down".equals(sorttype)) {
				if(luckygameGoods.getSort() > 0) {
					for(LuckygameGoods lg : listGoods) {
						if(lg.getSort().intValue() == luckygameGoods.getSort().intValue() - 1) {
							lg.setSort(lg.getSort().intValue() + 1);
							this.luckygameGoodsIssueDao.updateGoodsIssue(lg);
							params.put("luckygameGoods", lg.getUuid());
							params.put("sort", lg.getSort());
							this.luckygameGoodsIssueDao.updateSorts(params);
						} else if (lg.getUuid().equals(luckygameGoods.getUuid())) {
							lg.setSort(lg.getSort().intValue() - 1);
							this.luckygameGoodsIssueDao.updateGoodsIssue(lg);
							params.put("luckygameGoods", lg.getUuid());
							params.put("sort", lg.getSort());
							this.luckygameGoodsIssueDao.updateSorts(params);
						}
					}
				}
			} else {//置顶 
				if(luckygameGoods.getSort().intValue() < listGoods.size()) {
					for(LuckygameGoods lg : listGoods) {
						if(lg.getUuid().equals(luckygameGoods.getUuid())) {
							lg.setSort(listGoods.size());
							this.luckygameGoodsIssueDao.updateGoodsIssue(lg);
							params.put("luckygameGoods", lg.getUuid());
							params.put("sort", lg.getSort());
							this.luckygameGoodsIssueDao.updateSorts(params);
						} else {
							if(lg.getSort().intValue() > luckygameGoods.getSort().intValue()) {
								lg.setSort(lg.getSort().intValue() - 1);
								this.luckygameGoodsIssueDao.updateGoodsIssue(lg);
								params.put("luckygameGoods", lg.getUuid());
								params.put("sort", lg.getSort());
								this.luckygameGoodsIssueDao.updateSorts(params);
							}
						}
					}
				}
//				this.luckygameGoodsIssueDao.updateGoodsIssue(luckygameGoods);
//				params.put("luckygameGoods", luckygameGoods.getUuid());
//				params.put("sort", luckygameGoods.getSort());
//				this.luckygameGoodsIssueDao.updateSorts(params);
			}
		}
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "luckygameGoods", key = "'luckygameGoods:' + #luckygameGoods.uuid")})
	public void updateStatus(LuckygameGoods luckygameGoods, List<LuckygameGoodsIssue> list) {
		this.luckygameGoodsMapper.updateByPrimaryKey(luckygameGoods);
		if(list != null) {
			for(LuckygameGoodsIssue lgi : list) {
				lgi.setStatus(LuckygameGoodsIssueStatus.DELETE);
				this.luckygameGoodsIssueDao.update(lgi);
			}
		}
	}

	@Override
//	@Caching(evict={@CacheEvict(value = "luckygameGoodsIssue", key = "'luckygameGoodsIssue:' + #luckygameGoodsIssue.uuid")})
	public void updateIssue(LuckygameGoodsIssue luckygameGoodsIssue) {
		this.luckygameGoodsIssueDao.update(luckygameGoodsIssue);	
	}
	
}
