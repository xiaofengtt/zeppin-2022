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
@RequestMapping(value = "/back/roleMethod")
public class RoleMethodController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -895335163163232367L;

	/**
	 * 查询所有功能信息 
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Result all() {
		InputParams params = new InputParams("roleMethodService", "all");
		return this.execute(params);
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
		InputParams params = new InputParams("roleMethodService", "list");
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
	@ActionParam(key = "method", message = "方法", type = DataType.STRING_ARRAY)
	@ResponseBody
	public Result edit(String role, String[] method) {
		InputParams params = new InputParams("roleMethodService", "edit");
		params.addParams("role", null, role);
		params.addParams("method", null, method);
		return this.execute(params);
	}
}
