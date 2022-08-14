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

import cn.zeppin.product.score.entity.News;
import cn.zeppin.product.score.entity.News.NewsStatus;
import cn.zeppin.product.score.entity.NewsPublish;
import cn.zeppin.product.score.entity.NewsPublish.NewsPublishStatus;
import cn.zeppin.product.score.mapper.NewsPublishMapper;
import cn.zeppin.product.score.service.NewsPublishService;
import cn.zeppin.product.score.service.NewsService;
import cn.zeppin.product.score.vo.back.StatusCountVO;

@Service("newsPublishService")
public class NewsPublishServiceImpl implements NewsPublishService{
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private NewsPublishMapper newsPublishMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return newsPublishMapper.getCountByParams(params);
	}
	
	@Override
	public List<NewsPublish> getListByParams(Map<String, Object> params) {
		return newsPublishMapper.getListByParams(params);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "news", key = "'newsPublish:' + #newsPublish.news")})
	public void insertNewsPublish(NewsPublish newsPublish) {
		News news = this.newsService.get(newsPublish.getNews());
		if(news != null){
			news.setStatus(NewsStatus.PUBLISH);
			newsService.update(news);
		}
		newsPublishMapper.insert(newsPublish);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "news", allEntries = true),@CacheEvict(value = "newsPublish", allEntries = true)})
	public void deleteNewsPublish(String[] uuids, String[] news) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("uuid", uuids);
		params.put("status", NewsPublishStatus.DELETE);
		newsPublishMapper.updateStatus(params);
		
		params.clear();
		params.put("uuid", news);
		params.put("status", NewsStatus.NORMAL);
		newsService.updateStatus(params);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "newsPublish", allEntries = true)})
	public void updateStatus(Map<String, Object> params) {
		newsPublishMapper.updateStatus(params);
	}
	
	@Override
	public List<StatusCountVO> getStatusList(Map<String, Object> params) {
		return newsPublishMapper.getStatusList(params);
	}
	
	@Override
	@Cacheable(cacheNames="newsPublish",key="'newsPublish:' + #key")
	public NewsPublish get(String key) {
		return newsPublishMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(NewsPublish newsPublish) {
		return newsPublishMapper.insert(newsPublish);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "newsPublish", key = "'newsPublish:' + #key")})
	public int delete(String key) {
		return newsPublishMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "newsPublish", key = "'newsPublish:' + #newsPublish.uuid")})
	public int update(NewsPublish newsPublish) {
		return newsPublishMapper.updateByPrimaryKey(newsPublish);
	}
}
