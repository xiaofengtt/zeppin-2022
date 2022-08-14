/**
 * 
 */
package com.product.worldpay.service.store.impl;

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
import com.product.worldpay.entity.Menu;
import com.product.worldpay.entity.Menu.MenuType;
import com.product.worldpay.service.store.StoreMenuService;
import com.product.worldpay.vo.store.MenuVO;

@Service("storeMenuService")
public class StoreMenuServiceImpl implements StoreMenuService {

	@Autowired
    private MenuDao menuDao;
	
	@Override
	public void left(InputParams params, DataResult<Object> result) {
//		Map<String, Object> paramsMap = params.getParams();
//		CompanyAdmin ca = (CompanyAdmin) paramsMap.get("companyAdmin");
		
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("type", MenuType.STORE);
		
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
		result.setStatus(ResultStatusType.SUCCESS);
		result.setTotalResultCount(dataList.size());
	}
}
