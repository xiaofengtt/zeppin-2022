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
import cn.product.score.entity.Admin;

@Controller
@RequestMapping(value = "/back/admin")
public class AdminController extends BaseController{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7048839683461213590L;

	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
        InputParams params = new InputParams("adminService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "username", message="用户名", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(Admin admin, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("adminService", "list");
		params.addParams("admin", null, admin);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "username", message="用户名", type = DataType.STRING, required = true)
	@ActionParam(key = "realname", message="真实姓名", type = DataType.STRING, required = true)
	@ActionParam(key = "role", message="角色", type = DataType.STRING, required = true)
	@ActionParam(key = "password", message="密码", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
    public Result add(Admin admin){
		InputParams params = new InputParams("adminService", "add");
		params.addParams("admin", null, admin);
		return this.execute(params);
    }
    
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "realname", message="真实名称", type = DataType.STRING, required = true)
	@ActionParam(key = "role", message="角色", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result update(Admin admin){
		InputParams params = new InputParams("adminService", "update");
		params.addParams("admin", null, admin);
		return this.execute(params);
	}
	
	@RequestMapping(value="/password",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "password", message="密码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result password(Admin admin){
		InputParams params = new InputParams("adminService", "password");
		params.addParams("admin", null, admin);
		return this.execute(params);
	}
}
