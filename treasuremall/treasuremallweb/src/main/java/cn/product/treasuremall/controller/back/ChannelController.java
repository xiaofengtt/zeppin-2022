/**
 * 
 */
package cn.product.treasuremall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.controller.BaseController;


/**
 * 注册渠道信息管理
 */

@Controller
@RequestMapping(value = "/back/channel")
public class ChannelController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9181742756654578336L;

	/**
	 * 根据条件查询注册渠道信息 
	 * @param title
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "title", message="名称", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String title, String status, Integer pageNum, Integer pageSize, String sorts) {
		
		InputParams params = new InputParams("channelService", "list");
		params.addParams("title", null, title);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sorts);
		return this.execute(params);
	}
	
	/**
	 * 获取一条注册渠道信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {	
		InputParams params = new InputParams("channelService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 添加一条注册渠道信息
	 * @param channelId
	 * @param title
	 * @param appkey
	 * @param channel
	 * @param isDefault
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "channelId", message="channelId", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "title", message="注册渠道名称", type = DataType.STRING, required = true, maxLength = 30)
	@ActionParam(key = "appkey", message="appkey", type = DataType.STRING, maxLength = 100)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "isDefault", message="是否默认", type = DataType.BOOLEAN, required = true)
	@ResponseBody
	public Result add(String channelId, String title, String appkey, String channel, Boolean isDefault) {
		
		InputParams params = new InputParams("channelService", "add");
		params.addParams("channelId", null, channelId);
		params.addParams("title", null, title);
		params.addParams("appkey", null, appkey);
		params.addParams("channel", null, channel);
		params.addParams("isDefault", null, isDefault);
		return this.execute(params);
	}
	
	/**
	 *  编辑一条注册渠道信息
	 * @param uuid
	 * @param channelId
	 * @param title
	 * @param appkey
	 * @param channel
	 * @param isDefault
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "channelId", message="channelId", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "title", message="注册渠道名称", type = DataType.STRING, required = true, maxLength = 30)
	@ActionParam(key = "appkey", message="appkey", type = DataType.STRING, maxLength = 100)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "isDefault", message="是否默认", type = DataType.BOOLEAN, required = true)
	@ResponseBody
	public Result edit(String uuid, String channelId, String title, String appkey, String channel, Boolean isDefault) {
		InputParams params = new InputParams("channelService", "edit");
		params.addParams("channelId", null, channelId);
		params.addParams("title", null, title);
		params.addParams("appkey", null, appkey);
		params.addParams("channel", null, channel);
		params.addParams("isDefault", null, isDefault);
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 删除一条注册渠道信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		InputParams params = new InputParams("channelService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 设为默认
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/isdefault", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result isdefault(String uuid) {
		InputParams params = new InputParams("channelService", "isdefault");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
}
