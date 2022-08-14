/**
 * 
 */
package cn.product.payment.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.BaseController;
import cn.product.payment.controller.base.Result;
import cn.product.payment.controller.base.ResultManager;
import cn.product.payment.entity.Admin;
import cn.product.payment.entity.Menu;
import cn.product.payment.service.MenuService;
import cn.product.payment.service.RoleMenuService;
import cn.product.payment.vo.system.MenuVO;

/**
 * 菜单管理
 */

@Controller
@RequestMapping(value = "/system/menu")
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private RoleMenuService roleMenuService;
	
	/**
	 * 根据条件查询
	 * @param scode
	 * @param scode
	 * @param level
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "名称", type = DataType.STRING)
	@ActionParam(key = "parent", message = "父级", type = DataType.STRING)
	@ActionParam(key = "level", message = "级别", type = DataType.NUMBER)
	@ResponseBody
	public Result list(String name, String parent, Integer level) {
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("name", name);
		searchMap.put("parent", parent);
		searchMap.put("level", level);
		
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
		Admin currentOperator = getSystemAdmin();
		
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("role", currentOperator.getRole());
		searchMap.put("sort", "sort");
		
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
}
