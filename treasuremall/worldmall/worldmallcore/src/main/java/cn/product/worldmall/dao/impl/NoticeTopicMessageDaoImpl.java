package cn.product.worldmall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.NoticeTopicMessageDao;
import cn.product.worldmall.entity.NoticeTopicMessage;
import cn.product.worldmall.mapper.NoticeTopicMessageMapper;

@Component
public class NoticeTopicMessageDaoImpl implements NoticeTopicMessageDao{
	
	@Autowired
	private NoticeTopicMessageMapper noticeTopicMessageMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return noticeTopicMessageMapper.getCountByParams(params);
	}
	
	@Override
	public List<NoticeTopicMessage> getListByParams(Map<String, Object> params) {
		return noticeTopicMessageMapper.getListByParams(params);
	}
	
	@Override
	@Cacheable(cacheNames="noticeTopicMessage",key="'noticeTopicMessage:' + #key")
	public NoticeTopicMessage get(String key) {
		return noticeTopicMessageMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "noticeTopicMessage", key = "'noticeTopicMessage:' + #noticeTopicMessage.uuid", beforeInvocation = true)})
	public int insert(NoticeTopicMessage noticeTopicMessage) {
		return noticeTopicMessageMapper.insert(noticeTopicMessage);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "noticeTopicMessage", key = "'noticeTopicMessage:' + #key")})
	public int delete(String key) {
		return noticeTopicMessageMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "noticeTopicMessage", key = "'noticeTopicMessage:' + #noticeTopicMessage.uuid", beforeInvocation = true)})
	public int update(NoticeTopicMessage noticeTopicMessage) {
		return noticeTopicMessageMapper.updateByPrimaryKey(noticeTopicMessage);
	}
}
