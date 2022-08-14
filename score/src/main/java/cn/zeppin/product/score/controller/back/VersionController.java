/**
 * 
 */
package cn.zeppin.product.score.controller.back;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.score.controller.base.ActionParam;
import cn.zeppin.product.score.controller.base.ActionParam.DataType;
import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.entity.Admin;
import cn.zeppin.product.score.entity.Version;
import cn.zeppin.product.score.entity.Version.VersionStatus;
import cn.zeppin.product.score.service.VersionService;


/**
 * 版本管理
 */

@Controller
@RequestMapping(value = "/back/version")
public class VersionController extends BaseController {
 
	@Autowired
	private VersionService versionService;
	
	/**
	 * 列表
	 * @param type
	 * @param bundleid
	 * @param channel
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "type", message="系统类型", type = DataType.STRING)
	@ActionParam(key = "bundleid", message="包名", type = DataType.STRING)
	@ActionParam(key = "channel", message="应用商店", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String type, String bundleid, String channel, String status, Integer pageNum, Integer pageSize, String sort) {
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("type", type);
		searchMap.put("bundleid", bundleid);
		searchMap.put("channel", channel);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalResultCount = versionService.getCountByParams(searchMap);
		List<Version> list = versionService.getListByParams(searchMap);
		return ResultManager.createDataResult(list, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		Version version = versionService.get(uuid);
		if (version == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		return ResultManager.createDataResult(version);
	}
	
	/**
	 * 添加
	 * @param type
	 * @param bundleid
	 * @param displayname
	 * @param channel
	 * @param name
	 * @param code
	 * @param mainurl
	 * @param tempurl
	 * @param downloadurl
	 * @param flag
	 * @param status
	 * @param adverturl
	 * @param flagupdate
	 * @param flagcompel
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "type", message="系统类型", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "bundleid", message="包名", type = DataType.STRING, required = true, maxLength = 100)
	@ActionParam(key = "displayname", message="应用名称", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "channel", message="应用商店", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "name", message="版本号", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "code", message="版本编号", type = DataType.INTEGER, required = true)
	@ActionParam(key = "mainurl", message="实际应用地址", type = DataType.STRING)
	@ActionParam(key = "tempurl", message="马甲地址", type = DataType.STRING)
	@ActionParam(key = "downloadurl", message="下载地址", type = DataType.STRING)
	@ActionParam(key = "flag", message="开关", type = DataType.BOOLEAN)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ActionParam(key = "adverturl", message="主包地址", type = DataType.STRING, required = true)
	@ActionParam(key = "flagupdate", message="是否更新", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagcompel", message="是否强制更新", type = DataType.BOOLEAN, required = true)
	@ResponseBody
	public Result add(String type, String bundleid, String displayname, String channel, String name, Integer code, String mainurl,
			String tempurl, String downloadurl, Boolean flag, String status, String adverturl, Boolean flagupdate, Boolean flagcompel) {
		//取管理员信息
		Admin admin = this.getCurrentOperator();
		
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
		version.setCreator(admin.getUuid());
		version.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		version.setAdverturl(adverturl);
		version.setFlagupdate(flagupdate);
		version.setFlagcompel(flagcompel);
		versionService.insert(version);
		
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 编辑
	 * @param uuid
	 * @param mainurl
	 * @param tempurl
	 * @param downloadurl
	 * @param flag
	 * @param adverturl
	 * @param flagupdate
	 * @param flagcompel
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "mainurl", message="实际应用地址", type = DataType.STRING)
	@ActionParam(key = "tempurl", message="马甲地址", type = DataType.STRING)
	@ActionParam(key = "downloadurl", message="下载地址", type = DataType.STRING)
	@ActionParam(key = "flag", message="开关", type = DataType.BOOLEAN)
	@ActionParam(key = "adverturl", message="主包地址", type = DataType.STRING, required = true)
	@ActionParam(key = "flagupdate", message="是否更新", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagcompel", message="是否强制更新", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(String uuid, String mainurl, String tempurl, String downloadurl, Boolean flag, String adverturl, Boolean flagupdate, 
			Boolean flagcompel, String status) {
		Version version = versionService.get(uuid);
		if(version != null && uuid.equals(version.getUuid())){
			version.setMainurl(mainurl);
			version.setTempurl(tempurl);
			version.setDownloadurl(downloadurl);
			version.setFlag(flag);
			
			version.setAdverturl(adverturl);
			version.setFlagupdate(flagupdate);
			version.setFlagcompel(flagcompel);
			if(!VersionStatus.DISABLE.equals(status) && !VersionStatus.NORMAL.equals(status)) {
				return ResultManager.createFailResult("状态错误！");
			}
			version.setStatus(status);
			versionService.update(version);
			
			return ResultManager.createSuccessResult("保存成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		Version version = versionService.get(uuid);
		if(version != null && uuid.equals(version.getUuid())){
			version.setStatus(VersionStatus.DELETE);
			versionService.update(version);
			return ResultManager.createSuccessResult("删除成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
}
