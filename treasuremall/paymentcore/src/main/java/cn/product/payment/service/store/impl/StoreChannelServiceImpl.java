/**
 * 
 */
package cn.product.payment.service.store.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.ChannelDao;
import cn.product.payment.entity.Channel;
import cn.product.payment.service.store.StoreChannelService;
import cn.product.payment.vo.store.ChannelVO;

/**
 * 渠道
 * @author Administrator
 *
 */
@Service("storeChannelService")
public class StoreChannelServiceImpl implements StoreChannelService {

	@Autowired
	private ChannelDao channelDao;
	
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
		
		result.setData(list);
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
}
