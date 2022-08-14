package cn.product.worldmall.service.back.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.MenuDao;
import cn.product.worldmall.dao.RoleMenuDao;
import cn.product.worldmall.entity.Menu;
import cn.product.worldmall.service.back.MenuService;
import cn.product.worldmall.vo.back.MenuVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("menuService")
public class MenuServiceImpl implements MenuService {
	
	@Autowired
    private MenuDao menuDao;
	
	@Autowired
	private RoleMenuDao roleMenuDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Menu menu = (Menu) paramsMap.get("menu");
		String role = paramsMap.get("role") == null ? "" : paramsMap.get("role").toString();
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("name", menu.getName());
		searchMap.put("level", menu.getLevel());
		searchMap.put("role", role);
		//查询符合条件的菜单列表
		List<Menu> list = menuDao.getListByParams(searchMap);
		
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void left(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String role = paramsMap.get("role") == null ? "" : paramsMap.get("role").toString();
		
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("role", role);
		searchMap.put("sort", "sort");
		//查询符合条件的菜单列表
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
		result.setMessage("success");
		result.setTotalResultCount(dataList.size());
	}

	@Override
	public void pagelist(InputParams params, DataResult<Object> result) {
		
		Map<String, Object> paramsMap = params.getParams();
		Menu menu = (Menu) paramsMap.get("menu");
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
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
		searchMap.put("sort", sort);
		
		//查询符合条件的银行信息的总数
		Integer totalResultCount = menuDao.getCountByParams(searchMap);
		//查询符合条件的功能列表
		List<Menu> menuList = menuDao.getListByParams(searchMap);
		if(menuList == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("查询失败！");
			return;
		}
		result.setData(menuList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		Menu menu = menuDao.get(uuid);
		if(menu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
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
		result.setMessage("success");
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		
		Map<String, Object> paramsMap = params.getParams();
		Menu menu = (Menu) paramsMap.get("menu");
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		
		if(menu.getParent() != null && !"".equals(menu.getParent())){
			searchMap.put("parent", menu.getParent());
		}
		
		if(menu.getName() != null && !"".equals(menu.getName())){
			searchMap.put("name", menu.getName());
		}
		
		Integer totalResultCount = menuDao.getCountByParams(searchMap);
		if(totalResultCount > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("要添加的name已存在！");
			return;
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
			Menu menuParent = menuDao.get(menu.getParent());
			if(menuParent == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("该父级菜单不存在！");
				return;
			}
			menuNew.setParent(menu.getParent());
			menuNew.setLevel(menuParent.getLevel()+1);
		}else{
			menuNew.setParent("");
			menuNew.setLevel(1);
		}
		menuDao.insert(menuNew);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Menu menuEdit = (Menu) paramsMap.get("menuEdit");
		
		Menu menu = this.menuDao.get(menuEdit.getUuid());
		if(menu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("menu不存在！");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		
		if(menuEdit.getName() != null && !"".equals(menuEdit.getName()) && !menu.getName().equals(menuEdit.getName())){
			searchMap.put("name", menuEdit.getName());
			Integer totalResultCount = menuDao.getCountByParams(searchMap);
			if(totalResultCount > 0){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("要编辑的name已存在！");
				return;
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
		this.menuDao.update(menu);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		Menu menu = this.menuDao.get(uuid);
		if(menu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("menu不存在！");
			return;
		}
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("menu", uuid);
		Integer count = roleMenuDao.getCountByParams(searchMap);
		if(count != null && count > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("页面菜单已被引用，不能删除！");
			return;
		}
		this.menuDao.delete(menu.getUuid());
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("删除成功！");
	}

}
