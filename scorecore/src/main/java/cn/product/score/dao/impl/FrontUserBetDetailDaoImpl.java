package cn.product.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.score.dao.FrontUserBetDetailDao;
import cn.product.score.entity.FrontUserBetDetail;
import cn.product.score.mapper.FrontUserBetDetailMapper;
import cn.product.score.vo.back.BetSumVO;

@Component
public class FrontUserBetDetailDaoImpl implements FrontUserBetDetailDao{
	
	@Autowired
	private FrontUserBetDetailMapper frontUserBetDetailMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserBetDetailMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserBetDetail> getListByParams(Map<String, Object> params) {
		return frontUserBetDetailMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserBetDetail",key="'frontUserBetDetail:' + #key")
	public FrontUserBetDetail get(String key) {
		return frontUserBetDetailMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserBetDetail frontUserBetDetail) {
		return frontUserBetDetailMapper.insert(frontUserBetDetail);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserBetDetail", key = "'frontUserBetDetail:' + #key")})
	public int delete(String key) {
		return frontUserBetDetailMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserBetDetail", key = "'frontUserBetDetail:' + #frontUserBetDetail.uuid")})
	public int update(FrontUserBetDetail frontUserBetDetail) {
		return frontUserBetDetailMapper.updateByPrimaryKey(frontUserBetDetail);
	}

	@Override
	public List<BetSumVO> getBetSumList(Map<String, Object> params) {
		return frontUserBetDetailMapper.getBetSumList(params);
	}
}
