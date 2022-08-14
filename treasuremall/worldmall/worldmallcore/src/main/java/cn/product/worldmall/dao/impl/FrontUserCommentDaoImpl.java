package cn.product.worldmall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.FrontUserCommentDao;
import cn.product.worldmall.entity.FrontUserComment;
import cn.product.worldmall.mapper.FrontUserCommentMapper;

@Component
public class FrontUserCommentDaoImpl implements FrontUserCommentDao{
	
	@Autowired
	private FrontUserCommentMapper frontUserCommentMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserCommentMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserComment> getListByParams(Map<String, Object> params) {
		return frontUserCommentMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserComment",key="'frontUserComment:' + #key")
	public FrontUserComment get(String key) {
		return frontUserCommentMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserCommentFinish", key = "'frontUserCommentFinish:' + #frontUserComment.orderId")})
	public int insert(FrontUserComment frontUserComment) {
		return frontUserCommentMapper.insert(frontUserComment);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserComment", key = "'frontUserComment:' + #key"),@CacheEvict(value = "frontUserCommentFinish", allEntries = true)})
	public int delete(String key) {
		return frontUserCommentMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserComment", key = "'frontUserComment:' + #frontUserComment.uuid")
	, @CacheEvict(value = "frontUserCommentFinish", key = "'frontUserCommentFinish:' + #frontUserComment.orderId")})
	public int update(FrontUserComment frontUserComment) {
		return frontUserCommentMapper.updateByPrimaryKey(frontUserComment);
	}

	@Override
	@Cacheable(cacheNames="frontUserCommentFinish",key="'frontUserCommentFinish:' + #frontUserPaymentOrder")
	public Boolean isComment(String frontUserPaymentOrder) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("orderId", frontUserPaymentOrder);
		Integer count = this.frontUserCommentMapper.getCountByParams(searchMap);
		if(count != null && count > 0) {
			return true;
		}
		return false;
	}

}
