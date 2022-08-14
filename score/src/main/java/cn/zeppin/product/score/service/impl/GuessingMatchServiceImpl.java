package cn.zeppin.product.score.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zeppin.product.score.entity.GuessingMatch;
import cn.zeppin.product.score.entity.GuessingMatchOdds;
import cn.zeppin.product.score.entity.GuessingMatchType;
import cn.zeppin.product.score.mapper.GuessingMatchMapper;
import cn.zeppin.product.score.service.GuessingMatchOddsService;
import cn.zeppin.product.score.service.GuessingMatchService;
import cn.zeppin.product.score.service.GuessingMatchTypeService;
import cn.zeppin.product.score.vo.back.StatusCountVO;

@Service("guessingMatchService")
public class GuessingMatchServiceImpl implements GuessingMatchService{
	
	@Autowired
	private GuessingMatchMapper guessingMatchMapper;
	
	@Autowired
	private GuessingMatchTypeService guessingMatchTypeService;
	
	@Autowired
	private GuessingMatchOddsService guessingMatchOddsService;
	
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
		List<GuessingMatchType> gmtList = this.guessingMatchTypeService.getListByParams(params);
		
		guessingMatchMapper.updateByPrimaryKey(guessingMatch);
		for(GuessingMatchType gmt : gmtList){
			guessingMatchTypeService.deleteGuessingMatchType(gmt);
		}
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "guessingMatch", key = "'guessingMatch:' + #guessingMatch.uuid")})
	public void finishGuessingMatch(GuessingMatch guessingMatch, List<GuessingMatchType> gmtList, List<GuessingMatchOdds> gmoList) {
		this.guessingMatchMapper.updateByPrimaryKey(guessingMatch);
		for(GuessingMatchType gmt : gmtList){
			this.guessingMatchTypeService.update(gmt);
		}
		for(GuessingMatchOdds gmo : gmoList){
			this.guessingMatchOddsService.update(gmo);
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
			this.guessingMatchTypeService.update(gmt);
		}
	}
}
