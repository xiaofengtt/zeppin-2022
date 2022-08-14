/**
 * 
 */
package cn.product.payment.service.store.impl;

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
import cn.product.payment.entity.Menu;
import cn.product.payment.entity.Menu.MenuType;
import cn.product.payment.service.store.StoreMenuService;
import cn.product.payment.vo.store.MenuVO;

/**
 * 左菜单
 * @author Administrator
 *
 */
@Service("storeMenuService")
public class StoreMenuServiceImpl implements StoreMenuService {

	@Autowired
    private MenuDao menuDao;
	
	@Override
	public void left(InputParams params, DataResult<Object> result) {
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("type", MenuType.STORE);
		
		List<Menu> list = menuDao.getListByParams(searchMap);
		List<MenuVO> dataList = new ArrayList<>();
		//封装多级结构
		for(Menu menu:list){
			if(menu.getLevel() == 1){
				//一级菜单
				MenuVO menuVO = new MenuVO(menu);
				dataList.add(menuVO);
			}else{
				//二级菜单
				for(MenuVO data : dataList){
					//循环一级菜单
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
}
