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
import cn.product.payment.entity.Admin;

/**
 * 管理员
 */

@Controller
@RequestMapping(value = "/system/admin")
public class SystemAdminController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3448051193405796455L;
	
	/**
	 * 获取管理员
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("systemAdminService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 管理员列表
	 * @param admin
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "username", message="用户名", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(Admin admin, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("systemAdminService", "list");
		params.addParams("admin", null, admin);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 添加管理员
	 * @param admin
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "username", message="用户名", type = DataType.STRING, required = true)
	@ActionParam(key = "realname", message="真实姓名", type = DataType.STRING, required = true)
	@ActionParam(key = "role", message="角色", type = DataType.STRING, required = true)
	@ActionParam(key = "password", message="密码", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
    public Result add(Admin admin){
		InputParams params = new InputParams("systemAdminService", "add");
		params.addParams("admin", null, admin);
		return this.execute(params);
    }
    
	/**
	 * 修改管理员
	 * @param admin
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "realname", message="真实名称", type = DataType.STRING, required = true)
	@ActionParam(key = "role", message="角色", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(Admin admin){
		InputParams params = new InputParams("systemAdminService", "edit");
		params.addParams("admin", null, admin);
		return this.execute(params);
	}
	
	/**
	 * 修改管理员密码
	 * @param password
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value="/password",method=RequestMethod.POST)
	@ActionParam(key = "password", message="原密码", type = DataType.STRING, required = true)
	@ActionParam(key = "newPassword", message="新密码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result password(String password, String newPassword){
		InputParams params = new InputParams("systemAdminService", "password");
		params.addParams("admin", null, getSystemAdmin());
		params.addParams("password", null, password);
		params.addParams("newPassword", null, newPassword);
		return this.execute(params);
	}
	
	/**
	 * 删除管理员
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid){
        InputParams params = new InputParams("systemAdminService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 获取管理员通知信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value="/getNotice",method=RequestMethod.GET)
	@ResponseBody
	public Result getNotice(String uuid){
        InputParams params = new InputParams("systemAdminService", "getNotice");
        params.addParams("admin", null, getSystemAdmin());
		return this.execute(params);
	}
}
