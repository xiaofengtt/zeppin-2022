/**
 * 
 */
package cn.product.payment.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.BaseController;
import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.controller.base.Result;

/**
 * 角色功能管理
 */

@Controller
@RequestMapping(value = "/system/roleMethod")
public class SystemRoleMethodController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8392623648770877489L;

	/**
	 * 查询所有功能信息 
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Result all() {
		InputParams params = new InputParams("systemRoleMethodService", "all");
		return this.execute(params);
	}
	
	/**
	 * 根据条件查询角色页面信息 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "role", message = "角色", type = DataType.STRING , required = true, maxLength = 36)
	@ResponseBody
	public Result list(String role) {
		InputParams params = new InputParams("systemRoleMethodService", "list");
		params.addParams("role", null, role);
		return this.execute(params);
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
		InputParams params = new InputParams("systemRoleMethodService", "edit");
		params.addParams("role", null, role);
		params.addParams("method", null, method);
		return this.execute(params);
	}
}
