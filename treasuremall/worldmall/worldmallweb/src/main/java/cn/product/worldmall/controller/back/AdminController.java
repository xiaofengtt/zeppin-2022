package cn.product.worldmall.controller.back;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;
import cn.product.worldmall.api.base.ResultManager;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.entity.Admin;
import cn.product.worldmall.util.Utlity;

@Controller
@RequestMapping(value = "/back/admin")
public class AdminController extends BaseController{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7048839683461213590L;
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
        InputParams params = new InputParams("adminService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "username", message="用户名", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
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
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
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
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
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
	
	
	@RequestMapping(value="/resetPwd",method=RequestMethod.POST)
	@ActionParam(key = "password", message="密码", type = DataType.STRING, required = true)
	@ActionParam(key = "oldPwd", message="原密码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result resetPwd(Admin admin, String oldPwd){
		InputParams params = new InputParams("adminService", "resetPwd");
		admin.setUuid(this.getCurrentOperator().getUuid());
		params.addParams("admin", null, admin);
		params.addParams("oldPwd", null, oldPwd);
		return this.execute(params);
	}
	
	@RequestMapping(value="/getInfo",method=RequestMethod.GET)
	@ResponseBody
	public Result getInfo(){
        InputParams params = new InputParams("adminService", "get");
		params.addParams("uuid", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid){
        InputParams params = new InputParams("adminService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}	
	
	@RequestMapping(value="/getNotice",method=RequestMethod.GET)
	@ResponseBody
	public Result getNotice(String uuid){
        InputParams params = new InputParams("adminService", "getNotice");
        Admin admin = getCurrentOperator();
        if(Utlity.roles.contains(admin.getRole())) {
        	return this.execute(params);
        } else {
        	return ResultManager.createDataResult(new HashMap<String, Object>());
        }
	}
}
