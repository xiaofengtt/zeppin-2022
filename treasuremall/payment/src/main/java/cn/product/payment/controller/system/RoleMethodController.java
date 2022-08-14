/**
 * 
 */
package cn.product.payment.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.BaseController;
import cn.product.payment.controller.base.Result;
import cn.product.payment.controller.base.ResultManager;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.entity.Method;
import cn.product.payment.entity.Role;
import cn.product.payment.entity.RoleMethod;
import cn.product.payment.service.MethodService;
import cn.product.payment.service.RoleMethodService;
import cn.product.payment.service.RoleService;
import cn.product.payment.vo.system.MethodVO;

/**
 * 角色功能管理
 */

@Controller
@RequestMapping(value = "/system/roleMethod")
public class RoleMethodController extends BaseController {

	@Autowired
	private MethodService methodService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleMethodService roleMethodService;
	
	/**
	 * 查询所有功能信息 
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Result all() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sort", "level asc");
		List<Method> controllerlist = methodService.getListByParams(params);
		List<MethodVO> dataList = new ArrayList<>();
		for(Method method:controllerlist){
			MethodVO controllerVO = new MethodVO(method);
			addMethod(dataList, controllerVO);
		}
		
		return ResultManager.createDataResult(dataList, dataList.size());
	}
	
	/**
	 * 根据条件查询角色页面信息 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "role", message = "角色", type = DataType.STRING , required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result list(String role) {
		Role operatorRole = roleService.get(role);
		
		if(operatorRole!=null && role.equals(operatorRole.getUuid())){
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("role", role);
			List<RoleMethod> list = roleMethodService.getListByParams(searchMap);
			
			return ResultManager.createDataResult(list, list.size());
		}else{
			return ResultManager.createFailResult("角色不存在！");
		}
	}
	
	/**
	 * 变更角色页面权限
	 * @param role
	 * @param method
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST )
	@ActionParam(key = "role", message = "角色", type = DataType.STRING, required = true)
	@ActionParam(key = "method", message = "方法", type = DataType.STRING_ARRAY)
	@ResponseBody
	public Result edit(String role, String[] method) {
		Role operatorRole = roleService.get(role);
		
		if(operatorRole!=null && role.equals(operatorRole.getUuid())){
			List<Method> methodList = new ArrayList<>();
			
			//判断是否为空权限
			String[] methods = new String[0];
			if(method != null){
				methods = method;
			}
			
			//循环角色权限
			for(String m : methods){
				Method rm = methodService.get(m);
				methodList.add(rm);
			}
			roleMethodService.updateAll(operatorRole, methodList);
			return ResultManager.createDataResult(null, "权限更改成功");
		}else{
			return ResultManager.createFailResult("角色不存在！");
		}
	}
	
	//多级分类添加
	static Boolean addMethod(List<MethodVO> voList, MethodVO vo){
		Boolean flag = false;
		for(MethodVO parent : voList){
			if(vo.getUrl().startsWith(parent.getUrl())){
				flag = true;
				addMethod(parent.getChildren(), vo);
				break;
			}
		}
		if(!flag){
			voList.add(vo);
		}
		return flag;
	}
}
