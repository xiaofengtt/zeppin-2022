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
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.controller.BaseController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 版本更新（开关）
 */

@Controller
@RequestMapping(value = "/front/version")
@ApiIgnore
public class FrontVersionController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4813498900946524108L;

	/**
	 * 获取版本信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "channel", message = "渠道ID", type = DataType.STRING, required = true)
	@ActionParam(key = "version", message = "版本号", type = DataType.STRING, required = true)
	@ActionParam(key = "bundleid", message = "包名", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String channel, String version, String bundleid) {
		InputParams params = new InputParams("frontVersionService", "get");
		params.addParams("channel", null, channel);
		params.addParams("version", null, version);
		params.addParams("bundleid", null, bundleid);
		return this.execute(params);
		
	}
}
