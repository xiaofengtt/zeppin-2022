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
import cn.product.payment.dao.BankDao;
import cn.product.payment.dao.CompanyAccountDao;
import cn.product.payment.dao.CompanyBankcardDao;
import cn.product.payment.dao.CompanyDao;
import cn.product.payment.dao.CompanyTradeDao;
import cn.product.payment.entity.Admin;
import cn.product.payment.entity.Bank;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.CompanyAccount;
import cn.product.payment.entity.CompanyBankcard;
import cn.product.payment.entity.CompanyBankcard.CompanyBankcardStatus;
import cn.product.payment.entity.CompanyTrade;
import cn.product.payment.entity.CompanyTrade.CompanyTradeStatus;
import cn.product.payment.entity.CompanyTrade.CompanyTradeType;
import cn.product.payment.locker.Locker;
import cn.product.payment.locker.ZkCuratorLocker;
import cn.product.payment.service.system.SystemCompanyTradeService;
import cn.product.payment.util.Utlity;
import cn.product.payment.util.api.PaymentException;
import cn.product.payment.vo.system.CompanyTradeVO;

/**
 * 平台与商户交易
 */
@Service("systemCompanyTradeService")
public class SystemCompanyTradeServiceImpl implements SystemCompanyTradeService {
 
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
			
			//处理人（平台管理员）
			if(ct.getOperator() != null){
				Admin operator = this.adminDao.get(ct.getOperator());
				if(operator != null){
					vo.setOperatorName(operator.getRealname());
				}
			}
			
			//商户银行卡
			if(ct.getCompanyBankcard() != null){
				CompanyBankcard cb = this.companyBankcardDao.get(ct.getCompanyBankcard());
				if(cb != null){
					vo.setCompanyBankcardNum(cb.getAccountNum());
					vo.setCompanyBankcardArea(cb.getArea());
					vo.setCompanyBankcardBranchBank(cb.getBranchBank());
					//银行
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
    	
		CompanyTrade ct = companyTradeDao.get(uuid);
		if (ct == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
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
		
		//处理人（平台管理员）
		if(ct.getOperator() != null){
			Admin operator = this.adminDao.get(ct.getOperator());
			if(operator != null){
				vo.setOperatorName(operator.getRealname());
			}
		}
		
		//商户银行卡
		if(ct.getCompanyBankcard() != null){
			CompanyBankcard cb = this.companyBankcardDao.get(ct.getCompanyBankcard());
			if(cb != null){
				vo.setCompanyBankcardNum(cb.getAccountNum());
				vo.setCompanyBankcardArea(cb.getArea());
				vo.setCompanyBankcardBranchBank(cb.getBranchBank());
				//银行
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
	 * 平台管理员添加交易记录
	 */
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
			result.setMessage("商户不存在");
			return;
		}
		
		if(!Utlity.checkStringNull(companyTrade.getCompanyBankcard())){
			CompanyBankcard cb = this.companyBankcardDao.get(companyTrade.getCompanyBankcard());
			if(cb == null || !cb.getCompany().equals(companyTrade.getCompany())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("商户银行卡不存在");
				return;
			}
			if(!CompanyBankcardStatus.NORMAL.equals(cb.getStatus())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("商户银行卡不可用");
				return;
			}
		}else{
			companyTrade.setCompanyBankcard(null);
		}
		
		companyTrade.setUuid(UUID.randomUUID().toString());
		if(CompanyTradeType.RECHARGE.equals(companyTrade.getType())){
			//商户充值
			//创建订单号
			companyTrade.setOrderNum(Utlity.getOrderNum(Utlity.BILL_ROLE_COMPANY,Utlity.CHANNEL_TEADE_COMPANY,Utlity.BILL_TYPE_RECHARGE));
			//手续费
			BigDecimal poundage = BigDecimal.ZERO;
			if(company.getRechargePoundageRate() != null){
				//比率手续费
				poundage = companyTrade.getTotalAmount().multiply(company.getRechargePoundageRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
			}else if(company.getRechargePoundage() != null){
				//定额手续费
				poundage = company.getRechargePoundage();
			}
			companyTrade.setPoundage(poundage);
		}else{
			//商户提现
			companyTrade.setOrderNum(Utlity.getOrderNum(Utlity.BILL_ROLE_COMPANY,Utlity.CHANNEL_TEADE_COMPANY,Utlity.BILL_TYPE_WITHDRAW));
			//手续费
			BigDecimal poundage = BigDecimal.ZERO;
			if(company.getWithdrawPoundageRate() != null){
				//比例手续费
				poundage = companyTrade.getTotalAmount().multiply(company.getWithdrawPoundageRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
			}else if(company.getWithdrawPoundage() != null){
				//定额手续费
				poundage = company.getWithdrawPoundage();
			}
			companyTrade.setPoundage(poundage);
		}
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
			//入库错误
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(errorList.get(0));
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("添加成功！");
	}
	
	/**
	 * 平台管理员审核易记录
	 */
	@Override
	public void check(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	Admin admin = (Admin)paramsMap.get("admin");
		
		CompanyTrade ct = companyTradeDao.get(uuid);
		if(ct == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		//处理失败和待审核的可以审核
		if(!CompanyTradeStatus.FAIL.equals(ct.getStatus()) && !CompanyTradeStatus.CHECKING.equals(ct.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该记录无法处理！");
			return;
		}
		
		ct.setOperator(admin.getUuid());
		//入库
		try {
			this.companyTradeDao.processOrder(ct, CompanyTradeStatus.CHECKED);
		} catch (PaymentException e) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(e.getMessage());
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("审核成功！");
	}
	
	/**
	 * 平台管理员设为处理失败
	 */
	@Override
	public void fail(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String reason = paramsMap.get("reason").toString();
    	Admin admin = (Admin)paramsMap.get("admin");
		
		CompanyTrade ct = companyTradeDao.get(uuid);
		if(ct == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		//待审核和已审核的可以设为失败
		if(!CompanyTradeStatus.CHECKING.equals(ct.getStatus()) && !CompanyTradeStatus.CHECKED.equals(ct.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该记录无法处理！");
			return;
		}
		
		ct.setFailReason(reason);
		ct.setOperator(admin.getUuid());

		//入库
		try {
			this.companyTradeDao.processOrder(ct, CompanyTradeStatus.FAIL);
		} catch (PaymentException e) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(e.getMessage());
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
	
	/**
	 * 平台管理员设为处理成功
	 */
	@Override
	public void success(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String proof = paramsMap.get("proof").toString();
    	Admin admin = (Admin)paramsMap.get("admin");
		
		CompanyTrade ct = companyTradeDao.get(uuid);
		if(ct == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		//已审核的处理
		if(!CompanyTradeStatus.CHECKED.equals(ct.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该记录无法处理！");
			return;
		}
		
		ct.setOperator(admin.getUuid());
		ct.setProof(proof);
		
		//入库
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
			//入库失败
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(errorList.get(0));
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}

	/**
	 * 平台管理员关闭订单
	 */
	@Override
	public void close(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	Admin admin = (Admin)paramsMap.get("admin");
		
		CompanyTrade ct = companyTradeDao.get(uuid);
		if(ct == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		//处理失败和待处理的可关闭
		if(!CompanyTradeStatus.FAIL.equals(ct.getStatus()) && !CompanyTradeStatus.CHECKING.equals(ct.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该记录无法处理！");
			return;
		}
		
		ct.setOperator(admin.getUuid());
		
		//入库
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
			//入库失败
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage(errorList.get(0));
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("关闭成功！");
	}
}
