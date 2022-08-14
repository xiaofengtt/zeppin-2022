package cn.zeppin.product.score.controller.back;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.entity.Role;
import cn.zeppin.product.score.service.RoleService;

@Controller
@RequestMapping(value = "/back/role")
public class RoleController extends BaseController{
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public Result all(String uuid){
		List<Role> role = roleService.getListByParams(new HashMap<String, Object>());
		return ResultManager.createDataResult(role);
	}
}
