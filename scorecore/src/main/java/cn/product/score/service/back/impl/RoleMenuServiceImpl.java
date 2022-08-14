package cn.product.score.service.back.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.MenuDao;
import cn.product.score.dao.RoleDao;
import cn.product.score.dao.RoleMenuDao;
import cn.product.score.entity.Menu;
import cn.product.score.entity.Role;
import cn.product.score.service.back.RoleMenuService;
import cn.product.score.vo.back.MenuVO;
import cn.product.score.vo.back.RoleMenuVO;

@Service("roleMenuDao")
public class RoleMenuServiceImpl implements RoleMenuService {
	
	@Autowired
    private RoleMenuDao roleMenuDao;
	
	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public void all(InputParams params, DataResult<Object> result) {
		//查询符合条件的菜单列表
		List<Menu> list = menuDao.getListByParams(new HashMap<String, Object>());
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
			//查询符合条件的角色页面信息列表
			List<RoleMenuVO> list = roleMenuDao.getListForPage(searchMap);
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
			result.setData(dataList);
			result.setTotalResultCount(dataList.size());
			result.setStatus(ResultStatusType.SUCCESS);
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("角色不存在");
			return;
		}
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String role = paramsMap.get("role") == null ? "" : paramsMap.get("role").toString();
    	String[] menu = (String[]) paramsMap.get("menu");
		//获取修改的角色
		Role operatorRole = roleDao.get(role);
		
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
				Menu bkMenu = menuDao.get(menuid);
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
