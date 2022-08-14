/**
 * 
 */
package cn.product.payment.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.BaseController;
import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.controller.base.Result;
import cn.product.payment.entity.ChannelAccount;


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
	@ActionParam(key = "channel", message="渠道", type = DataType.STRING)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
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
	@ActionParam(key = "channel", message="渠道", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="账户名称", type = DataType.STRING, required = true)
	@ActionParam(key = "accountNum", message="账户号码", type = DataType.STRING, required = true)
	@ActionParam(key = "transferUrl", message="转发地址", type = DataType.STRING, required = true)
	@ActionParam(key = "data", message="账户数据", type = DataType.STRING, required = true)
	@ActionParam(key = "poundage", message="手续费（固定）", type = DataType.NUMBER)
	@ActionParam(key = "poundageRate", message="手续费（比率）", type = DataType.NUMBER)
	@ActionParam(key = "max", message="单次最高额度", type = DataType.NUMBER, required = true)
	@ActionParam(key = "min", message="单次最低额度", type = DataType.NUMBER, required = true)
	@ActionParam(key = "dailyMax", message="每日限额", type = DataType.NUMBER, required = true)
	@ActionParam(key = "totalMax", message="总限额", type = DataType.NUMBER, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
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
	@ActionParam(key = "uuid", message="账户名称", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="账户名称", type = DataType.STRING, required = true)
	@ActionParam(key = "accountNum", message="账户号码", type = DataType.STRING, required = true)
	@ActionParam(key = "data", message="账户数据", type = DataType.STRING)
	@ActionParam(key = "poundage", message="手续费（固定）", type = DataType.NUMBER)
	@ActionParam(key = "poundageRate", message="手续费（比率）", type = DataType.NUMBER)
	@ActionParam(key = "max", message="单次最高额度", type = DataType.NUMBER, required = true)
	@ActionParam(key = "min", message="单次最低额度", type = DataType.NUMBER, required = true)
	@ActionParam(key = "dailyMax", message="每日限额", type = DataType.NUMBER, required = true)
	@ActionParam(key = "totalMax", message="总限额", type = DataType.NUMBER, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
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
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status) {
		InputParams params = new InputParams("systemChannelAccountService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		return this.execute(params);
	}
}
