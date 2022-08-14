/**
 * 
 */
package cn.product.score.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.score.api.base.ActionParam;
import cn.product.score.api.base.ActionParam.DataType;
import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.Result;
import cn.product.score.controller.BaseController;

/**
 * 交易渠道
 */

@Controller
@RequestMapping(value = "/front/capital")
public class FrontCapitalAccountController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2353687743328229587L;

	/**
	 * 查询充值渠道
	 * @return
	 */
	@RequestMapping(value = "/platformList", method = RequestMethod.GET)
	@ResponseBody
	public Result platformList() {
		InputParams params = new InputParams("frontCapitalAccountService", "platformList");
		return this.execute(params);
	}
	
	/**
	 * 查询可选充值账号
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/accountList", method = RequestMethod.GET)
	@ActionParam(key = "capitalPlatform", message = "充值渠道", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String capitalPlatform) {
		
		InputParams params = new InputParams("frontCapitalAccountService", "get");
		params.addParams("capitalPlatform", null, capitalPlatform);
		return this.execute(params);
	}
}
