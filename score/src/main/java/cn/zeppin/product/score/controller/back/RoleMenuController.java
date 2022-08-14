/**
 * 
 */
package cn.zeppin.product.score.controller.back;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.zeppin.product.score.entity.Menu;
import cn.zeppin.product.score.entity.Role;
import cn.zeppin.product.score.service.MenuService;
import cn.zeppin.product.score.service.RoleMenuService;
import cn.zeppin.product.score.service.RoleService;
import cn.zeppin.product.score.vo.back.MenuVO;
import cn.zeppin.product.score.vo.back.RoleMenuVO;

/**
 * 后台角色页面信息管理
 */

@Controller
@RequestMapping(value = "/back/roleMenu")
public class RoleMenuController extends BaseController {

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleMenuService roleMenuService;
	
	/**
	 * 查询所有菜单信息 
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Result all() {
		//查询符合条件的菜单列表
		List<Menu> list = menuService.getListByParams(new HashMap<String, Object>());
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
	 * 根据条件查询角色页面信息 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "role", message = "角色", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result list(String role) {
		Role operatorRole = roleService.get(role);
		
		if(operatorRole!=null && role.equals(operatorRole.getUuid())){
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("role", role);
			//查询符合条件的角色页面信息列表
			List<RoleMenuVO> list = roleMenuService.getListForPage(searchMap);
			List<RoleMenuVO> dataList = new ArrayList<RoleMenuVO>();
			//循环数据
			for(RoleMenuVO roleMenu:list) {
//				//一级页面直接添加
				if(roleMenu.getLevel() == 1){
					dataList.add(roleMenu);
				}
				//二级页面 循环一级页面添加入一级页面的children
				else{
					for(RoleMenuVO data : dataList){
						if(data.getMenu().equals(roleMenu.getParent())){
							data.getChildren().add(roleMenu);
							break;
						}
					}
				}
			}
			return ResultManager.createDataResult(dataList, dataList.size());
		}else{
			return ResultManager.createFailResult("角色不存在！");
		}
	}
	
	/**
	 * 变更角色页面权限
	 * @param type
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST )
	@ActionParam(key = "role", message = "角色", type = DataType.STRING, required = true)
	@ActionParam(key = "menu", message = "权限", type = DataType.STRING_ARRAY)
	@ResponseBody
	public Result edit(String role, String[] menu) {
		//获取修改的角色
		Role operatorRole = roleService.get(role);
		
		//判断角色是否存在
		if(operatorRole!=null && role.equals(operatorRole.getUuid())){
			List<MenuVO> menuList = new ArrayList<>();
			
			//判断是否为空权限
			String[] menus = new String[0];
			if(menu != null){
				menus = menu;
			}
			
			//循环页面获取页面树状结构
			for(String menuid :menus){
				//获取页面
				Menu bkMenu = menuService.get(menuid);
				//判断页面是否存在
				if(bkMenu!=null && menuid.equals(bkMenu.getUuid())){
					menuList.add(new MenuVO(bkMenu));
					//一级页面直接添加
//					if(bkMenu.getLevel() == 1){
//						menuList.add(new MenuVO(bkMenu));
//					}
//					//二级页面 循环一级页面添加入一级页面的children
//					else{
//						for(MenuVO menuVO : menuList){
//							if(menuVO.getUuid().equals(bkMenu.getParent())){
//								menuVO.getChildren().add(new MenuVO(bkMenu));
//								break;
//							}
//						}
//					}
				}
			}
			roleMenuService.updateAll(operatorRole, menuList);
			return ResultManager.createDataResult(null, "权限更改成功");
		}else{
			return ResultManager.createFailResult("角色不存在！");
		}
	}
}
