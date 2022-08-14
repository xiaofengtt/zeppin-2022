/**
 * 
 */
package com.product.worldpay.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.worldpay.controller.BaseController;
import com.product.worldpay.controller.base.ActionParam;
import com.product.worldpay.controller.base.ActionParam.DataType;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.controller.base.Result;
import com.product.worldpay.entity.Admin;
import com.product.worldpay.entity.Company;


/**
 * 商户
 */

@Controller
@RequestMapping(value = "/system/company")
public class SystemCompanyController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6780218767042483571L;

	/**
	 * 根据条件查询
	 * @param name
	 * @param code
	 * @param type
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="name", type = DataType.STRING)
	@ActionParam(key = "code", message="code", type = DataType.STRING)
	@ActionParam(key = "type", message="type", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="page number", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="page size", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String code, String status, String type, Integer pageNum, Integer pageSize, String sort) {
		InputParams params = new InputParams("systemCompanyService", "list");
		params.addParams("name", null, name);
		params.addParams("code", null, code);
		params.addParams("type", null, type);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 获取
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		InputParams params = new InputParams("systemCompanyService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 创建
	 * @param name
	 * @param companyPrivate
	 * @param companyPublic
	 * @param whiteUrl
	 * @param rechargePoundage
	 * @param rechargePoundageRate
	 * @param withdrawPoundage
	 * @param withdrawPoundageRate
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "name", message="name", type = DataType.STRING, required = true)
	@ActionParam(key = "companyPrivate", message="store private key", type = DataType.STRING)
	@ActionParam(key = "companyPublic", message="store public key", type = DataType.STRING)
	@ActionParam(key = "whiteUrl", message="white list", type = DataType.STRING, required = true)
	@ActionParam(key = "rechargePoundage", message="fixed store recharge poundage", type = DataType.NUMBER)
	@ActionParam(key = "rechargePoundageRate", message="store recharge poundage rate", type = DataType.NUMBER)
	@ActionParam(key = "withdrawPoundage", message="fixed store withdraw poundage", type = DataType.NUMBER)
	@ActionParam(key = "withdrawPoundageRate", message="store withdraw poundage rate", type = DataType.NUMBER)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(Company company) {
		InputParams params = new InputParams("systemCompanyService", "add");
		Admin admin = getSystemAdmin();
		company.setCreator(admin.getUuid());
		
		params.addParams("company", null, company);
		return this.execute(params);
	}
	
	/**
	 * 修改
	 * @param uuid
	 * @param name
	 * @param companyPrivate
	 * @param companyPublic
	 * @param whiteUrl
	 * @param rechargePoundage
	 * @param rechargePoundageRate
	 * @param withdrawPoundage
	 * @param withdrawPoundageRate
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "name", message="name", type = DataType.STRING, required = true)
	@ActionParam(key = "companyPrivate", message="store private key", type = DataType.STRING)
	@ActionParam(key = "companyPublic", message="store public key", type = DataType.STRING)
	@ActionParam(key = "whiteUrl", message="white list", type = DataType.STRING, required = true)
	@ActionParam(key = "rechargePoundage", message="fixed store recharge poundage", type = DataType.NUMBER)
	@ActionParam(key = "rechargePoundageRate", message="store recharge poundage rate", type = DataType.NUMBER)
	@ActionParam(key = "withdrawPoundage", message="fixed store withdraw poundage", type = DataType.NUMBER)
	@ActionParam(key = "withdrawPoundageRate", message="store withdraw poundage rate", type = DataType.NUMBER)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(Company company) {
		InputParams params = new InputParams("systemCompanyService", "edit");
		params.addParams("company", null, company);
		return this.execute(params);
	}
	
	/**
	 * 变更状态
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status) {
		InputParams params = new InputParams("systemCompanyService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		return this.execute(params);
	}
}
