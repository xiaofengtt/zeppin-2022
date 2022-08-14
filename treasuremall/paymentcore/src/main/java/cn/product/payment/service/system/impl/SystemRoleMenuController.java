/**
 * 
 */
package cn.product.payment.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.MenuDao;
import cn.product.payment.dao.RoleDao;
import cn.product.payment.dao.RoleMenuDao;
import cn.product.payment.entity.Menu;
import cn.product.payment.entity.Role;
import cn.product.payment.entity.Menu.MenuType;
import cn.product.payment.service.system.SystemRoleMenuService;
import cn.product.payment.vo.system.MenuVO;
import cn.product.payment.vo.system.RoleMenuVO;

/**
 * 平台管理员菜单权限
 */
@Service("systemRoleMenuService")
public class SystemRoleMenuController implements SystemRoleMenuService {

	@Autowired
    private RoleMenuDao roleMenuDao;
	
	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public void all(InputParams params, DataResult<Object> result) {
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("type", MenuType.SYSTEM);
		
		List<Menu> list = menuDao.getListByParams(searchMap);
		List<MenuVO> dataList = new ArrayList<>();
		//循环数据
		for(Menu menu:list){
			if(menu.getLevel() == 1){
				//一级菜单
				MenuVO menuVO = new MenuVO(menu);
				dataList.add(menuVO);
			}else{
				//二级菜单
				for(MenuVO data : dataList){
					if(data.getUuid().equals(menu.getParent())){
						//寻找父级 插入父级子菜单列表
						MenuVO menuVO = new MenuVO(menu);
						data.getChildren().add(menuVO);
						break;
					}
				}
			}
		}
		result.setData(dataList);
		result.setTotalResultCount(dataList.size());
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String role = paramsMap.get("role") == null ? "" : paramsMap.get("role").toString();
    	
		Role operatorRole = roleDao.get(role);
		
		if(operatorRole!=null && role.equals(operatorRole.getUuid())){
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("role", role);
			
			List<RoleMenuVO> list = roleMenuDao.getListForPage(searchMap);
			List<RoleMenuVO> dataList = new ArrayList<RoleMenuVO>();
			//循环数据
			for(RoleMenuVO roleMenu:list) {
				if(roleMenu.getLevel() == 1){
					//一级菜单
					dataList.add(roleMenu);
				}
				else{
					//二级菜单
					for(RoleMenuVO data : dataList){
						if(data.getMenu().equals(roleMenu.getParent())){
							//寻找父级 插入父级子菜单列表
							data.getChildren().add(roleMenu);
							break;
						}
					}
				}
			}
			result.setData(dataList);
			result.setTotalResultCount(dataList.size());
			result.setStatus(ResultStatusType.SUCCESS);
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("角色不存在");
			return;
		}
	}
	
	/**
	 * 修改角色权限
	 */
	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String role = paramsMap.get("role") == null ? "" : paramsMap.get("role").toString();
    	String[] menu = (String[]) paramsMap.get("menu");
    	
		Role operatorRole = roleDao.get(role);
		if(operatorRole!=null && role.equals(operatorRole.getUuid())){
			//角色存在
			List<MenuVO> menuList = new ArrayList<>();
			
			String[] menus = new String[0];
			if(menu != null){
				//有设置权限
				menus = menu;
			}
			
			//循环页面权限
			for(String menuid :menus){
				Menu bkMenu = menuDao.get(menuid);
				if(bkMenu!=null && menuid.equals(bkMenu.getUuid())){
					menuList.add(new MenuVO(bkMenu));
				}
			}
			
			//入库
			roleMenuDao.updateAll(operatorRole, menuList);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("权限更改成功");
			return;
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("角色不存在");
			return;
		}
	}
}
