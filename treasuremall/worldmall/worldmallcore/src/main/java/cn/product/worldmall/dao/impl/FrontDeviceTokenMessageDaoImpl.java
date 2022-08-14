package cn.product.worldmall.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.dao.FrontDeviceTokenMessageDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserMessageDao;
import cn.product.worldmall.entity.FrontDeviceToken;
import cn.product.worldmall.entity.FrontDeviceTokenMessage;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserMessage;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageType;
import cn.product.worldmall.mapper.FrontDeviceTokenMessageMapper;
import cn.product.worldmall.rabbit.send.RabbitSenderService;
import cn.product.worldmall.util.Utlity;

@Component
public class FrontDeviceTokenMessageDaoImpl implements FrontDeviceTokenMessageDao{
	
	@Autowired
	private FrontDeviceTokenMessageMapper frontDeviceTokenMessageMapper;
	
	@Autowired
	private RabbitSenderService rabbitSenderService;
	
	@Autowired
	private FrontUserDao frontUserDao;
    
    @Autowired
    private FrontUserMessageDao frontUserMessageDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontDeviceTokenMessageMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontDeviceTokenMessage> getListByParams(Map<String, Object> params) {
		return frontDeviceTokenMessageMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontDeviceTokenMessage",key="'frontDeviceTokenMessage:' + #key")
	public FrontDeviceTokenMessage get(String key) {
		return frontDeviceTokenMessageMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontDeviceTokenMessage frontDeviceTokenMessage) {
		return frontDeviceTokenMessageMapper.insert(frontDeviceTokenMessage);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontDeviceTokenMessage", key = "'frontDeviceTokenMessage:' + #key")})
	public int delete(String key) {
		return frontDeviceTokenMessageMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontDeviceTokenMessage", key = "'frontDeviceTokenMessage:' + #frontDeviceTokenMessage.uuid")})
	public int update(FrontDeviceTokenMessage frontDeviceTokenMessage) {
		return frontDeviceTokenMessageMapper.updateByPrimaryKey(frontDeviceTokenMessage);
	}

	@Override
	@Transactional
	public void sendSNSMessage(List<FrontDeviceTokenMessage> list) {
		if(list != null && list.size() > 0) {
			for(FrontDeviceTokenMessage fdtm : list) {
				this.rabbitSenderService.messageStartSnsMessageSend(fdtm);
			}
		}
	}

	@Override
	@Transactional
	public void insertMessage(FrontDeviceToken fdt, FrontDeviceTokenMessage fdtm) {
		this.frontDeviceTokenMessageMapper.insert(fdtm);
		
		if(!Utlity.checkStringNull(fdt.getFrontUser()) && fdtm.getFlagUserSend()) {
			FrontUser fu = this.frontUserDao.get(fdt.getFrontUser());
			if(fu != null) {
				//发送站内信息
				FrontUserMessage fum = new FrontUserMessage();
				fum.setUuid(UUID.randomUUID().toString());
				fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
				fum.setType(FrontUserMessageType.SYSTEM_NOTICE);
				fum.setFrontUser(fdt.getFrontUser());
				fum.setFrontUserShowId(fu.getShowId());
				fum.setSourceId(fdtm.getUuid());
				fum.setSourceType(FrontUserMessageSourceType.SYSTEM_NOTICE);
				fum.setStatus(FrontUserMessageStatus.NORMAL);
				fum.setTitle(fdtm.getTitle());
				fum.setContent(fdtm.getContent());
				this.frontUserMessageDao.insert(fum);
			}
		}
	}
}
