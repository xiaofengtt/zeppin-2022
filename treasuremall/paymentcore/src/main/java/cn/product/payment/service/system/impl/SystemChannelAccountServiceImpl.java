/**
 * 
 */
package cn.product.payment.service.system.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.AdminDao;
import cn.product.payment.dao.ChannelAccountDao;
import cn.product.payment.dao.ChannelDao;
import cn.product.payment.dao.ResourceDao;
import cn.product.payment.entity.Channel;
import cn.product.payment.entity.Channel.ChannelStatus;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.ChannelAccount.ChannelAccountStatus;
import cn.product.payment.entity.Resource;
import cn.product.payment.service.system.SystemChannelAccountService;
import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.Utlity;
import cn.product.payment.vo.system.ChannelAccountVO;

/**
 * 渠道账户
 */
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
			
			//渠道
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	
		ChannelAccount ca = channelAccountDao.get(uuid);
		if (ca == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		ChannelAccountVO vo = new ChannelAccountVO(ca);
		
		//渠道
		Channel channel = this.channelDao.get(ca.getChannel());
		if(channel != null){
			vo.setChannelName(channel.getName());
		}
		
		if("02".equals(channel.getCode()) || "04".equals(channel.getCode())){
			//支付宝扫码或微信扫码
			String normalUuid = vo.getDataMap().get("normal").toString();
			//取非定额收款码
			Resource normalResource = this.resourceDao.get(normalUuid);
			if(normalResource != null){
				vo.getDataMap().put("normal", normalResource.getUuid() + "@" + normalResource.getUrl());
			}
			
			//定额收款码Map
			Map<String, Object> fixedMap = (Map<String, Object>)vo.getDataMap().get("fixed");
			
			for(String key : fixedMap.keySet()){
				//取定额收款码
				String resUuid = fixedMap.get(key).toString();
				Resource fixedResource = this.resourceDao.get(resUuid);
				if(fixedResource != null){
					fixedMap.put(key, resUuid + "@" + fixedResource.getUrl());
				}
			}
		}
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		ChannelAccount channelAccount = (ChannelAccount) paramsMap.get("channelAccount");
		
		if(!ChannelAccountStatus.NORMAL.equals(channelAccount.getStatus()) && !ChannelAccountStatus.DISABLE.equals(channelAccount.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("状态错误！");
			return;
		}
		
		if(channelAccount.getPoundage() == null && channelAccount.getPoundageRate() == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("手续费不能为空！");
			return;
		}
		
		if(channelAccount.getPoundage() != null && channelAccount.getPoundageRate() != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("手续费暂时只能选择一种方式！");
			return;
		}
		
		if(channelAccount.getMax().compareTo(channelAccount.getMin()) < 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("单次额度设置错误！");
			return;
		}
		
		Channel channel = this.channelDao.get(channelAccount.getChannel());
		if(channel == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道不存在！");
			return;
		}
		
		//取渠道应填字段内容
		Map<String, Object> baseMap = JSONUtils.json2map(channel.getData());
		//取填入字段内容
		Map<String, Object> dataMap = JSONUtils.json2map(channelAccount.getData());
		
		//循环应填写字段
		for(String key : baseMap.keySet()){
			if(dataMap.get(key) == null){
				//未填写应填字段
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("未填写"+baseMap.get(key)+"！");
				return;
			}
			try{
				if("02".equals(channel.getCode()) || "04".equals(channel.getCode())){
					//支付宝扫码支付 或 微信扫码支付
					if("normal".equals(key)){
						//非定额收款码
						if(Utlity.checkStringNull(dataMap.get(key).toString())){
							result.setStatus(ResultStatusType.FAILED);
							result.setMessage("通用收款码必填！");
							return;
						}
					}
					if("fixed".equals(key)){
						//定额收款码
						Map<String, Object> fixedMap = (Map<String, Object>) dataMap.get(key);
						//循环金额
						for(String amount : fixedMap.keySet()){
							if(!Utlity.isNumeric(amount)){
								//金额非数字
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage(baseMap.get(key)+"格式错误！");
								return;
							}
							//取二维码文件
							Resource codeResource = this.resourceDao.get(fixedMap.get(amount).toString());
							if(codeResource == null){
								//二维码文件不存在
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage(baseMap.get(key)+":"+amount+"二维码不存在！");
								return;
							}
						}
					}
				}
			} catch(Exception e){
				e.printStackTrace();
				
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage(baseMap.get(key)+"格式错误！");
				return;
			}
		}
		
		channelAccount.setUuid(UUID.randomUUID().toString());
		channelAccount.setType(channel.getType());
		channelAccount.setBalance(BigDecimal.ZERO);
		channelAccount.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.channelAccountDao.insert(channelAccount);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("创建成功！");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		ChannelAccount channelAccount = (ChannelAccount) paramsMap.get("channelAccount");
		
		if(!ChannelAccountStatus.NORMAL.equals(channelAccount.getStatus()) && !ChannelAccountStatus.DISABLE.equals(channelAccount.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("状态错误！");
			return;
		}
		
		if(channelAccount.getPoundage() == null && channelAccount.getPoundageRate() == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("手续费不能为空！");
			return;
		}
		
		if(channelAccount.getPoundage() != null && channelAccount.getPoundageRate() != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("手续费暂时只能选择一种方式！");
			return;
		}
		
		ChannelAccount ca = channelAccountDao.get(channelAccount.getUuid());
		if (ca == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		Channel channel = this.channelDao.get(channelAccount.getChannel());
		//取渠道应填字段内容
		Map<String, Object> baseMap = JSONUtils.json2map(channel.getData());
		//取天赋字段内容
		Map<String, Object> dataMap = JSONUtils.json2map(channelAccount.getData());
		
		//循环应填字段内容
		for(String key : baseMap.keySet()){
			if(dataMap.get(key) == null){
				//未填写应填字段
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("未填写"+baseMap.get(key)+"！");
				return;
			}
			try{
				if("02".equals(channel.getCode()) || "04".equals(channel.getCode())){
					//支付宝扫码或微信扫码
					if("normal".equals(key)){
						//非定额收款码
						if(Utlity.checkStringNull(dataMap.get(key).toString())){
							result.setStatus(ResultStatusType.FAILED);
							result.setMessage("通用收款码必填！");
							return;
						}
					}
					if("fixed".equals(key)){
						//定额收款码
						Map<String, Object> fixedMap = (Map<String, Object>) dataMap.get(key);
						//循环金额
						for(String amount : fixedMap.keySet()){
							if(!Utlity.isNumeric(amount)){
								//金额非数字
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage(baseMap.get(key)+"格式错误！");
								return;
							}
							
							//取二维码文件
							Resource codeResource = this.resourceDao.get(fixedMap.get(amount).toString());
							if(codeResource == null){
								//二维码文件不存在
								result.setStatus(ResultStatusType.FAILED);
								result.setMessage(baseMap.get(key)+":"+amount+"二维码不存在！");
								return;
							}
						}
					}
				}
			} catch(Exception e){
				e.printStackTrace();
				
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage(baseMap.get(key)+"格式错误！");
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
		result.setMessage("修改成功！");
	}
	
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String status = paramsMap.get("status").toString();
    	
		if(!ChannelStatus.NORMAL.equals(status) && !ChannelStatus.DISABLE.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("状态错误！");
			return;
		}
		
		ChannelAccount ca = channelAccountDao.get(uuid);
		if (ca == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		ca.setStatus(status);
		this.channelAccountDao.update(ca);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
}
