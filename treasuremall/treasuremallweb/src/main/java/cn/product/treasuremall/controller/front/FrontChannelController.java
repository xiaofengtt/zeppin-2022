/**
 * 
 */
package cn.product.treasuremall.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.controller.BaseController;
import io.swagger.annotations.Api;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;

/**
 * 前端银行列表
 */

@Controller
@RequestMapping(value = "/front/channel")
@Api(tags= {"channel"})
public class FrontChannelController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1947633938873668804L;

	/**
	 * 获取渠道信息
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "channelId", message = "channelId", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String channelId) {
		
		InputParams params = new InputParams("frontChannelService", "get");
		params.addParams("channelId", null, channelId);
		return this.execute(params);
	}
}
