package cn.product.payment.controller.system;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.BaseController;
import cn.product.payment.controller.base.Result;
import cn.product.payment.controller.base.ResultManager;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.entity.Admin;
import cn.product.payment.entity.Admin.AdminStatus;
import cn.product.payment.service.AdminService;
import cn.product.payment.util.PasswordHelper;

@Controller
@RequestMapping(value = "/system/admin")
public class AdminController extends BaseController{
	
	@Autowired
    private AdminService adminService;
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		Admin admin = adminService.get(uuid);
		if(admin == null || AdminStatus.DELETE.equals(admin.getStatus())){
			return ResultManager.createFailResult("管理员不存在");
		}
		return ResultManager.createDataResult(admin);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "username", message="用户名", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(Admin admin, Integer pageNum, Integer pageSize, String sort){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", admin.getUsername());
		params.put("status", admin.getStatus());
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("sort", sort);
		
		Integer totalCount = adminService.getCountByParams(params);
		List<Admin> adminList = adminService.getListByParams(params);
		
		return ResultManager.createDataResult(adminList, pageNum, pageSize, totalCount);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "username", message="用户名", type = DataType.STRING, required = true)
	@ActionParam(key = "realname", message="真实姓名", type = DataType.STRING, required = true)
	@ActionParam(key = "role", message="角色", type = DataType.STRING, required = true)
	@ActionParam(key = "password", message="密码", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
    public Result add(Admin admin){
    	admin.setUuid(UUID.randomUUID().toString());
    	admin.setCreatetime(new Timestamp(System.currentTimeMillis()));
    	PasswordHelper.encryptPassword(admin);
    	adminService.insert(admin);
		return ResultManager.createSuccessResult("添加成功！");
    }
    
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "realname", message="真实名称", type = DataType.STRING, required = true)
	@ActionParam(key = "role", message="角色", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(Admin admin){
		Admin a = adminService.get(admin.getUuid());
		if(admin == null || AdminStatus.DELETE.equals(admin.getStatus())){
			return ResultManager.createFailResult("管理员不存在");
		}
		a.setRealname(admin.getRealname());
		a.setRole(admin.getRole());
		a.setStatus(admin.getStatus());
		adminService.update(a);
		return ResultManager.createSuccessResult("修改成功！");
	}
	
	@RequestMapping(value="/password",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "password", message="密码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result password(Admin admin){
		Admin a = adminService.get(admin.getUuid());
		if(admin == null || AdminStatus.DELETE.equals(admin.getStatus())){
			return ResultManager.createFailResult("管理员不存在");
		}
		a.setPassword(admin.getPassword());
		PasswordHelper.encryptPassword(a);
		adminService.update(a);
		return ResultManager.createSuccessResult("修改成功！");
	}
}
