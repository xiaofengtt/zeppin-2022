/**
 * 
 */
package com.product.worldpay.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.worldpay.controller.BaseController;
import com.product.worldpay.controller.base.ActionParam;
import com.product.worldpay.controller.base.ActionParam.DataType;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.controller.base.Result;

/**
 * 角色页面管理
 */

@Controller
@RequestMapping(value = "/system/roleMenu")
public class SystemRoleMenuController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7865786042822743139L;

	/**
	 * 查询所有菜单信息 
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Result all() {
		InputParams params = new InputParams("systemRoleMenuService", "all");
		return this.execute(params);
	}
	
	/**
	 * 根据条件查询角色页面信息 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "role", message = "role", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result list(String role) {
		InputParams params = new InputParams("systemRoleMenuService", "list");
		params.addParams("role", null, role);
		return this.execute(params);
	}
	
	/**
	 * 变更角色页面权限
	 * @param role
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST )
	@ActionParam(key = "role", message = "role", type = DataType.STRING, required = true)
	@ActionParam(key = "menu", message = "menu", type = DataType.STRING_ARRAY)
	@ResponseBody
	public Result edit(String role, String[] menu) {
		InputParams params = new InputParams("systemRoleMenuService", "edit");
		params.addParams("role", null, role);
		params.addParams("menu", null, menu);
		return this.execute(params);
	}
}
