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
import cn.product.treasuremall.controller.BaseController;
import io.swagger.annotations.Api;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;


/**
 * 用户绑卡
 */

@Controller
@RequestMapping(value = "/front/bankcard")
@Api(tags= {"bankcard"})
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
	@ActionParam(key = "uuid", type = DataType.STRING, message="银行卡编号", required = true)
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
	@ActionParam(key = "bankcard", type = DataType.STRING, message="银行卡号", required = true)
	@ActionParam(key = "phone", type = DataType.STRING, message="银行预留手机号", required = true)
	@ActionParam(key = "mobileCode", type = DataType.STRING, message="验证码", required = true)
	@ActionParam(key = "bank", type = DataType.STRING, message="银行卡所属行", required = true)
	@ActionParam(key = "idcard", type = DataType.STRING, message="身份证号")
	@ActionParam(key = "realName", type = DataType.STRING, message="持卡人", required = true)
	@ResponseBody
	public Result add(String bankcard, String phone, String mobileCode, String bank, String idcard, String realName, HttpServletRequest request){
		InputParams params = new InputParams("frontUserBankcardService", "add");
		params.addParams("fu", null, getFrontUser(request).getUuid());
		params.addParams("realName", null, getFrontUser(request).getRealname());
		params.addParams("bankcard", null, bankcard);
		params.addParams("phone", null, phone);
		params.addParams("mobileCode", null, mobileCode);
		params.addParams("bank", null, bank);
		params.addParams("idcard", null, idcard);
		params.addParams("realName", null, realName);
		return this.execute(params);
	}
	
	/**
	 * 解绑银行卡
	 * @param bankcard
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message="银行卡编号", required = true)
	@ResponseBody
	public Result delete(String uuid, HttpServletRequest request){
		InputParams params = new InputParams("frontUserBankcardService", "delete");
		params.addParams("fu", null, getFrontUser(request).getUuid());
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
}
