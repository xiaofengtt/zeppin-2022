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
 * 菜单管理
 */

@Controller
@RequestMapping(value = "/system/menu")
public class SystemMenuController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7238854998401689359L;

	/**
	 * 根据条件查询
	 * @param name
	 * @param parent
	 * @param level
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "名称", type = DataType.STRING)
	@ActionParam(key = "parent", message = "父级", type = DataType.STRING)
	@ActionParam(key = "level", message = "级别", type = DataType.NUMBER)
	@ResponseBody
	public Result list(String name, String parent, Integer level) {
		InputParams params = new InputParams("systemMenuService", "list");
		params.addParams("name", null, name);
		params.addParams("parent", null, parent);
		params.addParams("level", null, level);
		params.addParams("admin", null, getSystemAdmin());
		return this.execute(params);
	}
	
	/**
	 * 查询角色左侧菜单信息 
	 * @return
	 */
	@RequestMapping(value = "/left", method = RequestMethod.GET)
	@ResponseBody
	public Result left() {
		InputParams params = new InputParams("systemMenuService", "left");
		params.addParams("admin", null, getSystemAdmin());
		return this.execute(params);
	}
	
	/**
	 * 获取信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		InputParams params = new InputParams("systemMenuService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
}
