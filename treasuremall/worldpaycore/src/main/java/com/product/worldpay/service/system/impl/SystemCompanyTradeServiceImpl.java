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
import com.product.worldpay.dao.CompanyBankcardDao;
import com.product.worldpay.dao.CompanyChannelDao;
import com.product.worldpay.dao.CompanyDao;
import com.product.worldpay.dao.CompanyTradeDao;
import com.product.worldpay.entity.Admin;
import com.product.worldpay.entity.Company;
import com.product.worldpay.entity.CompanyBankcard;
import com.product.worldpay.entity.CompanyBankcard.CompanyBankcardStatus;
import com.product.worldpay.entity.CompanyChannel;
import com.product.worldpay.entity.CompanyTrade;
import com.product.worldpay.entity.CompanyTrade.CompanyTradeStatus;
import com.product.worldpay.entity.CompanyTrade.CompanyTradeType;
import com.product.worldpay.locker.Locker;
import com.product.worldpay.locker.ZkCuratorLocker;
import com.product.worldpay.service.system.SystemCompanyTradeService;
import com.product.worldpay.util.Utlity;
import com.product.worldpay.util.api.PaymentException;
import com.product.worldpay.vo.system.CompanyTradeVO;


@Service("systemCompanyTradeService")
public class SystemCompanyTradeServiceImpl implements SystemCompanyTradeService {
 
	@Autowired
	private CompanyTradeDao companyTradeDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CompanyChannelDao companyChannelDao;
	
	@Autowired
	private CompanyBankcardDao companyBankcardDao;
	
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
		String company = paramsMap.get("company") == null ? null : paramsMap.get("company").toString();
		String orderNum = paramsMap.get("orderNum") == null ? null : paramsMap.get("orderNum").toString();
		String type = paramsMap.get("type") == null ? null : paramsMap.get("type").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		String starttime = paramsMap.get("starttime") == null ? null : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? null : paramsMap.get("endtime").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", company);
		searchMap.put("orderNumLike", orderNum);
		searchMap.put("type", type);
		searchMap.put("status", status);
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = companyTradeDao.getCountByParams(searchMap);
		List<CompanyTrade> list = companyTradeDao.getListByParams(searchMap);
		
		List<CompanyTradeVO> voList = new ArrayList<CompanyTradeVO>();
		for(CompanyTrade ct : list){
			CompanyTradeVO vo = new CompanyTradeVO(ct);
			
			Company c = this.companyDao.get(ct.getCompany());
			if(c != null){
				vo.setCompanyName(c.getName());
				vo.setCompanyCode(c.getCode());
			}
			
			if(ct.getOperator() != null){
				Admin operator = this.adminDao.get(ct.getOperator());
				if(operator != null){
					vo.setOperatorName(operator.getRealname());
				}
			}
			
			CompanyChannel cch = this.companyChannelDao.get(ct.getCompanyChannel());
			if(cch != null){
				vo.setBalance(cch.getBalance());
				vo.setBalanceLock(cch.getBalanceLock());
			}
			
			if(ct.getCompanyBankcard() != null){
				CompanyBankcard cb = this.companyBankcardDao.get(ct.getCompanyBankcard());
				if(cb != null){
					vo.setCompanyBankcardNum(cb.getAccountNum());
					vo.setCompanyBankcardArea(cb.getArea());
					vo.setCompanyBankcardBranchBank(cb.getBranchBank());
					vo.setCompanyBankcardBankName(cb.getBank());
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
    	
		CompanyTrade ct = companyTradeDao.get(uuid);
		if (ct == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		CompanyTradeVO vo = new CompanyTradeVO(ct);
		
		Company c = this.companyDao.get(ct.getCompany());
		if(c != null){
			vo.setCompanyName(c.getName());
			vo.setCompanyCode(c.getCode());
		}
		
		if(ct.getOperator() != null){
			Admin operator = this.adminDao.get(ct.getOperator());
			if(operator != null){
				vo.setOperatorName(operator.getRealname());
			}
		}
		CompanyChannel cch = this.companyChannelDao.get(ct.getCompanyChannel());
		if(cch != null){
			vo.setBalance(cch.getBalance());
			vo.setBalanceLock(cch.getBalanceLock());
		}
		
		if(ct.getCompanyBankcard() != null){
			CompanyBankcard cb = this.companyBankcardDao.get(ct.getCompanyBankcard());
			if(cb != null){
				vo.setCompanyBankcardNum(cb.getAccountNum());
				vo.setCompanyBankcardArea(cb.getArea());
				vo.setCompanyBankcardBranchBank(cb.getBranchBank());
				vo.setCompanyBankcardBankName(cb.getBank());
			}
		}
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		CompanyTrade companyTrade = (CompanyTrade) paramsMap.get("companyTrade");
		
		if(!CompanyTradeType.RECHARGE.equals(companyTrade.getType()) && !CompanyTradeType.WITHDRAW.equals(companyTrade.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("类型错误！");
			return;
		}
		
		Company company = this.companyDao.get(companyTrade.getCompany());
		if(company == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("company not exist !");
			return;
		}
		
		CompanyChannel cch = this.companyChannelDao.get(companyTrade.getCompanyChannel());
		if(cch == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("company channel not exist !");
			return;
		}
		
		if(!Utlity.checkStringNull(companyTrade.getCompanyBankcard())){
			CompanyBankcard cb = this.companyBankcardDao.get(companyTrade.getCompanyBankcard());
			if(cb == null || !cb.getCompany().equals(companyTrade.getCompany())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("company bankcard not exist !");
				return;
			}
			if(!CompanyBankcardStatus.NORMAL.equals(cb.getStatus())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("company bankcard disabled !");
				return;
			}
		}else{
			companyTrade.setCompanyBankcard(null);
		}
		
		companyTrade.setUuid(UUID.randomUUID().toString());
		if(CompanyTradeType.RECHARGE.equals(companyTrade.getType())){
			companyTrade.setOrderNum(Utlity.getOrderNum(Utlity.BILL_ROLE_COMPANY,Utlity.CHANNEL_TEADE_COMPANY,Utlity.BILL_TYPE_RECHARGE));
			BigDecimal poundage = BigDecimal.ZERO;
			if(company.getRechargePoundageRate() != null){
				poundage = companyTrade.getTotalAmount().multiply(company.getRechargePoundageRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
			}else if(company.getRechargePoundage() != null){
				poundage = company.getRechargePoundage();
			}
			companyTrade.setPoundage(poundage);
		}else{
			companyTrade.setOrderNum(Utlity.getOrderNum(Utlity.BILL_ROLE_COMPANY,Utlity.CHANNEL_TEADE_COMPANY,Utlity.BILL_TYPE_WITHDRAW));
			BigDecimal poundage = BigDecimal.ZERO;
			if(company.getWithdrawPoundageRate() != null){
				poundage = companyTrade.getTotalAmount().multiply(company.getWithdrawPoundageRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
			}else if(company.getWithdrawPoundage() != null){
				poundage = company.getWithdrawPoundage();
			}
			companyTrade.setPoundage(poundage);
		}
		companyTrade.setAmount(companyTrade.getTotalAmount().subtract(companyTrade.getPoundage()));
		companyTrade.setStatus(CompanyTradeStatus.CHECKING);
		companyTrade.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		List<String> errorList = new ArrayList<String>();
		locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
			try {
				this.companyTradeDao.insertTrade(companyTrade);
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
	public void check(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	Admin admin = (Admin)paramsMap.get("admin");
		
		CompanyTrade ct = companyTradeDao.get(uuid);
		if(ct == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		if(!CompanyTradeStatus.FAIL.equals(ct.getStatus()) && !CompanyTradeStatus.CHECKING.equals(ct.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data can not process !");
			return;
		}
		
		ct.setOperator(admin.getUuid());
		try {
			this.companyTradeDao.processOrder(ct, CompanyTradeStatus.CHECKED);
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
		
		CompanyTrade ct = companyTradeDao.get(uuid);
		if(ct == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		if(!CompanyTradeStatus.CHECKING.equals(ct.getStatus()) && !CompanyTradeStatus.CHECKED.equals(ct.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data can not process !");
			return;
		}
		
		ct.setFailReason(reason);
		ct.setOperator(admin.getUuid());
		try {
			this.companyTradeDao.processOrder(ct, CompanyTradeStatus.FAIL);
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
		
		CompanyTrade ct = companyTradeDao.get(uuid);
		if(ct == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		if(!CompanyTradeStatus.CHECKED.equals(ct.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data can not process !");
			return;
		}
		
		ct.setOperator(admin.getUuid());
		ct.setProof(proof);
		
		List<String> errorList = new ArrayList<String>();
		locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
			try {
				this.companyTradeDao.processOrder(ct, CompanyTradeStatus.SUCCESS);
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
	public void close(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	Admin admin = (Admin)paramsMap.get("admin");
		
		CompanyTrade ct = companyTradeDao.get(uuid);
		if(ct == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		if(!CompanyTradeStatus.FAIL.equals(ct.getStatus()) && !CompanyTradeStatus.CHECKING.equals(ct.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data can not process !");
			return;
		}
		
		ct.setOperator(admin.getUuid());
		
		List<String> errorList = new ArrayList<String>();
		locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
			try {
				this.companyTradeDao.processOrder(ct, CompanyTradeStatus.CLOSE);
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
