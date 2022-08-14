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
import com.product.worldpay.dao.UserWithdrawDao;
import com.product.worldpay.entity.Channel;
import com.product.worldpay.entity.Company;
import com.product.worldpay.entity.CompanyAdmin;
import com.product.worldpay.entity.CompanyChannel;
import com.product.worldpay.entity.UserWithdraw;
import com.product.worldpay.entity.UserWithdraw.UserWithdrawStatus;
import com.product.worldpay.locker.Locker;
import com.product.worldpay.locker.ZkCuratorLocker;
import com.product.worldpay.service.store.StoreUserWithdrawService;
import com.product.worldpay.util.api.PaymentException;
import com.product.worldpay.vo.store.UserWithdrawVO;


@Service("storeUserWithdrawService")
public class StoreUserWithdrawServiceImpl implements StoreUserWithdrawService {
 
	@Autowired
	private UserWithdrawDao userWithdrawDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private CompanyChannelDao companyChannelDao;
	
	@Autowired
    private Locker locker;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String channel = paramsMap.get("channel") == null ? null : paramsMap.get("channel").toString();
		String orderNum = paramsMap.get("orderNum") == null ? null : paramsMap.get("orderNum").toString();
		String companyOrderNum = paramsMap.get("companyOrderNum") == null ? null : paramsMap.get("companyOrderNum").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		String starttime = paramsMap.get("starttime") == null ? null : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? null : paramsMap.get("endtime").toString();
		CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", admin.getCompany());
		searchMap.put("channel", channel);
		searchMap.put("orderNumLike", orderNum);
		searchMap.put("companyOrderNumLike", companyOrderNum);
		searchMap.put("status", status);
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = userWithdrawDao.getCountByParams(searchMap);
		List<UserWithdraw> list = userWithdrawDao.getListByParams(searchMap);
		
		List<UserWithdrawVO> voList = new ArrayList<UserWithdrawVO>();
		for(UserWithdraw uw : list){
			UserWithdrawVO vo = new UserWithdrawVO(uw);
			
			Company c = this.companyDao.get(uw.getCompany());
			if(c != null){
				vo.setCompanyName(c.getName());
				vo.setCompanyCode(c.getCode());
			}
			
			Channel ch = this.channelDao.get(uw.getChannel());
			if(ch != null){
				vo.setChannelName(ch.getName());
				vo.setChannelCode(ch.getCode());
			}
			
			CompanyChannel cch = this.companyChannelDao.get(uw.getCompanyChannel());
			if(cch != null){
				vo.setBalance(cch.getBalance());
				vo.setBalanceLock(cch.getBalanceLock());
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
    	
		UserWithdraw uw = userWithdrawDao.get(uuid);
		if (uw == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		if(!uw.getCompany().equals(admin.getCompany())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("no permission to access !");
			return;
		}
		UserWithdrawVO vo = new UserWithdrawVO(uw);
		
		Company c = this.companyDao.get(uw.getCompany());
		if(c != null){
			vo.setCompanyName(c.getName());
			vo.setCompanyCode(c.getCode());
		}
		
		Channel ch = this.channelDao.get(uw.getChannel());
		if(ch != null){
			vo.setChannelName(ch.getName());
			vo.setChannelCode(ch.getCode());
		}
		
		CompanyChannel cch = this.companyChannelDao.get(uw.getCompanyChannel());
		if(cch != null){
			vo.setBalance(cch.getBalance());
			vo.setBalanceLock(cch.getBalanceLock());
		}
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void close(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
		
		UserWithdraw uw = userWithdrawDao.get(uuid);
		if(uw == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		if(!uw.getCompany().equals(admin.getCompany())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("no permission !");
			return;
		}
		if(UserWithdrawStatus.CLOSE.equals(uw.getStatus()) || UserWithdrawStatus.SUCCESS.equals(uw.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data can not process !");
			return;
		}
		
		List<String> errorList = new ArrayList<String>();
		locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
			try {
				this.userWithdrawDao.processOrder(uw, UserWithdrawStatus.CLOSE);
			} catch (PaymentException e) {
				errorList.add(e.getMessage());
				e.printStackTrace();
			}
		});
		if(errorList.size() > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(errorList.get(0));
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("operate successed !");
	}
	
	@Override
	public void retry(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
		
		UserWithdraw uw = userWithdrawDao.get(uuid);
		if(uw == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		if(!uw.getCompany().equals(admin.getCompany())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("no permission !");
			return;
		}
		if(!UserWithdrawStatus.FAIL.equals(uw.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data can not process !");
			return;
		}
		
		List<String> errorList = new ArrayList<String>();
		locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
			try {
				this.userWithdrawDao.processOrder(uw, UserWithdrawStatus.CHECKING);
			} catch (PaymentException e) {
				errorList.add(e.getMessage());
				e.printStackTrace();
			}
		});
		if(errorList.size() > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(errorList.get(0));
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("operate successed !");
	}
}
