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
@RequestMapping(value = "/front/bank")
@Api(tags= {"bank"})
public class FrontBankController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -687444890753909265L;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "search", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		
		InputParams params = new InputParams("frontBankService", "list");
		params.addParams("name", null, name);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sorts);
		return this.execute(params);
	}
}
