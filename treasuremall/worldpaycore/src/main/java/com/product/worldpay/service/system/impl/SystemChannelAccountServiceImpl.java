/**
 * 
 */
package com.product.worldpay.service.system.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.AdminDao;
import com.product.worldpay.dao.ChannelAccountDao;
import com.product.worldpay.dao.ChannelDao;
import com.product.worldpay.dao.ResourceDao;
import com.product.worldpay.entity.Channel;
import com.product.worldpay.entity.Channel.ChannelStatus;
import com.product.worldpay.entity.ChannelAccount;
import com.product.worldpay.entity.ChannelAccount.ChannelAccountStatus;
import com.product.worldpay.service.system.SystemChannelAccountService;
import com.product.worldpay.util.JSONUtils;
import com.product.worldpay.vo.system.ChannelAccountVO;


@Service("systemChannelAccountService")
public class SystemChannelAccountServiceImpl implements SystemChannelAccountService {

	@Autowired
	private ChannelAccountDao channelAccountDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String channel = paramsMap.get("channel") == null ? null : paramsMap.get("channel").toString();
		String name = paramsMap.get("name") == null ? null : paramsMap.get("name").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		String type = paramsMap.get("type") == null ? null : paramsMap.get("type").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("channel", channel);
		searchMap.put("name", name);
		searchMap.put("type", type);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = channelAccountDao.getCountByParams(searchMap);
		List<ChannelAccount> list = channelAccountDao.getListByParams(searchMap);
		
		List<ChannelAccountVO> voList = new ArrayList<ChannelAccountVO>();
		for(ChannelAccount ca : list){
			ChannelAccountVO vo = new ChannelAccountVO(ca);
			
			Channel ch = this.channelDao.get(ca.getChannel());
			if(ch != null){
				vo.setChannelName(ch.getName());
			}
			voList.add(vo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	
		ChannelAccount ca = channelAccountDao.get(uuid);
		if (ca == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		ChannelAccountVO vo = new ChannelAccountVO(ca);
		
		Channel channel = this.channelDao.get(ca.getChannel());
		if(channel != null){
			vo.setChannelName(channel.getName());
		}
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		ChannelAccount channelAccount = (ChannelAccount) paramsMap.get("channelAccount");
		
		if(!ChannelAccountStatus.NORMAL.equals(channelAccount.getStatus()) && !ChannelAccountStatus.DISABLE.equals(channelAccount.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("status error !");
			return;
		}
		
		if(channelAccount.getPoundage() == null && channelAccount.getPoundageRate() == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("poundage can not null !");
			return;
		}
		
		if(channelAccount.getPoundage() != null && channelAccount.getPoundageRate() != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("poundage choose only one type !");
			return;
		}
		
		if(channelAccount.getMax().compareTo(channelAccount.getMin()) < 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("single limit error !");
			return;
		}
		
		Channel channel = this.channelDao.get(channelAccount.getChannel());
		if(channel == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("channel not exist !");
			return;
		}
		
		Map<String, Object> baseMap = JSONUtils.json2map(channel.getData());
		Map<String, Object> dataMap = JSONUtils.json2map(channelAccount.getData());
		
		for(String key : baseMap.keySet()){
			if(dataMap.get(key) == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage(baseMap.get(key)+" can not null !");
				return;
			}
		}
		
		channelAccount.setUuid(UUID.randomUUID().toString());
		channelAccount.setType(channel.getType());
		channelAccount.setCurrency(channel.getCurrency());
		channelAccount.setBalance(BigDecimal.ZERO);
		channelAccount.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.channelAccountDao.insert(channelAccount);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("operate successed !");
	}
	
	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		ChannelAccount channelAccount = (ChannelAccount) paramsMap.get("channelAccount");
		
		if(!ChannelAccountStatus.NORMAL.equals(channelAccount.getStatus()) && !ChannelAccountStatus.DISABLE.equals(channelAccount.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("status error !");
			return;
		}
		
		if(channelAccount.getPoundage() == null && channelAccount.getPoundageRate() == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("poundage can not null !");
			return;
		}
		
		if(channelAccount.getPoundage() != null && channelAccount.getPoundageRate() != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("poundage choose only one type !");
			return;
		}
		
		ChannelAccount ca = channelAccountDao.get(channelAccount.getUuid());
		if (ca == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		
		Channel channel = this.channelDao.get(channelAccount.getChannel());
		Map<String, Object> baseMap = JSONUtils.json2map(channel.getData());
		Map<String, Object> dataMap = JSONUtils.json2map(channelAccount.getData());
		
		for(String key : baseMap.keySet()){
			if(dataMap.get(key) == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage(baseMap.get(key)+" can not null !");
				return;
			}
		}
		
		ca.setName(channelAccount.getName());
		ca.setAccountNum(channelAccount.getAccountNum());
		ca.setTransferUrl(channelAccount.getTransferUrl());
		ca.setData(channelAccount.getData());
		ca.setPoundage(channelAccount.getPoundage());
		ca.setPoundageRate(channelAccount.getPoundageRate());
		ca.setMin(channelAccount.getMin());
		ca.setMax(channelAccount.getMax());
		ca.setDailyMax(channelAccount.getDailyMax());
		ca.setTotalMax(channelAccount.getTotalMax());
		ca.setStatus(channelAccount.getStatus());
		this.channelAccountDao.update(ca);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("edit successed !");
	}
	
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String status = paramsMap.get("status").toString();
    	
		if(!ChannelStatus.NORMAL.equals(status) && !ChannelStatus.DISABLE.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("status error !");
			return;
		}
		
		ChannelAccount ca = channelAccountDao.get(uuid);
		if (ca == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		
		ca.setStatus(status);
		this.channelAccountDao.update(ca);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("operate successed !");
	}
}
