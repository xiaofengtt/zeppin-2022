package cn.product.treasuremall.service.front.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.ChannelDao;
import cn.product.treasuremall.entity.Channel;
import cn.product.treasuremall.service.front.FrontChannelService;

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
		result.setMessage("查询成功");
	}
	
}
