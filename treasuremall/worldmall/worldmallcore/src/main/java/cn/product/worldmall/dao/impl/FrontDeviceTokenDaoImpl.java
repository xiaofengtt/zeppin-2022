package cn.product.worldmall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.dao.FrontDeviceTokenDao;
import cn.product.worldmall.dao.NoticeTopicDao;
import cn.product.worldmall.entity.FrontDeviceToken;
import cn.product.worldmall.entity.NoticeTopic;
import cn.product.worldmall.mapper.FrontDeviceTokenMapper;
import cn.product.worldmall.rabbit.send.RabbitSenderService;

/**
 */
@Component
public class FrontDeviceTokenDaoImpl implements FrontDeviceTokenDao{
	
	private final static Logger log = LoggerFactory.getLogger(FrontDeviceTokenDaoImpl.class);
	
	@Autowired
	private FrontDeviceTokenMapper frontDeviceTokenMapper;
	
	@Autowired
	private NoticeTopicDao noticeTopicDao;
	
	@Autowired
	private RabbitSenderService rabbitSenderService;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontDeviceTokenMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontDeviceToken> getListByParams(Map<String, Object> params) {
		return frontDeviceTokenMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontDeviceToken",key="'frontDeviceToken:' + #key")
	public FrontDeviceToken get(String key) {
		return frontDeviceTokenMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontDeviceToken", key = "'frontDeviceToken:' + #frontDeviceToken.uuid", beforeInvocation = true)
	,@CacheEvict(value = "frontDeviceTokenUnique", key = "'frontDeviceTokenUnique:' + #frontDeviceToken.uniqueToken", beforeInvocation = true)
	,@CacheEvict(value = "frontDeviceTokenDevice", key = "'frontDeviceTokenDevice:' + #frontDeviceToken.deviceToken", beforeInvocation = true)
	,@CacheEvict(value = "frontDeviceTokenUser", key = "'frontDeviceTokenUser:' + #frontDeviceToken.frontUser", beforeInvocation = true)})
	public int insert(FrontDeviceToken frontDeviceToken) {
		return frontDeviceTokenMapper.insert(frontDeviceToken);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontDeviceToken", key = "'frontDeviceToken:' + #key")
	,@CacheEvict(value = "frontDeviceTokenUnique", allEntries = true)})
	public int delete(String key) {
		return frontDeviceTokenMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontDeviceToken", key = "'frontDeviceToken:' + #frontDeviceToken.uuid", beforeInvocation = true)
	,@CacheEvict(value = "frontDeviceTokenUnique", key = "'frontDeviceTokenUnique:' + #frontDeviceToken.uniqueToken", beforeInvocation = true)
	,@CacheEvict(value = "frontDeviceTokenDevice", key = "'frontDeviceTokenDevice:' + #frontDeviceToken.deviceToken", beforeInvocation = true)
	,@CacheEvict(value = "frontDeviceTokenUser", key = "'frontDeviceTokenUser:' + #frontDeviceToken.frontUser", beforeInvocation = true)})
	public int update(FrontDeviceToken frontDeviceToken) {
		return frontDeviceTokenMapper.updateByPrimaryKey(frontDeviceToken);
	}

	@Override
	public Integer getLeftCountByParams(Map<String, Object> params) {
		return frontDeviceTokenMapper.getLeftCountByParams(params);
	}

	@Override
	public List<FrontDeviceToken> getLeftListByParams(Map<String, Object> params) {
		return frontDeviceTokenMapper.getLeftListByParams(params);
	}

	@Override
	@Cacheable(cacheNames="frontDeviceTokenUnique",key="'frontDeviceTokenUnique:' + #uniqueToken")
	public FrontDeviceToken getByUniqueToken(String uniqueToken) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("uniqueToken", uniqueToken);
		List<FrontDeviceToken> list = this.frontDeviceTokenMapper.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	@Cacheable(cacheNames="frontDeviceTokenDevice",key="'frontDeviceTokenDevice:' + #deviceToken")
	public FrontDeviceToken getByDeviceToken(String deviceToken) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("deviceToken", deviceToken);
		List<FrontDeviceToken> list = this.frontDeviceTokenMapper.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	@Cacheable(cacheNames="frontDeviceTokenUser",key="'frontDeviceTokenUser:' + #frontUser")
	public FrontDeviceToken getByFrontUser(String frontUser) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		List<FrontDeviceToken> list = this.frontDeviceTokenMapper.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<FrontDeviceToken> getFrontUserGroupDfList() {
		return frontDeviceTokenMapper.getFrontUserGroupDfList();
	}

	@Override
	@Transactional
	public void updateTopicTask(List<NoticeTopic> insert, List<NoticeTopic> update) {
		if(insert != null && insert.size() > 0) {
			for(NoticeTopic nt : insert) {
				this.noticeTopicDao.insert(nt);
			}
		}
		if(update != null && update.size() > 0) {
			for(NoticeTopic nt : update) {
				this.noticeTopicDao.update(nt);
			}
		}
	}

	@Override
	public void bindTopic(List<FrontDeviceToken> list) {
		if(list != null && list.size() > 0) {
			for(FrontDeviceToken fdt : list) {
				this.rabbitSenderService.bindingTopicSend(fdt);
			}
		}
	}
}
