package com.product.worldpay.controller.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.worldpay.controller.BaseController;
import com.product.worldpay.controller.base.ActionParam;
import com.product.worldpay.controller.base.ActionParam.DataType;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.controller.base.Result;

/**
 * 管理员
 */

@Controller
@RequestMapping(value = "/store/companyAdmin")
public class StoreCompanyAdminController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -809606445090900670L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("storeCompanyAdminService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/password",method=RequestMethod.POST)
	@ActionParam(key = "password", message="old password", type = DataType.STRING, required = true)
	@ActionParam(key = "newPassword", message="new password", type = DataType.STRING, required = true)
	@ResponseBody
	public Result password(String password, String newPassword){
		InputParams params = new InputParams("storeCompanyAdminService", "password");
		params.addParams("companyAdmin", null, getCompanyAdmin());
		params.addParams("password", null, password);
		params.addParams("newPassword", null, newPassword);
		return this.execute(params);
	}
}
