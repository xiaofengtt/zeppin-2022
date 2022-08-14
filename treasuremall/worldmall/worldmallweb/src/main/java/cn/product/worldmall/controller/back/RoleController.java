package cn.product.worldmall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;
import cn.product.worldmall.controller.BaseController;

@Controller
@RequestMapping(value = "/back/role")
public class RoleController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2398580208983043960L;

	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public Result all(String uuid){
		InputParams params = new InputParams("roleService", "all");
		return this.execute(params);
	}
}
