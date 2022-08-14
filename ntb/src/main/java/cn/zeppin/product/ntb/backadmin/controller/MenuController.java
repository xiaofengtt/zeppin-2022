/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

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

import cn.zeppin.product.ntb.backadmin.service.api.IBkMenuService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkRoleMenuPermissionService;
import cn.zeppin.product.ntb.backadmin.vo.BkMenuVO;
import cn.zeppin.product.ntb.backadmin.vo.MenuVO;
import cn.zeppin.product.ntb.backadmin.vo.RoleMenuVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkMenu;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 * 菜单管理
 */

@Controller
@RequestMapping(value = "/backadmin/menu")
public class MenuController extends BaseController {

	@Autowired
	private IBkMenuService menuService;
	
	@Autowired
	private IBkRoleMenuPermissionService bkRoleMenuPermissionService;
	
	/**
	 * 根据条件查询菜单信息 
	 * @param scode
	 * @param level
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "scode", message = "菜单编码", type = DataType.STRING)
	@ActionParam(key = "level", message = "菜单级别", type = DataType.NUMBER)
	@ResponseBody
	public Result list(String scode, Integer level) {
		
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		//查询条件
		Map<String,String> searchMap = new HashMap<String,String>();
		searchMap.put("scode", scode);
		searchMap.put("level", level+"");
		searchMap.put("role", currentOperator.getRole());
		//查询符合条件的菜单列表
		List<Entity> list = menuService.getList(searchMap, BkMenu.class);
		
		return ResultManager.createDataResult(list);
	}
	
	/**
	 * 查询所有菜单信息 
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Result all() {
		//查询符合条件的菜单列表
		List<Entity> list = menuService.getAll(BkMenu.class);
		List<MenuVO> dataList = new ArrayList<>();
		for(Entity e:list){
			BkMenu menu = (BkMenu) e;
			if(menu.getLevel() == 1){
				MenuVO menuVO = new MenuVO(menu);
				dataList.add(menuVO);
			}else{
				for(MenuVO data : dataList){
					if(data.getUuid().equals(menu.getPid())){
						MenuVO menuVO = new MenuVO(menu);
						data.getChildren().add(menuVO);
						break;
					}
				}
			}
		}
		return ResultManager.createDataResult(dataList, dataList.size());
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
	@RequestMapping(value = "/menulist", method = RequestMethod.GET)
	@ActionParam(key = "pid", message = "父级菜单编号", type = DataType.STRING)
	@ActionParam(key = "title", message = "搜索参数", type = DataType.STRING)
	@ActionParam(key = "scode", message = "菜单编码", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ResponseBody
	public Result menulist(String pid, String title, String scode, Integer pageNum, Integer pageSize, String sorts) {
		//
		Map<String, String> searchMap = new HashMap<String, String>();
		if(pid != null && !"".equals(pid)){
			searchMap.put("pid", pid);
		}else{
			searchMap.put("level", "1");
		}
		
		if(title != null && !"".equals(title)){
			searchMap.put("title", title);
		}
		
		if(scode != null && !"".equals(scode)){
			searchMap.put("scodes", scode);
		}
		//查询符合条件的银行信息的总数
		Integer totalResultCount = menuService.getCount(searchMap);
		//查询符合条件的功能列表
		List<Entity> menuList = menuService.getListForPage(searchMap, pageNum, pageSize, sorts, BkMenu.class);
		if(menuList == null){
			return ResultManager.createFailResult("查询失败！");
		}
		
		return ResultManager.createDataResult(menuList, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/menuget", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result menuget(String uuid) {
		BkMenu menu = menuService.get(uuid);
		if(menu == null){
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		BkMenuVO menuvo = new BkMenuVO(menu);
		if(menu.getPid() != null && !"".equals(menu.getPid())){
			BkMenu menup = menuService.get(uuid);
			if(menup != null){
				menuvo.setPname(menup.getTitle());
			}
		}

		return ResultManager.createDataResult(menu);
	}
	
	/**
	 * 添加
	 * @param uuid
	 * @param name
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/menuadd", method = RequestMethod.POST)
	@ActionParam(key = "name", message = "菜单名称", type = DataType.STRING, required = true)
	@ActionParam(key = "title", message = "菜单标题", type = DataType.STRING, required = true)
	@ActionParam(key = "scode", message = "菜单编码", type = DataType.STRING, required = true)
	@ActionParam(key = "pid", message = "所属菜单", type = DataType.STRING)
	@ActionParam(key = "url", message = "菜单链接", type = DataType.STRING)
	@ActionParam(key = "icon", message = "菜单图标", type = DataType.STRING)
	@ActionParam(key = "sort", message = "排序序号", type = DataType.NUMBER)
	@ResponseBody
	public Result menuadd(String pid, String name, String title, String scode, String url, String icon, Integer sort) {
		
		Map<String, String> searchMap = new HashMap<String, String>();
		
		if(scode != null && !"".equals(scode)){
			searchMap.put("scode", scode);
		}
		Integer totalResultCount = menuService.getCount(searchMap);
		if(totalResultCount > 0){
			return ResultManager.createFailResult("要添加的scode已存在");
		}
		
		searchMap.clear();
		if(pid != null && !"".equals(pid)){
			searchMap.put("pid", pid);
		}
		
		if(name != null && !"".equals(name)){
			searchMap.put("name", name);
		}
		
		totalResultCount = menuService.getCount(searchMap);
		if(totalResultCount > 0){
			return ResultManager.createFailResult("要添加的name已存在");
		}
		
		BkMenu menu = new BkMenu();
		menu.setUuid(UUID.randomUUID().toString());
		menu.setName(name);
		menu.setTitle(title);
		menu.setScode(scode);
		menu.setSort(sort);
		if(url != null && !"".equals(url)){
			menu.setUrl(url);
		}else{
			menu.setUrl("");
		}
		if(icon != null && !"".equals(icon)){
			menu.setIcon(icon);
		}else{
			menu.setIcon("");
		}
		if(pid != null && !"".equals(pid)){
			BkMenu menuParent = menuService.get(pid);
			if(menuParent == null){
				return ResultManager.createFailResult("该父级菜单不存在！");
			}
			menu.setPid(pid);
			menu.setLevel(menuParent.getLevel()+1);
		}else{
			menu.setPid("");
			menu.setLevel(1);
		}
		menu = menuService.insert(menu);
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 编辑
	 * @param uuid
	 * @param name
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/menuedit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message = "菜单名称", type = DataType.STRING, required = true)
	@ActionParam(key = "title", message = "菜单标题", type = DataType.STRING, required = true)
	@ActionParam(key = "scode", message = "菜单编码", type = DataType.STRING, required = true)
	@ActionParam(key = "url", message = "菜单链接", type = DataType.STRING)
	@ActionParam(key = "icon", message = "菜单图标", type = DataType.STRING)
	@ActionParam(key = "sort", message = "排序序号", type = DataType.NUMBER)
	@ResponseBody
	public Result menuedit(String uuid, String name, String title, String scode, String url, String icon, Integer sort) {
		
		BkMenu menu = this.menuService.get(uuid);
		if(menu == null){
			return ResultManager.createFailResult("menu不存在");
		}
		
		Map<String, String> searchMap = new HashMap<String, String>();
		
		if(scode != null && !"".equals(scode) && !menu.getScode().equals(scode)){
			searchMap.put("scode", scode);
			Integer totalResultCount = menuService.getCount(searchMap);
			if(totalResultCount > 0){
				return ResultManager.createFailResult("要修改的scode已存在");
			}
		}
		
		
		searchMap.clear();
		if(name != null && !"".equals(name) && !menu.getName().equals(name)){
			searchMap.put("name", name);
			Integer totalResultCount = menuService.getCount(searchMap);
			if(totalResultCount > 0){
				return ResultManager.createFailResult("要编辑的name已存在");
			}
		}
		
		menu.setName(name);
		menu.setTitle(title);
		menu.setScode(scode);
		menu.setSort(sort);
		if(url != null && !"".equals(url)){
			menu.setUrl(url);
		}else{
			menu.setUrl("");
		}
		if(icon != null && !"".equals(icon)){
			menu.setIcon(icon);
		}else{
			menu.setIcon("");
		}
		menu = this.menuService.update(menu);
		return ResultManager.createSuccessResult("保存成功！");
		
	}
	
	/**
	 * 删除
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/menudelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result menudelete(String uuid) {
		
		BkMenu menu = this.menuService.get(uuid);
		if(menu == null){
			return ResultManager.createFailResult("menu不存在");
		}
		Map<String, String> searchMap = new HashMap<>();
		searchMap.put("menu", uuid);
		List<Entity> list = bkRoleMenuPermissionService.getListForPage(searchMap, RoleMenuVO.class);
		if(list != null && list.size() > 0){
			return ResultManager.createFailResult("页面菜单已被引用，不能删除");
		}
		menu = this.menuService.delete(menu);
		return ResultManager.createSuccessResult("删除成功！");
	}
	
	
	
}
