/**
 * 
 */
package cn.product.score.controller.front;

import javax.servlet.http.HttpServletRequest;

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
 * 用户信息
 */

@Controller
@RequestMapping(value = "/front/userAccount")
public class FrontUserAccountController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7841938151051410448L;

	/**
	 * 获取用户账户信息
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Result get(HttpServletRequest request) {
		InputParams params = new InputParams("frontUserAccountService", "get");
		params.addParams("fu", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 用户流水儿信息
	 * @return
	 */
	@RequestMapping(value = "/historyList", method = RequestMethod.GET)
	@ActionParam(key = "type", type = DataType.STRING, message="类型")
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ResponseBody
	public Result historyList(HttpServletRequest request, Integer pageNum, Integer pageSize, String type) {
		
		InputParams params = new InputParams("capitalAccountService", "list");
		params.addParams("type", null, type);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("fu", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
}
