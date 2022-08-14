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
import cn.product.score.entity.Category;

/**
 * 分类管理
 */

@Controller
@RequestMapping(value = "/back/category")
public class CategoryController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8036366762669690411L;

	/**
	 * 根据条件查询列表
	 * @param name
	 * @param shortname
	 * @param parent
	 * @param scode
	 * @param level
	 * @param istag
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "名称", type = DataType.STRING)
	@ActionParam(key = "shortname", message = "英文名称", type = DataType.STRING)
	@ActionParam(key = "parent", message = "父级", type = DataType.STRING)
	@ActionParam(key = "scode", message = "编码", type = DataType.STRING)
	@ActionParam(key = "level", message = "级别", type = DataType.NUMBER)
	@ActionParam(key = "istag", message = "是否是标签", type = DataType.BOOLEAN)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ResponseBody
	public Result list(Category category, Integer pageNum, Integer pageSize) {
		InputParams params = new InputParams("categoryService", "list");
		params.addParams("category", null, category);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		return this.execute(params);
	}
	
	/**
	 * 获取信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid) {
		InputParams params = new InputParams("categoryService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 添加
	 * @param name
	 * @param shortname
	 * @param parent
	 * @param icon
	 * @param istag
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "name", message = "名称", type = DataType.STRING, required = true)
	@ActionParam(key = "shortname", message = "英文名称", type = DataType.STRING, required = true)
	@ActionParam(key = "parent", message = "父级", type = DataType.STRING)
	@ActionParam(key = "icon", message = "图标", type = DataType.STRING)
	@ActionParam(key = "istag", message = "是否是标签", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(Category category) {
		
		InputParams params = new InputParams("categoryService", "add");
		params.addParams("category", null, category);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	/**
	 * 编辑
	 * @param uuid
	 * @param name
	 * @param shortname
	 * @param icon
	 * @param istag
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message = "名称", type = DataType.STRING, required = true)
	@ActionParam(key = "shortname", message = "英文名称", type = DataType.STRING, required = true)
	@ActionParam(key = "icon", message = "图标", type = DataType.STRING)
	@ActionParam(key = "istag", message = "是否是标签", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(Category category) {

		InputParams params = new InputParams("categoryService", "edit");
		params.addParams("category", null, category);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	/**
	 * 变更状态
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(Category category) {
		InputParams params = new InputParams("categoryService", "changeStatus");
		params.addParams("category", null, category);
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
		InputParams params = new InputParams("categoryService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}

}
