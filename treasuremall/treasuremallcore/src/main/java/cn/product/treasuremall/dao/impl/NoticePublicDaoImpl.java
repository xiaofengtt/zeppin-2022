package cn.product.treasuremall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.treasuremall.dao.NoticePublicDao;
import cn.product.treasuremall.entity.NoticePublic;
import cn.product.treasuremall.mapper.NoticePublicMapper;

@Component
public class NoticePublicDaoImpl implements NoticePublicDao{
	
	@Autowired
	private NoticePublicMapper noticePublicMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return noticePublicMapper.getCountByParams(params);
	}
	
	@Override
	public List<NoticePublic> getListByParams(Map<String, Object> params) {
		return noticePublicMapper.getListByParams(params);
	}
	
	@Override
	@Cacheable(cacheNames="noticePublic",key="'noticePublic:' + #key")
	public NoticePublic get(String key) {
		return noticePublicMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(NoticePublic noticePublic) {
		return noticePublicMapper.insert(noticePublic);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "noticePublic", key = "'noticePublic:' + #key")})
	public int delete(String key) {
		return noticePublicMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "noticePublic", key = "'noticePublic:' + #noticePublic.uuid")})
	public int update(NoticePublic noticePublic) {
		return noticePublicMapper.updateByPrimaryKey(noticePublic);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "noticePublic", allEntries=true)})
	public void updateTask(List<NoticePublic> update) {
		if(update != null && update.size() > 0) {
			for(NoticePublic np : update) {
				this.noticePublicMapper.updateByPrimaryKey(np);
			}
		}
	}
}
