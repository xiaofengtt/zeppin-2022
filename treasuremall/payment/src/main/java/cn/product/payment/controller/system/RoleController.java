package cn.product.payment.controller.system;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.base.BaseController;
import cn.product.payment.controller.base.Result;
import cn.product.payment.controller.base.ResultManager;
import cn.product.payment.entity.Role;
import cn.product.payment.service.RoleService;

@Controller
@RequestMapping(value = "/system/role")
public class RoleController extends BaseController{
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public Result all(){
		List<Role> list = roleService.getListByParams(new HashMap<String, Object>());
		return ResultManager.createDataResult(list);
	}
}
