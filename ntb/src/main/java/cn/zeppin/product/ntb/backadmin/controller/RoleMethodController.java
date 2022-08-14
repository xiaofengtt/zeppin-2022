/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBkControllerMethodService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkControllerService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorRoleService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkRoleControllerPermissionService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkControllerMethod;
import cn.zeppin.product.ntb.core.entity.BkOperatorRole;
import cn.zeppin.product.ntb.core.entity.BkRoleControllerPermission;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;

/**
 * @author hehe
 *
 * 后台角色页面信息管理
 */

@Controller
@RequestMapping(value = "/backadmin/roleMethod")
public class RoleMethodController extends BaseController {

	@Autowired
	private IBkControllerService bkControllerService;
	
	@Autowired
	private IBkControllerMethodService bkControllerMethodService;
	
	@Autowired
	private IBkOperatorRoleService bkOperatorRoleService;
	
	@Autowired
	private IBkRoleControllerPermissionService bkRoleControllerPermissionService;
	
	/**
	 * 根据条件查询角色页面信息 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "role", message = "角色", type = DataType.STRING , required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result list(String role) {
		BkOperatorRole operatorRole = bkOperatorRoleService.get(role);
		
		if(operatorRole!=null && role.equals(operatorRole.getUuid())){
			Map<String, String> searchMap = new HashMap<>();
			searchMap.put("role", role);
			//查询符合条件的角色功能信息列表
			List<BkRoleControllerPermission> list = bkRoleControllerPermissionService.getByRole(operatorRole);
			
			return ResultManager.createDataResult(list, list.size());
		}else{
			return ResultManager.createFailResult("角色不存在！");
		}
	}
	
	/**
	 * 变更角色页面权限
	 * @param type
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST )
	@ActionParam(key = "role", message = "角色", type = DataType.STRING, required = true)
	@ActionParam(key = "method", message = "方法", type = DataType.STRING_ARRAY)
	@ResponseBody
	public Result edit(String role, String[] method) {
		//获取修改的角色
		BkOperatorRole operatorRole = bkOperatorRoleService.get(role);
		
		//判断角色是否存在
		if(operatorRole!=null && role.equals(operatorRole.getUuid())){
			List<BkControllerMethod> methodList = new ArrayList<>();
			
			//判断是否为空权限
			String[] methods = new String[0];
			if(method != null){
				methods = method;
			}
			
			//循环角色权限
			for(String m : methods){
				BkControllerMethod controller = bkControllerMethodService.get(m);
				methodList.add(controller);
			}
			bkRoleControllerPermissionService.updateAll(operatorRole, methodList);
			return ResultManager.createDataResult(null, "权限更改成功");
		}else{
			return ResultManager.createFailResult("角色不存在！");
		}
	}
}
