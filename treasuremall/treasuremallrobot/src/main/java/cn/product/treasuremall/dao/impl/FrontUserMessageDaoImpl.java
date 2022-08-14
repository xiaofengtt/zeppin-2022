package cn.product.treasuremall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserMessageDao;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUser.FrontUserType;
import cn.product.treasuremall.entity.FrontUserMessage;
import cn.product.treasuremall.mapper.FrontUserMessageMapper;
import cn.product.treasuremall.rabbit.send.RabbitSenderService;

@Component
public class FrontUserMessageDaoImpl implements FrontUserMessageDao{
	
	@Autowired
	private FrontUserMessageMapper frontUserMessageMapper;
	
	@Autowired
	private RabbitSenderService rabbitSenderService;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserMessageMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserMessage> getListByParams(Map<String, Object> params) {
		return frontUserMessageMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserMessage",key="'frontUserMessage:' + #key")
	public FrontUserMessage get(String key) {
		return frontUserMessageMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserMessage frontUserMessage) {
		return frontUserMessageMapper.insert(frontUserMessage);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserMessage", key = "'frontUserMessage:' + #key")})
	public int delete(String key) {
		return frontUserMessageMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserMessage", key = "'frontUserMessage:' + #frontUserMessage.uuid")})
	public int update(FrontUserMessage frontUserMessage) {
		return frontUserMessageMapper.updateByPrimaryKey(frontUserMessage);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserMessage", allEntries=true)})
	public void readAll(List<FrontUserMessage> updateList) {
		if(updateList != null && updateList.size() > 0) {
			for(FrontUserMessage fum : updateList) {
				this.frontUserMessageMapper.updateByPrimaryKey(fum);
			}
			
		}
	}

	@Override
	public void sendMessage(FrontUserMessage fum) {
		this.rabbitSenderService.messageStartMessageSend(fum);
	}

	@Override
	public void sendMessage(FrontUserMessage fum, String temp_id) {
//		this.rabbitSenderService.smsStartMessageSend(fum, temp_id);
	}

	@Override
	public void sendMessage(List<FrontUserMessage> fumList, String temp_id) {
		if(fumList != null && fumList.size() > 0) {
			for(FrontUserMessage fum : fumList) {
				//判断是否是机器人，机器人不予发送中将短信通知
				FrontUser fu = this.frontUserDao.get(fum.getFrontUser());
				if(fu != null && FrontUserType.NORMAL.equals(fu.getType())) {
//					this.rabbitSenderService.smsStartMessageSend(fum, temp_id);
				}
			}
			
		}
	}

	@Override
	@Transactional
	public void sendMessage(List<FrontUserMessage> fumList) {
		if(fumList != null && fumList.size() > 0) {
			for(FrontUserMessage fum : fumList) {
//				this.rabbitSenderService.messageStartMessageSend(fum);
				this.frontUserMessageMapper.insert(fum);
			}
			
		}
	}

}
