/**
 * 
 */
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
import com.product.worldpay.entity.CompanyBankcard;

/**
 * 商户银行卡
 */

@Controller
@RequestMapping(value = "/store/companyBankcard")
public class StoreCompanyBankcardController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8343438733516328663L;

	/**
	 * 根据条件查询
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="page number", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="page size", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public Result list(String status, Integer pageNum, Integer pageSize, String sort) {
		InputParams params = new InputParams("storeCompanyBankcardService", "list");
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("companyAdmin", null, getCompanyAdmin());
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
		InputParams params = new InputParams("storeCompanyBankcardService", "get");
		params.addParams("uuid", null, uuid);
		params.addParams("companyAdmin", null, getCompanyAdmin());
		return this.execute(params);
	}
	
	/**
	 * 绑定
	 * @param bank
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "bank", message="bank name", type = DataType.STRING, required = true, maxLength = 100)
	@ActionParam(key = "holder", message="holder", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "accountNum", message="bankcard number", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "area", message="area", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "branchBank", message="bank branch", type = DataType.STRING, required = true, maxLength = 200)
	@ResponseBody
	public Result add(CompanyBankcard companyBankcard) {
		InputParams params = new InputParams("storeCompanyBankcardService", "add");
		params.addParams("companyBankcard", null, companyBankcard);
		params.addParams("companyAdmin", null, getCompanyAdmin());
		return this.execute(params);
	}
	
	/**
	 * 删除
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result delete(String uuid) {
		InputParams params = new InputParams("storeCompanyBankcardService", "delete");
		params.addParams("uuid", null, uuid);
		params.addParams("companyAdmin", null, getCompanyAdmin());
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
		InputParams params = new InputParams("storeCompanyBankcardService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		params.addParams("companyAdmin", null, getCompanyAdmin());
		return this.execute(params);
	}
}
