/**
 * 
 */
package com.product.worldpay.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.MenuDao;
import com.product.worldpay.dao.RoleDao;
import com.product.worldpay.dao.RoleMenuDao;
import com.product.worldpay.entity.Menu;
import com.product.worldpay.entity.Role;
import com.product.worldpay.entity.Menu.MenuType;
import com.product.worldpay.service.system.SystemRoleMenuService;
import com.product.worldpay.vo.system.MenuVO;
import com.product.worldpay.vo.system.RoleMenuVO;

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
				//一级页面直接添加
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
			result.setData(dataList);
			result.setTotalResultCount(dataList.size());
			result.setStatus(ResultStatusType.SUCCESS);
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("role not exist !");
			return;
		}
	}
	
	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String role = paramsMap.get("role") == null ? "" : paramsMap.get("role").toString();
    	String[] menu = (String[]) paramsMap.get("menu");
    	
		Role operatorRole = roleDao.get(role);
		if(operatorRole!=null && role.equals(operatorRole.getUuid())){
			List<MenuVO> menuList = new ArrayList<>();
			
			String[] menus = new String[0];
			if(menu != null){
				menus = menu;
			}
			
			//循环页面获取页面树状结构
			for(String menuid :menus){
				Menu bkMenu = menuDao.get(menuid);
				if(bkMenu!=null && menuid.equals(bkMenu.getUuid())){
					menuList.add(new MenuVO(bkMenu));
				}
			}
			roleMenuDao.updateAll(operatorRole, menuList);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("operate successed !");
			return;
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("role not exist !");
			return;
		}
	}
}
