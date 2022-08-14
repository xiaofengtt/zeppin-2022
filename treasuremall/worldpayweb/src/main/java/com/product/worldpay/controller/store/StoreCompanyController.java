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
import com.product.worldpay.entity.Company;

/**
 * 商户管理
 */

@Controller
@RequestMapping(value = "/store/company")
public class StoreCompanyController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8595844421609797550L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("storeCompanyService", "get");
		params.addParams("companyAdmin", null, getCompanyAdmin());
		return this.execute(params);
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "companyPublic", message="store public key", type = DataType.STRING, required = true)
	@ActionParam(key = "whiteUrl", message="white list", type = DataType.STRING, required = true)
	@ResponseBody
	public Result password(Company company){
		InputParams params = new InputParams("storeCompanyService", "edit");
		params.addParams("company", null, company);
		params.addParams("companyAdmin", null, getCompanyAdmin());
		return this.execute(params);
	}
}
