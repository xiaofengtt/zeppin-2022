/**
 * 
 */
package cn.product.worldmall.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.controller.BaseController;
import io.swagger.annotations.Api;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;


/**
 * 用户绑卡
 */

@Controller
@RequestMapping(value = "/front/card")
@Api(tags= {"card"})
public class FrontUserBankcardController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 328062075078839764L;

	/**
	 * 获取用户绑定的卡列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Result list(HttpServletRequest request) {
		InputParams params = new InputParams("frontUserBankcardService", "list");
		params.addParams("fu", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 获取用户绑定的卡
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="uuid", required = true)
	@ResponseBody
	public Result get(String uuid, HttpServletRequest request){
		InputParams params = new InputParams("frontUserBankcardService", "get");
		params.addParams("fu", null, getFrontUser(request).getUuid());
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 用户绑定银行卡
	 * @param bankcard
	 * @param phone
	 * @param mobileCode
	 * @param bank
	 * @param idcard
	 * @param realName
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "accountNumber", type = DataType.STRING, message="accountNumber", required = true)
	@ResponseBody
	public Result add(String accountNumber, HttpServletRequest request){
		InputParams params = new InputParams("frontUserBankcardService", "add");
		params.addParams("fu", null, getFrontUser(request).getUuid());
		params.addParams("accountNumber", null, accountNumber);
		return this.execute(params);
	}
	
	/**
	 * 解绑银行卡
	 * @param bankcard
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="uuid", required = true)
	@ResponseBody
	public Result delete(String uuid, HttpServletRequest request){
		InputParams params = new InputParams("frontUserBankcardService", "delete");
		params.addParams("fu", null, getFrontUser(request).getUuid());
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
}
