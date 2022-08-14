package cn.zeppin.product.score.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.zeppin.product.score.entity.CapitalAccountHistory;
import cn.zeppin.product.score.mapper.CapitalAccountHistoryMapper;
import cn.zeppin.product.score.service.CapitalAccountHistoryService;
import cn.zeppin.product.score.vo.back.StatusCountVO;

@Service("capitalAccountHistoryService")
public class CapitalAccountHistoryServiceImpl implements CapitalAccountHistoryService{
	
	@Autowired
	private CapitalAccountHistoryMapper capitalAccountHistoryMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return capitalAccountHistoryMapper.getCountByParams(params);
	}
	
	@Override
	public List<CapitalAccountHistory> getListByParams(Map<String, Object> params) {
		return capitalAccountHistoryMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="capitalAccountHistory",key="'capitalAccountHistory:' + #key")
	public CapitalAccountHistory get(String key) {
		return capitalAccountHistoryMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(CapitalAccountHistory capitalAccountHistory) {
		return capitalAccountHistoryMapper.insert(capitalAccountHistory);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "capitalAccountHistory", key = "'capitalAccountHistory:' + #key")})
	public int delete(String key) {
		return capitalAccountHistoryMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "capitalAccountHistory", key = "'capitalAccountHistory:' + #capitalAccountHistory.uuid")})
	public int update(CapitalAccountHistory capitalAccountHistory) {
		return capitalAccountHistoryMapper.updateByPrimaryKey(capitalAccountHistory);
	}

	@Override
	public List<StatusCountVO> getTypeList(Map<String, Object> params) {
		return capitalAccountHistoryMapper.getTypeList(params);
	}
	
	
}
