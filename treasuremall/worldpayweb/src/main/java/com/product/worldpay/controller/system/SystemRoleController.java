package com.product.worldpay.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.worldpay.controller.BaseController;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.controller.base.Result;

@Controller
@RequestMapping(value = "/system/role")
public class SystemRoleController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -761454408051578045L;

	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public Result all(){
		InputParams params = new InputParams("systemRoleService", "all");
		return this.execute(params);
	}
}
