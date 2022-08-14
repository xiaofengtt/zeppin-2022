package cn.zeppin.product.score.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zeppin.product.score.entity.GuessingMatchOdds;
import cn.zeppin.product.score.mapper.GuessingMatchOddsMapper;
import cn.zeppin.product.score.service.GuessingMatchOddsService;
import cn.zeppin.product.score.vo.back.GuessingMatchOddsChangeVO;

@Service("guessingMatchOddsService")
public class GuessingMatchOddsServiceImpl implements GuessingMatchOddsService{
	
	@Autowired
	private GuessingMatchOddsMapper guessingMatchOddsMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return guessingMatchOddsMapper.getCountByParams(params);
	}
	
	@Override
	public List<GuessingMatchOdds> getListByParams(Map<String, Object> params) {
		return guessingMatchOddsMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="guessingMatchOdds",key="'guessingMatchOdds:' + #key")
	public GuessingMatchOdds get(String key) {
		return guessingMatchOddsMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(GuessingMatchOdds guessingMatchOdds) {
		return guessingMatchOddsMapper.insert(guessingMatchOdds);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "guessingMatchOdds", key = "'guessingMatchOdds:' + #key")})
	public int delete(String key) {
		return guessingMatchOddsMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "guessingMatchOdds", key = "'guessingMatchOdds:' + #guessingMatchOdds.uuid")})
	public int update(GuessingMatchOdds guessingMatchOdds) {
		return guessingMatchOddsMapper.updateByPrimaryKey(guessingMatchOdds);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "guessingMatchOdds", allEntries = true)})
	public void deleteByGuessingMatchType(String guessingMatchType) {
		guessingMatchOddsMapper.deleteByGuessingMatchType(guessingMatchType);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "guessingMatchOdds", allEntries = true)})
	public void updateOdds(List<GuessingMatchOddsChangeVO> changevoList) {
		for(GuessingMatchOddsChangeVO vo : changevoList){
			guessingMatchOddsMapper.updateByPrimaryKey(vo.getOldData());
			guessingMatchOddsMapper.insert(vo.getNewData());
		}
	}
	
	
}
