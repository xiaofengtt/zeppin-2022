package cn.product.payment.controller.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.BaseController;
import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.controller.base.Result;

/**
 * 商户管理员
 */

@Controller
@RequestMapping(value = "/store/companyAdmin")
public class StoreCompanyAdminController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -809606445090900670L;

	/**
	 * 取商户管理员
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("storeCompanyAdminService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 商户管理员改密码
	 * @param password
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value="/password",method=RequestMethod.POST)
	@ActionParam(key = "password", message="原密码", type = DataType.STRING, required = true)
	@ActionParam(key = "newPassword", message="新密码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result password(String password, String newPassword){
		InputParams params = new InputParams("storeCompanyAdminService", "password");
		params.addParams("companyAdmin", null, getCompanyAdmin());
		params.addParams("password", null, password);
		params.addParams("newPassword", null, newPassword);
		return this.execute(params);
	}
}
