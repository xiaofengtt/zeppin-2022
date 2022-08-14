package cn.product.payment.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.BaseController;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.controller.base.Result;

@Controller
@RequestMapping(value = "/system/role")
public class SystemRoleController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -761454408051578045L;

	/**
	 * 所有角色列表
	 * @return
	 */
	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public Result all(){
		InputParams params = new InputParams("systemRoleService", "all");
		return this.execute(params);
	}
}
