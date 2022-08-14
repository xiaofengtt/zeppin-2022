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
import com.product.worldpay.entity.Admin;

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

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("systemAdminService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "username", message="username", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="page number", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="page size", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public Result list(Admin admin, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("systemAdminService", "list");
		params.addParams("admin", null, admin);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "username", message="username", type = DataType.STRING, required = true)
	@ActionParam(key = "realname", message="real name", type = DataType.STRING, required = true)
	@ActionParam(key = "role", message="role", type = DataType.STRING, required = true)
	@ActionParam(key = "password", message="password", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ResponseBody
    public Result add(Admin admin){
		InputParams params = new InputParams("systemAdminService", "add");
		params.addParams("admin", null, admin);
		return this.execute(params);
    }
    
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "realname", message="real name", type = DataType.STRING, required = true)
	@ActionParam(key = "role", message="role", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(Admin admin){
		InputParams params = new InputParams("systemAdminService", "edit");
		params.addParams("admin", null, admin);
		return this.execute(params);
	}
	
	@RequestMapping(value="/password",method=RequestMethod.POST)
	@ActionParam(key = "password", message="old password", type = DataType.STRING, required = true)
	@ActionParam(key = "newPassword", message="new password", type = DataType.STRING, required = true)
	@ResponseBody
	public Result password(String password, String newPassword){
		InputParams params = new InputParams("systemAdminService", "password");
		params.addParams("admin", null, getSystemAdmin());
		params.addParams("password", null, password);
		params.addParams("newPassword", null, newPassword);
		return this.execute(params);
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid){
        InputParams params = new InputParams("systemAdminService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	

	@RequestMapping(value="/getNotice",method=RequestMethod.GET)
	@ResponseBody
	public Result getNotice(String uuid){
        InputParams params = new InputParams("systemAdminService", "getNotice");
        params.addParams("admin", null, getSystemAdmin());
		return this.execute(params);
	}
}
