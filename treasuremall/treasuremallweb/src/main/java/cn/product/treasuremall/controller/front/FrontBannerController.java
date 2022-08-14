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
import io.swagger.annotations.Api;

/**
 * 前端银行列表
 */

@Controller
@RequestMapping(value = "/front/banner")
@Api(tags= {"banner"})
public class FrontBannerController extends BaseController {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -521440374666127182L;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "sorts", type = DataType.STRING)
	@ActionParam(key = "level", message = "level", type = DataType.STRING)
	@ActionParam(key = "type", message = "type", type = DataType.STRING)
	@ResponseBody
	public Result list(Integer pageNum, Integer pageSize, String sorts, String level, String type) { 
		
		InputParams params = new InputParams("frontBannerService", "list");
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("type", null, type);
		params.addParams("sorts", null, sorts);
		//20200508增加按用户级别获取不同banner
		params.addParams("level", null, level);
		return this.execute(params);
	}
}
