package cn.product.worldmall.service.front.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.ChannelDao;
import cn.product.worldmall.entity.Channel;
import cn.product.worldmall.service.front.FrontChannelService;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("frontChannelService")
public class FrontChannelServiceImpl implements FrontChannelService{
	
	
	@Autowired
	private ChannelDao channelDao;
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String channelId = paramsMap.get("channelId") == null ? "" : paramsMap.get("channelId").toString();
		
		Channel ch = this.channelDao.getById(channelId);
		
		result.setData(ch);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
	}
	
}
