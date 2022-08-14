/**
 * 
 */
package cn.product.payment.service.store.impl;

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
import cn.product.payment.dao.CompanyBankcardDao;
import cn.product.payment.entity.Bank;
import cn.product.payment.entity.CompanyAdmin;
import cn.product.payment.entity.CompanyBankcard;
import cn.product.payment.entity.CompanyBankcard.CompanyBankcardStatus;
import cn.product.payment.service.store.StoreCompanyBankcardService;
import cn.product.payment.vo.store.CompanyBankcardVO;

/**
 * 商户银行卡
 * @author Administrator
 *
 */
@Service("storeCompanyBankcardService")
public class StoreCompanyBankcardServiceImpl implements StoreCompanyBankcardService {

	@Autowired
	private CompanyBankcardDao companyBankcardDao;
	
	@Autowired
	private BankDao bankDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String status = paramsMap.get("status") == null ? null : paramsMap.get("status").toString();
		CompanyAdmin admin = (CompanyAdmin)paramsMap.get("companyAdmin");
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", admin.getCompany());
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
			
			//银行信息
			Bank bank = this.bankDao.get(cb.getBank());
			if(bank != null){
				vo.setBankName(bank.getName());
				vo.setBankLogo(bank.getLogo());
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
    	
		CompanyBankcard cb = companyBankcardDao.get(uuid);
		if (cb == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("银行卡不存在！");
			return;
		}
		if(!cb.getCompany().equals(admin.getCompany())){
			//非本商户银行卡
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("无法查询！");
			return;
		}
		
		CompanyBankcardVO vo = new CompanyBankcardVO(cb);
		
		//银行信息
		Bank bank = this.bankDao.get(cb.getBank());
		if(bank != null){
			vo.setBankName(bank.getName());
			vo.setBankLogo(bank.getLogo());
		}

		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		CompanyBankcard companyBankcard = (CompanyBankcard) paramsMap.get("companyBankcard");
    	CompanyAdmin admin = (CompanyAdmin)paramsMap.get("companyAdmin");
    	
    	Bank bank = this.bankDao.get(companyBankcard.getBank());
    	if(bank == null){
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("所属银行不存在！");
			return;
    	}
    	
    	companyBankcard.setUuid(UUID.randomUUID().toString());
    	companyBankcard.setCompany(admin.getCompany());
    	companyBankcard.setStatus(CompanyBankcardStatus.NORMAL);
    	companyBankcard.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.companyBankcardDao.insert(companyBankcard);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("绑定成功！");
	}
	
	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	CompanyAdmin admin = (CompanyAdmin)paramsMap.get("companyAdmin");
    	
		CompanyBankcard cb = companyBankcardDao.get(uuid);
		if (cb == null || CompanyBankcardStatus.DELETE.equals(cb.getStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("银行卡不存在！");
			return;
		}
		if(!cb.getCompany().equals(admin.getCompany())){
			//非本商户银行卡
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("无法修改！");
			return;
		}
		
		cb.setStatus(CompanyBankcardStatus.DELETE);
		this.companyBankcardDao.update(cb);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
	
	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
    	String status = paramsMap.get("status").toString();
    	CompanyAdmin admin = (CompanyAdmin)paramsMap.get("companyAdmin");
    	
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
		if(!cb.getCompany().equals(admin.getCompany())){
			//非本商户银行卡
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("无法修改！");
			return;
		}
		
		cb.setStatus(status);
		this.companyBankcardDao.update(cb);

		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
}
