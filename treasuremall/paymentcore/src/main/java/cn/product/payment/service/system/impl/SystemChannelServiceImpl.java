/**
 * 
 */
package cn.product.payment.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.AdminDao;
import cn.product.payment.dao.ChannelDao;
import cn.product.payment.entity.Channel;
import cn.product.payment.entity.Channel.ChannelStatus;
import cn.product.payment.service.system.SystemChannelService;
import cn.product.payment.vo.system.ChannelVO;

/**
 * 渠道
 */
@Service("systemChannelService")
public class SystemChannelServiceImpl implements SystemChannelService {

	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		String type = paramsMap.get("type") == null ? null : paramsMap.get("type").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("type", type);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = channelDao.getCountByParams(searchMap);
		List<Channel> list = channelDao.getListByParams(searchMap);
		
		List<ChannelVO> voList = new ArrayList<ChannelVO>();
		for(Channel channel : list){
			ChannelVO vo = new ChannelVO(channel);
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
    	
		Channel channel = channelDao.get(uuid);
		if (channel == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		ChannelVO vo = new ChannelVO(channel);
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
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
		
		Channel channel = channelDao.get(uuid);
		if (channel == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		channel.setStatus(status);
		this.channelDao.update(channel);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
}
