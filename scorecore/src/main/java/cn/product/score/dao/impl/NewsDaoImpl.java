package cn.product.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.score.dao.NewsDao;
import cn.product.score.entity.News;
import cn.product.score.mapper.NewsMapper;
import cn.product.score.vo.back.StatusCountVO;

@Component
public class NewsDaoImpl implements NewsDao{
	
	@Autowired
	private NewsMapper newsMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return newsMapper.getCountByParams(params);
	}
	
	@Override
	public List<News> getListByParams(Map<String, Object> params) {
		return newsMapper.getListByParams(params);
	}
	
	@Override
	@Caching(evict={@CacheEvict(value = "news", allEntries = true)})
	public void updateStatus(Map<String, Object> params) {
		newsMapper.updateStatus(params);
	}
	
	@Override
	public List<StatusCountVO> getStatusList() {
		return newsMapper.getStatusList();
	}

	@Override
	@Cacheable(cacheNames="news",key="'news:' + #key")
	public News get(String key) {
		return newsMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(News news) {
		return newsMapper.insert(news);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "news", key = "'news:' + #key")})
	public int delete(String key) {
		return newsMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "news", key = "'news:' + #news.uuid")})
	public int update(News news) {
		return newsMapper.updateByPrimaryKey(news);
	}
}
