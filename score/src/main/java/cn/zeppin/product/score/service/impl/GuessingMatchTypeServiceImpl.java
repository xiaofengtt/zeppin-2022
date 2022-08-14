package cn.zeppin.product.score.service.impl;

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
import cn.zeppin.product.score.mapper.GuessingMatchTypeMapper;
import cn.zeppin.product.score.service.GuessingMatchOddsService;
import cn.zeppin.product.score.service.GuessingMatchService;
import cn.zeppin.product.score.service.GuessingMatchTypeService;
import cn.zeppin.product.score.vo.front.CategoryCountVO;

@Service("guessingMatchTypeService")
public class GuessingMatchTypeServiceImpl implements GuessingMatchTypeService{
	
	@Autowired
	private GuessingMatchService guessingMatchService;
	
	@Autowired
	private GuessingMatchOddsService guessingMatchOddsService;
	
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
		guessingMatchService.update(guessingMatch);
		
	}

	@Override
	@Transactional
	public void addGuessingMatchType(GuessingMatchType guessingMatchType, List<GuessingMatchOdds> oddsList) {
		guessingMatchTypeMapper.insert(guessingMatchType);
		for(GuessingMatchOdds odds : oddsList){
			guessingMatchOddsService.insert(odds);
		}
	}
	
	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "guessingMatchType", key = "'guessingMatchType:' + #guessingMatchType.uuid")})
	public void deleteGuessingMatchType(GuessingMatchType guessingMatchType) {
		guessingMatchTypeMapper.updateByPrimaryKey(guessingMatchType);
		guessingMatchOddsService.deleteByGuessingMatchType(guessingMatchType.getUuid());
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "guessingMatchType", key = "'guessingMatchType:' + #guessingMatchType.uuid")})
	public void updateGuessingMatchType(GuessingMatchType guessingMatchType, List<GuessingMatchOdds> oddsList) {
		guessingMatchOddsService.deleteByGuessingMatchType(guessingMatchType.getUuid());
		guessingMatchTypeMapper.updateByPrimaryKey(guessingMatchType);
		for(GuessingMatchOdds odds : oddsList){
			guessingMatchOddsService.insert(odds);
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
