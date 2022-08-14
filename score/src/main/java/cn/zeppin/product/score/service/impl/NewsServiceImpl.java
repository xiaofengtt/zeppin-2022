package cn.zeppin.product.score.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import cn.zeppin.product.score.entity.News;
import cn.zeppin.product.score.mapper.NewsMapper;
import cn.zeppin.product.score.service.NewsService;
import cn.zeppin.product.score.vo.back.StatusCountVO;

@Service("newsService")
public class NewsServiceImpl implements NewsService{
	
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
