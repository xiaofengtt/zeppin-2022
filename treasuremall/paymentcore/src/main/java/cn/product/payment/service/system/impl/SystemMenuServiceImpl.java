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
import cn.product.payment.dao.RoleMenuDao;
import cn.product.payment.entity.Admin;
import cn.product.payment.entity.Menu;
import cn.product.payment.entity.Menu.MenuType;
import cn.product.payment.service.system.SystemMenuService;
import cn.product.payment.vo.system.MenuVO;

/**
 * 平台管理员菜单
 */
@Service("systemMenuService")
public class SystemMenuServiceImpl implements SystemMenuService {

	@Autowired
    private MenuDao menuDao;
	
	@Autowired
	private RoleMenuDao roleMenuDao;
	
	/**
	 * 菜单列表
	 */
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
	
	/**
	 * 取管理员菜单权限
	 */
	@Override
	public void left(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Admin admin = (Admin) paramsMap.get("admin");
		
		Map<String,Object> searchMap = new HashMap<String,Object>();
		//平台管理员菜单
		searchMap.put("type", MenuType.SYSTEM);
		searchMap.put("role", admin.getRole());
		
		List<Menu> list = menuDao.getListByRole(searchMap);
		List<MenuVO> dataList = new ArrayList<>();

		//循环菜单
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
			result.setMessage("该条数据不存在！");
			return;
		}
		
		MenuVO menuvo = new MenuVO(menu);
		if(menu.getParent() != null && !"".equals(menu.getParent())){
			//父级非空取父级
			Menu menup = menuDao.get(uuid);
			if(menup != null){
				menuvo.setPname(menup.getName());
			}
		}
		result.setData(menu);
		result.setStatus(ResultStatusType.SUCCESS);
	}
}
