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
import cn.product.score.entity.Menu;

/**
 * 菜单管理
 */

@Controller
@RequestMapping(value = "/back/menu")
public class MenuController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4157987338307076250L;

	/**
	 * 根据条件查询菜单信息 
	 * @param scode
	 * @param level
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "菜单编码", type = DataType.STRING)
	@ActionParam(key = "level", message = "菜单级别", type = DataType.NUMBER)
	@ResponseBody
	public Result list(Menu menu) {
		InputParams params = new InputParams("menuService", "list");
		params.addParams("menu", null, menu);
		params.addParams("role", null, this.getCurrentOperator().getRole());
		return this.execute(params);
	}
	
	/**
	 * 查询角色左侧菜单信息 
	 * @return
	 */
	@RequestMapping(value = "/left", method = RequestMethod.GET)
	@ResponseBody
	public Result left() {
		InputParams params = new InputParams("menuService", "left");
		params.addParams("role", null, this.getCurrentOperator().getRole());
		return this.execute(params);
	}
	
	/**
	 * 获取列表（分页）
	 * @param uuid
	 * @param name
	 * @param description
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/pagelist", method = RequestMethod.GET)
	@ActionParam(key = "parent", message = "父级菜单编号", type = DataType.STRING)
	@ActionParam(key = "name", message = "搜索参数", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ResponseBody
	public Result pagelist(Menu menu, Integer pageNum, Integer pageSize, String sorts) {
		
		InputParams params = new InputParams("menuService", "pagelist");
		params.addParams("menu", null, menu);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sorts);
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
		InputParams params = new InputParams("menuService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 添加
	 * @param uuid
	 * @param name
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "菜单名称", type = DataType.STRING, required = true)
	@ActionParam(key = "parent", message = "所属菜单", type = DataType.STRING)
	@ActionParam(key = "url", message = "菜单链接", type = DataType.STRING)
	@ActionParam(key = "icon", message = "菜单图标", type = DataType.STRING)
	@ActionParam(key = "sort", message = "排序序号", type = DataType.NUMBER)
	@ResponseBody
	public Result add(Menu menu) {
		InputParams params = new InputParams("menuService", "add");
		params.addParams("menu", null, menu);
		return this.execute(params);
	}
	
	/**
	 * 编辑
	 * @param uuid
	 * @param name
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message = "菜单名称", type = DataType.STRING, required = true)
	@ActionParam(key = "url", message = "菜单链接", type = DataType.STRING)
	@ActionParam(key = "icon", message = "菜单图标", type = DataType.STRING)
	@ActionParam(key = "sort", message = "排序序号", type = DataType.NUMBER)
	@ResponseBody
	public Result edit(Menu menuEdit) {
		InputParams params = new InputParams("menuService", "edit");
		params.addParams("menuEdit", null, menuEdit);
		return this.execute(params);
	}
	
	/**
	 * 删除
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		InputParams params = new InputParams("menuService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
}
