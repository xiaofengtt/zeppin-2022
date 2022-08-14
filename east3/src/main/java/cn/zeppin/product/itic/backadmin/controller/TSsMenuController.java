/**
 * 
 */
package cn.zeppin.product.itic.backadmin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.itic.backadmin.service.api.ITCheckInfoService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsMenuService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsOperatorMenuService;
import cn.zeppin.product.itic.backadmin.vo.TSsOperatorVO;
import cn.zeppin.product.itic.core.controller.base.ActionParam;
import cn.zeppin.product.itic.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.itic.core.controller.base.BaseController;
import cn.zeppin.product.itic.core.controller.base.Result;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.itic.core.entity.TSsMenu;
import cn.zeppin.product.itic.core.entity.TSsRole.RoleId;
import cn.zeppin.product.utility.Utlity;

/**
 * 菜单管理
 */

@Controller
@RequestMapping(value = "/backadmin/menu")
public class TSsMenuController extends BaseController {

	@Autowired
	private ITSsMenuService tSsMenuService;
	
	@Autowired
	private ITSsOperatorMenuService tSsOperatorMenuService;
	
	@Autowired
	private ITCheckInfoService tCheckInfoService;
	
	/**
	 * 根据条件查询菜单信息 
	 * @param scode
	 * @param level
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "scode", message = "页面编码", type = DataType.STRING)
	@ActionParam(key = "level", message = "页面等级", type = DataType.NUMBER)
	@ResponseBody
	public Result list(String scode, Integer level) {
		
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		//查询条件
		Map<String,String> searchMap = new HashMap<String,String>();
		searchMap.put("scode", scode);
		searchMap.put("level", level == null ? null : level+"");
		searchMap.put("operator", operator.getId());
		//查询符合条件的菜单列表
		List<TSsMenu> list = tSsMenuService.getListForOperator(searchMap);
		return ResultManager.createDataResult(list);
	}
	
	/**
	 * 查询管理员所有待审核条目
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkList", method = RequestMethod.GET)
	@ActionParam(key = "dataproduct", message="信托项目", type = DataType.STRING)
	@ResponseBody
	public Result checkList(String dataproduct) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		Map<String, String> productMap = (Map<String, String>) session.getAttribute("productMap");
		List<String> methodList = (List<String>) session.getAttribute("methodList");
		List<String> controllerList = new ArrayList<String>();
		for(String method : methodList){
			if(method.indexOf("check") > -1){
				controllerList.add(method.substring(0,method.indexOf(":")));
			}
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("dataproduct", dataproduct);
		if(RoleId.user.equals(operator.getRole())){
			if(RoleId.user.equals(operator.getRole())){inputParams.put("products", Utlity.getStringForSql(new ArrayList<String>(productMap.keySet())));}
			inputParams.put("datatypes", Utlity.getStringForSql(controllerList));
		}
		List<Object[]> datas = this.tCheckInfoService.getCountListForUser(inputParams);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for(Object[] data : datas){
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("name", data[0]);
			dataMap.put("description", data[1]);
			dataMap.put("count", data[3]);
			
			dataList.add(dataMap);
		}
		
		return ResultManager.createDataResult(dataList, dataList.size());
	}
	
	/**
	 * 查询管理员所有可见修改信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateList", method = RequestMethod.GET)
	@ActionParam(key = "dataproduct", message="信托项目", type = DataType.STRING)
	@ResponseBody
	public Result updateList(String dataproduct) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		Map<String, String> productMap = (Map<String, String>) session.getAttribute("productMap");
		List<String> methodList = (List<String>) session.getAttribute("methodList");
		List<String> controllerList = new ArrayList<String>();
		for(String method : methodList){
			if(method.indexOf("edit") > -1){
				controllerList.add(method.substring(0,method.indexOf(":")));
			}
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("dataproduct", dataproduct);
		if(RoleId.user.equals(operator.getRole())){
			if(RoleId.user.equals(operator.getRole())){inputParams.put("products", Utlity.getStringForSql(new ArrayList<String>(productMap.keySet())));}
			inputParams.put("datatypes", Utlity.getStringForSql(controllerList));
		}
		List<Object[]> datas = this.tCheckInfoService.getCountListForUser(inputParams);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for(Object[] data : datas){
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("name", data[0]);
			dataMap.put("description", data[1]);
			dataMap.put("count", data[3]);
			
			dataList.add(dataMap);
		}
		
		return ResultManager.createDataResult(dataList, dataList.size());
	}
}
