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
import com.product.worldpay.dao.CompanyChannelAccountDao;
import com.product.worldpay.dao.CompanyChannelDao;
import com.product.worldpay.dao.CompanyDao;
import com.product.worldpay.entity.Channel;
import com.product.worldpay.entity.ChannelAccount;
import com.product.worldpay.entity.Company;
import com.product.worldpay.entity.CompanyChannel;
import com.product.worldpay.entity.CompanyChannel.CompanyChannelStatus;
import com.product.worldpay.entity.CompanyChannelAccount;
import com.product.worldpay.service.system.SystemCompanyChannelService;
import com.product.worldpay.vo.system.CompanyChannelVO;


@Service("systemCompanyChannelService")
public class SystemCompanyChannelServiceImpl implements SystemCompanyChannelService {

	@Autowired
	private CompanyChannelDao companyChannelDao;
	
	@Autowired
	private CompanyChannelAccountDao companyChannelAccountDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private ChannelAccountDao channelAccountDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String company = paramsMap.get("company") == null ? null : paramsMap.get("company").toString();
		String channel = paramsMap.get("channel") == null ? null : paramsMap.get("channel").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		String type = paramsMap.get("type") == null ? null : paramsMap.get("type").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", company);
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
			
			Company c = this.companyDao.get(cc.getCompany());
			if(c != null){
				vo.setCompanyName(c.getName());
			}
			
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
    	
		CompanyChannel cc = companyChannelDao.get(uuid);
		if (cc == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		
		CompanyChannelVO vo = new CompanyChannelVO(cc);
		
		Company company = this.companyDao.get(cc.getCompany());
		if(company != null){
			vo.setCompanyName(company.getName());
		}
		
		Channel channel = this.channelDao.get(cc.getChannel());
		if(channel != null){
			vo.setChannelName(channel.getName());
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("companyChannel", uuid);
		List<CompanyChannelAccount> ccaList = this.companyChannelAccountDao.getListByParams(searchMap);
		List<String> caList = new ArrayList<String>();
		for(CompanyChannelAccount cca : ccaList){
			caList.add(cca.getChannelAccount());
		}
		vo.setCompanyChannelAccountList(caList);
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		CompanyChannel companyChannel = (CompanyChannel) paramsMap.get("companyChannel");
		String[] channelAccounts = {};
		if(paramsMap.get("channelAccount") != null){
			channelAccounts = (String[])paramsMap.get("channelAccount");
		}
		
		if(!CompanyChannelStatus.NORMAL.equals(companyChannel.getStatus()) && !CompanyChannelStatus.DISABLE.equals(companyChannel.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("status error !");
			return;
		}
		
		if(companyChannel.getMax().compareTo(companyChannel.getMin()) < 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("single limit error !");
			return;
		}
		
		if(companyChannel.getPoundage() == null && companyChannel.getPoundageRate() == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("poundage can not null !");
			return;
		}
		
		if(companyChannel.getPoundage() != null && companyChannel.getPoundageRate() != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("poundage choose only one type !");
			return;
		}
		
		Company company = this.companyDao.get(companyChannel.getCompany());
		if(company == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("company not exist !");
			return;
		}
		
		Channel channel = this.channelDao.get(companyChannel.getChannel());
		if(channel == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("channel not exist !");
			return;
		}
		
		companyChannel.setUuid(UUID.randomUUID().toString());
		companyChannel.setType(channel.getType());
		companyChannel.setCurrency(channel.getCurrency());
		companyChannel.setBalance(BigDecimal.ZERO);
		companyChannel.setBalanceLock(BigDecimal.ZERO);
		companyChannel.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		List<CompanyChannelAccount> ccaList = new ArrayList<CompanyChannelAccount>();
		for(String channelAccount : channelAccounts){
			ChannelAccount ca = this.channelAccountDao.get(channelAccount);
			if(ca == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("channel account not exist !");
				return;
			}
			
			CompanyChannelAccount cca = new CompanyChannelAccount();
			cca.setUuid(UUID.randomUUID().toString());
			cca.setCompany(company.getUuid());
			cca.setChannel(channel.getUuid());
			cca.setChannelAccount(ca.getUuid());
			cca.setCompanyChannel(companyChannel.getUuid());
			ccaList.add(cca);
		}
		
		this.companyChannelDao.insertCompanyChannel(companyChannel, ccaList);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("operate successed !");
	}
	
	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		CompanyChannel companyChannel = (CompanyChannel) paramsMap.get("companyChannel");
		String[] channelAccounts = {};
		if(paramsMap.get("channelAccount") != null){
			channelAccounts = (String[])paramsMap.get("channelAccount");
		}
		
		if(!CompanyChannelStatus.NORMAL.equals(companyChannel.getStatus()) && !CompanyChannelStatus.DISABLE.equals(companyChannel.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("status error !");
			return;
		}
		
		if(companyChannel.getMax().compareTo(companyChannel.getMin()) < 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("single limit error !");
			return;
		}
		
		if(companyChannel.getPoundage() == null && companyChannel.getPoundageRate() == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("poundage can not null !");
			return;
		}
		
		if(companyChannel.getPoundage() != null && companyChannel.getPoundageRate() != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("poundage choose only one type !");
			return;
		}
		
		CompanyChannel cc = companyChannelDao.get(companyChannel.getUuid());
		if (cc == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		
		List<CompanyChannelAccount> ccaList = new ArrayList<CompanyChannelAccount>();
		for(String channelAccount : channelAccounts){
			ChannelAccount ca = this.channelAccountDao.get(channelAccount);
			if(ca == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("channel account not exist !");
				return;
			}
			
			CompanyChannelAccount cca = new CompanyChannelAccount();
			cca.setUuid(UUID.randomUUID().toString());
			cca.setCompany(companyChannel.getCompany());
			cca.setChannel(companyChannel.getChannel());
			cca.setChannelAccount(ca.getUuid());
			cca.setCompanyChannel(companyChannel.getUuid());
			ccaList.add(cca);
		}
		
		cc.setMax(companyChannel.getMax());
		cc.setMin(companyChannel.getMin());
		cc.setPoundage(companyChannel.getPoundage());
		cc.setPoundageRate(companyChannel.getPoundageRate());
		cc.setStatus(companyChannel.getStatus());
		this.companyChannelDao.updateCompanyChannel(cc, ccaList);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("edit successed !");
	}
	
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String status = paramsMap.get("status").toString();
    	
		if(!CompanyChannelStatus.NORMAL.equals(status) && !CompanyChannelStatus.DISABLE.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("status error !");
			return;
		}
		
		CompanyChannel cc = companyChannelDao.get(uuid);
		if (cc == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		
		cc.setStatus(status);
		this.companyChannelDao.update(cc);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("operate successed !");
	}
}
