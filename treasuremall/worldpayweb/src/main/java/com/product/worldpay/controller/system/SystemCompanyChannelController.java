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
import com.product.worldpay.entity.CompanyChannel;


/**
 * 商户渠道
 */

@Controller
@RequestMapping(value = "/system/companyChannel")
public class SystemCompanyChannelController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -795246258420734625L;

	/**
	 * 根据条件查询
	 * @param company
	 * @param channel
	 * @param type
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "company", message="company", type = DataType.STRING)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING)
	@ActionParam(key = "type", message="type", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="page number", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="page size", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public Result list(String company, String channel, String status, String type, Integer pageNum, Integer pageSize, String sort) {
		InputParams params = new InputParams("systemCompanyChannelService", "list");
		params.addParams("channel", null, channel);
		params.addParams("company", null, company);
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
		InputParams params = new InputParams("systemCompanyChannelService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 创建
	 * @param uuid
	 * @param company
	 * @param channel
	 * @param channelAccount
	 * @param max
	 * @param min
	 * @param poundage
	 * @param poundageRate
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "company", message="company", type = DataType.STRING, required = true)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING, required = true)
	@ActionParam(key = "channelAccount", message="channel account", type = DataType.STRING_ARRAY)
	@ActionParam(key = "max", message="single max", type = DataType.NUMBER, required = true)
	@ActionParam(key = "min", message="single min", type = DataType.NUMBER, required = true)
	@ActionParam(key = "poundage", message="fixed poundage", type = DataType.NUMBER)
	@ActionParam(key = "poundageRate", message="poundage rate", type = DataType.NUMBER)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(CompanyChannel companyChannel, String[] channelAccount) {
		InputParams params = new InputParams("systemCompanyChannelService", "add");
		Admin admin = getSystemAdmin();
		companyChannel.setCreator(admin.getUuid());
		
		params.addParams("companyChannel", null, companyChannel);
		params.addParams("channelAccount", null, channelAccount);
		return this.execute(params);
	}
	
	/**
	 * 修改
	 * @param uuid
	 * @param channelAccount
	 * @param max
	 * @param min
	 * @param poundage
	 * @param poundageRate
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "channelAccount", message="channel account", type = DataType.STRING_ARRAY)
	@ActionParam(key = "max", message="single max", type = DataType.NUMBER, required = true)
	@ActionParam(key = "min", message="single min", type = DataType.NUMBER, required = true)
	@ActionParam(key = "poundage", message="fixed poundage", type = DataType.NUMBER)
	@ActionParam(key = "poundageRate", message="poundage rate", type = DataType.NUMBER)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(CompanyChannel companyChannel, String[] channelAccount) {
		InputParams params = new InputParams("systemCompanyChannelService", "edit");
		params.addParams("companyChannel", null, companyChannel);
		params.addParams("channelAccount", null, channelAccount);
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
		InputParams params = new InputParams("systemCompanyChannelService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		return this.execute(params);
	}
}
