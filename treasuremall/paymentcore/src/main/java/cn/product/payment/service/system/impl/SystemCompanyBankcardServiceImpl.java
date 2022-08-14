/**
 * 
 */
package cn.product.payment.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.BankDao;
import cn.product.payment.dao.CompanyBankcardDao;
import cn.product.payment.dao.CompanyDao;
import cn.product.payment.entity.Bank;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.CompanyBankcard;
import cn.product.payment.entity.CompanyBankcard.CompanyBankcardStatus;
import cn.product.payment.service.system.SystemCompanyBankcardService;
import cn.product.payment.vo.system.CompanyBankcardVO;

/**
 * 商户银行卡
 */
@Service("systemCompanyBankcardService")
public class SystemCompanyBankcardServiceImpl implements SystemCompanyBankcardService {

	@Autowired
	private CompanyBankcardDao companyBankcardDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private BankDao bankDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String company = paramsMap.get("company") == null ? null : paramsMap.get("company").toString();
		String bank = paramsMap.get("bank") == null ? null : paramsMap.get("bank").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", company);
		searchMap.put("bank", bank);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = companyBankcardDao.getCountByParams(searchMap);
		List<CompanyBankcard> list = companyBankcardDao.getListByParams(searchMap);
		
		List<CompanyBankcardVO> voList = new ArrayList<CompanyBankcardVO>();
		for(CompanyBankcard cb : list){
			CompanyBankcardVO vo = new CompanyBankcardVO(cb);
			
			//商户
			Company c = this.companyDao.get(cb.getCompany());
			if(c != null){
				vo.setCompanyName(c.getName());
			}
			
			//银行
			Bank b = this.bankDao.get(cb.getBank());
			if(b != null){
				vo.setBankName(b.getName());
				vo.setBankLogo(b.getLogo());
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
    	
		CompanyBankcard cb = companyBankcardDao.get(uuid);
		if (cb == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("银行卡不存在！");
			return;
		}
		
		CompanyBankcardVO vo = new CompanyBankcardVO(cb);
		
		//银行
		Bank bank = this.bankDao.get(cb.getBank());
		if(bank != null){
			vo.setBankName(bank.getName());
			vo.setBankLogo(bank.getLogo());
		}
		
		//商户
		Company c = this.companyDao.get(cb.getCompany());
		if(c != null){
			vo.setCompanyName(c.getName());
		}
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String status = paramsMap.get("status").toString();
    	
		if(!CompanyBankcardStatus.NORMAL.equals(status) && !CompanyBankcardStatus.DISABLE.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("状态错误！");
			return;
		}
		
		CompanyBankcard cb = companyBankcardDao.get(uuid);
		if (cb == null || CompanyBankcardStatus.DELETE.equals(cb.getStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("银行卡不存在！");
			return;
		}
		
		cb.setStatus(status);
		this.companyBankcardDao.update(cb);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
}
