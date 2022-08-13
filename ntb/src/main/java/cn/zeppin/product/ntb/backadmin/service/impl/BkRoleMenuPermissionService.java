package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBkMenuDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBkRoleMenuPermissionDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBkRoleMenuPermissionService;
import cn.zeppin.product.ntb.backadmin.vo.MenuVO;
import cn.zeppin.product.ntb.core.entity.BkMenu;
import cn.zeppin.product.ntb.core.entity.BkOperatorRole;
import cn.zeppin.product.ntb.core.entity.BkRoleMenuPermission;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author Clark.R 2016年9月21日
 *
 */

@Service
public class BkRoleMenuPermissionService extends BaseService implements IBkRoleMenuPermissionService {	
	
	@Autowired
	private IBkRoleMenuPermissionDAO bkRoleMenuPermissionDAO;
	
	@Autowired
	private IBkMenuDAO bkMenuDAO;
	
	/**
	 * 更新所有的角色权限数据
	 */
	@Override
	public void updateAll(BkOperatorRole role, List<MenuVO> menuList) {
		//删除现有权限
		bkRoleMenuPermissionDAO.deleteByRole(role);
		//循环一级权限
		for(int i=0;i<menuList.size();i++){
			MenuVO menu = menuList.get(i);
			//循环添加二级权限
			for(int j=0;j<menu.getChildren().size();j++){
				MenuVO childMenu = menu.getChildren().get(j);
				BkRoleMenuPermission roleMenu = new BkRoleMenuPermission();
				roleMenu.setUuid(UUID.randomUUID().toString());
				roleMenu.setMenu(childMenu.getUuid());
				roleMenu.setRole(role.getUuid());
				roleMenu.setSort(j+1);
				bkRoleMenuPermissionDAO.insert(roleMenu);
			}
			//添加一级权限
			BkRoleMenuPermission roleMenu = new BkRoleMenuPermission();
			roleMenu.setUuid(UUID.randomUUID().toString());
			roleMenu.setMenu(menu.getUuid());
			roleMenu.setRole(role.getUuid());
			roleMenu.setSort(i+1);
			bkRoleMenuPermissionDAO.insert(roleMenu);
		}
	}

	/**
	 * 更新角色权限顺序
	 * @param type
	 * @param menus
	 * @return
	 */
	public void updateSort (BkRoleMenuPermission roleMenu, String type){
		//获取被修改顺序的页面
		BkMenu menu = this.bkMenuDAO.get(roleMenu.getMenu());
		
		//筛选参数集
		Map<String, String> searchMap = new HashMap<>();
		searchMap.put("role", roleMenu.getRole());
		searchMap.put("level", menu.getLevel().toString());
		searchMap.put("pid", menu.getPid());
		//根据参数取跟当前排序有关的列表
		List<Entity> list = bkRoleMenuPermissionDAO.getList(searchMap, BkRoleMenuPermission.class);
		
		//上移
		if ("up".equals(type)){
			//判断是否可以上移
			if(roleMenu.getSort() > 1){
				//循环列表
				for(Entity e: list){
					BkRoleMenuPermission rm = (BkRoleMenuPermission) e;
					//前一条记录排序-1
					if(rm.getSort() == roleMenu.getSort() - 1){
						rm.setSort(rm.getSort() + 1);
						bkRoleMenuPermissionDAO.update(rm);
					}
					//选中记录排序+1
					else if(rm.getUuid().equals(roleMenu.getUuid())){
						rm.setSort(rm.getSort() - 1);
						bkRoleMenuPermissionDAO.update(rm);
					}
				}
			}
		}
		//下移
		else if ("down".equals(type)){
			//判断是否可以下移
			if(roleMenu.getSort() < list.size()){
				//循环列表
				for(Entity e: list){
					BkRoleMenuPermission rm = (BkRoleMenuPermission) e;
					//后一条记录排序+1
					if(rm.getSort() == roleMenu.getSort() + 1){
						rm.setSort(rm.getSort() - 1);
						bkRoleMenuPermissionDAO.update(rm);
					}
					//选中记录排序-1
					else if(rm.getUuid().equals(roleMenu.getUuid())){
						rm.setSort(rm.getSort() + 1);
						bkRoleMenuPermissionDAO.update(rm);
					}
				}
			}
		}
	}
	
	/**
	 * 获取角色权限列表
	 * @param searchMap
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return bkRoleMenuPermissionDAO.getListForPage(inputParams, resultClass);
	}
	
	/**
	 * 获取角色权限列表
	 * @param uuid
	 * @return
	 */
	@Override
	public BkRoleMenuPermission get(String uuid) {
		return bkRoleMenuPermissionDAO.get(uuid);
	}
}
