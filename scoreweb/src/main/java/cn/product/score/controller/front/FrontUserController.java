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
import cn.product.score.api.base.ResultManager;
import cn.product.score.controller.BaseController;
import cn.product.score.entity.FrontUser;

/**
 * 用户信息
 */

@Controller
@RequestMapping(value = "/front/user")
public class FrontUserController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8868788789286553051L;

	/**
	 * 获取用户信息
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Result get(HttpServletRequest request) {
		FrontUser fu = getFrontUser(request);
		return ResultManager.createDataResult(fu);
	}
	
	/**
	 * 实名认证
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/certification", method = RequestMethod.POST)
	@ActionParam(key = "name", type = DataType.STRING, message="用户姓名", required = true)
	@ActionParam(key = "idcard", type = DataType.STRING, message="用户身份证号", required = true)
	@ResponseBody
	public Result certification(String name, String idcard, HttpServletRequest request){
		InputParams params = new InputParams("frontUserService", "certification");
		params.addParams("name", null, name);
		params.addParams("idcard", null, idcard);
		params.addParams("fu", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
}
