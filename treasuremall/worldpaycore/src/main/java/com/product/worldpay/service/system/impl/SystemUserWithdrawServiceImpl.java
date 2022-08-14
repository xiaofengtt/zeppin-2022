/**
 * 
 */
package com.product.worldpay.service.system.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.AdminDao;
import com.product.worldpay.dao.ChannelAccountDailyDao;
import com.product.worldpay.dao.ChannelAccountDao;
import com.product.worldpay.dao.ChannelDao;
import com.product.worldpay.dao.CompanyChannelAccountDao;
import com.product.worldpay.dao.CompanyChannelDao;
import com.product.worldpay.dao.CompanyDao;
import com.product.worldpay.dao.UserWithdrawDao;
import com.product.worldpay.entity.Admin;
import com.product.worldpay.entity.Channel;
import com.product.worldpay.entity.ChannelAccount;
import com.product.worldpay.entity.ChannelAccount.ChannelAccountStatus;
import com.product.worldpay.entity.ChannelAccountDaily;
import com.product.worldpay.entity.Company;
import com.product.worldpay.entity.CompanyChannel;
import com.product.worldpay.entity.CompanyChannelAccount;
import com.product.worldpay.entity.UserWithdraw;
import com.product.worldpay.entity.UserWithdraw.UserWithdrawStatus;
import com.product.worldpay.locker.Locker;
import com.product.worldpay.locker.ZkCuratorLocker;
import com.product.worldpay.service.system.SystemUserWithdrawService;
import com.product.worldpay.util.api.PaymentException;
import com.product.worldpay.vo.system.ChannelAccountVO;
import com.product.worldpay.vo.system.StatusCountVO;
import com.product.worldpay.vo.system.UserWithdrawVO;


@Service("systemUserWithdrawService")
public class SystemUserWithdrawServiceImpl implements SystemUserWithdrawService {
 
	@Autowired
	private UserWithdrawDao userWithdrawDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CompanyChannelAccountDao companyChannelAccountDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private ChannelAccountDao channelAccountDao;
	
	@Autowired
	private ChannelAccountDailyDao channelAccountDailyDao;
	
	@Autowired
	private CompanyChannelDao companyChannelDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
    private Locker locker;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String channel = paramsMap.get("channel") == null ? null : paramsMap.get("channel").toString();
		String channelAccount = paramsMap.get("channelAccount") == null ? null : paramsMap.get("channelAccount").toString();
		String company = paramsMap.get("company") == null ? null : paramsMap.get("company").toString();
		String companyChannel = paramsMap.get("companyChannel") == null ? null : paramsMap.get("companyChannel").toString();
		String orderNum = paramsMap.get("orderNum") == null ? null : paramsMap.get("orderNum").toString();
		String companyOrderNum = paramsMap.get("companyOrderNum") == null ? null : paramsMap.get("companyOrderNum").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		String starttime = paramsMap.get("starttime") == null ? null : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? null : paramsMap.get("endtime").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", company);
		searchMap.put("channel", channel);
		searchMap.put("companyChannel", companyChannel);
		searchMap.put("channelAccount", channelAccount);
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
			
			if(uw.getChannelAccount() != null){
				ChannelAccount cha = this.channelAccountDao.get(uw.getChannelAccount());
				if(cha != null){
					vo.setChannelAccountName(cha.getName());
					vo.setChannelAccountNum(cha.getAccountNum());
				}
			}
			
			if(uw.getOperator() != null){
				Admin operator = this.adminDao.get(uw.getOperator());
				if(operator != null){
					vo.setOperatorName(operator.getRealname());
				}
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
    	
		UserWithdraw uw = userWithdrawDao.get(uuid);
		if (uw == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
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
		
		if(uw.getChannelAccount() != null){
			ChannelAccount cha = this.channelAccountDao.get(uw.getChannelAccount());
			if(cha != null){
				vo.setChannelAccountName(cha.getName());
				vo.setChannelAccountNum(cha.getAccountNum());
			}
		}
		
		if(uw.getOperator() != null){
			Admin operator = this.adminDao.get(uw.getOperator());
			if(operator != null){
				vo.setOperatorName(operator.getRealname());
			}
		}

		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void channelAccountList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String companyChannel = paramsMap.get("companyChannel").toString();
    	
    	CompanyChannel cc = this.companyChannelDao.get(companyChannel);
    	if(cc == null){
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("company channel not exist !");
			return;
    	}
    	
    	List<ChannelAccount> list = new ArrayList<ChannelAccount>();
    	Map<String, Object> searchMap = new HashMap<String, Object>();
    	searchMap.put("companyChannel", companyChannel);
		List<CompanyChannelAccount> ccaList = this.companyChannelAccountDao.getListByParams(searchMap);
		if(ccaList.size() > 0){
			searchMap.clear();
			searchMap.put("companyChannel", companyChannel);
			searchMap.put("status", ChannelAccountStatus.NORMAL);
			list = this.companyChannelAccountDao.getChannelAccountListByParams(searchMap);
		}else{
			searchMap.clear();
			searchMap.put("channel", cc.getChannel());
			searchMap.put("status", ChannelAccountStatus.NORMAL);
			list = this.channelAccountDao.getListByParams(searchMap);
		}
		
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
	}
	
	@Override
	public void check(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String channelAccount = paramsMap.get("channelAccount").toString();
    	Admin admin = (Admin)paramsMap.get("admin");
		
		UserWithdraw uw = userWithdrawDao.get(uuid);
		if(uw == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		if(!UserWithdrawStatus.FAIL.equals(uw.getStatus()) && !UserWithdrawStatus.CHECKING.equals(uw.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data can not process !");
			return;
		}
		
		ChannelAccount ca = this.channelAccountDao.get(channelAccount);
		if(ca == null || ChannelAccountStatus.DELETE.equals(ca.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("channel account not exist !");
			return;
		}
		if(!ChannelAccountStatus.NORMAL.equals(ca.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("channel account disabled !");
			return;
		}
		
		//手续费
		BigDecimal chaPoundage = BigDecimal.ZERO;
		if(ca.getPoundage() != null){
			chaPoundage = ca.getPoundage();
		}
		if(ca.getPoundageRate() != null){
			chaPoundage = uw.getAmount().multiply(ca.getPoundageRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
		}
		
		//日额度
		ChannelAccountDaily cad = this.channelAccountDailyDao.get(channelAccount);
		if(cad != null){
			if(cad.getAmount().add(uw.getAmount().add(chaPoundage)).compareTo(ca.getDailyMax()) > 0){
				ca.setStatus(ChannelAccountStatus.SUSPEND);
				this.channelAccountDao.update(ca);
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("channel account daily limit not enough !");
				return;
			}
		}
		
		//总额度
		if(ca.getBalance().subtract(uw.getAmount().add(chaPoundage)).abs().compareTo(ca.getTotalMax()) > 0){
			ca.setStatus(ChannelAccountStatus.DISABLE);
			this.channelAccountDao.update(ca);
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("channel account total limit not enough !");
			return;
		}
		
		
		uw.setChannelAccount(channelAccount);
		uw.setOperator(admin.getUuid());
		try {
			this.userWithdrawDao.processOrder(uw, UserWithdrawStatus.CHECKED);
		} catch (PaymentException e) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(e.getMessage());
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("check successed !");
	}
	
	@Override
	public void fail(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String reason = paramsMap.get("reason").toString();
    	Admin admin = (Admin)paramsMap.get("admin");
		
		UserWithdraw uw = userWithdrawDao.get(uuid);
		if(uw == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		if(!UserWithdrawStatus.CHECKING.equals(uw.getStatus()) && !UserWithdrawStatus.CHECKED.equals(uw.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data can not process !");
			return;
		}
		
		uw.setFailReason(reason);
		uw.setOperator(admin.getUuid());
		try {
			this.userWithdrawDao.processOrder(uw, UserWithdrawStatus.FAIL);
		} catch (PaymentException e) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(e.getMessage());
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("operate successed !");
	}
	
	@Override
	public void success(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String proof = paramsMap.get("proof").toString();
    	Admin admin = (Admin)paramsMap.get("admin");
		
		UserWithdraw uw = userWithdrawDao.get(uuid);
		if(uw == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		if(!UserWithdrawStatus.CHECKED.equals(uw.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data can not process !");
			return;
		}
		
		uw.setOperator(admin.getUuid());
		uw.setProof(proof);
		
		List<String> errorList = new ArrayList<String>();
		locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
			try {
				this.userWithdrawDao.processOrder(uw, UserWithdrawStatus.SUCCESS);
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
	public void typeList(InputParams params, DataResult<Object> result) {
    	List<StatusCountVO> voList = this.userWithdrawDao.getCheckingChannelList();
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
	}
}
