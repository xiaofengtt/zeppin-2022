package cn.product.score.dao.impl;

import java.util.HashMap;
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
import cn.product.score.mapper.GuessingMatchMapper;
import cn.product.score.vo.back.StatusCountVO;

@Component
public class GuessingMatchDaoImpl implements GuessingMatchDao{
	
	@Autowired
	private GuessingMatchMapper guessingMatchMapper;
	
	@Autowired
	private GuessingMatchTypeDao guessingMatchTypeDao;
	
	@Autowired
	private GuessingMatchOddsDao guessingMatchOddsDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return guessingMatchMapper.getCountByParams(params);
	}
	
	@Override
	public List<GuessingMatch> getListByParams(Map<String, Object> params) {
		return guessingMatchMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="guessingMatch",key="'guessingMatch:' + #key")
	public GuessingMatch get(String key) {
		return guessingMatchMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(GuessingMatch guessingMatch) {
		return guessingMatchMapper.insert(guessingMatch);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "guessingMatch", key = "'guessingMatch:' + #key")})
	public int delete(String key) {
		return guessingMatchMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "guessingMatch", key = "'guessingMatch:' + #guessingMatch.uuid")})
	public int update(GuessingMatch guessingMatch) {
		return guessingMatchMapper.updateByPrimaryKey(guessingMatch);
	}

	@Override
	public List<StatusCountVO> getStatusList() {
		return guessingMatchMapper.getStatusList();
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "guessingMatch", key = "'guessingMatch:' + #guessingMatch.uuid")})
	public void deleteGuessingMatch(GuessingMatch guessingMatch) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("guessingMatch", guessingMatch.getUuid());
		List<GuessingMatchType> gmtList = this.guessingMatchTypeDao.getListByParams(params);
		
		guessingMatchMapper.updateByPrimaryKey(guessingMatch);
		for(GuessingMatchType gmt : gmtList){
			guessingMatchTypeDao.deleteGuessingMatchType(gmt);
		}
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "guessingMatch", key = "'guessingMatch:' + #guessingMatch.uuid")})
	public void finishGuessingMatch(GuessingMatch guessingMatch, List<GuessingMatchType> gmtList, List<GuessingMatchOdds> gmoList) {
		this.guessingMatchMapper.updateByPrimaryKey(guessingMatch);
		for(GuessingMatchType gmt : gmtList){
			this.guessingMatchTypeDao.update(gmt);
		}
		for(GuessingMatchOdds gmo : gmoList){
			this.guessingMatchOddsDao.update(gmo);
		}
	}

	@Override
	public List<GuessingMatch> getWaitingMatchList() {
		return this.guessingMatchMapper.getWaitingMatchList();
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "guessingMatch", key = "'guessingMatch:' + #guessingMatch.uuid")})
	public void updateGuessingMatch(GuessingMatch guessingMatch, List<GuessingMatchType> gmtList) {
		this.guessingMatchMapper.updateByPrimaryKey(guessingMatch);
		for(GuessingMatchType gmt : gmtList){
			this.guessingMatchTypeDao.update(gmt);
		}
	}
}
