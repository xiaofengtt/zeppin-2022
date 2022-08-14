package cn.product.worldmall.service.back.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.dao.InternationalInfoDao;
import cn.product.worldmall.dao.InternationalInfoVersionDao;
import cn.product.worldmall.dao.VersionDao;
import cn.product.worldmall.entity.FrontUser.FrontUserStatus;
import cn.product.worldmall.entity.InternationalInfo;
import cn.product.worldmall.entity.InternationalInfo.InternationalInfoStatus;
import cn.product.worldmall.service.back.InternationalInfoVersionService;

@Service("internationalInfoService")
public class InternationalInfoServiceImpl implements InternationalInfoVersionService{
	
	@Autowired
	private InternationalInfoVersionDao internationalInfoVersionDao;
	
	@Autowired
	private VersionDao versionDao;
	
	@Autowired
	private InternationalInfoDao internationalInfoDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String code = paramsMap.get("code") == null ? "" : paramsMap.get("code").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("code", code);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("status", status);
		searchMap.put("sort", sort);
		
		Integer totalResultCount = internationalInfoDao.getCountByParams(searchMap);
		List<InternationalInfo> list = internationalInfoDao.getListByParams(searchMap);
		
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
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
    	InternationalInfo ii = internationalInfoDao.get(uuid);
		if (ii == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在");
			return;
		}
		result.setData(ii);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
	}

	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String[] uuids = paramsMap.get("uuids") == null ? null : (String[])paramsMap.get("uuids");
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		if(!InternationalInfoStatus.NORMAL.equals(status) && !InternationalInfoStatus.DISABLE.equals(status) && !InternationalInfoStatus.DELETE.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("变更状态有误");
			return;
		}
		
		try {
			if(uuids != null) {
				List<InternationalInfo> iiList = new ArrayList<InternationalInfo>();
				for(String ids : uuids) {
					InternationalInfo ii = internationalInfoDao.get(ids);
					if(ii == null || FrontUserStatus.DELETE.equals(ii.getStatus())){
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("信息不存在");
						return;
					}
					ii.setStatus(status);
					iiList.add(ii);
				}
				this.internationalInfoVersionDao.batchUpdate(iiList);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("修改成功！");
				return;
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户参数错误");
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常！");
			return;
		}
	}
	
}
