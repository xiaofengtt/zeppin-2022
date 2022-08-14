/**
 * 
 */
package cn.product.treasuremall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.controller.BaseController;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;


/**
 * 银行信息管理
 */

@Controller
@RequestMapping(value = "/back/bank")
public class BankController extends BaseController {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1202770811021716203L;

	/**
	 * 根据条件查询银行信息 
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		
		InputParams params = new InputParams("bankService", "list");
		params.addParams("name", null, name);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sorts);
		return this.execute(params);
	}
	
	/**
	 * 获取一条银行信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {	
		InputParams params = new InputParams("bankService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 添加一条银行信息
	 * @param name
	 * @param shortName
	 * @param logo
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "name", message="银行名称", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "shortName", message="简称", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "logo", message="图标", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "icon", message="ICON", type = DataType.STRING, minLength = 36, maxLength = 36)
	@ActionParam(key = "color", message="显示色值", type = DataType.STRING, maxLength = 10)
	@ActionParam(key = "iconColor", message="彩色ICON", type = DataType.STRING, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result add(String name, String logo, String status, String icon, String color, String shortName, String iconColor) {
		
		InputParams params = new InputParams("bankService", "add");
		params.addParams("name", null, name);
		params.addParams("logo", null, logo);
		params.addParams("status", null, status);
		params.addParams("icon", null, icon);
		params.addParams("color", null, color);
		params.addParams("shortName", null, shortName);
		params.addParams("iconColor", null, iconColor);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	/**
	 * 编辑一条银行信息
	 * @param uuid
	 * @param name
	 * @param shortName
	 * @param logo
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message="银行名称", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "shortName", message="简称", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "logo", message="图标", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "icon", message="ICON", type = DataType.STRING, minLength = 36, maxLength = 36)
	@ActionParam(key = "color", message="显示色值", type = DataType.STRING, maxLength = 10)
	@ActionParam(key = "iconColor", message="彩色ICON", type = DataType.STRING, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result edit(String uuid, String name, String logo, String status,String icon, String color, String shortName, String iconColor) {
		InputParams params = new InputParams("bankService", "edit");
		params.addParams("name", null, name);
		params.addParams("logo", null, logo);
		params.addParams("status", null, status);
		params.addParams("icon", null, icon);
		params.addParams("color", null, color);
		params.addParams("shortName", null, shortName);
		params.addParams("iconColor", null, iconColor);
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 删除一条银行信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		InputParams params = new InputParams("bankService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
}
