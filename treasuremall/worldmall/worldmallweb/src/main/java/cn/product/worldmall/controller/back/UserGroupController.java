/**
 * 
 */
package cn.product.worldmall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;


/**
 * 用户级别信息管理
 */

@Controller
@RequestMapping(value = "/back/group")
public class UserGroupController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5620906278138133258L;

	/**
	 * 根据条件查询用户级别信息 
	 * @param title
	 * @param status
	 * @param type
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="英文名称", type = DataType.STRING)
	@ActionParam(key = "disciption", message="描述", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String disciption, Integer pageNum, Integer pageSize, String sorts) {
		
		InputParams params = new InputParams("userGroupService", "list");
		params.addParams("name", null, name);
		params.addParams("disciption", null, disciption);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sorts);
		return this.execute(params);
	}
	
	/**
	 * 获取一条用户级别信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "name", message="name", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String name) {	
		InputParams params = new InputParams("userGroupService", "get");
		params.addParams("name", null, name);
		return this.execute(params);
	}
	
	/**
	 * 添加一条用户级别信息
	 * @param name
	 * @param disciption
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "name", message="英文名称", type = DataType.STRING, required = true)
	@ActionParam(key = "disciption", message="描述", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(String name, String disciption) {
		
		InputParams params = new InputParams("userGroupService", "add");
		params.addParams("name", null, name);
		params.addParams("disciption", null, disciption);
		return this.execute(params);
	}
	
	/**
	 * 编辑一条用户级别信息
	 * @param name
	 * @param disciption
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "name", message="英文名称", type = DataType.STRING, required = true)
	@ActionParam(key = "disciption", message="描述", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(String name, String disciption) {
		InputParams params = new InputParams("userGroupService", "edit");
		params.addParams("name", null, name);
		params.addParams("disciption", null, disciption);
		return this.execute(params);
	}
}
