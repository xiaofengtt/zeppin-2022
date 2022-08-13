/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBkControllerService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkControllerMethodService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorRoleService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkRoleControllerPermissionService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;

/**
 * @author elegantclack
 *
 */
@Controller
@RequestMapping(value = "/backadmin/initialize")
public class InitializeController extends BaseController {

	@Autowired
	private IBkOperatorRoleService bkOperatorRoleService;
	
	@Autowired
	private IBkControllerService bkControllerService;
	
	@Autowired
	private IBkControllerMethodService bkControllerMethodService;
	
	@Autowired
	private IBkRoleControllerPermissionService bkRoleControllerPermissionService;
	
	
	
	@RequestMapping(value = "/build", method = RequestMethod.POST)
	@ActionParam(key = "d", type = DataType.STRING, required = true)
	@ResponseBody
	public Result build() {
//		
//		/**
//		 * 清除原有数据
//		 */
//		//删除原有的角色权限数据
//		bkRoleControllerPermissionService.deleteAll();
//		//删除原有的ControllerMethod
//		bkControllerMethodService.deleteAll();
//		//删除原有的Controller
//		bkControllerService.deleteAll();
//		
//		/**
//		 * 获取角色对象
//		 */
//		BkOperatorRole admin = bkOperatorRoleService.getByName("管理员");
//		BkOperatorRole operator = bkOperatorRoleService.getByName("运营编辑");
//		
//		/**
//		 * controller("initialize") permission builder
//		 */
//		//Controller("initialize")入库
//		BkController controller = new BkController();
//		controller.setUuid(UUID.randomUUID().toString());
//		controller.setName("initialize");
//		controller.setDescription("后台管理员控制器");
//		controller = bkControllerService.insert(controller);
//	    
//		//Method("initialize:build")入库
//		BkControllerMethod method = new BkControllerMethod();
//		method.setUuid(UUID.randomUUID().toString());
//		method.setController(controller.getUuid());
//		method.setName("build");
//		method.setDescription("添加管理员");
//		method = bkControllerMethodService.insert(method);
//		
//		//Role("admin") Permission("initialize:build")入库
//		BkRoleControllerPermission permission = new BkRoleControllerPermission();
//		permission.setUuid(UUID.randomUUID().toString());
//		permission.setRole(admin.getUuid()); //管理员
//		permission.setController(method.getController());
//		permission.setMethod(method.getUuid());
//		permission = bkRoleControllerPermissionService.insert(permission);
//		
//		
		return ResultManager.createSuccessResult("添加成功！");
	}
	

}
