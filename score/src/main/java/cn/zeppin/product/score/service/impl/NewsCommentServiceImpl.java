package cn.zeppin.product.score.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.zeppin.product.score.entity.NewsComment;
import cn.zeppin.product.score.mapper.NewsCommentMapper;
import cn.zeppin.product.score.service.NewsCommentService;

@Service("newsCommentService")
public class NewsCommentServiceImpl implements NewsCommentService{
	
	@Autowired
	private NewsCommentMapper newsCommentMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return newsCommentMapper.getCountByParams(params);
	}
	
	@Override
	public List<NewsComment> getListByParams(Map<String, Object> params) {
		return newsCommentMapper.getListByParams(params);
	}
	
	@Override
	@Cacheable(cacheNames="newsComment",key="'newsComment:' + #key")
	public NewsComment get(String key) {
		return newsCommentMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(NewsComment newsComment) {
		return newsCommentMapper.insert(newsComment);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "newsComment", key = "'newsComment:' + #key")})
	public int delete(String key) {
		return newsCommentMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "newsComment", key = "'newsComment:' + #newsComment.uuid")})
	public int update(NewsComment newsComment) {
		return newsCommentMapper.updateByPrimaryKey(newsComment);
	}
}
