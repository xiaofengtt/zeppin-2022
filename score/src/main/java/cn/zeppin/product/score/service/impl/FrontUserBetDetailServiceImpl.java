package cn.zeppin.product.score.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.zeppin.product.score.entity.FrontUserBetDetail;
import cn.zeppin.product.score.mapper.FrontUserBetDetailMapper;
import cn.zeppin.product.score.service.FrontUserBetDetailService;
import cn.zeppin.product.score.vo.back.BetSumVO;

@Service("frontUserBetDetailService")
public class FrontUserBetDetailServiceImpl implements FrontUserBetDetailService{
	
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
