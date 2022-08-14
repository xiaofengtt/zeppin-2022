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
import cn.product.payment.entity.Company;

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

	/**
	 * 商户管理员取商户信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("storeCompanyService", "get");
		params.addParams("companyAdmin", null, getCompanyAdmin());
		return this.execute(params);
	}
	
	/**
	 * 修改商户公钥 白名单
	 * @param company
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "companyPublic", message="商户公钥", type = DataType.STRING, required = true)
	@ActionParam(key = "whiteUrl", message="白名单", type = DataType.STRING, required = true)
	@ResponseBody
	public Result password(Company company){
		InputParams params = new InputParams("storeCompanyService", "edit");
		params.addParams("company", null, company);
		params.addParams("companyAdmin", null, getCompanyAdmin());
		return this.execute(params);
	}
}
