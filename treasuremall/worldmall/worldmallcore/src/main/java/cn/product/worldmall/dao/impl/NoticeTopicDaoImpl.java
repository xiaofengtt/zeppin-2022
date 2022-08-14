package cn.product.worldmall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.dao.NoticeTopicDao;
import cn.product.worldmall.entity.NoticeTopic;
import cn.product.worldmall.mapper.NoticeTopicMapper;

@Component
public class NoticeTopicDaoImpl implements NoticeTopicDao{
	
	@Autowired
	private NoticeTopicMapper noticeTopicMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return noticeTopicMapper.getCountByParams(params);
	}
	
	@Override
	public List<NoticeTopic> getListByParams(Map<String, Object> params) {
		return noticeTopicMapper.getListByParams(params);
	}
	
	@Override
	@Cacheable(cacheNames="noticeTopic",key="'noticeTopic:' + #key")
	public NoticeTopic get(String key) {
		return noticeTopicMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "noticeTopic", key = "'noticeTopic:' + #noticeTopic.uuid", beforeInvocation = true)
	,@CacheEvict(value = "noticeTopicArn", key = "'noticeTopicArn:' + #noticeTopic.topicArn", beforeInvocation = true)})
	public int insert(NoticeTopic noticeTopic) {
		return noticeTopicMapper.insert(noticeTopic);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "noticeTopic", key = "'noticeTopic:' + #key")})
	public int delete(String key) {
		return noticeTopicMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "noticeTopic", key = "'noticeTopic:' + #noticeTopic.uuid", beforeInvocation = true)
	,@CacheEvict(value = "noticeTopicArn", key = "'noticeTopicArn:' + #noticeTopic.topicArn", beforeInvocation = true)})
	public int update(NoticeTopic noticeTopic) {
		return noticeTopicMapper.updateByPrimaryKey(noticeTopic);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "noticeTopic", allEntries=true), @CacheEvict(value = "noticeTopicArn", allEntries=true)})
	public void updateTask(List<NoticeTopic> update) {
		if(update != null && update.size() > 0) {
			for(NoticeTopic np : update) {
				this.noticeTopicMapper.updateByPrimaryKey(np);
			}
		}
	}

	@Override
	@Cacheable(cacheNames="noticeTopicArn",key="'noticeTopicArn:' + #topicArn")
	public NoticeTopic getByArn(String topicArn) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("topicArn", topicArn);
		List<NoticeTopic> list = this.noticeTopicMapper.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
