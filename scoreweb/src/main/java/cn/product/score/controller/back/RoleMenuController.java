/**
 * 
 */
package cn.product.score.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.score.api.base.ActionParam;
import cn.product.score.api.base.ActionParam.DataType;
import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.Result;
import cn.product.score.controller.BaseController;

/**
 * 后台角色页面信息管理
 */

@Controller
@RequestMapping(value = "/back/roleMenu")
public class RoleMenuController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3797963853815788738L;

	/**
	 * 查询所有菜单信息 
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Result all() {
		InputParams params = new InputParams("roleMenuService", "all");
		return this.execute(params);
	}
	
	/**
	 * 根据条件查询角色页面信息 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "role", message = "角色", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result list(String role) {
		
		InputParams params = new InputParams("roleMenuService", "list");
		params.addParams("role", null, role);
		return this.execute(params);
	}
	
	/**
	 * 变更角色页面权限
	 * @param type
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST )
	@ActionParam(key = "role", message = "角色", type = DataType.STRING, required = true)
	@ActionParam(key = "menu", message = "权限", type = DataType.STRING_ARRAY)
	@ResponseBody
	public Result edit(String role, String[] menu) {
		
		InputParams params = new InputParams("roleMenuService", "edit");
		params.addParams("role", null, role);
		params.addParams("menu", null, menu);
		return this.execute(params);
	}
}
