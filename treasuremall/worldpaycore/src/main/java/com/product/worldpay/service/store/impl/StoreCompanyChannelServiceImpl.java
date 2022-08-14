/**
 * 
 */
package com.product.worldpay.service.store.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.ChannelDao;
import com.product.worldpay.dao.CompanyChannelDao;
import com.product.worldpay.dao.CompanyDao;
import com.product.worldpay.entity.Channel;
import com.product.worldpay.entity.CompanyAdmin;
import com.product.worldpay.entity.CompanyChannel;
import com.product.worldpay.entity.CompanyChannel.CompanyChannelStatus;
import com.product.worldpay.service.store.StoreCompanyChannelService;
import com.product.worldpay.vo.store.CompanyChannelVO;


@Service("storeCompanyChannelService")
public class StoreCompanyChannelServiceImpl implements StoreCompanyChannelService {

	@Autowired
	private CompanyChannelDao companyChannelDao;
	
	@Autowired
	private CompanyDao companyDao;
	
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
		String channel = paramsMap.get("channel") == null ? null : paramsMap.get("channel").toString();
		CompanyAdmin admin = (CompanyAdmin)paramsMap.get("companyAdmin");
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", admin.getCompany());
		searchMap.put("channel", channel);
		searchMap.put("type", type);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = companyChannelDao.getCountByParams(searchMap);
		List<CompanyChannel> list = companyChannelDao.getListByParams(searchMap);
		
		List<CompanyChannelVO> voList = new ArrayList<CompanyChannelVO>();
		for(CompanyChannel cc : list){
			CompanyChannelVO vo = new CompanyChannelVO(cc);
			
			Channel ch = this.channelDao.get(cc.getChannel());
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
    	CompanyAdmin admin = (CompanyAdmin)paramsMap.get("companyAdmin");
    	
		CompanyChannel cc = companyChannelDao.get(uuid);
		if (cc == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		if(!cc.getCompany().equals(admin.getCompany())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("no permission to access !");
			return;
		}
		
		CompanyChannelVO vo = new CompanyChannelVO(cc);
		Channel channel = this.channelDao.get(cc.getChannel());
		if(channel != null){
			vo.setChannelName(channel.getName());
		}

		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String status = paramsMap.get("status").toString();
    	CompanyAdmin admin = (CompanyAdmin)paramsMap.get("companyAdmin");
    	
		if(!CompanyChannelStatus.NORMAL.equals(status) && !CompanyChannelStatus.DISABLE.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("status error !");
			return;
		}
		
		CompanyChannel cc = companyChannelDao.get(uuid);
		if (cc == null || CompanyChannelStatus.DELETE.equals(cc.getStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		if(!cc.getCompany().equals(admin.getCompany())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("no permission !");
			return;
		}
		
		cc.setStatus(status);
		this.companyChannelDao.update(cc);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("operate successed !");
	}
}
