package cn.product.score.service.back.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.AdminDao;
import cn.product.score.dao.CapitalAccountDao;
import cn.product.score.dao.CapitalPlatformDao;
import cn.product.score.entity.Admin;
import cn.product.score.entity.CapitalAccount;
import cn.product.score.entity.CapitalAccount.CapitalAccountStatus;
import cn.product.score.entity.CapitalPlatform;
import cn.product.score.entity.CapitalPlatform.CapitalPlatformType;
import cn.product.score.service.back.CapitalAccountService;
import cn.product.score.util.JSONUtils;
import cn.product.score.util.Utlity;
import cn.product.score.vo.back.CapitalAccountVO;

@Service("capitalAccountService")
public class CapitalAccountServiceImpl implements CapitalAccountService{
	
	@Autowired
	private CapitalAccountDao capitalAccountDao;
	
	@Autowired
	private CapitalPlatformDao capitalPlatformDao;

	@Autowired
	private AdminDao adminDao;

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		CapitalAccount ca = capitalAccountDao.get(uuid);
		if(ca == null || CapitalAccountStatus.DELETE.equals(ca.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("账号不存在");
			return;
		}
		
		CapitalAccountVO cavo = new CapitalAccountVO(ca);
		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
		if(cp != null){
			cavo.setCapitalPlatformName(cp.getName());
			cavo.setCapitalPlatformType(cp.getType());
		}
		Admin creator = this.adminDao.get(ca.getCreator());
		if(creator != null){
			cavo.setCreatorName(creator.getRealname());
		}
		result.setData(cavo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String capitalPlatform = paramsMap.get("capitalPlatform") == null ? "" : paramsMap.get("capitalPlatform").toString();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String accountNum = paramsMap.get("accountNum") == null ? "" : paramsMap.get("accountNum").toString();
		String transType = paramsMap.get("transType") == null ? "" : paramsMap.get("transType").toString();
		Boolean auto = paramsMap.get("auto") == null ? null : Boolean.valueOf(paramsMap.get("auto").toString());
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("capitalPlatform", capitalPlatform);
		paramsls.put("name", name);
		paramsls.put("accountNum", accountNum);
		paramsls.put("transType", transType);
		paramsls.put("auto", auto);
		paramsls.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			paramsls.put("offSet", (pageNum-1)*pageSize);
			paramsls.put("pageSize", pageSize);
		}
		paramsls.put("sort", sort);
		
		Integer totalCount = capitalAccountDao.getCountByParams(paramsls);
		List<CapitalAccount> caList = capitalAccountDao.getListByParams(paramsls);
		
		List<CapitalAccountVO> voList = new ArrayList<CapitalAccountVO>();
		for(CapitalAccount ca : caList){
			CapitalAccountVO cavo = new CapitalAccountVO(ca);
			CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
			if(cp != null){
				cavo.setCapitalPlatformName(cp.getName());
				cavo.setCapitalPlatformType(cp.getType());
			}
			Admin creator = this.adminDao.get(ca.getCreator());
			if(creator != null){
				cavo.setCreatorName(creator.getRealname());
			}
			voList.add(cavo);
		}
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {

		Map<String, Object> paramsMap = params.getParams();
		CapitalAccount capitalAccount = (CapitalAccount) paramsMap.get("capitalAccount");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		String accountName = paramsMap.get("accountName") == null ? "" : paramsMap.get("accountName").toString();
		String bankName = paramsMap.get("bankName") == null ? "" : paramsMap.get("bankName").toString();
		String branchBank = paramsMap.get("branchBank") == null ? "" : paramsMap.get("branchBank").toString();
		String privateKey = paramsMap.get("privateKey") == null ? "" : paramsMap.get("privateKey").toString();
		
		CapitalPlatform cp = this.capitalPlatformDao.get(capitalAccount.getCapitalPlatform());
		if(cp == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("所属渠道不存在");
			return;
		}
		
		//微信:appid(accountnum),mch_id  支付宝对公:appid(accountnum),privateKey 银行对私:accountnum,accountname,bankname 银行对公:accountnum,accountname,bankname,brankbank
		Map<String, Object> data = new HashMap<String, Object>();
		if(CapitalPlatformType.COMPANY_BANKCARD.equals(cp.getType())){
			if(Utlity.checkStringNull(accountName)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("账户名不能为空");
				return;
			}
			if(Utlity.checkStringNull(bankName)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("所属银行不能为空");
				return;
			}
			if(Utlity.checkStringNull(branchBank)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("开户行不能为空");
				return;
			}
			data.put("accountName", accountName);
			data.put("bankName", bankName);
			data.put("branchBank", branchBank);
		}else if(CapitalPlatformType.PERSONAL_BANKCARD.equals(cp.getType())){
			if(Utlity.checkStringNull(accountName)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("账户名不能为空");
				return;
			}
			if(Utlity.checkStringNull(bankName)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("所属银行不能为空");
				return;
			}
			data.put("accountName", accountName);
			data.put("bankName", bankName);
		}else if(CapitalPlatformType.COMPANY_ALIPAY.equals(cp.getType())){
			if(Utlity.checkStringNull(privateKey)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("私钥不能为空");
				return;
			}
			data.put("privateKey", privateKey);
		}
		capitalAccount.setData(JSONUtils.obj2json(data));
		
		capitalAccount.setUuid(UUID.randomUUID().toString());
		capitalAccount.setPoundageRate(BigDecimal.ZERO);
		capitalAccount.setType(cp.getType());
		capitalAccount.setTransType(cp.getTransType());
		capitalAccount.setDailySum(BigDecimal.ZERO);
		capitalAccount.setBalance(BigDecimal.ZERO);
		capitalAccount.setCreator(admin);
		capitalAccount.setCreatetime(new Timestamp(System.currentTimeMillis()));
		capitalAccountDao.insert(capitalAccount);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("添加成功！");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		
		Map<String, Object> paramsMap = params.getParams();
		CapitalAccount capitalAccount = (CapitalAccount) paramsMap.get("capitalAccount");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		String accountName = paramsMap.get("accountName") == null ? "" : paramsMap.get("accountName").toString();
		String bankName = paramsMap.get("bankName") == null ? "" : paramsMap.get("bankName").toString();
		String branchBank = paramsMap.get("branchBank") == null ? "" : paramsMap.get("branchBank").toString();
		String privateKey = paramsMap.get("privateKey") == null ? "" : paramsMap.get("privateKey").toString();
		
		CapitalAccount ca = capitalAccountDao.get(capitalAccount.getUuid());
		if(ca == null || CapitalAccountStatus.DELETE.equals(ca.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("账号不存在");
			return;
		}
		
		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
		Map<String, Object> data = new HashMap<String, Object>();
		if(CapitalPlatformType.COMPANY_BANKCARD.equals(cp.getType())){
			if(Utlity.checkStringNull(accountName)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("账户名不能为空");
				return;
			}
			if(Utlity.checkStringNull(bankName)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("所属银行不能为空");
				return;
			}
			if(Utlity.checkStringNull(branchBank)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("开户行不能为空");
				return;
			}
			data.put("accountName", accountName);
			data.put("bankName", bankName);
			data.put("branchBank", branchBank);
		}else if(CapitalPlatformType.PERSONAL_BANKCARD.equals(cp.getType())){
			if(Utlity.checkStringNull(accountName)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("账户名不能为空");
				return;
			}
			if(Utlity.checkStringNull(bankName)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("所属银行不能为空");
				return;
			}
			data.put("accountName", accountName);
			data.put("bankName", bankName);
		}else if(CapitalPlatformType.COMPANY_ALIPAY.equals(cp.getType())){
			if(Utlity.checkStringNull(privateKey)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("私钥不能为空");
				return;
			}
			data.put("privateKey", privateKey);
		}
		ca.setData(JSONUtils.obj2json(data));
		
		ca.setName(capitalAccount.getName());
		ca.setAccountNum(capitalAccount.getAccountNum());
		ca.setMin(capitalAccount.getMin());
		ca.setMax(capitalAccount.getMax());
		ca.setDailyMax(capitalAccount.getDailyMax());
		ca.setRemark(capitalAccount.getRemark());
		ca.setStatus(capitalAccount.getStatus());
		ca.setSort(capitalAccount.getSort());
		ca.setCreator(admin);
		ca.setCreatetime(new Timestamp(System.currentTimeMillis()));
		capitalAccountDao.update(ca);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}

	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		CapitalAccount ca = capitalAccountDao.get(uuid);
		if(ca == null || CapitalAccountStatus.DELETE.equals(ca.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("账号不存在");
			return;
		}
		
		ca.setStatus(status);
		
		capitalAccountDao.update(ca);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}
	
}
