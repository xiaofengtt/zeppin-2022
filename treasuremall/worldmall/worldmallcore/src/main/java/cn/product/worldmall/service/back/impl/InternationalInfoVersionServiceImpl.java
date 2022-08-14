package cn.product.worldmall.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import cn.product.worldmall.entity.InternationalInfoVersion;
import cn.product.worldmall.entity.InternationalInfoVersion.InternationalInfoVersionStatus;
import cn.product.worldmall.entity.Version;
import cn.product.worldmall.service.back.InternationalInfoVersionService;
import cn.product.worldmall.vo.back.InternationalInfoVersionVO;

@Service("internationalInfoVersionService")
public class InternationalInfoVersionServiceImpl implements InternationalInfoVersionService{
	
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
		String internationalInfo = paramsMap.get("internationalInfo") == null ? "" : paramsMap.get("internationalInfo").toString();
		String version = paramsMap.get("version") == null ? "" : paramsMap.get("version").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("internationalInfo", internationalInfo);
		searchMap.put("version", version);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("status", status);
		searchMap.put("sort", sort);
		
		Integer totalResultCount = internationalInfoVersionDao.getCountByParams(searchMap);
		List<InternationalInfoVersion> list = internationalInfoVersionDao.getListByParams(searchMap);
		List<InternationalInfoVersionVO> listvo = new ArrayList<InternationalInfoVersionVO>();
		if(list != null && list.size() > 0) {
			for(InternationalInfoVersion iiv : list) {
				InternationalInfoVersionVO iivvo = new InternationalInfoVersionVO(iiv);
				InternationalInfo ii = this.internationalInfoDao.get(iiv.getInternationalInfo());
				if(ii != null) {
					iivvo.setInternationalInfoName(ii.getName());
					iivvo.setInternationalInfoNameEn(ii.getNameEn());
				}
				listvo.add(iivvo);
			}
		}
		
		result.setData(listvo);
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
    	
		InternationalInfoVersion iiv = internationalInfoVersionDao.get(uuid);
		if (iiv == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在");
			return;
		}
		InternationalInfoVersionVO iivvo = new InternationalInfoVersionVO(iiv);
		InternationalInfo ii = this.internationalInfoDao.get(iiv.getInternationalInfo());
		if(ii != null) {
			iivvo.setInternationalInfoName(ii.getName());
			iivvo.setInternationalInfoNameEn(ii.getNameEn());
		}
		result.setData(iivvo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		
		Map<String, Object> paramsMap = params.getParams();
		String internationalInfo = paramsMap.get("internationalInfo") == null ? "" : paramsMap.get("internationalInfo").toString();
		String version = paramsMap.get("version") == null ? "" : paramsMap.get("version").toString();
		Boolean flagWithdraw = paramsMap.get("flagWithdraw") == null ? null : Boolean.valueOf(paramsMap.get("flagWithdraw").toString());
		
		try {
			Version v = this.versionDao.get(version);
			if(v == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("渠道版本数据不存在");
				return;
			}
			
			InternationalInfo ii = this.internationalInfoDao.get(internationalInfo);
			if(ii == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("国家数据不存在");
				return;
			}
			
			//排他校验
			if(this.internationalInfoVersionDao.getByInternationalInfoVersion(ii.getCode(), v.getChannel(), v.getName()) != null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("信息已存在！");
				return;
			}
			InternationalInfoVersion iiv = new InternationalInfoVersion();
			iiv.setUuid(UUID.randomUUID().toString());
			iiv.setCreatetime(new Timestamp(System.currentTimeMillis()));
			iiv.setStatus(InternationalInfoVersionStatus.NORMAL);
			iiv.setInternationalInfo(internationalInfo);
			iiv.setVersion(version);
			iiv.setCode(ii.getCode());
			iiv.setChannel(v.getChannel());
			iiv.setVersionName(v.getName());
			iiv.setFlagWithdraw(flagWithdraw);
			
			internationalInfoVersionDao.insert(iiv);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常！");
			return;
		}
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String internationalInfo = paramsMap.get("internationalInfo") == null ? "" : paramsMap.get("internationalInfo").toString();
		String version = paramsMap.get("version") == null ? "" : paramsMap.get("version").toString();
		Boolean flagWithdraw = paramsMap.get("flagWithdraw") == null ? null : Boolean.valueOf(paramsMap.get("flagWithdraw").toString());
		
		
		try {
			InternationalInfoVersion iiv = internationalInfoVersionDao.get(uuid);
			if(iiv != null && uuid.equals(iiv.getUuid())){
				
				Version v = this.versionDao.get(version);
				if(v == null) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("渠道版本数据不存在");
					return;
				}
				
				InternationalInfo ii = this.internationalInfoDao.get(internationalInfo);
				if(ii == null) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("国家数据不存在");
					return;
				}
				
				//排他校验
				InternationalInfoVersion check = this.internationalInfoVersionDao.getByInternationalInfoVersion(ii.getCode(), v.getChannel(), v.getName());
				
				if(check != null && !check.getUuid().equals(iiv.getUuid())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("信息已存在！");
					return;
				}
				
				iiv.setInternationalInfo(internationalInfo);
				iiv.setVersion(version);
				iiv.setCode(ii.getCode());
				iiv.setChannel(v.getChannel());
				iiv.setVersionName(v.getName());
				iiv.setFlagWithdraw(flagWithdraw);
				
				internationalInfoVersionDao.update(iiv);
				
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("保存成功！");
			}
			else{
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("该条数据不存在");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常！");
			return;
		}
	}

	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String[] uuids = paramsMap.get("uuids") == null ? null : (String[])paramsMap.get("uuids");
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		if(!InternationalInfoVersionStatus.NORMAL.equals(status) && !InternationalInfoVersionStatus.DISABLE.equals(status) && !InternationalInfoVersionStatus.DELETE.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("变更状态有误");
			return;
		}
		
		try {
			if(uuids != null) {
				List<InternationalInfoVersion> iivList = new ArrayList<InternationalInfoVersion>();
				for(String ids : uuids) {
					InternationalInfoVersion iiv = internationalInfoVersionDao.get(ids);
					if(iiv == null || FrontUserStatus.DELETE.equals(iiv.getStatus())){
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("信息不存在");
						return;
					}
					if(InternationalInfoVersionStatus.NORMAL.equals(status)) {
						iiv.setFlagWithdraw(true);
					} else if(InternationalInfoVersionStatus.DISABLE.equals(status)) {
						iiv.setFlagWithdraw(false);
					} else {
						iiv.setStatus(status);
					}
					iivList.add(iiv);
				}
				this.internationalInfoDao.batchUpdate(iivList);
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
