/**
 * 
 */
package cn.product.payment.service.store.impl;

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
import cn.product.payment.dao.BankDao;
import cn.product.payment.dao.CompanyAccountDao;
import cn.product.payment.dao.CompanyBankcardDao;
import cn.product.payment.dao.CompanyDao;
import cn.product.payment.dao.CompanyTradeDao;
import cn.product.payment.entity.Bank;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.CompanyAccount;
import cn.product.payment.entity.CompanyAdmin;
import cn.product.payment.entity.CompanyBankcard;
import cn.product.payment.entity.CompanyBankcard.CompanyBankcardStatus;
import cn.product.payment.entity.CompanyTrade;
import cn.product.payment.entity.CompanyTrade.CompanyTradeStatus;
import cn.product.payment.entity.CompanyTrade.CompanyTradeType;
import cn.product.payment.locker.Locker;
import cn.product.payment.locker.ZkCuratorLocker;
import cn.product.payment.service.store.StoreCompanyTradeService;
import cn.product.payment.util.Utlity;
import cn.product.payment.util.api.PaymentException;
import cn.product.payment.vo.store.CompanyTradeVO;

/**
 * 商户与平台交易
 * @author Administrator
 *
 */
@Service("storeCompanyTradeService")
public class StoreCompanyTradeServiceImpl implements StoreCompanyTradeService {
 
	@Autowired
	private CompanyTradeDao companyTradeDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CompanyAccountDao companyAccountDao;

	@Autowired
	private CompanyBankcardDao companyBankcardDao;
	
	@Autowired
	private BankDao bankDao;
	
	@Autowired
    private Locker locker;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String orderNum = paramsMap.get("orderNum") == null ? null : paramsMap.get("orderNum").toString();
		String type = paramsMap.get("type") == null ? null : paramsMap.get("type").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		String starttime = paramsMap.get("starttime") == null ? null : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? null : paramsMap.get("endtime").toString();
		CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", admin.getCompany());
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
			
			//商户
			Company c = this.companyDao.get(ct.getCompany());
			if(c != null){
				vo.setCompanyName(c.getName());
				vo.setCompanyCode(c.getCode());
			}
			
			//商户账户
			CompanyAccount ca = this.companyAccountDao.get(ct.getCompany());
			if(ca != null){
				vo.setCompanyBalance(ca.getBalance());
				vo.setCompanyBalanceLock(ca.getBalanceLock());
			}
			
			//商户银行卡
			if(ct.getCompanyBankcard() != null){
				CompanyBankcard cb = this.companyBankcardDao.get(ct.getCompanyBankcard());
				if(cb != null){
					vo.setCompanyBankcardNum(cb.getAccountNum());
					vo.setCompanyBankcardArea(cb.getArea());
					vo.setCompanyBankcardBranchBank(cb.getBranchBank());
					Bank bank = this.bankDao.get(cb.getBank());
					if(bank != null){
						vo.setCompanyBankcardBankName(bank.getName());
					}
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
    	CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
    	
		CompanyTrade ct = companyTradeDao.get(uuid);
		if (ct == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		if(!ct.getCompany().equals(admin.getCompany())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("无法查询！");
			return;
		}
		CompanyTradeVO vo = new CompanyTradeVO(ct);
		
		//商户
		Company c = this.companyDao.get(ct.getCompany());
		if(c != null){
			vo.setCompanyName(c.getName());
			vo.setCompanyCode(c.getCode());
		}
		
		//商户账户
		CompanyAccount ca = this.companyAccountDao.get(ct.getCompany());
		if(ca != null){
			vo.setCompanyBalance(ca.getBalance());
			vo.setCompanyBalanceLock(ca.getBalanceLock());
		}
		
		//商户银行卡
		if(ct.getCompanyBankcard() != null){
			CompanyBankcard cb = this.companyBankcardDao.get(ct.getCompanyBankcard());
			if(cb != null){
				vo.setCompanyBankcardNum(cb.getAccountNum());
				vo.setCompanyBankcardArea(cb.getArea());
				vo.setCompanyBankcardBranchBank(cb.getBranchBank());
				Bank bank = this.bankDao.get(cb.getBank());
				if(bank != null){
					vo.setCompanyBankcardBankName(bank.getName());
				}
			}
		}
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	/**
	 * 商户向平台充值
	 */
	@Override
	public void recharge(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		CompanyTrade companyTrade = (CompanyTrade) paramsMap.get("companyTrade");
		CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
		
		companyTrade.setUuid(UUID.randomUUID().toString());
		companyTrade.setCompany(admin.getCompany());
		companyTrade.setType(CompanyTradeType.RECHARGE);
		
		//商户
		Company company = this.companyDao.get(companyTrade.getCompany());
		if(company == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商户不存在");
			return;
		}
		
		//商户银行卡
		CompanyBankcard cb = this.companyBankcardDao.get(companyTrade.getCompanyBankcard());
		if(cb == null || !cb.getCompany().equals(admin.getCompany())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("银行卡不存在");
			return;
		}
		if(!CompanyBankcardStatus.NORMAL.equals(cb.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("银行卡不可用");
			return;
		}
		
		//订单号
		companyTrade.setOrderNum(Utlity.getOrderNum(Utlity.BILL_ROLE_COMPANY,Utlity.CHANNEL_TEADE_COMPANY,Utlity.BILL_TYPE_RECHARGE));
		
		//手续费
		BigDecimal poundage = BigDecimal.ZERO;
		if(company.getRechargePoundageRate() != null){
			//比率手续费
			poundage = companyTrade.getTotalAmount().multiply(company.getRechargePoundageRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
		}else if(company.getRechargePoundage() != null){
			//固定手续费
			poundage = company.getRechargePoundage();
		}
		companyTrade.setPoundage(poundage);
		
		companyTrade.setAmount(companyTrade.getTotalAmount().subtract(companyTrade.getPoundage()));
		companyTrade.setStatus(CompanyTradeStatus.CHECKING);
		companyTrade.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		//入库
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
			//操作错误
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(errorList.get(0));
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
	
	/**
	 * 商户向平台提现
	 */
	@Override
	public void withdraw(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		CompanyTrade companyTrade = (CompanyTrade) paramsMap.get("companyTrade");
		CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
		
		companyTrade.setUuid(UUID.randomUUID().toString());
		companyTrade.setCompany(admin.getCompany());
		companyTrade.setType(CompanyTradeType.WITHDRAW);
		
		//商户
		Company company = this.companyDao.get(companyTrade.getCompany());
		if(company == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商户不存在");
			return;
		}
		
		//商户银行卡
		CompanyBankcard cb = this.companyBankcardDao.get(companyTrade.getCompanyBankcard());
		if(cb == null || !cb.getCompany().equals(admin.getCompany())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("银行卡不存在");
			return;
		}
		if(!CompanyBankcardStatus.NORMAL.equals(cb.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("银行卡不可用");
			return;
		}
		
		//生成订单号
		companyTrade.setOrderNum(Utlity.getOrderNum(Utlity.BILL_ROLE_COMPANY,Utlity.CHANNEL_TEADE_COMPANY,Utlity.BILL_TYPE_WITHDRAW));
		
		//手续费
		BigDecimal poundage = BigDecimal.ZERO;
		if(company.getWithdrawPoundageRate() != null){
			//比率手续费
			poundage = companyTrade.getTotalAmount().multiply(company.getWithdrawPoundageRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
		}else if(company.getWithdrawPoundage() != null){
			//固定手续费
			poundage = company.getWithdrawPoundage();
		}
		companyTrade.setPoundage(poundage);
		
		companyTrade.setAmount(companyTrade.getTotalAmount().subtract(companyTrade.getPoundage()));
		companyTrade.setStatus(CompanyTradeStatus.CHECKING);
		companyTrade.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		//入库
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
			//操作错误
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(errorList.get(0));
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
	
	/**
	 * 关闭订单
	 */
	@Override
	public void close(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
		
		CompanyTrade ct = companyTradeDao.get(uuid);
		if(ct == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		if(!ct.getCompany().equals(admin.getCompany())){
			//非本商户订单
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("无法操作！");
			return;
		}
		if(!CompanyTradeStatus.FAIL.equals(ct.getStatus()) && !CompanyTradeStatus.CHECKING.equals(ct.getStatus())){
			//当前状态不能关闭
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该记录无法处理！");
			return;
		}
		
		//关闭
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
			//关闭错误
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(errorList.get(0));
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
	
	/**
	 * 重试
	 */
	@Override
	public void retry(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
		
		CompanyTrade ct = companyTradeDao.get(uuid);
		if(ct == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		if(!ct.getCompany().equals(admin.getCompany())){
			//非本商户订单
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("无法操作！");
			return;
		}
		if(!CompanyTradeStatus.FAIL.equals(ct.getStatus())){
			//非错误订单不能重试
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该记录无法处理！");
			return;
		}
		
		//重试
		List<String> errorList = new ArrayList<String>();
		locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
			try {
				this.companyTradeDao.processOrder(ct, CompanyTradeStatus.CHECKING);
			} catch (PaymentException e) {
				errorList.add(e.getMessage());
				e.printStackTrace();
			}
		});
		if(errorList.size() > 0){
			//操作错误
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(errorList.get(0));
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
}
