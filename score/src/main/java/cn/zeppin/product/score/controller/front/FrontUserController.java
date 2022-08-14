/**
 * 
 */
package cn.zeppin.product.score.controller.front;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.score.controller.base.ActionParam;
import cn.zeppin.product.score.controller.base.ActionParam.DataType;
import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.controller.base.TransactionException;
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.service.FrontUserService;
import cn.zeppin.product.score.util.Base64Util;
import cn.zeppin.product.score.util.Utlity;

/**
 * 用户信息
 */

@Controller
@RequestMapping(value = "/front/user")
public class FrontUserController extends BaseController{
	
	@Autowired
	private FrontUserService frontUserService;
	
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
		FrontUser fu = getFrontUser(request);
		
		name = Base64Util.getFromBase64(name);
		idcard = Base64Util.getFromBase64(idcard);
		idcard = idcard.toUpperCase();
		if(!Utlity.checkIdCard(idcard)) {
			return ResultManager.createFailResult("认证失败，请填写正确的身份证号。");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idcard", idcard);
		Integer count = this.frontUserService.getCountByParams(params);
		if(count > 0){
			return ResultManager.createFailResult("认证失败，该身份证信息已被其他用户认证。");
		}
		try {
			this.frontUserService.certification(idcard, name, fu);
			return ResultManager.createSuccessResult("实名认证成功！");
		} catch (TransactionException e) {
			e.printStackTrace();
			return ResultManager.createFailResult("你输入的身份认证信息有误，请重新认证。");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("操作异常，实名认证失败！");
		}
	}
}
