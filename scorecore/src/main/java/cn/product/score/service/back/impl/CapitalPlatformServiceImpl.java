package cn.product.score.service.back.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.CapitalPlatformDao;
import cn.product.score.entity.CapitalPlatform;
import cn.product.score.entity.CapitalPlatform.CapitalPlatformStatus;
import cn.product.score.service.back.CapitalPlatformService;

@Service("capitalPlatformService")
public class CapitalPlatformServiceImpl implements CapitalPlatformService{
	
	@Autowired
	private CapitalPlatformDao capitalPlatformDao;

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		CapitalPlatform capitalPlatform = capitalPlatformDao.get(uuid);
		if(capitalPlatform == null || CapitalPlatformStatus.DELETE.equals(capitalPlatform.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道不存在");
			return;
		}
		result.setData(capitalPlatform);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String transType = paramsMap.get("transType") == null ? "" : paramsMap.get("transType").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("name", name);
		paramsls.put("transType", transType);
		paramsls.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			paramsls.put("offSet", (pageNum-1)*pageSize);
			paramsls.put("pageSize", pageSize);
		}
		paramsls.put("sort", sort);
		
		Integer totalCount = capitalPlatformDao.getCountByParams(paramsls);
		List<CapitalPlatform> capitalPlatformList = capitalPlatformDao.getListByParams(paramsls);
		
		result.setData(capitalPlatformList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		CapitalPlatform capitalPlatform = (CapitalPlatform) paramsMap.get("capitalPlatform");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		capitalPlatform.setUuid(UUID.randomUUID().toString());
		capitalPlatform.setCreator(admin);
		capitalPlatform.setCreatetime(new Timestamp(System.currentTimeMillis()));
		capitalPlatformDao.insert(capitalPlatform);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("添加成功！");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		CapitalPlatform capitalPlatform = (CapitalPlatform) paramsMap.get("capitalPlatform");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		CapitalPlatform cp = capitalPlatformDao.get(capitalPlatform.getUuid());
		if(cp == null || CapitalPlatformStatus.DELETE.equals(cp.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道不存在");
			return;
		}
		cp.setName(capitalPlatform.getName());
		cp.setRemark(capitalPlatform.getRemark());
		cp.setStatus(capitalPlatform.getStatus());
		cp.setSort(capitalPlatform.getSort());
		cp.setCreator(admin);
		cp.setCreatetime(new Timestamp(System.currentTimeMillis()));
		capitalPlatformDao.update(cp);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}

	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		CapitalPlatform cp = capitalPlatformDao.get(uuid);
		if(cp == null || CapitalPlatformStatus.DELETE.equals(cp.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道不存在");
			return;
		}
		
		cp.setStatus(status);
		
		capitalPlatformDao.update(cp);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}
	
}
