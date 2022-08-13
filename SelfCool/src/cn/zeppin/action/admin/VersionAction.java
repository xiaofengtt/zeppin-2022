package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Resource;
import cn.zeppin.entity.Version;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IResourceService;
import cn.zeppin.service.api.IVersionService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

public class VersionAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5081406955151314194L;

	private IVersionService versionService;
	private IResourceService resourceService;
	
	public IVersionService getVersionService() {
		return versionService;
	}

	public void setVersionService(IVersionService versionService) {
		this.versionService = versionService;
	}
	
	public IResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	/**
	 * 添加
	 * @author Clark
	 * @date: 2014年6月24日 下午2:49:24 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "version", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "forcedUpdate", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "device", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "resource.id", type = ValueType.NUMBER)
	public void Add(){
		ActionResult result = new ActionResult();

		String versionStr = request.getParameter("version");
		short status = Short.valueOf(this.request.getParameter("status"));
		short forcedUpdate = Short.valueOf(this.request.getParameter("forcedUpdate"));
		short device = Short.valueOf(this.request.getParameter("device"));
		
		Version version = new Version();
		version.setDevice(device);
		version.setForcedUpdate(forcedUpdate);
		version.setVersion(versionStr);
		version.setStatus(status);
		version.setCreatetime(new Timestamp((new Date()).getTime()));
		if (request.getParameterMap().containsKey("resource.id")) {
			Integer resourceID = Integer.parseInt(request.getParameter("resource.id"));
			Resource resource = this.getResourceService().getById(resourceID);
			if (resource != null) {
				version.setResource(resource);
			}
		}
		this.getVersionService().addVersion(version);
		Map<String, Object> data = SerializeEntity.version2Map(version);
		result.init(SUCCESS_STATUS, "添加版本成功！", data);
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);		
	}

	/**
	 * 删除
	 * @author Clark
	 * @date: 2014年7月15日 下午5:52:13 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete(){
		ActionResult result = new ActionResult();
		Integer versionID = Integer.valueOf(request.getParameter("id"));
		Version version = this.getVersionService().getVersionById(versionID);
		if (version != null){
			this.getVersionService().deleteVersion(version);
			Map<String, Object> data = SerializeEntity.version2Map(version);
			result.init(SUCCESS_STATUS, "删除版本成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的版本ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}

	/**
	 * 加载 用于修改的时候load
	 * @author Clark
	 * @date: 2014年7月15日 上午11:36:48 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Load(){
		ActionResult result = new ActionResult();
		Integer versionID = Integer.valueOf(request.getParameter("id"));
		String split =request.getParameter("split");
		Version version = this.getVersionService().getVersionById(versionID);
		if (version != null) {
			Map<String, Object> data = SerializeEntity.version2Map(version,split);
			result.init(SUCCESS_STATUS, "加载版本成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的版本ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}

	/**
	 * 修改
	 * @author Clark
	 * @date: 2014年7月20日 下午6:41:58 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "version", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "forcedUpdate", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "device", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "downloadPath", type = ValueType.STRING)
	public void Update(){
		ActionResult result = new ActionResult();
		Integer versionID = Integer.valueOf(request.getParameter("id"));
		String versionStr = request.getParameter("version");
		short status = Short.valueOf(this.request.getParameter("status"));
		short forcedUpdate = Short.valueOf(this.request.getParameter("forcedUpdate"));
		short device = Short.valueOf(this.request.getParameter("device"));

		Version version = this.getVersionService().getVersionById(versionID);
		if (version != null){
			version.setDevice(device);
			version.setForcedUpdate(forcedUpdate);
			version.setVersion(versionStr);
			version.setStatus(status);
			version.setCreatetime(new Timestamp((new Date()).getTime()));
			
			if (request.getParameterMap().containsKey("resource.id")) {
				Integer resourceID = Integer.parseInt(request.getParameter("resource.id"));
				Resource resource = this.getResourceService().getById(resourceID);
				if (resource == null) {
					result.init(FAIL_STATUS, "文件不存在", null);
				}
				version.setResource(resource);
			}
			
	
			this.getVersionService().updateVersion(version); 
			Map<String, Object> data = SerializeEntity.version2Map(version);
			result.init(SUCCESS_STATUS, "修改版本成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的版本ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);		
	}

	/**
	 * 分类列表
	 */
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "sysUser.id", type = ValueType.NUMBER)
	@ActionParam(key = "sysUser.name", type = ValueType.STRING)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	public void List(){
		
		ActionResult result = new ActionResult();
		
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("sysUser.id", request.getParameter("sysUser.id"));
		searchMap.put("sysUser.name", request.getParameter("sysUser.name"));
		searchMap.put("status", request.getParameter("status"));
		
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		
		int recordCount =this.getVersionService().getVersionCountByParams(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount/pagesize);

		List<Version> versionList = getVersionService().getVersionListByParams(searchMap, sorts, offset, pagesize);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (versionList != null && versionList.size() > 0){
			for (Version version : versionList){
				Map<String, Object> data = SerializeEntity.version2Map(version);
				dataList.add(data);
			}
		}
		result.init(SUCCESS_STATUS, "搜索完成！", dataList);
		result.setPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalCount(recordCount);
		
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}
}
