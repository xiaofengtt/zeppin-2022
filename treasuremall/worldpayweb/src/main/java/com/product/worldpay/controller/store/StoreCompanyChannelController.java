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


/**
 * 商户渠道
 */

@Controller
@RequestMapping(value = "/store/companyChannel")
public class StoreCompanyChannelController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2099308255544384130L;

	/**
	 * 根据条件查询
	 * @param channel
	 * @param type
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING)
	@ActionParam(key = "type", message="type", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="page number", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="page size", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public Result list(String channel, String status, String type, Integer pageNum, Integer pageSize, String sort) {
		InputParams params = new InputParams("storeCompanyChannelService", "list");
		params.addParams("channel", null, channel);
		params.addParams("type", null, type);
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
		InputParams params = new InputParams("storeCompanyChannelService", "get");
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
		InputParams params = new InputParams("storeCompanyChannelService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		params.addParams("companyAdmin", null, getCompanyAdmin());
		return this.execute(params);
	}
}
