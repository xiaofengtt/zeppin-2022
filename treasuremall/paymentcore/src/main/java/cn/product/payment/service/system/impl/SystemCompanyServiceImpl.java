/**
 * 
 */
package cn.product.payment.service.system.impl;

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
import cn.product.payment.dao.CompanyAccountDao;
import cn.product.payment.dao.CompanyDao;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.Company.CompanyStatus;
import cn.product.payment.entity.CompanyAccount;
import cn.product.payment.service.system.SystemCompanyService;
import cn.product.payment.util.PaymentUtil;
import cn.product.payment.util.Utlity;
import cn.product.payment.vo.system.CompanyVO;

/**
 * 商户
 */
@Service("systemCompanyService")
public class SystemCompanyServiceImpl implements SystemCompanyService {

	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CompanyAccountDao companyAccountDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String code = paramsMap.get("code") == null ? null : paramsMap.get("code").toString();
		String name = paramsMap.get("name") == null ? null : paramsMap.get("name").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		String type = paramsMap.get("type") == null ? null : paramsMap.get("type").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);
		searchMap.put("code", code);
		searchMap.put("type", type);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = companyDao.getCountByParams(searchMap);
		List<Company> list = companyDao.getListByParams(searchMap);
		
		List<CompanyVO> voList = new ArrayList<CompanyVO>();
		for(Company company : list){
			CompanyVO vo = new CompanyVO(company);
			
			//商户账户
			CompanyAccount ca = this.companyAccountDao.get(company.getUuid());
			vo.setBalance(ca.getBalance());
			vo.setBalanceLock(ca.getBalanceLock());
			
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
    	
		Company company = companyDao.get(uuid);
		if (company == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		CompanyVO vo = new CompanyVO(company);
		
		//商户账户
		CompanyAccount ca = this.companyAccountDao.get(company.getUuid());
		vo.setBalance(ca.getBalance());
		vo.setBalanceLock(ca.getBalanceLock());
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	/**
	 * 开通商户
	 */
	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Company company = (Company) paramsMap.get("company");
		
		if(!CompanyStatus.NORMAL.equals(company.getStatus()) && !CompanyStatus.DISABLE.equals(company.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("状态错误！");
			return;
		}
		
		if(!Utlity.checkStringNull(company.getCompanyPrivate()) && !Utlity.checkStringNull(company.getCompanyPublic())){
			//商户公钥和商户私钥都不为空
			if(!PaymentUtil.checkKeys(company.getCompanyPrivate(), company.getCompanyPublic())){
				//商户公钥和商户私钥不匹配
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("商户秘钥不匹配！");
				return;
			}
		}
		
		if(company.getRechargePoundage() == null && company.getRechargePoundageRate() == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("注资手续费不能为空！");
			return;
		}
		
		if(company.getRechargePoundage() != null && company.getRechargePoundageRate() != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("注资手续费暂时只能选择一种方式！");
			return;
		}
		
		if(company.getWithdrawPoundage() == null && company.getWithdrawPoundageRate() == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("提现手续费不能为空！");
			return;
		}
		
		if(company.getWithdrawPoundage() != null && company.getWithdrawPoundageRate() != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("提现手续费暂时只能选择一种方式！");
			return;
		}
		
		company.setUuid(UUID.randomUUID().toString());
		//生成平台公钥与平台私钥
		Map<String, String> keyResult = companyDao.getCompanyKeys(company);
		company.setSystemPrivate(keyResult.get("privateKey"));
		company.setSystemPublic(keyResult.get("publicKey"));
		
		//校验平台公钥与平台私钥
		if(!PaymentUtil.checkKeys(company.getSystemPrivate(), company.getSystemPublic())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("创建出错,请稍后再试！");
			return;
		}
		
		company.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.companyDao.insertCompany(company);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("创建成功！");
	}
	
	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Company company = (Company) paramsMap.get("company");
		
		if(!CompanyStatus.NORMAL.equals(company.getStatus()) && !CompanyStatus.DISABLE.equals(company.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("状态错误！");
			return;
		}
		
		if(!Utlity.checkStringNull(company.getCompanyPrivate()) && !Utlity.checkStringNull(company.getCompanyPublic())){
			//商户公钥和商户私钥都不为空
			if(!PaymentUtil.checkKeys(company.getCompanyPrivate(), company.getCompanyPublic())){
				//商户公钥和商户私钥不匹配
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("商户秘钥不匹配！");
				return;
			}
		}
		
		if(company.getRechargePoundage() == null && company.getRechargePoundageRate() == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商户注资手续费不能为空！");
			return;
		}
		
		if(company.getRechargePoundage() != null && company.getRechargePoundageRate() != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商户注资手续费暂时只能选择一种方式！");
			return;
		}
		
		if(company.getWithdrawPoundage() == null && company.getWithdrawPoundageRate() == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商户提现手续费不能为空！");
			return;
		}
		
		if(company.getWithdrawPoundage() != null && company.getWithdrawPoundageRate() != null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商户提现手续费暂时只能选择一种方式！");
			return;
		}
		
		Company c = companyDao.get(company.getUuid());
		if (c == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		c.setName(company.getName());
		c.setCompanyPrivate(company.getCompanyPrivate());
		c.setCompanyPublic(company.getCompanyPublic());
		c.setWhiteUrl(company.getWhiteUrl());
		c.setRechargePoundage(company.getRechargePoundage());
		c.setRechargePoundageRate(company.getRechargePoundageRate());
		c.setWithdrawPoundage(company.getWithdrawPoundage());
		c.setWithdrawPoundageRate(company.getWithdrawPoundageRate());
		c.setStatus(company.getStatus());
		this.companyDao.update(c);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}
	
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String status = paramsMap.get("status").toString();
    	
		if(!CompanyStatus.NORMAL.equals(status) && !CompanyStatus.DISABLE.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("状态错误！");
			return;
		}
		
		Company company = companyDao.get(uuid);
		if (company == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("记录不存在！");
			return;
		}
		
		company.setStatus(status);
		this.companyDao.update(company);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
}
