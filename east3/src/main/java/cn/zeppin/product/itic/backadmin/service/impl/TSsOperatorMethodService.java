/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgroups.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsControllerMethodDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITSsMenuDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITSsOperatorMenuDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITSsOperatorMethodDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITSsOperatorMethodService;
import cn.zeppin.product.itic.core.entity.TSsControllerMethod;
import cn.zeppin.product.itic.core.entity.TSsMenu;
import cn.zeppin.product.itic.core.entity.TSsOperator;
import cn.zeppin.product.itic.core.entity.TSsOperatorMenu;
import cn.zeppin.product.itic.core.entity.TSsOperatorMethod;
import cn.zeppin.product.itic.core.service.base.BaseService;
import cn.zeppin.product.utility.RealmUtility;
import cn.zeppin.product.utility.Utlity;


@Service
public class TSsOperatorMethodService extends BaseService implements ITSsOperatorMethodService {

	@Autowired
	private ITSsOperatorMethodDAO tSsOperatorMethodDAO;

	@Autowired
	private ITSsOperatorMenuDAO tSsOperatorMenuDAO;
	
	@Autowired
	private ITSsControllerMethodDAO tSsControllerMethodDAO;
	
	@Autowired
	private ITSsMenuDAO tSsMenuDAO;
	
	
	@Override
	public TSsOperatorMethod insert(TSsOperatorMethod t) {
		return tSsOperatorMethodDAO.insert(t);
	}

	@Override
	public TSsOperatorMethod delete(TSsOperatorMethod t) {
		return tSsOperatorMethodDAO.delete(t);
	}

	@Override
	public TSsOperatorMethod update(TSsOperatorMethod t) {
		return tSsOperatorMethodDAO.update(t);
	}

	@Override
	public TSsOperatorMethod get(String uuid) {
		return tSsOperatorMethodDAO.get(uuid);
	}

	@Override
	public List<TSsOperatorMethod> getList(Map<String, String> inputParams) {
		return this.tSsOperatorMethodDAO.getList(inputParams);
	}
	
	@Override
	public void updateAll(List<TSsOperator> operators, List<TSsControllerMethod> methods, List<String> menus) {
		Boolean flagCheck = "check".equals(methods.get(0).getName());
		Map<String, String> params = new HashMap<>();
		params.put("names", Utlity.getStringForSql(menus));
		
		List<TSsMenu> menuList = this.tSsMenuDAO.getList(params);
		List<String> parentMenus = new ArrayList<>();
		for(TSsMenu mid : menuList){
			if(!parentMenus.contains(mid.getPid())){
				parentMenus.add(mid.getPid());
			}
		}
		
		params.clear();
		params.put("ids", Utlity.getStringForSql(parentMenus));
		List<TSsMenu> parentMenuList = this.tSsMenuDAO.getList(params);
		menuList.addAll(parentMenuList);
		
		List<String> controllerList = new ArrayList<>();
		for(TSsControllerMethod cm : methods){
			if(!controllerList.contains(cm.getController())){
				controllerList.add(cm.getController());
			}
		}
		
		if(flagCheck){
			params.clear();
			params.put("controllers", Utlity.getStringForSql(controllerList));
			params.put("name", "edit");
			List<TSsControllerMethod> methodEditList = this.tSsControllerMethodDAO.getList(params);
			methods.addAll(methodEditList);
		}
		
		params.clear();
		params.put("controllers", Utlity.getStringForSql(controllerList));
		params.put("name", "list");
		List<TSsControllerMethod> methodGetList = this.tSsControllerMethodDAO.getList(params);
		methods.addAll(methodGetList);
		
		for(TSsOperator operator : operators){
			this.tSsOperatorMenuDAO.deleteByOperator(operator);
			this.tSsOperatorMethodDAO.deleteByOperator(operator);
			
			for(TSsMenu menu : menuList){
				TSsOperatorMenu om = new TSsOperatorMenu();
				om.setId(UUID.randomUUID().toString());
				om.setMenu(menu.getId());
				om.setOperator(operator.getId());
				this.tSsOperatorMenuDAO.insert(om);
			}
			for(TSsControllerMethod method : methods){
				TSsOperatorMethod om = new TSsOperatorMethod();
				om.setId(UUID.randomUUID().toString());
				om.setController(method.getController());
				om.setMethod(method.getId());
				om.setOperator(operator.getId());
				this.tSsOperatorMethodDAO.insert(om);
			}
		}
		RealmUtility.clearAuth();//清空缓存
	}

	@Override
	public List<TSsOperatorMethod> getByOperator(TSsOperator operator) {
		return this.tSsOperatorMethodDAO.getByOperator(operator);
	}
	
	
}
