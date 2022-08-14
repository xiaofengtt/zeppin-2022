/**
 * 
 */
package cn.zeppin.product.score.controller.back;

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

import cn.zeppin.product.score.controller.base.ActionParam;
import cn.zeppin.product.score.controller.base.ActionParam.DataType;
import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.entity.Admin;
import cn.zeppin.product.score.entity.Menu;
import cn.zeppin.product.score.service.MenuService;
import cn.zeppin.product.score.service.RoleMenuService;
import cn.zeppin.product.score.vo.back.MenuVO;

/**
 * 菜单管理
 */

@Controller
@RequestMapping(value = "/back/menu")
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private RoleMenuService roleMenuService;
	
	/**
	 * 根据条件查询菜单信息 
	 * @param scode
	 * @param level
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "菜单编码", type = DataType.STRING)
	@ActionParam(key = "level", message = "菜单级别", type = DataType.NUMBER)
	@ResponseBody
	public Result list(Menu menu) {
		Admin currentOperator = getCurrentOperator();
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("name", menu.getName());
		searchMap.put("level", menu.getLevel());
		searchMap.put("role", currentOperator.getRole());
		//查询符合条件的菜单列表
		List<Menu> list = menuService.getListByParams(searchMap);
		
		return ResultManager.createDataResult(list);
	}
	
	/**
	 * 查询角色左侧菜单信息 
	 * @return
	 */
	@RequestMapping(value = "/left", method = RequestMethod.GET)
	@ResponseBody
	public Result left() {
		Admin currentOperator = getCurrentOperator();
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("role", currentOperator.getRole());
		searchMap.put("sort", "sort");
		//查询符合条件的菜单列表
		List<Menu> list = menuService.getListByRole(searchMap);
		
		List<MenuVO> dataList = new ArrayList<>();
		for(Menu menu:list){
			if(menu.getLevel() == 1){
				MenuVO menuVO = new MenuVO(menu);
				dataList.add(menuVO);
			}else{
				for(MenuVO data : dataList){
					if(data.getUuid().equals(menu.getParent())){
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
	@RequestMapping(value = "/pagelist", method = RequestMethod.GET)
	@ActionParam(key = "parent", message = "父级菜单编号", type = DataType.STRING)
	@ActionParam(key = "name", message = "搜索参数", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ResponseBody
	public Result pagelist(Menu menu, Integer pageNum, Integer pageSize, String sorts) {
		//
		Map<String, Object> searchMap = new HashMap<String, Object>();
		if(menu.getParent() != null && !"".equals(menu.getParent())){
			searchMap.put("parent", menu.getParent());
		}else{
			searchMap.put("level", "1");
		}
		
		if(menu.getName() != null && !"".equals(menu.getName())){
			searchMap.put("names", menu.getName());
		}
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sorts);
		
		//查询符合条件的银行信息的总数
		Integer totalResultCount = menuService.getCountByParams(searchMap);
		//查询符合条件的功能列表
		List<Menu> menuList = menuService.getListByParams(searchMap);
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
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		Menu menu = menuService.get(uuid);
		if(menu == null){
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		MenuVO menuvo = new MenuVO(menu);
		if(menu.getParent() != null && !"".equals(menu.getParent())){
			Menu menup = menuService.get(uuid);
			if(menup != null){
				menuvo.setPname(menup.getName());
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
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "菜单名称", type = DataType.STRING, required = true)
	@ActionParam(key = "parent", message = "所属菜单", type = DataType.STRING)
	@ActionParam(key = "url", message = "菜单链接", type = DataType.STRING)
	@ActionParam(key = "icon", message = "菜单图标", type = DataType.STRING)
	@ActionParam(key = "sort", message = "排序序号", type = DataType.NUMBER)
	@ResponseBody
	public Result add(Menu menu) {
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		
		if(menu.getParent() != null && !"".equals(menu.getParent())){
			searchMap.put("parent", menu.getParent());
		}
		
		if(menu.getName() != null && !"".equals(menu.getName())){
			searchMap.put("name", menu.getName());
		}
		
		Integer totalResultCount = menuService.getCountByParams(searchMap);
		if(totalResultCount > 0){
			return ResultManager.createFailResult("要添加的name已存在");
		}
		
		Menu menuNew = new Menu();
		menuNew.setUuid(UUID.randomUUID().toString());
		menuNew.setName(menu.getName());
		menuNew.setSort(menu.getSort());
		if(menu.getUrl() != null && !"".equals(menu.getUrl())){
			menuNew.setUrl(menu.getUrl());
		}else{
			menuNew.setUrl("");
		}
		if(menu.getIcon() != null && !"".equals(menu.getIcon())){
			menuNew.setIcon(menu.getIcon());
		}else{
			menuNew.setIcon("");
		}
		if(menu.getParent() != null && !"".equals(menu.getParent())){
			Menu menuParent = menuService.get(menu.getParent());
			if(menuParent == null){
				return ResultManager.createFailResult("该父级菜单不存在！");
			}
			menuNew.setParent(menu.getParent());
			menuNew.setLevel(menuParent.getLevel()+1);
		}else{
			menuNew.setParent("");
			menuNew.setLevel(1);
		}
		menuService.insert(menuNew);
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 编辑
	 * @param uuid
	 * @param name
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message = "菜单名称", type = DataType.STRING, required = true)
	@ActionParam(key = "url", message = "菜单链接", type = DataType.STRING)
	@ActionParam(key = "icon", message = "菜单图标", type = DataType.STRING)
	@ActionParam(key = "sort", message = "排序序号", type = DataType.NUMBER)
	@ResponseBody
	public Result edit(Menu menuEdit) {
		
		Menu menu = this.menuService.get(menuEdit.getUuid());
		if(menu == null){
			return ResultManager.createFailResult("menu不存在");
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		
		if(menuEdit.getName() != null && !"".equals(menuEdit.getName()) && !menu.getName().equals(menuEdit.getName())){
			searchMap.put("name", menuEdit.getName());
			Integer totalResultCount = menuService.getCountByParams(searchMap);
			if(totalResultCount > 0){
				return ResultManager.createFailResult("要编辑的name已存在");
			}
		}
		
		menu.setName(menuEdit.getName());
		menu.setSort(menuEdit.getSort());
		if(menuEdit.getUrl() != null && !"".equals(menuEdit.getUrl())){
			menu.setUrl(menuEdit.getUrl());
		}else{
			menu.setUrl("");
		}
		if(menuEdit.getIcon() != null && !"".equals(menuEdit.getIcon())){
			menu.setIcon(menuEdit.getIcon());
		}else{
			menu.setIcon("");
		}
		this.menuService.update(menu);
		return ResultManager.createSuccessResult("保存成功！");
		
	}
	
	/**
	 * 删除
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		
		Menu menu = this.menuService.get(uuid);
		if(menu == null){
			return ResultManager.createFailResult("menu不存在");
		}
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("menu", uuid);
		Integer count = roleMenuService.getCountByParams(searchMap);
		if(count != null && count > 0){
			return ResultManager.createFailResult("页面菜单已被引用，不能删除");
		}
		this.menuService.delete(menu.getUuid());
		return ResultManager.createSuccessResult("删除成功！");
	}
	
}
