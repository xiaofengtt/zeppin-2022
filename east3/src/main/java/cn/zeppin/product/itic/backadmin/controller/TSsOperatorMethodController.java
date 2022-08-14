/**
 * 
 */
package cn.zeppin.product.itic.backadmin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.itic.backadmin.service.api.ITSsControllerMethodService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsControllerService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsOperatorMethodService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsOperatorService;
import cn.zeppin.product.itic.backadmin.vo.TSsControllerVO;
import cn.zeppin.product.itic.core.controller.base.ActionParam;
import cn.zeppin.product.itic.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.itic.core.controller.base.BaseController;
import cn.zeppin.product.itic.core.controller.base.Result;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.itic.core.entity.TSsController;
import cn.zeppin.product.itic.core.entity.TSsControllerMethod;
import cn.zeppin.product.itic.core.entity.TSsOperator;
import cn.zeppin.product.itic.core.entity.TSsOperatorMethod;

/**
 * 用户权限管理
 */

@Controller
@RequestMapping(value = "/backadmin/operatorMethod")
public class TSsOperatorMethodController extends BaseController {
	
	@Autowired
	private ITSsOperatorMethodService tSsOperatorMethodService;
	
	@Autowired
	private ITSsOperatorService tSsOperatorService;
	
	@Autowired
	private ITSsControllerService tSsControllerService;
	
	@Autowired
	private ITSsControllerMethodService tSsControllerMethodService;
	
	/**
	 * 查询所有权限 
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Result all() {
		List<TSsController> cList = this.tSsControllerService.getAll();
		List<TSsControllerVO> cvoList = new ArrayList<TSsControllerVO>();
		
		Map<String, String> params = new HashMap<String, String>();
		List<TSsControllerMethod> mList = this.tSsControllerMethodService.getList(params);
		
		for(TSsController c : cList){
			TSsControllerVO cvo = new TSsControllerVO(c);
			for(TSsControllerMethod m : mList){
				if(m.getController().equals(c.getId()) && !"list".equals(m.getName())){
					cvo.getMethodList().add(m);
				}
			}
			cvoList.add(cvo);
		}
		return ResultManager.createDataResult(cvoList, cvoList.size());
	}
	
	/**
	 * 获取用户权限
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "operator", message = "用户ID", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String operator) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("operator", operator);
		
		List<TSsOperatorMethod> omList = this.tSsOperatorMethodService.getList(params);
		Boolean checkFlag = false;
		for(TSsOperatorMethod om :omList){
			TSsControllerMethod cm = this.tSsControllerMethodService.get(om.getMethod());
			if("check".equals(cm.getName())){
				checkFlag = true;
				break;
			}
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("list", omList);
		resultMap.put("type", checkFlag ? "check" : "edit");
		return ResultManager.createDataResult(resultMap, omList.size());
	}
	
	/**
	 * 修改用户权限
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "operator", message = "用户ID", required = true, type = DataType.STRING_ARRAY)
	@ActionParam(key = "method", message = "功能ID", required = true, type = DataType.STRING_ARRAY)
	@ResponseBody
	public Result edit(String[] operator, String[] method) {
		
		if(operator.length == 0){
			return ResultManager.createFailResult("未选择用户!");
		}
		
		List<TSsOperator> operatorList = new ArrayList<>();
		for(String o : operator){
			TSsOperator op = this.tSsOperatorService.get(o);
			if(op == null){
				return ResultManager.createFailResult("用户ID不存在!");
			}
			operatorList.add(op);
		}
		
		Boolean flagCheck = false;
		Boolean flagEdit = false;
		List<TSsControllerMethod> methodList = new ArrayList<>();
		List<String> menuList = new ArrayList<>();
		for(String m : method){
			TSsControllerMethod cm = this.tSsControllerMethodService.get(m);
			if(cm == null){
				return ResultManager.createFailResult("功能ID不存在!");
			}
			methodList.add(cm);
			
			if("check".equals(cm.getName())){
				flagCheck = true;
			}else{
				flagEdit = true;
			}
			
			TSsController tc = this.tSsControllerService.get(cm.getController());
			if(!menuList.contains(tc.getName())){
				menuList.add(tc.getName());
			}
		}
		
		if(flagCheck && flagEdit){
			return ResultManager.createFailResult("权限配置有误，请重新配置!");
		}
		
		this.tSsOperatorMethodService.updateAll(operatorList,methodList,menuList);
		
		return ResultManager.createSuccessResult("修改成功!");
	}
}
