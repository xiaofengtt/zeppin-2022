/**
 * 
 */
package cn.product.worldmall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;


/**
 * 设备管理
 */

@Controller
@RequestMapping(value = "/back/device")
public class DeviceTokenController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2472602707747683313L;

	/**
	 *  列表
	 * @param deviceType
	 * @param frontUserGroup
	 * @param country
	 * @param ip
	 * @param topic
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param createtime
	 * @param totalRecharge
	 * @param totalWinning
	 * @param totalWithdraw
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "deviceType", message="设备类型", type = DataType.STRING)
	@ActionParam(key = "frontUserGroup", message="用户类型", type = DataType.STRING)
	@ActionParam(key = "country", message="国家ID", type = DataType.STRING)
	@ActionParam(key = "ip", message="ip", type = DataType.STRING)
	@ActionParam(key = "topic", message="主题ID", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ActionParam(key = "createtime", message="创建时间", type = DataType.STRING)
	@ActionParam(key = "totalRecharge", message="充值金额", type = DataType.STRING)
	@ActionParam(key = "totalWinning", message="中奖金额", type = DataType.STRING)
	@ActionParam(key = "totalWithdraw", message="提现金额", type = DataType.STRING)
	@ResponseBody
	public Result list(String deviceType, String frontUserGroup, String country, String ip, String topic, String status, Integer pageNum, Integer pageSize, String sorts
			, String createtime, String totalRecharge, String totalWinning, String totalWithdraw) {
		
		InputParams params = new InputParams("deviceTokenService", "list");
		params.addParams("deviceType", null, deviceType);
		params.addParams("frontUserGroup", null, frontUserGroup);
		params.addParams("country", null, country);
		params.addParams("ip", null, ip);
		params.addParams("topic", null, topic);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sorts);
		params.addParams("createtime", null, createtime);
		params.addParams("totalRecharge", null, totalRecharge);
		params.addParams("totalWinning", null, totalWinning);
		params.addParams("totalWithdraw", null, totalWithdraw);
		return this.execute(params);
	}
	
	/**
	 * 获取
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		
		InputParams params = new InputParams("deviceTokenService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	
	/**
	 * 批量发送
	 * @param uuids
	 * @param title
	 * @param content
	 * @return
	 */
	@RequestMapping(value="/send",method=RequestMethod.POST)
	@ActionParam(key = "uuids", message="uuid", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "title", message="标题", type = DataType.STRING, required = true)
	@ActionParam(key = "content", message="内容", type = DataType.STRING, required = true)
	@ResponseBody
	public Result send(String[] uuids, String title, String content){
		
		InputParams params = new InputParams("deviceTokenService", "send");
		params.addParams("uuids", null, uuids);
		params.addParams("title", null, title);
		params.addParams("content", null, content);
		return this.execute(params);
	}

	/**
	 * 批量绑定
	 * @param uuids
	 * @param topic
	 * @return
	 */
	@RequestMapping(value="/bind",method=RequestMethod.POST)
	@ActionParam(key = "uuids", message="uuid", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "topic", message="主题ID", type = DataType.STRING, required = true)
	@ResponseBody
	public Result bind(String[] uuids, String topic){
		
		InputParams params = new InputParams("deviceTokenService", "bind");
		params.addParams("uuids", null, uuids);
		params.addParams("topic", null, topic);
		return this.execute(params);
	}
}
