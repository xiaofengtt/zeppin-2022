/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.service.api.IVersionService;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.backadmin.vo.VersionVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.Version;
import cn.zeppin.product.ntb.core.entity.Version.VersionStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

/**
 * @author elegantclack
 *
 * 后台版本控制管理
 */

@Controller
@RequestMapping(value = "/backadmin/version")
public class VersionController extends BaseController {
 
	@Autowired
	private IVersionService versionService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	/**
	 * 根据条件查询版本信息 
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "device", message="设备", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, String device, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(!Utlity.checkStringNull(status) && !"all".equals(status)){
			searchMap.put("status", status);
		}
		if(!Utlity.checkStringNull(device) && !"all".equals(device)){
			searchMap.put("device", device);
		}
		
		
		//查询符合条件的版本信息的总数
		Integer totalResultCount = versionService.getCount(searchMap);
		//查询符合条件的版本信息列表
		List<Entity> list = versionService.getListForPage(searchMap, pageNum, pageSize, sorts, Version.class);
		List<VersionVO> listvo = new ArrayList<VersionVO>();
		for(Entity entity : list){
			Version version = (Version)entity;
			VersionVO vo = new VersionVO(version);
			
			Resource resource = resourceService.get(version.getResource());
			if (resource != null) {
				vo.setResourceUrl(resource.getUrl());
				vo.setResourceName(resource.getFilename()+"."+resource.getFiletype());
			}
			
			if(vo.getCreator() != null){
				BkOperator op = this.bkOperatorService.get(vo.getCreator());
				if(op != null){
					vo.setCreatorCN(op.getRealname());
				} else {
					vo.setCreatorCN("-");
				}
			} else {
				vo.setCreatorCN("-");
			}
			listvo.add(vo);
		}
		
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取版本信息分状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ActionParam(key = "device", message="设备", type = DataType.STRING)
	@ResponseBody
	public Result statusList(String device) {
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!Utlity.checkStringNull(device) && !"all".equals(device)){
			searchMap.put("device", device);
		}
		List<Entity> list = versionService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取一条版本信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//获取版本信息
		Version version = versionService.get(uuid);
		if (version == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		//界面返回封装对象
		VersionVO versionVO = new VersionVO(version);
		
		//资源信息
		Resource resource = resourceService.get(version.getResource());
		if (resource != null) {
			versionVO.setResourceUrl(resource.getUrl());
			versionVO.setResourceName(resource.getFilename()+"."+resource.getFiletype());
		}
		
		if(version.getCreator() != null){
			BkOperator op = this.bkOperatorService.get(version.getCreator());
			if(op != null){
				versionVO.setCreatorCN(op.getRealname());
			} else {
				versionVO.setCreatorCN("-");
			}
		} else {
			versionVO.setCreatorCN("-");
		}
		

		return ResultManager.createDataResult(versionVO);
	}
	
	/**
	 * 添加一条版本信息
	 * @param name
	 * @param logo
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "version", message="版本号", type = DataType.INTEGER, required = true)
	@ActionParam(key = "versionName", message="版本名称", type = DataType.STRING, required = true)
//	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "versionDes", message="版本描述", type = DataType.STRING)
	@ActionParam(key = "resource", message="资源文件", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "device", message="设备", type = DataType.STRING, required = true)
	@ActionParam(key = "flagCompel", message="是否强制更新", type = DataType.BOOLEAN, required = true)
	@ResponseBody
	public Result add(Integer version, String versionName, String versionDes, String resource, String device, Boolean flagCompel) {
		
		//验证是否有重名的情况
		if (versionService.isExistVersionByName(version,device,null)) {
			return ResultManager.createFailResult("该版本号已存在！");
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//创建版本信息
		Version vs = new Version();
		vs.setUuid(UUID.randomUUID().toString());
		vs.setVersion(version);
		vs.setVersionDes(versionDes);
		vs.setVersionName(versionName);
		vs.setDevice(device);
		vs.setFlagCompel(flagCompel);
		vs.setResource(resource);
		vs.setStatus(VersionStatus.UNPUBLISH);
		vs.setCreator(currentOperator.getUuid());
		vs.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		versionService.insert(vs);
		
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 编辑一条版本信息
	 * @param uuid
	 * @param name
	 * @param logo
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "version", message="版本号", type = DataType.INTEGER, required = true)
	@ActionParam(key = "versionName", message="版本名称", type = DataType.STRING, required = true)
//	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "versionDes", message="版本描述", type = DataType.STRING)
	@ActionParam(key = "resource", message="资源文件", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "device", message="设备", type = DataType.STRING, required = true)
	@ActionParam(key = "flagCompel", message="是否强制更新", type = DataType.BOOLEAN, required = true)
	@ResponseBody
	public Result edit(String uuid, Integer version, String versionName, String versionDes, String resource, String device,
			Boolean flagCompel) {
		
		//获取版本信息
		Version vs = versionService.get(uuid);
		if(vs != null && uuid.equals(vs.getUuid())){
			//验证是否有重名的情况
			if (versionService.isExistVersionByName(version,device,uuid)) {
				return ResultManager.createFailResult("该版本号已存在！");
			}
			
			//修改版本信息
			vs.setVersion(version);
			vs.setVersionDes(versionDes);
			vs.setVersionName(versionName);
			vs.setDevice(device);
			vs.setFlagCompel(flagCompel);
			vs.setResource(resource);
			
			versionService.update(vs);
			
			return ResultManager.createSuccessResult("保存成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}

	/**
	 * 停用/启用/发布一条版本信息
	 * @param uuid
	 * @param status published unpublish disable
	 * @return
	 */
	@RequestMapping(value = "/change", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result change(String uuid, String status) {
		
		//获取版本信息
		Version vs = versionService.get(uuid);
		if(vs != null && uuid.equals(vs.getUuid())){
			if(VersionStatus.DISABLE.equals(status) || VersionStatus.UNPUBLISH.equals(status) || VersionStatus.PUBLISHED.equals(status)){
				vs.setStatus(status);
				versionService.update(vs);
			} else {
				return ResultManager.createFailResult("状态错误，操作失败！");
			}
			return ResultManager.createSuccessResult("操作成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条版本信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		
		//获取版本信息
		Version vs = versionService.get(uuid);
		if(vs != null && uuid.equals(vs.getUuid())){
			//删除版本信息
			versionService.delete(vs);
			return ResultManager.createSuccessResult("删除成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
}
