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
import com.product.worldpay.entity.ChannelAccount;


/**
 * 渠道账户
 */

@Controller
@RequestMapping(value = "/system/channelAccount")
public class SystemChannelAccountController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2530157696724911034L;

	/**
	 * 根据条件查询
	 * @param channel
	 * @param name
	 * @param type
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING)
	@ActionParam(key = "name", message="name", type = DataType.STRING)
	@ActionParam(key = "type", message="type", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="page number", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="page size", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public Result list(String channel, String name, String status, String type, Integer pageNum, Integer pageSize, String sort) {
		InputParams params = new InputParams("systemChannelAccountService", "list");
		params.addParams("channel", null, channel);
		params.addParams("name", null, name);
		params.addParams("status", null, status);
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
		InputParams params = new InputParams("systemChannelAccountService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 创建
	 * @param channel
	 * @param name
	 * @param accountNum
	 * @param transferUrl
	 * @param data
	 * @param poundage
	 * @param poundageRate
	 * @param max
	 * @param min
	 * @param dailyMax
	 * @param totalMax
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="account name", type = DataType.STRING, required = true)
	@ActionParam(key = "accountNum", message="account number", type = DataType.STRING, required = true)
	@ActionParam(key = "transferUrl", message="transfer url", type = DataType.STRING, required = true)
	@ActionParam(key = "data", message="account data", type = DataType.STRING, required = true)
	@ActionParam(key = "poundage", message="fixed poundage", type = DataType.NUMBER)
	@ActionParam(key = "poundageRate", message="poundage rate", type = DataType.NUMBER)
	@ActionParam(key = "max", message="single max", type = DataType.NUMBER, required = true)
	@ActionParam(key = "min", message="single min", type = DataType.NUMBER, required = true)
	@ActionParam(key = "dailyMax", message="daily max", type = DataType.NUMBER, required = true)
	@ActionParam(key = "totalMax", message="total max", type = DataType.NUMBER, required = true)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(ChannelAccount channelAccount) {
		InputParams params = new InputParams("systemChannelAccountService", "add");
		params.addParams("channelAccount", null, channelAccount);
		return this.execute(params);
	}
	
	/**
	 * 修改
	 * @param uuid
	 * @param name
	 * @param accountNum
	 * @param data
	 * @param poundage
	 * @param poundageRate
	 * @param max
	 * @param min
	 * @param dailyMax
	 * @param totalMax
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="account name", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="account name", type = DataType.STRING, required = true)
	@ActionParam(key = "accountNum", message="account number", type = DataType.STRING, required = true)
	@ActionParam(key = "data", message="account data", type = DataType.STRING)
	@ActionParam(key = "poundage", message="fixed poundage", type = DataType.NUMBER)
	@ActionParam(key = "poundageRate", message="poundage rate", type = DataType.NUMBER)
	@ActionParam(key = "max", message="single max", type = DataType.NUMBER, required = true)
	@ActionParam(key = "min", message="single min", type = DataType.NUMBER, required = true)
	@ActionParam(key = "dailyMax", message="daily max", type = DataType.NUMBER, required = true)
	@ActionParam(key = "totalMax", message="total max", type = DataType.NUMBER, required = true)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(ChannelAccount channelAccount) {
		InputParams params = new InputParams("systemChannelAccountService", "edit");
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
		InputParams params = new InputParams("systemChannelAccountService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		return this.execute(params);
	}
}
