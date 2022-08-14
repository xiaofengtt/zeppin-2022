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


/**
 * 企业渠道账户
 */

@Controller
@RequestMapping(value = "/system/companyChannelAccount")
public class SystemCompanyChannelAccountController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8327735814098324249L;

	/**
	 * 根据条件查询
	 * @param channel
	 * @param company
	 * @param companyChannel
	 * @param channelAccount
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING)
	@ActionParam(key = "company", message="company", type = DataType.STRING)
	@ActionParam(key = "companyChannel", message="company channel", type = DataType.STRING)
	@ActionParam(key = "channelAccount", message="channel account", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="page number", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="page size", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public Result list(String channel, String company, String companyChannel, String channelAccount, Integer pageNum, Integer pageSize, String sort) {
		InputParams params = new InputParams("systemCompanyChannelAccountService", "list");
		params.addParams("channel", null, channel);
		params.addParams("company", null, company);
		params.addParams("companyChannel", null, companyChannel);
		params.addParams("channelAccount", null, channelAccount);
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
		InputParams params = new InputParams("systemCompanyChannelAccountService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
}
