package cn.product.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.score.dao.GuessingMatchDao;
import cn.product.score.dao.GuessingMatchOddsDao;
import cn.product.score.dao.GuessingMatchTypeDao;
import cn.product.score.entity.GuessingMatch;
import cn.product.score.entity.GuessingMatchOdds;
import cn.product.score.entity.GuessingMatchType;
import cn.product.score.mapper.GuessingMatchTypeMapper;
import cn.product.score.vo.front.CategoryCountVO;

@Component
public class GuessingMatchTypeDaoImpl implements GuessingMatchTypeDao{
	
	@Autowired
	private GuessingMatchDao guessingMatchDao;
	
	@Autowired
	private GuessingMatchOddsDao guessingMatchOddsDao;
	
	@Autowired
	private GuessingMatchTypeMapper guessingMatchTypeMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return guessingMatchTypeMapper.getCountByParams(params);
	}
	
	@Override
	public List<GuessingMatchType> getListByParams(Map<String, Object> params) {
		return guessingMatchTypeMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="guessingMatchType",key="'guessingMatchType:' + #key")
	public GuessingMatchType get(String key) {
		return guessingMatchTypeMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(GuessingMatchType guessingMatchType) {
		return guessingMatchTypeMapper.insert(guessingMatchType);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "guessingMatchType", key = "'guessingMatchType:' + #key")})
	public int delete(String key) {
		return guessingMatchTypeMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "guessingMatchType", key = "'guessingMatchType:' + #guessingMatchType.uuid")})
	public int update(GuessingMatchType guessingMatchType) {
		return guessingMatchTypeMapper.updateByPrimaryKey(guessingMatchType);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "guessingMatchType", key = "'guessingMatchType:' + #guessingMatchType.uuid")})
	public void publishGuessingMatchType(GuessingMatchType guessingMatchType, GuessingMatch guessingMatch) {
		guessingMatchTypeMapper.updateByPrimaryKey(guessingMatchType);
		guessingMatchDao.update(guessingMatch);
		
	}

	@Override
	@Transactional
	public void addGuessingMatchType(GuessingMatchType guessingMatchType, List<GuessingMatchOdds> oddsList) {
		guessingMatchTypeMapper.insert(guessingMatchType);
		for(GuessingMatchOdds odds : oddsList){
			guessingMatchOddsDao.insert(odds);
		}
	}
	
	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "guessingMatchType", key = "'guessingMatchType:' + #guessingMatchType.uuid")})
	public void deleteGuessingMatchType(GuessingMatchType guessingMatchType) {
		guessingMatchTypeMapper.updateByPrimaryKey(guessingMatchType);
		guessingMatchOddsDao.deleteByGuessingMatchType(guessingMatchType.getUuid());
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "guessingMatchType", key = "'guessingMatchType:' + #guessingMatchType.uuid")})
	public void updateGuessingMatchType(GuessingMatchType guessingMatchType, List<GuessingMatchOdds> oddsList) {
		guessingMatchOddsDao.deleteByGuessingMatchType(guessingMatchType.getUuid());
		guessingMatchTypeMapper.updateByPrimaryKey(guessingMatchType);
		for(GuessingMatchOdds odds : oddsList){
			guessingMatchOddsDao.insert(odds);
		}
	}
	
	@Override
	public List<CategoryCountVO> getCategoryList(Map<String, Object> params) {
		return guessingMatchTypeMapper.getCategoryList(params);
	}
	
	@Override
	public List<GuessingMatchType> getWaitingMatchTypeList() {
		return this.guessingMatchTypeMapper.getWaitingMatchTypeList();
	}
}
