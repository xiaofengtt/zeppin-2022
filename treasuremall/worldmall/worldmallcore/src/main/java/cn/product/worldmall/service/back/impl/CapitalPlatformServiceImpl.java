package cn.product.worldmall.service.back.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.CapitalPlatformDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.entity.CapitalPlatform;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.CapitalPlatform.CapitalPlatformStatus;
import cn.product.worldmall.service.back.CapitalPlatformService;
import cn.product.worldmall.vo.back.CapitalPlatformVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("capitalPlatformService")
public class CapitalPlatformServiceImpl implements CapitalPlatformService{
	
	@Autowired
	private CapitalPlatformDao capitalPlatformDao;

	@Autowired
	private ResourceDao resourceDao;
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		CapitalPlatform cp = capitalPlatformDao.get(uuid);
		if(cp == null || CapitalPlatformStatus.DELETE.equals(cp.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("充值渠道不存在");
			return;
		}
		
		CapitalPlatformVO cpVO = new CapitalPlatformVO(cp);
		Resource logo = resourceDao.get(cp.getLogo());
		if(logo != null){
			cpVO.setLogoUrl(logo.getUrl());
		}
		
		Resource explanImg = resourceDao.get(cp.getExplanImg());
		if(explanImg != null){
			cpVO.setExplanImgUrl(explanImg.getUrl());
		}
		
		result.setData(cpVO);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);
		searchMap.put("type", type);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = capitalPlatformDao.getCountByParams(searchMap);
		List<CapitalPlatform> cpList = capitalPlatformDao.getListByParams(searchMap);
		
		List<CapitalPlatformVO> voList = new ArrayList<>();
		for(CapitalPlatform cp : cpList){
			CapitalPlatformVO vo = new CapitalPlatformVO(cp);
			
			Resource logo = resourceDao.get(cp.getLogo());
			if(logo != null){
				vo.setLogoUrl(logo.getUrl());
			}
			voList.add(vo);
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
		CapitalPlatform capitalPlatform = (CapitalPlatform) paramsMap.get("capitalPlatform");
		
		capitalPlatform.setUuid(UUID.randomUUID().toString());
		capitalPlatformDao.insert(capitalPlatform);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("添加成功！");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		CapitalPlatform capitalPlatform = (CapitalPlatform) paramsMap.get("capitalPlatform");
		
		CapitalPlatform cp = capitalPlatformDao.get(capitalPlatform.getUuid());
		if(cp == null || CapitalPlatformStatus.DELETE.equals(cp.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("充值渠道不存在");
			return;
		}
		cp.setName(capitalPlatform.getName());
		cp.setMax(capitalPlatform.getMax());
		cp.setMin(capitalPlatform.getMin());
		cp.setIsRecommend(capitalPlatform.getIsRecommend());
		cp.setIsUniqueAmount(capitalPlatform.getIsUniqueAmount());
		cp.setIsRandomAmount(capitalPlatform.getIsRandomAmount());
		cp.setLogo(capitalPlatform.getLogo());
		cp.setExplanation(capitalPlatform.getExplanation());
		cp.setExplanImg(capitalPlatform.getExplanImg());
		cp.setRemark(capitalPlatform.getRemark());
		cp.setStatus(capitalPlatform.getStatus());
		cp.setSort(capitalPlatform.getSort());
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
			result.setMessage("充值渠道不存在");
			return;
		}
		
		cp.setStatus(status);
		
		capitalPlatformDao.update(cp);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}
	
}
