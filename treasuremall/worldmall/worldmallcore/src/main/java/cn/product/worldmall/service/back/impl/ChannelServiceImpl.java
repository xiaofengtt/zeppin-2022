package cn.product.worldmall.service.back.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.ChannelDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.entity.Channel;
import cn.product.worldmall.entity.Channel.ChannelStatus;
import cn.product.worldmall.service.back.ChannelService;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("channelService")
public class ChannelServiceImpl implements ChannelService{
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sorts = paramsMap.get("sorts") == null ? "" : paramsMap.get("sorts").toString();
		String title = paramsMap.get("title") == null ? "" : paramsMap.get("title").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("title", title);
		searchMap.put("status", status);
		searchMap.put("sort", sorts);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//查询符合条件的注册渠道信息的总数
		Integer totalResultCount = channelDao.getCountByParams(searchMap);
		//查询符合条件的注册渠道信息列表
		List<Channel> list = channelDao.getListByParams(searchMap);
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		//获取注册渠道信息
		Channel channel = channelDao.get(uuid);
		if (channel == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
			return;
		}
		
		result.setData(channel);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		/*
		 * */
		String channelId = paramsMap.get("channelId") == null ? "" : paramsMap.get("channelId").toString();
		String title = paramsMap.get("title") == null ? "" : paramsMap.get("title").toString();
		String appkey = paramsMap.get("appkey") == null ? "" : paramsMap.get("appkey").toString();
		String channelstr = paramsMap.get("channel") == null ? "" : paramsMap.get("channel").toString();
		Boolean isDefault = paramsMap.get("isDefault") == null ? false : Boolean.valueOf(paramsMap.get("isDefault").toString());
		
		try {
			if(this.channelDao.getById(channelId) != null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("渠道ID已存在");
				return;
			}
			//创建注册渠道信息
			Channel channel = new Channel();
			channel.setUuid(UUID.randomUUID().toString());
			channel.setChannelId(channelId);
			channel.setTitle(title);
			channel.setAppkey(appkey);
			channel.setChannel(channelstr);
			channel.setIsDefault(isDefault);
			channel.setStatus(ChannelStatus.NORMAL);
			channel.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			channelDao.insert(channel);
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("保存异常");
		}
		
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String channelId = paramsMap.get("channelId") == null ? "" : paramsMap.get("channelId").toString();
		String title = paramsMap.get("title") == null ? "" : paramsMap.get("title").toString();
		String appkey = paramsMap.get("appkey") == null ? "" : paramsMap.get("appkey").toString();
		String channelstr = paramsMap.get("channel") == null ? "" : paramsMap.get("channel").toString();
		Boolean isDefault = paramsMap.get("isDefault") == null ? false : Boolean.valueOf(paramsMap.get("isDefault").toString());
		
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		try {
			//获取注册渠道信息
			Channel channel = channelDao.get(uuid);
			if(channel != null && uuid.equals(channel.getUuid())){
				
				//修改注册渠道信息
				channel.setChannelId(channelId);
				channel.setTitle(title);
				channel.setAppkey(appkey);
				channel.setChannel(channelstr);
				channel.setIsDefault(isDefault);
				
				channelDao.update(channel);
				
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("保存成功");
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("该条数据不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("保存异常");
		}
		
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		//获取注册渠道信息
		Channel channel = channelDao.get(uuid);
		if(channel != null && uuid.equals(channel.getUuid())){
			//删除注册渠道信息
			channel.setStatus(ChannelStatus.DELETE);
			channelDao.update(channel);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("删除成功");
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
		}
	}

	@Override
	public void isdefault(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();		
		try {
			//获取注册渠道信息
			Channel channel = channelDao.get(uuid);
			if(channel != null && uuid.equals(channel.getUuid())){
				
				//修改注册渠道信息
				channel.setIsDefault(true);
				
				channelDao.updateIsDefault(channel);
				
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("保存成功");
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("该条数据不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("保存异常");
		}
	}
	
}
