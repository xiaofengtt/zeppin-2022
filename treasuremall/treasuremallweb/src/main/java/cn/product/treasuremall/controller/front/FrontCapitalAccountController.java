/**
 * 
 */
package cn.product.treasuremall.controller.front;

import javax.servlet.http.HttpServletRequest;

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
import io.swagger.annotations.ApiOperation;

/**
 * 交易渠道
 */

@Controller
@RequestMapping(value = "/front/capital")
@Api(tags= {"capital"})
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
	 * 查询支付渠道
	 * @return
	 */
	@ApiOperation(value = "查询支付渠道", notes = "查询支付渠道")
	@RequestMapping(value = "/accountList", method = RequestMethod.GET)
	@ResponseBody
	public Result accountList(HttpServletRequest request) {
		InputParams params = new InputParams("frontCapitalAccountService", "accountList");
		//20200428增加按用户级别获取不同充值渠道
		params.addParams("level", null, this.getFrontUser(request) == null ? "registered" : this.getFrontUser(request).getLevel());
		params.addParams("frontUserStatus", null, this.getFrontUser(request) == null ? "normal" : this.getFrontUser(request).getStatus());
		params.addParams("frontUser", null, this.getFrontUser(request) == null ? "" : this.getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 查询可选充值账号
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "capitalPlatform", message = "capitalPlatform", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String capitalPlatform) {
		
		InputParams params = new InputParams("frontCapitalAccountService", "get");
		params.addParams("capitalPlatform", null, capitalPlatform);
		return this.execute(params);
	}
}
