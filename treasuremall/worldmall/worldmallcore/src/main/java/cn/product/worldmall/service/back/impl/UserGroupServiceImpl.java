package cn.product.worldmall.service.back.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.FrontUserGroupDao;
import cn.product.worldmall.entity.FrontUserGroup;
import cn.product.worldmall.service.back.UserGroupService;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("userGroupService")
public class UserGroupServiceImpl implements UserGroupService{
	
	@Autowired
	private FrontUserGroupDao frontUserGroupDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sorts = paramsMap.get("sorts") == null ? "" : paramsMap.get("sorts").toString();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String discription = paramsMap.get("discription") == null ? "" : paramsMap.get("discription").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);
		searchMap.put("discription", discription);
		searchMap.put("sort", sorts);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//查询符合条件的广告图信息的总数
		Integer totalResultCount = frontUserGroupDao.getCountByParams(searchMap);
		//查询符合条件的广告图信息列表
		List<FrontUserGroup> list = frontUserGroupDao.getListByParams(searchMap);
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		//获取广告图信息
		FrontUserGroup fug = frontUserGroupDao.get(name);
		if (fug == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
			return;
		}
		
		result.setData(fug);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String discription = paramsMap.get("discription") == null ? "" : paramsMap.get("discription").toString();
		
		
		try {
			//创建信息
			FrontUserGroup fug = new FrontUserGroup();
			fug.setName(name);
			fug.setDiscription(discription);
			frontUserGroupDao.insert(fug);
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("保存异常");
		}
		
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String discription = paramsMap.get("discription") == null ? "" : paramsMap.get("discription").toString();
		
		try {
			//获取广告图信息
			FrontUserGroup fug = frontUserGroupDao.get(name);
			if(fug != null && name.equals(fug.getName())){
				
				fug.setDiscription(discription);
				frontUserGroupDao.update(fug);
				
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("保存成功");
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("该条数据不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("保存异常");
		}
		
	}
}
