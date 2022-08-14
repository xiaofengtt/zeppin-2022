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
import com.product.worldpay.dao.RoleMenuDao;
import com.product.worldpay.entity.Admin;
import com.product.worldpay.entity.Menu;
import com.product.worldpay.entity.Menu.MenuType;
import com.product.worldpay.service.system.SystemMenuService;
import com.product.worldpay.vo.system.MenuVO;

@Service("systemMenuService")
public class SystemMenuServiceImpl implements SystemMenuService {

	@Autowired
    private MenuDao menuDao;
	
	@Autowired
	private RoleMenuDao roleMenuDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Menu menu = (Menu) paramsMap.get("menu");
		Admin admin = (Admin) paramsMap.get("admin");
		
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("type", MenuType.SYSTEM);
		searchMap.put("name", menu.getName());
		searchMap.put("level", menu.getLevel());
		searchMap.put("parent", menu.getParent());
		searchMap.put("role", admin.getRole());
		//查询符合条件的菜单列表
		List<Menu> list = menuDao.getListByParams(searchMap);
		
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void left(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Admin admin = (Admin) paramsMap.get("admin");
		
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("type", MenuType.SYSTEM);
		searchMap.put("role", admin.getRole());
		
		List<Menu> list = menuDao.getListByRole(searchMap);
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
		result.setStatus(ResultStatusType.SUCCESS);
		result.setTotalResultCount(dataList.size());
	}
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid").toString();
		
		Menu menu = menuDao.get(uuid);
		if(menu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("data not exist !");
			return;
		}
		
		MenuVO menuvo = new MenuVO(menu);
		if(menu.getParent() != null && !"".equals(menu.getParent())){
			Menu menup = menuDao.get(uuid);
			if(menup != null){
				menuvo.setPname(menup.getName());
			}
		}
		result.setData(menu);
		result.setStatus(ResultStatusType.SUCCESS);
	}
}
