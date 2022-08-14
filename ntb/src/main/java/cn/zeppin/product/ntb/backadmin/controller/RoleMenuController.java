/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBkMenuService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorRoleService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkRoleMenuPermissionService;
import cn.zeppin.product.ntb.backadmin.vo.MenuVO;
import cn.zeppin.product.ntb.backadmin.vo.RoleMenuVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkMenu;
import cn.zeppin.product.ntb.core.entity.BkOperatorRole;
import cn.zeppin.product.ntb.core.entity.BkRoleMenuPermission;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;

/**
 * @author hehe
 *
 * 后台角色页面信息管理
 */

@Controller
@RequestMapping(value = "/backadmin/roleMenu")
public class RoleMenuController extends BaseController {

	@Autowired
	private IBkMenuService bkMenuService;
	
	@Autowired
	private IBkOperatorRoleService bkOperatorRoleService;
	
	@Autowired
	private IBkRoleMenuPermissionService bkRoleMenuPermissionService;
	
	/**
	 * 根据条件查询角色页面信息 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "role", message = "角色", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result list(String role) {
		BkOperatorRole operatorRole = bkOperatorRoleService.get(role);
		
		if(operatorRole!=null && role.equals(operatorRole.getUuid())){
			Map<String, String> searchMap = new HashMap<>();
			searchMap.put("role", role);
			//查询符合条件的角色页面信息列表
			List<Entity> list = bkRoleMenuPermissionService.getListForPage(searchMap, RoleMenuVO.class);
			List<RoleMenuVO> dataList = new ArrayList<>();
			
			//循环数据
			for(Entity e:list){
				RoleMenuVO roleMenu = (RoleMenuVO) e;
				//一级页面直接添加
				if(roleMenu.getLevel() == 1){
					dataList.add(roleMenu);
				}
				//二级页面 循环一级页面添加入一级页面的children
				else{
					for(RoleMenuVO data : dataList){
						if(data.getMenu().equals(roleMenu.getPid())){
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
	 * 改变角色页面列表顺序
	 * @param type
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/sort", method = RequestMethod.GET)
	@ActionParam(key = "type", message = "类型", type = DataType.STRING, required = true)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result sort(String type, String uuid) {
		//判断类型参数是否正确
		if("up".equals(type) || "down".equals(type)){
			//获取被修改权限
			BkRoleMenuPermission roleMenu = bkRoleMenuPermissionService.get(uuid);
			if(roleMenu!=null && uuid.equals(roleMenu.getUuid())){
				//修改权限
				bkRoleMenuPermissionService.updateSort(roleMenu,type);
				return ResultManager.createDataResult(null, "排序更改成功");
			}else{
				return ResultManager.createFailResult("角色页面权限不存在！");
			}
		}else{
			return ResultManager.createFailResult("接口参数有误！");
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
		BkOperatorRole operatorRole = bkOperatorRoleService.get(role);
		
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
				BkMenu bkMenu = bkMenuService.get(menuid);
				//判断页面是否存在
				if(bkMenu!=null && menuid.equals(bkMenu.getUuid())){
					//一级页面直接添加
					if(bkMenu.getLevel() == 1){
						menuList.add(new MenuVO(bkMenu));
					}
					//二级页面 循环一级页面添加入一级页面的children
					else{
						for(MenuVO menuVO : menuList){
							if(menuVO.getUuid().equals(bkMenu.getPid())){
								menuVO.getChildren().add(new MenuVO(bkMenu));
								break;
							}
						}
					}
				}
			}
			bkRoleMenuPermissionService.updateAll(operatorRole, menuList);
			return ResultManager.createDataResult(null, "权限更改成功");
		}else{
			return ResultManager.createFailResult("角色不存在！");
		}
	}
}
