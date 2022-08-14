/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBkControllerMethodService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkControllerService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkRoleControllerPermissionService;
import cn.zeppin.product.ntb.backadmin.vo.BkControllerMethodVO;
import cn.zeppin.product.ntb.backadmin.vo.ControllerVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkController;
import cn.zeppin.product.ntb.core.entity.BkControllerMethod;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 * 功能管理
 */

@Controller
@RequestMapping(value = "/backadmin/controllerMethod")
public class ControllerMethodController extends BaseController {

	@Autowired
	private IBkControllerMethodService controllerMethodService;
	
	@Autowired
	private IBkControllerService controllerService;
	
	@Autowired
	private IBkRoleControllerPermissionService bkRoleControllerPermissionService;
	
	/**
	 * 查询所有功能信息 
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Result all() {
		//查询符合条件的功能列表
		List<Entity> controllerlist = controllerService.getAll(BkController.class);
		List<ControllerVO> dataList = new ArrayList<>();
		//将功能转成VO插入结果列表
		for(Entity e:controllerlist){
			BkController controller = (BkController) e;
			ControllerVO controllerVO = new ControllerVO(controller);
			dataList.add(controllerVO);
		}
		
		List<Entity> list = controllerMethodService.getAll(BkControllerMethod.class);
		for(Entity e: list){
			BkControllerMethod controllerMethod = (BkControllerMethod) e;
			for(ControllerVO data : dataList){
				if(data.getUuid().equals(controllerMethod.getController())){
					data.getChildren().add(controllerMethod);
					break;
				}
			}
		}
		
		return ResultManager.createDataResult(dataList, dataList.size());
	}
	
	/**
	 * 获取列表
	 * @param name
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/controllerlist", method = RequestMethod.GET)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "description", message="详细描述", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result controllerlist(String name, String description, Integer pageNum, Integer pageSize, String sorts) {
		Map<String, String> searchMap = new HashMap<String, String>();
		if(name != null && !"".equals(name)){
			searchMap.put("name", name);
		}
		if(description != null && !"".equals(description)){
			searchMap.put("description", description);
		}
		
		//查询符合条件的信息总数
		Integer totalResultCount = controllerService.getCount(searchMap);
		
		//查询符合条件的功能列表
		List<Entity> controllerlist = controllerService.getListForPage(searchMap, pageNum, pageSize, sorts, BkController.class);
		if(controllerlist != null){
			return ResultManager.createDataResult(controllerlist, pageNum, pageSize, totalResultCount);
		}else{
			return ResultManager.createFailResult("查询失败！");
		}
	
	}
	
	/**
	 * 获取信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/controllerget", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result controllerget(String uuid) {		
		BkController controller = controllerService.get(uuid);
		if(controller == null){
			return ResultManager.createFailResult("该条数据不存在！");
		}

		return ResultManager.createDataResult(controller);
	}
	
	/**
	 * 添加
	 * @param name
	 * @param description
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/controlleradd", method = RequestMethod.POST)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "description", message="详细描述", type = DataType.STRING)
	@ActionParam(key = "sort", message="默认排序", type = DataType.NUMBER)
	@ResponseBody
	public Result controlleradd(String name, String description, Integer sort) {
		Map<String, String> searchMap = new HashMap<String, String>();
		if(name != null && !"".equals(name)){
			searchMap.put("name", name);
		}
		//查询符合条件的信息总数
		Integer totalResultCount = controllerService.getCount(searchMap);
		if(totalResultCount > 0){
			return ResultManager.createFailResult("要添加的controller已存在");
		}
		BkController controller = new BkController();
		controller.setUuid(UUID.randomUUID().toString());
		controller.setName(name);
		controller.setSort(sort);
		if(description != null && !"".equals(description)){
			controller.setDescription(description);
		}else{
			controller.setDescription("");
		}
		controller = controllerService.insert(controller);
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 编辑
	 * @param uuid
	 * @param name
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/controlleredit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "description", message="详细描述", type = DataType.STRING)
	@ActionParam(key = "sort", message="默认排序", type = DataType.NUMBER)
	@ResponseBody
	public Result controlleredit(String uuid,String name,String description, Integer sort) {
		
		BkController controller = this.controllerService.get(uuid);
		if(controller == null){
			return ResultManager.createFailResult("controller不存在");
		}
		
		Map<String, String> searchMap = new HashMap<String, String>();
		if(name != null && !"".equals(name) && !controller.getName().equals(name)){
			searchMap.put("name", name);
			//查询符合条件的信息总数
			Integer totalResultCount = controllerService.getCount(searchMap);
			if(totalResultCount > 0){
				return ResultManager.createFailResult("要添加的controller已存在");
			}
		}
		
		if(description != null && !"".equals(description)){
			controller.setDescription(description);
		}else{
			controller.setDescription("");
		}

		controller.setName(name);
		controller.setSort(sort);
		controller = this.controllerService.update(controller);
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 删除
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/controllerdelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result controllerdelete(String uuid) {
		
		BkController conroller = this.controllerService.get(uuid);
		if(conroller == null){
			return ResultManager.createFailResult("controller不存在");
		}
		
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("controller", uuid);
		
		//查询符合条件的信息总数
		Integer totalResultCount = controllerMethodService.getCount(searchMap);
		
		if(totalResultCount > 0){
			return ResultManager.createFailResult("存在下属方法，不能删除！");
		}
		
		conroller = this.controllerService.delete(conroller);
		return ResultManager.createSuccessResult("删除成功！");
	}
	
	
	/**
	 * 获取列表（分页）
	 * @param uuid
	 * @param name
	 * @param description
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/methodlist", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "description", message="详细描述", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result methodlist(String uuid, String name, String description, Integer pageNum, Integer pageSize, String sorts) {
		//
		BkController controller = controllerService.get(uuid);
		if(controller == null){
			return ResultManager.createFailResult("所属功能不存在，查询失败！");
		}
		
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("controller", uuid);
		
		if(name != null && !"".equals(name)){
			searchMap.put("name", name);
		}
		if(description != null && !"".equals(description)){
			searchMap.put("description", description);
		}
		
		//查询符合条件的信息总数
		Integer totalResultCount = controllerMethodService.getCount(searchMap);
		
		//查询符合条件的功能列表
		List<Entity> controllerMethodList = controllerMethodService.getListForPage(searchMap, pageNum, pageSize, sorts, BkControllerMethod.class);
		if(controllerMethodList == null){
			return ResultManager.createFailResult("查询失败！");
		}
		
		List<BkControllerMethodVO> listVO = new ArrayList<BkControllerMethodVO>();
		for(Entity entity : controllerMethodList){
			BkControllerMethod method = (BkControllerMethod) entity;
			BkControllerMethodVO methodvo = new BkControllerMethodVO(method);
			methodvo.setControllerName(controller.getName());
			listVO.add(methodvo);
		}
		
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/methodget", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result methodget(String uuid) {
		BkControllerMethod method = controllerMethodService.get(uuid);
		if(method == null){
			return ResultManager.createFailResult("该条数据不存在！");
		}
		BkControllerMethodVO methodvo = new BkControllerMethodVO(method);
		BkController controller = controllerService.get(method.getController());
		if(controller == null){
			return ResultManager.createFailResult("所属功能不存在，查询失败！");
		}
		methodvo.setControllerName(controller.getDescription());
		return ResultManager.createDataResult(methodvo);
	}
	
	/**
	 * 添加
	 * @param uuid
	 * @param name
	 * @param description
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/methodadd", method = RequestMethod.POST)
	@ActionParam(key = "cuuid", message="所属功能", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "description", message="详细描述", type = DataType.STRING)
	@ActionParam(key = "sort", message="默认排序", type = DataType.NUMBER)
	@ResponseBody
	public Result methodadd(String cuuid, String name, String description, Integer sort) {
		
		Map<String, String> searchMap = new HashMap<String, String>();
		if(cuuid != null && !"".equals(cuuid)){
			BkController controller = controllerService.get(cuuid);
			if(controller == null){
				return ResultManager.createFailResult("所属功能不存在，查询失败！");
			}
			searchMap.put("controller", cuuid);
		}
		if(name != null && !"".equals(name)){
			searchMap.put("name", name);
		}
		//查询符合条件的信息总数
		Integer totalResultCount = controllerMethodService.getCount(searchMap);
		if(totalResultCount > 0){
			return ResultManager.createFailResult("要添加的method已存在");
		}
		BkControllerMethod method = new BkControllerMethod();
		method.setUuid(UUID.randomUUID().toString());
		method.setController(cuuid);
		method.setName(name);
		method.setSort(sort);
		if(description != null && !"".equals(description)){
			method.setDescription(description);
		}else{
			method.setDescription("");
		}
		method = controllerMethodService.insert(method);
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 编辑
	 * @param uuid
	 * @param name
	 * @param description
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/methodedit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "description", message="详细描述", type = DataType.STRING)
	@ActionParam(key = "sort", message="默认排序", type = DataType.NUMBER)
	@ResponseBody
	public Result methodedit(String uuid, String name, String description, Integer sort) {
		
		BkControllerMethod method = this.controllerMethodService.get(uuid);
		if(method == null){
			return ResultManager.createFailResult("method不存在");
		}
		
		Map<String, String> searchMap = new HashMap<String, String>();
		if(name != null && !"".equals(name) && !method.getName().equals(name)){
			searchMap.put("name", name);
			searchMap.put("controller", method.getController());
			//查询符合条件的信息总数
			Integer totalResultCount = controllerMethodService.getCount(searchMap);
			if(totalResultCount > 0){
				return ResultManager.createFailResult("要编辑的methodName已存在");
			}
		}
		
		
		if(description != null && !"".equals(description)){
			method.setDescription(description);
		}else{
			method.setDescription("");
		}
		
		method.setName(name);
		method.setSort(sort);
		method = this.controllerMethodService.update(method);
		return ResultManager.createSuccessResult("保存成功！");
		
	}
	
	/**
	 * 删除
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/methoddelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result methoddelete(String uuid) {
		
		BkControllerMethod method = this.controllerMethodService.get(uuid);
		if(method == null){
			return ResultManager.createFailResult("method不存在");
		}
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("method", uuid);
		//查询符合条件的信息总数
		Integer totalResultCount = bkRoleControllerPermissionService.getCount(searchMap);
		
		if(totalResultCount > 0){
			return ResultManager.createFailResult("方法已被引用，不能删除！");
		}
		
		method = this.controllerMethodService.delete(method);
		return ResultManager.createSuccessResult("删除成功！");
	}
}
