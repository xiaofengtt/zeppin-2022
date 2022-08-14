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
import cn.product.score.dao.VersionDao;
import cn.product.score.entity.Version;
import cn.product.score.entity.Version.VersionStatus;
import cn.product.score.service.back.VersionService;

@Service("versionDao")
public class VersionServiceImpl implements VersionService{
	
	@Autowired
	private VersionDao versionDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String bundleid = paramsMap.get("bundleid") == null ? "" : paramsMap.get("bundleid").toString();
		String channel = paramsMap.get("channel") == null ? "" : paramsMap.get("channel").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("type", type);
		searchMap.put("bundleid", bundleid);
		searchMap.put("channel", channel);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("status", status);
		searchMap.put("sort", sort);
		
		Integer totalResultCount = versionDao.getCountByParams(searchMap);
		List<Version> list = versionDao.getListByParams(searchMap);
		
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
    	
		Version version = versionDao.get(uuid);
		if (version == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在");
			return;
		}
		result.setData(version);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		
		Map<String, Object> paramsMap = params.getParams();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String bundleid = paramsMap.get("bundleid") == null ? "" : paramsMap.get("bundleid").toString();
		String displayname = paramsMap.get("displayname") == null ? "" : paramsMap.get("displayname").toString();
		String channel = paramsMap.get("channel") == null ? "" : paramsMap.get("channel").toString();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		Integer code = paramsMap.get("code") == null ? null : Integer.valueOf(paramsMap.get("code").toString());
		String mainurl = paramsMap.get("mainurl") == null ? "" : paramsMap.get("mainurl").toString();
		String tempurl = paramsMap.get("tempurl") == null ? "" : paramsMap.get("tempurl").toString();
		String downloadurl = paramsMap.get("downloadurl") == null ? "" : paramsMap.get("downloadurl").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		Boolean flag = paramsMap.get("flag") == null ? null : Boolean.valueOf(paramsMap.get("flag").toString());
		
		Version version = new Version();
		version.setUuid(UUID.randomUUID().toString());
		version.setType(type);
		version.setBundleid(bundleid);
		version.setDisplayname(displayname);
		version.setChannel(channel);
		version.setName(name);
		version.setCode(code);
		version.setMainurl(mainurl);
		version.setTempurl(tempurl);
		version.setDownloadurl(downloadurl);
		version.setFlag(flag);
		version.setStatus(status);
		version.setCreator(admin);
		version.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		versionDao.insert(version);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String mainurl = paramsMap.get("mainurl") == null ? "" : paramsMap.get("mainurl").toString();
		String tempurl = paramsMap.get("tempurl") == null ? "" : paramsMap.get("tempurl").toString();
		String downloadurl = paramsMap.get("downloadurl") == null ? "" : paramsMap.get("downloadurl").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		Boolean flag = paramsMap.get("flag") == null ? null : Boolean.valueOf(paramsMap.get("flag").toString());
		
		Version version = versionDao.get(uuid);
		if(version != null && uuid.equals(version.getUuid())){
			version.setMainurl(mainurl);
			version.setTempurl(tempurl);
			version.setDownloadurl(downloadurl);
			version.setFlag(flag);
			
			if(!VersionStatus.DISABLE.equals(status) && !VersionStatus.NORMAL.equals(status)) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("状态错误！");
			}
			version.setStatus(status);
			
			versionDao.update(version);
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("保存成功！");
		}
		else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在");
			return;
		}
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		Version version = versionDao.get(uuid);
		if(version != null && uuid.equals(version.getUuid())){
			version.setStatus(VersionStatus.DELETE);
			versionDao.update(version);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("删除成功！");
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在");
			return;
		}
	}
	
}
