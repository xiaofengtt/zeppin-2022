package cn.product.treasuremall.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.AdminDao;
import cn.product.treasuremall.dao.CapitalAccountDao;
import cn.product.treasuremall.dao.CapitalPlatformDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.entity.CapitalAccount;
import cn.product.treasuremall.entity.CapitalAccount.CapitalAccountStatus;
import cn.product.treasuremall.entity.CapitalPlatform;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.service.back.CapitalAccountService;
import cn.product.treasuremall.vo.back.CapitalAccountVO;

@Service("capitalAccountService")
public class CapitalAccountServiceImpl implements CapitalAccountService{
	
	@Autowired
	private CapitalAccountDao capitalAccountDao;
	
	@Autowired
	private CapitalPlatformDao capitalPlatformDao;

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private ResourceDao resourceDao;

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
		Resource logo = resourceDao.get(ca.getLogo());
		if(logo != null){
			cavo.setLogoUrl(logo.getUrl());
		}
		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
		if(cp != null){
			cavo.setCapitalPlatformName(cp.getName());
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
			Resource logo = resourceDao.get(ca.getLogo());
			if(logo != null){
				cavo.setLogoUrl(logo.getUrl());
			}
			CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
			if(cp != null){
				cavo.setCapitalPlatformName(cp.getName());
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
		
		CapitalPlatform cp = this.capitalPlatformDao.get(capitalAccount.getCapitalPlatform());
		if(cp == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("充值渠道不存在");
			return;
		}
		
		capitalAccount.setUuid(UUID.randomUUID().toString());
		capitalAccount.setType(cp.getType());
		capitalAccount.setTransType(cp.getTransType());
		capitalAccount.setCreator(admin);
		capitalAccount.setCreatetime(new Timestamp(System.currentTimeMillis()));
		capitalAccountDao.insertWithStatistics(capitalAccount);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("添加成功！");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		
		Map<String, Object> paramsMap = params.getParams();
		CapitalAccount capitalAccount = (CapitalAccount) paramsMap.get("capitalAccount");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		CapitalAccount ca = capitalAccountDao.get(capitalAccount.getUuid());
		if(ca == null || CapitalAccountStatus.DELETE.equals(ca.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("账号不存在");
			return;
		}
		
		ca.setName(capitalAccount.getName());
		ca.setAccountNum(capitalAccount.getAccountNum());
		ca.setData(capitalAccount.getData());
		ca.setMin(capitalAccount.getMin());
		ca.setMax(capitalAccount.getMax());
		ca.setDailyMax(capitalAccount.getDailyMax());
		ca.setTotalMax(capitalAccount.getTotalMax());
		ca.setPoundageRate(capitalAccount.getPoundageRate());
		ca.setRemark(capitalAccount.getRemark());
		ca.setStatus(capitalAccount.getStatus());
		ca.setSort(capitalAccount.getSort());
		ca.setCreator(admin);
		ca.setCreatetime(new Timestamp(System.currentTimeMillis()));
		ca.setLogo(capitalAccount.getLogo());
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
