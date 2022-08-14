package cn.product.worldmall.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.AdminDao;
import cn.product.worldmall.dao.CapitalAccountDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.entity.Admin;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.SystemParam.SystemParamType;
import cn.product.worldmall.service.back.SystemParamService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.SystemParamVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("systemParamService")
public class SystemParamServiceImpl implements SystemParamService{
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Autowired
    private CapitalAccountDao capitalAccountDao;
	
	@Autowired
    private AdminDao adminDao;

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String paramKey = paramsMap.get("paramKey") == null ? "" : paramsMap.get("paramKey").toString();
    	
		SystemParam sp = systemParamDao.get(paramKey);
		if(sp == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("参数不存在");
			return;
		}
		
		SystemParamVO spvo = new SystemParamVO(sp);
		
		Admin admin = this.adminDao.get(sp.getCreator());
		if(admin != null){
			spvo.setCreatorName(admin.getRealname());
		}
		
		result.setData(spvo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String description = paramsMap.get("description") == null ? "" : paramsMap.get("description").toString();
		String partitional = paramsMap.get("partitional") == null ? "" : paramsMap.get("partitional").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("description", description);
		searchMap.put("partitional", partitional);
		searchMap.put("type", type);
		searchMap.put("pageNum", pageNum);
		searchMap.put("pageSize", pageSize);
		searchMap.put("sort", sort);
		
		Integer totalCount = systemParamDao.getCountByParams(searchMap);
		List<SystemParam> spList = systemParamDao.getListByParams(searchMap);
		
		List<SystemParamVO> voList = new ArrayList<SystemParamVO>();
		for(SystemParam sp : spList){
			SystemParamVO spvo = new SystemParamVO(sp);
			
			Admin admin = this.adminDao.get(sp.getCreator());
			if(admin != null){
				spvo.setCreatorName(admin.getRealname());
			}
			
			voList.add(spvo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String paramKey = paramsMap.get("paramKey") == null ? "" : paramsMap.get("paramKey").toString();
    	String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
    	String[] paramValues = (String[]) paramsMap.get("paramValues");
		
		SystemParam sp = systemParamDao.get(paramKey);
		if(sp == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("参数不存在");
			return;
		}
		
		if(SystemParamType.NUMERIC.equals(sp.getType())){
			if(paramValues.length != 1 || !Utlity.isNumeric(paramValues[0])){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("请输入数字类型参数");
				return;
			}
			sp.setParamValue(paramValues[0]);
		}else if(SystemParamType.CURRENCY.equals(sp.getType())){
			if(paramValues.length != 1 || !Utlity.isPositiveCurrency(paramValues[0])){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("请输入货币类型参数");
				return;
			}
			sp.setParamValue(paramValues[0]);
		}else if(SystemParamType.BOOLEAN.equals(sp.getType())){
			if(paramValues.length != 1 || (!paramValues[0].equals("true") && !paramValues[0].equals("false"))){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("请输入布尔类型参数");
				return;
			}
			sp.setParamValue(paramValues[0]);
		}else if(SystemParamType.MAP.equals(sp.getType())){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			for(String paramValue : paramValues){
				String[] paramsls = paramValue.split("@_@");
				if(paramsls == null || paramsls.length != 2){
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("请输入Map类型参数");
					return;
				}
				paramMap.put(paramsls[0].trim(), paramsls[1].trim());
			}
			if(!Utlity.checkSystemParamMap(sp.getParamKey(), paramMap)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("请输参数有误，请仔细检查！");
				return;
			}
			sp.setParamValue(JSONUtils.obj2json(paramMap));
		}else if(SystemParamType.PRIMARYKEY.equals(sp.getType())){
			if(paramValues.length != 1){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("请选择正确的参数值！");
				return;
			}
//			if(SystemParamKey.WITHDRAW_DEFAULT_ACCOUNT.equals(paramKey)){
//				CapitalAccount ca = this.capitalAccountDao.get(paramValues[0]);
//				if(ca == null || !CapitalAccountStatus.NORMAL.equals(ca.getStatus()) || !CapitalPlatformTransType.WITHDRAW.equals(ca.getTransType())){
//					result.setStatus(ResultStatusType.FAILED);
//					result.setMessage("请选择正确的参数值！");
//					return;
//				}
//			}
			sp.setParamValue(paramValues[0]);
		}else{
			if(paramValues.length != 1){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("请输入字符串类型参数！");
				return;
			}
			sp.setParamValue(paramValues[0].trim());
		}
		
		sp.setCreator(admin);
		sp.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		this.systemParamDao.update(sp);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}
	
}
