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
import com.product.worldpay.dao.CompanyAccountHistoryDao;
import com.product.worldpay.dao.CompanyDao;
import com.product.worldpay.entity.Channel;
import com.product.worldpay.entity.Company;
import com.product.worldpay.entity.CompanyAccountHistory;
import com.product.worldpay.entity.CompanyAdmin;
import com.product.worldpay.service.store.StoreCompanyAccountHistoryService;
import com.product.worldpay.vo.store.CompanyAccountHistoryVO;

@Service("storeCompanyAccountHistoryService")
public class StoreCompanyAccountHistoryServiceImpl implements StoreCompanyAccountHistoryService {

	@Autowired
	private CompanyAccountHistoryDao companyAccountHistoryDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String channel = paramsMap.get("channel") == null ? null : paramsMap.get("channel").toString();
		String companyChannel = paramsMap.get("companyChannel") == null ? null : paramsMap.get("companyChannel").toString();
		String orderNum = paramsMap.get("orderNum") == null ? null : paramsMap.get("orderNum").toString();
		String companyOrderNum = paramsMap.get("companyOrderNum") == null ? null : paramsMap.get("companyOrderNum").toString();
		String type = paramsMap.get("type") == null ? null : paramsMap.get("type").toString();
		String starttime = paramsMap.get("starttime") == null ? null : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? null : paramsMap.get("endtime").toString();
		CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("channel", channel);
		searchMap.put("company", admin.getCompany());
		searchMap.put("companyChannel", companyChannel);
		searchMap.put("orderNum", orderNum);
		searchMap.put("companyOrderNum", companyOrderNum);
		searchMap.put("type", type);
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = companyAccountHistoryDao.getCountByParams(searchMap);
		List<CompanyAccountHistory> list = companyAccountHistoryDao.getListByParams(searchMap);
		
		List<CompanyAccountHistoryVO> voList = new ArrayList<CompanyAccountHistoryVO>();
		for(CompanyAccountHistory cah : list){
			CompanyAccountHistoryVO vo = new CompanyAccountHistoryVO(cah);
			
			Channel ch = this.channelDao.get(cah.getChannel());
			if(ch != null){
				vo.setChannelName(ch.getName());
				vo.setChannelCode(ch.getCode());
			}
			Company c = this.companyDao.get(cah.getCompany());
			if(c != null){
				vo.setCompanyName(c.getName());
				vo.setCompanyCode(c.getCode());
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
    	CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
    	
		CompanyAccountHistory cah = companyAccountHistoryDao.get(uuid);
		if (cah == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		if(!cah.getCompany().equals(admin.getCompany())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("no permission to access !");
			return;
		}
		CompanyAccountHistoryVO vo = new CompanyAccountHistoryVO(cah);
		
		Channel ch = this.channelDao.get(cah.getChannel());
		if(ch != null){
			vo.setChannelName(ch.getName());
			vo.setChannelCode(ch.getCode());
		}
		Company c = this.companyDao.get(cah.getCompany());
		if(c != null){
			vo.setCompanyName(c.getName());
			vo.setCompanyCode(c.getCode());
		}
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
}
